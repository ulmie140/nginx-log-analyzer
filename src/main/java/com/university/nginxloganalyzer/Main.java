package com.university.nginxloganalyzer;

import com.university.nginxloganalyzer.config.AppConfig;
import com.university.nginxloganalyzer.parser.NginxLogParser;
import com.university.nginxloganalyzer.service.LogAnalysisService;
import com.university.nginxloganalyzer.report.JsonReportWriter;

/**
 * Точка входа в приложение анализа логов Nginx.
 * <p>
 * Приложение читает настройки из {@code app.properties}, парсит указанный лог-файл,
 * выполняет требуемую аналитику и выводит отчёт в JSON-формате либо в файл,
 * либо на стандартный вывод.
 * </p>
 */
public class Main {

    /**
     * Главный метод приложения.
     *
     * @param args аргументы командной строки (в текущей версии не используются)
     */
    public static void main(String[] args) {
        try {
            var config = new AppConfig();
            var parser = new NginxLogParser();
            var entries = parser.parse(config.getLogFilePath());

            var service = new LogAnalysisService();
            var report = service.analyze(entries,
                    config.getTopIpCount(),
                    config.getFilterUserAgent());

            new JsonReportWriter().write(report, config.getOutputTarget());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}