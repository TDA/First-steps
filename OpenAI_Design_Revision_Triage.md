# OpenAI Design Revision Triage

Date: 2026-05-17

This triage is based on the actual cheat-sheet headings in the design revision doc. I removed duplicate prompt families
and only marked items as 15-minute revisions when they have strong overlap with an existing cheat sheet.

## 15-Minute Revisions

These are close to existing cheat sheets and should be fast recall passes, not full redesigns.

| Prompt Family             | Source Prompts Covered                               | Cheat-Sheet Overlap                                         | Focus Areas                                                                                             |
|---------------------------|------------------------------------------------------|-------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| Video Streaming / Netflix | Design a Video Streaming Platform (Netflix)          | YouTube / Video Streaming Cheat Sheet                       | Ingestion, transcoding, object storage, CDN, adaptive bitrate playback, metadata, analytics.            |
| Slack / Chat Messaging    | Design Slack MVP; Design Slack-Like Messaging System | Chat System, Distributed Message Queue, Notification System | Workspaces/channels/messages, WebSockets, fanout, offline catch-up, ordering, notifications.            |
| Webhook Delivery Platform | Design a Webhook Delivery Platform                   | Distributed Message Queue, Notification System              | Subscriptions, delivery attempts, retries/backoff, idempotency, DLQ, replay, endpoint failure handling. |

## 30-45 Minute New / Higher-Risk Prompts

These are not direct cheat-sheet repeats. Some reuse familiar primitives, but they need a real skeleton pass.

| Prompt Family                       | Source Prompts Covered                                           | Why It Needs More Time                                                                          | Focus Areas                                                                                                                    |
|-------------------------------------|------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|
| ChatGPT                             | Design ChatGPT                                                   | No direct cheat sheet; OpenAI-specific model-serving path.                                      | Conversation history, prompt assembly, streaming, model routing, safety filters, rate limiting, telemetry, feedback loop.      |
| GitHub Actions / Multi-Tenant CI/CD | Design GitHub Actions; Design Multi-Tenant CI/CD Workflow System | No direct cheat sheet; overlaps queues/schedulers but has runner isolation and tenant concerns. | Workflow DAG, scheduler, isolated runners, logs/artifacts, caches, secrets, retries, quotas, RBAC, noisy-neighbor protection.  |
| Payments Pipeline                   | Design a Payments Pipeline                                       | No direct cheat sheet; transactional correctness and reconciliation heavy.                      | Idempotency, ledger, processor handoff, holds, settlement batches, reconciliation, retry/failure recovery.                     |
| Real-Time Chess                     | Design Online Chess; Design Real-Time Chess Service              | No direct cheat sheet; real-time correctness and game state.                                    | Authoritative move validation, move ordering, clocks, reconnect/resume, persistence/replay, spectators, sharding by game.      |
| Hosted Notebook Platform            | Design a Hosted Notebook Platform                                | No direct cheat sheet; multi-tenant compute orchestration.                                      | Workspace lifecycle, containers/VMs, cold start vs resume, persistent volumes, quotas, isolation, GPU/CPU scheduling.          |
| Distributed Crossword Solver        | Design a Distributed Crossword Solver                            | No direct cheat sheet; algorithmic search plus distributed execution.                           | Dictionary indexing, constraint propagation, task splitting, result streaming, cancellation, partial results, fault tolerance. |
| Mobile Model Usage Quotas           | Design Mobile Model Usage Quotas                                 | Partial overlap with Rate Limiter, but mobile/API contract is new.                              | Per-model quotas, reset windows, entitlement API, toast timing, upgrade CTA, client state model, auth.                         |

## Optional Cheat-Sheet Skims

These are not necessarily listed as tomorrow’s target prompts, but they are useful supporting primitives.

| Cheat Sheet                   | Why Skim                                                                    |
|-------------------------------|-----------------------------------------------------------------------------|
| Distributed Message Queue     | Supports Slack, Webhooks, CI/CD, async processing, retries.                 |
| Rate Limiter                  | Supports ChatGPT and Mobile Model Usage Quotas.                             |
| Notification System           | Supports Slack, Webhooks, mobile UX, and async delivery.                    |
| S3-like Object Storage        | Supports video, CI artifacts/logs, notebooks, and large object persistence. |
| Metrics Monitoring & Alerting | Useful operational layer for almost every design.                           |

## Sunday Execution Order

1. DDIA Ch. 5-6 vocabulary pass.
2. Design quiz #1.
3. DDIA Ch. 7-9 vocabulary pass.
4. 15-minute revisions: Video Streaming, Slack/Chat, Webhook Delivery.
5. 30-45-minute new prompts: ChatGPT, GitHub Actions/CI, Real-Time Chess.
6. If time remains, pick one: Hosted Notebook, Distributed Crossword, or Mobile Model Usage Quotas.
7. Design quiz #2.
8. One coding revision question.
9. Coding gotchas skim.

## Revision Template

For each design, spend the time as follows:

1. Requirements and assumptions: 2 minutes.
2. APIs and core entities: 3 minutes.
3. High-level architecture: 4 minutes.
4. Data model and storage choices: 3 minutes.
5. Deep dives and tradeoffs: 5-10 minutes.
6. Failure modes, observability, security: 3-5 minutes.

For 15-minute prompts, compress the last three steps. For 30-45-minute prompts, spend more time on the deep-dive axis.
