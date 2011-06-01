-- MySQL dump 10.13  Distrib 5.1.49, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: jAPS2Serv
-- ------------------------------------------------------
-- Server version	5.1.49-1ubuntu8.1

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
-- Current Database: `jAPS2Serv`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `jAPS2Serv` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `jAPS2Serv`;

--
-- Table structure for table `authgroups`
--

DROP TABLE IF EXISTS `authgroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authgroups` (
  `groupname` varchar(20) NOT NULL,
  `descr` varchar(50) NOT NULL,
  PRIMARY KEY (`groupname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authgroups`
--

LOCK TABLES `authgroups` WRITE;
/*!40000 ALTER TABLE `authgroups` DISABLE KEYS */;
INSERT INTO `authgroups` VALUES ('administrators','Administrators'),('free','Free Access');
/*!40000 ALTER TABLE `authgroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authpermissions`
--

DROP TABLE IF EXISTS `authpermissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authpermissions` (
  `permissionname` varchar(30) NOT NULL,
  `descr` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`permissionname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authpermissions`
--

LOCK TABLES `authpermissions` WRITE;
/*!40000 ALTER TABLE `authpermissions` DISABLE KEYS */;
INSERT INTO `authpermissions` VALUES ('editContents','Content Editing'),('enterBackend','Access to Administration Area'),('manageCategories','Operations on Categories'),('managePages','Operations on Pages'),('manageResources','Operations on Resources'),('superuser','All functions'),('validateContents','Supervision of contents');
/*!40000 ALTER TABLE `authpermissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authrolepermissions`
--

DROP TABLE IF EXISTS `authrolepermissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authrolepermissions` (
  `rolename` varchar(30) NOT NULL,
  `permissionname` varchar(30) NOT NULL,
  PRIMARY KEY (`rolename`,`permissionname`),
  KEY `authrolepermissions_permissionname_fkey` (`permissionname`),
  CONSTRAINT `authrolepermissions_permissionname_fkey` FOREIGN KEY (`permissionname`) REFERENCES `authpermissions` (`permissionname`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `authrolepermissions_rolename_fkey` FOREIGN KEY (`rolename`) REFERENCES `authroles` (`rolename`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authrolepermissions`
--

LOCK TABLES `authrolepermissions` WRITE;
/*!40000 ALTER TABLE `authrolepermissions` DISABLE KEYS */;
INSERT INTO `authrolepermissions` VALUES ('admin','superuser');
/*!40000 ALTER TABLE `authrolepermissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authroles`
--

DROP TABLE IF EXISTS `authroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authroles` (
  `rolename` varchar(30) NOT NULL,
  `descr` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authroles`
--

LOCK TABLES `authroles` WRITE;
/*!40000 ALTER TABLE `authroles` DISABLE KEYS */;
INSERT INTO `authroles` VALUES ('admin','Administrator');
/*!40000 ALTER TABLE `authroles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authusergroups`
--

DROP TABLE IF EXISTS `authusergroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authusergroups` (
  `username` varchar(40) NOT NULL,
  `groupname` varchar(20) NOT NULL,
  PRIMARY KEY (`username`,`groupname`),
  KEY `new_fk_constraint` (`groupname`),
  CONSTRAINT `new_fk_constraint` FOREIGN KEY (`groupname`) REFERENCES `authgroups` (`groupname`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authusergroups`
--

LOCK TABLES `authusergroups` WRITE;
/*!40000 ALTER TABLE `authusergroups` DISABLE KEYS */;
INSERT INTO `authusergroups` VALUES ('admin','administrators');
/*!40000 ALTER TABLE `authusergroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authuserroles`
--

DROP TABLE IF EXISTS `authuserroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authuserroles` (
  `username` varchar(40) NOT NULL,
  `rolename` varchar(30) NOT NULL,
  PRIMARY KEY (`username`,`rolename`),
  KEY `authuserroles_rolename_fkey` (`rolename`),
  CONSTRAINT `authuserroles_rolename_fkey` FOREIGN KEY (`rolename`) REFERENCES `authroles` (`rolename`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authuserroles`
--

LOCK TABLES `authuserroles` WRITE;
/*!40000 ALTER TABLE `authuserroles` DISABLE KEYS */;
INSERT INTO `authuserroles` VALUES ('admin','admin');
/*!40000 ALTER TABLE `authuserroles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authusers`
--

DROP TABLE IF EXISTS `authusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authusers` (
  `username` varchar(40) NOT NULL,
  `passwd` varchar(40) DEFAULT NULL,
  `registrationdate` date NOT NULL,
  `lastaccess` date DEFAULT NULL,
  `lastpasswordchange` date DEFAULT NULL,
  `active` tinyint(4) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authusers`
--

LOCK TABLES `authusers` WRITE;
/*!40000 ALTER TABLE `authusers` DISABLE KEYS */;
INSERT INTO `authusers` VALUES ('admin','adminadmin','2008-10-10','2009-07-09',NULL,1);
/*!40000 ALTER TABLE `authusers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authusershortcuts`
--

DROP TABLE IF EXISTS `authusershortcuts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authusershortcuts` (
  `username` varchar(40) NOT NULL,
  `config` longtext NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authusershortcuts`
--

LOCK TABLES `authusershortcuts` WRITE;
/*!40000 ALTER TABLE `authusershortcuts` DISABLE KEYS */;
INSERT INTO `authusershortcuts` VALUES ('admin','<shortcuts>\n	<box pos=\"0\">core.component.user.list</box>\n	<box pos=\"1\">core.component.categories</box>\n	<box pos=\"2\">core.component.labels.list</box>\n	<box pos=\"3\">jacms.content.new</box>\n	<box pos=\"4\">jacms.content.list</box>\n	<box pos=\"5\">jacms.contentType</box>\n	<box pos=\"6\">core.portal.pageTree</box>\n	<box pos=\"7\">core.portal.showletType</box>\n	<box pos=\"8\">core.tools.entities</box>\n	<box pos=\"9\">core.tools.setting</box>\n</shortcuts>');
/*!40000 ALTER TABLE `authusershortcuts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-01 12:28:02
