drop table TB_CSM_GROUP_COMPANY;

create table TB_CSM_GROUP_COMPANY (
   customer_num       VARCHAR(10)            not null,
   REGISTER_NUM         VARCHAR(50),
   TOTAL_ASSETS         DECIMAL(18,2),
   LIABILITY_SUM        DECIMAL(18,2),
   CUST_STATUS          VARCHAR(1),
   GROUP_MEMBER_NUM     DECIMAL(8),
   STATUS               VARCHAR(100),
   CHANNEL              VARCHAR(18),
   ENTMARK              VARCHAR(100),
   TMPENTMARK           VARCHAR(100),
   IN_SRC               VARCHAR(18),
   CREATE_TIME          TIMESTAMP,
   UPDATE_TIME          TIMESTAMP,
   constraint P_Key_1 primary key (customer_num)
);

comment on table TB_CSM_GROUP_COMPANY is
'集团客户';

comment on column TB_CSM_GROUP_COMPANY.customer_num is
'客户编号';

comment on column TB_CSM_GROUP_COMPANY.REGISTER_NUM is
'注册登记号码';

comment on column TB_CSM_GROUP_COMPANY.TOTAL_ASSETS is
'资产总额';

comment on column TB_CSM_GROUP_COMPANY.LIABILITY_SUM is
'负债总额';

comment on column TB_CSM_GROUP_COMPANY.CUST_STATUS is
'客户状态';

comment on column TB_CSM_GROUP_COMPANY.GROUP_MEMBER_NUM is
'集团成员数';

comment on column TB_CSM_GROUP_COMPANY.STATUS is
'状态（:XD_KHCD0231）';

comment on column TB_CSM_GROUP_COMPANY.CHANNEL is
'渠道';

comment on column TB_CSM_GROUP_COMPANY.ENTMARK is
'商号';

comment on column TB_CSM_GROUP_COMPANY.TMPENTMARK is
'tmp商号';

comment on column TB_CSM_GROUP_COMPANY.IN_SRC is
'来源系统';

comment on column TB_CSM_GROUP_COMPANY.CREATE_TIME is
'创建时间';

comment on column TB_CSM_GROUP_COMPANY.UPDATE_TIME is
'更新时间';


