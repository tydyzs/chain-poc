drop table TB_CSM_INDIVIDUAL;

create table TB_CSM_INDIVIDUAL (
   customer_num       VARCHAR(10)            not null,
   PARTY_NAME           VARCHAR(300),
   ENGLISH_NAME         VARCHAR(300),
   GENDER_CD            VARCHAR(2),
   MARRIAGE_CD          VARCHAR(2),
   BIRTHDAY             VARCHAR(10),
   DEGREE_CD            VARCHAR(6),
   EDUCATION_BACKGROUD_CD VARCHAR(2),
   NATION               VARCHAR(2),
   REMARK               VARCHAR(1000),
   UPDATE_USER_NUM      VARCHAR(10),
   UPDATE_ORG_NUM       VARCHAR(9),
   NATURAL_PERSON_TYPE_CD VARCHAR(6),
   WORK_UNIT            VARCHAR(200),
   cert_type_cd       VARCHAR(6),
   CERT_NUM             VARCHAR(30),
   HUKOU_PROPERTY       VARCHAR(20),
   IS_FARMER            VARCHAR(20),
   HUKOU_REGISTED       VARCHAR(200),
   HEALTH_STATE         VARCHAR(20),
   STREET_POLICE_STATION VARCHAR(200),
   PROFESSION           VARCHAR(20),
   PROFESSIONAL_TITLE   VARCHAR(20),
   ACCOUNTING_ASSISTANT VARCHAR(20),
   POSITION_PROPERTY    VARCHAR(20),
   WORK_YEARS           VARCHAR(20),
   FAMILY_NUMBER        VARCHAR(6),
   PROVIDE_FOR_NUMBER   VARCHAR(6),
   FAMILY_ADDRESS       VARCHAR(500),
   HOUSE_PROPERTY       VARCHAR(20),
   FAMILY_PHONE         VARCHAR(40),
   PHONE_NUMBER         VARCHAR(60),
   UNIT_ADRESS          VARCHAR(500),
   UNIT_PHONE           VARCHAR(50),
   INDUSTRY             VARCHAR(100),
   INDUSTRY_DESC        VARCHAR(200),
   JOINT_GUARANTEE      VARCHAR(20),
   STOCKHOLDER_OF_BANK  VARCHAR(20),
   WHETHER_BLACK_LIST   VARCHAR(20),
   BLACK_LIST_REASON    VARCHAR(200),
   IS_BANK_EMPLOYEE     VARCHAR(20),
   EMPLOYEE_GRADE       VARCHAR(6),
   IS_SAMLL_LOAND_EMP   VARCHAR(20),
   COUNTRY_SIGN         VARCHAR(6),
   IS_BASEBANK_RELA_CUST VARCHAR(20),
   IS_THIRD_CUST        VARCHAR(20),
   THIRD_CUST_TYPE_CD   VARCHAR(6),
   MIDDLE_CODE          VARCHAR(30),
   WEIXIN_NUM           VARCHAR(200),
   UNIT_PROPERTY        VARCHAR(60),
   INIT_BUSNIESS_DATE   VARCHAR(10),
   CERT_END_DATE        VARCHAR(10),
   CREATE_TIME          timestamp,
   UPDATE_TIME          timestamp,
   constraint P_Key_1 primary key (customer_num)
);

comment on table TB_CSM_INDIVIDUAL is
'个人客户基本信息';

comment on column TB_CSM_INDIVIDUAL.customer_num is
'客户编号';

comment on column TB_CSM_INDIVIDUAL.PARTY_NAME is
'参与人名称';

comment on column TB_CSM_INDIVIDUAL.ENGLISH_NAME is
'英文名称';

comment on column TB_CSM_INDIVIDUAL.GENDER_CD is
'性别代码(CD000004)';

comment on column TB_CSM_INDIVIDUAL.MARRIAGE_CD is
'婚姻状况(CD000007)';

comment on column TB_CSM_INDIVIDUAL.BIRTHDAY is
'出生日期';

comment on column TB_CSM_INDIVIDUAL.DEGREE_CD is
'学位代码(CD000010)';

comment on column TB_CSM_INDIVIDUAL.EDUCATION_BACKGROUD_CD is
'学历代码(CD000011)';

comment on column TB_CSM_INDIVIDUAL.NATION is
'民族(CD000005)';

comment on column TB_CSM_INDIVIDUAL.REMARK is
'备注';

comment on column TB_CSM_INDIVIDUAL.UPDATE_USER_NUM is
'更新人';

comment on column TB_CSM_INDIVIDUAL.UPDATE_ORG_NUM is
'更新机构';

comment on column TB_CSM_INDIVIDUAL.NATURAL_PERSON_TYPE_CD is
'自然人类型';

