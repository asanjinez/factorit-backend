package com.factorit.descuento;

public enum TipoDescuento {
    PROMO_4X3("Promo 4x3 en producto"),
    DESCUENTO_CANTIDAD("Descuento por mas de 3 productos"),
    COMPRA_VIP("Beneficio cliente VIP");

    private final String descripcion;

    TipoDescuento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
