
drop table tb_crd_detail;

--==============================================================
-- Table: tb_crd_detail
--==============================================================
create table tb_crd_detail (
   crd_detail_num     VARCHAR(32)            not null,
   crd_main_num       VARCHAR(40),
   crd_detail_prd     VARCHAR(10) not null,
   crd_grant_org_num  VARCHAR(12) not null,
   customer_num       VARCHAR(40) not null,
   crd_admit_flag     VARCHAR(10),
   currency_cd        VARCHAR(10),
   exchange_rate      DECIMAL(24,2),
   begin_date         VARCHAR(10),
   end_date           VARCHAR(10),
   limit_credit       DECIMAL(24,2),
   limit_avi          DECIMAL(24,2),
   limit_used         DECIMAL(24,2),
   exp_credit         DECIMAL(24,2),
   exp_used           DECIMAL(24,2),
   exp_avi            DECIMAL(24,2),
   limit_pre          DECIMAL(24,2),
   exp_pre            DECIMAL(24,2),
   limit_earmark       DECIMAL(24,2),
   earmark_begin_date       VARCHAR(10),
   earmark_end_date       VARCHAR(10),
   limit_frozen       DECIMAL(24,2),
   exp_frozen        DECIMAL(24,2),
   is_cycle          VARCHAR(1),
   is_mix             VARCHAR(1),
   mix_credit        DECIMAL(24,2),
   mix_used           DECIMAL(24,2),
   mixremark          VARCHAR(250),
   close_date         VARCHAR(10),
   close_reason       VARCHAR(250),
   is_continue        VARCHAR(1),
   tran_system        VARCHAR(10),
   user_num           VARCHAR(12),
   org_num            VARCHAR(12),
   create_time        TIMESTAMP,
   update_time        TIMESTAMP,
   constraint P_Key_1 primary key (crd_detail_num)
);

comment on table tb_crd_detail is
'额度明细表（客户+三级额度产品+机构）';

comment on column tb_crd_detail.crd_detail_num is
'三级额度编号';

comment on column tb_crd_detail.crd_main_num is
'二级额度编号';

comment on column tb_crd_detail.crd_detail_prd is
'三级额度品种';

comment on column tb_crd_detail.crd_grant_org_num is
'授信机构号';

comment on column tb_crd_detail.customer_num is
'客户编号';

comment on column tb_crd_detail.crd_admit_flag is
'客户准入状态 0：禁止 1：准入';

comment on column tb_crd_detail.currency_cd is
'币种';

comment on column tb_crd_detail.exchange_rate is
'汇率';

comment on column tb_crd_detail.begin_date is
'额度起期';

comment on column tb_crd_detail.end_date is
'额度止期';

comment on column tb_crd_detail.limit_credit is
'授信额度';

comment on column tb_crd_detail.limit_avi is
'可用额度';

comment on column tb_crd_detail.limit_used is
'已用额度';

comment on column tb_crd_detail.exp_credit is
'授信敞口';

comment on column tb_crd_detail.exp_used is
'已用敞口';

comment on column tb_crd_detail.exp_avi is
'可用敞口';

comment on column tb_crd_detail.limit_pre is
'已用额度';

comment on column tb_crd_detail.exp_pre is
'预占用敞口';

comment on column tb_crd_detail.limit_earmark is
'可用额度';

comment on column tb_crd_detail.earmark_begin_date is
'圈存开始日';

comment on column tb_crd_detail.earmark_end_date is
'圈存到期日';

comment on column tb_crd_detail.limit_frozen is
'冻结额度';

comment on column tb_crd_detail.exp_frozen is
'冻结敞口';

comment on column tb_crd_detail.is_cycle is
'是否可循环';

comment on column tb_crd_detail.is_mix is
'是否可串用';

comment on column tb_crd_detail.mixremark is
'串用说明';

comment on column tb_crd_detail.create_time is
'创建时间';

comment on column tb_crd_detail.update_time is
'修改时间';

comment on column tb_crd_detail.close_date is
'终结日期';

comment on column tb_crd_detail.close_reason is
'终结原因';

comment on column tb_crd_detail.is_continue is
'续作标志';

comment on column tb_crd_detail.user_num is
'经办人';

comment on column tb_crd_detail.org_num is
'经办机构';

comment on column tb_crd_detail.tran_system is
'经办系统';


alter table tb_crd_detail
        add constraint uq_crd_detail unique(crd_detail_prd,crd_grant_org_num,customer_num);
