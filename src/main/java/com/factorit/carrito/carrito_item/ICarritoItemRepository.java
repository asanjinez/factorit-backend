package com.factorit.carrito.carrito_item;

import java.util.List;

import com.factorit.carrito.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICarritoItemRepository extends JpaRepository<CarritoItem, Long> {

    List<CarritoItem> findByCarritoId(Long carritoId);
}
