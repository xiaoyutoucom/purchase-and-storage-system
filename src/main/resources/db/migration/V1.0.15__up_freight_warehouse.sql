ALTER TABLE public.freight_warehouse
    ADD COLUMN fliwayaccount  varchar(50) ;
UPDATE public.freight_warehouse
SET fliwayaccount='30002584'
WHERE warehouse='hamilton';
UPDATE public.freight_warehouse
SET fliwayaccount='30002413'
WHERE warehouse='otahuhu';
UPDATE public.freight_warehouse
SET fliwayaccount='30002549'
WHERE warehouse='christchurch';
UPDATE public.freight_warehouse
SET fliwayaccount='30002413'
WHERE warehouse='auckland';