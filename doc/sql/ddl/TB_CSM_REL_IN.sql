drop table TB_CSM_REL_IN;

create table TB_CSM_REL_IN (
   ID                   VARCHAR(32)            not null,
   customer_num       VARCHAR(10)            not null,
   REL_customer_num   VARCHAR(32),
   REL_TYPE             VARCHAR(3),
   IN_SRC               VARCHAR(5),
   CREATE_TIME          TIMESTAMP,
   UPDATE_TIME          TIMESTAMP,
   constraint P_Key_1 primary key (ID)
);

comment on table TB_CSM_REL_IN is
'客户行内关联关系';

comment on column TB_CSM_REL_IN.ID is
'主键ID,唯一识别标识';

comment on column TB_CSM_REL_IN.customer_num is
'客户编号';

comment on column TB_CSM_REL_IN.REL_customer_num is
'关联人的客户编号';

comment on column TB_CSM_REL_IN.REL_TYPE is
'关系类型(CD000016)';

comment on column TB_CSM_REL_IN.IN_SRC is
'来源系统';

comment on column TB_CSM_REL_IN.CREATE_TIME is
'创建时间';

comment on column TB_CSM_REL_IN.UPDATE_TIME is
'更新时间';
