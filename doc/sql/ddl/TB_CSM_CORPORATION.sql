drop table TB_CSM_CORPORATION;

create table TB_CSM_CORPORATION (
   customer_num       VARCHAR(10)            not null,
   ENGLISH_NAME         VARCHAR(300),
   CORP_cust_type     VARCHAR(5),
   UNIFY_SOCIETY_CREDIT_NUM VARCHAR(100),
   ECONOMIC_SECTOR_CODE VARCHAR(5),
   INDUSTRY             VARCHAR(12),
   LISTING_CORPORATION  VARCHAR(1),
   REGISTER_ASSETS      DECIMAL(18,2),
   REGISTER_ASSETS_CURRENCY_CD VARCHAR(3),
   EMPLOYEES_NUMBER     DECIMAL(10),
   GOVERNMENT_TENT_num VARCHAR(50),
   REGISTER_DATE        VARCHAR(10),
   LEGAL_CERTIFICATE_num VARCHAR(30),
   LEGAL_CERTIFICATE_END_DATE VARCHAR(10),
   REGISTRATION_TYPE    VARCHAR(6),
   AREA_TYPE            VARCHAR(1),
   CONTRY_REGION_CD     VARCHAR(20),
   LEGAL_NAME           VARCHAR(20),
   LEGAL_cert_type_cd VARCHAR(6),
   REGISTR_CD           VARCHAR(40),
   REGISTER_END_DATE    VARCHAR(10),
   BUSINESS_SCOPE       VARCHAR(3000),
   ORG_REGISTER_CD      VARCHAR(30),
   ORG_REGISTER_END_DATE VARCHAR(10),
   MIDDEL_CODE          VARCHAR(30),
   ORG_CREDIT_CODE      VARCHAR(30),
   NATIONAL_TAX_num   VARCHAR(40),
   ENTERPRISE_SCALE     VARCHAR(30),
   IS_GROUP_CUST        VARCHAR(6)             default '0',
   ATTACH_GROUP_NAME    VARCHAR(200),
   WHETHER_IMP_EXP      VARCHAR(6),
   STOCKHOLDER_OF_BANK  VARCHAR(6),
   JOINT_GUARANTEE      VARCHAR(6),
   FAMILY_ENTERPRISE    VARCHAR(6),
   COUNTRYSIDE_ENTERPRISE VARCHAR(6),
   WHETHER_BLACK_LIST   VARCHAR(6),
   FOCUS_CUSTOMER       VARCHAR(6),
   IS_REAL_ESTATE_DEV   VARCHAR(6),
   FUND_SRC             VARCHAR(200),
   OPEN_FUND            DECIMAL(18,2),
   IS_BASEBANK_RELA_CUST VARCHAR(20),
   THIRD_CUST_TYPE_CD   VARCHAR(20),
   IS_THIRD_CUST        VARCHAR(20),
   ENTERPRISE_CERT_END_DATE VARCHAR(10),
   ENTERPRISE_CERT_NUM  VARCHAR(30),
   bank_num           VARCHAR(50),
   sup_bank_num       VARCHAR(50),
   SWIFT_BIC_NUM        VARCHAR(11),
   UPDATE_TIME          timestamp,
   CREATE_TIME          timestamp,
   constraint P_Key_1 primary key (customer_num)
);

comment on table TB_CSM_CORPORATION is
'公司客户基本信息';

comment on column TB_CSM_CORPORATION.customer_num is
'客户编号';

comment on column TB_CSM_CORPORATION.ENGLISH_NAME is
'英文名称';

comment on column TB_CSM_CORPORATION.CORP_cust_type is
'对公客户类型(CD000033)';

comment on column TB_CSM_CORPORATION.UNIFY_SOCIETY_CREDIT_NUM is
'统一社会信用代码';

comment on column TB_CSM_CORPORATION.ECONOMIC_SECTOR_CODE is
'企业出资人经济成分(CD000030)';

comment on column TB_CSM_CORPORATION.INDUSTRY is
'行业分类(CD000015)';

comment on column TB_CSM_CORPORATION.LISTING_CORPORATION is
'是否上市公司(:YesOrNo)';

comment on column TB_CSM_CORPORATION.REGISTER_ASSETS is
'注册资本';

comment on column TB_CSM_CORPORATION.REGISTER_ASSETS_CURRENCY_CD is
'注册资本币种(CD000019)';

comment on column TB_CSM_CORPORATION.EMPLOYEES_NUMBER is
'从业人数';

