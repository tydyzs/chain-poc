create table TB_RCM_CONFIG_HIS
(
    HIS_ID VARCHAR(40) not null
        constraint TB_RCM_CONFIG_HIS_PK
            unique,
    QUOTA_NUM VARCHAR(40) not null,
    QUOTA_NAME VARCHAR(100),
    QUOTA_INDEX_NUM VARCHAR(40),
    USE_ORG_NUM VARCHAR(20),
    QUOTA_USED_AMT DECIMAL(18,2),
    QUOTA_FREE_AMT DECIMAL(18,2),
    QUOTA_USED_RATIO DECIMAL(10,6),
    QUOTA_FREE_RATIO DECIMAL(10,6),
    QUOTA_TOTAL_SUM DECIMAL(18,2),
    QUOTA_TOTAL_TYPE VARCHAR(10),
    START_DATE VARCHAR(10),
    INVALID_DATE VARCHAR(10),
    QUOTA_STATE VARCHAR(10),
    HIS_FREQUENCY_A DECIMAL(8),
    HIS_FREQUENCY_B DECIMAL(8),
    HIS_FREQUENCY_C DECIMAL(8),
    HIS_FREQUENCY DECIMAL(8),
    EXPLAIN_INFO VARCHAR(400),
    INSPECT_USER_NUM VARCHAR(20),
    INSPECT_ORG_NUM VARCHAR(20),
    INSPECT_TIME TIMESTAMP(6),
    APPLY_STATE VARCHAR(10),
    USER_NUM VARCHAR(20),
    ORG_NUM VARCHAR(20),
    CREATE_TIME TIMESTAMP(6),
    UPDATE_TIME TIMESTAMP(6),
    REMARK VARCHAR(100)
);

comment on table TB_RCM_CONFIG_HIS is '限额配置历史表';

comment on column TB_RCM_CONFIG_HIS.HIS_ID is '历史表主键';

comment on column TB_RCM_CONFIG_HIS.QUOTA_NUM is '集中度限额编号';

comment on column TB_RCM_CONFIG_HIS.QUOTA_NAME is '限额名称';

comment on column TB_RCM_CONFIG_HIS.QUOTA_INDEX_NUM is '限额指标编号';

comment on column TB_RCM_CONFIG_HIS.USE_ORG_NUM is '生效机构';

comment on column TB_RCM_CONFIG_HIS.QUOTA_USED_AMT is '限额已用金额';

comment on column TB_RCM_CONFIG_HIS.QUOTA_FREE_AMT is '限额可用金额';

comment on column TB_RCM_CONFIG_HIS.QUOTA_USED_RATIO is '限额已用比率';

comment on column TB_RCM_CONFIG_HIS.QUOTA_FREE_RATIO is '限额可用比率';

comment on column TB_RCM_CONFIG_HIS.QUOTA_TOTAL_SUM is '限额总额';

comment on column TB_RCM_CONFIG_HIS.QUOTA_TOTAL_TYPE is '限额总额类型:CD000263';

comment on column TB_RCM_CONFIG_HIS.START_DATE is '限额生效日期';

comment on column TB_RCM_CONFIG_HIS.INVALID_DATE is '限额失效日期';

comment on column TB_RCM_CONFIG_HIS.QUOTA_STATE is '限额状态:CD000261';

comment on column TB_RCM_CONFIG_HIS.HIS_FREQUENCY_A is '观察值历史触发次数';

comment on column TB_RCM_CONFIG_HIS.HIS_FREQUENCY_B is '预警值历史触发次数';

comment on column TB_RCM_CONFIG_HIS.HIS_FREQUENCY_C is '控制值历史触发次数';

comment on column TB_RCM_CONFIG_HIS.HIS_FREQUENCY is '历史触发次数';

comment on column TB_RCM_CONFIG_HIS.EXPLAIN_INFO is '补充说明';

comment on column TB_RCM_CONFIG_HIS.INSPECT_USER_NUM is '复核人';

comment on column TB_RCM_CONFIG_HIS.INSPECT_ORG_NUM is '复核机构';

comment on column TB_RCM_CONFIG_HIS.INSPECT_TIME is '复核日期';

comment on column TB_RCM_CONFIG_HIS.APPLY_STATE is '申请状态:CD**';

comment on column TB_RCM_CONFIG_HIS.USER_NUM is '经办人';

comment on column TB_RCM_CONFIG_HIS.ORG_NUM is '经办机构';

comment on column TB_RCM_CONFIG_HIS.CREATE_TIME is '创建时间';

comment on column TB_RCM_CONFIG_HIS.UPDATE_TIME is '更新时间';

comment on column TB_RCM_CONFIG_HIS.REMARK is '备注';

