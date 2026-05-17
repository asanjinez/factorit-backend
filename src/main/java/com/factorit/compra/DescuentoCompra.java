package com.factorit.compra;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DescuentoCompra {
    private String tipo;
    private String nivel;
    private String descripcion;
    private BigDecimal monto;
    private String productoNombre;
}
