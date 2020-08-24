#修改字典表的字典值字段,改为varchar类型
ALTER TABLE `chain_dict` MODIFY COLUMN `dict_key` varchar(20) NULL DEFAULT NULL COMMENT '字典值' AFTER `code`;
#添加两个字段
alter table chain_dict add column code_cn  varchar(30);
COMMENT ON COLUMN BLADE_DICT.code_cn IS '字典码中文名';
alter table chain_dict add column dict_value_en  varchar(10);
COMMENT ON COLUMN BLADE_DICT.dict_value_en IS '字典值';

