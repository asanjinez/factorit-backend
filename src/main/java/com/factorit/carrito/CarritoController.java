package com.factorit.carrito;

import com.factorit.carrito.dto.AgregarItemRequest;
import com.factorit.carrito.dto.CarritoResponse;
import com.factorit.carrito.dto.CheckoutRequest;
import com.factorit.carrito.dto.CrearCarritoRequest;
import com.factorit.compra.dto.CompraResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    @Autowired
    private ICarritoService carritoService;

    @PostMapping
    public CarritoResponse create(@RequestBody CrearCarritoRequest req) {
        return carritoService.create(req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        carritoService.delete(id);
    }

    @PostMapping("/{id}/items")
    public CarritoResponse addItem(@PathVariable Long id, @RequestBody AgregarItemRequest req) {
        return carritoService.addItem(id, req);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public void deleteItem(@PathVariable Long id, @PathVariable Long itemId) {
        carritoService.deleteItem(id, itemId);
    }

    @GetMapping
    public List<CarritoResponse> findAll() {
        return carritoService.findAll();
    }
    @GetMapping("/{id}")
    public CarritoResponse findById(@PathVariable Long id) {
        return carritoService.findById(id);
    }

    @PostMapping("/{id}/checkout")
    public CompraResponse checkout(@PathVariable Long id, @RequestBody CheckoutRequest req) {
        return carritoService.checkout(id, req);
    }
}
