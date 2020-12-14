/*
 Navicat Premium Data Transfer

 Source Server         : CloudMySql
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : haogege.icu:3306
 Source Schema         : xc_order

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 23/10/2020 00:20:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xc_orders
-- ----------------------------
DROP TABLE IF EXISTS `xc_orders`;
CREATE TABLE `xc_orders`  (
  `order_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `initial_price` float(8, 2) NULL DEFAULT NULL COMMENT '定价',
  `price` float(8, 2) NULL DEFAULT NULL COMMENT '交易价',
  `start_time` datetime(0) NOT NULL COMMENT '起始时间',
  `end_time` datetime(0) NOT NULL COMMENT '结束时间',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易状态',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `details` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单明细',
  PRIMARY KEY (`order_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xc_orders
-- ----------------------------

-- ----------------------------
-- Table structure for xc_orders_detail
-- ----------------------------
DROP TABLE IF EXISTS `xc_orders_detail`;
CREATE TABLE `xc_orders_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `item_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品id',
  `item_num` int(0) NOT NULL COMMENT '商品数量',
  `item_price` float(8, 2) NOT NULL COMMENT '金额',
  `valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程有效性',
  `start_time` datetime(0) NOT NULL COMMENT '课程开始时间',
  `end_time` datetime(0) NOT NULL COMMENT '课程结束时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `xc_orders_detail_unique`(`order_number`, `item_id`) USING BTREE,
  CONSTRAINT `fk_xc_orders_detail_order_number` FOREIGN KEY (`order_number`) REFERENCES `xc_orders` (`order_number`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xc_orders_detail
-- ----------------------------

-- ----------------------------
-- Table structure for xc_orders_pay
-- ----------------------------
DROP TABLE IF EXISTS `xc_orders_pay`;
CREATE TABLE `xc_orders_pay`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `pay_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付系统订单号',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '交易状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `xc_orders_pay_order_number_unique`(`order_number`) USING BTREE,
  UNIQUE INDEX `xc_orders_pay_pay_number_unique`(`pay_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xc_orders_pay
-- ----------------------------

-- ----------------------------
-- Table structure for xc_task
-- ----------------------------
DROP TABLE IF EXISTS `xc_task`;
CREATE TABLE `xc_task`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务id',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  `task_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务类型',
  `mq_exchange` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交换机名称',
  `mq_routingkey` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'routingkey',
  `request_body` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务请求的内容',
  `version` int(0) NULL DEFAULT NULL COMMENT '乐观锁版本号',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务状态',
  `errormsg` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务错误信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xc_task
-- ----------------------------

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
INSERT INTO `xc_task_his` VALUES ('4028858162959ce5', '2018-04-05 20:09:17', '2020-10-19 17:35:04', '2020-10-19 17:35:34', 'add_choosecourse', 'ex_learning_addchoosecourse', 'addchoosecourse', '{\"courseId\":\"4028e58161bcf7f40161bcf8b77c0000\",\"userId\":\"49\"}', NULL, '10201', NULL);

SET FOREIGN_KEY_CHECKS = 1;
