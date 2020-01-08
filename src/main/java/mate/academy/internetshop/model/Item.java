package mate.academy.internetshop.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private Long id;
    private String name;
    private Double price;

    public Item(String name, Double price) {
        id = GeneratorId.getNewItemId();
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(getId(), item.getId())
                && Objects.equals(getName(), item.getName())
                && Objects.equals(getPrice(), item.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice());
    }

    @Override
    public String toString() {
        return "Item{" + "id="
                + id + ", name='"
                + name + '\'' + ", price="
                + price + '}';
    }
}
