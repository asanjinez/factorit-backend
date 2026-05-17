package com.factorit.carrito;

import com.factorit.FactoritEntity;
import jakarta.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Carrito extends FactoritEntity {
    private boolean special;
}
