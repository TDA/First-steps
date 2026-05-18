# Frequently Asked OpenAI Design Cheat Sheets

Date: 2026-05-17

Use these as 10-minute skim sheets. The goal is to know the domain-specific pressure points, not to memorize a full
script.

---

# Video Streaming / Netflix Cheat Sheet

## I. Requirements & Scope

Functional:

- Browse/search catalog.
- Start playback on supported devices.
- Stream video with adaptive quality.
- Track playback position across devices.
- Admin/content pipeline for upload, transcoding, packaging, and publishing.

Non-functional:

- Very high read/egress volume.
- Low startup latency and low rebuffering.
- High availability for playback.
- Eventual consistency is acceptable for catalog/recommendation updates.
- Stronger correctness for entitlements, account/device limits, and DRM.

Back-of-envelope:

- 100M DAU x 5 hours/day = 500M viewer-hours/day.
- Average concurrent viewers ~= 500M / 24 ~= 20M.
- Egress dominates. At 2-5GB/hour, daily egress is exabyte-scale.
- Media storage depends on catalog size and renditions, not daily views.

## II. High-Level Design

### APIs

- `GET /v1/videos?cursor=...`
- `GET /v1/videos/search?q=...`
- `GET /v1/videos/{videoId}/metadata`
- `POST /v1/videos/{videoId}/playback-session`
    - returns manifest URL, DRM license info, session ID.
- `POST /v1/users/{userId}/playback-state`
    - stores position, device, timestamp.

### Core Flow

Ingest path:

```text
Admin Upload -> Object Storage -> Queue -> Transcode/Package Workers
             -> Rendition Segments -> Metadata DB -> CDN Warm/Publish
```

Playback path:

```text
Client -> Playback API -> Entitlement/Device Check -> Manifest URL
       -> CDN Edge -> HLS/DASH Segments
```

### Storage

Video metadata:

- `video_id`, title, runtime, languages, content rating, manifest versions.

Playback state:

- key: `(user_id, video_id)`
- value: position, device, updated_at.

Media objects:

- original upload in object storage.
- transcoded renditions split into immutable segments.

Analytics:

- append-only playback events through Kafka/PubSub.

## III. Deep Dives

### Adaptive Bitrate

- API returns a manifest, not a single video file.
- Manifest lists segment URLs for multiple bitrates/resolutions.
- Client switches bitrate based on bandwidth, buffer, CPU, and device.

### CDN Strategy

- CDN absorbs most traffic.
- Use long TTLs because segments are immutable/versioned.
- Use origin shielding to prevent cache-miss storms.
- Pre-warm CDN for major releases.

### Live Streaming

- Live differs from VOD because renditions cannot be fully precomputed.
- Flow: live ingest -> real-time transcode/package -> low-latency segments -> CDN.
- Client decodes/renders; server-side packaging/transcoding remains necessary.

## IV. Gotchas

- Egress, not API QPS, is the dominant scaling axis.
- Viral releases are CDN/origin-shield problems more than DB hot-key problems.
- Store playback position separately from media metadata.
- Transcoding is async and retryable; use queues and DLQs.
- DRM/entitlements must be checked before playback session creation.
- Segment boundaries and encoding profiles can cause playback artifacts.
- For live, optimize latency vs reliability; smaller segments reduce latency but increase overhead.

---

# ChatGPT Cheat Sheet

## I. Requirements & Scope

Functional:

- Text-only conversational product.
- Create conversations and messages.
- Stream assistant responses.
- Preserve conversation history.
- Assemble prompts with system/developer/user context.
- Route requests to models.
- Apply safety filters and rate limits.
- Store telemetry and feedback.

Non-functional:

- Low time-to-first-token.
- High availability and graceful degradation.
- Strong privacy/security boundaries.
- Cost control for model usage.
- Abuse prevention.

## II. High-Level Design

### APIs

- `POST /v1/conversations`
- `POST /v1/conversations/{conversationId}/messages`
- `POST /v1/responses/stream`
- `GET /v1/conversations/{conversationId}/messages`
- `POST /v1/messages/{messageId}/feedback`

### Core Flow

```text
Client -> API Gateway -> Auth/Quota/Rate Limit
       -> Conversation Service -> Prompt Assembly
       -> Safety Precheck -> Model Router
       -> Inference Gateway -> Model Serving
       -> Safety Postcheck -> Stream Tokens
       -> Telemetry/Feedback Pipeline
```

### Storage

Conversation DB:

- `conversation_id`, `user_id`, created_at, metadata.

