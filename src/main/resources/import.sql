INSERT INTO clientes ( nombre, apellido, email, create_at, foto) VALUES( 'David', 'Alberto', 'david@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('David2', 'Alberto2', 'david2@gmail.com', '2017-09-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('David3', 'Alberto2', 'david2@gmail.com', '2017-09-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('David4', 'Alberto2', 'david2@gmail.com', '2017-09-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('David5', 'Alberto2', 'david2@gmail.com', '2017-09-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('David6', 'Alberto2', 'david2@gmail.com', '2017-09-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('David7', 'Alberto2', 'david2@gmail.com', '2017-09-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('David8', 'Alberto2', 'david2@gmail.com', '2017-09-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('David9', 'Alberto2', 'david2@gmail.com', '2017-09-28', '');


--Productos

INSERT INTO productos (nombre, precio, create_at) VALUES ('Leche lala', 90.2, GETDATE());
INSERT INTO productos(nombre, precio, create_at) VALUES('LECHE SANTA CLARA', 123.1, GETDATE());
INSERT INTO productos(nombre, precio, create_at) VALUES('LECHE SANTA CLARA', 355, GETDATE());
INSERT INTO productos(nombre, precio, create_at) VALUES('Sabritas', 950, GETDATE());
INSERT INTO productos(nombre, precio, create_at) VALUES('Tamales', 500, GETDATE());

SELECT * FROM productos 

INSERT INTO facturas(descripcion, observacion, cliente_id, creado_at) VALUES ('Compra en tiend', 'Compra en casa', 1, GETDATE());

INSERT INTO items_factura (cantidad, producto_id) VALUES (3 , 2);