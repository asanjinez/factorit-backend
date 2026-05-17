package com.factorit.descuento.strategy;

import com.factorit.carrito.Carrito;
import com.factorit.descuento.DescuentoAplicado;

import java.util.List;

public interface ReglaDescuento {

    List<DescuentoAplicado> aplicar(Carrito carrito);
}
