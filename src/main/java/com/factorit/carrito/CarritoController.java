package com.factorit.carrito;

import com.factorit.carrito.dto.AgregarItemRequest;
import com.factorit.carrito.dto.CarritoResponse;
import com.factorit.carrito.dto.CheckoutRequest;
import com.factorit.carrito.dto.CrearCarritoRequest;
import com.factorit.compra.dto.CompraResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    @PostMapping
    public CarritoResponse crear(@RequestBody CrearCarritoRequest req) {
        return null; 
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        
    }

    @PostMapping("/{id}/items")
    public CarritoResponse agregarItem(@PathVariable Long id, @RequestBody AgregarItemRequest req) {
        return null; 
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public void eliminarItem(@PathVariable Long id, @PathVariable Long itemId) {

    }

    @GetMapping("/{id}")
    public CarritoResponse consultar(@PathVariable Long id) {
        return null; 
    }

    @PostMapping("/{id}/checkout")
    public CompraResponse checkout(@PathVariable Long id, @RequestBody CheckoutRequest req) {
        return null; 
    }
}
