/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.1.62-community : Database - tcc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tcc` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tcc`;
/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.1.62-community : Database - tcc_cap
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tcc_cap` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tcc_cap`;

/*Table structure for table `cap_capital_account` */

DROP TABLE IF EXISTS `cap_capital_account`;

CREATE TABLE `cap_capital_account` (
  `CAPITAL_ACCOUNT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BALANCE_AMOUNT` decimal(10,0) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CAPITAL_ACCOUNT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `cap_trade_order` */

DROP TABLE IF EXISTS `cap_trade_order`;

CREATE TABLE `cap_trade_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SELF_USER_ID` bigint(11) DEFAULT NULL,
  `OPPOSITE_USER_ID` bigint(11) DEFAULT NULL,
  `MERCHANT_ORDER_NO` varchar(45) NOT NULL,
  `AMOUNT` decimal(10,0) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UX_MERCHANT_ORDER_NO` (`MERCHANT_ORDER_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=254 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.1.62-community : Database - tcc_ord
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tcc_ord` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tcc_ord`;

/*Table structure for table `ord_order` */

DROP TABLE IF EXISTS `ord_order`;

CREATE TABLE `ord_order` (
  `ORDER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PAYER_USER_ID` int(11) DEFAULT NULL,
  `PAYEE_USER_ID` int(11) DEFAULT NULL,
  `RED_PACKET_PAY_AMOUNT` decimal(10,0) DEFAULT NULL,
  `CAPITAL_PAY_AMOUNT` decimal(10,0) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  `MERCHANT_ORDER_NO` varchar(45) NOT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ORDER_ID`),
  UNIQUE KEY `MERCHANT_ORDER_NO_UNIQUE` (`MERCHANT_ORDER_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=259 DEFAULT CHARSET=utf8;

/*Table structure for table `ord_order_line` */

DROP TABLE IF EXISTS `ord_order_line`;

CREATE TABLE `ord_order_line` (
  `ORDER_LINE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` int(11) DEFAULT NULL,
  `QUANTITY` decimal(10,0) DEFAULT NULL,
  `UNIT_PRICE` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`ORDER_LINE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Table structure for table `ord_product` */

DROP TABLE IF EXISTS `ord_product`;

CREATE TABLE `ord_product` (
  `PRODUCT_ID` int(11) NOT NULL,
  `SHOP_ID` int(11) NOT NULL,
  `PRODUCT_NAME` varchar(64) DEFAULT NULL,
  `PRICE` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`PRODUCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ord_shop` */

DROP TABLE IF EXISTS `ord_shop`;

CREATE TABLE `ord_shop` (
  `SHOP_ID` int(11) NOT NULL,
  `OWNER_USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`SHOP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.1.62-community : Database - tcc_red
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tcc_red` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tcc_red`;

/*Table structure for table `red_red_packet_account` */

DROP TABLE IF EXISTS `red_red_packet_account`;

CREATE TABLE `red_red_packet_account` (
  `RED_PACKET_ACCOUNT_ID` int(11) NOT NULL,
  `BALANCE_AMOUNT` decimal(10,0) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`RED_PACKET_ACCOUNT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `red_trade_order` */

DROP TABLE IF EXISTS `red_trade_order`;

CREATE TABLE `red_trade_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SELF_USER_ID` bigint(11) DEFAULT NULL,
  `OPPOSITE_USER_ID` bigint(11) DEFAULT NULL,
  `MERCHANT_ORDER_NO` varchar(45) NOT NULL,
  `AMOUNT` decimal(10,0) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `MERCHANT_ORDER_NO_UNIQUE` (`MERCHANT_ORDER_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
