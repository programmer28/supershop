package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private Long id;
    private List<Item> items;

    public Bucket() {
        id = GeneratorId.getNewBucketId();
        items = new ArrayList<>();
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

    @Override
    public String toString() {
        return "Bucket{" + "id="
                + id + ", items="
                + items + '}';
    }
}
