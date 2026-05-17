package com.factorit.compra;

import com.factorit.FactoritEntity;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private BigDecimal total;

}
