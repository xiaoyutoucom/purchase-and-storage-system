-- drop table freight_enum;

/*==============================================================*/
/* Table: freight_enum                                          */
/*==============================================================*/
create table freight_enum (
                              id                   VARCHAR(50)          not null,
                              code                 VARCHAR(20)          null,
                              name                 VARCHAR(500)         null,
                              notes                VARCHAR(50)          null,
                              type                 VARCHAR(20)          null,
                              create_time          TIMESTAMP            null,
                              is_deleted           INT2                 null,
                              constraint PK_FREIGHT_ENUM primary key (id)
);
