drop table TB_CSM_INTERBANK;

create table TB_CSM_INTERBANK (
   customer_num       VARCHAR(10)            not null,
   full_name          VARCHAR(300),
   short_name         VARCHAR(100),
   csm_type           VARCHAR(5),
   UNIFY_SOCIETY_CREDIT_NUM VARCHAR(100),
   project_CODE       VARCHAR(5),
   system_flag        VARCHAR(12),
   bank_pay_sys_num   VARCHAR(14),
   organ_level_type   VARCHAR(2),
   ecif_cust_num      VARCHAR(10),
   superior_cust_num  VARCHAR(10),
   user_num           VARCHAR(12),
   org_num            VARCHAR(12),
   UPDATE_TIME          TIMESTAMP,
   CREATE_TIME          TIMESTAMP,
   constraint P_Key_1 primary key (customer_num)
);

comment on table TB_CSM_INTERBANK is
'同业客户基本信息';

comment on column TB_CSM_INTERBANK.customer_num is
'客户编号';

comment on column TB_CSM_INTERBANK.full_name is
'客户全称';

comment on column TB_CSM_INTERBANK.short_name is
'客户简称';

comment on column TB_CSM_INTERBANK.csm_type is
'客户类型';

comment on column TB_CSM_INTERBANK.UNIFY_SOCIETY_CREDIT_NUM is
'组织机构代码';

comment on column TB_CSM_INTERBANK.project_CODE is
'项目代码';

comment on column TB_CSM_INTERBANK.system_flag is
'系统标识';

comment on column TB_CSM_INTERBANK.bank_pay_sys_num is
'人行支付清算行号';

comment on column TB_CSM_INTERBANK.organ_level_type is
'机构层级类型';

comment on column TB_CSM_INTERBANK.ecif_cust_num is
'ECIF客户号';

comment on column TB_CSM_INTERBANK.superior_cust_num is
'上级机构ECIF客户号';

comment on column TB_CSM_INTERBANK.user_num is
'经办人';

comment on column TB_CSM_INTERBANK.org_num is
'经办机构';

comment on column TB_CSM_INTERBANK.UPDATE_TIME is
'更新时间';

comment on column TB_CSM_INTERBANK.CREATE_TIME is
'创建时间';

