package com.factorit.carrito.dto;

import com.factorit.descuento.DescuentoResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoResponse {
    private Long id;
    private Integer cantidadProductos;
    private BigDecimal subtotal;
    private BigDecimal descuentoTotal;
    private BigDecimal total;
    private List<DescuentoResponse> descuentos;
    private List<CarritoItemResponse> items;
}
