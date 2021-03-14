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
-- Table structure for wf_base
-- ----------------------------
DROP TABLE IF EXISTS `wf_base`;
CREATE TABLE `wf_base`  (
        `ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
        `versions` int(11) NULL DEFAULT NULL,
        `CREATE_DATE` datetime NULL DEFAULT NULL,
        `CREATE_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
        `UPDATE_DATE` datetime NULL DEFAULT NULL,
        `UPDATE_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
        `APPLY_DATE` datetime NOT NULL,
        `APPLY_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
        `APPLYER_DEPART` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
        `CATEGORY_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
        `LEVEL` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
        `NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
        `PROJECT_NO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
        `STATUS` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
        `WORKFLOW_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
        `WORKFLOW_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
        `BUSINESS_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
        `USER_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
        PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;
