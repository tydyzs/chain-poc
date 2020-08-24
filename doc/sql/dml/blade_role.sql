#创建基本角色
delete from chain_role;
INSERT INTO `chain_role`(`id`, `tenant_id`, `parent_id`, `role_name`, `sort`, `role_alias`, `is_deleted`) VALUES (1183657990503268353, '000000', 0, '系统管理员', 1, 'administrator', 0);
INSERT INTO `chain_role`(`id`, `tenant_id`, `parent_id`, `role_name`, `sort`, `role_alias`, `is_deleted`) VALUES (1183658219222859778, '000000', 0, '查询用户', 2, 'user', 0);
commit;
