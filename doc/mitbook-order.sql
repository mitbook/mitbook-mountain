/*
Navicat MySQL Data Transfer

Source Server         : 远程mysql
Source Server Version : 50729
Source Host           : 192.168.2.102:3306
Source Database       : mitbook-order

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-07-12 12:33:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `order_no` varchar(64) NOT NULL,
  `user_id` int(20) NOT NULL,
  `create_date` datetime NOT NULL,
  `product_id` varchar(32) NOT NULL,
  `product_num` int(10) DEFAULT NULL,
  PRIMARY KEY (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_info
-- ----------------------------