Message DB:

- `message_id`, `conversation_id`, role, content, token_count, created_at.

Usage/quota store:

- per user/org/model counters and reset windows.

Telemetry/events:

- append-only logs for latency, errors, model choice, feedback.

## III. Deep Dives

### Prompt Assembly

- Fetch recent conversation history.
- Add system/developer instructions.
- Apply context-window limits.
- Summarize or truncate older turns if needed.
- Avoid leaking hidden/system content.
- Use three context layers:
    - recent raw turns for exact continuity,
    - rolling summary for older conversation,
    - retrieval/embeddings for semantic long-term memory or external knowledge.
- Embeddings are useful for semantic recall, not as the only source for exact facts/instructions.

### Streaming

- Use SSE or WebSocket.
- Return partial tokens as generated.
- Persist final message once complete.
- Handle client disconnect: continue, cancel, or allow resume depending on product semantics.
- For risky outputs, buffer a small token window or run lightweight streaming classifiers before emitting.

### Model Routing

Inputs:

- model requested, user tier, latency/cost target, availability, safety risk, context length.

Fallback:

- degraded model, shorter context, retry on another region, or fail gracefully.
- Do not silently downgrade a user-selected paid model unless product policy explicitly allows it.

### Time To First Token

TTFT path:

```text
gateway -> auth/quota -> conversation fetch -> prompt assembly
        -> safety precheck -> model routing -> queueing/batching
        -> model prefill -> first token
```

Optimization levers:

- cache conversation metadata and summaries,
- reduce input tokens and retrieval payload,
- route to available low-latency capacity,
- separate interactive traffic from batch/background jobs,
- cap dynamic batching wait time,
- keep model workers warm,
- reuse prefix/KV cache for repeated system/context prefixes where possible.

## IV. Gotchas

- Time-to-first-token matters more than total response time for perceived latency.
- Prompt assembly is a correctness and safety boundary.
- Rate limits may be per user, org, IP, model, and endpoint.
- Caching is selective: exact prompt caching is hard; embeddings/retrieval/cacheable system context may help.
- Safety checks can run before and after inference.
- Idempotency matters if a client retries message submission.
- Message submission idempotency should return existing response/message state and avoid double-consuming quota.
- Conversation storage and telemetry have different consistency/privacy requirements.
- Cost control is a first-class design axis.
- Alert on TTFT, queue time by model/region, tokens/sec, safety flag rates, retry/idempotency hits, and cost per
  user/org/model.

---

# GitHub Actions / Multi-Tenant CI/CD Cheat Sheet

## I. Requirements & Scope

Functional:

- Trigger workflows from git events, manual dispatch, or schedule.
- Parse workflow DAG.
- Schedule jobs to isolated runners.
- Stream logs.
- Store artifacts and caches.
- Manage secrets securely.
- Support retries/cancellation.

Non-functional:

- Multi-tenant isolation.
- Fair scheduling and quotas.
- Secure execution of untrusted code.
- Durable job state.
- Autoscaling runner capacity.

## II. High-Level Design

### APIs

- `POST /v1/repos/{repoId}/workflow-runs`
- `GET /v1/workflow-runs/{runId}`
- `POST /v1/jobs/{jobId}/cancel`
- `GET /v1/jobs/{jobId}/logs`
- `GET /v1/jobs/{jobId}/artifacts`

### Core Flow

```text
Webhook/Event -> Workflow Service -> DAG Parser -> Job State DB
              -> Scheduler -> Runner Lease -> Runner Pool
              -> Logs/Artifacts/Caches -> Status Updates
```

### Storage

Workflow/run DB:

- run ID, repo, commit SHA, state.

Job DB:

- job ID, dependencies, state, runner lease, attempt number.

Logs:

- append-only stream to object storage/search index.

Artifacts/cache:

- object storage, scoped by repo/org/workflow/key.

Secrets:

- encrypted KMS-backed secret store.

## III. Deep Dives

### Scheduling

- Model workflow as a DAG.
- Jobs become runnable when dependencies succeed.
- Scheduler assigns jobs based on labels, tenant quota, priority, and runner availability.
- DAG and state machine are separate:
    - DAG defines dependencies,
    - each job has states such as `queued -> leased -> running -> succeeded/failed/canceled`.
- Manual approvals can be modeled as `waiting_for_approval -> approved -> queued`.

### Runner Isolation

