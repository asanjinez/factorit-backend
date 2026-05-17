package com.factorit.descuento.strategy;

import com.factorit.descuento.DescuentoAplicado;
import com.factorit.descuento.ParametrosDescuento;

import java.util.List;

public interface ReglaDescuento {

    List<DescuentoAplicado> aplicar(ParametrosDescuento parametros);
}
