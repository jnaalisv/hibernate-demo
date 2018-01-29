package com.example.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ProductId implements Serializable {

    @GeneratedValue
    @Column( columnDefinition = "uuid", updatable = false, name = "id" )
    private UUID value;

    private ProductId() {/* for hibernate */}

    private ProductId(UUID value) {
        this.value = value;
    }

    public static ProductId generateNew() {
        return new ProductId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ProductId) {
            return Objects.equals(((ProductId)other).value, value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
