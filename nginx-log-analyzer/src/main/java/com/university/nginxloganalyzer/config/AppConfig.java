package com.university.nginxloganalyzer.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс загрузки и хранения конфигурации приложения.
 * <p>
 * Параметры берутся из файла {@code app.properties}, находящегося в classpath.
 * Если какой-либо параметр отсутствует — используются значения по умолчанию.
 * </p>
 */
public class AppConfig {
    private final Properties properties = new Properties();

    /**
     * Загружает свойства из {@code app.properties}.
     *
     * @throws RuntimeException если файл не найден или не удалось его прочитать
     */
    public AppConfig() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            if (is == null) {
                throw new IllegalStateException("app.properties not found");
            }
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read app.properties", e);
        }
    }

    /** @return путь к файлу лога Nginx */
    public String getLogFilePath() {
        return properties.getProperty("log.file.path");
    }

    /**
     * @return количество IP-адресов, которые нужно включить в топ
     *         (по умолчанию 10)
     */
    public int getTopIpCount() {
        return Integer.parseInt(properties.getProperty("report.top.ip.count", "10"));
    }

    /**
     * @return User-Agent, по которому нужно посчитать количество запросов.
     *         Может быть {@code null}, если фильтрация не требуется.
     */
    public String getFilterUserAgent() {
        return properties.getProperty("filter.user.agent");
    }

    /**
     * @return куда выводить отчёт: {@code "FILE"} — в файл report.json,
     *         любое другое значение (по умолчанию {@code "STDOUT"}) — в консоль
     */
    public String getOutputTarget() {
        return properties.getProperty("output.target", "STDOUT");
    }
}