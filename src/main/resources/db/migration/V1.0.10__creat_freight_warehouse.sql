-- drop table freight_warehouse;

/*==============================================================*/
/* Table: freight_warehouse                                     */
/*==============================================================*/
create table freight_warehouse
(
    id          VARCHAR(50) not null,
    warehouse   VARCHAR(20) null,
    code        VARCHAR(50) null,
    shortCode   VARCHAR(20) null,
    addressLine VARCHAR(50) null,
    suburb      VARCHAR(20) null,
    city        VARCHAR(20) null,
    postcode    VARCHAR(20) null,
    lon         VARCHAR(50) null,
    lat         VARCHAR(50) null,
    phoneNumber VARCHAR(20) null,
    create_time TIMESTAMP   null,
    is_deleted  INT2        null,
    constraint PK_FREIGHT_WAREHOUSE primary key (id)
);

