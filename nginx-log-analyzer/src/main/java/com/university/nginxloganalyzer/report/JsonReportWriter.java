package com.university.nginxloganalyzer.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

/**
 * Класс записи отчёта в JSON-формат.
 * <p>
 * В зависимости от настройки {@code output.target} запись происходит либо в файл
 * {@code report.json}, либо в стандартный вывод.
 * </p>
 */
public class JsonReportWriter {

    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Записывает отчёт в JSON.
     *
     * @param report объект отчёта
     * @param target значение свойства {@code output.target}:
     *               {@code "FILE"} — в файл, иначе — в консоль
     * @throws IOException при ошибке записи в файл
     */
    public void write(Report report, String target) throws IOException {
        if ("FILE".equalsIgnoreCase(target)) {
            mapper.writeValue(new File("report.json"), report);
            System.out.println("Report saved to report.json");
        } else {
            String json = mapper.writeValueAsString(report);
            System.out.println(json);
        }
    }
}