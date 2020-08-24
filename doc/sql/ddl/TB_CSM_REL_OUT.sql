drop table TB_CSM_REL_OUT;

create table TB_CSM_REL_OUT (
   ID                   VARCHAR(32)            not null,
   customer_num       VARCHAR(10)            not null,
   REL_CUST_PROPERTY    VARCHAR(1),
   CUST_NAME            VARCHAR(200),
   ORG_SHORT_NAME       VARCHAR(200),
   CUST_ENG_NAME        VARCHAR(200),
   ORG_RNG_SHORT_NAME   VARCHAR(100),
   cert_type_cd       VARCHAR(2),
   CERT_NUM             VARCHAR(60)            not null,
   CERT_START_DATE      VARCHAR(10),
   CERT_END_DATE        VARCHAR(10),
   ISSUED_INST          VARCHAR(120),
   FOUND_DATE           VARCHAR(10),
   REG_CPTL_CURR        VARCHAR(3),
   REG_CAPITAL          DECIMAL(24, 2),
   UNIT_SCALE           VARCHAR(1),
   EMP_NUMBER           VARCHAR(8),
   PAY_CREDIT_FLAG      VARCHAR(1),
   CORPORATE_CUST_TYPE  VARCHAR(3),
   OPERATING_SITE_OWNERSHIP VARCHAR(1),
   SPECIAL_ZONE_TYPE    VARCHAR(2),
   FINANCE_MECHANISM_CLASSI VARCHAR(2),
   ECONOMIC_TYPE        VARCHAR(9),
   BUSIN_SCOPE          VARCHAR(256),
   COLLECT_CAPITAL_CURRENCY VARCHAR(3),
   CAP_INHD             DECIMAL(24, 2),
   INVEST_MODE          VARCHAR(4),
   CAPITAL_AMOUNT       DECIMAL(24, 2),
   INVESTOR_ECON_COMP   VARCHAR(5),
   INVESTOR_PERCENT     DECIMAL(7, 2),
   TOTAL_ENTITY         VARCHAR(18),
   NATION               VARCHAR(3),
   OCCUPATION           VARCHAR(5),
   OCCUPATION_EXPLAIN   VARCHAR(30),
   DUTY                 VARCHAR(1),
   PER_YEAR_INCOME      DECIMAL(24, 2),
   REL_ADDR             VARCHAR(256),
   REL_PHONE            VARCHAR(11),
   CREATE_TIME          TIMESTAMP,
   UPDATE_TIME          TIMESTAMP,
   constraint P_Key_1 primary key (ID)
);

comment on table TB_CSM_REL_OUT is
'客户行外关联的信息';

comment on column TB_CSM_REL_OUT.ID is
'ID主键';

comment on column TB_CSM_REL_OUT.customer_num is
'客户编号';

comment on column TB_CSM_REL_OUT.REL_CUST_PROPERTY is
'关系客户类型(CD000016)';

comment on column TB_CSM_REL_OUT.CUST_NAME is
'关系人名称';

comment on column TB_CSM_REL_OUT.ORG_SHORT_NAME is
'组织中文简称';

comment on column TB_CSM_REL_OUT.CUST_ENG_NAME is
'客户英文名称';

comment on column TB_CSM_REL_OUT.ORG_RNG_SHORT_NAME is
'组织英文简称';

comment on column TB_CSM_REL_OUT.cert_type_cd is
'证件类型(CD000003)';

comment on column TB_CSM_REL_OUT.CERT_NUM is
'证件号码';

comment on column TB_CSM_REL_OUT.CERT_START_DATE is
'证件生效日期';

comment on column TB_CSM_REL_OUT.CERT_END_DATE is
'证件到期日期';

comment on column TB_CSM_REL_OUT.ISSUED_INST is
'证件发放机关';

comment on column TB_CSM_REL_OUT.FOUND_DATE is
'成立日期';

comment on column TB_CSM_REL_OUT.REG_CPTL_CURR is
'注册资本币种(CD000019)';

comment on column TB_CSM_REL_OUT.REG_CAPITAL is
'注册资本';

comment on column TB_CSM_REL_OUT.UNIT_SCALE is
'企业规模(CD000020)';

comment on column TB_CSM_REL_OUT.EMP_NUMBER is
'企业员工人数';

comment on column TB_CSM_REL_OUT.PAY_CREDIT_FLAG is
'是否代发工资';

comment on column TB_CSM_REL_OUT.CORPORATE_CUST_TYPE is
'涉农法人客户类型(CD000022)';

comment on column TB_CSM_REL_OUT.OPERATING_SITE_OWNERSHIP is
'经营场地所有权(CD000027';

comment on column TB_CSM_REL_OUT.SPECIAL_ZONE_TYPE is
'特殊经济区类型(CD000021)';

comment on column TB_CSM_REL_OUT.FINANCE_MECHANISM_CLASSI is
'金融机构行业分类(CD000028)';

comment on column TB_CSM_REL_OUT.ECONOMIC_TYPE is
'经济类型(CD000026)';

comment on column TB_CSM_REL_OUT.BUSIN_SCOPE is
'经营范围';

comment on column TB_CSM_REL_OUT.COLLECT_CAPITAL_CURRENCY is
'实收资本币种(CD000019)';

comment on column TB_CSM_REL_OUT.CAP_INHD is
'实收资本';

comment on column TB_CSM_REL_OUT.INVEST_MODE is
'出资方式(CD000034)';

comment on column TB_CSM_REL_OUT.CAPITAL_AMOUNT is
'出资金额';

comment on column TB_CSM_REL_OUT.INVESTOR_ECON_COMP is
'出资人经济成分';

comment on column TB_CSM_REL_OUT.INVESTOR_PERCENT is
'出资占比';

comment on column TB_CSM_REL_OUT.TOTAL_ENTITY is
'股本总量';

comment on column TB_CSM_REL_OUT.NATION is
'关系人国籍(CD000001)';

comment on column TB_CSM_REL_OUT.OCCUPATION is
'关系人职业(CD000012';

comment on column TB_CSM_REL_OUT.OCCUPATION_EXPLAIN is
'职业说明';

comment on column TB_CSM_REL_OUT.DUTY is
'职务(CD000013)';

comment on column TB_CSM_REL_OUT.PER_YEAR_INCOME is
'个人年收入';

comment on column TB_CSM_REL_OUT.REL_ADDR is
'联系地址';

comment on column TB_CSM_REL_OUT.REL_PHONE is
'联系电话';

comment on column TB_CSM_REL_OUT.CREATE_TIME is
'创建时间';

comment on column TB_CSM_REL_OUT.UPDATE_TIME is
'更新时间';
