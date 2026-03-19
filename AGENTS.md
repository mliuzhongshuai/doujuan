# AGENTS.md

This file provides guidance for agentic coding agents operating in this repository.

## Build & Test Commands

```bash
# Build project
mvn clean package

# Run locally
mvn spring-boot:run

# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=DoujuanApplicationTests

# Run a specific test method
mvn test -Dtest=DoujuanApplicationTests#testMethodName

# Docker build and run
docker build -t doujuan:latest .
docker run -p 8080:8080 doujuan:latest
```

## Architecture Overview

This is a **Spring Boot 3.4.4 / Java 21** REST API following a layered architecture:

```
src/main/java/com/lingjing/doujuan/
├── controller/           # HTTP request handling, input validation, DTOs
├── application/          # Use case orchestration (application services)
├── domain/               # Core business logic (domain services)
└── infrastructure/
    ├── config/           # Spring beans, filters, exception handling
    ├── storage/          # MyBatis Plus repositories + PO models
    ├── cache/            # Caching infrastructure
    ├── utils/            # Shared utilities (ApiResult, etc.)
    ├── constants/        # Error messages, constants
    └── enums/            # ResultCode, Gender, AccountStatus, etc.
```

## Dependencies

- **Spring Boot** 3.4.4 with Java 21
- **Sa-Token** 1.42.0 - Authentication & authorization
- **MyBatis Plus** 3.5.12 - ORM framework
- **Spring Data Redis** - Caching
- **Spring Validation** - Request validation
- **Lombok** - Boilerplate reduction

## Code Style Guidelines

### General Conventions

- **Language**: Java 21
- **Framework**: Spring Boot 3.x
- **Build Tool**: Maven

### Naming Conventions

- **Classes**: PascalCase (e.g., `UserController`, `AuthenticationApplication`)
- **Methods & Variables**: camelCase (e.g., `userRepository`, `loginPassword`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `ACCOUNT_STATUS_ACTIVE`)
- **Database Tables/Columns**: snake_case (e.g., `user_id`, `last_login_time`)
- **PO Classes**: Plain names WITHOUT "PO" suffix (e.g., `User`, not `UserPO`)

### Project Structure

- **Controllers**: Handle HTTP requests, perform input validation, return `ApiResult<T>`
- **Application Services**: Orchestrate use cases, contain business logic
- **Domain Services**: Core business logic
- **Repositories**: Extend `BaseMapper<T>` from MyBatis Plus
- **DTOs**: Request/response objects in `controller/dto/` package

### Response Format

All API responses use `ApiResult<T>` wrapper:

```java
ApiResult<T> {
    int code;      // 200 = success, 400 = bad request, 401 = unauthorized, 403 = forbidden, 500 = error
    String traceId; // UUID for request correlation
    String message; // Human-readable message
    T data;         // Response data
}
```

**Usage examples:**
```java
// Success
return ApiResult.success(data);
return ApiResult.success(data, "操作成功");

// Failure
return ApiResult.failed(MDC.get("traceId"), ErrorMessage.USER_NOT_FOUND);
return ApiResult.failed(ResultCode.BAD_REQUEST.getCode(), traceId, message);
```

### Request Validation

Use Jakarta Validation annotations on DTOs:

```java
@Data
public class UserModifyRequest {
    @Size(min = 1, max = 50, message = "昵称长度应为1-50个字符")
    private String nickName;

    @Pattern(regexp = "^https?://.*", message = "头像必须是有效的URL")
    private String avatar;

    @Pattern(regexp = "^(男|女|其他)$", message = "性别必须是男、女或其他")
    private String gender;
}
```

In controllers, use `@Valid` annotation:
```java
@PostMapping("/user/modify")
public ApiResult<String> modify(@Valid @RequestBody UserModifyRequest request) { ... }
```

### Dependency Injection

Use **constructor injection** (recommended over field injection):

```java
@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

### Exception Handling

- Use `GlobalExceptionHandler` with `@ControllerAdvice` for centralized exception handling
- Throw `IllegalArgumentException` with error messages from `ErrorMessage` constants
- Validation errors are handled automatically via `MethodArgumentNotValidException`
- Sa-Token exceptions (`NotLoginException`, `NotPermissionException`) are handled automatically

```java
// Throwing errors in application services
if (user == null) {
    throw new IllegalArgumentException(ErrorMessage.USER_NOT_FOUND);
}
```

### Logging

- Use Lombok `@Slf4j` for logging
- Include `traceId` from MDC in error logs
- Log levels: `log.error()` for errors, `log.warn()` for warnings, `log.info()` for info

```java
@Slf4j
public class SomeService {
    public void someMethod() {
        log.error(ErrorMessage.USER_NOT_FOUND + ": {}", userId);
    }
}
```

### Import Order

Organize imports in the following order:
1. Java/Jakarta external packages (`java.*`, `jakarta.*`)
2. Spring framework (`org.springframework.*`)
3. Third-party libraries (`cn.dev33.*`, `com.baomidou.*`, etc.)
4. Internal project packages (`com.lingjing.doujuan.*`)

### Database Access

- Repositories extend `BaseMapper<T>` from MyBatis Plus
- Use `LambdaQueryWrapper` for type-safe queries:
```java
AuthenticationPhone authPhone = authenticationPhoneRepository.selectOne(
    new LambdaQueryWrapper<AuthenticationPhone>()
        .eq(AuthenticationPhone::getPhoneNumber, phone));
```

- Use standard insert/select/update/delete methods from MyBatis Plus:
```java
userRepository.insert(user);
userRepository.selectById(id);
userRepository.updateById(user);
userRepository.deleteById(id);
```

### Enums

Define enums with fields and getters:

```java
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未登录"),
    FORBIDDEN(403, "无权限"),
    SERVER_ERROR(500, "服务器错误");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }
}
```

### Authentication

- Use Sa-Token (`StpUtil`) for authentication
- Get current user ID: `StpUtil.getLoginIdAsInt()`
- Login: `StpUtil.login(userId)`
- Logout: `StpUtil.logout()`

## Database

- **MySQL** at `localhost:3306/doujuan`
- **Dev credentials**: root/root
- Key tables: `user`, `accounts`, `authentication_phone`, `authentication_password`, `contact_info`

## Logging

- Logs written to `/Users/mliuzs/Downloads/logs/doujuan-log.log`
- 30-day rolling retention
- Every log line includes `traceId` MDC variable for request correlation

## Testing

- Tests located in `src/test/java/`
- Use Spring Boot Test framework
- Test class naming: `*Tests.java` or `*Test.java`
- Run specific test: `mvn test -Dtest=ClassName#methodName`
