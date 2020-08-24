drop table tb_fund_grant_detail;

--==============================================================
-- Table: tb_fund_grant_detail
--==============================================================
create table tb_fund_grant_detail (
   event_detail_id    VARCHAR(32) not null primary key,
   event_main_id      VARCHAR(32),
   tran_seq_sn        VARCHAR(40),
   tran_date          DATE,
   crd_grant_org_num  VARCHAR(12),
   customer_num       VARCHAR(40),
   crd_main_prd       VARCHAR(10),
   crd_currency_cd    VARCHAR(10),
   crd_sum_amt        DECIMAL(24,2),
   crd_begin_date     DATE,
   crd_end_date       DATE,
   busi_segm_amt      DECIMAL(24,2),
   busi_segm_cnt      DECIMAL(5),
   crd_detail_prd     VARCHAR(10),
   crd_detail_amt     DECIMAL(24,2)
);

comment on table tb_fund_grant_detail is
'资金授信事件明细';

comment on column tb_fund_grant_detail.tran_seq_sn is
'交易流水号';

comment on column tb_fund_grant_detail.tran_date is
'交易日期';

comment on column tb_fund_grant_detail.crd_grant_org_num is

'授信机构';
