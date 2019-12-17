package top.cyixlq.crouter.chain;

public enum RouteStatus {
    PROCESSING,
    SUCCEED,
    INTERCEPTED,
    NOT_FOUND,
    FAILED;

    public boolean isSuccessful() {
        return this == SUCCEED;
    }
}
