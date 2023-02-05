CREATE TABLE IF NOT EXISTS restaurantes_formas_pagamento(
    restaurante_id bigint NOT NULL,
    forma_pagamento_id bigint NOT NULL,

    CONSTRAINT fk_restaurante_id_restaurante FOREIGN KEY(restaurante_id) REFERENCES restaurantes(id),
    CONSTRAINT fk_forma_pagamento_id_restaurante FOREIGN KEY(forma_pagamento_id) REFERENCES formas_pagamento(id),
    CONSTRAINT pk_restaurante_forma_pagamento PRIMARY KEY(restaurante_id, forma_pagamento_id)
);

