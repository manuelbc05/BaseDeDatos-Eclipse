INSERT INTO Tiendas (id_tienda, nombre, direccion, telefono, correo) VALUES
(1, 'Pc Componentes', 'Online Plaza, Madrid', '966666666', 'uno@pccomponentes.com'),
(2, 'Pc Componentes dos', 'Sol, Madrid', '977777777', 'dos@pccomponentes.com');


INSERT INTO Empleados (id_empleado, nombre, apellidos, nif, telefono, correo, puesto, salario, id_tienda) VALUES
(1, 'Manuel', 'Bravo Candilejo', '80371234A', '600000000', 'manuel.bravo@gmail.com', 'Técnico', 2000.00, 1),
(2, 'Ariadne', 'Garcia Nikolaeva', '23456789M', '611111111', 'ariadne.garcia@gmail.com', 'Técnico', 2000.00, 2),
(3, 'Nieves', 'Peña Aranda', '01239774N', '622222222', 'nieves.peña@gmail.com', 'Vendedora', 1800.00, 1),
(4, 'Pedro', 'Garcia Lopez', '37164838P', '633333333', 'pedro.garcia@gmail.com', 'Vendedor', 1800.00, 2),
(5, 'Sofia', 'Manolova Boroga', 'X2000280Z', '644444444', 'sofia.manolova@gmail.com', 'Gerente', 2500.00, 1);


INSERT INTO Clientes (id_cliente, nombre_completo, nif_cif, telefono, correo, ciudad) VALUES
(1, 'Gonzalo Bravo', '11111111G', '655555555', 'gonzalo@gmail.com', 'Badajoz'),
(2, 'Martin Garcia', '22222222M', '600000006', 'martin@gmail.com', 'Toledo'),
(3, 'Isabel Gonzalez', '33333333I', '677777777', 'isabel@gmail.com', 'Madrid'),
(4, 'Daniela Ibai', '44444444D', '688888888', 'daniela@gmail.com', 'Madrid'),
(5, 'Soledad Vega', '55555555E', '699999999', 'soledad@gmail.com', 'Cuenca');


INSERT INTO Proveedores (id_proveedor, nombre, nif_cif, telefono, correo, direccion) VALUES
(1, 'Mbr S.L', 'M11111111', '912345678', 'contacto@mbr.com', 'C/ Frambuesa, Huelva'),
(2, 'Mike Wheeler', 'M22222222', '987654321', 'mike@wheeler.com', 'C/ Carteles, Madrid'),
(3, 'Pourtugueis', 'P33333333', '987651234', 'cr@pourtugueis.com', 'C/ Microchips, Madrid'),
(4, 'Nancy', 'N44444444', '912348765', 'wheel@nancy.com', 'C/ Componentes, Madrid'),
(5, 'Joyce', 'J55555555', '932654321', 'luces@joyce.com', 'C/ LucesNavidad, Madrid');


INSERT INTO Articulos (id_articulo, nombre, categoria, precio, stock, id_proveedor) VALUES
(1, 'Teclado logitech razer', 'Periféricos', 250.00, 50, 1),
(2, 'Ratón NewSkill Patuski', 'Periféricos', 45.00, 10, 2),
(3, 'Lenovo"" Sobremesa', 'Procesadores', 1000.00, 35, 5),
(4, 'Monitor lenovo"" hurra', 'Pantallas', 200.00, 10, 3),
(5, 'Disco HDD', 'Almacenamiento', 60.00, 30, 4),
(6, 'Disco SSD 1TB', 'Almacenamiento', 190.00, 20, 4),
(7, 'CPU Ryzen', 'Procesadores', 180.00, 22, 5),
(8, 'CPU Intel', 'Procesadores', 200.00, 44, 5),
(9, 'Radeon', 'Componentes', 150.00, 25, 1),
(10,'Nvidia', 'Componentes', 65.00, 18, 2);


INSERT INTO Pedidos (id_pedido, id_cliente, id_empleado, id_tienda, fecha, estado) VALUES
(1, 1, 1, 1, TO_DATE('2025-01-10','YYYY-MM-DD'), 'Procesando'),
(2, 3, 4, 2, TO_DATE('2025-01-14','YYYY-MM-DD'), 'Enviado'),
(3, 4, 2, 1, TO_DATE('2025-01-16','YYYY-MM-DD'), 'Entregado');


INSERT INTO LineasPedido (id_linea, id_pedido, id_articulo, cantidad, precio_unitario) VALUES
(1, 1, 1, 1, 250.00),
(2, 1, 10, 1, 65.00),
(3, 2, 2, 1, 45.00),
(4, 2, 5, 1, 60.00),
(5, 3, 7, 1, 180.00),
(6, 3, 9, 1, 150.00);


INSERT INTO Reparaciones (
    id_reparacion, id_cliente, id_empleado, id_tienda,
    fecha_entrada, fecha_salida, descripcion, diagnostico,
    coste_mano_obra, estado
) VALUES
(1, 1, 2, 1, TO_DATE('2025-01-05','YYYY-MM-DD'), NULL, 
 'Sobremesa no enciende', 'Posible fallo en fuente de alimentación', 45.00, 'En proceso'),
(2, 3, 4, 2, TO_DATE('2025-01-08','YYYY-MM-DD'), NULL,
 'Ruido interno', 'Pendiente de diagnóstico', 0.00, 'Pendiente'),
(3, 4, 2, 1, TO_DATE('2025-01-03','YYYY-MM-DD'),
 TO_DATE('2025-01-07','YYYY-MM-DD'),
 'Cambio de batería', 'Reparación completada', 30.00, 'Finalizada');
