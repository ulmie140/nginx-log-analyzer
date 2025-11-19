package com.university.nginxloganalyzer;

import com.university.nginxloganalyzer.model.LogEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogAnalysisService {
    private com.university.nginxloganalyzer.service.LogAnalysisService service;
    private List<LogEntry> entries;

    @BeforeEach
    void setUp() {
        service = new com.university.nginxloganalyzer.service.LogAnalysisService();
        entries = List.of(
                new LogEntry("192.168.1.1", "...", "GET", "/", "HTTP/1.1", 200, 100, "Mozilla"),
                new LogEntry("192.168.1.1", "...", "GET", "/a", "HTTP/1.1", 200, 200, "Mozilla"),
                new LogEntry("10.0.0.1", "...", "GET", "/api", "HTTP/1.1", 404, 150, "curl/7.68.0"),
                new LogEntry("10.0.0.1", "...", "POST", "/b", "HTTP/1.1", 404, 150, "curl/7.68.0"),
                new LogEntry("172.16.0.1", "...", "GET", "/x", "HTTP/1.1", 301, 0, "bot")
        );
    }

    @Test
    public void topIps() {
        var r = service.analyze(entries, 2, null);
        assertEquals("192.168.1.1", r.top_ips().get(0).ip());
        assertEquals(2L, r.top_ips().get(0).count()); // ← добавил L, на всякий
    }

    @Test
    public void statusCodes() {
        var r = service.analyze(entries, 10, null);
        assertEquals(2L, r.status_codes().get("200"));
        assertEquals(2L, r.status_codes().get("404"));
        assertEquals(1L, r.status_codes().get("301"));
    }

    @Test
    public void userAgentHits() {
        var r = service.analyze(entries, 10, "curl/7.68.0");
        assertEquals(2L, r.user_agent_hits());
    }

    @Test
    public void userAgentNoMatch() {
        var r = service.analyze(entries, 10, "Chrome");
        assertEquals(0L, r.user_agent_hits());
    }
}