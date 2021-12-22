-- drop table freight_user;

/*==============================================================*/
/* Table: freight_user                                          */
/*==============================================================*/
create table freight_user (
                              id                   VARCHAR(50)          not null,
                              username             VARCHAR(50)          null,
                              password             VARCHAR(50)          null,
                              accountId            VARCHAR(50)          null,
                              role                 VARCHAR(50)          null,
                              create_time          TIMESTAMP            null,
                              is_deleted           INT2                 null,
                              constraint PK_FREIGHT_USER primary key (id)
);

comment on column freight_user.username is
    '用户名';
INSERT INTO public.freight_user(
    id, username, password)
VALUES (1, 'tradedepot_test', 'tradedepot');