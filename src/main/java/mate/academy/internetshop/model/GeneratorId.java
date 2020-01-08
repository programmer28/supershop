package mate.academy.internetshop.model;

public class GeneratorId {
    private static Long itemId = Long.valueOf(0);
    private static Long bucketId = Long.valueOf(0);
    private static Long userId = Long.valueOf(0);
    private static Long orderId = Long.valueOf(0);

    public static Long getNewItemId() {
        return ++itemId;
    }

    public static Long getNewBucketId() {
        return ++bucketId;
    }

    public static Long getNewUserId() {
        return ++userId;
    }

    public static Long getNewOrderId() {
        return ++orderId;
    }
}
