/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MariaDB
 Source Server Version : 100307
 Source Host           : 127.0.0.1:3306
 Source Schema         : shiro

 Target Server Type    : MariaDB
 Target Server Version : 100307
 File Encoding         : 65001

 Date: 03/07/2019 14:59:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(20) NOT NULL,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `res_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `res_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `permission` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, NULL, '系统管理', 'menu', 'system', NULL);
INSERT INTO `sys_permission` VALUES (2, 1, '用户管理', 'menu', 'systemUserList', '/user/userList');
INSERT INTO `sys_permission` VALUES (3, 1, '角色管理', 'menu', 'systemRole', '/roles');
INSERT INTO `sys_permission` VALUES (4, NULL, '一级菜单', 'menu', 'menu', NULL);
INSERT INTO `sys_permission` VALUES (5, 4, '二级菜单1', 'menu', 'menuXxx', '/xxx');
INSERT INTO `sys_permission` VALUES (6, 4, '二级菜单2', 'menu', 'menuYyy', '/yyy');
INSERT INTO `sys_permission` VALUES (7, 2, '用户添加', 'button', 'systemUserAdd', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL,
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '用户管理员');
INSERT INTO `sys_role` VALUES (2, '普通用户');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (1, 2);
INSERT INTO `sys_role_permission` VALUES (1, 3);
INSERT INTO `sys_role_permission` VALUES (1, 4);
INSERT INTO `sys_role_permission` VALUES (1, 5);
INSERT INTO `sys_role_permission` VALUES (1, 6);
INSERT INTO `sys_role_permission` VALUES (1, 7);
INSERT INTO `sys_role_permission` VALUES (2, 1);
INSERT INTO `sys_role_permission` VALUES (2, 2);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL,
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `full_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'zhangsan', '张三', '86fb1b048301461bdc71d021d2af3f97', '1');
INSERT INTO `sys_user` VALUES (2, 'lisi', '李四', 'c9351e5cf153923f052ebe1462cca93a', '2');
INSERT INTO `sys_user` VALUES (3, 'wangwu', '王五', '92262648696eae1b0a321cbd2b238bf2', '3');
INSERT INTO `sys_user` VALUES (4, 'user1', '用户1', '86fb1b048301461bdc71d021d2af3f97', '4');
INSERT INTO `sys_user` VALUES (5, 'user2', '用户2', '86fb1b048301461bdc71d021d2af3f97', '5');
INSERT INTO `sys_user` VALUES (6, 'user3', '用户3', '86fb1b048301461bdc71d021d2af3f97', '5');
INSERT INTO `sys_user` VALUES (7, 'user4', '用户4', '86fb1b048301461bdc71d021d2af3f97', '4');
INSERT INTO `sys_user` VALUES (8, 'user5', '用户5', '86fb1b048301461bdc71d021d2af3f97', '4');
INSERT INTO `sys_user` VALUES (9, 'user6', '用户6', '86fb1b048301461bdc71d021d2af3f97', '4');
INSERT INTO `sys_user` VALUES (10, 'user7', '用户7', '86fb1b048301461bdc71d021d2af3f97', '4');
INSERT INTO `sys_user` VALUES (11, 'user8', '用户8', '86fb1b048301461bdc71d021d2af3f97', '4');
INSERT INTO `sys_user` VALUES (12, 'user9', '用户9', '86fb1b048301461bdc71d021d2af3f97', '4');
INSERT INTO `sys_user` VALUES (13, 'user10', '用户10', '86fb1b048301461bdc71d021d2af3f97', '4');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

SET FOREIGN_KEY_CHECKS = 1;
