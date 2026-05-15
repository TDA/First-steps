package OpenAI_Interviews;

// Full Problem: Given a starting URL, crawl all pages within the same domain. Extract links from each page,
// avoid visiting the same URL twice, return all discovered URLs.
//
//Interface:
//
//crawl(startUrl) → List<String>
//

import java.net.URI;
import java.util.*;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.runTest;

/// / Assume a provided: getLinks(url) → List<String>
//
//Extensions:
//
//Make it concurrent (thread pool with N workers) — ExecutorService, ConcurrentHashMap, BlockingQueue
//Add depth limiting
//Add rate limiting (max N requests per second)
//Respect robots.txt

public class WebCrawler {
    private static final Map<String, List<String>> LINK_GRAPH = Map.ofEntries(
            Map.entry("https://openai.com", List.of(
                    "https://openai.com/research",
                    "https://openai.com/careers",
                    "https://openai.com/blog",
                    "https://example.com/outside"
            )),
            Map.entry("https://openai.com/research", List.of(
                    "https://openai.com/research/gpt-5",
                    "https://openai.com/blog",
                    "https://openai.com"
            )),
            Map.entry("https://openai.com/careers", List.of(
                    "https://openai.com/careers/software-engineer",
                    "https://jobs.ashbyhq.com/openai"
            )),
            Map.entry("https://openai.com/blog", List.of(
                    "https://openai.com/research",
                    "https://openai.com/blog/safety"
            )),
            Map.entry("https://openai.com/research/gpt-5", List.of(
                    "https://openai.com/blog/safety"
            )),
            Map.entry("https://openai.com/careers/software-engineer", List.of()),
            Map.entry("https://openai.com/blog/safety", List.of(
                    "https://openai.com"
            )),
            Map.entry("https://example.com", List.of(
                    "https://example.com/about",
                    "https://example.com/docs",
                    "https://openai.com"
            )),
            Map.entry("https://example.com/about", List.of(
                    "https://example.com/docs"
            )),
            Map.entry("https://example.com/docs", List.of(
                    "https://example.com/about"
            ))
    );

    // This is basically a breadth-first search starting from a given URL. The reason we don't want to do a depth first
    // search in a real world webcrawler would be that this likely can lead to sinkhole issues
    public List<String> crawl(String startUrl) {
        Set<String> visitedLinks = new LinkedHashSet<>();
        Deque<String> linkQueue = new ArrayDeque<>();
        // crawl all pages within the **same** domain
        String domain = extractDomain(startUrl);
        linkQueue.push(startUrl);
        while (!linkQueue.isEmpty()) {
            var link = linkQueue.poll();
            if (visitedLinks.contains(link)) continue;
            visitedLinks.add(link);
            for (var l : getLinks(link)) {
                if (domain.equals(extractDomain(l)))
                    linkQueue.add(l);
            }
        }
        return visitedLinks.stream().toList();
    }

    private String extractDomain(String url) {
        try {
            URI uri = new URI(url);
            return uri.getHost();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL");
        }
    }

    public List<String> getLinks(String url) {
        return LINK_GRAPH.getOrDefault(url, List.of());
    }

    public static void main(String[] args) {
        runTest("crawl returns start url when page has no links", () -> {
            WebCrawler crawler = new WebCrawler();

            assertEquals(
                    List.of("https://openai.com/careers/software-engineer"),
                    crawler.crawl("https://openai.com/careers/software-engineer"),
                    "crawler should include start URL even when it has no outgoing links"
            );
        });

        runTest("crawl discovers only same-domain links", () -> {
            WebCrawler crawler = new WebCrawler();

            assertEquals(
                    List.of(
                            "https://openai.com",
                            "https://openai.com/research",
                            "https://openai.com/careers",
                            "https://openai.com/blog",
                            "https://openai.com/research/gpt-5",
                            "https://openai.com/careers/software-engineer",
                            "https://openai.com/blog/safety"
                    ),
                    crawler.crawl("https://openai.com"),
                    "crawler should stay within the start URL domain and avoid duplicates"
            );
        });

        runTest("crawl handles cycles without duplicates", () -> {
            WebCrawler crawler = new WebCrawler();

            assertEquals(
                    List.of(
                            "https://example.com",
                            "https://example.com/about",
                            "https://example.com/docs"
                    ),
                    crawler.crawl("https://example.com"),
                    "crawler should terminate on cyclic graphs and return each URL once"
            );
        });

        runTest("separate crawl calls should not share visited state", () -> {
            WebCrawler crawler = new WebCrawler();

            assertEquals(
                    List.of("https://openai.com/careers/software-engineer"),
                    crawler.crawl("https://openai.com/careers/software-engineer"),
                    "first crawl should work"
            );
            assertEquals(
                    List.of(
                            "https://example.com",
                            "https://example.com/about",
                            "https://example.com/docs"
                    ),
                    crawler.crawl("https://example.com"),
                    "second crawl should not be affected by prior visited state"
            );
        });
    }
}
