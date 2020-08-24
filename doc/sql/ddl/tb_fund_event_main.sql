drop table tb_fund_event_main;

--==============================================================
-- Table: tb_fund_event_main
--==============================================================
create table tb_fund_event_main (
   event_main_id      VARCHAR(32) NOT NULL PRIMARY KEY,
   tran_seq_sn        VARCHAR(40),
   tran_date          VARCHAR(10),
   busi_deal_num      VARCHAR(40),
   tran_type_cd       VARCHAR(2),
   crd_apply_amt      DECIMAL(24,2),
   tran_event_status  VARCHAR(2),
   tran_event_info    VARCHAR(200),
   tran_acct_status   VARCHAR(2),
   crd_currency_cd    VARCHAR(10)
);

comment on table tb_fund_event_main is
'资金用信事件主表';

comment on column tb_fund_event_main.event_main_id is
'事件主表id';

comment on column tb_fund_event_main.tran_seq_sn is
'应能唯一确认交易';

comment on column tb_fund_event_main.tran_date is
'交易日期';

comment on column tb_fund_event_main.busi_deal_num is
'业务编号 应能唯一确认业务';

comment on column tb_fund_event_main.tran_type_cd is
'交易类型 03 占用 04 占用撤销 05 恢复（到期恢复时用票号） 06 恢复撤销';

comment on column tb_fund_event_main.crd_apply_amt is
'用信金额';

comment on column tb_fund_event_main.tran_event_status is
'本方交易状态 0 未处理  1 处理成功 2 处理失败 ';

comment on column tb_fund_event_main.tran_event_info is
'事件处理信息';

comment on column tb_fund_event_main.tran_acct_status is
'对方对账状态 0 未处理  1 处理成功 2 处理失败';

comment on column tb_fund_event_main.crd_currency_cd is
'币种(CD000019)';
