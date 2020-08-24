drop table tb_crd_granting_serial;

--==============================================================
-- Table: tb_crd_granting_serial
--==============================================================
create table tb_crd_granting_serial (
   granting_serial_id VARCHAR(32)            not null,
   tran_seq_sn        VARCHAR(32)            not null,
   tran_date          VARCHAR(10),
   busi_deal_num      VARCHAR(12),
   tran_type_cd       VARCHAR(2),
   crd_grant_org_num  VARCHAR(12),
   customer_num       VARCHAR(40),
   crd_detail_prd     VARCHAR(10),
   crd_detail_num     VARCHAR(40)            not null,
   crd_currency_cd    VARCHAR(10),
   crd_detail_amt     DECIMAL(24,2),
   crd_eark_amt       DECIMAL(24,2),
   crd_begin_date     VARCHAR(10),
   crd_end_date       VARCHAR(10),
   crd_status         VARCHAR(2),
   crd_admit_flag     VARCHAR(2),
   deal_status        VARCHAR(2),
   tran_system        VARCHAR(12),
   user_num           VARCHAR(12),
   create_time        TIMESTAMP,
   update_time        TIMESTAMP,
   constraint P_Key_1 primary key (granting_serial_id)
);

comment on table tb_crd_granting_serial is
'额度授信流水';

comment on column tb_crd_granting_serial.granting_serial_id is
'授信流水id';

comment on column tb_crd_granting_serial.tran_seq_sn is
'交易流水号';

comment on column tb_crd_granting_serial.tran_date is
'交易日期';

comment on column tb_crd_granting_serial.busi_deal_num is
'业务编号';

comment on column tb_crd_granting_serial.tran_type_cd is
'01 维护综合额度
02 维护切分额度
03 新增额度圈存
04 额度圈存维护
05 客户额度冻结
06 客户准入调整';

comment on column tb_crd_granting_serial.crd_grant_org_num is
'经办机构';

comment on column tb_crd_granting_serial.customer_num is
'客户编号';

comment on column tb_crd_granting_serial.crd_detail_prd is
'额度产品号';

comment on column tb_crd_granting_serial.crd_detail_num is
'额度编号';

comment on column tb_crd_granting_serial.crd_currency_cd is
'币种';

comment on column tb_crd_granting_serial.crd_detail_amt is
'授信额度';

comment on column tb_crd_granting_serial.crd_eark_amt is
'圈存额度';

comment on column tb_crd_granting_serial.crd_begin_date is
'额度起期';

comment on column tb_crd_granting_serial.crd_end_date is
'额度止期';

comment on column tb_crd_granting_serial.crd_status is
'01 正常 02 冻结';

comment on column tb_crd_granting_serial.crd_admit_flag is
'0：禁止1：准入';

comment on column tb_crd_granting_serial.deal_status is
'交易状态';

comment on column tb_crd_granting_serial.tran_system is
'经办系统';

comment on column tb_crd_granting_serial.user_num is
'经办人';

comment on column tb_crd_granting_serial.create_time is
'创建时间';

comment on column tb_crd_granting_serial.update_time is
'修改时间';
