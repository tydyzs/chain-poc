
CREATE TABLE "DB2INST1"."TB_FRONT_SERVICE_CONFIG"  (
		  "ID" BIGINT NOT NULL ,
		  "TERMINAL_CODE" BIGINT WITH DEFAULT NULL ,
		  "SERVICE_ROLE" VARCHAR(10 OCTETS) WITH DEFAULT NULL ,
		  "SERVICE_CODE" VARCHAR(50 OCTETS) NOT NULL ,
		  "SERVICE_NAME" VARCHAR(100 OCTETS) NOT NULL ,
		  "SERVICE_ADAPTER" VARCHAR(100 OCTETS) WITH DEFAULT NULL ,
		  "INVOKE_MODE" VARCHAR(10 OCTETS) WITH DEFAULT NULL ,
		  "INVOKE_API" VARCHAR(200 OCTETS) WITH DEFAULT NULL ,
		  "ENCODE_MODE" VARCHAR(10 OCTETS) WITH DEFAULT NULL ,
		  "MESSAGE_STYLE" VARCHAR(10 OCTETS) WITH DEFAULT NULL ,
		  "TIMEOUT" BIGINT WITH DEFAULT NULL ,
		  "TRANSACTION_MODE" VARCHAR(10 OCTETS) WITH DEFAULT NULL ,
		  "VERSION" VARCHAR(10 OCTETS) WITH DEFAULT NULL ,
		  "STATUS" BIGINT NOT NULL ,
		  "REMARK" VARCHAR(200 OCTETS) WITH DEFAULT NULL ,
		  "CREATE_DEPT" BIGINT WITH DEFAULT NULL ,
		  "CREATE_USER" BIGINT WITH DEFAULT NULL ,
		  "CREATE_TIME" DATE WITH DEFAULT NULL ,
		  "UPDATE_USER" BIGINT WITH DEFAULT NULL ,
		  "UPDATE_TIME" DATE WITH DEFAULT NULL ,
		  "TENANT_ID" VARCHAR(12 OCTETS) WITH DEFAULT NULL ,
		  "IS_DELETED" BIGINT WITH DEFAULT NULL )
		 IN "USERSPACE1"
		 ORGANIZE BY ROW  ;
COMMENT ON TABLE "DB2INST1"."TB_FRONT_SERVICE_CONFIG" IS '前置服务配置表' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."CREATE_DEPT" IS '创建机构' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."CREATE_TIME" IS '创建时间' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."CREATE_USER" IS '创建人' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."ENCODE_MODE" IS '编码方式' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."ID" IS '主键' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."INVOKE_API" IS '调用接口' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."INVOKE_MODE" IS '调用方式' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."IS_DELETED" IS '是否删除' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."MESSAGE_STYLE" IS '报文风格' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."REMARK" IS '备注' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."SERVICE_ADAPTER" IS '服务适配器' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."SERVICE_CODE" IS '服务代码' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."SERVICE_NAME" IS '服务名称' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."SERVICE_ROLE" IS '服务角色' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."STATUS" IS '是否启用' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."TENANT_ID" IS '租户编号' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."TERMINAL_CODE" IS '终端代码' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."TIMEOUT" IS '超时毫秒' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."TRANSACTION_MODE" IS '事务方式' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."UPDATE_TIME" IS '更新时间' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."UPDATE_USER" IS '更新人' ;
COMMENT ON COLUMN "DB2INST1"."TB_FRONT_SERVICE_CONFIG"."VERSION" IS '版本号' ;
ALTER TABLE "DB2INST1"."TB_FRONT_SERVICE_CONFIG"
	ADD CONSTRAINT "TB_FRONT_SERVICE_CONFIG_PK" PRIMARY KEY
		("ID") ;
GRANT CONTROL ON TABLE "DB2INST1"."TB_FRONT_SERVICE_CONFIG" TO USER "DB2INST1"  ;
