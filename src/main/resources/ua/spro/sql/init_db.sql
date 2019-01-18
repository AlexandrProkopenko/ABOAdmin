#create database if not exists abo;
#use abo;

create table departments
(
  department_id int auto_increment
    primary key,
  cl_department varchar(30) null
);

create table users
(
  user_id int auto_increment
    primary key,
  login varchar(30) not null,
  user_password varchar(30) not null
);
insert into users values(null, 'null', 'null');
insert into users values(null, 'SPro', '1111');
insert into users values(null, 'Orest', 'orest');
insert into users values(null, 'Eva', 'eva');
insert into users values(null, 'Love', 'love');
insert into users values(null, 'Marina', 'marina');
insert into users values(null, 'Sasha St', 'sasha');

create table histories
(
  history_id int auto_increment
    primary key,
  date_h     datetime     null,
  note       varchar(255) null,
  user_id    int not null,
  foreign key (user_id) references users (user_id)
);

create table statuses
(
  status_id int auto_increment
    primary key,
  cl_status varchar(30) null
);

create table clients
(
  client_id     int auto_increment
    primary key,
  child_name    varchar(30)     null,
  birthday      date            null,
  parent_name   varchar(30)     null,
  phone         varchar(25)     null,
  location      varchar(30)     null,
  department_id int default '1' not null,
  status_id     int default '1' not null,

  constraint clients_ibfk_1
  foreign key (status_id) references statuses (status_id),
  constraint clients_ibfk_2
  foreign key (department_id) references departments (department_id)
);

create index department_id
  on clients (department_id);

create index status_id
  on clients (status_id);

create table clients_history
(
  client_id  int not null,
  history_id int not null,
  constraint clients_client_id_fk
  foreign key (client_id) references clients (client_id),
  constraint histories_history_id_fk
  foreign key (history_id) references histories (history_id)
);

create table tasks
(
  task_id     int auto_increment primary key,
  end_date    datetime     null,
  executor_id    int not null,
  done boolean,
  foreign key (executor_id) references users (user_id)
);

create table histories_tasks
(
  history_id  int not null,
  task_id int not null,
  constraint histories_history_id_fk_1
  foreign key (history_id) references histories (history_id),
  constraint tasks_task_id_fk
  foreign key (task_id) references tasks (task_id)
);

insert into statuses values(null, 'Новий');
insert into statuses values(null, 'Цікавляться');
insert into statuses values(null, 'Будуть на МК');
insert into statuses values(null, 'Були на МК');
insert into statuses values(null, 'Навчаються');
insert into statuses values(null, 'Старички');
insert into statuses values(null, 'Ходили раніше');
insert into statuses values(null, 'Пасивний');
insert into statuses values(null, 'Відмовились');
insert into statuses values(null, 'Всі');


