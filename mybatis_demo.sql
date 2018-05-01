/*
Navicat MySQL Data Transfer

Source Server         : MI-localhost
Source Server Version : 50553
Source Host           : localhost:3306
Source Database       : mybatis_demo

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2018-05-01 23:21:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `province_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_city_province` (`province_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES ('1', '成都', '1');
INSERT INTO `city` VALUES ('2', '巴中', '1');

-- ----------------------------
-- Table structure for province
-- ----------------------------
DROP TABLE IF EXISTS `province`;
CREATE TABLE `province` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of province
-- ----------------------------
INSERT INTO `province` VALUES ('1', '四川');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'AAA', '20');
INSERT INTO `user` VALUES ('3', '', '30');
INSERT INTO `user` VALUES ('4', 'Mr.Li', '18');
INSERT INTO `user` VALUES ('5', 'Mr.Ming', '18');
INSERT INTO `user` VALUES ('6', 'Mr.Feng', '25');
INSERT INTO `user` VALUES ('8', 'n1', null);
INSERT INTO `user` VALUES ('9', 'n2', null);
INSERT INTO `user` VALUES ('10', 'n1', '11');
INSERT INTO `user` VALUES ('11', 'n2', '12');
INSERT INTO `user` VALUES ('12', 'n3', '11');
INSERT INTO `user` VALUES ('13', 'n4', '12');
INSERT INTO `user` VALUES ('14', 'n3', '11');
INSERT INTO `user` VALUES ('15', 'n4', '12');
