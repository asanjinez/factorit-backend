package com.factorit.carrito.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItemResponse {
    private Long id;
    private String nombre;
    private BigDecimal precioUnitario;
    private Integer cantidad;
}
