package com.university.nginxloganalyzer.parser;

import com.university.nginxloganalyzer.model.LogEntry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Парсер строк лога Nginx в формате Combined Log Format.
 * <p>
 * Поддерживает стандартный формат:
 * <pre>
 *  ip - - [timestamp] "METHOD URL PROTO" status bytes "-" "user-agent"
 * </pre>
 */
public class NginxLogParser {

    /** Регулярное выражение для разбора одной строки лога */
    private static final Pattern PATTERN = Pattern.compile(
            "^(\\S+) \\S+ \\S+ \\[([^]]+)] \"([A-Z]+) ([^\"]+) ([^\"]+)\" (\\d+) (\\d+|-) \"([^\"]*)\" \"([^\"]*)\"$"
    );

    /**
     * Читает файл лога и преобразует каждую валидную строку в объект {@link LogEntry}.
     *
     * @param path путь к файлу лога
     * @return список распарсенных записей
     * @throws IOException если произошла ошибка ввода-вывода
     */
    public List<LogEntry> parse(String path) throws IOException {
        List<LogEntry> entries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                LogEntry entry = parseLine(line);
                if (entry != null) {
                    entries.add(entry);
                }
            }
        }
        return entries;
    }

    /**
     * Парсит одну строку лога.
     *
     * @param line строка лога
     * @return объект {@link LogEntry} или {@code null}, если строка не соответствует формату
     */
    private LogEntry parseLine(String line) {
        Matcher m = PATTERN.matcher(line);
        if (!m.matches()) {
            return null;
        }

        String ip = m.group(1);
        String ts = m.group(2);
        String method = m.group(3);
        String url = m.group(4);
        String proto = m.group(5);
        int status = Integer.parseInt(m.group(6));
        long bytes = "-".equals(m.group(7)) ? 0L : Long.parseLong(m.group(7));
        String ua = m.group(9);

        return new LogEntry(ip, ts, method, url, proto, status, bytes, ua);
    }
}
