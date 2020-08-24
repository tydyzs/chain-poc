create table TB_RCM_CONFIG_TOTAL
(
    TOTAL_MONTH VARCHAR(10) not null,
    TOTAL_YEAR VARCHAR(10) not null,
    QUOTA_NUM VARCHAR(40) not null,
    QUOTA_USED_AMT DECIMAL(24,2),
    QUOTA_FREE_AMT DECIMAL(24,2),
    QUOTA_USED_RATIO DECIMAL(10,6),
    QUOTA_FREE_RATIO DECIMAL(10,6),
    QUOTA_TOTAL_SUM DECIMAL(24,2),
    QUOTA_TOTAL_TYPE VARCHAR(10),
    YEAR_TO_YEAR_AMT DECIMAL(24,2),
    YEAR_TO_YEAR_RATIO DECIMAL(10,6),
    LAST_YEAR_AMT DECIMAL(24,2),
    LAST_YEAR_RATIO DECIMAL(10,6),
    MONTH_TO_MONTH_AMT DECIMAL(24,2),
    MONTH_TO_MONTH_RATIO DECIMAL(10,6),
    HIS_FREQUENCY_A DECIMAL(8),
    HIS_FREQUENCY_B DECIMAL(8),
    HIS_FREQUENCY_C DECIMAL(8),
    HIS_FREQUENCY DECIMAL(8),
    HIS_FREQUENCY_A2 DECIMAL(8),
    HIS_FREQUENCY_B2 DECIMAL(8),
    HIS_FREQUENCY_C2 DECIMAL(8),
    HIS_FREQUENCY_YEAR DECIMAL(8),
    HIS_FREQUENCY_A3 DECIMAL(8),
    HIS_FREQUENCY_B3 DECIMAL(8),
    HIS_FREQUENCY_C3 DECIMAL(8),
    HIS_FREQUENCY_MONTH DECIMAL(8),
    NET_CAPITAL DECIMAL(24,2),
    NET_PRIMARY_CAPITAL DECIMAL(24,2),
    NET_ASSETS DECIMAL(24,2),
    USE_ORG_NUM VARCHAR(20),
    USER_NUM VARCHAR(20),
    ORG_NUM VARCHAR(20),
    CREATE_TIME TIMESTAMP(6),
    UPDATE_TIME TIMESTAMP(6),
    REMARK VARCHAR(100),
    OBSERVE_VALUE DECIMAL(30,6),
    WARN_VALUE DECIMAL(30,6),
    CONTROL_VALUE DECIMAL(30,6),
    OBSERVE_VALUE_TYPE VARCHAR(10),
    WARN_VALUE_TYPE VARCHAR(10),
    CONTROL_VALUE_TYPE VARCHAR(10),
    OBSERVE_NODE_MEASURE VARCHAR(10),
    WARN_NODE_MEASURE VARCHAR(10),
    CONTROL_NODE_MEASURE VARCHAR(10),
    QUOTA_INDEX_NUM VARCHAR(40),
    QUOTA_INDEX_NAME VARCHAR(100),
    QUOTA_NAME VARCHAR(100),
    constraint PK_TB_RCM_CONFIG_TOTAL
        primary key (QUOTA_NUM, TOTAL_YEAR, TOTAL_MONTH)
);

comment on table TB_RCM_CONFIG_TOTAL is '限额详细统计表';

comment on column TB_RCM_CONFIG_TOTAL.TOTAL_MONTH is '月份';

comment on column TB_RCM_CONFIG_TOTAL.TOTAL_YEAR is '年份';

comment on column TB_RCM_CONFIG_TOTAL.QUOTA_NUM is '集中度限额编号';

comment on column TB_RCM_CONFIG_TOTAL.QUOTA_USED_AMT is '限额已用金额';

comment on column TB_RCM_CONFIG_TOTAL.QUOTA_FREE_AMT is '限额可用金额';

comment on column TB_RCM_CONFIG_TOTAL.QUOTA_USED_RATIO is '限额已用比率';

comment on column TB_RCM_CONFIG_TOTAL.QUOTA_FREE_RATIO is '限额可用比率';

comment on column TB_RCM_CONFIG_TOTAL.QUOTA_TOTAL_SUM is '限额总额';

comment on column TB_RCM_CONFIG_TOTAL.QUOTA_TOTAL_TYPE is '限额总额类型';

comment on column TB_RCM_CONFIG_TOTAL.YEAR_TO_YEAR_AMT is '比同期增长量（同比）';