- Run untrusted code in ephemeral VMs/containers.
- No reuse of dirty state across tenants.
- Network egress controls.
- Short-lived credentials.
- Forked/untrusted PRs should not get production secrets by default.
- Use restricted or staging-scoped credentials only when tenant policy allows it.

### Correctness

Strong consistency for:

- job state transitions,
- runner lease assignment,
- quota consumption,
- secret access policy.
- Use state updates like `WHERE job_id = ? AND lease_token = ? AND state = running` to prevent stale runners from
  committing.

Eventual consistency acceptable for:

- logs UI,
- metrics,
- notifications,
- artifact indexing.

## IV. Gotchas

- Assign each job to exactly one runner with a lease/fencing token.
- Runner may die after picking up a job; scheduler must detect heartbeat timeout and retry safely.
- Logs are append-only and can lag behind job state.
- Store logs/artifacts in object storage or search/log infrastructure; keep only pointers/metadata in the job DB.
- Secrets must never be written to logs; masking is defense-in-depth, not the primary control.
- Caches are best-effort and should not affect correctness.
- Noisy tenants need quotas and separate pools if necessary.
- Use idempotency keys for workflow triggers.
- Watch for cache poisoning across trust boundaries.
- Monitor queue depth by tenant/runner label, time in queue, runner utilization, stuck jobs, artifact/log failures,
  secret anomalies, and quota throttling.

---

# Payments Pipeline Cheat Sheet

## I. Requirements & Scope

Functional:

- Accept payment intent.
- Authorize/forward to payment processor.
- Hold funds.
- Capture/settle in daily batches.
- Track payment state.
- Support retries, refunds, reconciliation.

Non-functional:

- Correctness over availability.
- Idempotency for all external mutations.
- Auditability.
- Secure handling of payment data.
- Clear failure recovery.

## II. High-Level Design

### APIs

- `POST /v1/payment-intents`
- `POST /v1/payment-intents/{id}/authorize`
- `POST /v1/payment-intents/{id}/capture`
- `POST /v1/refunds`
- `GET /v1/payments/{id}`

### Core Flow

```text
Client -> Payment API -> Idempotency Check -> Payment DB/Ledger
       -> Processor Adapter -> Processor
       -> Webhook Receiver -> State Machine
       -> Settlement Batch -> Reconciliation
```

### Storage

Payment intent:

- `payment_id`, user, amount, currency, status, idempotency_key.

Ledger:

- append-only debits/credits.

Processor events:

- raw external events for audit/replay.

Settlement batch:

- batch ID, payments, totals, status.

## III. Deep Dives

### State Machine

Example:

```text
created -> authorized -> captured -> settled
        -> failed/canceled/refunded
```

Only allow valid transitions.

- A processor timeout means unknown, not failed. Use `capture_pending` / `unknown` and reconcile by webhook, processor
  lookup, or safe retry.

### Idempotency

- Client supplies idempotency key.
- Server stores request hash and result.
- Retry returns same result if original succeeded.
- Processor calls also need idempotency keys when supported.
- Store idempotency key + request hash + final result atomically.

### Reconciliation

- Compare internal ledger with processor reports.
- Detect missing, duplicate, or mismatched transactions.
- Never rely only on synchronous API response; webhooks and settlement files are source-of-truth inputs too.
- Settlement is source of truth for funds movement/reconciliation, not necessarily the first signal that authorization
  succeeded.

### Processor Adapter And Webhooks

- Processor adapter wraps Stripe/Adyen/bank-specific APIs for auth, capture, refund, idempotency, retries, and status
  mapping.
- Webhook receiver ingests async processor facts: authorization/capture updates, chargebacks, refunds, risk review,
  settlement events.
- Deduplicate webhooks by provider event ID and verify signatures.

## IV. Gotchas

- Do not update balances with ad hoc mutable counters; use an append-only ledger.
- Processor API timeout does not mean payment failed.
- Webhooks can be duplicated, delayed, or out of order.
- Exactly-once is usually implemented as at-least-once plus idempotency.
- Daily settlement is a batch workflow with reconciliation and exception handling.
- PCI scope: avoid storing raw card data; use tokens from processor.
- Strong consistency matters around money movement and state transitions.
- We are usually designing an internal merchant payment pipeline, not building Stripe/card networks.

---

# Real-Time Chess Cheat Sheet

## I. Requirements & Scope

Functional:

- Create/join games.
- Matchmaking.
- Real-time legal moves.
- Chess clocks.
- Reconnect/resume.
- Game end by checkmate, timeout, resignation, draw.
- Spectators and replay as extensions.

Non-functional:

