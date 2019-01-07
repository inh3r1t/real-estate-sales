-- auto-generated definition
create table bus_agent_company
(
  id            int auto_increment,
  name          varchar(100) null comment '中介公司名称',
  poll_code     varchar(50)  not null comment '注册码',
  phone         varchar(20)  null comment '公司电话',
  address       varchar(200) null comment '公司地址',
  state         int          null comment '状态：0-本公司，1-中介公司（与bus_role的type对应）',
  charge_person varchar(20)  null comment '公司负责人',
  constraint bus_agent_company_id_uindex
    unique (id),
  constraint bus_agent_company_poll_code_uindex
    unique (poll_code)
)
  comment '中介公司';

alter table bus_agent_company
  add primary key (id);

-- auto-generated definition
create table bus_customer
(
  id          int auto_increment,
  agent_id    int                                 null,
  name        varchar(50)                         null,
  phone_num   varchar(20)                         null,
  sex         int(1)    default 0                 null comment '0-male,1-female',
  create_time timestamp default CURRENT_TIMESTAMP null,
  update_time timestamp default CURRENT_TIMESTAMP null,
  constraint bus_customer_id_uindex
    unique (id)
);

alter table bus_customer
  add primary key (id);

-- auto-generated definition
create table bus_deal
(
  id                        int auto_increment,
  real_estate_id            int                                 null comment '楼盘ID',
  real_estate_name          varchar(200)                        null comment '楼盘名称',
  state                     int                                 null comment '状态：0-报备，1-预约，2-到访，3-认购，4-签约',
  customer_id               int                                 null comment '客户ID',
  customer_phone            varchar(20)                         null comment '客户电话',
  customer_name             varchar(20)                         null comment '客户姓名',
  report_user_id            int                                 null comment '报备人ID',
  report_user_phone         varchar(20)                         null comment '报备人手机',
  report_company            varchar(200)                        null comment '报备公司',
  appointment_time          timestamp                           null comment '预约时间',
  report_time               timestamp                           null comment '报备时间',
  arrive_time               timestamp                           null comment '到访时间',
  subscribe_time            timestamp                           null comment '认购时间',
  sign_time                 timestamp                           null comment '签约时间',
  manager_id                int                                 null comment '项目经理ID',
  manager_phone             varchar(20)                         null comment '项目经理手机',
  arrive_certify_photo_path varchar(500)                        null comment '到访照片',
  subscribe_money           varchar(50)                         null comment '认购金额',
  subscribe_commision       varchar(50)                         null comment '认购佣金',
  subscribe_photo_pahts     varchar(500)                        null comment '认购合同照片，用","分隔',
  sign_photo_paths          varchar(500)                        null comment '签约照片路径，用","分隔',
  sign_money                varchar(50)                         null comment '签约金额',
  appointment_operate_time  timestamp                           null comment '预约时间（操作）',
  report_operate_time       timestamp                           null comment '报备时间（操作）',
  arrive_operate_time       timestamp                           null comment '到访时间（操作）',
  subscribe_operate_time    timestamp                           null comment '认购时间（操作）',
  sign_operate_time         timestamp                           null comment '签约时间（操作）',
  create_time               timestamp default CURRENT_TIMESTAMP null,
  update_time               timestamp default CURRENT_TIMESTAMP null,
  constraint bus_deal_id_uindex
    unique (id)
)
  comment '订单';

alter table bus_deal
  add primary key (id);

-- auto-generated definition
create table bus_notify_msg
(
  id              int auto_increment,
  type            int                                 null comment '类型：0-报备信息，1-预约信息，2-认购信息，3-签约信息',
  deal_id         int                                 null,
  send_user_id    int                                 null,
  receive_user_id int                                 null,
  msg_content     varchar(1000)                       null,
  create_time     timestamp default CURRENT_TIMESTAMP null,
  update_time     timestamp default CURRENT_TIMESTAMP null,
  constraint bus_notify_msg_id_uindex
    unique (id)
);

alter table bus_notify_msg
  add primary key (id);

-- auto-generated definition
create table bus_real_estate
(
  id                int auto_increment,
  name              varchar(100)                        null comment '楼盘名称',
  summery           varchar(500)                        null comment '简介',
  address           varchar(300)                        null,
  sort_weight       int                                 null comment '排序权重，越大越靠前',
  commission        varchar(200)                        null comment '佣金
',
  manager_id        int                                 null comment '项目经理ID
',
  manager_name      varchar(50)                         null comment '项目经理名称',
  detail            text                                null comment '楼盘详情',
  is_top_recommend  bit                                 null comment '是否首页顶部推荐',
  is_list_recommend bit                                 null comment '是否首页列表推荐',
  create_time       timestamp default CURRENT_TIMESTAMP null,
  update_time       timestamp default CURRENT_TIMESTAMP null,
  constraint bus_real_estate_id_uindex
    unique (id)
);

alter table bus_real_estate
  add primary key (id);

-- auto-generated definition
create table bus_role
(
  id          int auto_increment,
  name        varchar(50)                         null,
  type        varchar(1)                          null comment '0-驻场经理，1-中介（与bus_agent_company的state对应）',
  create_time timestamp default CURRENT_TIMESTAMP not null,
  update_time timestamp default CURRENT_TIMESTAMP null,
  constraint bus_roles_id_uindex
    unique (id)
);

alter table bus_role
  add primary key (id);

-- auto-generated definition
create table bus_user
(
  id           int auto_increment,
  open_id      varchar(200)                        null comment '微信用户唯一标识',
  user_name    varchar(50)                         null,
  passwd       varchar(50)                         null,
  phone_num    varchar(15)                         null,
  poll_code    varchar(50)                         null comment '注册码',
  role_id      int                                 null,
  company_id   int                                 null comment '中介公司id',
  company_name varchar(100)                        null comment '中介公司名称',
  create_time  timestamp default CURRENT_TIMESTAMP not null,
  update_time  timestamp default CURRENT_TIMESTAMP null,
  constraint bus_user_open_id_uindex
    unique (open_id),
  constraint bus_users_id_uindex
    unique (id)
);

alter table bus_user
  add primary key (id);

