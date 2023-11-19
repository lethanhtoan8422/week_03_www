-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.1.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for databaseweek2
CREATE DATABASE IF NOT EXISTS `databaseweek2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `databaseweek2`;

-- Dumping structure for table databaseweek2.accounts
CREATE TABLE IF NOT EXISTS `accounts` (
  `account_id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `cust_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  KEY `FKd8tv7cd2n0q2kh45le1dfp9mu` (`cust_id`),
  CONSTRAINT `FKd8tv7cd2n0q2kh45le1dfp9mu` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table databaseweek2.accounts: ~4 rows (approximately)
INSERT INTO `accounts` (`account_id`, `email`, `password`, `role`, `cust_id`) VALUES
	(1, 'admin@gmail.com', 'admin', 'admin', 1),
	(2, 'min@gmail.com', '123456789', 'user', 3),
	(3, 'mon@gmail.com', '123456789', 'user', 4),
	(4, 'mun@gmail.com', '123456789', 'user', 5);

-- Dumping structure for table databaseweek2.candidate
CREATE TABLE IF NOT EXISTS `candidate` (
  `can_id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`can_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table databaseweek2.candidate: ~0 rows (approximately)

-- Dumping structure for table databaseweek2.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `cust_id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cust_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table databaseweek2.customer: ~5 rows (approximately)
INSERT INTO `customer` (`cust_id`, `address`, `cust_name`, `email`, `phone`) VALUES
	(1, 'HCM', 'admin', 'admin@gmail.com', '0329623380'),
	(2, 'HCM city', 'Lê Thanh Toàn', 'thanhtoan@gmail.com', '0329623380'),
	(3, 'HCM city', 'Min', 'min@gmail.com', '0329623381'),
	(4, 'HCM city', 'Mon', 'mon@gmail.com', '0329623380'),
	(5, 'HCM tity', 'Mun', 'mun@gmail.com', '0329623385');

-- Dumping structure for table databaseweek2.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `emp_id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `dob` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table databaseweek2.employee: ~1 rows (approximately)
INSERT INTO `employee` (`emp_id`, `address`, `dob`, `email`, `full_name`, `phone`, `status`) VALUES
	(1, 'HCM', '2002-04-08', 'thanhtoan@gmail.com', 'Lê Thanh Toàn', '0329623380', 1),
	(2, 'HCM', '2003-12-23', 'em@gmail.com', 'Trần Văn Tánh', '0329623386', 1);

-- Dumping structure for table databaseweek2.experience
CREATE TABLE IF NOT EXISTS `experience` (
  `id` bigint(20) NOT NULL,
  `companyName` varchar(255) DEFAULT NULL,
  `from_date` date DEFAULT NULL,
  `role` tinyint(4) DEFAULT NULL,
  `to_date` date DEFAULT NULL,
  `work_desc` varchar(255) DEFAULT NULL,
  `can_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8d5oqe0wxh52v352i04qnuady` (`can_id`),
  CONSTRAINT `FK8d5oqe0wxh52v352i04qnuady` FOREIGN KEY (`can_id`) REFERENCES `candidate` (`can_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table databaseweek2.experience: ~0 rows (approximately)

-- Dumping structure for table databaseweek2.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` bigint(20) NOT NULL,
  `order_date` varchar(255) DEFAULT NULL,
  `cust_id` bigint(20) DEFAULT NULL,
  `emp_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FK89iy70txskld506d7n1cfpmpx` (`cust_id`),
  KEY `FKr9ug3vtws6i4uw4v1ef95qw9a` (`emp_id`),
  CONSTRAINT `FK89iy70txskld506d7n1cfpmpx` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`),
  CONSTRAINT `FKr9ug3vtws6i4uw4v1ef95qw9a` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table databaseweek2.orders: ~21 rows (approximately)
INSERT INTO `orders` (`order_id`, `order_date`, `cust_id`, `emp_id`) VALUES
	(1, '2023-10-13', 1, 1),
	(2, '2023-09-26', 2, 1),
	(3, '2023-09-27', 3, 1),
	(4, '2023-09-29', 4, 1),
	(5, '2023-01-10', 1, 1),
	(6, '2023-02-10', 1, 1),
	(7, '2023-03-10', 2, 1),
	(8, '2023-04-10', 1, 1),
	(9, '2023-05-10', 2, 1),
	(10, '2023-06-10', 2, 1),
	(11, '2023-07-10', 2, 1),
	(12, '2023-08-10', 2, 1),
	(13, '2023-09-10', 1, 1),
	(14, '2023-10-10', 2, 1),
	(15, '2023-10-13', 1, 1),
	(16, '2023-10-25', 4, 1),
	(17, '2023-10-25', 3, 1),
	(18, '2023-11-14', 3, 1),
	(19, '2023-11-15', 5, 1),
	(20, '2023-11-14', 5, 2),
	(21, '2023-11-15', 3, 2),
	(22, '2023-11-19', 5, 2),
	(23, '2023-11-19', 3, 2),
	(24, '2023-11-19', 3, 2),
	(25, '2023-11-19', 3, 2),
	(26, '2023-11-19', 3, 1);