- Low-latency move delivery.
- Strong correctness for move order and clock state.
- Durable game history.
- Graceful reconnect.

## II. High-Level Design

### APIs

- `POST /v1/games`
- `POST /v1/matchmaking/join`
- `POST /v1/games/{gameId}/moves`
- `GET /v1/games/{gameId}`
- `WebSocket /v1/games/{gameId}/stream`

### Core Flow

```text
Client -> Game Gateway/WebSocket -> Game Service
       -> Validate Move -> Persist Event -> Update State
       -> Fanout to Opponent/Spectators
```

### Storage

Game state:

- game ID, players, board state/FEN, turn, clocks, status.

Move log:

- append-only move events with sequence numbers.

Connection/session store:

- user/device -> active connection.

## III. Deep Dives

### Move Ordering

- Each game has one authoritative sequencer.
- Moves carry expected version/turn number.
- Reject stale or out-of-turn moves.
- Persist before fanout if replay/recovery matters.
- Route moves for a game to one authoritative owner/actor using consistent hashing or a routing table.
- The reason is single-writer ordering, not mainly NTP avoidance.

### Clocks

- Server is authoritative.
- Store last move timestamp and remaining time.
- Compute elapsed time at move receipt.
- Use monotonic time on server process for durations; persist timestamps carefully.
- Persist remaining time, current turn, and last committed move time so another server can recover after failover.

### Reconnect

- Client reconnects with last seen sequence.
- Server sends missed moves/events.
- Current state can be rebuilt from snapshot + move log.
- Moves should include a move ID/idempotency key plus expected sequence/version. Retried moves return committed result
  or are rejected as stale.

## IV. Gotchas

- Do not trust client-side move validation or clocks.
- Exactly one side can move at a time; enforce expected sequence/version.
- WebSocket delivery can be duplicated or missed; use sequence numbers.
- Game state is small, so shard by game ID.
- Spectators should not slow down player move path.
- For anti-cheat, keep it out of critical move latency path initially.
- Clock correctness is usually the hardest domain-specific part.
- Timer expiration can happen without a move; the server needs a timeout/check mechanism.

---

# Hosted Notebook Platform Cheat Sheet

## I. Requirements & Scope

Functional:

- Create/open notebook workspace.
- Execute code cells.
- Persist notebooks/files.
- Resume previous workspace quickly.
- Support CPU/GPU runtimes.
- Share/collaborate as extension.

Non-functional:

- Multi-tenant isolation.
- Fast cold start/resume.
- Resource quotas.
- Durable storage.
- Safe execution of untrusted code.

## II. High-Level Design

### APIs

- `POST /v1/workspaces`
- `POST /v1/workspaces/{id}/start`
- `POST /v1/workspaces/{id}/execute`
- `POST /v1/workspaces/{id}/stop`
- `GET /v1/workspaces/{id}/files`

### Core Flow

```text
Client -> Notebook API -> Workspace Manager
       -> Scheduler -> Runtime Pod/VM
       -> Kernel Gateway -> Execute Cell
       -> Persistent Volume/Object Store
```

### Storage

Notebook metadata:

- owner, workspace ID, runtime image, state.

Files:

- object storage or network volume.

Runtime state:

- ephemeral container/VM plus checkpoints if supported.

Events/logs:

- execution logs and metrics.
- Treat durable notebook/files separately from ephemeral kernel memory and process state.

## III. Deep Dives

### Workspace Lifecycle

States:

```text
stopped -> starting -> running -> idle -> stopping -> stopped
                      -> failed
```

Keep warm pools for common images to reduce cold start.

- Optional states: `snapshotting -> restoring` if runtime checkpointing is supported.

### Isolation

- Containers are lighter, VMs stronger.
- GPU workloads may need node-level scheduling.
- Network egress and filesystem access should be restricted.
- Secrets injected as short-lived tokens.

### Persistence

- Notebook document and files are durable.
- Kernel memory is ephemeral unless checkpointing is implemented.
- Autosave to object storage or volume snapshots.
- Outputs can be large; store them separately from notebook metadata.
- Fast resume can mean either keeping idle runtimes warm for a TTL or restoring from snapshots.

## IV. Gotchas

- Fast resume and strong isolation fight each other.
- GPU scheduling is capacity-constrained and quota-heavy.
- Idle timeout saves cost but can annoy users.
- Package installation creates image/cache complexity.
- Persistent volumes need backup and lifecycle cleanup.
- Never let one tenant access another tenant’s filesystem, network, or secrets.
- Logs/outputs can contain sensitive data.
- Package installs create environment drift; use images, layer caches, or environment snapshots.
- Runtime scheduling dimensions include CPU/GPU, memory, image type, tenant quota, priority, region/data locality, and
  warm-pool availability.
