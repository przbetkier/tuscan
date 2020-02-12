package pro.tuscan.infrastructure;

class ErrorResponse {

    private final Long timestamp;
    private final String path;
    private final Integer status;
    private final String error;
    private final String message;

    ErrorResponse(Long timestamp, String path, Integer status, String error, String message) {
        this.timestamp = timestamp;
        this.path = path;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
