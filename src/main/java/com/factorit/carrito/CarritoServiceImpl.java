package com.factorit.carrito;

import com.factorit.carrito.carrito_item.ICarritoItemRepository;
import com.factorit.carrito.dto.AgregarItemRequest;
import com.factorit.carrito.dto.CarritoResponse;
import com.factorit.carrito.dto.CheckoutRequest;
import com.factorit.carrito.dto.CrearCarritoRequest;
import com.factorit.compra.dto.CompraResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoServiceImpl implements ICarritoService {

    @Autowired
    private ICarritoRepository carritoRepository;

    @Autowired
    private ICarritoItemRepository carritoItemRepository;

    @Override
    public CarritoResponse create(CrearCarritoRequest req) {
        return null; 
    }

    @Override
    public void delete(Long id) {
         
    }

    @Override
    public CarritoResponse addItem(Long id, AgregarItemRequest req) {
        return null;  
    }

    @Override
    public void deleteItem(Long id, Long itemId) {
         
    }

    @Override
    public CarritoResponse findById(Long id) {
        return null;  
    }

    @Override
    public CompraResponse checkout(Long id, CheckoutRequest req) {
        return null;  
    }
}