-- Dumping structure for table databaseweek2.order_detail
CREATE TABLE IF NOT EXISTS `order_detail` (
  `order_detail_id` bigint(20) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`order_detail_id`),
  KEY `FKrws2q0si6oyd6il8gqe2aennc` (`order_id`),
  KEY `FKb8bg2bkty0oksa3wiq5mp5qnc` (`product_id`),
  CONSTRAINT `FKb8bg2bkty0oksa3wiq5mp5qnc` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `FKrws2q0si6oyd6il8gqe2aennc` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table databaseweek2.order_detail: ~36 rows (approximately)
INSERT INTO `order_detail` (`order_detail_id`, `note`, `price`, `quantity`, `order_id`, `product_id`) VALUES
	(1, 'note', 10000000, 1, 1, 1),
	(2, 'note', 8990000, 2, 2, 1),
	(3, 'note', 10500000, 1, 2, 2),
	(4, 'note', 8990000, 1, 3, 1),
	(5, 'note', 10500000, 1, 3, 2),
	(6, 'note', 8990000, 1, 4, 1),
	(7, 'note', 10500000, 1, 4, 2),
	(8, 'note', 13700000, 1, 4, 3),
	(9, 'note', 200000000, 20, 5, 1),
	(10, 'note', 300000000, 30, 6, 1),
	(11, 'note', 150000000, 15, 7, 1),
	(12, 'note', 100000000, 10, 8, 1),
	(13, 'note', 250000000, 25, 9, 1),
	(14, 'note', 200000000, 20, 10, 1),
	(15, 'note', 300000000, 30, 11, 1),
	(16, 'note', 320000000, 32, 12, 1),
	(17, 'note', 400000000, 40, 13, 1),
	(18, 'note', 350000000, 35, 14, 1),
	(19, 'note', 100000000, 10, 15, 1),
	(20, 'note', 10000000, 1, 16, 1),
	(21, 'note', 10500000, 1, 16, 2),
	(22, 'note', 13700000, 2, 16, 3),
	(23, 'note', 10000000, 1, 17, 1),
	(24, 'note', 10500000, 2, 17, 2),
	(25, 'note', 17000000, 1, 18, 7),
	(26, 'note', 24000000, 1, 18, 8),
	(27, 'note', 8990000, 1, 19, 1),
	(28, 'note', 10500000, 1, 19, 2),
	(29, 'note', 17000000, 1, 20, 7),
	(30, 'note', 14200000, 1, 20, 13),
	(31, 'note', 7500000, 1, 20, 26),
	(32, 'note', 25000000, 1, 21, 18),
	(33, 'note', 11000000, 1, 21, 12),
	(34, 'note', 6900000, 1, 21, 25),
	(35, 'note', 11500000, 1, 21, 22),
	(36, 'note', 29000000, 1, 21, 20),
	(37, 'note', 8990000, 1, 22, 1),
	(38, 'note', 10500000, 1, 22, 2),
	(39, 'note', 6500000, 1, 23, 5),
	(40, 'note', 11500000, 1, 24, 22),
	(41, 'note', 7500000, 1, 25, 26),
	(42, 'note', 13700000, 1, 26, 3);

