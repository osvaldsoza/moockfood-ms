CREATE TABLE item_do_pedido (
  id serial NOT NULL PRIMARY KEY,
  descricao varchar(255) DEFAULT NULL,
  quantidade int NOT NULL,
  pedido_id bigint NOT NULL,
  FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
)