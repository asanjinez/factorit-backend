package com.factorit.descuento.strategy;

import com.factorit.carrito.Carrito;
import java.math.BigDecimal;
import java.util.List;

import com.factorit.descuento.DescuentoAplicado;
import com.factorit.descuento.NivelDescuento;
import com.factorit.descuento.ParametrosDescuento;
import com.factorit.descuento.TipoDescuento;
import org.springframework.stereotype.Component;

@Component
public class DescuentoCantidadRegla implements ReglaDescuento {

    private static final int CANTIDAD_MINIMA_DESCUENTO = 3;
    private static final BigDecimal DESCUENTO_CARRITO_COMUN = BigDecimal.valueOf(100);
    private static final BigDecimal DESCUENTO_CARRITO_ESPECIAL = BigDecimal.valueOf(150);

    @Override
    public List<DescuentoAplicado> aplicar(ParametrosDescuento parametros) {
        Carrito carrito = parametros.getCarrito();
        if (carrito.calcularCantidadProductos() <= CANTIDAD_MINIMA_DESCUENTO) {
            return List.of();
        }

        BigDecimal monto = carrito.isSpecial() ? DESCUENTO_CARRITO_ESPECIAL : DESCUENTO_CARRITO_COMUN;
        return List.of(new DescuentoAplicado(
                TipoDescuento.DESCUENTO_CANTIDAD,
                NivelDescuento.TOTAL,
                TipoDescuento.DESCUENTO_CANTIDAD.getDescripcion(),
                monto,
                null));
    }
}
