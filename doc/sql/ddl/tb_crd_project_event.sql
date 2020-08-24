create table TB_CRD_PROJECT_EVENT
(
	TRAN_SEQ_SN VARCHAR(50) not null
		constraint PK_CRD_PROJECT_EVENT
			primary key,
	OP_TYPE VARCHAR(2),
	PROJECT_NUM VARCHAR(40),
	CUSTOMER_NUM VARCHAR(40),
	CRD_MAIN_PRD VARCHAR(40),
	CRD_MAIN_NUM VARCHAR(40),
	CRD_DETAIL_PRD VARCHAR(10),
	CRD_DETAIL_NUM VARCHAR(40),
	PROJECT_NAME VARCHAR(200),
	PROJECT_TYPE VARCHAR(10),
	CURRENCY_CD VARCHAR(10),
	TOTAL_AMT DECIMAL(18,2),
	USED_AMT DECIMAL(18,2),
	AVI_AMT DECIMAL(18,2),
	LIMIT_CONTROL_TYPE VARCHAR(10),
	AGREEMENT_TERM DECIMAL(18,2),
	AGREEMENT_TERM_UNIT VARCHAR(10),
	PROJECT_STATUS VARCHAR(10),
	TRAN_DATE VARCHAR(10),
	TRAN_SYSTEM VARCHAR(10),
	USER_NUM VARCHAR(10),
	ORG_NUM VARCHAR(10),
	CREATE_TIME TIMESTAMP(6),
	UPDATE_TIME TIMESTAMP(6)
);

comment on table TB_CRD_PROJECT_EVENT is '信贷实时-项目协议表';

comment on column TB_CRD_PROJECT_EVENT.TRAN_SEQ_SN is '交易流水号';

comment on column TB_CRD_PROJECT_EVENT.OP_TYPE is '操作类型（01 新增02 调整 03删除）';

comment on column TB_CRD_PROJECT_EVENT.PROJECT_NUM is '项目协议编号';

comment on column TB_CRD_PROJECT_EVENT.CUSTOMER_NUM is '客户编号';

comment on column TB_CRD_PROJECT_EVENT.CRD_MAIN_NUM is '额度三级编号';

comment on column TB_CRD_PROJECT_EVENT.CRD_DETAIL_PRD is '额度三级产品号';

comment on column TB_CRD_PROJECT_EVENT.CRD_DETAIL_NUM is '额度三级编号';

comment on column TB_CRD_PROJECT_EVENT.PROJECT_NAME is '项目协议名称';

comment on column TB_CRD_PROJECT_EVENT.PROJECT_TYPE is '项目类型（CD000172）';

comment on column TB_CRD_PROJECT_EVENT.CURRENCY_CD is '币种';

comment on column TB_CRD_PROJECT_EVENT.TOTAL_AMT is '总金额';

comment on column TB_CRD_PROJECT_EVENT.USED_AMT is '已用金额';

comment on column TB_CRD_PROJECT_EVENT.AVI_AMT is '可用金额';

comment on column TB_CRD_PROJECT_EVENT.LIMIT_CONTROL_TYPE is '额度控制方式（CD000173）';

comment on column TB_CRD_PROJECT_EVENT.AGREEMENT_TERM is '协议期限';

comment on column TB_CRD_PROJECT_EVENT.AGREEMENT_TERM_UNIT is '协议期限单位（CD000169）';

comment on column TB_CRD_PROJECT_EVENT.PROJECT_STATUS is '项目状态';

comment on column TB_CRD_PROJECT_EVENT.TRAN_DATE is '交易日期';

comment on column TB_CRD_PROJECT_EVENT.TRAN_SYSTEM is '经办系统';

comment on column TB_CRD_PROJECT_EVENT.USER_NUM is '客户经理';

comment on column TB_CRD_PROJECT_EVENT.ORG_NUM is '经办机构';

comment on column TB_CRD_PROJECT_EVENT.CREATE_TIME is '创建时间';

comment on column TB_CRD_PROJECT_EVENT.UPDATE_TIME is '修改时间';

