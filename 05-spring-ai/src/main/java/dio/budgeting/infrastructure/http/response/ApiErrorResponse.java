package dio.budgeting.infrastructure.http.response;

import java.util.Map;

public record ApiErrorResponse(String message, Map<String, String> errors) {
}