-- Dumping structure for table databaseweek2.product
CREATE TABLE IF NOT EXISTS `product` (
  `product_id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `manufacturer_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table databaseweek2.product: ~30 rows (approximately)
INSERT INTO `product` (`product_id`, `description`, `manufacturer_name`, `name`, `status`, `unit`) VALUES
	(1, 'iPhone Xs Max', 'Apple', 'iPhone Xs Max', 1, 'piece'),
	(2, 'iPhone 11', 'Apple', 'iPhone 11', 1, 'piece'),
	(3, 'iPhone 12', 'Apple', 'iPhone 12', 1, 'piece'),
	(4, 'iPhone 12 Pro', 'Apple', 'iPhone 12 Pro', 1, 'piece'),
	(5, 'iPhone SE', 'Apple', 'iPhone SE', 1, 'piece'),
	(6, 'iPhone XR', 'Apple', 'iPhone XR', 1, 'piece'),
	(7, 'iPhone 13', 'Apple', 'iPhone 13', 1, 'piece'),
	(8, 'iPhone 13 Pro', 'Apple', 'iPhone 13 Pro', 1, 'piece'),
	(9, 'iPhone 13 Mini', 'Apple', 'iPhone 13 Mini', 1, 'piece'),
	(10, 'iPhone 13 Pro Max', 'Apple', 'iPhone 13 Pro Max', 1, 'piece'),
	(11, 'iPhone 14', 'Apple', 'iPhone 14', 1, 'piece'),
	(12, 'iPhone 14 Pro', 'Apple', 'iPhone 14 Pro', 1, 'piece'),
	(13, 'iPhone 14 Mini', 'Apple', 'iPhone 14 Mini', 1, 'piece'),
	(14, 'iPhone 14 Pro Max', 'Apple', 'iPhone 14 Pro Max', 1, 'piece'),
	(15, 'Samsung Galaxy S21', 'Samsung', 'Samsung Galaxy S21', 1, 'piece'),
	(16, 'Samsung Galaxy S21 Ultra', 'Samsung', 'Samsung Galaxy S21 Ultra', 1, 'piece'),
	(17, 'Google Pixel 6', 'Google', 'Google Pixel 6', 1, 'piece'),
	(18, 'Google Pixel 6 Pro', 'Google', 'Google Pixel 6 Pro', 1, 'piece'),
	(19, 'OnePlus 9', 'OnePlus', 'OnePlus 9', 1, 'piece'),
	(20, 'OnePlus 9 Pro', 'OnePlus', 'OnePlus 9 Pro', 1, 'piece'),
	(21, 'iPhone 15', 'Apple', 'iPhone 15', 1, 'piece'),
	(22, 'iPhone 15 Pro', 'Apple', 'iPhone 15 Pro', 1, 'piece'),
	(23, 'iPhone 15 Mini', 'Apple', 'iPhone 15 Mini', 1, 'piece'),
	(24, 'iPhone 15 Pro Max', 'Apple', 'iPhone 15 Pro Max', 1, 'piece'),
	(25, 'Samsung Galaxy S22', 'Samsung', 'Samsung Galaxy S22', 1, 'piece'),
	(26, 'Samsung Galaxy S22 Ultra', 'Samsung', 'Samsung Galaxy S22 Ultra', 1, 'piece'),
	(27, 'Google Pixel 7', 'Google', 'Google Pixel 7', 1, 'piece'),
	(28, 'Google Pixel 7 Pro', 'Google', 'Google Pixel 7 Pro', 1, 'piece'),
	(29, 'OnePlus 10', 'OnePlus', 'OnePlus 10', 1, 'piece'),
	(30, 'OnePlus 10 Pro', 'OnePlus', 'OnePlus 10 Pro', 1, 'piece');

-- Dumping structure for table databaseweek2.product_image
CREATE TABLE IF NOT EXISTS `product_image` (
  `image_id` bigint(20) NOT NULL,
  `alternative` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  KEY `FK6oo0cvcdtb6qmwsga468uuukk` (`product_id`),
  CONSTRAINT `FK6oo0cvcdtb6qmwsga468uuukk` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table databaseweek2.product_image: ~30 rows (approximately)
INSERT INTO `product_image` (`image_id`, `alternative`, `path`, `product_id`) VALUES
	(1, 'iPhone Xs Max', 'iPhoneXsMax64GB.png', 1),
	(2, 'iPhone 11', 'iPhone11.jpg', 2),
	(3, 'iPhone 12', 'iPhone12.png', 3),
	(4, 'iPhone 12 Pro', 'iPhone12Pro.png', 4),
	(5, 'iPhone SE', 'iPhoneSE.jpg', 5),
	(6, 'iPhone XR', 'iPhoneXR.png', 6),
	(7, 'iPhone 13', 'iPhone13.png', 7),
	(8, 'iPhone 13 Pro', 'iPhone13Pro.png', 8),
	(9, 'iPhone 13 Mini', 'iPhone13Mini.png', 9),
	(10, 'iPhone 13 Pro Max', 'iPhone13ProMax.png', 10),
	(11, 'iPhone 14', 'iPhone14.png', 11),
	(12, 'iPhone 14 Pro', 'iPhone14Pro.png', 12),
	(13, 'iPhone 14 Mini', 'iPhone14Mini.png', 13),
	(14, 'iPhone 14 Pro Max', 'iPhone14ProMax.png', 14),
	(15, 'Samsung Galaxy S21', 'SamsungGalaxyS21.png', 15),
	(16, 'Samsung Galaxy S21 Ultra', 'SamsungGalaxyS21Ultra.png', 16),
	(17, 'Google Pixel 6', 'GooglePixel6.png', 17),
	(18, 'Google Pixel 6 Pro', 'GooglePixel6Pro.png', 18),
	(19, 'OnePlus 9', 'OnePlus9.png', 19),
	(20, 'OnePlus 9 Pro', 'OnePlus9Pro.png', 20),
	(21, 'iPhone 15', 'iPhone15.png', 21),
	(22, 'iPhone 15 Pro', 'iPhone15Pro.png', 22),
	(23, 'iPhone 15 Mini', 'iPhone15Mini.png', 23),
	(24, 'iPhone 15 Pro Max', 'iPhone15ProMax.png', 24),
	(25, 'Samsung Galaxy S22', 'SamsungGalaxyS22.png', 25),
	(26, 'Samsung Galaxy S22 Ultra', 'SamsungGalaxyS22Ultra.png', 26),
	(27, 'Google Pixel 7', 'GooglePixel7.png', 27),
	(28, 'Google Pixel 7 Pro', 'GooglePixel7Pro.png', 28),
	(29, 'OnePlus 10', 'OnePlus10.png', 29),
	(30, 'OnePlus 10 Pro', 'OnePlus10Pro.png', 30);

-- Dumping structure for table databaseweek2.product_price
CREATE TABLE IF NOT EXISTS `product_price` (
  `price_id` bigint(20) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `price_date_time` datetime(6) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`price_id`),
  KEY `FKeupemu63ifqfc4txkskyy1hyi` (`product_id`),
  CONSTRAINT `FKeupemu63ifqfc4txkskyy1hyi` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table databaseweek2.product_price: ~30 rows (approximately)
INSERT INTO `product_price` (`price_id`, `note`, `price`, `price_date_time`, `product_id`) VALUES
	(1, 'Giá ban đầu', 8990000, '2023-09-24 17:34:10.000000', 1),
	(2, 'Giá ban đầu', 10500000, '2023-09-24 17:34:10.000000', 2),
	(3, 'Giá ban đầu', 13700000, '2023-09-24 17:34:10.000000', 3),
	(4, 'Giá ban đầu', 15000000, '2023-09-24 17:34:10.000000', 4),
	(5, 'Giá ban đầu', 6500000, '2023-09-24 17:34:10.000000', 5),
	(6, 'Giá ban đầu', 7000000, '2023-09-24 17:34:10.000000', 6),
	(7, 'Giá ban đầu', 17000000, '2023-09-24 17:34:10.000000', 7),
	(8, 'Giá ban đầu', 24000000, '2023-09-24 17:34:10.000000', 8),
	(9, 'Giá ban đầu', 20000000, '2023-09-24 17:34:10.000000', 9),
	(10, 'Giá ban đầu', 28000000, '2023-09-24 17:34:10.000000', 10),
	(11, 'Giá mới', 9500000, '2023-11-14 10:00:00.000000', 11),
	(12, 'Giá mới', 11000000, '2023-11-14 10:00:00.000000', 12),
	(13, 'Giá mới', 14200000, '2023-11-14 10:00:00.000000', 13),
	(14, 'Giá mới', 15500000, '2023-11-14 10:00:00.000000', 14),
	(15, 'Giá mới', 6700000, '2023-11-14 10:00:00.000000', 15),
	(16, 'Giá mới', 7300000, '2023-11-14 10:00:00.000000', 16),
	(17, 'Giá mới', 17500000, '2023-11-14 10:00:00.000000', 17),
	(18, 'Giá mới', 25000000, '2023-11-14 10:00:00.000000', 18),
	(19, 'Giá mới', 21000000, '2023-11-14 10:00:00.000000', 19),
	(20, 'Giá mới', 29000000, '2023-11-14 10:00:00.000000', 20),
	(21, 'Giá mới', 9800000, '2023-11-14 10:00:00.000000', 21),
	(22, 'Giá mới', 11500000, '2023-11-14 10:00:00.000000', 22),
	(23, 'Giá mới', 14800000, '2023-11-14 10:00:00.000000', 23),
	(24, 'Giá mới', 16100000, '2023-11-14 10:00:00.000000', 24),
	(25, 'Giá mới', 6900000, '2023-11-14 10:00:00.000000', 25),
	(26, 'Giá mới', 7500000, '2023-11-14 10:00:00.000000', 26),
	(27, 'Giá mới', 18000000, '2023-11-14 10:00:00.000000', 27),
	(28, 'Giá mới', 26000000, '2023-11-14 10:00:00.000000', 28),
	(29, 'Giá mới', 22000000, '2023-11-14 10:00:00.000000', 29),
	(30, 'Giá mới', 31000000, '2023-11-14 10:00:00.000000', 30);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
