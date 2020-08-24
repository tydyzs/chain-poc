drop table tb_fund_transfer_in;

--==============================================================
-- table: tb_fund_transfer_in
--==============================================================
create table tb_fund_transfer_in (
   transfer_in_id     varchar(32)            not null,
   event_main_id    varchar(32)            not null,
   tran_seq_sn        varchar(40),
   tran_date            varchar(10),
   crd_in_org_num     varchar(12),
   busi_prd_num       varchar(3),
   busi_newl_req_num  varchar(40),
   currency_cd        varchar(3),
   crd_apply_in_amt     decimal(24, 2),
   constraint p_key_1 primary key (transfer_in_id)
);

comment on table tb_fund_transfer_in is
'资金额度转让-转入';

comment on column tb_fund_transfer_in.transfer_in_id is
'资金系统额度转让-转入id';

comment on column tb_fund_transfer_in.event_main_id is
'资金系统额度转让事件主表id';

comment on column tb_fund_transfer_in.tran_seq_sn is
'交易流水号';

comment on column tb_fund_transfer_in.tran_date is
'交易日期';

comment on column tb_fund_transfer_in.crd_in_org_num is
'转入机构(成员行)';

comment on column tb_fund_transfer_in.busi_prd_num is
'产品类型';

comment on column tb_fund_transfer_in.busi_newl_req_num is

'业务编号(新唯一)';

comment on column tb_fund_transfer_in.currency_cd is
'币种(cd000019)';

comment on column tb_fund_transfer_in.crd_apply_in_amt is
'转入金额';
