drop table TB_CSM_MANAGE_TEAM;

create table TB_CSM_MANAGE_TEAM (
   UUID                 VARCHAR(32)            not null,
   customer_num       VARCHAR(10)            not null,
   USER_NUM             VARCHAR(10),
   ORG_NUM              VARCHAR(10),
   USER_PLACING_CD      VARCHAR(6),
   UPDATE_TIME          TIMESTAMP,
   CREATE_TIME          TIMESTAMP,
   constraint P_Key_1 primary key (UUID)
);

comment on table TB_CSM_MANAGE_TEAM is
'管理团队';

comment on column TB_CSM_MANAGE_TEAM.UUID is
'客户经理小组成员的唯一标识，一个用户的一个角色可能对应多个该ID(属于多个客户管理团队)';

comment on column TB_CSM_MANAGE_TEAM.customer_num is
'客户编号';

comment on column TB_CSM_MANAGE_TEAM.USER_NUM is
'经办用户';

comment on column TB_CSM_MANAGE_TEAM.ORG_NUM is
'经办机构';

comment on column TB_CSM_MANAGE_TEAM.USER_PLACING_CD is
'权限类型';

comment on column TB_CSM_MANAGE_TEAM.UPDATE_TIME is
'更新时间';

comment on column TB_CSM_MANAGE_TEAM.CREATE_TIME is
'创建时间';

