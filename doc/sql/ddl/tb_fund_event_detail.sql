drop table tb_fund_event_detail;

--==============================================================
-- Table: tb_fund_event_detail
--==============================================================
create table tb_fund_event_detail (
   event_detailed_id  VARCHAR(32)            not null primary  key,
   event_main_id      VARCHAR(32) NOT NULL,
   tran_seq_sn        VARCHAR(40)            not null,
   tran_date          VARCHAR(10)            not null,
   crd_grant_org_num  VARCHAR(12),
   customer_num       VARCHAR(40)            not null,
   crd_detail_prd     VARCHAR(10)            not null,
   busi_deal_num      VARCHAR(40)            not null,
   busi_prd_num       VARCHAR(10),
   busi_deal_desc     VARCHAR(50),
   busi_deal_org_num  VARCHAR(40),
   busi_deal_org_name VARCHAR(100),
   busi_oppt_org_num  VARCHAR(40),
   busi_oppt_org_name VARCHAR(100),
   busi_sum_amt       DECIMAL(24,2),
   busi_cert_cnt      DECIMAL(24,0),
   cert_num           VARCHAR(40)            not null,
   cert_type_cd       VARCHAR(40),
   cert_ppt_cd        VARCHAR(40),
   cert_interest_period VARCHAR(40),
   cert_interest_rate DECIMAL(24,2),
   cert_currency_cd   VARCHAR(10),
   cert_seq_amt       DECIMAL(24,2),
   cert_apply_amt     DECIMAL(24,2),
   cert_apply_balance DECIMAL(24,2),
   cert_status        VARCHAR(10),
   cert_begin_date    VARCHAR(10),
   cert_end_date      VARCHAR(10),
   cert_finish_date   VARCHAR(10),
   cert_drawer_cust_num VARCHAR(50),
   cert_drawer_name   VARCHAR(200),
   cert_drawer_bank_num VARCHAR(50),
   cert_drawer_bank_name VARCHAR(200),
   cert_guaranty_type VARCHAR(40),
   cert_guaranty_person VARCHAR(40),
   cert_busi_remark   VARCHAR(200)
);

comment on table tb_fund_event_detail is
'资金用信事件明细';

comment on column tb_fund_event_detail.event_detailed_id is
'事件明细id';

comment on column tb_fund_event_detail.event_main_id is
'事件主表id';

comment on column tb_fund_event_detail.tran_seq_sn is
'交易流水号';

comment on column tb_fund_event_detail.tran_date is
'交易日期';

comment on column tb_fund_event_detail.crd_grant_org_num is
'授信机构';

comment on column tb_fund_event_detail.customer_num is
'用信客户号';

comment on column tb_fund_event_detail.crd_detail_prd is
'明细额度产品';

comment on column tb_fund_event_detail.busi_deal_num is
'业务编号';

comment on column tb_fund_event_detail.busi_prd_num is
'业务产品编号';

comment on column tb_fund_event_detail.busi_deal_desc is
'业务交易描述';

comment on column tb_fund_event_detail.busi_deal_org_num is
'本方机构';

comment on column tb_fund_event_detail.busi_deal_org_name is
'本方机构名称';

comment on column tb_fund_event_detail.busi_oppt_org_num is
'对手机构';

comment on column tb_fund_event_detail.busi_oppt_org_name is
'对手机构名称';

comment on column tb_fund_event_detail.busi_sum_amt is
'交易总金额';

comment on column tb_fund_event_detail.busi_cert_cnt is
'凭证张数';

comment on column tb_fund_event_detail.cert_num is
'凭证编号';

comment on column tb_fund_event_detail.cert_type_cd is
'凭证品种';

comment on column tb_fund_event_detail.cert_ppt_cd is
'凭证性质';

comment on column tb_fund_event_detail.cert_interest_period is
'凭证计息期限';

comment on column tb_fund_event_detail.cert_interest_rate is
'收益率/利率';

comment on column tb_fund_event_detail.cert_currency_cd is
'币种';

comment on column tb_fund_event_detail.cert_seq_amt is
'凭证原始金额';

comment on column tb_fund_event_detail.cert_apply_amt is
'凭证用信金额';

comment on column tb_fund_event_detail.cert_apply_balance is
'凭证用信余额';

comment on column tb_fund_event_detail.cert_status is
'凭证状态';

comment on column tb_fund_event_detail.cert_begin_date is
'凭证起期';

comment on column tb_fund_event_detail.cert_end_date is
'凭证止期';

comment on column tb_fund_event_detail.cert_finish_date is
'凭证结清日期';

comment on column tb_fund_event_detail.cert_drawer_cust_num is
'发行人客户号';

comment on column tb_fund_event_detail.cert_drawer_name is
'发行人客户名称';

comment on column tb_fund_event_detail.cert_drawer_bank_num is
'发行人代理/承兑行号';

comment on column tb_fund_event_detail.cert_drawer_bank_name is
'发行人代理/承兑行名';

comment on column tb_fund_event_detail.cert_guaranty_type is
'担保方式';

comment on column tb_fund_event_detail.cert_guaranty_person is
'担保人';

comment on column tb_fund_event_detail.cert_busi_remark is
'备注信息';
