package com.factorit.descuento.strategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.factorit.carrito.CarritoItem;
import com.factorit.descuento.DescuentoAplicado;
import com.factorit.descuento.NivelDescuento;
import com.factorit.descuento.ParametrosDescuento;
import com.factorit.descuento.TipoDescuento;
import org.springframework.stereotype.Component;

@Component
public class Promo4x3Regla implements ReglaDescuento {

    private static final int CANTIDAD_PROMO_4X3 = 4;

    @Override
    public List<DescuentoAplicado> aplicar(ParametrosDescuento parametros) {
        List<DescuentoAplicado> descuentos = new ArrayList<>();
        for (CarritoItem item : parametros.getCarrito().getItems()) {
            int cantidadPromos = item.getCantidad() / CANTIDAD_PROMO_4X3;

            if (cantidadPromos > 0) {
                descuentos.add(crearDescuento(item, cantidadPromos));
            }
        }

        return descuentos;
    }

    private DescuentoAplicado crearDescuento(CarritoItem item, int cantidadPromos) {
        BigDecimal monto = item.getPrecioUnitario().multiply(BigDecimal.valueOf(cantidadPromos));

        return new DescuentoAplicado(
                TipoDescuento.PROMO_4X3,
                NivelDescuento.ITEM,
                TipoDescuento.PROMO_4X3.getDescripcion(),
                monto,
                item.getProductoNombre());
    }
}
