# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build
mvn clean package

# Run locally
mvn spring-boot:run

# Run tests
mvn test

# Run a single test class
mvn test -Dtest=DoujuanApplicationTests

# Docker build and run
docker build -t doujuan:latest .
docker run -p 8080:8080 doujuan:latest
```

## Architecture

This is a **Spring Boot 3.4.4 / Java 21** REST API following a layered architecture:

```
controller/       → HTTP request handling, input validation
application/      → Use case orchestration (application services)
domain/           → Core business logic (domain services)
infrastructure/
  config/         → Spring beans, filters, exception handling
  storage/        → MyBatis Plus repositories + PO (persistence objects) models
  cache/          → Caching infrastructure
  utils/          → Shared utilities (ApiResult wrapper)
```

**Key conventions:**
- All API responses use `ApiResult<T>` — a generic wrapper with `status`, `traceId`, `message`, and `data` fields
- Each request gets a unique `traceId` (UUID) injected by `MdcFilter` and included in logs and responses
- Centralized exception handling in `GlobalExceptionHandler` — handles Sa-Token auth exceptions and general exceptions
- Persistence models live in `infrastructure/storage/po/` (suffix `PO` convention is not used — classes are plain names like `User`, `Accounts`, `AuthenticationPhone`)

## Key Dependencies

- **Sa-Token** (v1.42.0) — authentication & authorization framework; token config in `SaTokenConfig`, role/permission loading in `SaAuthorLoadConfig`
- **MyBatis Plus** (v3.5.12) — ORM; repositories extend `BaseMapper<T>`
- **MySQL** with HikariCP connection pool (configured in `application.yml`)
- **Lombok** — used throughout for boilerplate reduction

## Database

MySQL at `localhost:3306/doujuan` (dev defaults: root/root). Key tables map to:
- `User` — user profile (nickName, avatar, realName, gender, etc.)
- `Accounts` — account status and last login tracking
- `AuthenticationPhone` — phone-based login credentials with failed attempt tracking
- `AuthenticationPassword` — password-based credentials
- `ContactInfo` — user contact records (email, phone) with primary/disabled flags

## Logging

Logback configured in `logback-spring.xml`. Logs written to `/Users/mliuzs/Downloads/logs/doujuan-log.log` with 30-day rolling retention. The `traceId` MDC variable is included in every log line for request correlation.