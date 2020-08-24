drop table TB_CSM_GROUP_MEMBER;

--==============================================================
-- Table: TB_CSM_GROUP_MEMBER
--==============================================================
create table TB_CSM_GROUP_MEMBER (
   ID                   VARCHAR(32)            not null,
   customer_num       VARCHAR(10)            not null,
   MEMBER_customer_num VARCHAR(32)            not null,
   STATUS               VARCHAR(6),
   UPDATE_TIME          TIMESTAMP,
   CREATE_TIME          TIMESTAMP,
   constraint P_Key_1 primary key (ID)
);

comment on table TB_CSM_GROUP_MEMBER is
'集团成员表';

comment on column TB_CSM_GROUP_MEMBER.ID is
'主键ID';

comment on column TB_CSM_GROUP_MEMBER.customer_num is
'客户编号';

comment on column TB_CSM_GROUP_MEMBER.MEMBER_customer_num is
'成员客户编号';

comment on column TB_CSM_GROUP_MEMBER.STATUS is
'关联关系状态';

comment on column TB_CSM_GROUP_MEMBER.UPDATE_TIME is
'更新时间';

comment on column TB_CSM_GROUP_MEMBER.CREATE_TIME is
'创建时间';
