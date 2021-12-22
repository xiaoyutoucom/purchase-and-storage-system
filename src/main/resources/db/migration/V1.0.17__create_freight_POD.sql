/*==============================================================*/
/* Table: freight_POD                                           */
/*==============================================================*/
create table freight_POD (
                             id                   VARCHAR(50)          not null,
                             trackingNo           VARCHAR(50)          null,
                             RefNo                VARCHAR(50)          null,
                             create_time          TIMESTAMP            null,
                             is_deleted           INT2                 null,
                             relatedItemUrl       VARCHAR(1000)          null,
                             returnbody          VARCHAR(100000)          null,
                             constraint PK_FREIGHT_POD primary key (id)
);

comment on column freight_POD.trackingNo is
    'CONSIGNMENT';

comment on column freight_POD.RefNo is
    'TICKET';

comment on column freight_POD.create_time is
    'CREATEDON';