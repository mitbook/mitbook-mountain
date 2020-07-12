/*
Navicat MySQL Data Transfer

Source Server         : 远程mysql
Source Server Version : 50729
Source Host           : 192.168.2.102:3306
Source Database       : mitbook-stock

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-07-12 12:33:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for product_stock_info
-- ----------------------------
DROP TABLE IF EXISTS `product_stock_info`;
CREATE TABLE `product_stock_info` (
  `product_id` varchar(20) NOT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `stock_num` int(20) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_stock_info
-- ----------------------------
INSERT INTO `product_stock_info` VALUES ('1', '华为p30', '83');
