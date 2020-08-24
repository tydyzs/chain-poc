create table CLM.TB_RCM_NET_CAPITAL
(
    NET_CAPITAL_NUM VARCHAR(32) not null
        constraint PK_NET_CAPITAL
            primary key,
    USE_ORG_NUM VARCHAR(20) not null,
    USE_DATE VARCHAR(10) not null,
    NET_CAPITAL DECIMAL(18,2),
    NET_PRIMARY_CAPITAL DECIMAL(18,2),
    NET_ASSETS DECIMAL(18,2),
    NET_STATE VARCHAR(10) default 2,
    REMARK VARCHAR(400),
    USER_NUM VARCHAR(20),
    ORG_NUM VARCHAR(20),
    CREATE_TIME TIMESTAMP(6),
    UPDATE_TIME TIMESTAMP(6),
    constraint IDX_NET_CAPITAL
        unique (USE_ORG_NUM, USE_DATE)
);

comment on table CLM.TB_RCM_NET_CAPITAL is '资本信息配置表';

comment on column CLM.TB_RCM_NET_CAPITAL.NET_CAPITAL_NUM is '主键';

comment on column CLM.TB_RCM_NET_CAPITAL.USE_ORG_NUM is '生效机构';

comment on column CLM.TB_RCM_NET_CAPITAL.USE_DATE is '生效日期';

comment on column CLM.TB_RCM_NET_CAPITAL.NET_CAPITAL is '资本净额';

comment on column CLM.TB_RCM_NET_CAPITAL.NET_PRIMARY_CAPITAL is '一级资本净额';

comment on column CLM.TB_RCM_NET_CAPITAL.NET_ASSETS is '净资产';

comment on column CLM.TB_RCM_NET_CAPITAL.NET_STATE is '生效状态：CD000251（1:生效 2:失效）';

comment on column CLM.TB_RCM_NET_CAPITAL.REMARK is '备注';

comment on column CLM.TB_RCM_NET_CAPITAL.USER_NUM is '经办人';

comment on column CLM.TB_RCM_NET_CAPITAL.ORG_NUM is '经办机构';

comment on column CLM.TB_RCM_NET_CAPITAL.CREATE_TIME is '创建时间';

comment on column CLM.TB_RCM_NET_CAPITAL.UPDATE_TIME is '更新时间';