- The hard part is isolated, resumable compute workspaces; CRUD for notebooks is the easy part.

---

# Distributed Crossword Solver Cheat Sheet

## I. Requirements & Scope

Functional:

- Receive crossword grid with blocks, blanks, optional letters.
- Use a dictionary version.
- Fill horizontal and vertical slots.
- Respect crossings and optional no-reuse rule.
- Return one or more solutions.

Non-functional:

- Support large dictionaries.
- Support puzzles with large search spaces.
- Allow cancellation/time limits.
- Stream partial results if useful.

## II. High-Level Design

### APIs

- `POST /v1/crosswords/solve`
- `GET /v1/solve-jobs/{jobId}`
- `GET /v1/solve-jobs/{jobId}/solutions`
- `POST /v1/solve-jobs/{jobId}/cancel`

### Core Flow

```text
API -> Validate Grid -> Extract Slots -> Dictionary Index Lookup
    -> Solver Coordinator -> Search Workers
    -> Solution Store -> Stream/Return Results
```

### Storage

Dictionary index:

- by word length.
- by pattern constraints, e.g. length + known positions.
- Source of truth can be object storage or durable DB; hot indexes are precomputed artifacts loaded into worker memory.

Solve job:

- grid, dictionary version, status, limits.

Solutions:

- assignments, score/order, created_at.

## III. Deep Dives

### Algorithm

- Extract across/down slots.
- For each slot, compute candidate words by length and known letters.
- Pick most constrained slot first.
- Backtracking search:
    - assign word,
    - propagate crossing constraints,
    - prune invalid branches.

### Distribution

- Split search by early assignments.
- Coordinator creates subproblems.
- Workers explore independently.
- Store partial/final results.
- Support cancellation and timeout.
- Use a distributed work queue, not Pub/Sub broadcast; each subproblem should be processed by one worker.
- Handle stragglers because some branches are much harder than others.

### Dictionary Indexing

- `length -> words`.
- Optional inverted index: `(length, position, letter) -> word IDs`.
- Intersect sets to answer pattern queries quickly.
- Offline pipeline:
    - raw dictionary -> normalize/validate -> build indexes -> serialize index files -> object storage -> publish
      dictionary version.
- Online path:
    - solve request pins dictionary version; workers hydrate/cached index and do in-memory lookup/backtracking.

## IV. Gotchas

- Naive brute force explodes combinatorially.
- Good slot ordering matters more than raw worker count.
- Dictionary version must be fixed per job for reproducibility.
- Avoid duplicate word use if required by prompt.
- Some subproblems are much harder than others; dynamic work stealing helps.
- Return first solution vs all solutions changes runtime/cost drastically.
- Cancellation must stop workers and clean partial state.
- Dictionary lookups must not be network calls in the hot inner loop.

---

# Mobile Model Usage Quotas Cheat Sheet

## I. Requirements & Scope

Functional:

- App shows which model versions user can access.
- Each model has its own free quota.
- When quota exceeded, show time until next free access.
- Provide upgrade CTA.
- Backend APIs are platform-independent.
- Mobile architecture can be MVVM or MVI.

Non-functional:

- Low-latency quota checks.
- Correct enough to prevent abuse.
- Good offline/degraded UX.
- Secure entitlement enforcement on backend.

## II. High-Level Design

### APIs

- `GET /v1/models`
- `GET /v1/users/{userId}/model-entitlements`
- `POST /v1/model-usage/check`
- `POST /v1/model-usage/consume`

Example response:

```json
{
  "model": "gpt-x",
  "allowed": false,
  "remaining": 0,
  "resetsAt": "2026-05-18T10:00:00Z",
  "upgradeUrl": "..."
}
```

### Core Flow

```text
Mobile App -> Entitlement API -> Quota Service
           -> Plan/Subscription Service
           -> Usage Counter Store
           -> Response with allowed/remaining/resetsAt
```

### Storage

Plan config:

- model, tier, quota, reset window.

Usage counters:

- key: `(user_id, model_id, window_start)`.
- value: consumed count/tokens.

Entitlements:

- user/org plan, overrides, experiments.

## III. Deep Dives

### Quota Enforcement

