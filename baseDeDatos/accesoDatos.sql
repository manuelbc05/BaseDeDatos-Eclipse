DROP TABLE IF EXISTS Reparaciones, LineasPedido, Pedidos, Articulos, Proveedores, Clientes, Empleados, Tiendas;

CREATE TABLE Tiendas (
    id_tienda INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100)
);

CREATE TABLE Empleados (
    id_empleado INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    nif VARCHAR(20) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    puesto VARCHAR(50),
    salario DECIMAL(10,2),
    id_tienda INT,
    FOREIGN KEY(id_tienda) REFERENCES Tiendas(id_tienda)
);

CREATE TABLE Clientes (
    id_cliente INT PRIMARY KEY,
    nombre_completo VARCHAR(200) NOT NULL,
    nif_cif VARCHAR(20) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    ciudad VARCHAR(100)
);

CREATE TABLE Proveedores (
    id_proveedor INT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    nif_cif VARCHAR(20) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    direccion VARCHAR(200)
);

CREATE TABLE Articulos (
    id_articulo INT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    categoria VARCHAR(50),
    precio DECIMAL(10,2),
    stock INT,
    id_proveedor INT,
    FOREIGN KEY(id_proveedor) REFERENCES Proveedores(id_proveedor)
);

CREATE TABLE Pedidos (
    id_pedido INT PRIMARY KEY,
    id_cliente INT,
    id_empleado INT,
    id_tienda INT,
    fecha DATE,
    estado VARCHAR(50),
    FOREIGN KEY(id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY(id_empleado) REFERENCES Empleados(id_empleado),
    FOREIGN KEY(id_tienda) REFERENCES Tiendas(id_tienda)
);

CREATE TABLE LineasPedido (
    id_linea INT PRIMARY KEY,
    id_pedido INT,
    id_articulo INT,
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    FOREIGN KEY(id_pedido) REFERENCES Pedidos(id_pedido),
    FOREIGN KEY(id_articulo) REFERENCES Articulos(id_articulo)
);

CREATE TABLE Reparaciones (
    id_reparacion INT PRIMARY KEY,
    id_cliente INT,
    id_empleado INT,
    id_tienda INT,
    fecha_entrada DATE,
    fecha_salida DATE,
    descripcion VARCHAR(300),
    diagnostico VARCHAR(300),
    coste_mano_obra DECIMAL(10,2),
    estado VARCHAR(50),
    FOREIGN KEY(id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY(id_empleado) REFERENCES Empleados(id_empleado),
    FOREIGN KEY(id_tienda) REFERENCES Tiendas(id_tienda)
);
