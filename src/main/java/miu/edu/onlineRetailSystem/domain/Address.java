package miu.edu.onlineRetailSystem.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.onlineRetailSystem.nonDomain.AddressType;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "addressID")
    private int id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private boolean isDefaultShippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerID")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;
}
