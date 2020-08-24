drop table tb_fund_transfer_out;

--==============================================================
-- table: tb_fund_transfer_out
--==============================================================
create table tb_fund_transfer_out (
   transfer_out_id    varchar(32)            not null,
   event_main_id      varchar(32)            not null,
   tran_seq_sn        varchar(10)            not null,
   tran_date            varchar(10),
   crd_out_org_num    varchar(12),
   busi_source_req_num varchar(40),
   currency_cd        varchar(3),
   crd_apply_out_amt    decimal(24, 2),
   constraint p_key_1 primary key (transfer_out_id)
);

comment on table tb_fund_transfer_out is
'资金系统额度转让-转出';

comment on column tb_fund_transfer_out.transfer_out_id is
'资金系统额度转让-转出id';

comment on column tb_fund_transfer_out.event_main_id is
'事件主表id';

comment on column tb_fund_transfer_out.tran_seq_sn is
'交易流水号';

comment on column tb_fund_transfer_out.tran_date is
'交易日期';

comment on column tb_fund_transfer_out.crd_out_org_num is
'转出机构';

comment on column tb_fund_transfer_out.busi_source_req_num
is
'业务编号';

comment on column tb_fund_transfer_out.currency_cd is
'币种';

comment on column tb_fund_transfer_out.crd_apply_out_amt is
'转出金额';
