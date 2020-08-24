create table CLM.TB_RCM_INDEX_BANK
(
	QUOTA_INDEX_NUM VARCHAR(40) not null
		constraint PK_RCM_INDEX_BANK
			primary key,
	BUSINESS_TYPE VARCHAR(10),
	BUSS_SCENE VARCHAR(10),
	RANGE_CUSTOMER VARCHAR(10),
	CURRENCY VARCHAR(400),
	RANGE_COUNTRY VARCHAR(400),
	RANGE_PRODUCT VARCHAR(400),
	CREATE_TIME TIMESTAMP(6),
	UPDATE_TIME TIMESTAMP(6),
	REMARK VARCHAR(50)
);

comment on table CLM.TB_RCM_INDEX_BANK is '同业业务限额基础指标库';

comment on column CLM.TB_RCM_INDEX_BANK.QUOTA_INDEX_NUM is '限额指标编号';

comment on column CLM.TB_RCM_INDEX_BANK.BUSINESS_TYPE is '业务类型';

comment on column CLM.TB_RCM_INDEX_BANK.BUSS_SCENE is '业务场景';

comment on column CLM.TB_RCM_INDEX_BANK.RANGE_CUSTOMER is '客户类型(同业客户) 说明：目前只有同业客户，后继可能会有细分或者同业集团客户';

comment on column CLM.TB_RCM_INDEX_BANK.CURRENCY is '币种(可多选，保存字段串用逗号分隔，为空默认人民币)';

comment on column CLM.TB_RCM_INDEX_BANK.RANGE_COUNTRY is '国别(可多选，保存字段串用逗号分隔，为空代表不考虑国别情况)';

comment on column CLM.TB_RCM_INDEX_BANK.RANGE_PRODUCT is '产品(可多选，保存字段串用逗号分隔，保存最基本产品编号，为空代表全产品) ';

comment on column CLM.TB_RCM_INDEX_BANK.CREATE_TIME is '创建时间';

comment on column CLM.TB_RCM_INDEX_BANK.UPDATE_TIME is '更新时间';

comment on column CLM.TB_RCM_INDEX_BANK.REMARK is '备注';

