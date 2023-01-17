CREATE TABLE pagamentos(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    valor decimal(19,2) NOT NULL,
    nome VARCHAR(100) DEFAULT NULL,
    numero VARCHAR(19) DEFAULT NULL,
    expiracao VARCHAR(7) NOT NULL,
    codigo VARCHAR(3) DEFAULT NULL,
    status VARCHAR(255) NOT NULL,
    forma_de_pagamento_id bigint(20) NOT NULL,
    pedido_id bigint(20) NOT NULL,
    PRIMARY KEY (id)
);