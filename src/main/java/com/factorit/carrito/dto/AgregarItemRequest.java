package com.factorit.carrito.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgregarItemRequest {
    private String nombre;
    private BigDecimal precio;
    private Integer cantidad;
}
