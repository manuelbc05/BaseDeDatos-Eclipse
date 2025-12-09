-- Desactivar claves foráneas para evitar errores
ALTER SESSION SET CONSTRAINTS = DEFERRED;

-- Drop de tablas sin PL/SQL
DROP TABLE Reparaciones CASCADE CONSTRAINTS PURGE;
DROP TABLE LineasPedido CASCADE CONSTRAINTS PURGE;
DROP TABLE Pedidos CASCADE CONSTRAINTS PURGE;
DROP TABLE Articulos CASCADE CONSTRAINTS PURGE;
DROP TABLE Proveedores CASCADE CONSTRAINTS PURGE;
DROP TABLE Clientes CASCADE CONSTRAINTS PURGE;
DROP TABLE Empleados CASCADE CONSTRAINTS PURGE;
DROP TABLE Tiendas CASCADE CONSTRAINTS PURGE;

-- Activar claves foráneas de nuevo
ALTER SESSION SET CONSTRAINTS = IMMEDIATE;

-- Crear tablas

CREATE TABLE Tiendas (
    id_tienda NUMBER PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    direccion VARCHAR2(200) NOT NULL,
    telefono VARCHAR2(20),
    correo VARCHAR2(100)
);

CREATE TABLE Empleados (
    id_empleado NUMBER PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    apellidos VARCHAR2(150) NOT NULL,
    nif VARCHAR2(20) UNIQUE NOT NULL,
    telefono VARCHAR2(20),
    correo VARCHAR2(100),
    puesto VARCHAR2(50),
    salario NUMBER(10,2),
    id_tienda NUMBER,
    FOREIGN KEY(id_tienda) REFERENCES Tiendas(id_tienda)
);

CREATE TABLE Clientes (
    id_cliente NUMBER PRIMARY KEY,
    nombre_completo VARCHAR2(200) NOT NULL,
    nif_cif VARCHAR2(20) UNIQUE NOT NULL,
    telefono VARCHAR2(20),
    correo VARCHAR2(100),
    ciudad VARCHAR2(100)
);

CREATE TABLE Proveedores (
    id_proveedor NUMBER PRIMARY KEY,
    nombre VARCHAR2(150) NOT NULL,
    nif_cif VARCHAR2(20) UNIQUE NOT NULL,
    telefono VARCHAR2(20),
    correo VARCHAR2(100),
    direccion VARCHAR2(200)
);

CREATE TABLE Articulos (
    id_articulo NUMBER PRIMARY KEY,
    nombre VARCHAR2(150) NOT NULL,
    categoria VARCHAR2(50),
    precio NUMBER(10,2),
    stock NUMBER,
    id_proveedor NUMBER,
    FOREIGN KEY(id_proveedor) REFERENCES Proveedores(id_proveedor)
);

CREATE TABLE Pedidos (
    id_pedido NUMBER PRIMARY KEY,
    id_cliente NUMBER,
    id_empleado NUMBER,
    id_tienda NUMBER,
    fecha DATE,
    estado VARCHAR2(50),
    FOREIGN KEY(id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY(id_empleado) REFERENCES Empleados(id_empleado),
    FOREIGN KEY(id_tienda) REFERENCES Tiendas(id_tienda)
);

CREATE TABLE LineasPedido (
    id_linea NUMBER PRIMARY KEY,
    id_pedido NUMBER,
    id_articulo NUMBER,
    cantidad NUMBER,
    precio_unitario NUMBER(10,2),
    FOREIGN KEY(id_pedido) REFERENCES Pedidos(id_pedido),
    FOREIGN KEY(id_articulo) REFERENCES Articulos(id_articulo)
);

CREATE TABLE Reparaciones (
    id_reparacion NUMBER PRIMARY KEY,
    id_cliente NUMBER,
    id_empleado NUMBER,
    id_tienda NUMBER,
    fecha_entrada DATE,
    fecha_salida DATE,
    descripcion VARCHAR2(300),
    diagnostico VARCHAR2(300),
    coste_mano_obra NUMBER(10,2),
    estado VARCHAR2(50),
    FOREIGN KEY(id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY(id_empleado) REFERENCES Empleados(id_empleado),
    FOREIGN KEY(id_tienda) REFERENCES Tiendas(id_tienda)
);
