drop table TB_CSM_GUAR_GROUP;

create table TB_CSM_GUAR_GROUP (
   customer_num       VARCHAR(10)            not null,
   JOINT_GUARANTEE_TYPE VARCHAR(6),
   MANAGE_WAY           VARCHAR(30),
   JOINT_GUARANTEE_STATUS VARCHAR(20),
   IS_DIF_ORG_GUARANTEE VARCHAR(1),
   CREATE_DATE          TIMESTAMP,
   UPDATE_DATE          TIMESTAMP,
   constraint P_Key_1 primary key (customer_num)
);

comment on table TB_CSM_GUAR_GROUP is
'联保小组';

comment on column TB_CSM_GUAR_GROUP.customer_num is
'客户编号';

comment on column TB_CSM_GUAR_GROUP.JOINT_GUARANTEE_TYPE is
'联保小组类型(CD000083';

comment on column TB_CSM_GUAR_GROUP.MANAGE_WAY is
'职保小组管理方式';

comment on column TB_CSM_GUAR_GROUP.JOINT_GUARANTEE_STATUS is
'联保小组状态(CD000084';

comment on column TB_CSM_GUAR_GROUP.IS_DIF_ORG_GUARANTEE is
'是否跨机构联保：(是：1，否：0)';

comment on column TB_CSM_GUAR_GROUP.CREATE_DATE is
'创建时间';

comment on column TB_CSM_GUAR_GROUP.UPDATE_DATE is
'更新时间';
