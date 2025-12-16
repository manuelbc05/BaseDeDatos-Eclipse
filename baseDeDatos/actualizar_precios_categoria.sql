DELIMITER //
CREATE PROCEDURE actualizar_precios_categoria(
    cat VARCHAR(50),
    porc DECIMAL(5,2)
)
BEGIN
    UPDATE articulos SET precio = precio * (1 + porc/100) WHERE categoria = cat;
END //
DELIMITER ;
