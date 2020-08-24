drop table TB_CSM_GUAR_MEMEBER;

create table TB_CSM_GUAR_MEMEBER (
   ID                   VARCHAR(32)            not null,
   customer_num       VARCHAR(10)            not null,
   MEMBER_customer_num VARCHAR(32)            not null,
   STATUS               VARCHAR(20),
   CREATE_TIME          TIMESTAMP,
   UPDATE_TIME          TIMESTAMP,
   constraint P_Key_1 primary key (ID)
);

comment on table TB_CSM_GUAR_MEMEBER is
'联保小组成员';

comment on column TB_CSM_GUAR_MEMEBER.ID is
'主键';

comment on column TB_CSM_GUAR_MEMEBER.customer_num is
'客户编号';

comment on column TB_CSM_GUAR_MEMEBER.MEMBER_customer_num is
'成员客户编号';

comment on column TB_CSM_GUAR_MEMEBER.STATUS is
'成员状态';

comment on column TB_CSM_GUAR_MEMEBER.CREATE_TIME is
'创建时间';

comment on column TB_CSM_GUAR_MEMEBER.UPDATE_TIME is
'更新时间';
