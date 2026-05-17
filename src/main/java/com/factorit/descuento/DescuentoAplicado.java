package com.factorit.descuento;

import java.math.BigDecimal;

public record DescuentoAplicado(
        TipoDescuento tipo,
        NivelDescuento nivel,
        String descripcion,
        BigDecimal monto,
        String productoNombre
) {
}
