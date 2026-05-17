package com.factorit.compra;

import com.factorit.FactoritEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Compra extends FactoritEntity {
    private String clienteDni;
    private LocalDateTime fecha;
    private BigDecimal subtotal;
    private BigDecimal descuentoTotal;
    private BigDecimal total;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "compra_id")
    private List<ItemCompra> items = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "compra_descuento", joinColumns = @JoinColumn(name = "compra_id"))
    private List<DescuentoCompra> descuentos = new ArrayList<>();
}
