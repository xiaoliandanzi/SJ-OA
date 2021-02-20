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
-- Table structure for flow_tmpcard_approval
-- ----------------------------
DROP TABLE IF EXISTS `flow_tmpcard_approval`;
CREATE TABLE `flow_tmpcard_approval`  (
      `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
      `VERSIONS` int(11) NULL DEFAULT 0 COMMENT '版本号',
      `CREATE_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
      `CREATE_DATE` datetime NOT NULL COMMENT '创建时间',
      `UPDATE_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
      `UPDATE_DATE` datetime NULL DEFAULT NULL COMMENT '更新时间',
      `BOOKDATE` datetime NULL DEFAULT NULL COMMENT '借用时间',
      `USE_DAY` datetime NULL DEFAULT NULL COMMENT '使用日期',
      `DEPARTMENTNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '使用科室',
      `CARDNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '餐卡名称',
      `USERNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '借用人',
      `QUANTITY` int(11) NULL DEFAULT NULL COMMENT '数量',
      `REASON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '使用事由',
      `COMMIT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
      `APPLY_STATUS` int(11) NULL DEFAULT 0 COMMENT '审核状态',
      `JSON_DATA` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '多规格数据',
      `RETURN_FLAG` int(11) NULL DEFAULT 1 COMMENT '归还状态',
      PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;
