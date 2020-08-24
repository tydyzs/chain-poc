create table CLM.TB_RCM_INDEX
(
    QUOTA_INDEX_NUM VARCHAR(40) not null
        constraint PK_RCM_INDEX
            primary key,
    QUOTA_INDEX_NAME VARCHAR(100),
    QUOTA_INDEX_TYPE VARCHAR(10),
    QUOTA_INDEX_STATE VARCHAR(10),
    QUOTA_TYPE VARCHAR(10),
    QUOTA_INDEX_CALIBER VARCHAR(10),
    QUOTA_INDEX_RANGE VARCHAR(10),
    COMPUTING_METHOD VARCHAR(10),
    COMPUTING_TARGET VARCHAR(10),
    EXPLAIN_INFO VARCHAR(400),
    USER_NUM VARCHAR(20),
    ORG_NUM VARCHAR(20),
    CREATE_TIME TIMESTAMP(6),
    UPDATE_TIME TIMESTAMP(6),
    REMARK VARCHAR(50)
);

comment on table CLM.TB_RCM_INDEX is '限额基础指标库';

comment on column CLM.TB_RCM_INDEX.QUOTA_INDEX_NUM is '限额指标编号';

comment on column CLM.TB_RCM_INDEX.QUOTA_INDEX_NAME is '限额指标名称';

comment on column CLM.TB_RCM_INDEX.QUOTA_INDEX_TYPE is '限额指标类型(1,同业限额指标; 2,非同业限额指标)';

comment on column CLM.TB_RCM_INDEX.QUOTA_INDEX_STATE is '指标状态(1、启动 ；2、停用)';

comment on column CLM.TB_RCM_INDEX.QUOTA_TYPE is '限额类型(1-监管限额;2-我行限额)';

comment on column CLM.TB_RCM_INDEX.EXPLAIN_INFO is '补充说明';

comment on column CLM.TB_RCM_INDEX.USER_NUM is '经办人';

comment on column CLM.TB_RCM_INDEX.ORG_NUM is '经办机构';

comment on column CLM.TB_RCM_INDEX.CREATE_TIME is '创建时间';

comment on column CLM.TB_RCM_INDEX.UPDATE_TIME is '更新时间';

comment on column CLM.TB_RCM_INDEX.REMARK is '备注';

comment on column CLM.TB_RCM_INDEX.QUOTA_INDEX_CALIBER is '统计口径(1-贷款敞口余额；2-授信余额；3-批复敞口金额)';

comment on column CLM.TB_RCM_INDEX.QUOTA_INDEX_RANGE is '分析维度(0-全维度 1-条件范围)';

comment on column CLM.TB_RCM_INDEX.COMPUTING_METHOD is '限额计算方式1、统计口径/资本净额；2、统计口径/一级资本净额；3、统计口径/我行授信总额；4、金额上限';

comment on column CLM.TB_RCM_INDEX.COMPUTING_TARGET is '限额计算对象';

