package miu.edu.onlineRetailSystem.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
@Inheritance(strategy = InheritanceType.JOINED)
public  class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemID")
    private int id;
    private String name;
    private String description;
    private double price;
    private byte[] image;
    private String barcodeNumber;
    private int quantityInStock;


//    @JoinTable(name = "ItemReview",
//    joinColumns = @JoinColumn(name = "itemID"),
//    inverseJoinColumns = @JoinColumn(name = "reviewID"))
    @OneToMany(cascade = CascadeType.DETACH)
    private List<Review> reviews = new ArrayList<>();
}
