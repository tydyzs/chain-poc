
drop table TB_CSM_PARTY;

create table TB_CSM_PARTY (
   customer_num       VARCHAR(10)            not null,
   is_bank_rel        VARCHAR(20),
   PARTY_TYPE_CD        VARCHAR(2),
   PARTY_NAME           VARCHAR(200),
   IF_DATA_MOVE         VARCHAR(5),
   GRADE                VARCHAR(10),
   tran_system        VARCHAR(12),
   CREATE_TIME          TIMESTAMP,
   UPDATE_TIME          TIMESTAMP,
   constraint P_Key_1 primary key (customer_num)
);

comment on table TB_CSM_PARTY is
'客户主表';

comment on column TB_CSM_PARTY.customer_num is
'客户编号';

comment on column TB_CSM_PARTY.is_bank_rel is
'是否我行关联方(:YesOrNo)';

comment on column TB_CSM_PARTY.PARTY_TYPE_CD is
'参与人类型(CD000033)';

comment on column TB_CSM_PARTY.PARTY_NAME is
'客户名称';

comment on column TB_CSM_PARTY.IF_DATA_MOVE is
'是否迁移数据';

comment on column TB_CSM_PARTY.GRADE is
'评级信息';

comment on column TB_CSM_PARTY.tran_system is
'经办系统';

comment on column TB_CSM_PARTY.CREATE_TIME is
'创建时间';

comment on column TB_CSM_PARTY.UPDATE_TIME is
'更新时间';
