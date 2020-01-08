package mate.academy.internetshop;

import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class Main {

    @Inject
    private static UserService userService;

    @Inject
    private static ItemService itemService;

    @Inject
    private static BucketService bucketService;

    @Inject
    private static OrderService orderService;

    static {
        try {
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        User user1 = new User("name1");
        User user2 = new User("name2");
        userService.create(user1);
        userService.create(user2);
        System.out.println(Storage.users);
        userService.delete(user1);
        System.out.println(Storage.users);
        Bucket bucket = new Bucket();
        bucketService.create(bucket);
        Item item1 = new Item("Item1", 5.0);
        Item item2 = new Item("Item2", 10.0);
        Item item3 = new Item("Item3", 7.0);
        itemService.create(item1);
        itemService.create(item2);
        itemService.create(item3);
        System.out.println(Storage.items);
        bucketService.addItem(bucket, item1);
        bucketService.addItem(bucket, item2);
        bucketService.addItem(bucket, item3);
        System.out.println(Storage.buckets);
        orderService.completeOrder(bucketService.getAllItems(bucket),user2);
        System.out.println(Storage.orders);
    }
}
