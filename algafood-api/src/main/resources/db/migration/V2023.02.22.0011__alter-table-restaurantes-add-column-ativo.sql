ALTER TABLE IF EXISTS restaurantes ADD ativo boolean;
UPDATE restaurantes SET ativo = true;
ALTER TABLE IF EXISTS restaurantes ALTER COLUMN ativo SET NOT NULL;

