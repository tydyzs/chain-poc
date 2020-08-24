drop table tb_crd_main;

--==============================================================
-- Table: tb_crd_main
--==============================================================
create table tb_crd_main (
   crd_main_num       VARCHAR(40)            not null,
   crd_main_prd       VARCHAR(10) not null,
   crd_grant_org_num  VARCHAR(12) not null,
   customer_num       VARCHAR(40) not null,
   currency_cd        VARCHAR(10),
   exchange_rate      DECIMAL(24,2),
   credit_status      VARCHAR(10),
   limit_credit       DECIMAL(24,2),
   limit_used         DECIMAL(24,2),
   limit_avi          DECIMAL(24,2),
   exp_credit         DECIMAL(24,2),
   exp_used           DECIMAL(24,2),
   exp_avi            DECIMAL(24,2),
   limit_pre          DECIMAL(24,2),
   exp_pre            DECIMAL(24,2),
   limit_frozen       DECIMAL(24,2),
   exp_frozen        DECIMAL(24,2),
   begin_date         VARCHAR(10),
   end_date           VARCHAR(10),
   frozen_date        VARCHAR(10),
   over_date          VARCHAR(10),
   tran_system        VARCHAR(10),
   org_num            VARCHAR(12),
   user_num           VARCHAR(12),
   create_time        TIMESTAMP,
   update_time        TIMESTAMP,
   constraint P_Key_1 primary key (crd_main_num)
);

comment on table tb_crd_main is
'额度主表（客户+二级额度产品+机构）';

comment on column tb_crd_main.crd_main_num is
'额度编号';

comment on column tb_crd_main.crd_main_prd is
'额度产品编号';

comment on column tb_crd_main.crd_grant_org_num is
'授信机构';

comment on column tb_crd_main.customer_num is
'客户编号';

comment on column tb_crd_main.currency_cd is
'币种';

comment on column tb_crd_main.exchange_rate is
'汇率';

comment on column tb_crd_main.credit_status is
'额度状态';

comment on column tb_crd_main.limit_credit is
'授信额度';

comment on column tb_crd_main.limit_used is
'已用额度';

comment on column tb_crd_main.limit_avi is
'可用额度';

comment on column tb_crd_main.exp_credit is
'授信敞口';

comment on column tb_crd_main.exp_used is
'已用敞口';

comment on column tb_crd_main.exp_avi is
'可用敞口';

comment on column tb_crd_main.limit_pre is
'已用额度';

comment on column tb_crd_main.exp_pre is
'预占用敞口';

comment on column tb_crd_main.limit_frozen is
'冻结额度';

comment on column tb_crd_main.exp_frozen is
'冻结敞口';

comment on column tb_crd_main.begin_date is
'额度生效日';

comment on column tb_crd_main.end_date is
'额度到期日';

comment on column tb_crd_main.frozen_date is
'冻结日期';

comment on column tb_crd_main.over_date is
'终止日期';

comment on column tb_crd_main.create_time is
'创建时间';

comment on column tb_crd_main.update_time is
'修改时间';

comment on column tb_crd_main.org_num is
'经办机构';

comment on column tb_crd_main.user_num is
'经办人';

comment on column tb_crd_main.tran_system is
'经办系统';


alter table tb_crd_main
        add constraint uq_crd_main unique(crd_main_prd,crd_grant_org_num,customer_num);
