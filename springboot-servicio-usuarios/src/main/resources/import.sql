INSERT INTO `usuarios`(username, password, enabled, nombre, apellidos, email) VALUES ('raul', '12345', 1, 'Raul', 'Gómez Beteta', 'raul@email.com');
INSERT INTO `usuarios`(username, password, enabled, nombre, apellidos, email) VALUES ('admin', '12345', 1, 'Brandon', 'Sanderson', 'cosmere@email.com');

INSERT INTO `roles`(nombre) VALUES ('ROLE_USER');
INSERT INTO `roles`(nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles`(usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles`(usuario_id, role_id) VALUES (2, 2);
INSERT INTO `usuarios_roles`(usuario_id, role_id) VALUES (2, 1);
