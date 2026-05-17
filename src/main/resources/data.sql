INSERT INTO carrito (id, special, fecha_creacion, fecha_modificacion)
VALUES (1, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO carrito_item (id, producto_nombre, precio_unitario, cantidad, carrito_id, fecha_creacion, fecha_modificacion)
VALUES (1, 'Mouse inalambrico', 1200, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO compra (id, cliente_dni, fecha, subtotal, descuento_total, total, fecha_creacion, fecha_modificacion)
VALUES (1, '30111222', '2026-05-01 10:00:00', 6000, 0, 6000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO item_compra (id, producto_nombre, precio_unitario, cantidad, compra_id, fecha_creacion, fecha_modificacion)
VALUES (1, 'Monitor', 6000, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO compra (id, cliente_dni, fecha, subtotal, descuento_total, total, fecha_creacion, fecha_modificacion)
VALUES (2, '30111222', '2026-05-10 11:00:00', 4800, 1300, 3500, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO item_compra (id, producto_nombre, precio_unitario, cantidad, compra_id, fecha_creacion, fecha_modificacion)
VALUES (2, 'Mouse inalambrico', 1200, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO compra_descuento (compra_id, tipo, nivel, descripcion, monto, producto_nombre)
VALUES (2, 'PROMO_4X3', 'ITEM', 'Promo 4x3 en producto', 1200, 'Mouse inalambrico');

INSERT INTO compra_descuento (compra_id, tipo, nivel, descripcion, monto, producto_nombre)
VALUES (2, 'DESCUENTO_CANTIDAD', 'TOTAL', 'Descuento por mas de 3 productos', 100, NULL);

INSERT INTO compra (id, cliente_dni, fecha, subtotal, descuento_total, total, fecha_creacion, fecha_modificacion)
VALUES (3, '40999888', '2026-05-12 12:00:00', 2500, 0, 2500, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO item_compra (id, producto_nombre, precio_unitario, cantidad, compra_id, fecha_creacion, fecha_modificacion)
VALUES (3, 'Teclado mecanico', 2500, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

ALTER TABLE carrito ALTER COLUMN id RESTART WITH 2;
ALTER TABLE carrito_item ALTER COLUMN id RESTART WITH 2;
ALTER TABLE compra ALTER COLUMN id RESTART WITH 4;
ALTER TABLE item_compra ALTER COLUMN id RESTART WITH 4;
