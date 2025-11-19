package com.university.nginxloganalyzer.model;

/**
 * Неизменяемая запись, представляющая одну строку лога Nginx.
 *
 * @param ip         IP-адрес клиента
 * @param timestamp  дата и время запроса в формате CLF
 * @param method     HTTP-метод (GET, POST и т.д.)
 * @param url        запрошенный ресурс
 * @param protocol   версия протокола (HTTP/1.1, HTTP/2 и т.п.)
 * @param statusCode код ответа сервера
 * @param bytesSent  количество отправленных байт (0, если в логе "-")
 * @param userAgent  значение заголовка User-Agent
 */
public record LogEntry(
        String ip,
        String timestamp,
        String method,
        String url,
        String protocol,
        int statusCode,
        long bytesSent,
        String userAgent
) {}