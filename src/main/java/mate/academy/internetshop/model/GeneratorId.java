package mate.academy.internetshop.model;

public class GeneratorId {
    private static Long itemId = 0L;
    private static Long bucketId = 0L;
    private static Long userId = 0L;
    private static Long orderId = 0L;
    private static Long roleId = 0L;

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

    public static Long getNewRoleId() {
        return ++roleId;
    }
}
