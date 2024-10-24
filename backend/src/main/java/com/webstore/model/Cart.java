package com.webstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.ElementCollection;
import javax.persistence.CollectionTable;
import javax.persistence.Table;
import java.util.List;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "cart_items",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @ElementCollection
    @CollectionTable(name = "cart_item_quantities", joinColumns = @JoinColumn(name = "cart_id"))
    @Column(name = "quantity")
    private List<Integer> quantities;

    public void addProduct(Product product, int quantity) {
        this.products.add(product);
        this.quantities.add(quantity);
    }

    public void removeProduct(Product product) {
        int index = this.products.indexOf(product);
        if (index >= 0) {
            this.products.remove(index);
            this.quantities.remove(index);
        }
    }
}
