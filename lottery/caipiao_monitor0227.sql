/*
Navicat MySQL Data Transfer

Source Server         : localhost5.7
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : caipiao_monitor

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2017-02-27 04:42:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for adm_menu
-- ----------------------------
DROP TABLE IF EXISTS `adm_menu`;
CREATE TABLE `adm_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `system_code` varchar(100) DEFAULT NULL COMMENT '子系统代码',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '链接',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父栏目id',
  `state` smallint(6) DEFAULT NULL COMMENT '状态(50正常，-50删除)',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `type` smallint(6) DEFAULT NULL COMMENT '类型 1:子系统, 2:菜单项 3:菜单',
  `icon_rsurl` varchar(200) DEFAULT NULL COMMENT '菜单图标',
  `tree_code` varchar(50) DEFAULT NULL COMMENT '树形代码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='系统菜单';

-- ----------------------------
-- Table structure for ds_caipiao_config
-- ----------------------------
DROP TABLE IF EXISTS `ds_caipiao_config`;
CREATE TABLE `ds_caipiao_config` (
  `caipiao_code` varchar(20) NOT NULL DEFAULT '',
  `caipiao_name` varchar(50) DEFAULT NULL,
  `monitor_web_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`caipiao_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ds_caipiao_except
-- ----------------------------
DROP TABLE IF EXISTS `ds_caipiao_except`;
CREATE TABLE `ds_caipiao_except` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `webcode` varchar(20) DEFAULT NULL,
  `caipiao_code` varchar(20) DEFAULT NULL,
  `qishu` varchar(20) DEFAULT NULL,
  `memo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ds_caipiao_log
-- ----------------------------
DROP TABLE IF EXISTS `ds_caipiao_log`;
CREATE TABLE `ds_caipiao_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `md5` varchar(50) DEFAULT NULL,
  `caipiao_code` varchar(255) DEFAULT NULL,
  `messge` mediumtext,
  `create_time` datetime DEFAULT NULL,
  `memo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `md5` (`md5`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=688169 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ds_caipiao_qishu
-- ----------------------------
DROP TABLE IF EXISTS `ds_caipiao_qishu`;
CREATE TABLE `ds_caipiao_qishu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `web_code` varchar(20) DEFAULT NULL,
  `caipiao_code` varchar(20) DEFAULT NULL,
  `qishu` varchar(50) DEFAULT NULL,
  `result` varchar(50) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `web_code` (`web_code`) USING BTREE,
  KEY `caipiao_code` (`caipiao_code`) USING BTREE,
  KEY `qishu` (`qishu`) USING BTREE,
  KEY `state` (`state`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1312541 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ds_caipiao_result
-- ----------------------------
DROP TABLE IF EXISTS `ds_caipiao_result`;
CREATE TABLE `ds_caipiao_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `caipiao_code` varchar(20) DEFAULT NULL,
  `qishu` varchar(50) DEFAULT NULL,
  `result` varchar(50) DEFAULT NULL,
  `create_web_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `caipiao_code` (`caipiao_code`,`qishu`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=443054 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ds_caipiao_web_config
-- ----------------------------
DROP TABLE IF EXISTS `ds_caipiao_web_config`;
CREATE TABLE `ds_caipiao_web_config` (
  `caipiao_code` varchar(20) DEFAULT NULL,
  `caipiao_web_code` varchar(20) DEFAULT NULL,
  `web_name` varchar(50) DEFAULT NULL,
  `state` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fenfen_check_ids
-- ----------------------------
DROP TABLE IF EXISTS `fenfen_check_ids`;
CREATE TABLE `fenfen_check_ids` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fenfen_lottery_id` bigint(21) DEFAULT NULL COMMENT '分分彩注单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fenfen_none_check_ids
-- ----------------------------
DROP TABLE IF EXISTS `fenfen_none_check_ids`;
CREATE TABLE `fenfen_none_check_ids` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fenfen_lottery_id` bigint(21) DEFAULT NULL COMMENT '分分彩注单ID',
  `msg` varchar(100) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44159 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lottery_log
-- ----------------------------
DROP TABLE IF EXISTS `lottery_log`;
CREATE TABLE `lottery_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_log_check_id` bigint(20) DEFAULT NULL,
  `fenfen_lottery_id` bigint(20) DEFAULT NULL COMMENT '下注ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `play_result` mediumtext COMMENT '所投结果',
  `type_code` varchar(50) DEFAULT NULL COMMENT '彩票种类代码',
  `item_code` mediumtext COMMENT '彩票项目代码',
  `msg` varchar(50) DEFAULT NULL COMMENT '信息',
  `result` varchar(50) DEFAULT NULL COMMENT '开奖结果',
  `state` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=630 DEFAULT CHARSET=utf8 COMMENT='彩票警告日志';

-- ----------------------------
-- Table structure for lottery_log_check
-- ----------------------------
DROP TABLE IF EXISTS `lottery_log_check`;
CREATE TABLE `lottery_log_check` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fenfen_lottery_id` bigint(20) DEFAULT NULL COMMENT '下注ID',
  `bill_no` varchar(50) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `play_result` mediumtext COMMENT '所投结果',
  `type_code` varchar(50) DEFAULT NULL COMMENT '彩票种类代码',
  `item_code` mediumtext COMMENT '彩票项目代码',
  `msg` varchar(50) DEFAULT NULL COMMENT '信息',
  `result` varchar(50) DEFAULT NULL COMMENT '开奖结果',
  `number` varchar(255) DEFAULT NULL COMMENT '注数',
  `multiple` int(10) DEFAULT NULL COMMENT '倍数',
  `per_amount` decimal(12,2) DEFAULT NULL COMMENT '注单金额',
  `win` decimal(12,4) DEFAULT NULL COMMENT '赢',
  `lose` decimal(12,2) DEFAULT NULL COMMENT '输',
  `check_result` varchar(50) DEFAULT NULL COMMENT '分析结果',
  `state` smallint(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127728 DEFAULT CHARSET=utf8 COMMENT='彩票日志';

-- ----------------------------
-- Table structure for lottery_parent_regular
-- ----------------------------
DROP TABLE IF EXISTS `lottery_parent_regular`;
CREATE TABLE `lottery_parent_regular` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_type_id` bigint(20) DEFAULT NULL COMMENT '彩票种类ID',
  `name` varchar(100) DEFAULT NULL COMMENT '规则名称',
  `state` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8 COMMENT='中奖父规则';

-- ----------------------------
-- Table structure for lottery_periods
-- ----------------------------
DROP TABLE IF EXISTS `lottery_periods`;
CREATE TABLE `lottery_periods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_code` varchar(50) DEFAULT NULL COMMENT '彩票代码',
  `web_code` varchar(50) DEFAULT NULL COMMENT '网站代码',
  `periods` varchar(50) DEFAULT NULL COMMENT '彩票期数',
  `result` varchar(50) DEFAULT NULL COMMENT '开奖结果',
  `state` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='彩票期数';

-- ----------------------------
-- Table structure for lottery_regular
-- ----------------------------
DROP TABLE IF EXISTS `lottery_regular`;
CREATE TABLE `lottery_regular` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_type_id` bigint(20) DEFAULT NULL COMMENT '彩票种类ID',
  `parent_regular_id` bigint(20) DEFAULT NULL COMMENT '父规则ID',
  `item_code` varchar(20) DEFAULT NULL COMMENT '彩票种类项目代码',
  `item_desc` varchar(50) DEFAULT NULL COMMENT '彩票种类项目说明',
  `bonus` decimal(12,0) DEFAULT NULL COMMENT '单注奖金',
  `lottery_regular` varchar(500) DEFAULT NULL COMMENT '中奖规则',
  `state` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=283 DEFAULT CHARSET=utf8 COMMENT='中奖规则';

-- ----------------------------
-- Table structure for lottery_result
-- ----------------------------
DROP TABLE IF EXISTS `lottery_result`;
CREATE TABLE `lottery_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `caipiao_code` varchar(50) DEFAULT NULL COMMENT '彩票代码',
  `periods` varchar(50) DEFAULT NULL COMMENT '彩票期数',
  `result` varchar(50) DEFAULT NULL COMMENT '开奖结果',
  `web_code` varchar(50) DEFAULT NULL COMMENT '网站代码',
  `state` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='彩票结果';

-- ----------------------------
-- Table structure for lottery_sub_regular
-- ----------------------------
DROP TABLE IF EXISTS `lottery_sub_regular`;
CREATE TABLE `lottery_sub_regular` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_regular_id` bigint(20) DEFAULT NULL COMMENT '中奖规则ID',
  `grade` smallint(6) DEFAULT NULL COMMENT '中奖等级',
  `bonus` decimal(12,4) DEFAULT NULL COMMENT '单注奖金',
  `other_bonus` decimal(12,4) DEFAULT NULL,
  `lottery_regular` varchar(500) DEFAULT NULL COMMENT '中奖规则',
  `state` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=468 DEFAULT CHARSET=utf8 COMMENT='中奖子规则';

-- ----------------------------
-- Table structure for lottery_type
-- ----------------------------
DROP TABLE IF EXISTS `lottery_type`;
CREATE TABLE `lottery_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type_code` varchar(50) DEFAULT NULL COMMENT '彩票种类代码',
  `type_name` varchar(50) DEFAULT NULL COMMENT '彩票种类名称',
  `other_type_code` varchar(50) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='彩票种类';

-- ----------------------------
-- Table structure for lottery_type_web
-- ----------------------------
DROP TABLE IF EXISTS `lottery_type_web`;
CREATE TABLE `lottery_type_web` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_type_id` bigint(20) DEFAULT NULL COMMENT '彩票种类ID',
  `lottery_web_id` bigint(20) DEFAULT NULL COMMENT '网站ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='彩票种类与网站';

-- ----------------------------
-- Table structure for lottery_web
-- ----------------------------
DROP TABLE IF EXISTS `lottery_web`;
CREATE TABLE `lottery_web` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `web_code` varchar(50) DEFAULT NULL COMMENT '网站代码',
  `web_url` varchar(200) DEFAULT NULL COMMENT '网站URL',
  `web_desc` varchar(500) DEFAULT NULL COMMENT '网站描述',
  `state` smallint(6) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='彩票网站';
