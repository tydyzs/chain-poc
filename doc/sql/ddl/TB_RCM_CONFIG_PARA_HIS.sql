create table TB_RCM_CONFIG_PARA_HIS
(
    HIS_ID VARCHAR(40) not null
        constraint TB_RCM_CONFIG_PARA_HIS_PK
            unique,
    SUB_PARA_NUM VARCHAR(40) not null,
    QUOTA_NUM VARCHAR(40),
    QUOTA_INDEX_NUM VARCHAR(40),
    USE_ORG_NUM VARCHAR(20),
    QUOTA_LEVEL VARCHAR(10),
    QUOTA_CONTROL_AMT DECIMAL(18,2),
    QUOTA_CONTROL_RATIO DECIMAL(10,6),
    QUOTA_CONTROL_TYPE VARCHAR(10),
    CONTROL_NODE VARCHAR(10),
    NODE_MEASURE VARCHAR(10),
    MEASURE_LEVEL VARCHAR(10),
    EXPLAIN_INFO VARCHAR(400),
    USER_NUM VARCHAR(20),
    ORG_NUM VARCHAR(20),
    CREATE_TIME TIMESTAMP(6),
    UPDATE_TIME TIMESTAMP(6),
    REMARK VARCHAR(50),
    MAIN_HIS_ID VARCHAR(40)
);

comment on table TB_RCM_CONFIG_PARA_HIS is '限额管控参数历史表';

comment on column TB_RCM_CONFIG_PARA_HIS.HIS_ID is '历史表主键';

comment on column TB_RCM_CONFIG_PARA_HIS.SUB_PARA_NUM is '分项参数编号';

comment on column TB_RCM_CONFIG_PARA_HIS.QUOTA_NUM is '集中度限额编号';

comment on column TB_RCM_CONFIG_PARA_HIS.QUOTA_INDEX_NUM is '限额指标编号';

comment on column TB_RCM_CONFIG_PARA_HIS.USE_ORG_NUM is '生效机构';

comment on column TB_RCM_CONFIG_PARA_HIS.QUOTA_LEVEL is '阈值层级:CD000223';

comment on column TB_RCM_CONFIG_PARA_HIS.QUOTA_CONTROL_AMT is '阈值（余额）';

comment on column TB_RCM_CONFIG_PARA_HIS.QUOTA_CONTROL_RATIO is '阈值（占比）';

comment on column TB_RCM_CONFIG_PARA_HIS.QUOTA_CONTROL_TYPE is '阈值类型:CD000258';

comment on column TB_RCM_CONFIG_PARA_HIS.CONTROL_NODE is '限额管控节点:CD000259';

comment on column TB_RCM_CONFIG_PARA_HIS.NODE_MEASURE is '管控节点的应对措施:CD000260';

comment on column TB_RCM_CONFIG_PARA_HIS.MEASURE_LEVEL is '应对措施等级:CD000265';

comment on column TB_RCM_CONFIG_PARA_HIS.EXPLAIN_INFO is '补充说明';

comment on column TB_RCM_CONFIG_PARA_HIS.USER_NUM is '经办人';

comment on column TB_RCM_CONFIG_PARA_HIS.ORG_NUM is '经办机构';

comment on column TB_RCM_CONFIG_PARA_HIS.CREATE_TIME is '创建时间';

comment on column TB_RCM_CONFIG_PARA_HIS.UPDATE_TIME is '更新时间';

comment on column TB_RCM_CONFIG_PARA_HIS.REMARK is '备注';

comment on column TB_RCM_CONFIG_PARA_HIS.MAIN_HIS_ID is '主表的主键';

