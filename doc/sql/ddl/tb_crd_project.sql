create table TB_CRD_PROJECT
(
	PROJECT_NUM VARCHAR(50) not null
		constraint P_KEY_1
			primary key,
	CUSTOMER_NUM VARCHAR(40),
	CRD_DETAIL_NUM VARCHAR(32),
	CRD_DETAIL_PRD VARCHAR(10),
	PROJECT_NAME VARCHAR(200),
	PROJECT_TYPE VARCHAR(10),
	CURRENCY_CD VARCHAR(10),
	TOTAL_AMT DECIMAL(24,6),
	USED_AMT DECIMAL(24,6),
	AVI_AMT DECIMAL(24,6),
	LIMIT_CONTROL_TYPE VARCHAR(10),
	TRAN_DATE VARCHAR(10),
	AGREEMENT_TERM DECIMAL(24,6),
	AGREEMENT_TERM_UNIT VARCHAR(10),
	PROJECT_STATUS VARCHAR(10),
	USER_NUM VARCHAR(10),
	ORG_NUM VARCHAR(10),
	CREATE_TIME TIMESTAMP(6),
	UPDATE_TIME TIMESTAMP(6),
	TRAN_SYSTEM VARCHAR(10),
	CRD_MAIN_NUM VARCHAR(40),
	CRD_MAIN_PRD VARCHAR(10)
);

comment on table TB_CRD_PROJECT is '项目协议表';

comment on column TB_CRD_PROJECT.PROJECT_NUM is '项目ID';

comment on column TB_CRD_PROJECT.CUSTOMER_NUM is '客户编号';

comment on column TB_CRD_PROJECT.CRD_DETAIL_NUM is '三级额度编号';

comment on column TB_CRD_PROJECT.CRD_DETAIL_PRD is '三级额度产品';

comment on column TB_CRD_PROJECT.PROJECT_NAME is '项目协议名称';

comment on column TB_CRD_PROJECT.PROJECT_TYPE is '项目类型（CD000172）';

comment on column TB_CRD_PROJECT.CURRENCY_CD is '币种';

comment on column TB_CRD_PROJECT.TOTAL_AMT is '总金额';

comment on column TB_CRD_PROJECT.USED_AMT is '已用金额';

comment on column TB_CRD_PROJECT.AVI_AMT is '可用金额';

comment on column TB_CRD_PROJECT.LIMIT_CONTROL_TYPE is '额度控制方式（CD000173）';

comment on column TB_CRD_PROJECT.TRAN_DATE is '申请日期';

comment on column TB_CRD_PROJECT.AGREEMENT_TERM is '协议期限';

comment on column TB_CRD_PROJECT.AGREEMENT_TERM_UNIT is '协议期限单位（CD000169）';

comment on column TB_CRD_PROJECT.PROJECT_STATUS is '项目状态';

comment on column TB_CRD_PROJECT.USER_NUM is '客户经理';

comment on column TB_CRD_PROJECT.ORG_NUM is '经办机构';

comment on column TB_CRD_PROJECT.CREATE_TIME is '创建时间';

comment on column TB_CRD_PROJECT.UPDATE_TIME is '修改时间';

comment on column TB_CRD_PROJECT.TRAN_SYSTEM is '经办系统';

comment on column TB_CRD_PROJECT.CRD_MAIN_NUM is '二级额度编号';

comment on column TB_CRD_PROJECT.CRD_MAIN_PRD is '二级额度品种';

