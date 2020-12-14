/*
 Navicat Premium Data Transfer

 Source Server         : CloudMySql
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : haogege.icu:3306
 Source Schema         : xc_learning

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 23/10/2020 00:20:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xc_learning_course
-- ----------------------------
DROP TABLE IF EXISTS `xc_learning_course`;
CREATE TABLE `xc_learning_course`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `course_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程id',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `charge` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费规则',
  `price` float(8, 2) NULL DEFAULT NULL COMMENT '课程价格',
  `valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效性',
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选课状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `xc_learning_list_unique`(`course_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xc_learning_course
-- ----------------------------
INSERT INTO `xc_learning_course` VALUES ('40281f007541e616017541ee084f0000', '4028e58161bcf7f40161bcf8b77c0000', '49', NULL, NULL, NULL, NULL, NULL, '501001');

-- ----------------------------
-- Table structure for xc_task_his
-- ----------------------------
DROP TABLE IF EXISTS `xc_task_his`;
CREATE TABLE `xc_task_his`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务id',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  `task_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务类型',
  `mq_exchange` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交换机名称',
  `mq_routingkey` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'routingkey',
  `request_body` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务请求的内容',
  `version` int(0) NULL DEFAULT 0 COMMENT '乐观锁版本号',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务状态',
  `errormsg` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xc_task_his
-- ----------------------------
INSERT INTO `xc_task_his` VALUES ('4028858162959ce5', '2018-04-05 20:09:17', '2020-06-05 17:15:51', '2020-06-05 17:40:04', 'add_choosecourse', 'ex_learning_addchoosecourse', 'addchoosecourse', '{\"courseId\":\"4028e58161bcf7f40161bcf8b77c0000\",\"userId\":\"49\"}', NULL, '10201', NULL);

SET FOREIGN_KEY_CHECKS = 1;
