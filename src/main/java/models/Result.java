package models;

public record Result(boolean success, String message) {
    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public boolean isSuccessFull() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
