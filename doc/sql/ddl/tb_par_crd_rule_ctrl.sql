drop index type_index;

drop table tb_par_crd_rule_ctrl;

--==============================================================
-- table: tb_par_crd_rule_ctrl
--==============================================================
create table tb_par_crd_rule_ctrl (
   serial_id          varchar(32)            not null,
   event_type_cd      varchar(40)            not null,
   tran_type_cd       varchar(10),
   check_item         varchar(100)            not null,
   check_flag         varchar(10)            not null,
   check_method       varchar(100)            not null,
   create_time        timestamp,
   update_time        timestamp,
   constraint p_key_1 primary key (serial_id)
);

comment on table tb_par_crd_rule_ctrl is
'额度管控规则表';

comment on column tb_par_crd_rule_ctrl.serial_id is
'主键';

comment on column tb_par_crd_rule_ctrl.event_type_cd is
'205，302，日终到期，日终对账';

comment on column tb_par_crd_rule_ctrl.tran_type_cd is
'交易类型(cd000197)';

comment on column tb_par_crd_rule_ctrl.check_item is
'检查项';

comment on column tb_par_crd_rule_ctrl.check_flag is
'是否检查 0不检查，1检查';

comment on column tb_par_crd_rule_ctrl.check_method is
'检查方法';

comment on column tb_par_crd_rule_ctrl.create_time is
'创建时间';

comment on column tb_par_crd_rule_ctrl.update_time is
'修改时间';

--==============================================================
-- index: type_index
--==============================================================
create index type_index on tb_par_crd_rule_ctrl (
   event_type_cd      asc
);
