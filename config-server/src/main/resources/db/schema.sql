DROP TABLE IF EXISTS `properties`;
CREATE TABLE `properties`(
    id                     INT UNSIGNED AUTO_INCREMENT        NOT NULL COMMENT '非业务主键' PRIMARY KEY,
    app_name               VARCHAR(50)                        NOT NULL COMMENT 'app-name',
    properties_id          INT UNSIGNED                       NOT NULL COMMENT '配置id',
    properties_name        VARCHAR(50)                        NOT NULL COMMENT '配置文件的名称',
    properties_description VARCHAR(50)                        NOT NULL COMMENT '注释 描述',
    content                VARCHAR(1000)                      NOT NULL COMMENT '配置文件的内容 使用LinkedHashMap类型的JSON格式字符串',
    state                  TINYINT                            NOT NULL COMMENT '0 已修改,未审核 1 审核中 2 发布状态',
    version                INT                                NOT NULL COMMENT '版本号',
    environment            VARCHAR(10)                        NOT NULL COMMENT '当前只有一个环境 dev',
    create_time            DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    update_time            DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间',
    UNIQUE KEY `uniq_id_version` (properties_id, version)
) ENGINE = InnoDB;


DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log`(
    id                INT UNSIGNED AUTO_INCREMENT        NOT NULL COMMENT '非业务主键' PRIMARY KEY,
    app_name          VARCHAR(50)                        NOT NULL COMMENT 'app-name 该字段冗余 方便查看操作记录',
    properties_id     INT UNSIGNED                       NOT NULL COMMENT '所操作的配置文件的id',
    operation_version INT UNSIGNED                       NOT NULL COMMENT '所操作的版本',
    operation_type    VARCHAR(10)                        NOT NULL COMMENT '操作类型 remove-删除配置文件, add-新增配置文件, update-修改配置文件',
    operation_user_id VARCHAR(20)                        NOT NULL COMMENT '操作者',
    create_time       DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
) ENGINE = InnoDB;