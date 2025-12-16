DELIMITER //
CREATE PROCEDURE actualizar_precios_categoria(
    IN categoria_param VARCHAR(50),
    IN porcentaje_param DECIMAL(5,2)
)
BEGIN
    UPDATE articulos 
    SET precio = precio * (1 + porcentaje_param / 100)
    WHERE categoria = categoria_param;
    COMMIT;
    SELECT CONCAT('Precios actualizados para categoría: ', categoria_param) AS resultado;
END //
DELIMITER ;