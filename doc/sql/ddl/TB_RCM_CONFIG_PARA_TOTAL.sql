create table TB_RCM_CONFIG_PARA_TOTAL
(
    SUB_PARA_NUM VARCHAR(40) not null
        constraint TB_RCM_CONFIG_PARA_TOTAL_PK
            unique,
    QUOTA_NUM VARCHAR(40),
    QUOTA_INDEX_NUM VARCHAR(40),
    USE_ORG_NUM VARCHAR(20),
    QUOTA_LEVEL VARCHAR(10),
    QUOTA_CONTROL_AMT DECIMAL(24,2),
    QUOTA_CONTROL_RATIO DECIMAL(10,6),
    QUOTA_CONTROL_TYPE VARCHAR(10),
    CONTROL_NODE VARCHAR(10),
    NODE_MEASURE VARCHAR(10),
    MEASURE_LEVEL VARCHAR(10),
    EXPLAIN_INFO VARCHAR(400),
    TOTAL_MONTH VARCHAR(10),
    TOTAL_YEAR VARCHAR(10),
    USER_NUM VARCHAR(20),
    ORG_NUM VARCHAR(20),
    CREATE_TIME TIMESTAMP(6),
    UPDATE_TIME TIMESTAMP(6),
    REMARK VARCHAR(100)
);

comment on table TB_RCM_CONFIG_PARA_TOTAL is '限额参数配置信息统计表';

comment on column TB_RCM_CONFIG_PARA_TOTAL.SUB_PARA_NUM is '分项参数编号';

comment on column TB_RCM_CONFIG_PARA_TOTAL.QUOTA_NUM is '集中度限额编号';

comment on column TB_RCM_CONFIG_PARA_TOTAL.QUOTA_INDEX_NUM is '限额指标编号';

comment on column TB_RCM_CONFIG_PARA_TOTAL.USE_ORG_NUM is '生效机构';

comment on column TB_RCM_CONFIG_PARA_TOTAL.QUOTA_LEVEL is '阈值层级';

comment on column TB_RCM_CONFIG_PARA_TOTAL.QUOTA_CONTROL_AMT is '阈值（余额）';

comment on column TB_RCM_CONFIG_PARA_TOTAL.QUOTA_CONTROL_RATIO is '阈值（占比）';

comment on column TB_RCM_CONFIG_PARA_TOTAL.QUOTA_CONTROL_TYPE is '阈值类型';

comment on column TB_RCM_CONFIG_PARA_TOTAL.CONTROL_NODE is '限额管控节点';

comment on column TB_RCM_CONFIG_PARA_TOTAL.NODE_MEASURE is '管控节点的应对措施';

comment on column TB_RCM_CONFIG_PARA_TOTAL.MEASURE_LEVEL is '应对措施等级';

comment on column TB_RCM_CONFIG_PARA_TOTAL.EXPLAIN_INFO is '补充说明';

comment on column TB_RCM_CONFIG_PARA_TOTAL.TOTAL_MONTH is '月份';

comment on column TB_RCM_CONFIG_PARA_TOTAL.TOTAL_YEAR is '年份';

comment on column TB_RCM_CONFIG_PARA_TOTAL.USER_NUM is '经办人';

comment on column TB_RCM_CONFIG_PARA_TOTAL.ORG_NUM is '经办机构';

comment on column TB_RCM_CONFIG_PARA_TOTAL.CREATE_TIME is '创建时间';

comment on column TB_RCM_CONFIG_PARA_TOTAL.UPDATE_TIME is '更新时间';

comment on column TB_RCM_CONFIG_PARA_TOTAL.REMARK is '备注';

