drop table TB_CSM_CERT_INFO;

create table TB_CSM_CERT_INFO (
   ID                   VARCHAR(32)            not null,
   customer_num       VARCHAR(10)            not null,
   cert_type_cd       VARCHAR(2),
   CERT_NUM             VARCHAR(60)            not null,
   CERT_FLAG            VARCHAR(2),
   CERT_START_DATE      VARCHAR(10),
   CERT_END_DATE        VARCHAR(10),
   ISSUED_INST          VARCHAR(120),
   IMPORTANT_CERT_DAYE  VARCHAR(10),
   CREATE_TIME          TIMESTAMP,
   UPDATE_TIME          TIMESTAMP,
   constraint P_Key_1 primary key (ID)
);

comment on table TB_CSM_CERT_INFO is
'证件信息表';

comment on column TB_CSM_CERT_INFO.ID is
'主键ID';

comment on column TB_CSM_CERT_INFO.customer_num is
'客户编号';

comment on column TB_CSM_CERT_INFO.cert_type_cd is
'证件类型(CD000003)';

comment on column TB_CSM_CERT_INFO.CERT_NUM is
'证件号码';

comment on column TB_CSM_CERT_INFO.CERT_FLAG is
'开户证件标识(CD000182)';

comment on column TB_CSM_CERT_INFO.CERT_START_DATE is
'证件生效日期';

comment on column TB_CSM_CERT_INFO.CERT_END_DATE is
'证件到期日期';

comment on column TB_CSM_CERT_INFO.ISSUED_INST is
'证件发放机关';

comment on column TB_CSM_CERT_INFO.IMPORTANT_CERT_DAYE is
'重要证件最近年检日期';

comment on column TB_CSM_CERT_INFO.CREATE_TIME is
'创建时间';

comment on column TB_CSM_CERT_INFO.UPDATE_TIME is
'更新时间';
