drop table tb_fund_grant_main;

--==============================================================
-- Table: tb_fund_grant_main
--==============================================================
create table tb_fund_grant_main (
   event_main_id      VARCHAR(32) not null primary key,
   tran_seq_sn        VARCHAR(40),
   tran_date          DATE,
   busi_deal_num      VARCHAR(40),
   tran_type_cd       VARCHAR(2),
   crd_apply_amt      DECIMAL(24,2),
   tran_event_status  VARCHAR(2),
   tran_event_info    VARCHAR(200),
   tran_acct_status   VARCHAR(2),
   CRD_currency_cd    VARCHAR(10)
);

comment on table tb_fund_grant_main is
'资金授信主事件表';

comment on column tb_fund_grant_main.event_main_id is
'事件主表id';

comment on column tb_fund_grant_main.tran_seq_sn is
'交易流水号 应能唯一确认交易';

comment on column tb_fund_grant_main.tran_date is
'交易日期';

comment on column tb_fund_grant_main.busi_deal_num is
'业务编号 应能唯一确认业务';

comment on column tb_fund_grant_main.tran_type_cd is
'交易类型 01 圈存';

comment on column tb_fund_grant_main.crd_apply_amt is
'交易金额';

comment on column tb_fund_grant_main.tran_event_status is
'本方交易状态 0 未处理  1 处理成功 2 处理失败 ';

comment on column tb_fund_grant_main.tran_event_info is
'事件处理信息';

comment on column tb_fund_grant_main.tran_acct_status is
'对方对账状态 0未处理  1 处理成功 2 处理失败';

comment on column tb_fund_grant_main.CRD_currency_cd is
'币种(CD000019)';
