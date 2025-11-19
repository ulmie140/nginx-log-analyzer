# Nginx Log Analyzer

[![CI](https://github.com/ulmie140/nginx-log-analyzer/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/ulmie140/nginx-log-analyzer/actions/workflows/ci.yml)

**Анализатор логов веб-сервера Nginx** — лабораторная работа №3  
Автор: **Бондаренко Илья**  
Дата: ноябрь 2025

## Что делает программа

Приложение читает файл логов Nginx в стандартном Combined Log Format и формирует JSON-отчёт:

- Топ-N самых активных IP-адресов  
- Статистика по HTTP-кодам ответа  
- Количество запросов с заданным User-Agent (например, от curl)

Результат выводится в консоль.

Технологии: **Java 17 + Gradle + JUnit 5**

---

## Полная инструкция по запуску (для тех, кто делает это впервые)

Для начала необходимо открыть командную строку для этого необходимо нажать комбинацию клавиш

- WIN + R

И в открывшемся окне написать 

- cmd

# 1. Создаём папку и клонируем проект

В открывшемся терминале выполните по очереди:

- mkdir nginx-project
- cd nginx-project
- git clone https://github.com/ulmie140/nginx-log-analyzer.git
- cd nginx-log-analyzer

# 2. Создаём папку для логов и открываем файл в Блокноте

Затем выполните следующие команды по очереди:

- mkdir data
- notepad data\access.log

После того, как открылся блокнот скопируйте и вставьте, что приведено ниже

```bash
192.168.1.10 - - [19/Nov/2025:12:15:22 +0300] "GET /index.html HTTP/1.1" 200 3521 "-" "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
192.168.1.10 - - [19/Nov/2025:12:15:25 +0300] "GET /style.css HTTP/1.1" 200 842 "-" "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
10.10.5.25 - - [19/Nov/2025:12:16:01 +0300] "GET /api/users HTTP/1.1" 404 127 "-" "curl/7.68.0"
192.168.1.10 - - [19/Nov/2025:12:16:10 +0300] "POST /login HTTP/1.1" 200 512 "-" "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
172.16.0.8 - - [19/Nov/2025:12:17:33 +0300] "GET /admin HTTP/1.1" 403 154 "-" "Python-urllib/3.9"
10.10.5.25 - - [19/Nov/2025:12:18:05 +0300] "POST /webhook HTTP/1.1" 200 0 "-" "curl/7.68.0"
192.168.1.10 - - [19/Nov/2025:12:18:44 +0300] "GET /favicon.ico HTTP/1.1" 404 150 "-" "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
203.0.113.5 - - [19/Nov/2025:12:20:01 +0300] "GET /robots.txt HTTP/1.1" 200 124 "-" "Googlebot/2.1"
10.10.5.25 - - [19/Nov/2025:12:21:15 +0300] "GET /health HTTP/1.1" 200 67 "-" "curl/7.68.0"
192.168.1.15 - - [19/Nov/2025:12:22:30 +0300] "GET /about.html HTTP/1.1" 301 178 "-" "Mozilla/5.0 (Linux; Android 10)"
10.10.5.25 - - [19/Nov/2025:12:23:44 +0300] "DELETE /temp/file.txt HTTP/1.1" 501 198 "-" "curl/7.68.0"
```
→ Нажмите Ctrl+S, закройте Блокнот.

# 3. Создаём конфигурационный файл

Также выполните следующие команды по очереди:

- mkdir src\main\resources 
- notepad src\main\resources\app.properties

После того, как открылся блокнот скопируйте и вставьте, что приведено ниже

```bash
log.file.path=data/access.log
report.top.ip.count=10
filter.user.agent=curl/7.68.0
output.target=STDOUT
```
→ Нажмите Ctrl+S, закройте Блокнот.

И выполните следующие две команды

# 4. Собираем проект
- gradlew build

# 5. Запускаем приложение
- gradlew run

После того как программа запустилась вы должны увидеть следующее в командной строке
{

  "top_ips" : [ {
  
    "ip" : "192.168.1.10",
	
    "count" : 4
	
  }, {
  
    "ip" : "10.10.5.25",
	
    "count" : 4
	
  }, {
  
    "ip" : "172.16.0.8",
	
    "count" : 1
	
  }, {
  
    "ip" : "203.0.113.5",
	
    "count" : 1
	
  }, {
  
    "ip" : "192.168.1.15",
	
    "count" : 1
	
  } ],
  
  "status_codes" : {
  
    "200" : 6,
	
    "301" : 1,
	
    "501" : 1,
	
    "403" : 1,
	
    "404" : 2
	
  },
  
  "user_agent_hits" : 4
  
}