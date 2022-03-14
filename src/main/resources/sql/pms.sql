-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pms
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `per_id` int NOT NULL AUTO_INCREMENT COMMENT '主键,权限表ID',
  `per_name` varchar(255) NOT NULL COMMENT '资源名称',
  `per_type` varchar(255) DEFAULT NULL COMMENT '资源类型,menu,button等',
  `per_permission` varchar(255) DEFAULT NULL COMMENT '权限字符串',
  `per_url` varchar(255) DEFAULT NULL COMMENT '访问url地址',
  `per_available` char(1) DEFAULT NULL COMMENT '是否可用,1:可用,0:不可用',
  `per_page_url` varchar(255) DEFAULT NULL COMMENT '页面地址',
  `per_parent_id` int DEFAULT NULL COMMENT '父菜单id,一般菜单为0',
  PRIMARY KEY (`per_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` (`per_id`, `per_name`, `per_type`, `per_permission`, `per_url`, `per_available`, `per_page_url`, `per_parent_id`) VALUES (1,'菜单','menu',NULL,'/menu',NULL,NULL,NULL),(2,'按钮','button',NULL,'/button',NULL,NULL,NULL),(3,'登录','login',NULL,'/user/login',NULL,NULL,NULL);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT COMMENT '主键,角色ID',
  `role_name` varchar(255) NOT NULL COMMENT '角色名称',
  `role_available` char(1) DEFAULT NULL COMMENT '是否可用,1:可用,0:不可用',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`role_id`, `role_name`, `role_available`) VALUES (1,'管理员',NULL),(2,'用户',NULL),(3,'游客',NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permissions`
--

DROP TABLE IF EXISTS `role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permissions` (
  `erp_id` int NOT NULL AUTO_INCREMENT COMMENT '主键,角色权限中间表ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  `per_id` int NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`erp_id`),
  KEY `role_permissions_role_role_id_fk` (`role_id`),
  KEY `role_permissions_permissions_per_id_fk` (`per_id`),
  CONSTRAINT `role_permissions_permissions_per_id_fk` FOREIGN KEY (`per_id`) REFERENCES `permission` (`per_id`),
  CONSTRAINT `role_permissions_role_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限中间表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
INSERT INTO `role_permissions` (`erp_id`, `role_id`, `per_id`) VALUES (1,1,1),(2,1,2),(3,1,3),(4,2,2),(5,2,3),(6,3,3);
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '主键,用户ID',
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `user_password` varchar(255) NOT NULL COMMENT '密码',
  `user_salt` varchar(255) DEFAULT NULL COMMENT '用户盐',
  `user_locked` char(1) DEFAULT NULL COMMENT '账户是否被锁定,1:锁定,0:未锁定',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `user_name`, `user_password`, `user_salt`, `user_locked`) VALUES (1,'ssx','a8cdf313b9955055c9a68af46db97101','',NULL),(2,'zhangsan','123',NULL,NULL),(3,'lisi','123',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `eur_id` int NOT NULL AUTO_INCREMENT COMMENT '主键,用户角色中间表ID',
  `user_id` int NOT NULL COMMENT '用户表主键',
  `role_id` int NOT NULL COMMENT '角色表主键',
  PRIMARY KEY (`eur_id`),
  KEY `user_role_role_role_id_fk` (`role_id`),
  KEY `user_role_user_user_id_fk` (`user_id`),
  CONSTRAINT `user_role_role_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `user_role_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色中间表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`eur_id`, `user_id`, `role_id`) VALUES (1,1,1),(2,2,2),(3,3,3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-14 20:27:34
