package com.factorit.descuento.strategy;

import com.factorit.compra.ICompraRepository;
import com.factorit.descuento.DescuentoAplicado;
import com.factorit.descuento.NivelDescuento;
import com.factorit.descuento.ParametrosDescuento;
import com.factorit.descuento.TipoDescuento;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CompraVipRegla implements IReglaDescuento {

    private static final BigDecimal MONTO_MINIMO_MENSUAL_VIP = BigDecimal.valueOf(5000);
    private static final BigDecimal MONTO_MINIMO_COMPRA_VIP = BigDecimal.valueOf(2000);
    private static final BigDecimal DESCUENTO_VIP = BigDecimal.valueOf(500);

    private final ICompraRepository compraRepository;

    public CompraVipRegla(ICompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @Override
    public List<DescuentoAplicado> aplicar(ParametrosDescuento parametros) {
        if (parametros.getDni() == null || parametros.getDni().isBlank() || parametros.getFecha() == null) {
            return List.of();
        }
        if (parametros.getCarrito().calcularSubtotal().compareTo(MONTO_MINIMO_COMPRA_VIP) <= 0) {
            return List.of();
        }
        if (calcularComprasDelMes(parametros).compareTo(MONTO_MINIMO_MENSUAL_VIP) <= 0) {
            return List.of();
        }

        return List.of(new DescuentoAplicado(
                TipoDescuento.COMPRA_VIP,
                NivelDescuento.TOTAL,
                TipoDescuento.COMPRA_VIP.getDescripcion(),
                DESCUENTO_VIP,
                null));
    }

    private BigDecimal calcularComprasDelMes(ParametrosDescuento parametros) {
        YearMonth mes = YearMonth.from(parametros.getFecha());
        LocalDateTime desde = mes.atDay(1).atStartOfDay();
        LocalDateTime hasta = mes.atEndOfMonth().atTime(LocalTime.MAX);

        return compraRepository.sumarTotalByClienteDniAndFechaBetween(parametros.getDni(), desde, hasta);
    }
}
