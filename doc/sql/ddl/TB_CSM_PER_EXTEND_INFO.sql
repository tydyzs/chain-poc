drop table TB_CSM_PER_EXTEND_INFO;

create table TB_CSM_PER_EXTEND_INFO (
   customer_num       VARCHAR(10)            not null,
   MAIN_ECONOMIC_SOURES VARCHAR(1),
   HOUSE_ANNUAL_INCOME  DECIMAL(24, 2),
   PER_YEAR_INCOME      DECIMAL(24, 2),
   RESID_SITUAT         VARCHAR(1),
   CUST_GRADE           VARCHAR(1),
   PER_TOTAL_ASSET      DECIMAL(24, 2),
   LIABILITY_BALANCE    DECIMAL(24, 2),
   PER_ASSEST_TYPE      VARCHAR(2),
   PER_LIAB_TYPE        VARCHAR(2),
   CREATE_TIME          TIMESTAMP,
   UPDATE_TIME          TIMESTAMP,
   constraint P_Key_1 primary key (customer_num)
);

comment on table TB_CSM_PER_EXTEND_INFO is
'对私客户扩展信息';

comment on column TB_CSM_PER_EXTEND_INFO.customer_num is
'客户编号';

comment on column TB_CSM_PER_EXTEND_INFO.MAIN_ECONOMIC_SOURES is
'主要经济来源(CD000047)';

comment on column TB_CSM_PER_EXTEND_INFO.HOUSE_ANNUAL_INCOME is
'家庭年收入';

comment on column TB_CSM_PER_EXTEND_INFO.PER_YEAR_INCOME is
'个人年收入';

comment on column TB_CSM_PER_EXTEND_INFO.RESID_SITUAT is
'居住状况(CD000035)';

comment on column TB_CSM_PER_EXTEND_INFO.CUST_GRADE is
'客户等级';

comment on column TB_CSM_PER_EXTEND_INFO.PER_TOTAL_ASSET is
'个人总资产';

comment on column TB_CSM_PER_EXTEND_INFO.LIABILITY_BALANCE is
'个人总负债';

comment on column TB_CSM_PER_EXTEND_INFO.PER_ASSEST_TYPE is
'个人资产类型(CD000046)';

comment on column TB_CSM_PER_EXTEND_INFO.PER_LIAB_TYPE is
'个人负债类型(CD000045)';

comment on column TB_CSM_PER_EXTEND_INFO.CREATE_TIME is
'创建时间';

comment on column TB_CSM_PER_EXTEND_INFO.UPDATE_TIME is
'更新时间';