comment on column TB_CSM_INDIVIDUAL.WORK_UNIT is
'工作单位';

comment on column TB_CSM_INDIVIDUAL.cert_type_cd is
'证件类型(CD000003)';

comment on column TB_CSM_INDIVIDUAL.CERT_NUM is
'证件号码';

comment on column TB_CSM_INDIVIDUAL.HUKOU_PROPERTY is
'户籍性质';

comment on column TB_CSM_INDIVIDUAL.IS_FARMER is
'是否农户(:YesOrNo)';

comment on column TB_CSM_INDIVIDUAL.HUKOU_REGISTED is
'户籍所在地';

comment on column TB_CSM_INDIVIDUAL.HEALTH_STATE is
'健康状况(CD000009)';

comment on column TB_CSM_INDIVIDUAL.STREET_POLICE_STATION is
'所属街道派出所';

comment on column TB_CSM_INDIVIDUAL.PROFESSION is
'职业(CD000012)';

comment on column TB_CSM_INDIVIDUAL.PROFESSIONAL_TITLE is
'职称(CD000014)';

comment on column TB_CSM_INDIVIDUAL.ACCOUNTING_ASSISTANT is
'职务(CD000013)';

comment on column TB_CSM_INDIVIDUAL.POSITION_PROPERTY is
'岗位性质';

comment on column TB_CSM_INDIVIDUAL.WORK_YEARS is
'目前工作持续年限';

comment on column TB_CSM_INDIVIDUAL.FAMILY_NUMBER is
'家庭人口';

comment on column TB_CSM_INDIVIDUAL.PROVIDE_FOR_NUMBER is
'供养人口';

comment on column TB_CSM_INDIVIDUAL.FAMILY_ADDRESS is
'家庭住址';

comment on column TB_CSM_INDIVIDUAL.HOUSE_PROPERTY is
'居住状况(CD000035)';

comment on column TB_CSM_INDIVIDUAL.FAMILY_PHONE is
'家庭电话';

comment on column TB_CSM_INDIVIDUAL.PHONE_NUMBER is
'手机号码';

comment on column TB_CSM_INDIVIDUAL.UNIT_ADRESS is
'单位地址';

comment on column TB_CSM_INDIVIDUAL.UNIT_PHONE is
'单位电话';

comment on column TB_CSM_INDIVIDUAL.INDUSTRY is
'行业(CD000015)';

comment on column TB_CSM_INDIVIDUAL.INDUSTRY_DESC is
'行业具体描述';

comment on column TB_CSM_INDIVIDUAL.JOINT_GUARANTEE is
'职保小组标志(:YesOrNo)';

comment on column TB_CSM_INDIVIDUAL.STOCKHOLDER_OF_BANK is
'我行股东标志(:YesOrNo)';

comment on column TB_CSM_INDIVIDUAL.WHETHER_BLACK_LIST is
'黑名单标志(:YesOrNo)';

comment on column TB_CSM_INDIVIDUAL.BLACK_LIST_REASON is
'加入黑名单原因';

comment on column TB_CSM_INDIVIDUAL.IS_BANK_EMPLOYEE is
'是否本行员工(:YesOrNo)';

comment on column TB_CSM_INDIVIDUAL.EMPLOYEE_GRADE is
'在职行员等级';

comment on column TB_CSM_INDIVIDUAL.IS_SAMLL_LOAND_EMP is
'是否小贷中心员工(:YesOrNo)';

comment on column TB_CSM_INDIVIDUAL.COUNTRY_SIGN is
'国籍(CD000001';

comment on column TB_CSM_INDIVIDUAL.IS_BASEBANK_RELA_CUST is
'是否我行关联方(:YesOrNo)';

comment on column TB_CSM_INDIVIDUAL.IS_THIRD_CUST is
'是否第三方客户(:YesOrNo)';

comment on column TB_CSM_INDIVIDUAL.THIRD_CUST_TYPE_CD is
'第三方客户类型(CD000033)';

comment on column TB_CSM_INDIVIDUAL.MIDDLE_CODE is
'中征码';

comment on column TB_CSM_INDIVIDUAL.WEIXIN_NUM is
'微信号';

comment on column TB_CSM_INDIVIDUAL.UNIT_PROPERTY is
'单位性质';

comment on column TB_CSM_INDIVIDUAL.INIT_BUSNIESS_DATE is
'开业日期';

comment on column TB_CSM_INDIVIDUAL.CERT_END_DATE is
'证件到期日';

comment on column TB_CSM_INDIVIDUAL.CREATE_TIME is
'创建时间';

comment on column TB_CSM_INDIVIDUAL.UPDATE_TIME is
'更新时间';

