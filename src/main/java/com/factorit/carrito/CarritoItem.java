package com.factorit.carrito;

import com.factorit.FactoritEntity;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CarritoItem extends FactoritEntity {
    private String productoNombre;
    private BigDecimal precioUnitario;
    private Integer cantidad;
}
