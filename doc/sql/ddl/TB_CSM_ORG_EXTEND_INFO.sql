drop table TB_CSM_ORG_EXTEND_INFO;

create table TB_CSM_ORG_EXTEND_INFO (
   customer_num       VARCHAR(10)            not null,
   TAX_RES_TYPE         VARCHAR(1),
   INVEST_MODE          VARCHAR(4),
   CAPITAL_AMOUNT       DECIMAL(24, 2),
   INVESTOR_ECON_COMP   VARCHAR(5),
   INVESTOR_PERCENT     DECIMAL(7, 2),
   TOTAL_ENTITY         VARCHAR(18),
   LISTING_DATE         VARCHAR(10),
   EXTERNAL_CREDIT_RESULT VARCHAR(4),
   NATURE_CREDIT_AGENCY VARCHAR(2),
   LISTED_COM_TYPE      VARCHAR(1),
   EXTERNAL_ASSESS_INSTIT VARCHAR(2),
   RESID_NON_RESID      VARCHAR(2),
   BASIC_ACCOUNT_AUTH_NUM VARCHAR(30),
   BASIC_LOCAL_OPEN_BANK VARCHAR(90),
   CUST_REGISTER_NUM    VARCHAR(10),
   CURR_BUSIN_PERMIT_num VARCHAR(20),
   FRIST_DATE           VARCHAR(10),
   WHETHER_TRANS_COMPANY VARCHAR(1),
   RESID_COUNTRY_CODE   VARCHAR(3),
   FOREIGN_INVES_COUNTRY1 VARCHAR(3),
   FOREIGN_INVES_COUNTRY2 VARCHAR(3),
   FOREIGN_INVES_COUNTRY3 VARCHAR(3),
   FOREIGN_INVES_COUNTRY4 VARCHAR(3),
   FOREIGN_INVES_COUNTRY5 VARCHAR(3),
   FORE_EXCH_BURE_CODE  VARCHAR(6),
   DECLARE_MODE         VARCHAR(1),
   INTER_INCO_EXPEN_DECLA VARCHAR(20),
   FINAN_INSTITU_FLAG   VARCHAR(1),
   OVERSEAS_CUST_FLAG   VARCHAR(1),
   HIGH_TECH_ENTERP_INDI VARCHAR(1),
   PLACE_OF_LISTING     VARCHAR(1),
   STOCK_CODE           VARCHAR(20),
   STOCK_NAME           VARCHAR(100),
   BENE_CUST_TYPE       VARCHAR(1),
   IN_SRC               VARCHAR(5),
   CREATE_TIME          TIMESTAMP,
   UPDATE_TIME          TIMESTAMP,
   constraint P_Key_1 primary key (customer_num)
);

comment on table TB_CSM_ORG_EXTEND_INFO is
'对公客户扩展信息表';

comment on column TB_CSM_ORG_EXTEND_INFO.customer_num is
'客户编号';

comment on column TB_CSM_ORG_EXTEND_INFO.TAX_RES_TYPE is
'税收居民身份(CD000017)';

comment on column TB_CSM_ORG_EXTEND_INFO.INVEST_MODE is
'出资方式(CD000034)';

comment on column TB_CSM_ORG_EXTEND_INFO.CAPITAL_AMOUNT is
'出资金额';

comment on column TB_CSM_ORG_EXTEND_INFO.INVESTOR_ECON_COMP is
'出资人经济成分(CD000030)';

comment on column TB_CSM_ORG_EXTEND_INFO.INVESTOR_PERCENT is
'出资占比';

comment on column TB_CSM_ORG_EXTEND_INFO.TOTAL_ENTITY is
'股本总量';

comment on column TB_CSM_ORG_EXTEND_INFO.LISTING_DATE is
'上市日期';

comment on column TB_CSM_ORG_EXTEND_INFO.EXTERNAL_CREDIT_RESULT is
'客户外部评级结果';

