drop table TB_CSM_ADDRESS_INFO;

create table TB_CSM_ADDRESS_INFO (
   ID                   VARCHAR(32)            not null,
   customer_num       VARCHAR(10)            not null,
   CONN_TYPE            VARCHAR(3),
   COUN_REGI            VARCHAR(3),
   PROVINCE             VARCHAR(6),
   CITY                 VARCHAR(6),
   COUNTY               VARCHAR(6),
   STREET               VARCHAR(90),
   DETAIL_ADDR          VARCHAR(256),
   ENG_ADDR             VARCHAR(256),
   link_name          VARCHAR(50),
   link_tel           VARCHAR(50),
   CREATE_TIME          TIMESTAMP,
   UPDATE_TIME          TIMESTAMP,
   constraint P_Key_1 primary key (ID)
);

comment on table TB_CSM_ADDRESS_INFO is
'地址信息';

comment on column TB_CSM_ADDRESS_INFO.ID is
'主键ID';

comment on column TB_CSM_ADDRESS_INFO.customer_num is
'客户编号';

comment on column TB_CSM_ADDRESS_INFO.CONN_TYPE is
'地址类型(CD000031)';

comment on column TB_CSM_ADDRESS_INFO.COUN_REGI is
'国家和地区代码(CD000001)';

comment on column TB_CSM_ADDRESS_INFO.PROVINCE is
'省代码(CD000002)';

comment on column TB_CSM_ADDRESS_INFO.CITY is
'市代码(CD000002)';

comment on column TB_CSM_ADDRESS_INFO.COUNTY is
'县代码(CD000002)';

comment on column TB_CSM_ADDRESS_INFO.STREET is
'街道地址';

comment on column TB_CSM_ADDRESS_INFO.DETAIL_ADDR is
'详细地址';

comment on column TB_CSM_ADDRESS_INFO.ENG_ADDR is
'英文地址';

comment on column TB_CSM_ADDRESS_INFO.link_name is
'联系人名称';

comment on column TB_CSM_ADDRESS_INFO.link_tel is
'联系人电话';

comment on column TB_CSM_ADDRESS_INFO.CREATE_TIME is
'创建时间';

comment on column TB_CSM_ADDRESS_INFO.UPDATE_TIME is
'更新时间';

