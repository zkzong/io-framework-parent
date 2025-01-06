CREATE TABLE t_user
(
    id          int AUTO_INCREMENT COMMENT 'id'
        PRIMARY KEY,
    user_name   varchar(20)                         NOT NULL COMMENT '姓名',
    age         int                                 NOT NULL COMMENT '年龄',
    create_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    update_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
)
    COMMENT '用户表';

