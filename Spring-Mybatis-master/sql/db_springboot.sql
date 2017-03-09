
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for auth
-- ----------------------------
DROP TABLE IF EXISTS `auth`;
CREATE TABLE `auth` (
  `id` int(11) NOT NULL,
  `auth_code` varchar(255) DEFAULT NULL,
  `auth_name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth
-- ----------------------------
INSERT INTO `auth` VALUES ('1', 'AUTH_CKBG', '查看表格', '0');
INSERT INTO `auth` VALUES ('2', 'AUTH_XGSJ', '修改数据', '0');
INSERT INTO `auth` VALUES ('3', 'AUTH_SCSJ', '删除数据', '0');
INSERT INTO `auth` VALUES ('4', 'AUTH_PTYH', '普通用户', '0');

-- ----------------------------
-- Table structure for auth_resource
-- ----------------------------
DROP TABLE IF EXISTS `auth_resource`;
CREATE TABLE `auth_resource` (
  `id` int(11) NOT NULL,
  `auth_id` int(11) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_resource
-- ----------------------------
INSERT INTO `auth_resource` VALUES ('1', '1', '1');
INSERT INTO `auth_resource` VALUES ('2', '1', '2');
INSERT INTO `auth_resource` VALUES ('3', '1', '3');
INSERT INTO `auth_resource` VALUES ('4', '4', '3');

-- ----------------------------
-- Table structure for resources
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `id` int(11) NOT NULL,
  `resource_code` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resources
-- ----------------------------
INSERT INTO `resources` VALUES ('1', 'test-datagrid', '/datagrid/test-datagrid', '0');
INSERT INTO `resources` VALUES ('2', 'get-datagrid-data', '/datagrid/get-datagrid-data', '0');
INSERT INTO `resources` VALUES ('3', 'index', '/index', '0');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `role_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '人事操作员', 'ROLE_RSCZY');
INSERT INTO `role` VALUES ('2', '人事管理员', 'ROLE_RSGLY');
INSERT INTO `role` VALUES ('3', '财务操作员', 'ROLE_CWCZY');
INSERT INTO `role` VALUES ('4', '财务管理员', 'ROLE_CWGLY');
INSERT INTO `role` VALUES ('5', '用户', 'ROLE_USER');

-- ----------------------------
-- Table structure for role_auth
-- ----------------------------
DROP TABLE IF EXISTS `role_auth`;
CREATE TABLE `role_auth` (
  `id` int(11) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `auth_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_auth
-- ----------------------------
INSERT INTO `role_auth` VALUES ('1', '1', '1');
INSERT INTO `role_auth` VALUES ('2', '1', '2');
INSERT INTO `role_auth` VALUES ('3', '1', '3');
INSERT INTO `role_auth` VALUES ('4', '2', '2');
INSERT INTO `role_auth` VALUES ('5', '3', '4');
INSERT INTO `role_auth` VALUES ('6', '1', '4');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `brithday` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'aa', '123456', '1', '2016-07-25');
INSERT INTO `user` VALUES ('2', 'bb', 'admin123', '0', '2016-07-25');
INSERT INTO `user` VALUES ('3', 'cc', 'cc', '1', '2016-07-25');
INSERT INTO `user` VALUES ('4', 'dd', 'dd', '1', '2016-07-25');
INSERT INTO `user` VALUES ('5', 'ee', 'ee', '0', '2016-07-25');
INSERT INTO `user` VALUES ('6', 'ff', 'ff', '1', '2016-07-25');
INSERT INTO `user` VALUES ('7', 'gg', 'gg', '1', '2016-07-25');
INSERT INTO `user` VALUES ('8', 'aa1', 'aa', '0', '2016-07-25');
INSERT INTO `user` VALUES ('9', 'ww', 'ww', '0', '2016-07-25');
INSERT INTO `user` VALUES ('10', 'tt', 'tt', '0', '2016-07-25');
INSERT INTO `user` VALUES ('11', 'yy', 'yy', '1', '2016-07-25');
INSERT INTO `user` VALUES ('12', 'uu', 'uu', '0', '2016-07-25');
INSERT INTO `user` VALUES ('13', 'ii', 'ii', '0', '2016-07-25');
INSERT INTO `user` VALUES ('14', 'RR', 'RR', '1', '2016-07-25');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
INSERT INTO `user_role` VALUES ('2', '1', '2');
INSERT INTO `user_role` VALUES ('3', '2', '3');
INSERT INTO `user_role` VALUES ('4', '2', '4');
INSERT INTO `user_role` VALUES ('5', '1', '5');
INSERT INTO `user_role` VALUES ('6', '2', '5');
