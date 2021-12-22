-- Table: public.freight_region_info

-- DROP TABLE public.freight_region_info;

CREATE TABLE IF NOT EXISTS public.freight_region_info
(
    id character varying(50) COLLATE pg_catalog."default" NOT NULL,
    suburb character varying(50) COLLATE pg_catalog."default",
    postcode bigint,
    city character varying(50) COLLATE pg_catalog."default",
    notes character varying(50) COLLATE pg_catalog."default",
    code character varying(50) COLLATE pg_catalog."default",
    create_time timestamp without time zone,
    is_deleted smallint,
    CONSTRAINT pk_freight_region_info PRIMARY KEY (id)
)

    TABLESPACE pg_default;



COMMENT ON COLUMN public.freight_region_info.suburb
    IS '郊区';