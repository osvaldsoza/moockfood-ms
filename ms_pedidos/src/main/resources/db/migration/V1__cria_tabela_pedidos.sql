CREATE TABLE pedidos (
  id serial NOT NULL primary key,
  data_hora timestamp NOT NULL,
  status varchar(255) NOT NULL
)