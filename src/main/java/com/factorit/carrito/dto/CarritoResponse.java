package com.factorit.carrito.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoResponse {
    private Long id;
    private Integer totalProductos;
    private List<CarritoItemResponse> items;
}
