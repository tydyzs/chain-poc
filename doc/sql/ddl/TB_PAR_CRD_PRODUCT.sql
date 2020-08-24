--额度产品-业务品种
drop table TB_PAR_CRD_PRODUCT;
create table TB_PAR_CRD_PRODUCT(
    id    VARCHAR(36) NOT NULL ,
    crd_product_num    VARCHAR(30) ,
    PRODUCT_Num     VARCHAR(30),
    cust_type  VARCHAR(10),
    main_guarantee_type   VARCHAR(50),
    product_type   VARCHAR(50),
    PROJECT_TYPE   VARCHAR(50),
    IS_LOW_RISK   VARCHAR(2),
    CRD_TYPE   VARCHAR(50),
    user_num  VARCHAR(20),
    org_num   VARCHAR(20),
    CREATE_TIME        DATE  ,
    UPDATE_TIME        DATE ,
    is_deleted BIGINT NULL DEFAULT 0 ,
    constraint P_Key_1 primary key (id)
);
COMMENT ON TABLE TB_PAR_CRD_PRODUCT IS '额度产品业务品种关联关系表';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.id IS '主键';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.crd_product_num IS '额度产品编号';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.PRODUCT_Num IS '业务品种编号';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.cust_type IS '客户类型';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.PROJECT_TYPE IS '项目类型';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.IS_LOW_RISK IS '是否低风险';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.CRD_TYPE IS '额度类型';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.main_guarantee_type IS '主要担保方式';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.product_type IS '业务种类（个人消费 个人经营 流贷 固贷）';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.user_num IS '创建人id';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.org_num IS '创建机构id';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.UPDATE_TIME IS '更新时间';
COMMENT ON COLUMN TB_PAR_CRD_PRODUCT.is_deleted IS '是否已删除';
commit;
