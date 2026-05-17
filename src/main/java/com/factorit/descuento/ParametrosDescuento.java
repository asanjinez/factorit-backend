package com.factorit.descuento;

import com.factorit.carrito.Carrito;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametrosDescuento {
    private Carrito carrito;
    private String dni;
    private LocalDateTime fecha;
}
