ALTER TABLE public.empresas ALTER COLUMN nome DROP NOT NULL;
ALTER TABLE public.empresas ADD nome_fantasia varchar(120);