comment on column TB_RCM_CONFIG_TOTAL.YEAR_TO_YEAR_RATIO is '比同期增长率（同比）';

comment on column TB_RCM_CONFIG_TOTAL.LAST_YEAR_AMT is '比年初增长量';

comment on column TB_RCM_CONFIG_TOTAL.LAST_YEAR_RATIO is '比年初增长率';

comment on column TB_RCM_CONFIG_TOTAL.MONTH_TO_MONTH_AMT is '比上期增长量（环比）';

comment on column TB_RCM_CONFIG_TOTAL.MONTH_TO_MONTH_RATIO is '比上期增长率（环比）';

comment on column TB_RCM_CONFIG_TOTAL.HIS_FREQUENCY_A is '观察值历史触发次数';

comment on column TB_RCM_CONFIG_TOTAL.HIS_FREQUENCY_B is '预警值历史触发次数';

comment on column TB_RCM_CONFIG_TOTAL.HIS_FREQUENCY_C is '控制值历史触发次数';

comment on column TB_RCM_CONFIG_TOTAL.HIS_FREQUENCY is '历史累计触发次数';

comment on column TB_RCM_CONFIG_TOTAL.HIS_FREQUENCY_A2 is '观察值本年触发次数';

comment on column TB_RCM_CONFIG_TOTAL.HIS_FREQUENCY_B2 is '预警值本年触发次数';

comment on column TB_RCM_CONFIG_TOTAL.HIS_FREQUENCY_C2 is '控制值本年触发次数';

comment on column TB_RCM_CONFIG_TOTAL.HIS_FREQUENCY_YEAR is '本年累计触发次数';

comment on column TB_RCM_CONFIG_TOTAL.HIS_FREQUENCY_A3 is '观察值本月触发次数';

comment on column TB_RCM_CONFIG_TOTAL.HIS_FREQUENCY_B3 is '预警值本月触发次数';

comment on column TB_RCM_CONFIG_TOTAL.HIS_FREQUENCY_C3 is '控制值本月触发次数';

comment on column TB_RCM_CONFIG_TOTAL.HIS_FREQUENCY_MONTH is '当月累计触发次数';

comment on column TB_RCM_CONFIG_TOTAL.NET_CAPITAL is '当月资本净额';

comment on column TB_RCM_CONFIG_TOTAL.NET_PRIMARY_CAPITAL is '当月一级资本净额';

comment on column TB_RCM_CONFIG_TOTAL.NET_ASSETS is '当月净资产';

comment on column TB_RCM_CONFIG_TOTAL.USE_ORG_NUM is '生效机构';

comment on column TB_RCM_CONFIG_TOTAL.USER_NUM is '经办人';

comment on column TB_RCM_CONFIG_TOTAL.ORG_NUM is '经办机构';

comment on column TB_RCM_CONFIG_TOTAL.CREATE_TIME is '创建时间';

comment on column TB_RCM_CONFIG_TOTAL.UPDATE_TIME is '更新时间';

comment on column TB_RCM_CONFIG_TOTAL.REMARK is '备注';

comment on column TB_RCM_CONFIG_TOTAL.OBSERVE_VALUE is '观察值';

comment on column TB_RCM_CONFIG_TOTAL.WARN_VALUE is '预警值';

comment on column TB_RCM_CONFIG_TOTAL.CONTROL_VALUE is '控制值';

comment on column TB_RCM_CONFIG_TOTAL.OBSERVE_VALUE_TYPE is '观察值类型';

comment on column TB_RCM_CONFIG_TOTAL.WARN_VALUE_TYPE is '预警值类型';

comment on column TB_RCM_CONFIG_TOTAL.CONTROL_VALUE_TYPE is '控制值类型';

comment on column TB_RCM_CONFIG_TOTAL.OBSERVE_NODE_MEASURE is '观察值应对措施';

comment on column TB_RCM_CONFIG_TOTAL.WARN_NODE_MEASURE is '预警值应对措施';

comment on column TB_RCM_CONFIG_TOTAL.CONTROL_NODE_MEASURE is '控制值应对措施';

comment on column TB_RCM_CONFIG_TOTAL.QUOTA_INDEX_NUM is '限额指标编号';

comment on column TB_RCM_CONFIG_TOTAL.QUOTA_INDEX_NAME is '限额指标名称';

comment on column TB_RCM_CONFIG_TOTAL.QUOTA_NAME is '限额名称';

