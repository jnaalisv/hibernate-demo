package com.example.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ProductId implements Serializable {

    private String id;

    private ProductId() {/* for hibernate */}

    public ProductId(UUID uuid) {
        this.id = uuid.toString();
    }

    public static ProductId generateNew() {
        return new ProductId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ProductId) {
            return Objects.equals(id, id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
