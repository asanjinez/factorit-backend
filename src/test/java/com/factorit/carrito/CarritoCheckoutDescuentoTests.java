package com.factorit.carrito;

import static org.assertj.core.api.Assertions.assertThat;

import com.factorit.carrito.dto.AgregarItemRequest;
import com.factorit.carrito.dto.CarritoResponse;
import com.factorit.carrito.dto.CheckoutRequest;
import com.factorit.carrito.dto.CrearCarritoRequest;
import com.factorit.compra.Compra;
import com.factorit.compra.ICompraRepository;
import com.factorit.compra.dto.CompraResponse;
import com.factorit.descuento.DescuentoResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CarritoCheckoutDescuentoTests {

    private static final String DNI = "30111222";

    @Autowired
    private ICarritoService carritoService;

    @Autowired
    private ICarritoRepository carritoRepository;

    @Autowired
    private ICompraRepository compraRepository;

    @BeforeEach
    void setUp() {
        carritoRepository.deleteAll();
        compraRepository.deleteAll();
    }

    @Test
    void checkoutComunConMasDeTresProductosDescuentaCienPesos() {
        Long carritoId = crearCarrito(false);
        agregarItem(carritoId, "Teclado", "1000", 2);
        agregarItem(carritoId, "Mouse", "1000", 2);

        CompraResponse compra = carritoService.checkout(carritoId, new CheckoutRequest(DNI));

        logTotal("comun +4 productos", "3900", compra);
        assertThat(compra.getSubtotal()).isEqualByComparingTo("4000");
        assertThat(compra.getDescuentoTotal()).isEqualByComparingTo("100");
        assertThat(compra.getTotal()).isEqualByComparingTo("3900");
        assertThat(compra.getDescuentos())
                .extracting(DescuentoResponse::tipo)
                .containsExactly("DESCUENTO_CANTIDAD");
    }

    @Test
    void checkoutEspecialConMasDeTresProductosDescuentaCientoCincuentaPesos() {
        Long carritoId = crearCarrito(true);
        agregarItem(carritoId, "Teclado", "1000", 2);
        agregarItem(carritoId, "Mouse", "1000", 2);

        CompraResponse compra = carritoService.checkout(carritoId, new CheckoutRequest(DNI));

        logTotal("especial +4 productos", "3850", compra);
        assertThat(compra.getSubtotal()).isEqualByComparingTo("4000");
        assertThat(compra.getDescuentoTotal()).isEqualByComparingTo("150");
        assertThat(compra.getTotal()).isEqualByComparingTo("3850");
        assertThat(compra.getDescuentos())
                .extracting(DescuentoResponse::tipo)
                .containsExactly("DESCUENTO_CANTIDAD");
    }

    @Test
    void checkoutConCuatroProductosIgualesAplicaPromoCuatroPorTresYDescuentoPorCantidad() {
        Long carritoId = crearCarrito(false);
        agregarItem(carritoId, "Mouse", "1200", 4);

        CompraResponse compra = carritoService.checkout(carritoId, new CheckoutRequest(DNI));

        logTotal("4x3 + descuento cantidad", "3500", compra);
        assertThat(compra.getSubtotal()).isEqualByComparingTo("4800");
        assertThat(compra.getDescuentoTotal()).isEqualByComparingTo("1300");
        assertThat(compra.getTotal()).isEqualByComparingTo("3500");
        assertThat(compra.getDescuentos())
                .extracting(DescuentoResponse::tipo)
                .containsExactlyInAnyOrder("PROMO_4X3", "DESCUENTO_CANTIDAD");
    }

    @Test
    void checkoutConOchoProductosIgualesAplicaDosPromosCuatroPorTres() {
        Long carritoId = crearCarrito(false);
        agregarItem(carritoId, "Mouse", "1200", 8);

        CompraResponse compra = carritoService.checkout(carritoId, new CheckoutRequest(DNI));

        logTotal("8 productos iguales", "7100", compra);
        assertThat(compra.getSubtotal()).isEqualByComparingTo("9600");
        assertThat(compra.getDescuentoTotal()).isEqualByComparingTo("2500");
        assertThat(compra.getTotal()).isEqualByComparingTo("7100");
        assertThat(compra.getDescuentos())
                .extracting(DescuentoResponse::tipo)
                .containsExactlyInAnyOrder("PROMO_4X3", "DESCUENTO_CANTIDAD");
        assertThat(compra.getDescuentos())
                .filteredOn(descuento -> "PROMO_4X3".equals(descuento.tipo()))
                .singleElement()
                .satisfies(descuento -> assertThat(descuento.monto()).isEqualByComparingTo("2400"));
    }

    @Test
    void carritoMuestraDescuentosEnTiempoRealAlAgregarItems() {
        Long carritoId = crearCarrito(false);

        CarritoResponse carrito = carritoService.addItem(
                carritoId,
                new AgregarItemRequest("Mouse", new BigDecimal("1200"), 4));

        logCarrito("carrito en tiempo real 4x3", carrito);
        assertThat(carrito.getCantidadProductos()).isEqualTo(4);
        assertThat(carrito.getSubtotal()).isEqualByComparingTo("4800");
        assertThat(carrito.getDescuentoTotal()).isEqualByComparingTo("1300");
        assertThat(carrito.getTotal()).isEqualByComparingTo("3500");
        assertThat(carrito.getDescuentos())
                .extracting(DescuentoResponse::tipo)
                .containsExactlyInAnyOrder("PROMO_4X3", "DESCUENTO_CANTIDAD");
        assertThat(carrito.getItems()).singleElement()
                .extracting("id")
                .isNotNull();
    }

    @Test
    void carritoRecalculaDescuentosCuandoSeEliminaUnItem() {
        Long carritoId = crearCarrito(false);
        CarritoResponse carritoConDescuento = carritoService.addItem(
                carritoId,
                new AgregarItemRequest("Mouse", new BigDecimal("1200"), 4));
        Long itemId = carritoConDescuento.getItems().getFirst().getId();

        carritoService.deleteItem(carritoId, itemId);
        CarritoResponse carrito = carritoService.findById(carritoId);

        logCarrito("carrito luego de eliminar item", carrito);
        assertThat(carrito.getCantidadProductos()).isZero();
        assertThat(carrito.getSubtotal()).isEqualByComparingTo("0");
        assertThat(carrito.getDescuentoTotal()).isEqualByComparingTo("0");
        assertThat(carrito.getTotal()).isEqualByComparingTo("0");
        assertThat(carrito.getDescuentos()).isEmpty();
        assertThat(carrito.getItems()).isEmpty();
    }

    @Test
    void checkoutVipDescuentaQuinientosCuandoLaCompraSuperaDosMilPesos() {
        guardarCompraPrevia(DNI, "6000");
        Long carritoId = crearCarrito(false);
        agregarItem(carritoId, "Monitor", "2500", 1);

        CompraResponse compra = carritoService.checkout(carritoId, new CheckoutRequest(DNI));

        logTotal("vip con compra mayor a 2000", "2000", compra);
        assertThat(compra.getSubtotal()).isEqualByComparingTo("2500");
        assertThat(compra.getDescuentoTotal()).isEqualByComparingTo("500");
        assertThat(compra.getTotal()).isEqualByComparingTo("2000");
        assertThat(compra.getDescuentos())
                .extracting(DescuentoResponse::tipo)
                .containsExactly("COMPRA_VIP");
    }

    @Test
    void checkoutVipNoDescuentaQuinientosCuandoLaCompraNoSuperaDosMilPesos() {
        guardarCompraPrevia(DNI, "6000");
        Long carritoId = crearCarrito(false);
        agregarItem(carritoId, "Auriculares", "2000", 1);

        CompraResponse compra = carritoService.checkout(carritoId, new CheckoutRequest(DNI));

        logTotal("vip con compra igual a 2000", "2000", compra);
        assertThat(compra.getSubtotal()).isEqualByComparingTo("2000");
        assertThat(compra.getDescuentoTotal()).isEqualByComparingTo("0");
        assertThat(compra.getTotal()).isEqualByComparingTo("2000");
        assertThat(compra.getDescuentos()).isEmpty();
    }

    @Test
    void checkoutConTodosLosDescuentosAcumulaLasPromocionesAplicables() {
        guardarCompraPrevia(DNI, "6000");
        Long carritoId = crearCarrito(true);
        agregarItem(carritoId, "Mouse", "1200", 4);
        agregarItem(carritoId, "Teclado", "1000", 1);

        CompraResponse compra = carritoService.checkout(carritoId, new CheckoutRequest(DNI));

        logTotal("todos los descuentos", "3950", compra);
        assertThat(compra.getSubtotal()).isEqualByComparingTo("5800");
        assertThat(compra.getDescuentoTotal()).isEqualByComparingTo("1850");
        assertThat(compra.getTotal()).isEqualByComparingTo("3950");
        assertThat(compra.getDescuentos())
                .extracting(DescuentoResponse::tipo)
                .containsExactlyInAnyOrder("PROMO_4X3", "DESCUENTO_CANTIDAD", "COMPRA_VIP");
    }

    @Test
    void checkoutVipNoAplicaCuandoLasComprasDelMesNoSuperanCincoMilPesos() {
        guardarCompraPrevia(DNI, "5000");
        Long carritoId = crearCarrito(false);
        agregarItem(carritoId, "Monitor", "2500", 1);

        CompraResponse compra = carritoService.checkout(carritoId, new CheckoutRequest(DNI));

        logTotal("vip con compras previas igual a 5000", "2500", compra);
        assertThat(compra.getSubtotal()).isEqualByComparingTo("2500");
        assertThat(compra.getDescuentoTotal()).isEqualByComparingTo("0");
        assertThat(compra.getTotal()).isEqualByComparingTo("2500");
        assertThat(compra.getDescuentos()).isEmpty();
    }

    @Test
    void checkoutVipNoCuentaComprasDeOtroMes() {
        guardarCompraPrevia(DNI, "6000", YearMonth.now().minusMonths(1).atDay(1).atStartOfDay());
        Long carritoId = crearCarrito(false);
        agregarItem(carritoId, "Monitor", "2500", 1);

        CompraResponse compra = carritoService.checkout(carritoId, new CheckoutRequest(DNI));

        logTotal("vip con compras previas de otro mes", "2500", compra);
        assertThat(compra.getSubtotal()).isEqualByComparingTo("2500");
        assertThat(compra.getDescuentoTotal()).isEqualByComparingTo("0");
        assertThat(compra.getTotal()).isEqualByComparingTo("2500");
        assertThat(compra.getDescuentos()).isEmpty();
    }

    private Long crearCarrito(boolean special) {
        CarritoResponse carrito = carritoService.create(new CrearCarritoRequest(special));
        return carrito.getId();
    }

    private void agregarItem(Long carritoId, String nombre, String precio, int cantidad) {
        carritoService.addItem(carritoId, new AgregarItemRequest(nombre, new BigDecimal(precio), cantidad));
    }

    private void guardarCompraPrevia(String dni, String total) {
        guardarCompraPrevia(dni, total, YearMonth.now().atDay(1).atStartOfDay());
    }

    private void guardarCompraPrevia(String dni, String total, LocalDateTime fecha) {
        Compra compra = new Compra();
        compra.setClienteDni(dni);
        compra.setFecha(fecha);
        compra.setTotal(new BigDecimal(total));
        compraRepository.save(compra);
    }

    private void logTotal(String caso, String esperado, CompraResponse compra) {
        System.out.printf(
                "CHECKOUT | caso=%s | subtotal=%s | descuento=%s | total=%s | esperado=%s | descuentos=%s%n",
                caso,
                compra.getSubtotal(),
                compra.getDescuentoTotal(),
                compra.getTotal(),
                esperado,
                formatearDescuentos(compra.getDescuentos()));
    }

    private void logCarrito(String caso, CarritoResponse carrito) {
        System.out.printf(
                "CARRITO | caso=%s | productos=%s | subtotal=%s | descuento=%s | total=%s | descuentos=%s%n",
                caso,
                carrito.getCantidadProductos(),
                carrito.getSubtotal(),
                carrito.getDescuentoTotal(),
                carrito.getTotal(),
                formatearDescuentos(carrito.getDescuentos()));
    }

    private String formatearDescuentos(Iterable<DescuentoResponse> descuentos) {
        StringBuilder detalle = new StringBuilder("[");
        for (DescuentoResponse descuento : descuentos) {
            if (detalle.length() > 1) {
                detalle.append(", ");
            }
            detalle.append(descuento.tipo())
                    .append(":")
                    .append(descuento.monto());
        }
        return detalle.append("]").toString();
    }
}
