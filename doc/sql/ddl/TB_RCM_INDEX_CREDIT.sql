create table CLM.TB_RCM_INDEX_CREDIT
(
    QUOTA_INDEX_NUM VARCHAR(40) not null
        constraint PK_RCM_INDEX_CREDIT
            primary key,
    RANGE_REGION VARCHAR(10),
    RANGE_CUSTOMER VARCHAR(10),
    CURRENCY VARCHAR(400),
    RANGE_COUNTRY VARCHAR(400),
    RANGE_INDUSTRY VARCHAR(400),
    RANGE_PRODUCT VARCHAR(400),
    RANGE_TERM VARCHAR(400),
    RANGER_RISK_MITIGATION VARCHAR(10),
    CREATE_TIME TIMESTAMP(6),
    UPDATE_TIME TIMESTAMP(6),
    REMARK VARCHAR(50)
);

comment on table CLM.TB_RCM_INDEX_CREDIT is '信贷业务限额基础指标';

comment on column CLM.TB_RCM_INDEX_CREDIT.QUOTA_INDEX_NUM is '限额指标编号';

comment on column CLM.TB_RCM_INDEX_CREDIT.RANGE_REGION is '区域(1、不区分,2、区域内客户,3、区域外客户)';

comment on column CLM.TB_RCM_INDEX_CREDIT.RANGE_CUSTOMER is '客户类型(个人客户、法人客户、所有客户)';

comment on column CLM.TB_RCM_INDEX_CREDIT.CURRENCY is '币种(可多选，保存字段串用逗号分隔，为空默认人民币)';

comment on column CLM.TB_RCM_INDEX_CREDIT.RANGE_COUNTRY is '国别(可多选，保存字段串用逗号分隔，为空代表不考虑国别情况)';

comment on column CLM.TB_RCM_INDEX_CREDIT.RANGE_INDUSTRY is '行业(可多选，保存字段串用逗号分隔，保存行业小类，为空代表全行业)';

comment on column CLM.TB_RCM_INDEX_CREDIT.RANGE_PRODUCT is '产品(可多选，保存字段串用逗号分隔，保存最基本产品编号，为空代表全产品) ';

comment on column CLM.TB_RCM_INDEX_CREDIT.RANGE_TERM is '期限范围';

comment on column CLM.TB_RCM_INDEX_CREDIT.RANGER_RISK_MITIGATION is '风险缓释(0、不区分1、信用2、保证3、抵押4、质押5、保证保险)';

comment on column CLM.TB_RCM_INDEX_CREDIT.CREATE_TIME is '创建时间';

comment on column CLM.TB_RCM_INDEX_CREDIT.UPDATE_TIME is '更新时间';

comment on column CLM.TB_RCM_INDEX_CREDIT.REMARK is '备注';

