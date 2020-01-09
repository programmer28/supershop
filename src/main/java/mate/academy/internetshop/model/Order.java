package mate.academy.internetshop.model;

import java.util.List;

public class Order {
    private Long id;
    private List<Item> items;
    private Long userId;

    public Order() {
        id = GeneratorId.getNewOrderId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" + "id="
                + id + ", items="
                + items + ", userId="
                + userId + '}';
    }
}
