package com.factorit.producto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Producto {
    private String nombre;
    private BigDecimal precio;
    private Integer cantidad;
}
