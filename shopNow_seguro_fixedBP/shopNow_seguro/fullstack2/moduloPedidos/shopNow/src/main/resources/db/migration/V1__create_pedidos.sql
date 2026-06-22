CREATE TABLE Pedidos (
    idPedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    idCliente BIGINT NOT NULL,
    fechaPedido DATETIME NOT NULL,
    estado VARCHAR(50) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    metodoPago VARCHAR(50) NOT NULL,
    direccionEnvio VARCHAR(255) NOT NULL,
    fechaEntrega DATE NOT NULL
);
