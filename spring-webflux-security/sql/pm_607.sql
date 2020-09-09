/*
 Navicat Premium Data Transfer

 Source Server         : 116.63.178.111
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 116.63.178.111:3306
 Source Schema         : pm_607

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 09/09/2020 16:14:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pm_dept
-- ----------------------------
DROP TABLE IF EXISTS `pm_dept`;
CREATE TABLE `pm_dept`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `parent_id` int(0) NULL DEFAULT 0 COMMENT '父部门id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门描述',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `create_by` int(0) NULL DEFAULT NULL,
  `update_by` int(0) NULL DEFAULT NULL,
  `is_deleted` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  INDEX `dept_parent_id_index`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pm_dept
-- ----------------------------
INSERT INTO `pm_dept` VALUES (1, 0, '管理部门', '管理所有部门', '2020-09-07 06:33:28', '2020-09-07 06:33:28', 1, 1, 0);

-- ----------------------------
-- Table structure for pm_menu
-- ----------------------------
DROP TABLE IF EXISTS `pm_menu`;
CREATE TABLE `pm_menu`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `parent_id` int(0) NULL DEFAULT 0 COMMENT '父菜单id',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名权限名',
  `menu_url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单url',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单标识',
  `sort` int(0) NULL DEFAULT 0 COMMENT '排序,从大到小',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `create_by` int(0) NULL DEFAULT NULL,
  `update_by` int(0) NULL DEFAULT NULL,
  `is_deleted` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `menu_name`(`menu_name`) USING BTREE,
  UNIQUE INDEX `menu_url`(`menu_url`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pm_menu
-- ----------------------------
INSERT INTO `pm_menu` VALUES (1, 0, '系统设置', '/system', NULL, 0, '2020-09-07 08:04:31', '2020-09-07 08:04:31', NULL, NULL, 0);
INSERT INTO `pm_menu` VALUES (2, 1, '用户管理', '/userManage', NULL, 1, '2020-09-07 08:05:08', '2020-09-09 06:57:17', NULL, NULL, 0);

-- ----------------------------
-- Table structure for pm_role
-- ----------------------------
DROP TABLE IF EXISTS `pm_role`;
CREATE TABLE `pm_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色标识',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `create_by` int(0) NULL DEFAULT NULL,
  `update_by` int(0) NULL DEFAULT NULL,
  `is_deleted` int(0) NULL DEFAULT NULL,
  `menu_id` json NULL COMMENT '[1,2,3]的格式存储菜单主键',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pm_role
-- ----------------------------
INSERT INTO `pm_role` VALUES (1, '管理员', '管理员角色', '2020-09-07 06:33:28', '2020-09-07 08:05:46', 1, 1, 0, '[1, 2]');

-- ----------------------------
-- Table structure for pm_user
-- ----------------------------
DROP TABLE IF EXISTS `pm_user`;
CREATE TABLE `pm_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号名称',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `password` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `salt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `dept_id` int(0) NULL DEFAULT NULL COMMENT '部门id',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色id',
  `status` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '停用,启用',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `create_by` int(0) NULL DEFAULT NULL,
  `update_by` int(0) NULL DEFAULT NULL,
  `is_deleted` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `user_status_index`(`status`) USING BTREE,
  CONSTRAINT `pm_user_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `pm_dept` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `pm_user_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `pm_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pm_user
-- ----------------------------
INSERT INTO `pm_user` VALUES (1, 'admin', '管理员', '$2a$10$jbCoxNdEMAMob8X.xG85zO/VG.mdmfvIsTNo8hESF5NjyghusQPKe', 'cq]fh|$c', 1, 1, '1', '2020-09-07 06:33:28', '2020-09-08 08:56:20', 1, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
