drop table tb_crd_apply_serial;

--==============================================================
-- Table: tb_crd_apply_serial
--==============================================================
create table tb_crd_apply_serial (
   serial_id          VARCHAR(32)            not null,
   tran_seq_sn        VARCHAR(40)            not null,
   tran_date          VARCHAR(10),
   busi_deal_num      VARCHAR(40),
   tran_type_cd       VARCHAR(10),
   crd_detail_num     VARCHAR(32),
   crd_grant_org_num  VARCHAR(12),
   customer_num       VARCHAR(40)            not null,
   crd_detail_prd     VARCHAR(10),
   limit_credit_amt   DECIMAL(24, 2),
   exp_credit_amt     DECIMAL(24, 2),
   currency_cd        VARCHAR(10),
   is_mix             VARCHAR(1),
   mix_credit         DECIMAL(24, 2),
   tran_system        VARCHAR(12),
   org_num            VARCHAR(12),
   user_num           VARCHAR(12),
   create_time        TIMESTAMP,
   update_time        TIMESTAMP,
   constraint P_Key_1 primary key (serial_id)
);

comment on table tb_crd_apply_serial is
'额度使用流水';

comment on column tb_crd_apply_serial.serial_id is
'主键';

comment on column tb_crd_apply_serial.tran_seq_sn is
'交易流水号';

comment on column tb_crd_apply_serial.tran_date is
'交易日期';

comment on column tb_crd_apply_serial.busi_deal_num is
'业务编号（存放不同的业务场景下的业务编号）';

comment on column tb_crd_apply_serial.tran_type_cd is
'交易类型(CD000197)';

comment on column tb_crd_apply_serial.crd_detail_num is
'三级额度编号';

comment on column tb_crd_apply_serial.crd_grant_org_num is
'授信机构';

comment on column tb_crd_apply_serial.customer_num is
'用信客户号';

comment on column tb_crd_apply_serial.crd_detail_prd is
'三级额度产品';

comment on column tb_crd_apply_serial.limit_credit_amt is
'占用/恢复额度金额';

comment on column tb_crd_apply_serial.exp_credit_amt is
'占用/恢复敞口金额';

comment on column tb_crd_apply_serial.currency_cd is
'币种';

comment on column tb_crd_apply_serial.is_mix is
'是否串用';

comment on column tb_crd_apply_serial.mix_credit is
'串用额度';

comment on column tb_crd_apply_serial.tran_system is
'经办系统';

comment on column tb_crd_apply_serial.org_num is
'经办机构';

comment on column tb_crd_apply_serial.user_num is
'经办人';

comment on column tb_crd_apply_serial.create_time is
'创建时间';

comment on column tb_crd_apply_serial.update_time is
'修改时间';