comment on column TB_CSM_ORG_EXTEND_INFO.NATURE_CREDIT_AGENCY is
'评级机构性质(CD000025)';

comment on column TB_CSM_ORG_EXTEND_INFO.LISTED_COM_TYPE is
'上市公司类型(CD000018)';

comment on column TB_CSM_ORG_EXTEND_INFO.EXTERNAL_ASSESS_INSTIT is
'外部信用等级评级机构(CD000029)';

comment on column TB_CSM_ORG_EXTEND_INFO.RESID_NON_RESID is
'居民/非居民';

comment on column TB_CSM_ORG_EXTEND_INFO.BASIC_ACCOUNT_AUTH_NUM is
'基本账户开户许可证核准号';

comment on column TB_CSM_ORG_EXTEND_INFO.BASIC_LOCAL_OPEN_BANK is
'本地基本账户开户行';

comment on column TB_CSM_ORG_EXTEND_INFO.CUST_REGISTER_NUM is
'海关注册号';

comment on column TB_CSM_ORG_EXTEND_INFO.CURR_BUSIN_PERMIT_num is
'外汇经营许可证号';

comment on column TB_CSM_ORG_EXTEND_INFO.FRIST_DATE is
'最初设立日期';

comment on column TB_CSM_ORG_EXTEND_INFO.WHETHER_TRANS_COMPANY is
'是否跨国公司';

comment on column TB_CSM_ORG_EXTEND_INFO.RESID_COUNTRY_CODE is
'常驻国家代码(CD000001)';

comment on column TB_CSM_ORG_EXTEND_INFO.FOREIGN_INVES_COUNTRY1 is
'外方投资者国别(CD000001)';

comment on column TB_CSM_ORG_EXTEND_INFO.FOREIGN_INVES_COUNTRY2 is
'外方投资者国别(CD000001)';

comment on column TB_CSM_ORG_EXTEND_INFO.FOREIGN_INVES_COUNTRY3 is
'外方投资者国别(CD000001)';

comment on column TB_CSM_ORG_EXTEND_INFO.FOREIGN_INVES_COUNTRY4 is
'外方投资者国别(CD000001)';

comment on column TB_CSM_ORG_EXTEND_INFO.FOREIGN_INVES_COUNTRY5 is
'外方投资者国别(CD000001)';

comment on column TB_CSM_ORG_EXTEND_INFO.FORE_EXCH_BURE_CODE is
'所属外汇局代码';

comment on column TB_CSM_ORG_EXTEND_INFO.DECLARE_MODE is
'申报方式(CD000183)';

comment on column TB_CSM_ORG_EXTEND_INFO.INTER_INCO_EXPEN_DECLA is
'国际收支申报联系方式';

comment on column TB_CSM_ORG_EXTEND_INFO.FINAN_INSTITU_FLAG is
'金融机构标志';

comment on column TB_CSM_ORG_EXTEND_INFO.OVERSEAS_CUST_FLAG is
'境外客户标志';

comment on column TB_CSM_ORG_EXTEND_INFO.HIGH_TECH_ENTERP_INDI is
'高新技术企业标志';

comment on column TB_CSM_ORG_EXTEND_INFO.PLACE_OF_LISTING is
'上市地点(CD000037)';

comment on column TB_CSM_ORG_EXTEND_INFO.STOCK_CODE is
'股票代码';

comment on column TB_CSM_ORG_EXTEND_INFO.STOCK_NAME is
'股票名称';

comment on column TB_CSM_ORG_EXTEND_INFO.BENE_CUST_TYPE is
'对公客户类型(CD000184)';

comment on column TB_CSM_ORG_EXTEND_INFO.IN_SRC is
'来源系统号';

comment on column TB_CSM_ORG_EXTEND_INFO.CREATE_TIME is
'创建时间';

comment on column TB_CSM_ORG_EXTEND_INFO.UPDATE_TIME is
'更新时间';

