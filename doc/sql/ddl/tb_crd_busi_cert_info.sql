
drop table tb_crd_busi_cert_info;


/*==============================================================*/
/* Table: tb_crd_busi_cert_info                                 */
/*==============================================================*/
create table tb_crd_busi_cert_info
(
   cret_info_id         VARCHAR(32)                    not null,
   crd_detail_num       VARCHAR(32)                    not null,
   busi_deal_num        VARCHAR(40)                    not null,
   busi_prd_num         VARCHAR(10)                    not null,
   busi_deal_desc       VARCHAR(50)                    null,
   busi_deal_org_num    VARCHAR(40)                    null,
   busi_deal_org_name   VARCHAR(100)                   null,
   busi_oppt_org_num    VARCHAR(40)                    null,
   busi_oppt_org_name   VARCHAR(100)                   null,
   busi_sum_amt         DECIMAL(24, 2)                 null,
   busi_cert_cnt        DECIMAL(24, 0)                     null,
   cert_num             VARCHAR(40)                    not null,
   cert_type_cd         VARCHAR(40)                    null,
   cert_ppt_cd          VARCHAR(40)                    null,
   cert_interest_peri_type VARCHAR(10)                    null,
   cert_interest_period VARCHAR(40)                    null,
   cert_interest_rate_type VARCHAR(10)                    null,
   cert_interest_rate   DECIMAL(24, 2)                 null,
   cert_currency_cd     VARCHAR(10)                    null,
   cert_seq_amt         DECIMAL(24, 2)                 null,
   cert_apply_amt       DECIMAL(24, 2)                 null,
   cert_apply_balance   DECIMAL(24, 2)                 null,
   cert_status          VARCHAR(10)                    null,
   cert_begin_date      VARCHAR(10)                    null,
   cert_end_date        VARCHAR(10)                    null,
   cert_finish_date     VARCHAR(10)                    null,
   cert_drawer_cust_num VARCHAR(50)                    null,
   cert_drawer_name     VARCHAR(200)                   null,
   cert_drawer_bank_num VARCHAR(50)                    null,
   cert_drawer_bank_name VARCHAR(200)                   null,
   cert_guaranty_type   VARCHAR(40)                    null,
   cert_guaranty_person VARCHAR(40)                    null,
   last_summary_bal     DECIMAL(24, 2)                 null,
   user_num             VARCHAR(12)                    null,
   cert_busi_remark     VARCHAR(1000)                  null,
   constraint PK_TB_CRD_BUSI_CERT_INFO primary key (cret_info_id),
   constraint AK_UQ_TB_CRD_B unique (crd_detail_num, busi_deal_num, cert_num)
);

comment on table tb_crd_busi_cert_info is
'业务产品凭证信息表';

comment on column tb_crd_busi_cert_info.cret_info_id is
'凭证信息id';

comment on column tb_crd_busi_cert_info.crd_detail_num is
'三级额度编号';

comment on column tb_crd_busi_cert_info.busi_deal_num is
'业务编号';

comment on column tb_crd_busi_cert_info.busi_prd_num is
'业务产品编号';

comment on column tb_crd_busi_cert_info.busi_deal_desc is
'交易描述';

comment on column tb_crd_busi_cert_info.busi_deal_org_num is
'本方机构';

comment on column tb_crd_busi_cert_info.busi_deal_org_name is
'本方机构名称';

comment on column tb_crd_busi_cert_info.busi_oppt_org_num is
'对手机构';

comment on column tb_crd_busi_cert_info.busi_oppt_org_name is
'对手机构名称';

comment on column tb_crd_busi_cert_info.busi_sum_amt is
'交易总金额';

comment on column tb_crd_busi_cert_info.busi_cert_cnt is
'凭证张数';

comment on column tb_crd_busi_cert_info.cert_num is
'凭证编号';

comment on column tb_crd_busi_cert_info.cert_type_cd is
'凭证品种';

comment on column tb_crd_busi_cert_info.cert_ppt_cd is
'凭证性质';

comment on column tb_crd_busi_cert_info.cert_interest_peri_type is
'凭证计息期限类型';

comment on column tb_crd_busi_cert_info.cert_interest_period is
'凭证计息期限';

comment on column tb_crd_busi_cert_info.cert_interest_rate_type is
'收益率/利率类型';

comment on column tb_crd_busi_cert_info.cert_interest_rate is
'收益率/利率';

comment on column tb_crd_busi_cert_info.cert_currency_cd is
'币种';

comment on column tb_crd_busi_cert_info.cert_seq_amt is
'凭证申请金额';

comment on column tb_crd_busi_cert_info.cert_apply_amt is
'凭证用信金额';

comment on column tb_crd_busi_cert_info.cert_apply_balance is
'凭证用信余额';

comment on column tb_crd_busi_cert_info.cert_status is
'凭证状态（CD000201）';

comment on column tb_crd_busi_cert_info.cert_begin_date is
'凭证起期';

comment on column tb_crd_busi_cert_info.cert_end_date is
'凭证止期';

comment on column tb_crd_busi_cert_info.cert_finish_date is
'凭证结清日期';

comment on column tb_crd_busi_cert_info.cert_drawer_cust_num is
'发行人客户号';

comment on column tb_crd_busi_cert_info.cert_drawer_name is
'发行人客户名称';

comment on column tb_crd_busi_cert_info.cert_drawer_bank_num is
'发行人代理/承兑行号';

comment on column tb_crd_busi_cert_info.cert_drawer_bank_name is
'发行人代理/承兑行名';

comment on column tb_crd_busi_cert_info.cert_guaranty_type is
'担保方式';

comment on column tb_crd_busi_cert_info.cert_guaranty_person is
'担保人';

comment on column tb_crd_busi_cert_info.last_summary_bal is
'上月凭证金额';

comment on column tb_crd_busi_cert_info.user_num is
'经办人';

comment on column tb_crd_busi_cert_info.cert_busi_remark is
'备注信息';
