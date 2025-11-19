package com.university.nginxloganalyzer.report;

import java.util.List;
import java.util.Map;

/**
 * Отчёт, формируемый после анализа логов.
 *
 * @param top_ips        список топ-IP с количеством запросов
 * @param status_codes   карта «код ответа → количество»
 * @param user_agent_hits количество запросов с указанным User-Agent
 */
public record Report(
        List<IpCount> top_ips,
        Map<String, Long> status_codes,
        long user_agent_hits
) {
    /** Пара IP-адрес ↔ количество запросов */
    public record IpCount(String ip, long count) {}
}