-- drop table freight_Freight3plAccount;

/*==============================================================*/
/* Table: freight_Freight3plAccount                             */
/*==============================================================*/
create table freight_Freight3plAccount (
                                           id                   VARCHAR(50)          not null,
                                           warehouse            VARCHAR(20)          null,
                                           company              VARCHAR(50)          null,
                                           customerId           VARCHAR(20)          null,
                                           create_time          TIMESTAMP            null,
                                           is_deleted           INT2                 null,
                                           constraint PK_FREIGHT_FREIGHT3PLACCOUNT primary key (id)
);

comment on column freight_Freight3plAccount.warehouse is
    '仓库';

comment on column freight_Freight3plAccount.company is
    '公司介绍
    ';

comment on column freight_Freight3plAccount.customerId is
    '客户ID';
