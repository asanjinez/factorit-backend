package com.factorit.descuento;

import com.factorit.carrito.Carrito;
import java.math.BigDecimal;
import java.util.List;

import com.factorit.descuento.strategy.ReglaDescuento;
import org.springframework.stereotype.Service;

@Service
public class CalculadorDescuentosService {

    private final List<ReglaDescuento> reglas;

    public CalculadorDescuentosService(List<ReglaDescuento> reglas) {
        this.reglas = reglas;
    }

    public ResultadoDescuentos calcular(Carrito carrito) {
        BigDecimal subtotal = carrito.calcularSubtotal();
        List<DescuentoAplicado> descuentos = reglas.stream()
                .flatMap(regla -> regla.aplicar(carrito).stream())
                .toList();

        BigDecimal descuentoTotal = descuentos.stream()
                .map(DescuentoAplicado::monto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        BigDecimal total = subtotal.subtract(descuentoTotal);

        return new ResultadoDescuentos(subtotal, descuentoTotal, total, descuentos);
    }
}
