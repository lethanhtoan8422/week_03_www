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
	(3, 'mon@gmail.com', '123456789', 'user', 4);

-- Dumping structure for table databaseweek2.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `cust_id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cust_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table databaseweek2.customer: ~4 rows (approximately)
INSERT INTO `customer` (`cust_id`, `address`, `cust_name`, `email`, `phone`) VALUES
	(1, 'HCM', 'admin', 'admin@gmail.com', '0329623380'),
	(2, 'HCM city', 'Lê Thanh Toàn', 'thanhtoan@gmail.com', '0329623380'),
	(3, 'HCM city', 'min', 'min@gmail.com', '0329623381'),
	(4, 'HCM city', 'Mon', 'mon@gmail.com', '0329623380');

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
	(1, 'HCM', '2002-04-08', 'thanhtoan@gmail.com', 'Lê Thanh Toàn', '0329623380', 1);

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

-- Dumping data for table databaseweek2.orders: ~4 rows (approximately)
INSERT INTO `orders` (`order_id`, `order_date`, `cust_id`, `emp_id`) VALUES
	(1, '2023-09-26', 1, 1),
	(2, '2023-09-26', 2, 1),
	(3, '2023-09-27', 3, 1),
	(4, '2023-09-29', 4, 1);

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

-- Dumping data for table databaseweek2.order_detail: ~8 rows (approximately)
INSERT INTO `order_detail` (`order_detail_id`, `note`, `price`, `quantity`, `order_id`, `product_id`) VALUES
	(1, 'note', 1, 1, 1, 1),
	(2, 'note', 8990000, 2, 2, 1),
	(3, 'note', 10500000, 1, 2, 2),
	(4, 'note', 8990000, 1, 3, 1),
	(5, 'note', 10500000, 1, 3, 2),
	(6, 'note', 8990000, 1, 4, 1),
	(7, 'note', 10500000, 1, 4, 2),
	(8, 'note', 13700000, 1, 4, 3);

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

-- Dumping data for table databaseweek2.product: ~10 rows (approximately)
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
	(10, 'iPhone 13 Pro Max', 'Apple', 'iPhone 13 Pro Max', 1, 'piece');

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

-- Dumping data for table databaseweek2.product_image: ~10 rows (approximately)
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
	(10, 'iPhone 13 Pro Max', 'iPhone13ProMax.png', 10);

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

-- Dumping data for table databaseweek2.product_price: ~10 rows (approximately)
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
	(10, 'Giá ban đầu', 28000000, '2023-09-24 17:34:10.000000', 10);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
