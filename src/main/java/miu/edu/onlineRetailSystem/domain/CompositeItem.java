package miu.edu.onlineRetailSystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "compositeItems")
public class CompositeItem extends Item {
//    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
@OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "compositeItems_Items")
    private List<Item> subItems = new ArrayList<>();

    // Constructors, getters, and setters

    public void addSubItem(Item item) {
        subItems.add(item);
    }

    public void removeSubItem(Item item) {
        subItems.remove(item);
    }

    @Override
    public double getPrice() {
        return calculatePrice();
    }

    private double calculatePrice() {
        double sum = 0;
        for (Item item : subItems) {
            sum += item.getPrice();
        }
        return sum;
    }
}
