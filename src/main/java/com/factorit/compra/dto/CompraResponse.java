package com.factorit.compra.dto;

import com.factorit.descuento.DescuentoResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraResponse {

    private Long id;
    private String dni;
    private LocalDateTime fecha;
    private BigDecimal subtotal;
    private BigDecimal descuentoTotal;
    private BigDecimal total;
    private List<DescuentoResponse> descuentos;
    private List<CompraItemResponse> items;
}
