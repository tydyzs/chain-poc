#先删除字段
#ALTER TABLE chain_dept DROP org_state, drop org_attr, drop manage_org_type, drop corp_org_code, drop org_code_certifi, drop pb_org_lc, drop start_date, drop end_date, drop province_code, drop aera_code, drop org_address, drop tel_no, drop zip_code, drop org_level, drop create_time, drop update_time;
#添加字段
alter table chain_dept add org_state varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构状态';
alter table chain_dept add org_attr varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构属性';
alter table chain_dept add manage_org_type varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理机构类型';
alter table chain_dept add corp_org_code varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '法人机构';
alter table chain_dept add org_code_certifi varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织机构代码';
alter table chain_dept add pb_org_lc varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人行机构信用证';
alter table chain_dept add start_date date  COMMENT '开业日期';
alter table chain_dept add end_date date  COMMENT '结业日期';
alter table chain_dept add province_code varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省区代码';
alter table chain_dept add aera_code varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地区代码';
alter table chain_dept add org_address varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构地址';
alter table chain_dept add tel_no varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话';
alter table chain_dept add zip_code varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮政编码';
alter table chain_dept add org_level varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构级别';
alter table chain_dept add create_time date  COMMENT '创建日期';
alter table chain_dept add update_time date  COMMENT '维护日期';
--添加字段‘是否成员行’
--alter table BLADE_DEPT add column DEPT_TYPE  VARCHAR(4);
--COMMENT ON COLUMN BLADE_DEPT.DEPT_TYPE IS '机构类型  1省联社 2成员行';
