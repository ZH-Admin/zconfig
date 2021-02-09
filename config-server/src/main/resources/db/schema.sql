DROP DATABASE IF EXISTS config_server;
CREATE DATABASE config_server;
use config_server;

DROP TABLE IF EXISTS `properties`;
CREATE TABLE `properties`
(
    id           INT UNSIGNED AUTO_INCREMENT        NOT NULL COMMENT '非业务主键' PRIMARY KEY,
    app_name     VARCHAR(50)                        NOT NULL COMMENT 'app-name',
    data_id      VARCHAR(50)                        NOT NULL COMMENT '配置id',
    description  VARCHAR(50)                        NOT NULL COMMENT '注释 描述',
    content      VARCHAR(1000)                      NOT NULL COMMENT '配置文件的内容 使用LinkedHashMap类型的JSON格式字符串',
    state        TINYINT                            NOT NULL COMMENT '0 已修改,未审核 1 审核中 2 发布状态',
    app_version  INT                                NOT NULL COMMENT '应用版本号',
    data_version INT                                NOT NULL COMMENT '数据版本号',
    environment  VARCHAR(10)                        NOT NULL COMMENT '当前只有一个环境 dev',
    create_time  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    update_time  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间',
    UNIQUE KEY `uniq_id_version` (app_name, data_id, data_version)
) ENGINE = InnoDB
  CHARSET = 'utf8';

DROP TABLE IF EXISTS `config_server_discovery`;
CREATE TABLE config_server_discovery
(
    hostname          VARCHAR(128) NOT NULL COMMENT '主机名称',
    ip                VARCHAR(15)  NOT NULL COMMENT 'ip地址',
    port              INT          NOT NULL COMMENT '端口号',
    last_renew_time   DATETIME     NOT NULL COMMENT '上一次续约时间',
    renew_time_period BIGINT(11)   NOT NULL COMMENT '续约的时间周期 单位ms'
) ENGINE = InnoDB
  CHARSET = 'utf8';