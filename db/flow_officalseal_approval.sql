/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : active4j_dev

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2020-12-20 18:56:18
*/
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `flow_officalseal_approval`
-- ----------------------------
DROP TABLE IF EXISTS `flow_officalseal_approval`;
CREATE TABLE `flow_officalseal_approval` (
  `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `VERSIONS` int DEFAULT '0' COMMENT '版本号',
  `CREATE_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `CREATE_DATE` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  `BOOKDATE` datetime DEFAULT NULL COMMENT '借用时间',
  `START_DAY` datetime DEFAULT NULL COMMENT '开始日期',
  `END_DAY` datetime DEFAULT NULL COMMENT '结束日期',
  `SEALNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '公章名称',
  `DEPARTMENTNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '科室',
  `USERNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '借用人',
  `USEUNIT` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '主送单位',
  `CONTENT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内容',
  `COMMIT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `APPLY_STATUS` int DEFAULT '0' COMMENT '审核状态',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of flow_officalseal_approval
-- ----------------------------
INSERT INTO `flow_officalseal_approval` VALUES ('478654aeeaaba3758caa6d76c98e35a7', '0', 'hello', '2020-12-18 17:38:32', null, null, '2020-12-18 00:00:00', '2020-12-18 00:00:00', '科室1', 'jinxin', '科室2', '审批', 'test', '-1');
