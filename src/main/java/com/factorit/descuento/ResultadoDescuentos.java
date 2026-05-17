package com.factorit.descuento;

import java.math.BigDecimal;
import java.util.List;

public record ResultadoDescuentos(
        BigDecimal subtotal,
        BigDecimal descuentoTotal,
        BigDecimal total,
        List<DescuentoAplicado> descuentos
) {
}
