package com.factorit.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.factorit.carrito.ICarritoRepository;
import com.factorit.compra.ICompraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTests {

    @Autowired
    private MockMvc mockMvc;

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
    void respondeCarritoNoEncontrado() throws Exception {
        mockMvc.perform(get("/carritos/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("CARRITO_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Cart not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value("/carritos/999999"));
    }

    @Test
    void respondeItemNoEncontrado() throws Exception {
        Long carritoId = crearCarrito();

        mockMvc.perform(delete("/carritos/{id}/items/{itemId}", carritoId, 999999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("CARRITO_ITEM_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Cart item not found"));
    }

    @Test
    void respondeProductoInvalido() throws Exception {
        Long carritoId = crearCarrito();

        mockMvc.perform(post("/carritos/{id}/items", carritoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "Cable USB",
                                  "precio": 100,
                                  "cantidad": 1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("PRODUCTO_INVALID"))
                .andExpect(jsonPath("$.message").value("Product price must be greater than 100"));
    }

    @Test
    void respondeCheckoutInvalido() throws Exception {
        Long carritoId = crearCarrito();

        mockMvc.perform(post("/carritos/{id}/checkout", carritoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "dni": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("CHECKOUT_INVALID"))
                .andExpect(jsonPath("$.message").value("DNI is required"));
    }

    @Test
    void respondeDniInvalidoEnCheckout() throws Exception {
        Long carritoId = crearCarrito();

        mockMvc.perform(post("/carritos/{id}/checkout", carritoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "dni": "12A"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("DNI_INVALID"))
                .andExpect(jsonPath("$.message").value("DNI must contain exactly 8 digits"));
    }

    @Test
    void respondeDniInvalidoEnConsultaDeCompras() throws Exception {
        mockMvc.perform(get("/compras/12A"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("DNI_INVALID"))
                .andExpect(jsonPath("$.message").value("DNI must contain exactly 8 digits"));
    }

    @Test
    void respondeBodyInvalido() throws Exception {
        mockMvc.perform(post("/carritos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_REQUEST_BODY"))
                .andExpect(jsonPath("$.message").value("Invalid request body"));
    }

    @Test
    void respondeParametroFechaInvalido() throws Exception {
        mockMvc.perform(get("/compras/{dni}/from", "30111222")
                        .param("from", "17-05-2026"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_PARAMETER"))
                .andExpect(jsonPath("$.message").value("Invalid parameter"));
    }

    @Test
    void respondeParametroFaltante() throws Exception {
        mockMvc.perform(get("/compras/{dni}/periodo", "30111222")
                        .param("from", "2026-05-01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("MISSING_PARAMETER"))
                .andExpect(jsonPath("$.message").value("Required parameter is missing"));
    }

    @Test
    void respondeMetodoNoPermitido() throws Exception {
        mockMvc.perform(post("/compras"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.code").value("METHOD_NOT_ALLOWED"))
                .andExpect(jsonPath("$.message").value("Method not allowed"));
    }

    private Long crearCarrito() throws Exception {
        String response = mockMvc.perform(post("/carritos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "special": false
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return Long.valueOf(response.replaceAll(".*\"id\":(\\d+).*", "$1"));
    }
}
