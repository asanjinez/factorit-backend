package com.factorit.compra;

import static org.assertj.core.api.Assertions.assertThat;

import com.factorit.compra.dto.CompraResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

@SpringBootTest
class CompraServiceConsultaTests {

    private static final String DNI = "30111222";
    private static final String OTRO_DNI = "40999888";

    @Autowired
    private ICompraService compraService;

    @Autowired
    private ICompraRepository compraRepository;

    @BeforeEach
    void setUp() {
        compraRepository.deleteAll();
    }

    @Test
    void findAllOrdenaPorFechaDescendente() {
        guardarCompra(DNI, "2026-05-01T10:00:00", "1000");
        guardarCompra(OTRO_DNI, "2026-05-03T10:00:00", "3000");
        guardarCompra(DNI, "2026-05-02T10:00:00", "2000");

        List<CompraResponse> compras = compraService.findAll(Sort.by(Sort.Direction.DESC, "fecha"));

        logCompras("todas por fecha desc", compras);
        assertTotales(compras, "3000", "2000", "1000");
    }

    @Test
    void findByDniFiltraComprasDelCliente() {
        guardarCompra(DNI, "2026-05-01T10:00:00", "1000");
        guardarCompra(OTRO_DNI, "2026-05-02T10:00:00", "2000");
        guardarCompra(DNI, "2026-05-03T10:00:00", "3000");

        List<CompraResponse> compras = compraService.findByDni(DNI, Sort.by(Sort.Direction.ASC, "fecha"));

        logCompras("por dni", compras);
        assertThat(compras).hasSize(2);
        assertThat(compras).extracting(CompraResponse::getDni).containsOnly(DNI);
        assertTotales(compras, "1000", "3000");
    }

    @Test
    void findByDniFromTraeComprasDesdeLaFechaIndicada() {
        guardarCompra(DNI, "2026-05-01T10:00:00", "1000");
        guardarCompra(DNI, "2026-05-10T10:00:00", "2000");
        guardarCompra(DNI, "2026-05-20T10:00:00", "3000");

        List<CompraResponse> compras = compraService.findByDniFrom(
                DNI,
                LocalDateTime.parse("2026-05-10T00:00:00"),
                Sort.by(Sort.Direction.ASC, "fecha"));

        logCompras("por dni desde fecha", compras);
        assertTotales(compras, "2000", "3000");
    }

    @Test
    void findByDniPeriodTraeComprasDentroDelPeriodo() {
        guardarCompra(DNI, "2026-05-01T10:00:00", "1000");
        guardarCompra(DNI, "2026-05-10T10:00:00", "2000");
        guardarCompra(DNI, "2026-05-20T10:00:00", "3000");

        List<CompraResponse> compras = compraService.findByDniPeriod(
                DNI,
                LocalDateTime.parse("2026-05-05T00:00:00"),
                LocalDateTime.parse("2026-05-15T23:59:59"),
                Sort.by(Sort.Direction.ASC, "fecha"));

        logCompras("por dni periodo", compras);
        assertThat(compras).singleElement()
                .satisfies(compra -> assertThat(compra.getTotal()).isEqualByComparingTo("2000"));
    }

    @Test
    void findByDniOrdenaPorMontoAscendente() {
        guardarCompra(DNI, "2026-05-01T10:00:00", "3000");
        guardarCompra(DNI, "2026-05-02T10:00:00", "1000");
        guardarCompra(DNI, "2026-05-03T10:00:00", "2000");

        List<CompraResponse> compras = compraService.findByDni(DNI, Sort.by(Sort.Direction.ASC, "total"));

        logCompras("por dni monto asc", compras);
        assertTotales(compras, "1000", "2000", "3000");
    }

    private void guardarCompra(String dni, String fecha, String total) {
        Compra compra = new Compra();
        compra.setClienteDni(dni);
        compra.setFecha(LocalDateTime.parse(fecha));
        compra.setSubtotal(new BigDecimal(total));
        compra.setDescuentoTotal(BigDecimal.ZERO);
        compra.setTotal(new BigDecimal(total));
        compraRepository.save(compra);
    }

    private void logCompras(String caso, List<CompraResponse> compras) {
        System.out.printf("COMPRAS | caso=%s | totales=%s%n",
                caso,
                compras.stream().map(CompraResponse::getTotal).toList());
    }

    private void assertTotales(List<CompraResponse> compras, String... totales) {
        assertThat(compras).hasSize(totales.length);
        for (int i = 0; i < totales.length; i++) {
            assertThat(compras.get(i).getTotal()).isEqualByComparingTo(totales[i]);
        }
    }
}
