package com.university.nginxloganalyzer.service;

import com.university.nginxloganalyzer.model.LogEntry;
import com.university.nginxloganalyzer.report.Report;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис, выполняющий аналитику по списку записей лога.
 * <p>
 * Формирует:
 * <ul>
 *   <li>Топ N IP-адресов по количеству запросов</li>
 *   <li>Статистику HTTP-кодов ответа</li>
 *   <li>Количество запросов с заданным User-Agent (если указан)</li>
 * </ul>
 * </p>
 */
public class LogAnalysisService {

    /**
     * Выполняет анализ логов и формирует отчёт.
     *
     * @param entries   список распарсенных записей лога (не должен быть {@code null})
     * @param topCount  количество IP-адресов в топе (должно быть положительным)
     * @param filterUa  точное значение User-Agent для подсчёта хитов,
     *                  может быть {@code null} — тогда счётчик будет 0
     * @return объект {@link Report}, содержащий результаты анализа
     */
    public Report analyze(List<LogEntry> entries, int topCount, String filterUa) {

        // Топ IP-адресов по количеству запросов
        List<Report.IpCount> topIps = entries.stream()
                .collect(Collectors.groupingBy(LogEntry::ip, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(topCount)
                .map(e -> new Report.IpCount(e.getKey(), e.getValue()))
                .toList();

        // Статистика по HTTP-кодам ответа
        Map<String, Long> statusCodes = entries.stream()
                .collect(Collectors.groupingBy(
                        entry -> String.valueOf(entry.statusCode()),
                        Collectors.counting()
                ));

        // Количество запросов с указанным User-Agent
        long uaHits = filterUa == null ? 0L :
                entries.stream()
                        .filter(e -> filterUa.equals(e.userAgent()))
                        .count();

        return new Report(topIps, statusCodes, uaHits);
    }
}