comment on column TB_CSM_CORPORATION.GOVERNMENT_TENT_num is
'地税登记证号码';

comment on column TB_CSM_CORPORATION.REGISTER_DATE is
'注册登记日期';

comment on column TB_CSM_CORPORATION.LEGAL_CERTIFICATE_num is
'法人代表证件号码';

comment on column TB_CSM_CORPORATION.LEGAL_CERTIFICATE_END_DATE is
'法人代表证件到期日';

comment on column TB_CSM_CORPORATION.REGISTRATION_TYPE is
'登记注册类型(CD000181)';

comment on column TB_CSM_CORPORATION.AREA_TYPE is
'区域类型-境内外(CD000181)';

comment on column TB_CSM_CORPORATION.CONTRY_REGION_CD is
'国家或地区(:CD000001)';

comment on column TB_CSM_CORPORATION.LEGAL_NAME is
'法人代表名称';

comment on column TB_CSM_CORPORATION.LEGAL_cert_type_cd is
'法人代表证件类型(CD000003)';

comment on column TB_CSM_CORPORATION.REGISTR_CD is
'注册登记号码';

comment on column TB_CSM_CORPORATION.REGISTER_END_DATE is
'营业执照到期日';

comment on column TB_CSM_CORPORATION.BUSINESS_SCOPE is
'经营范围';

comment on column TB_CSM_CORPORATION.ORG_REGISTER_CD is
'组织机构代码';

comment on column TB_CSM_CORPORATION.ORG_REGISTER_END_DATE is
'组织机构代码到期日';

comment on column TB_CSM_CORPORATION.MIDDEL_CODE is
'中征码';

comment on column TB_CSM_CORPORATION.ORG_CREDIT_CODE is
'机构信用代码';

comment on column TB_CSM_CORPORATION.NATIONAL_TAX_num is
'国税登记证号码';

comment on column TB_CSM_CORPORATION.ENTERPRISE_SCALE is
'工信部企业规模(CD000020)';

comment on column TB_CSM_CORPORATION.IS_GROUP_CUST is
'是否集团客户(:YesOrNo)';

comment on column TB_CSM_CORPORATION.ATTACH_GROUP_NAME is
'所属集团名称';

comment on column TB_CSM_CORPORATION.WHETHER_IMP_EXP is
'进出口权标志(:YesOrNo)';

comment on column TB_CSM_CORPORATION.STOCKHOLDER_OF_BANK is
'我行股东标志(:YesOrNo)';

comment on column TB_CSM_CORPORATION.JOINT_GUARANTEE is
'联保小组标志(:YesOrNo)';

comment on column TB_CSM_CORPORATION.FAMILY_ENTERPRISE is
'家族企业标志(:YesOrNo)';

comment on column TB_CSM_CORPORATION.COUNTRYSIDE_ENTERPRISE is
'农村企业标志(:YesOrNo)';

comment on column TB_CSM_CORPORATION.WHETHER_BLACK_LIST is
'黑名单标志(:YesOrNo)';

comment on column TB_CSM_CORPORATION.FOCUS_CUSTOMER is
'重点客户标志(:YesOrNo)';

comment on column TB_CSM_CORPORATION.IS_REAL_ESTATE_DEV is
'是否从事房地产开发(:YesOrNo)';

comment on column TB_CSM_CORPORATION.FUND_SRC is
'经费来源';

comment on column TB_CSM_CORPORATION.OPEN_FUND is
'开办资金';

comment on column TB_CSM_CORPORATION.IS_BASEBANK_RELA_CUST is
'是否我行关联方(:YesOrNo)';

comment on column TB_CSM_CORPORATION.THIRD_CUST_TYPE_CD is
'第三方客户类型(CD000033)';

comment on column TB_CSM_CORPORATION.IS_THIRD_CUST is
'是否第三方客户(:YesOrNo)';

comment on column TB_CSM_CORPORATION.ENTERPRISE_CERT_END_DATE is
'事业法人证书到期日';

comment on column TB_CSM_CORPORATION.ENTERPRISE_CERT_NUM is
'事业法人证书号';

comment on column TB_CSM_CORPORATION.bank_num is
'联行号';

comment on column TB_CSM_CORPORATION.sup_bank_num is
'上级机构行号';

comment on column TB_CSM_CORPORATION.SWIFT_BIC_NUM is
'swift BIC码';

comment on column TB_CSM_CORPORATION.UPDATE_TIME is
'更新时间';

comment on column TB_CSM_CORPORATION.CREATE_TIME is
'创建时间';

