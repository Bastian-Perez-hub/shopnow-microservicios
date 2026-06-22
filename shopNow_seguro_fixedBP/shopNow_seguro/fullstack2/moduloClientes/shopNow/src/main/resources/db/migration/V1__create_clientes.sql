CREATE TABLE Clientes (
    idCliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    run VARCHAR(20) NOT NULL,
    fechaNacimiento DATE NOT NULL
);