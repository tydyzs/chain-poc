
--票据用信事件主表
drop table tb_bill_event_main;


create table tb_bill_event_main (
   event_main_id      VARCHAR(32) not null,
   tran_seq_sn        VARCHAR(40),
   tran_date          DATE,
   busi_deal_num  VARCHAR(40),
   tran_type_cd       VARCHAR(2),
   cert_currency_cd  VARCHAR(10),
   crd_apply_amt      DECIMAL(18,2),
   tran_event_status  VARCHAR(2),
   tran_event_info    VARCHAR(200),
   tran_acct_status   VARCHAR(2),
   constraint P_Key_1 primary key (event_main_id)
);

comment on table tb_bill_event_main is
'票据用信事件主表';

comment on column tb_bill_event_main.event_main_id is
'事件主表id';

comment on column tb_bill_event_main.tran_seq_sn is
'交易流水号';

comment on column tb_bill_event_main.tran_date is
'交易日期';

comment on column tb_bill_event_main.busi_deal_num is
'业务编号';

comment on column tb_bill_event_main.cert_currency_cd is
'币种';

comment on column tb_bill_event_main.tran_type_cd is
'交易类型（03 占用，04 占用撤销，05 恢复（到期恢复时用票号），06 恢复撤销）';

comment on column tb_bill_event_main.crd_apply_amt is
'用信金额';

comment on column tb_bill_event_main.tran_event_status is
'本方交易状态（0 未处理  1 处理成功 2 处理失败 ）';

comment on column tb_bill_event_main.tran_event_info is
'事件处理信息';

comment on column tb_bill_event_main.tran_acct_status is
'对方对账状态（0 未处理  1 处理成功 2 处理失败）';
