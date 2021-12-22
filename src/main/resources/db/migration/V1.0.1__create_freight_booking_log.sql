-- drop table freight_booking_log;
create table freight_booking_log (
                                     id                   varchar(50)          not null,
                                     order_ref            varchar(20)               null,
                                     connote_number       varchar(50)               null,
--     quote_m2h            FLOAT8               null,
--     quote_tdt            FLOAT8               null,
--     quote_gss            FLOAT8               null,
                                     order_company        varchar(50)               null,
                                     order_shipping_fee   FLOAT8               null,
                                     order_shipping_by    varchar(20)          null,
                                     order_postcode_from  varchar(4)               null,
                                     order_region_from    varchar(4)               null,
                                     order_postcode_to    varchar(50)               null,
                                     order_region_to      varchar(50)               null,
                                     order_cbm            FLOAT8               null,
                                     order_weight         FLOAT8               null,
                                     order_value          FLOAT4               null,
                                     order_distance       FLOAT4               null,
                                     order_type           varchar(50)                null,
                                     order_time           TIMESTAMP            null,
                                     create_time          TIMESTAMP            null,
                                     is_deleted           INT                 null default 0,
                                     is_success           INT                 null default 1,
                                     constraint PK_FREIGHT_BOOKING_LOG primary key (id)
);
comment on column freight_booking_log.connote_number is
    '货运号';
comment on column freight_booking_log.order_ref is
    '订单号';
comment on column freight_booking_log.order_cbm is
    '体积';