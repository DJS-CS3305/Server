-- MySQL dump 10.13  Distrib 5.5.24, for Win64 (x86)
--
-- Host: localhost    Database: SCOffice
-- ------------------------------------------------------
-- Server version	5.5.24-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `SCOffice`
--

/*!40000 DROP DATABASE IF EXISTS `SCOffice`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `scoffice` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `SCOffice`;

--
-- Temporary table structure for view `completedcourses`
--

DROP TABLE IF EXISTS `completedcourses`;
/*!50001 DROP VIEW IF EXISTS `completedcourses`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `completedcourses` (
  `code` char(6),
  `username` varchar(255)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses` (
  `code` char(6) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `lecturer` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `fee` int(11) DEFAULT NULL,
  `description` text,
  `startDate` date DEFAULT NULL,
  `courseDuration` int(11) DEFAULT NULL,
  `classDuration` int(11) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `startTime` time DEFAULT NULL,
  `hits` int(11) DEFAULT '0',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('PH1101','Photojournalism for Beginners','Frank West','Western Gateway Building 109',1899,'','2013-06-16',3,60,8,'09:00:00',0),('UB1209','Basics of Underwater Basket Weaving','Prof. Daffyd Lloyd','Western Gateway Building G07',599,'Have a new image:\n\n<imagestart link=http://i.imgur.com/YqRQl.jpg height=480 width=640 imageend>','2013-06-15',5,120,3,'14:00:00',21),('VT2097','Proper Dog Care','Prof. Daffyd Lloyd','Western Gateway Building G07',1149,'','2013-07-15',7,90,6,'14:30:00',0);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecturers`
--

DROP TABLE IF EXISTS `lecturers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lecturers` (
  `name` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturers`
--

LOCK TABLES `lecturers` WRITE;
/*!40000 ALTER TABLE `lecturers` DISABLE KEYS */;
INSERT INTO `lecturers` VALUES ('Frank West',''),('Prof. Daffyd Lloyd','');
/*!40000 ALTER TABLE `lecturers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `mailinglist`
--

DROP TABLE IF EXISTS `mailinglist`;
/*!50001 DROP VIEW IF EXISTS `mailinglist`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `mailinglist` (
  `code` char(6),
  `email` varchar(255)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `content` text,
  `replyId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registrations`
--

DROP TABLE IF EXISTS `registrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registrations` (
  `courseCode` char(6) NOT NULL DEFAULT '',
  `username` varchar(255) NOT NULL DEFAULT '',
  `daysRemaining` int(11) DEFAULT NULL,
  `hasStarted` bit(1) NOT NULL DEFAULT b'0',
  `hasPaid` bit(1) NOT NULL DEFAULT b'0',
  `wasRefunded` bit(1) NOT NULL DEFAULT b'0',
  `paypalUsername` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`courseCode`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registrations`
--

LOCK TABLES `registrations` WRITE;
/*!40000 ALTER TABLE `registrations` DISABLE KEYS */;
INSERT INTO `registrations` VALUES ('PH1101','bobkatt362',0,'','','\0','test2'),('PH1101','jmurray',0,'','','\0','test1'),('VT2097','jmurray',7,'\0','\0','\0','test1');
/*!40000 ALTER TABLE `registrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `unrepliedusermessages`
--

DROP TABLE IF EXISTS `unrepliedusermessages`;
/*!50001 DROP VIEW IF EXISTS `unrepliedusermessages`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `unrepliedusermessages` (
  `id` int(11),
  `username` varchar(255),
  `content` text
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(255) NOT NULL,
  `password` char(64) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `gender` bit(1) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `streetAddr` varchar(255) DEFAULT NULL,
  `townAddr` varchar(255) DEFAULT NULL,
  `stateAddr` varchar(255) DEFAULT NULL,
  `countryAddr` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telNo` varchar(255) DEFAULT NULL,
  `isAdmin` bit(1) DEFAULT b'0',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('Admin001','4194d1706ed1f408d5e02d672777019f4d5385c766a8c6ca8acba3167d36a7b9','Michael','Coleman','',28,'11 Cedar Row','Cork City','Co. Cork','Ireland','110011331@umail.ucc.ie','0095929099',''),('bobkatt362','5c773b22ea79d367b38810e7e9ad108646ed62e231868cefb0b1280ea88ac4f0','Bobkatt','Goldthwaite','',45,'65 Cedar Avenue','Gregorville','Yorkshire','United Kingdom','110011331@umail.ucc.ie','0095453047','\0'),('jmurray','71675606c3f8c263eb9387524b28b6d43b856ca159c2f8accbe43e36eec0c28a','Janice','Murray','\0',21,'32 Willow Drive','Ballincollig','Co. Cork','Ireland','110011331@umail.ucc.ie','0095463091','\0'),('The Great Mansini','38f6662398ef057e53c63bfed93b8f50db63e59b8a2c3094ebe08112db257274','David','White','',32,'11 Maple Row','Bupkiss','Alabama','USA','110011331@umail.ucc.ie','0095929091','\0');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `SCOffice`
--

USE `SCOffice`;

--
-- Final view structure for view `completedcourses`
--

/*!50001 DROP TABLE IF EXISTS `completedcourses`*/;
/*!50001 DROP VIEW IF EXISTS `completedcourses`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `completedcourses` AS select `courses`.`code` AS `code`,`users`.`username` AS `username` from ((`registrations` join `courses`) join `users`) where ((`courses`.`code` = `registrations`.`courseCode`) and (`users`.`username` = `registrations`.`username`) and (`registrations`.`daysRemaining` = 0)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `mailinglist`
--

/*!50001 DROP TABLE IF EXISTS `mailinglist`*/;
/*!50001 DROP VIEW IF EXISTS `mailinglist`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `mailinglist` AS select `courses`.`code` AS `code`,`users`.`email` AS `email` from ((`registrations` join `courses`) join `users`) where ((`courses`.`code` = `registrations`.`courseCode`) and (`users`.`username` = `registrations`.`username`) and (`registrations`.`hasPaid` = 1) and (`registrations`.`wasRefunded` = 0) and (`registrations`.`daysRemaining` > 0)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `unrepliedusermessages`
--

/*!50001 DROP TABLE IF EXISTS `unrepliedusermessages`*/;
/*!50001 DROP VIEW IF EXISTS `unrepliedusermessages`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `unrepliedusermessages` AS select `messages`.`id` AS `id`,`users`.`username` AS `username`,`messages`.`content` AS `content` from (`messages` join `users`) where ((`users`.`username` = `messages`.`username`) and (`users`.`isAdmin` = 0) and (`messages`.`replyId` = NULL)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-03-13 11:48:51
