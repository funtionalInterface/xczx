/*
 Navicat Premium Data Transfer

 Source Server         : CloudMySql
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : haogege.icu:3306
 Source Schema         : xc_course

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 23/10/2020 00:20:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `label` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类标签默认和名称一样',
  `parentid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父结点id',
  `isshow` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否显示',
  `orderby` int(0) NULL DEFAULT NULL COMMENT '排序字段',
  `isleaf` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否叶子',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '根结点', '根结点', '0', '1', 1, '0');
INSERT INTO `category` VALUES ('1-1', '前端开发', '前端开发', '1', '1', 1, '0');
INSERT INTO `category` VALUES ('1-1-1', 'HTML/CSS', 'HTML/CSS', '1-1', '1', 1, '1');
INSERT INTO `category` VALUES ('1-1-10', '其它', '其它', '1-1', '1', 10, '1');
INSERT INTO `category` VALUES ('1-1-2', 'JavaScript', 'JavaScript', '1-1', '1', 2, '1');
INSERT INTO `category` VALUES ('1-1-3', 'jQuery', 'jQuery', '1-1', '1', 3, '1');
INSERT INTO `category` VALUES ('1-1-4', 'ExtJS', 'ExtJS', '1-1', '1', 4, '1');
INSERT INTO `category` VALUES ('1-1-5', 'AngularJS', 'AngularJS', '1-1', '1', 5, '1');
INSERT INTO `category` VALUES ('1-1-6', 'ReactJS', 'ReactJS', '1-1', '1', 6, '1');
INSERT INTO `category` VALUES ('1-1-7', 'Bootstrap', 'Bootstrap', '1-1', '1', 7, '1');
INSERT INTO `category` VALUES ('1-1-8', 'Node.js', 'Node.js', '1-1', '1', 8, '1');
INSERT INTO `category` VALUES ('1-1-9', 'Vue', 'Vue', '1-1', '1', 9, '1');
INSERT INTO `category` VALUES ('1-10', '研发管理', '研发管理', '1', '1', 10, '0');
INSERT INTO `category` VALUES ('1-10-1', '敏捷开发', '敏捷开发', '1-10', '1', 1, '1');
INSERT INTO `category` VALUES ('1-10-2', '软件设计', '软件设计', '1-10', '1', 2, '1');
INSERT INTO `category` VALUES ('1-10-3', '软件测试', '软件测试', '1-10', '1', 3, '1');
INSERT INTO `category` VALUES ('1-10-4', '研发管理', '研发管理', '1-10', '1', 4, '1');
INSERT INTO `category` VALUES ('1-10-5', '其它', '其它', '1-10', '1', 5, '1');
INSERT INTO `category` VALUES ('1-11', '系统运维', '系统运维', '1', '1', 11, '0');
INSERT INTO `category` VALUES ('1-11-1', 'Linux', 'Linux', '1-11', '1', 1, '1');
INSERT INTO `category` VALUES ('1-11-10', '其它', '其它', '1-11', '1', 10, '1');
INSERT INTO `category` VALUES ('1-11-2', 'Windows', 'Windows', '1-11', '1', 2, '1');
INSERT INTO `category` VALUES ('1-11-3', 'UNIX', 'UNIX', '1-11', '1', 3, '1');
INSERT INTO `category` VALUES ('1-11-4', 'Mac OS', 'Mac OS', '1-11', '1', 4, '1');
INSERT INTO `category` VALUES ('1-11-5', '网络技术', '网络技术', '1-11', '1', 5, '1');
INSERT INTO `category` VALUES ('1-11-6', '路由协议', '路由协议', '1-11', '1', 6, '1');
INSERT INTO `category` VALUES ('1-11-7', '无线网络', '无线网络', '1-11', '1', 7, '1');
INSERT INTO `category` VALUES ('1-11-8', 'Ngnix', 'Ngnix', '1-11', '1', 8, '1');
INSERT INTO `category` VALUES ('1-11-9', '邮件服务器', '邮件服务器', '1-11', '1', 9, '1');
INSERT INTO `category` VALUES ('1-12', '产品经理', '产品经理', '1', '1', 12, '0');
INSERT INTO `category` VALUES ('1-12-1', '交互设计', '交互设计', '1-12', '1', 1, '1');
INSERT INTO `category` VALUES ('1-12-2', '产品设计', '产品设计', '1-12', '1', 2, '1');
INSERT INTO `category` VALUES ('1-12-3', '原型设计', '原型设计', '1-12', '1', 3, '1');
INSERT INTO `category` VALUES ('1-12-4', '用户体验', '用户体验', '1-12', '1', 4, '1');
INSERT INTO `category` VALUES ('1-12-5', '需求分析', '需求分析', '1-12', '1', 5, '1');
INSERT INTO `category` VALUES ('1-12-6', '其它', '其它', '1-12', '1', 6, '1');
INSERT INTO `category` VALUES ('1-13', '企业/办公/职场', '企业/办公/职场', '1', '1', 13, '0');
INSERT INTO `category` VALUES ('1-13-1', '运营管理', '运营管理', '1-13', '1', 1, '1');
INSERT INTO `category` VALUES ('1-13-2', '企业信息化', '企业信息化', '1-13', '1', 2, '1');
INSERT INTO `category` VALUES ('1-13-3', '网络营销', '网络营销', '1-13', '1', 3, '1');
INSERT INTO `category` VALUES ('1-13-4', 'Office/WPS', 'Office/WPS', '1-13', '1', 4, '1');
INSERT INTO `category` VALUES ('1-13-5', '招聘/面试', '招聘/面试', '1-13', '1', 5, '1');
INSERT INTO `category` VALUES ('1-13-6', '电子商务', '电子商务', '1-13', '1', 6, '1');
INSERT INTO `category` VALUES ('1-13-7', 'CRM', 'CRM', '1-13', '1', 7, '1');
INSERT INTO `category` VALUES ('1-13-8', 'ERP', 'ERP', '1-13', '1', 8, '1');
INSERT INTO `category` VALUES ('1-13-9', '其它', '其它', '1-13', '1', 9, '1');
INSERT INTO `category` VALUES ('1-14', '信息安全', '信息安全', '1', '1', 14, '0');
INSERT INTO `category` VALUES ('1-14-1', '密码学/加密/破解', '密码学/加密/破解', '1-14', '1', 1, '1');
INSERT INTO `category` VALUES ('1-14-10', '其它', '其它', '1-14', '1', 10, '1');
INSERT INTO `category` VALUES ('1-14-2', '渗透测试', '渗透测试', '1-14', '1', 2, '1');
INSERT INTO `category` VALUES ('1-14-3', '社会工程', '社会工程', '1-14', '1', 3, '1');
INSERT INTO `category` VALUES ('1-14-4', '漏洞挖掘与利用', '漏洞挖掘与利用', '1-14', '1', 4, '1');
INSERT INTO `category` VALUES ('1-14-5', '云安全', '云安全', '1-14', '1', 5, '1');
INSERT INTO `category` VALUES ('1-14-6', '防护加固', '防护加固', '1-14', '1', 6, '1');
INSERT INTO `category` VALUES ('1-14-7', '代码审计', '代码审计', '1-14', '1', 7, '1');
INSERT INTO `category` VALUES ('1-14-8', '移动安全', '移动安全', '1-14', '1', 8, '1');
INSERT INTO `category` VALUES ('1-14-9', '病毒木马', '病毒木马', '1-14', '1', 9, '1');
INSERT INTO `category` VALUES ('1-2', '移动开发', '移动开发', '1', '1', 2, '0');
INSERT INTO `category` VALUES ('1-2-1', '微信开发', '微信开发', '1-2', '1', 1, '1');
INSERT INTO `category` VALUES ('1-2-2', 'iOS', 'iOS', '1-2', '1', 2, '1');
INSERT INTO `category` VALUES ('1-2-3', '手游开发', '手游开发', '1-2', '1', 3, '1');
INSERT INTO `category` VALUES ('1-2-4', 'Swift', 'Swift', '1-2', '1', 4, '1');
INSERT INTO `category` VALUES ('1-2-5', 'Android', 'Android', '1-2', '1', 5, '1');
INSERT INTO `category` VALUES ('1-2-6', 'ReactNative', 'ReactNative', '1-2', '1', 6, '1');
INSERT INTO `category` VALUES ('1-2-7', 'Cordova', 'Cordova', '1-2', '1', 7, '1');
INSERT INTO `category` VALUES ('1-2-8', '其它', '其它', '1-2', '1', 8, '1');
INSERT INTO `category` VALUES ('1-3', '编程开发', '编程开发', '1', '1', 3, '0');
INSERT INTO `category` VALUES ('1-3-1', 'C/C++', 'C/C++', '1-3', '1', 1, '1');
INSERT INTO `category` VALUES ('1-3-2', 'Java', 'Java', '1-3', '1', 2, '1');
INSERT INTO `category` VALUES ('1-3-3', '.NET', '.NET', '1-3', '1', 3, '1');
INSERT INTO `category` VALUES ('1-3-4', 'Objective-C', 'Objective-C', '1-3', '1', 4, '1');
INSERT INTO `category` VALUES ('1-3-5', 'Go语言', 'Go语言', '1-3', '1', 5, '1');
INSERT INTO `category` VALUES ('1-3-6', 'Python', 'Python', '1-3', '1', 6, '1');
INSERT INTO `category` VALUES ('1-3-7', 'Ruby/Rails', 'Ruby/Rails', '1-3', '1', 7, '1');
INSERT INTO `category` VALUES ('1-3-8', '其它', '其它', '1-3', '1', 8, '1');
INSERT INTO `category` VALUES ('1-4', '数据库', '数据库', '1', '1', 4, '0');
INSERT INTO `category` VALUES ('1-4-1', 'Oracle', 'Oracle', '1-4', '1', 1, '1');
INSERT INTO `category` VALUES ('1-4-2', 'MySQL', 'MySQL', '1-4', '1', 2, '1');
INSERT INTO `category` VALUES ('1-4-3', 'SQL Server', 'SQL Server', '1-4', '1', 3, '1');
INSERT INTO `category` VALUES ('1-4-4', 'DB2', 'DB2', '1-4', '1', 4, '1');
INSERT INTO `category` VALUES ('1-4-5', 'NoSQL', 'NoSQL', '1-4', '1', 5, '1');
INSERT INTO `category` VALUES ('1-4-6', 'Mongo DB', 'Mongo DB', '1-4', '1', 6, '1');
INSERT INTO `category` VALUES ('1-4-7', 'Hbase', 'Hbase', '1-4', '1', 7, '1');
INSERT INTO `category` VALUES ('1-4-8', '数据仓库', '数据仓库', '1-4', '1', 8, '1');
INSERT INTO `category` VALUES ('1-4-9', '其它', '其它', '1-4', '1', 9, '1');
INSERT INTO `category` VALUES ('1-5', '人工智能', '人工智能', '1', '1', 5, '0');
INSERT INTO `category` VALUES ('1-5-1', '机器学习', '机器学习', '1-5', '1', 1, '1');
INSERT INTO `category` VALUES ('1-5-2', '深度学习', '深度学习', '1-5', '1', 2, '1');
INSERT INTO `category` VALUES ('1-5-3', '语音识别', '语音识别', '1-5', '1', 3, '1');
INSERT INTO `category` VALUES ('1-5-4', '计算机视觉', '计算机视觉', '1-5', '1', 4, '1');
INSERT INTO `category` VALUES ('1-5-5', 'NLP', 'NLP', '1-5', '1', 5, '1');
INSERT INTO `category` VALUES ('1-5-6', '强化学习', '强化学习', '1-5', '1', 6, '1');
INSERT INTO `category` VALUES ('1-5-7', '其它', '其它', '1-5', '1', 7, '1');
INSERT INTO `category` VALUES ('1-6', '云计算/大数据', '云计算/大数据', '1', '1', 6, '0');
INSERT INTO `category` VALUES ('1-6-1', 'Spark', 'Spark', '1-6', '1', 1, '1');
INSERT INTO `category` VALUES ('1-6-2', 'Hadoop', 'Hadoop', '1-6', '1', 2, '1');
INSERT INTO `category` VALUES ('1-6-3', 'OpenStack', 'OpenStack', '1-6', '1', 3, '1');
INSERT INTO `category` VALUES ('1-6-4', 'Docker/K8S', 'Docker/K8S', '1-6', '1', 4, '1');
INSERT INTO `category` VALUES ('1-6-5', '云计算基础架构', '云计算基础架构', '1-6', '1', 5, '1');
INSERT INTO `category` VALUES ('1-6-6', '虚拟化技术', '虚拟化技术', '1-6', '1', 6, '1');
INSERT INTO `category` VALUES ('1-6-7', '云平台', '云平台', '1-6', '1', 7, '1');
INSERT INTO `category` VALUES ('1-6-8', 'ELK', 'ELK', '1-6', '1', 8, '1');
INSERT INTO `category` VALUES ('1-6-9', '其它', '其它', '1-6', '1', 9, '1');
INSERT INTO `category` VALUES ('1-7', 'UI设计', 'UI设计', '1', '1', 7, '0');
INSERT INTO `category` VALUES ('1-7-1', 'Photoshop', 'Photoshop', '1-7', '1', 1, '1');
INSERT INTO `category` VALUES ('1-7-10', 'InDesign', 'InDesign', '1-7', '1', 10, '1');
INSERT INTO `category` VALUES ('1-7-11', 'Pro/Engineer', 'Pro/Engineer', '1-7', '1', 11, '1');
INSERT INTO `category` VALUES ('1-7-12', 'Cinema 4D', 'Cinema 4D', '1-7', '1', 12, '1');
INSERT INTO `category` VALUES ('1-7-13', '3D Studio', '3D Studio', '1-7', '1', 13, '1');
INSERT INTO `category` VALUES ('1-7-14', 'After Effects（AE）', 'After Effects（AE）', '1-7', '1', 14, '1');
INSERT INTO `category` VALUES ('1-7-15', '原画设计', '原画设计', '1-7', '1', 15, '1');
INSERT INTO `category` VALUES ('1-7-16', '动画制作', '动画制作', '1-7', '1', 16, '1');
INSERT INTO `category` VALUES ('1-7-17', 'Dreamweaver', 'Dreamweaver', '1-7', '1', 17, '1');
INSERT INTO `category` VALUES ('1-7-18', 'Axure', 'Axure', '1-7', '1', 18, '1');
INSERT INTO `category` VALUES ('1-7-19', '其它', '其它', '1-7', '1', 19, '1');
INSERT INTO `category` VALUES ('1-7-2', '3Dmax', '3Dmax', '1-7', '1', 2, '1');
INSERT INTO `category` VALUES ('1-7-3', 'Illustrator', 'Illustrator', '1-7', '1', 3, '1');
INSERT INTO `category` VALUES ('1-7-4', 'Flash', 'Flash', '1-7', '1', 4, '1');
INSERT INTO `category` VALUES ('1-7-5', 'Maya', 'Maya', '1-7', '1', 5, '1');
INSERT INTO `category` VALUES ('1-7-6', 'AUTOCAD', 'AUTOCAD', '1-7', '1', 6, '1');
INSERT INTO `category` VALUES ('1-7-7', 'UG', 'UG', '1-7', '1', 7, '1');
INSERT INTO `category` VALUES ('1-7-8', 'SolidWorks', 'SolidWorks', '1-7', '1', 8, '1');
INSERT INTO `category` VALUES ('1-7-9', 'CorelDraw', 'CorelDraw', '1-7', '1', 9, '1');
INSERT INTO `category` VALUES ('1-8', '游戏开发', '游戏开发', '1', '1', 8, '0');
INSERT INTO `category` VALUES ('1-8-1', 'Cocos', 'Cocos', '1-8', '1', 1, '1');
INSERT INTO `category` VALUES ('1-8-2', 'Unity3D', 'Unity3D', '1-8', '1', 2, '1');
INSERT INTO `category` VALUES ('1-8-3', 'Flash', 'Flash', '1-8', '1', 3, '1');
INSERT INTO `category` VALUES ('1-8-4', 'SpriteKit 2D', 'SpriteKit 2D', '1-8', '1', 4, '1');
INSERT INTO `category` VALUES ('1-8-5', 'Unreal', 'Unreal', '1-8', '1', 5, '1');
INSERT INTO `category` VALUES ('1-8-6', '其它', '其它', '1-8', '1', 6, '1');
INSERT INTO `category` VALUES ('1-9', '智能硬件/物联网', '智能硬件/物联网', '1', '1', 9, '0');
INSERT INTO `category` VALUES ('1-9-1', '无线通信', '无线通信', '1-9', '1', 1, '1');
INSERT INTO `category` VALUES ('1-9-10', '物联网技术', '物联网技术', '1-9', '1', 10, '1');
INSERT INTO `category` VALUES ('1-9-11', '其它', '其它', '1-9', '1', 11, '1');
INSERT INTO `category` VALUES ('1-9-2', '电子工程', '电子工程', '1-9', '1', 2, '1');
INSERT INTO `category` VALUES ('1-9-3', 'Arduino', 'Arduino', '1-9', '1', 3, '1');
INSERT INTO `category` VALUES ('1-9-4', '体感技术', '体感技术', '1-9', '1', 4, '1');
INSERT INTO `category` VALUES ('1-9-5', '智能硬件', '智能硬件', '1-9', '1', 5, '1');
INSERT INTO `category` VALUES ('1-9-6', '驱动/内核开发', '驱动/内核开发', '1-9', '1', 6, '1');
INSERT INTO `category` VALUES ('1-9-7', '单片机/工控', '单片机/工控', '1-9', '1', 7, '1');
INSERT INTO `category` VALUES ('1-9-8', 'WinCE', 'WinCE', '1-9', '1', 8, '1');
INSERT INTO `category` VALUES ('1-9-9', '嵌入式', '嵌入式', '1-9', '1', 9, '1');

-- ----------------------------
-- Table structure for course_base
-- ----------------------------
DROP TABLE IF EXISTS `course_base`;
CREATE TABLE `course_base`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
  `users` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '适用人群',
  `mt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程大分类',
  `grade` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程等级',
  `studymodel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学习模式',
  `teachmode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授课模式',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程介绍',
  `st` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程小分类',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程状态',
  `company_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教育机构',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_base
-- ----------------------------
INSERT INTO `course_base` VALUES ('297e7c7c62b888f00162b8a7dec20000', 'java', 'string1', '1-2', '200001', '201001', 'string', 'java世界第一', '1-2-1', '202002', 'string', 'string');
INSERT INTO `course_base` VALUES ('297e7c7c62b888f00162b8a965510001', 'test_java基础', 'test_java基础', '1-3', '200001', '201001', NULL, 'test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2', '1-3-1', '202002', '', NULL);
INSERT INTO `course_base` VALUES ('297e7c7c62b8aa9d0162b8ab13910000', 'java基础312321', 'java基础3', '1-3', '200001', '201001', NULL, 'java基础3java基础3java基础3java基础3java基础3java基础3', '1-3-2', '202002', '', NULL);
INSERT INTO `course_base` VALUES ('297e7c7c62b8aa9d0162b8ab56ba0001', 'java基础3', 'b2', '1-3', '200001', '201001', NULL, 'java基础3java基础3java基础3java基础3java基础3java基础3ffff', '1-3-2', '202001', '', NULL);
INSERT INTO `course_base` VALUES ('297e7c7c62b8aa9d0162b8ab70e90002', '56+5+65+6', 'java基础3', '1-3', '200001', '201001', NULL, 'java基础3java基础3java基础3java基础3java基础3java基础3', '1-3-2', '202002', '', NULL);
INSERT INTO `course_base` VALUES ('297e7c7c62b8aa9d0162b8accd4c0003', 'java基础4', 'java基础4', '1-3', '200001', '201001', NULL, 'java基础4java基础4java基础4java基础4java基础4java基础4java基础4', '1-3-2', '202001', '', NULL);
INSERT INTO `course_base` VALUES ('297e7c7c62b8aa9d0162b8ad78a10004', 'java基础5', 'java基础5', '1-3', '200001', '201001', NULL, 'java基础5java基础5java基础5java基础5', '1-3-2', '202002', '', NULL);
INSERT INTO `course_base` VALUES ('297e7c7c62b8aa9d0162b8ae50300005', 'java基础5', 'java基础5', '1-3', '200001', '201001', NULL, 'java基础5', '1-3-2', '202001', '', NULL);
INSERT INTO `course_base` VALUES ('297e7c7c62b8afe20162b8b0477c0000', 'test_java基础6', 'test_java基础6', '1-3', '200001', '201001', NULL, 'test_java基础6test_java基础6', '1-3-2', '202001', '', NULL);
INSERT INTO `course_base` VALUES ('297e7c7c62b8b3ff0162b8b58c260000', 'test_java基础6', 'test_java基础6', '1-3', '200001', '201001', NULL, 'test_java基础6', '1-3-2', '202001', '', NULL);
INSERT INTO `course_base` VALUES ('40281f81640220d601640222665b0001', 'java零基础入门', 'java小白', '1-3', '200001', '201001', NULL, 'java零基础入门java零基础入门java零基础入门java零基础入门', '1-3-2', '202001', NULL, NULL);
INSERT INTO `course_base` VALUES ('4028460073e8a8670173e8ab683a0000', '金玉测试', '', '1-1', '200002', '201001', NULL, '', '1-1-2', '202001', NULL, NULL);
INSERT INTO `course_base` VALUES ('40287e0173e474e40173e477c19d0003', '名称', '', '1-1', '200001', '201001', NULL, '', '1-1-2', '202001', NULL, NULL);
INSERT INTO `course_base` VALUES ('40287e0173e474e40173e477c1c00004', '名称', '', '1-1', '200001', '201001', NULL, '', '1-1-2', '202001', NULL, NULL);
INSERT INTO `course_base` VALUES ('40287e0173e474e40173e47846d40005', '名', '', '1-1', '200001', '201002', NULL, '', '1-1-1', '202001', NULL, NULL);
INSERT INTO `course_base` VALUES ('402885816240d276016240f7e5000002', 'test', 'dd', '1-6', '200003', '201001', NULL, '', '1-6-1', '202001', '2', NULL);
INSERT INTO `course_base` VALUES ('402885816240d276016241019be70004', 'ddd', 'dd', '1-1', '200001', '201001', NULL, '', '1-1-2', '202001', '2', NULL);
INSERT INTO `course_base` VALUES ('402885816240d2760162410bac010006', 'ffff', '', '1-6', '200001', '201001', NULL, '', '1-6-4', '202001', '2', NULL);
INSERT INTO `course_base` VALUES ('402885816243d2dd016243f24c030002', '大数据', '具有一定的java基础', '1-6', '200001', '201001', NULL, '111111大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据', '1-6-1', '202002', '1', NULL);
INSERT INTO `course_base` VALUES ('40288581625c7e7301625c8ed6af0000', 'test001', '', '1-1', '200001', '201001', NULL, '', '1-1-4', '202001', '2', NULL);
INSERT INTO `course_base` VALUES ('4028858162e0bc0a0162e0bfdf1a0000', '人工智能+python', '小白', '1-6', '200002', '201001', NULL, '人工智能+python非常不错！！！', '1-6-5', '202002', '1', NULL);
INSERT INTO `course_base` VALUES ('4028e581617f945f01617f9dabc40000', 'Bootstrap开发框架', '', '1-1', '200002', '201001', NULL, 'Bootstrap是由Twitter推出的一个前台页面开发框架，在行业之中使用较为广泛。此开发框架包含了大量的CSS、JS程序代码，可以帮助开发者（尤其是不擅长页面开发的程序人员）轻松的实现一个不受浏览器限制的精美界面效果。', '1-1-1', '202002', '1', NULL);
INSERT INTO `course_base` VALUES ('4028e58161bcf7f40161bcf8b77c0000', 'spring cloud实战', '所有人', '1-3', '200003', '201001', NULL, '本课程主要从四个章节进行讲解： 1.微服务架构入门 2.spring cloud 基础入门 3.实战Spring Boot 4.注册中心eureka。', '1-3-2', '202002', '1', NULL);
INSERT INTO `course_base` VALUES ('4028e58161bd22e60161bd23672a0001', 'Javascript之VueJS', '所有人', '1-1', '200002', '201001', NULL, 'Vue系列课程：从Vue1.0讲到Vue2.0，从理论讲到实战，理论与案例巧妙结合，让课程更容易理解！', '1-1-9', '202002', '1', NULL);
INSERT INTO `course_base` VALUES ('4028e58161bd3b380161bd3bcd2f0000', 'Redis从入门到项目实战', '', '1-3', '200002', '201001', NULL, 'redis在当前的大型网站和500强企业中，已被广泛应用。 redis是基于内存的key-value数据库，比传统的关系型数据库在性能方面有非常大的优势。 肖老师这套视频，精选了redis在实际项目中的十几个应用场景。通过本课程的学习，可以让学员快速掌握redis在实际项目中如何应用。 作为架构师，redis是必须要掌握的技能！', '1-3-2', '202002', '1', NULL);

-- ----------------------------
-- Table structure for course_market
-- ----------------------------
DROP TABLE IF EXISTS `course_market`;
CREATE TABLE `course_market`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程id',
  `charge` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收费规则，对应数据字典',
  `valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '有效性，对应数据字典',
  `expires` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '过期时间',
  `qq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '咨询qq',
  `price` float(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `price_old` float(10, 2) NULL DEFAULT NULL COMMENT '原价',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '课程有效期-开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '课程有效期-结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_market
-- ----------------------------
INSERT INTO `course_market` VALUES ('297e7c7c62b888f00162b8a7dec20000', '203002', '204002', '2020-09-30 17:44:50', 'string', 2.00, 0.00, '2020-09-15 09:29:11', '2020-09-15 09:29:11');
INSERT INTO `course_market` VALUES ('297e7c7c62b888f00162b8a965510001', '203001', '204001', '2018-05-28 19:03:56', '443242', 0.01, NULL, NULL, NULL);
INSERT INTO `course_market` VALUES ('297e7c7c62b8aa9d0162b8ab13910000', '203002', '204001', '2020-09-23 04:54:15', '1', 0.01, NULL, NULL, NULL);
INSERT INTO `course_market` VALUES ('297e7c7c62b8aa9d0162b8ab70e90002', '203002', '204001', '2020-09-30 15:47:38', NULL, 1.99, NULL, NULL, NULL);
INSERT INTO `course_market` VALUES ('297e7c7c62b8aa9d0162b8accd4c0003', '203001', '204001', '2020-09-23 04:39:38', '1', NULL, NULL, NULL, NULL);
INSERT INTO `course_market` VALUES ('297e7c7c62b8aa9d0162b8ad78a10004', '203001', '204001', '2020-08-14 16:28:00', '安慰发射点发射点发 ', NULL, NULL, NULL, NULL);
INSERT INTO `course_market` VALUES ('297e7c7c62b8afe20162b8b0477c0000', '203001', '204001', '2020-09-23 05:01:52', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `course_market` VALUES ('4028460073e8a8670173e8ab683a0000', '203001', '204001', '2020-08-14 16:25:07', '1', NULL, NULL, NULL, NULL);
INSERT INTO `course_market` VALUES ('402885816243d2dd016243f24c030002', '203002', '204002', '2018-06-01 23:40:39', NULL, 0.01, 199.00, '2018-04-01 10:50:31', '2020-04-01 00:00:00');
INSERT INTO `course_market` VALUES ('4028858162e0bc0a0162e0bfdf1a0000', '203002', '204002', '2018-05-28 19:03:52', '45323453', 0.01, NULL, NULL, NULL);
INSERT INTO `course_market` VALUES ('4028e581617f945f01617f9dabc40000', '203002', '204001', '2020-10-13 15:04:10', '4', 0.01, NULL, '2018-04-01 10:50:31', '2020-04-01 10:50:37');
INSERT INTO `course_market` VALUES ('4028e58161bcf7f40161bcf8b77c0000', '203002', '204002', '2018-04-05 10:50:53', '54354353', 0.01, NULL, '2018-04-01 10:50:31', '2020-04-01 10:50:37');
INSERT INTO `course_market` VALUES ('4028e58161bd22e60161bd23672a0001', '203001', '204002', '2018-06-02 00:00:22', '4324322', 0.01, NULL, '2018-04-01 10:50:31', '2020-04-01 10:50:37');
INSERT INTO `course_market` VALUES ('4028e58161bd3b380161bd3bcd2f0000', '203002', '204001', '2018-04-05 10:50:55', '32432432', 0.01, NULL, '2018-04-01 10:50:31', '2020-04-01 10:50:37');

-- ----------------------------
-- Table structure for course_off
-- ----------------------------
DROP TABLE IF EXISTS `course_off`;
CREATE TABLE `course_off`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
  `users` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '适用人群',
  `mt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大分类',
  `st` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小分类',
  `grade` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程等级',
  `studymodel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学习模式',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程介绍',
  `timestamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '时间戳',
  `charge` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收费规则，对应数据字典',
  `valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '有效性，对应数据字典',
  `qq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '咨询qq',
  `price` float(10, 2) NOT NULL COMMENT '价格',
  `price_old` float(10, 2) NULL DEFAULT NULL COMMENT '原价格',
  `expires` timestamp(0) NULL DEFAULT NULL COMMENT '过期时间',
  `pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程图片',
  `teachplan` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程计划',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_off
-- ----------------------------

-- ----------------------------
-- Table structure for course_pic
-- ----------------------------
DROP TABLE IF EXISTS `course_pic`;
CREATE TABLE `course_pic`  (
  `courseid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程id',
  `pic` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片id',
  PRIMARY KEY (`courseid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_pic
-- ----------------------------
INSERT INTO `course_pic` VALUES ('297e7c7c62b888f00162b8a7dec20000', 'group1/M00/00/00/wKifgV97G3CAE39IAABjnXSWAr0762.jpg');
INSERT INTO `course_pic` VALUES ('297e7c7c62b888f00162b8a965510001', 'group1/M00/00/00/wKifgV98ar2AQ9ChAADcMytyA3E427.jpg');
INSERT INTO `course_pic` VALUES ('297e7c7c62b8aa9d0162b8ab13910000', 'group1/M00/00/00/wKifgV98atqADqAMAALq1Lhy1po379.jpg');
INSERT INTO `course_pic` VALUES ('4028e581617f945f01617f9dabc40000', 'group1/M00/00/00/wKifgV-Fwp2AccWUAABjnXSWAr0472.jpg');

-- ----------------------------
-- Table structure for course_pre
-- ----------------------------
DROP TABLE IF EXISTS `course_pre`;
CREATE TABLE `course_pre`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
  `users` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '适用人群',
  `mt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大分类',
  `st` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小分类',
  `grade` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程等级',
  `studymodel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学习模式',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课程介绍',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程状态',
  `timestamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '时间戳',
  `charge` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收费规则，对应数据字典',
  `valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '有效性，对应数据字典',
  `qq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '咨询qq',
  `price` float(10, 2) NOT NULL COMMENT '价格',
  `price_old` float(10, 2) NULL DEFAULT NULL COMMENT '原价格',
  `expires` timestamp(0) NULL DEFAULT NULL COMMENT '过期时间',
  `pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程图片',
  `teachplan` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程计划',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_pre
-- ----------------------------
INSERT INTO `course_pre` VALUES ('4028e581617f945f01617f9dabc40000', 'Bootstrap开发框架', '', '1-1', '1-1-1', '200002', '201001', 'Bootstrap是由Twitter推出的一个前台页面开发框架，在行业之中使用较为广泛。此开发框架包含了大量的CSS、JS程序代码，可以帮助开发者（尤其是不擅长页面开发的程序人员）轻松的实现一个不受浏览器限制的精美界面效果。', '202001', '2018-02-22 21:49:22', '203002', '204001', '4455432', 89.10, NULL, '2018-02-22 17:45:42', '[\"group1/M00/00/01/wKhlQFqO0OGAFyhGAAA-8SWa8Qc537.jpg\"]', '计算机原理 计算机硬件 计算机软件 计算机编程入门 java语法介绍 Hello World 数据库编程 操作系统原理 操作系统原理 操作系统类型介绍 ');
INSERT INTO `course_pre` VALUES ('4028e58161bcf7f40161bcf8b77c0000', 'spring cloud实战', '所有人', '1-3', '1-3-2', '200003', '201001', '本课程主要从四个章节进行讲解： 1.微服务架构入门 2.spring cloud 基础入门 3.实战Spring Boot 4.注册中心eureka。', '202001', '2018-02-22 21:49:26', '203002', '204002', '54354353', 99.00, NULL, '2018-02-22 18:17:54', '[\"group1/M00/00/01/wKhlQFqO2HqAA6sPAAArlhJed-w088.jpg\"]', '微服务架构入门 为什么要使用微服务:单体架构的特点 为什么要使用微服务:微服务的优缺点 spring cloud 基础入门 为什么要选择spring cloud? 为什么springcloud要设计一套新的版本升级规则？ 实战-Spring Boot 为什么越来越多的开发者选择使用spring boot？它解决了什么问题？ spring boot的入门例子 注册中心Eureka 微服务架构为什么需要注册中心，它解决了什么问题？  一个Eureka注册中心的入门例子 ');
INSERT INTO `course_pre` VALUES ('4028e58161bd22e60161bd23672a0001', 'Javascript之VueJS', '所有人', '1-1', '1-1-9', '200002', '201001', 'Vue系列课程：从Vue1.0讲到Vue2.0，从理论讲到实战，理论与案例巧妙结合，让课程更容易理解！', '202001', '2018-02-22 21:49:30', '203002', '204001', '4324322', 90.00, NULL, '2018-02-22 18:53:13', '[\"group1/M00/00/01/wKhlQFqO4MmAOP53AAAcwDwm6SU490.jpg\"]', 'Vuejs 第一讲 第一节 vue基础、常用指令、bootstrap+vue的简易留言 第二节 属性和事件、模板、交互、案例 Vuejs 第二讲 第一节 计算属性的使用、vue实例的简单方法、提高循环的性能，让重复数据显示出来 第二节 自定义过滤器、自定义指令 、自定义键盘事件、数据的监听 Vuejs 第三讲 ');
INSERT INTO `course_pre` VALUES ('4028e58161bd3b380161bd3bcd2f0000', 'Redis从入门到项目实战', '', '1-3', '1-3-2', '200002', '201001', 'redis在当前的大型网站和500强企业中，已被广泛应用。 redis是基于内存的key-value数据库，比传统的关系型数据库在性能方面有非常大的优势。 肖老师这套视频，精选了redis在实际项目中的十几个应用场景。通过本课程的学习，可以让学员快速掌握redis在实际项目中如何应用。 作为架构师，redis是必须要掌握的技能！', '202001', '2018-02-22 21:49:35', '203002', '204001', '32432432', 100.00, NULL, '2018-02-22 19:20:24', '[\"group1/M00/00/01/wKhlQFqO5yqAQMozAAAqor3lyz0082.jpg\"]', '第一章：redis简介 第一节 NoSQL简介 第二节 认识Redis 第二章：redis安装与配置 第三章：Redis数据操作 第四章：Redis进阶操作 第五章：Redis主从配置 ');

-- ----------------------------
-- Table structure for course_pub
-- ----------------------------
DROP TABLE IF EXISTS `course_pub`;
CREATE TABLE `course_pub`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
  `users` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '适用人群',
  `mt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大分类',
  `st` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小分类',
  `grade` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程等级',
  `studymodel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学习模式',
  `teachmode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教育模式',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程介绍',
  `timestamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '时间戳logstash使用',
  `charge` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收费规则，对应数据字典',
  `valid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '有效性，对应数据字典',
  `qq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '咨询qq',
  `price` float(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `price_old` float(10, 2) NULL DEFAULT NULL COMMENT '原价格',
  `expires` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过期时间',
  `start_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程有效期-开始时间',
  `end_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程有效期-结束时间',
  `pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程图片',
  `teachplan` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程计划',
  `pub_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_pub
-- ----------------------------
INSERT INTO `course_pub` VALUES ('297e7c7c62b888f00162b8a7dec20000', 'java', 'string', '1-2', '1-2-1', '200001', '201001', 'string', 'java世界第一', '2020-10-15 15:53:29', '203002', '204002', 'string', 2.00, 0.00, NULL, NULL, NULL, 'group1/M00/00/00/wKifgV97G3CAE39IAABjnXSWAr0762.jpg', '{\"children\":[{\"children\":[{\"id\":\"40281f00752678650175268ed3ea0002\",\"media_fileoriginalname\":\"15-webpack研究-webpack-dev-server-程序调试 【www.zxit8.com】.avi\",\"media_id\":\"36fa330ca8dc8668770df539cc369b06\",\"pname\":\"快快乐乐乐\"}],\"id\":\"40281f00751d828501751d858d7f0001\",\"pname\":\"学习使我快乐\"}],\"id\":\"4028460073e8a8670173e8b13ecd0001\",\"pname\":\"test_java基础33\"}', '2020-10-15 23:53:28');
INSERT INTO `course_pub` VALUES ('297e7c7c62b888f00162b8a965510001', 'test_java基础', 'test_java基础', '1-3', '1-3-1', '200001', '201001', NULL, 'test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2', '2020-10-14 11:23:02', '203001', '204001', '443242', 0.01, NULL, NULL, NULL, NULL, 'group1/M00/00/00/wKifgV98ar2AQ9ChAADcMytyA3E427.jpg', '{\"children\":[{\"children\":[{\"id\":\"40281f00752678650175267d76930001\",\"media_fileoriginalname\":\"01-项目概述-功能构架-项目背景 【www.zxit8.com】.avi\",\"media_id\":\"69a81d4b4b3404887ade5b252a5b8ca6\",\"pname\":\"123\"}],\"id\":\"40281f00752678650175267d587a0000\",\"pname\":\"1\"}],\"id\":\"40281f0075264bc90175264f8d9c0000\",\"pname\":\"test_java基础\"}', '2020-10-14 19:23:01');
INSERT INTO `course_pub` VALUES ('4028e581617f945f01617f9dabc40000', 'Bootstrap开发框架', '', '1-1', '1-1-1', '200002', '201001', NULL, 'Bootstrap是由Twitter推出的一个前台页面开发框架，在行业之中使用较为广泛。此开发框架包含了大量的CSS、JS程序代码，可以帮助开发者（尤其是不擅长页面开发的程序人员）轻松的实现一个不受浏览器限制的精美界面效果。', '2020-10-18 14:05:42', '203002', '204001', '4', 0.01, NULL, NULL, NULL, NULL, 'group1/M00/00/00/wKifgV-Fwp2AccWUAABjnXSWAr0472.jpg', '{\"children\":[{\"children\":[{\"id\":\"3\",\"pname\":\"计算机硬件\"},{\"id\":\"4\",\"pname\":\"计算机软件\"}],\"id\":\"2\",\"pname\":\"计算机原理\"},{\"children\":[{\"id\":\"6\",\"pname\":\"java语法介绍\"},{\"id\":\"7\",\"pname\":\"Hello World\"}],\"id\":\"5\",\"pname\":\"计算机编程入门\"},{\"children\":[{\"id\":\"402885816347f814016348d68bad0000\",\"pname\":\"数据库基础知识\"},{\"id\":\"402885816347f814016348d6c5920001\",\"pname\":\"SQL查询\"},{\"id\":\"402885816347f814016348d7153c0002\",\"pname\":\"SQL优化\"}],\"id\":\"4028e581617ce7b601617ce801790000\",\"pname\":\"数据库编程\"},{\"children\":[{\"id\":\"4028e58161bbcd350161bbcefe3d0001\",\"pname\":\"操作系统原理\"},{\"id\":\"4028e581617d02e101617d070ed90000\",\"pname\":\"操作系统类型介绍\"}],\"id\":\"8\",\"pname\":\"操作系统原理\"}],\"id\":\"1\",\"pname\":\"Bootstrap开发框架\"}', '2020-10-18 22:05:41');

-- ----------------------------
-- Table structure for teachplan
-- ----------------------------
DROP TABLE IF EXISTS `teachplan`;
CREATE TABLE `teachplan`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parentid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `grade` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '层级，分为1、2、3级',
  `ptype` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程类型:1视频、2文档',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '章节及课程时介绍',
  `timelength` double(5, 2) NULL DEFAULT NULL COMMENT '时长，单位分钟',
  `courseid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程id',
  `orderby` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排序字段',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态：未发布、已发布',
  `trylearn` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否试学',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachplan
-- ----------------------------
INSERT INTO `teachplan` VALUES ('1', 'Bootstrap开发框架', '0', '1', '0', NULL, NULL, '4028e581617f945f01617f9dabc40000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('2', '计算机原理', '1', '2', '0', '介绍计算机工作原理', NULL, '4028e581617f945f01617f9dabc40000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('22', 'spring cloud与spring boot实战', '0', '1', NULL, NULL, NULL, '4028e58161bcf7f40161bcf8b77c0000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('297e02f7639af61a01639afd3a7b0000', '第一节', '402885816243d2dd016243f24c040003', '2', '1', NULL, NULL, '402885816243d2dd016243f24c030002', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('3', '计算机硬件', '2', '3', '1', NULL, 10.00, '4028e581617f945f01617f9dabc40000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4', '计算机软件', '2', '3', '1', NULL, 12.00, '4028e581617f945f01617f9dabc40000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('40281f00751d828501751d858d7f0001', '学习使我快乐', '4028460073e8a8670173e8b13ecd0001', '2', NULL, NULL, NULL, '297e7c7c62b888f00162b8a7dec20000', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('40281f0075264bc90175264f8d9c0000', 'test_java基础', '0', '1', NULL, NULL, NULL, '297e7c7c62b888f00162b8a965510001', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('40281f00752678650175267d587a0000', '1', '40281f0075264bc90175264f8d9c0000', '2', NULL, NULL, NULL, '297e7c7c62b888f00162b8a965510001', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('40281f00752678650175267d76930001', '123', '40281f00752678650175267d587a0000', '3', NULL, NULL, NULL, '297e7c7c62b888f00162b8a965510001', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('40281f00752678650175268ed3ea0002', '快快乐乐乐', '40281f00751d828501751d858d7f0001', '3', NULL, NULL, NULL, '297e7c7c62b888f00162b8a7dec20000', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('4028460073e8a8670173e8b13ecd0001', 'test_java基础33', '0', '1', NULL, NULL, NULL, '297e7c7c62b888f00162b8a7dec20000', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('4028460073e8d1010173e8dbc4ff0000', '金玉测试', '0', '1', NULL, NULL, NULL, '4028460073e8a8670173e8ab683a0000', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('4028460073e8d1010173e8dbc50a0001', '1', '4028460073e8d1010173e8dbc4ff0000', '2', NULL, NULL, NULL, '4028460073e8a8670173e8ab683a0000', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('40287e0074b90c150174b94324830000', 'java基础4', '0', '1', NULL, NULL, NULL, '297e7c7c62b8aa9d0162b8accd4c0003', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('40287e0074b90c150174b94324870001', '1', '40287e0074b90c150174b94324830000', '2', NULL, NULL, NULL, '297e7c7c62b8aa9d0162b8accd4c0003', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('40287e0074b90c150174b94d74de0002', '1', '4028858163012bbf01630136a5140000', '2', NULL, NULL, NULL, '297e7c7c62b8aa9d0162b8ab13910000', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('40287e0074dde1720174dde678900000', 'java基础5', '0', '1', NULL, NULL, NULL, '297e7c7c62b8aa9d0162b8ad78a10004', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('40287e0074dde1720174dde678940001', '1', '40287e0074dde1720174dde678900000', '2', NULL, NULL, NULL, '297e7c7c62b8aa9d0162b8ad78a10004', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('40287e0173e3bc7f0173e3bf012a0001', '1', '40287e0173e3bc7f0173e3bed2690000', '3', NULL, NULL, NULL, '4028e581617f945f01617f9dabc40000', NULL, '0', NULL);
INSERT INTO `teachplan` VALUES ('402881e764034e4301640351f3d70003', '一切皆为对象', '402881e764034e430164035091a00002', '3', '1', '一切皆为对象', 10.00, '297e7c7c62b888f00162b8a7dec20000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('402885816240d276016240f7e5010003', 'test', '0', '1', NULL, NULL, NULL, '402885816240d276016240f7e5000002', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('402885816240d276016241019be70005', 'ddd', '0', '1', NULL, NULL, NULL, '402885816240d276016241019be70004', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('402885816240d2760162410bac010007', 'ffff', '0', '1', NULL, NULL, NULL, '402885816240d2760162410bac010006', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('402885816240d2760162414086a0000b', '课程介绍', '402885816240d276016240f7e5010003', '2', '1', NULL, NULL, '402885816240d276016240f7e5000002', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('402885816240d2760162414102fd000c', '如何学习一个项目', '402885816240d2760162414086a0000b', '3', '1', NULL, NULL, '402885816240d276016240f7e5000002', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('402885816240d2760162416df996000d', '我是一个新课时，请点击修改填写正确信息', '402885816240d2760162414086a0000b', '3', NULL, NULL, NULL, '402885816240d276016240f7e5000002', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('402885816243d2dd016243f24c040003', '第一章', '0', '1', NULL, NULL, NULL, '402885816243d2dd016243f24c030002', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('40288581625c7e7301625c8ed6be0001', 'test001', '0', '1', NULL, NULL, NULL, '40288581625c7e7301625c8ed6af0000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028858162bec7f30162bed26cd70001', 'java基础3', '0', '1', NULL, NULL, NULL, '297e7c7c62b8aa9d0162b8ab56ba0001', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028858162bee2020162bee3c9c60000', '我是一个新章节，请点击修改填写正确信息', '4028858162bec7f30162bed26cd70001', '2', NULL, NULL, NULL, '297e7c7c62b8aa9d0162b8ab56ba0001', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028858162bee9e90162beea3f480000', 'java基础3', '0', '1', NULL, NULL, NULL, '297e7c7c62b8aa9d0162b8ab70e90002', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028858162bee9e90162beea81690001', '我是一个新章节，请点击修改填写正确信息', '4028858162bee9e90162beea3f480000', '2', '1', 'fdsfds', 2.00, '297e7c7c62b8aa9d0162b8ab70e90002', '1', '1', NULL);
INSERT INTO `teachplan` VALUES ('4028858162e0bc0a0162e0bfdf2b0001', '人工智能+python', '0', '1', NULL, NULL, NULL, '4028858162e0bc0a0162e0bfdf1a0000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028858163012bbf01630136a5140000', 'java基础3', '0', '1', NULL, NULL, NULL, '297e7c7c62b8aa9d0162b8ab13910000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('40288581632b593e01632bd53ff10001', 'Hello World', '40288581632b593e01632bd4ec360000', '3', '1', NULL, NULL, '297e7c7c62b888f00162b8a7dec20000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('40288581632b593e01632bd5d31f0003', '表达式', '40288581632b593e01632bd597810002', '3', '1', NULL, NULL, '297e7c7c62b888f00162b8a7dec20000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('40288581632b593e01632bd606480004', '逻辑运算', '40288581632b593e01632bd597810002', '3', '1', NULL, NULL, '297e7c7c62b888f00162b8a7dec20000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('402885816347f814016348d68bad0000', '数据库基础知识', '4028e581617ce7b601617ce801790000', '3', '1', NULL, NULL, '4028e581617f945f01617f9dabc40000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('402885816347f814016348d6c5920001', 'SQL查询', '4028e581617ce7b601617ce801790000', '3', '1', NULL, NULL, '4028e581617f945f01617f9dabc40000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('402885816347f814016348d7153c0002', 'SQL优化', '4028e581617ce7b601617ce801790000', '3', '1', NULL, NULL, '4028e581617f945f01617f9dabc40000', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e581617ce7b601617ce801790000', '数据库编程', '1', '2', '1', '数据库编程数据库编程', 11.00, '4028e581617f945f01617f9dabc40000', '4', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e581617d02e101617d070ed90000', '操作系统类型介绍', '8', '3', '1', '操作系统类型介绍操作系统类型介绍', 11.00, '4028e581617f945f01617f9dabc40000', '6', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bbcd350161bbcefe3d0001', '操作系统原理', '8', '3', '1', '操作系统原理操作系统原理操作系统原理操作系统原理', 22.00, '4028e581617f945f01617f9dabc40000', '6', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd14c20161bd14f1520000', '微服务架构入门', '22', '2', NULL, '微服务架构入门', NULL, '4028e58161bcf7f40161bcf8b77c0000', '1-1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd18ea0161bd1b00ab0000', '为什么要使用微服务:单体架构的特点', '4028e58161bd14c20161bd14f1520000', '3', '1', '为什么要使用微服务:单体架构的特点', 44.00, '4028e58161bcf7f40161bcf8b77c0000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd18ea0161bd1bd2d10001', '为什么要使用微服务:微服务的优缺点', '4028e58161bd14c20161bd14f1520000', '3', '1', '为什么要使用微服务:微服务的优缺点', 55.00, '4028e58161bcf7f40161bcf8b77c0000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd18ea0161bd1c83590002', 'spring cloud 基础入门', '22', '2', NULL, 'spring cloud 基础入门', NULL, '4028e58161bcf7f40161bcf8b77c0000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd18ea0161bd1cc4850003', '实战-Spring Boot', '22', '2', NULL, '实战-Spring Boot', NULL, '4028e58161bcf7f40161bcf8b77c0000', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd18ea0161bd1cf3e10004', '注册中心Eureka', '22', '2', NULL, '注册中心Eureka', 55.00, '4028e58161bcf7f40161bcf8b77c0000', '4', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd18ea0161bd1d2f3f0005', '为什么要选择spring cloud?', '4028e58161bd18ea0161bd1c83590002', '3', '1', '为什么要选择spring cloud?', 12.00, '4028e58161bcf7f40161bcf8b77c0000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd18ea0161bd1d8f1b0006', '为什么springcloud要设计一套新的版本升级规则？', '4028e58161bd18ea0161bd1c83590002', '3', '1', '为什么springcloud要设计一套新的版本升级规则？', 33.00, '4028e58161bcf7f40161bcf8b77c0000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd18ea0161bd1df0ad0007', '为什么越来越多的开发者选择使用spring boot？它解决了什么问题？', '4028e58161bd18ea0161bd1cc4850003', '3', '1', '为什么越来越多的开发者选择使用spring boot？它解决了什么问题？', 10.00, '4028e58161bcf7f40161bcf8b77c0000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd18ea0161bd1f73190008', 'spring boot的入门例子', '4028e58161bd18ea0161bd1cc4850003', '3', '1', 'spring boot的入门例子', 44.00, '4028e58161bcf7f40161bcf8b77c0000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd18ea0161bd1fd31c0009', '微服务架构为什么需要注册中心，它解决了什么问题？', '4028e58161bd18ea0161bd1cf3e10004', '3', '1', '微服务架构为什么需要注册中心，它解决了什么问题？', 33.00, '4028e58161bcf7f40161bcf8b77c0000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd18ea0161bd202c45000a', ' 一个Eureka注册中心的入门例子', '4028e58161bd18ea0161bd1cf3e10004', '3', '1', ' 一个Eureka注册中心的入门例子', 22.00, '4028e58161bcf7f40161bcf8b77c0000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd22e60161bd2366fb0000', 'Javascript之VueJS', '0', '1', NULL, NULL, NULL, '4028e58161bd22e60161bd23672a0001', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd269f0161bd270a340000', 'Vuejs 第一讲', '4028e58161bd22e60161bd2366fb0000', '2', NULL, 'Vuejs 第一讲', NULL, '4028e58161bd22e60161bd23672a0001', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd269f0161bd2778750001', '第一节 vue基础、常用指令、bootstrap+vue的简易留言', '4028e58161bd269f0161bd270a340000', '3', '1', '第一节 vue基础、常用指令、bootstrap+vue的简易留言', 22.00, '4028e58161bd22e60161bd23672a0001', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd269f0161bd27d7c50002', '第二节 属性和事件、模板、交互、案例', '4028e58161bd269f0161bd270a340000', '3', '1', '第二节 属性和事件、模板、交互、案例', 33.00, '4028e58161bd22e60161bd23672a0001', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd269f0161bd281bde0003', 'Vuejs 第二讲', '4028e58161bd22e60161bd2366fb0000', '2', NULL, NULL, NULL, '4028e58161bd22e60161bd23672a0001', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd269f0161bd284bad0004', 'Vuejs 第三讲', '4028e58161bd22e60161bd2366fb0000', '2', NULL, NULL, NULL, '4028e58161bd22e60161bd23672a0001', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd269f0161bd2877740005', '第一节 计算属性的使用、vue实例的简单方法、提高循环的性能，让重复数据显示出来', '4028e58161bd269f0161bd281bde0003', '3', NULL, NULL, NULL, '4028e58161bd22e60161bd23672a0001', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd269f0161bd293df90006', '第二节 自定义过滤器、自定义指令 、自定义键盘事件、数据的监听', '4028e58161bd269f0161bd281bde0003', '3', '1', NULL, NULL, '4028e58161bd22e60161bd23672a0001', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd3b380161bd3bcd400001', 'Redis从入门到项目实战', '0', '1', NULL, NULL, NULL, '4028e58161bd3b380161bd3bcd2f0000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd3b380161bd3e47da0003', '第一章：redis简介', '4028e58161bd3b380161bd3bcd400001', '2', NULL, NULL, NULL, '4028e58161bd3b380161bd3bcd2f0000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd3b380161bd3f484c0004', '第二章：redis的安装与配置', '4028e58161bd3b380161bd3bcd400001', '2', NULL, NULL, NULL, '4028e58161bd3b380161bd3bcd2f0000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd3b380161bd3f6f440005', '第三章：Redis数据操作', '4028e58161bd3b380161bd3bcd400001', '2', NULL, NULL, NULL, '4028e58161bd3b380161bd3bcd2f0000', '3', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd3b380161bd3fb0050006', '第四章：Redis进阶操作', '4028e58161bd3b380161bd3bcd400001', '2', NULL, NULL, NULL, '4028e58161bd3b380161bd3bcd2f0000', '4', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd3b380161bd3fd3420007', '第五章：Redis主从配置', '4028e58161bd3b380161bd3bcd400001', '2', NULL, NULL, NULL, '4028e58161bd3b380161bd3bcd2f0000', '5', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd3b380161bd3fe9220008', '第一节 NoSQL简介', '4028e58161bd3b380161bd3e47da0003', '3', '1', NULL, NULL, '4028e58161bd3b380161bd3bcd2f0000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('4028e58161bd3b380161bd40cf340009', '第二节 认识Redis', '4028e58161bd3b380161bd3e47da0003', '3', '1', NULL, NULL, '4028e58161bd3b380161bd3bcd2f0000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('5', '计算机编程入门', '1', '2', '0', NULL, NULL, '4028e581617f945f01617f9dabc40000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('6', 'java语法介绍', '5', '3', '1', NULL, NULL, '4028e581617f945f01617f9dabc40000', '1', '0', NULL);
INSERT INTO `teachplan` VALUES ('7', 'Hello World', '5', '3', '1', NULL, NULL, '4028e581617f945f01617f9dabc40000', '2', '0', NULL);
INSERT INTO `teachplan` VALUES ('8', '操作系统原理', '1', '2', NULL, NULL, NULL, '4028e581617f945f01617f9dabc40000', '6', '0', NULL);

-- ----------------------------
-- Table structure for teachplan_media
-- ----------------------------
DROP TABLE IF EXISTS `teachplan_media`;
CREATE TABLE `teachplan_media`  (
  `teachplan_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程计划id',
  `media_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '媒资文件id',
  `media_fileoriginalname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '媒资文件的原始名称',
  `media_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '媒资文件访问地址',
  `courseid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程Id',
  PRIMARY KEY (`teachplan_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachplan_media
-- ----------------------------
INSERT INTO `teachplan_media` VALUES ('40281f00752678650175268ed3ea0002', '0485c48656a57f766a96531ee5a93b8f', '26-页面查询接口测试-Postman 【www.zxit8.com】.avi', '/0/4/0485c48656a57f766a96531ee5a93b8f/hls/0485c48656a57f766a96531ee5a93b8f.m3u8', '297e7c7c62b888f00162b8a7dec20000');

-- ----------------------------
-- Table structure for teachplan_media_pub
-- ----------------------------
DROP TABLE IF EXISTS `teachplan_media_pub`;
CREATE TABLE `teachplan_media_pub`  (
  `teachplan_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程计划id',
  `media_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '媒资文件id',
  `media_fileoriginalname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '媒资文件的原始名称',
  `media_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '媒资文件访问地址',
  `courseid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程Id',
  `timestamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'logstash使用',
  PRIMARY KEY (`teachplan_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachplan_media_pub
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
