package com.factorit.carrito;

import com.factorit.FactoritEntity;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CarritoItem extends FactoritEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id", nullable = false)
    Carrito carrito;

    private String productoNombre;
    private BigDecimal precioUnitario;
    private Integer cantidad;
}
