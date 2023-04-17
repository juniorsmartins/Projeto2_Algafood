ALTER TABLE IF EXISTS restaurantes ADD aberto boolean;
UPDATE restaurantes SET aberto = true;
ALTER TABLE IF EXISTS restaurantes ALTER COLUMN aberto SET NOT NULL;

