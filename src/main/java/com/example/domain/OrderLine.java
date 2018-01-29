package com.example.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_line")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Embedded
    @AttributeOverride(name="value", column = @Column(name="product_id"))
    private ProductId productId;

    private BigDecimal amount;

    @ManyToOne
    private Order order;

    private OrderLine() {}

    public OrderLine(ProductId productId, BigDecimal amount) {
        this.productId = productId;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;

        if (other instanceof OrderLine) {

            OrderLine otherOrder = (OrderLine)other;

            return otherOrder.id == id
                    && Objects.equals(otherOrder.productId, productId)
                    && Objects.equals(otherOrder.amount, amount);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = Objects.hashCode(id);
        hashCode += 31 * Objects.hashCode(productId);
        hashCode += 31 * Objects.hashCode(amount);
        return hashCode;
    }

    @Override
    public String toString() {
        return String.format("OrderLine %s %s, amount=%.2f", order, productId, amount);
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
