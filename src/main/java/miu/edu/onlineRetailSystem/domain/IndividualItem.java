package miu.edu.onlineRetailSystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "individualItems")
@Data
public class IndividualItem extends Item {
}
