1. Descripción de la empresa TecnoLocal Informática 
La empresa TecnoLocal Informática dispone de varias tiendas físicas, inicialmente 
dos. Cada tienda tiene: un identificador interno, un nombre comercial, una dirección 
completa, un teléfono de contacto y un correo electrónico de la tienda. 
Dentro de cada tienda trabajan varios empleados. Cada empleado tiene: un identificador 
interno, nombre, apellidos, NIF, teléfono, correo electrónico, un puesto dentro de la 
empresa (por ejemplo, gerente, vendedor, técnico), un salario mensual, y está asignado a 
una única tienda. 
La empresa vende productos de informática a sus clientes. Cada cliente tiene: un 
identificador interno, nombre completo (nombre y apellidos si es un particular o razón 
social si es empresa), NIF/NIE o CIF, teléfono, correo electrónico y ciudad. Un cliente 
puede comprar en cualquiera de las tiendas y puede tener varios pedidos a lo largo del 
tiempo. 
Los productos que se venden se almacenan como artículos. Cada artículo tiene: un 
identificador interno, un nombre, una categoría (por ejemplo, portátil, componente, 
periférico, software), un precio de venta y un stock disponible. Cada artículo tiene 
asociado un proveedor principal. 
La tienda trabaja con varios proveedores. Cada proveedor tiene: un identificador 
interno, un nombre comercial, un NIF/CIF, teléfono, correo electrónico y dirección. 
PROYECTO CAPÍTULO 2 (MANEJO DE CONECTORES) AD CURSO 25_26 
Las ventas se registran como pedidos de venta. Cada pedido de venta tiene: un 
identificador interno de pedido, un cliente que lo realiza, un empleado que lo tramita 
(vendedor), la tienda en la que se realiza, una fecha de pedido y un estado (por ejemplo, 
pendiente, enviado, entregado, cancelado). Cada pedido está formado por varias líneas, 
y en cada línea aparece un artículo, una cantidad y un precio unitario aplicable en ese 
momento. 
Además, la empresa ofrece servicio de reparaciones. Cada reparación tiene: un 
identificador interno de reparación, un cliente que la solicita, un empleado técnico que 
la realiza, la tienda donde se registra la reparación, una fecha de entrada, una fecha de 
salida (si la reparación ya ha sido entregada), una descripción del problema, un 
diagnóstico, un coste de mano de obra y un estado (por ejemplo, en revisión, pendiente 
de piezas, reparado, entregado).
