完善了网上的拼图小程序，加入了数据库，可以进行用户的注册和登录，登录后，每个用户有自己的存档进程，会自动读取上一次的进度。
该项目中，没有数据库，需要自己加入mysql数据库，数据表的连接代码：DatabaseConnector.java，将这里面的连接参数改为自己的数据库和数据表即可。

游戏进度表：create table game_progress
(
    id           int auto_increment
        primary key,
    user_id      int          null,
    game_state   text         null,
    image_path   varchar(255) null,
    last_updated timestamp    null,
    step         int          null
);

用户表：create table user
(
    username   varchar(50) null,
    password   int         null,
    id         int auto_increment
        primary key,
    created_at datetime    null comment '创建时间',
    constraint user_id_uindex
        unique (id)
)
    comment '密码和账号';
