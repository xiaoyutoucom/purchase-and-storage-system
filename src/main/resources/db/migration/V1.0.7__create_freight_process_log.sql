-- Table: public.freight_process_log

-- DROP TABLE public.freight_process_log;

CREATE TABLE IF NOT EXISTS public.freight_process_log
(
    id character varying(50) COLLATE pg_catalog."default" NOT NULL,
    order_ref character varying(20) COLLATE pg_catalog."default",
    message character varying(3000) COLLATE pg_catalog."default",
    order_type character varying(20) COLLATE pg_catalog."default",
    create_time timestamp without time zone,
    is_deleted smallint,
    CONSTRAINT pk_freight_process_log PRIMARY KEY (id)
)

    TABLESPACE pg_default;



COMMENT ON COLUMN public.freight_process_log.order_ref
    IS '订单号';