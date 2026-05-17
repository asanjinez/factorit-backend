package com.factorit.carrito;

import com.factorit.carrito.dto.AgregarItemRequest;
import com.factorit.carrito.dto.CarritoItemResponse;
import com.factorit.carrito.dto.CarritoResponse;
import com.factorit.carrito.dto.CheckoutRequest;
import com.factorit.carrito.dto.CrearCarritoRequest;
import com.factorit.compra.Compra;
import com.factorit.compra.ICompraRepository;
import com.factorit.compra.ItemCompra;
import com.factorit.compra.dto.CompraItemResponse;
import com.factorit.compra.dto.CompraResponse;
import com.factorit.exception.CarritoItemNotFoundException;
import com.factorit.exception.CarritoNotFoundException;
import com.factorit.exception.CheckoutInvalidException;
import com.factorit.exception.ProductoInvalidException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CarritoServiceImpl implements ICarritoService {

    @Autowired
    private ICarritoRepository carritoRepository;

    @Autowired
    private ICompraRepository compraRepository;

    @Override
    public CarritoResponse create(CrearCarritoRequest req) {
        Carrito carrito = new Carrito();
        carrito.setSpecial(req.isSpecial());
        carritoRepository.save(carrito);
        return toResponse(carrito);
    }

    @Override
    public void delete(Long id) {
        Carrito carrito = findCarrito(id);
        carritoRepository.delete(carrito);
    }

    @Override
    public CarritoResponse addItem(Long id, AgregarItemRequest req) {
        validateProducto(req);

        Carrito carrito = findCarrito(id);
        CarritoItem item = new CarritoItem();
        item.setProductoNombre(req.getNombre());
        item.setPrecioUnitario(req.getPrecio());
        item.setCantidad(req.getCantidad());
        carrito.getItems().add(item);
        carritoRepository.saveAndFlush(carrito);
        return toResponse(carrito);
    }

    @Override
    public void deleteItem(Long id, Long itemId) {
        Carrito carrito = findCarrito(id);
        boolean removed = carrito.getItems().removeIf(item -> item.getId().equals(itemId));
        if (!removed) {
            throw new CarritoItemNotFoundException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CarritoResponse findById(Long id) {
        Carrito carrito = findCarrito(id);
        return toResponse(carrito);
    }

    @Override
    public CompraResponse checkout(Long id, CheckoutRequest req) {
        validateCheckout(req);

        Carrito carrito = findCarrito(id);
        List<CarritoItem> carritoItems = List.copyOf(carrito.getItems());
        if (carritoItems.isEmpty()) {
            throw new CheckoutInvalidException("Cart is empty");
        }

        BigDecimal total = carritoItems.stream()
                .map(i -> i.getPrecioUnitario().multiply(BigDecimal.valueOf(i.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Compra compra = new Compra();
        compra.setClienteDni(req.getDni());
        compra.setFecha(LocalDateTime.now());
        compra.setTotal(total);

        List<ItemCompra> itemsCompra = carritoItems.stream()
                .map(this::toItemCompra)
                .toList();
        compra.getItems().addAll(itemsCompra);
        compraRepository.save(compra);

        carritoRepository.delete(carrito);

        return toCompraResponse(compra, itemsCompra);
    }

    private Carrito findCarrito(Long id) {
        return carritoRepository.findById(id).orElseThrow(CarritoNotFoundException::new);
    }

    private void validateProducto(AgregarItemRequest req) {
        if (req == null) {
            throw new ProductoInvalidException("Product is required");
        }
        if (req.getNombre() == null || req.getNombre().isBlank()) {
            throw new ProductoInvalidException("Product name is required");
        }
        if (req.getPrecio() == null || req.getPrecio().compareTo(BigDecimal.valueOf(100)) <= 0) {
            throw new ProductoInvalidException("Product price must be greater than 100");
        }
        if (req.getCantidad() == null || req.getCantidad() <= 0) {
            throw new ProductoInvalidException("Product quantity must be greater than 0");
        }
    }

    private void validateCheckout(CheckoutRequest req) {
        if (req == null || req.getDni() == null || req.getDni().isBlank()) {
            throw new CheckoutInvalidException("DNI is required");
        }
    }

    private ItemCompra toItemCompra(CarritoItem carritoItem) {
        ItemCompra itemCompra = new ItemCompra();
        itemCompra.setProductoNombre(carritoItem.getProductoNombre());
        itemCompra.setPrecioUnitario(carritoItem.getPrecioUnitario());
        itemCompra.setCantidad(carritoItem.getCantidad());
        return itemCompra;
    }

    private CompraResponse toCompraResponse(Compra compra, List<ItemCompra> items) {
        List<CompraItemResponse> itemResponses = items.stream()
                .map(i -> new CompraItemResponse(i.getProductoNombre(), i.getPrecioUnitario(), i.getCantidad()))
                .toList();
        return new CompraResponse(compra.getId(), compra.getClienteDni(), compra.getFecha(),
                compra.getTotal(), itemResponses);
    }

    private CarritoResponse toResponse(Carrito carrito) {
        List<CarritoItem> items = carrito.getItems();
        List<CarritoItemResponse> itemResponses = items.stream()
                .map(i -> new CarritoItemResponse(i.getId(), i.getProductoNombre(),
                        i.getPrecioUnitario(), i.getCantidad()))
                .toList();
        int totalProductos = items.stream().mapToInt(CarritoItem::getCantidad).sum();
        return new CarritoResponse(carrito.getId(), totalProductos, itemResponses);
    }
}
