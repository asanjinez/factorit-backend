package com.factorit.descuento;

import java.math.BigDecimal;

public record DescuentoResponse(
        String tipo,
        String nivel,
        String descripcion,
        BigDecimal monto,
        String productoNombre
) {
}
