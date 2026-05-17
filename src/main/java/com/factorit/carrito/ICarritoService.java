package com.factorit.carrito;

import com.factorit.carrito.dto.AgregarItemRequest;
import com.factorit.carrito.dto.CarritoResponse;
import com.factorit.carrito.dto.CheckoutRequest;
import com.factorit.carrito.dto.CrearCarritoRequest;
import com.factorit.compra.dto.CompraResponse;

public interface ICarritoService {

    CarritoResponse create(CrearCarritoRequest req);

    void delete(Long id);

    CarritoResponse addItem(Long id, AgregarItemRequest req);

    void deleteItem(Long id, Long itemId);

    CarritoResponse findById(Long id);

    CompraResponse checkout(Long id, CheckoutRequest req);
}