- UI check is advisory.
- Backend must enforce quota at actual model request time.
- Use atomic increment/check in Redis/DynamoDB/SQL transaction.
- Include idempotency key for retries.
- Quota check and consume should usually be one atomic operation on send/model request.
- Store idempotency key per message/request to avoid double-consuming quota on retries.

### Mobile Architecture

MVVM:

- ViewModel exposes model access state.
- Repository calls entitlement/quota APIs.
- UI renders allowed/blocked/toast/upgrade CTA.

MVI:

- state includes selected model, access status, reset time, loading/error.
- intents: select model, send message, upgrade click.
- You only need lightweight familiarity: client is a thin stateful UI, backend is source of truth.
- Client cache is okay for model picker UX, but it is advisory and should be short-lived/stale-while-revalidate.

### Reset Windows

- Fixed daily windows are simple.
- Rolling windows are fairer but harder.
- Response should include server-computed `resetsAt`; client should not infer.
- For "resets every 3 hours," fixed windows are usually enough; token bucket fits refill/burst semantics; sliding window
  is overkill unless fairness around boundaries matters.
- Compute/update active windows on request; do not precompute unused windows.

### Plan vs Quota Service

- Plan/Subscription Service answers: what plan is the user/org on, and which models/features are they entitled to?
- Quota Service answers: for this user/org/model/window, how much has been consumed and what remains?

## IV. Gotchas

- Never rely only on client-side quota state.
- Use server time, not device clock.
- Quota check and consume can race; prefer atomic consume on request.
- Idempotency prevents retries from double-consuming quota.
- Per-model quotas mean model switching changes eligibility.
- Paid upgrades may need immediate entitlement refresh.
- Toast copy needs exact wait time from backend.
- Offline mode should not grant access to server-side models.
- After upgrade, client should force-refresh entitlements; push/WebSocket is nice but not required.
- Avoid relying on app-server wall clocks for reset correctness; use server-computed reset time and an authoritative
  quota store.

---

# Webhook Delivery Platform Cheat Sheet

## I. Requirements & Scope

Functional:

- Producers emit events.
- Customers register subscriptions/endpoints.
- Deliver HTTPS POST webhooks.
- Retry with backoff.
- Dead-letter failed events.
- Replay support.

Non-functional:

- At-least-once delivery.
- Durable event storage.
- Endpoint isolation.
- High fanout scalability.
- Clear observability for customers.

## II. High-Level Design

### APIs

- `POST /v1/events`
- `POST /v1/subscriptions`
- `GET /v1/deliveries/{deliveryId}`
- `POST /v1/deliveries/{deliveryId}/replay`

### Core Flow

```text
Producer -> Event API -> Event Store -> Match Subscriptions
         -> Delivery Queue -> Delivery Workers
         -> Customer Endpoint -> Attempt Store/DLQ
```

### Storage

Events:

- event ID, type, payload, timestamp.

Subscriptions:

- customer ID, endpoint URL, event filters, secret.

Delivery attempts:

- delivery ID, event ID, endpoint, attempt count, status, response code.

## III. Deep Dives

### Events vs Subscriptions

- `POST /events` is for producers to create one durable event.
- `POST /subscriptions` is for customers to register endpoint URL, event filters, and secret.
- Internal queue consumers are delivery workers; external consumers receive HTTPS POST webhooks.

### Delivery Semantics

- At-least-once is realistic.
- Customer must dedupe by event ID/delivery ID.
- Sign payloads with shared secret.
- Retry transient failures, not permanent 4xx except selected cases.
- If customer processes an event but the connection times out before our system receives `200`, retry may duplicate
  delivery.

### Retry Policy

- Exponential backoff + jitter.
- Max attempts or max age.
- DLQ after exhaustion.
- Per-customer rate limits to avoid hammering bad endpoints.
- `5xx`, `408`, and `429` are usually retriable.
- Most `4xx` are non-retriable; `410 Gone` may disable the endpoint/subscription.

### Security

- Sign payload with HMAC using the customer's webhook secret.
- Include timestamp and signature headers.
- Customer verifies signature and timestamp to prevent spoofing and replay.

## IV. Gotchas

- Webhooks can be duplicated and out of order.
- Customer endpoints are unreliable and slow.
- One bad customer must not block others.
- Payload signing and timestamp prevent spoofing/replay.
- Replay should reuse original event payload, possibly with new delivery ID.
- Observability is part of product: attempt logs, response codes, next retry time.
- Store original event before enqueueing deliveries; the event store is source of truth for retry, replay, audit, and
  delivery reconstruction.
