
--业务品种表
drop table TB_PAR_PRODUCT;
create table TB_PAR_PRODUCT (
    PRODUCT_Num    VARCHAR(36) NOT NULL,
    PRODUCT_NAME     VARCHAR(200) ,
    SUPER_PRODUCT_Num  VARCHAR(15),
    SUPER_PRODUCT_NAME   VARCHAR(50) ,
	product_type   VARCHAR(100) ,
    cust_type   VARCHAR(200) ,
	guarantee_type   VARCHAR(150) ,
    PRODUCT_DESCR        VARCHAR(1000) ,
    PRODUCT_STATUS_CD    VARCHAR(1) ,
    limit_used_type    VARCHAR(1) ,
    sys_no             VARCHAR(50) ,
    risk_exposure_type VARCHAR(10) ,
    product_targer     VARCHAR(10),
    inout_table        CHAR(10) ,
    vaild_time        DATE,
	user_num  VARCHAR(20),
    org_num   VARCHAR(20),
    update_user_num  VARCHAR(20),
    update_org_num   VARCHAR(20),
   CREATE_TIME        DATE ,
   UPDATE_TIME          DATE,
   is_deleted BIGINT NULL DEFAULT 0 ,
   constraint P_Key_1 primary key (PRODUCT_Num)
);
COMMENT ON TABLE TB_PAR_PRODUCT IS '业务品种表';
COMMENT ON COLUMN TB_PAR_PRODUCT.PRODUCT_Num IS '业务品种编号';
COMMENT ON COLUMN TB_PAR_PRODUCT.PRODUCT_NAME IS '业务品种名称';
COMMENT ON COLUMN TB_PAR_PRODUCT.SUPER_PRODUCT_Num IS '业务品种父类产品编号';
COMMENT ON COLUMN TB_PAR_PRODUCT.SUPER_PRODUCT_NAME IS '业务品种父类产品名称';
COMMENT ON COLUMN TB_PAR_PRODUCT.product_type IS '业务种类分类';
COMMENT ON COLUMN TB_PAR_PRODUCT.cust_type IS '客户主体分类';
COMMENT ON COLUMN TB_PAR_PRODUCT.guarantee_type IS '担保方式分类';
COMMENT ON COLUMN TB_PAR_PRODUCT.PRODUCT_DESCR IS '业务品种描述';
COMMENT ON COLUMN TB_PAR_PRODUCT.PRODUCT_STATUS_CD IS '业务品种状态';
COMMENT ON COLUMN TB_PAR_PRODUCT.limit_used_type IS '产品额度占用方式';
COMMENT ON COLUMN TB_PAR_PRODUCT.sys_no IS '来源系统';
COMMENT ON COLUMN TB_PAR_PRODUCT.risk_exposure_type IS '风险暴露类型';
COMMENT ON COLUMN TB_PAR_PRODUCT.product_targer IS '1、信贷类产品；2、同业类产品；3、同有产品；';
COMMENT ON COLUMN TB_PAR_PRODUCT.inout_table IS '表内外标志（CD000187）';
COMMENT ON COLUMN TB_PAR_PRODUCT.vaild_time IS '启用日期';
COMMENT ON COLUMN TB_PAR_PRODUCT.user_num IS '经办人编号';
COMMENT ON COLUMN TB_PAR_PRODUCT.org_num IS '经办机构编号';
COMMENT ON COLUMN TB_PAR_PRODUCT.update_user_num IS '修改人编号';
COMMENT ON COLUMN TB_PAR_PRODUCT.update_org_num IS '修改人机构编号';
COMMENT ON COLUMN TB_PAR_PRODUCT.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN TB_PAR_PRODUCT.UPDATE_TIME IS '更新时间';
COMMENT ON COLUMN TB_PAR_PRODUCT.is_deleted IS '是否已删除';


