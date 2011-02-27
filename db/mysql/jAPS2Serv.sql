-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.49-1ubuntu8.1


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema jAPS2Serv
--

CREATE DATABASE IF NOT EXISTS jAPS2Serv;
USE jAPS2Serv;

--
-- Definition of table `jAPS2Serv`.`authgroups`
--

DROP TABLE IF EXISTS `jAPS2Serv`.`authgroups`;
CREATE TABLE  `jAPS2Serv`.`authgroups` (
  `groupname` varchar(20) NOT NULL,
  `descr` varchar(50) NOT NULL,
  PRIMARY KEY (`groupname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2Serv`.`authgroups`
--

/*!40000 ALTER TABLE `authgroups` DISABLE KEYS */;
LOCK TABLES `authgroups` WRITE;
INSERT INTO `jAPS2Serv`.`authgroups` VALUES  ('administrators','Administrators'),
 ('free','Free Access');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authgroups` ENABLE KEYS */;


--
-- Definition of table `jAPS2Serv`.`authpermissions`
--

DROP TABLE IF EXISTS `jAPS2Serv`.`authpermissions`;
CREATE TABLE  `jAPS2Serv`.`authpermissions` (
  `permissionname` varchar(30) NOT NULL,
  `descr` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`permissionname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2Serv`.`authpermissions`
--

/*!40000 ALTER TABLE `authpermissions` DISABLE KEYS */;
LOCK TABLES `authpermissions` WRITE;
INSERT INTO `jAPS2Serv`.`authpermissions` VALUES  ('editContents','Content Editing'),
 ('enterBackend','Access to Administration Area'),
 ('manageCategories','Operations on Categories'),
 ('managePages','Operations on Pages'),
 ('manageResources','Operations on Resources'),
 ('superuser','All functions'),
 ('validateContents','Supervision of contents');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authpermissions` ENABLE KEYS */;


--
-- Definition of table `jAPS2Serv`.`authrolepermissions`
--

DROP TABLE IF EXISTS `jAPS2Serv`.`authrolepermissions`;
CREATE TABLE  `jAPS2Serv`.`authrolepermissions` (
  `rolename` varchar(30) NOT NULL,
  `permissionname` varchar(30) NOT NULL,
  PRIMARY KEY (`rolename`,`permissionname`),
  KEY `authrolepermissions_permissionname_fkey` (`permissionname`),
  CONSTRAINT `authrolepermissions_permissionname_fkey` FOREIGN KEY (`permissionname`) REFERENCES `authpermissions` (`permissionname`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `authrolepermissions_rolename_fkey` FOREIGN KEY (`rolename`) REFERENCES `authroles` (`rolename`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2Serv`.`authrolepermissions`
--

/*!40000 ALTER TABLE `authrolepermissions` DISABLE KEYS */;
LOCK TABLES `authrolepermissions` WRITE;
INSERT INTO `jAPS2Serv`.`authrolepermissions` VALUES  ('admin','superuser');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authrolepermissions` ENABLE KEYS */;


--
-- Definition of table `jAPS2Serv`.`authroles`
--

DROP TABLE IF EXISTS `jAPS2Serv`.`authroles`;
CREATE TABLE  `jAPS2Serv`.`authroles` (
  `rolename` varchar(30) NOT NULL,
  `descr` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2Serv`.`authroles`
--

/*!40000 ALTER TABLE `authroles` DISABLE KEYS */;
LOCK TABLES `authroles` WRITE;
INSERT INTO `jAPS2Serv`.`authroles` VALUES  ('admin','Administrator');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authroles` ENABLE KEYS */;


--
-- Definition of table `jAPS2Serv`.`authusergroups`
--

DROP TABLE IF EXISTS `jAPS2Serv`.`authusergroups`;
CREATE TABLE  `jAPS2Serv`.`authusergroups` (
  `username` varchar(40) NOT NULL,
  `groupname` varchar(20) NOT NULL,
  PRIMARY KEY (`username`,`groupname`),
  KEY `new_fk_constraint` (`groupname`),
  CONSTRAINT `new_fk_constraint` FOREIGN KEY (`groupname`) REFERENCES `authgroups` (`groupname`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2Serv`.`authusergroups`
--

/*!40000 ALTER TABLE `authusergroups` DISABLE KEYS */;
LOCK TABLES `authusergroups` WRITE;
INSERT INTO `jAPS2Serv`.`authusergroups` VALUES  ('admin','administrators');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authusergroups` ENABLE KEYS */;


--
-- Definition of table `jAPS2Serv`.`authuserroles`
--

DROP TABLE IF EXISTS `jAPS2Serv`.`authuserroles`;
CREATE TABLE  `jAPS2Serv`.`authuserroles` (
  `username` varchar(40) NOT NULL,
  `rolename` varchar(30) NOT NULL,
  PRIMARY KEY (`username`,`rolename`),
  KEY `authuserroles_rolename_fkey` (`rolename`),
  CONSTRAINT `authuserroles_rolename_fkey` FOREIGN KEY (`rolename`) REFERENCES `authroles` (`rolename`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2Serv`.`authuserroles`
--

/*!40000 ALTER TABLE `authuserroles` DISABLE KEYS */;
LOCK TABLES `authuserroles` WRITE;
INSERT INTO `jAPS2Serv`.`authuserroles` VALUES  ('admin','admin');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authuserroles` ENABLE KEYS */;


--
-- Definition of table `jAPS2Serv`.`authusers`
--

DROP TABLE IF EXISTS `jAPS2Serv`.`authusers`;
CREATE TABLE  `jAPS2Serv`.`authusers` (
  `username` varchar(40) NOT NULL,
  `passwd` varchar(40) DEFAULT NULL,
  `registrationdate` date NOT NULL,
  `lastaccess` date DEFAULT NULL,
  `lastpasswordchange` date DEFAULT NULL,
  `active` tinyint(4) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2Serv`.`authusers`
--

/*!40000 ALTER TABLE `authusers` DISABLE KEYS */;
LOCK TABLES `authusers` WRITE;
INSERT INTO `jAPS2Serv`.`authusers` VALUES  ('admin','adminadmin','2008-10-10','2009-07-09',NULL,1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `authusers` ENABLE KEYS */;


--
-- Definition of table `jAPS2Serv`.`authusershortcuts`
--

DROP TABLE IF EXISTS `jAPS2Serv`.`authusershortcuts`;
CREATE TABLE  `jAPS2Serv`.`authusershortcuts` (
  `username` varchar(40) NOT NULL,
  `config` longtext NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2Serv`.`authusershortcuts`
--

/*!40000 ALTER TABLE `authusershortcuts` DISABLE KEYS */;
LOCK TABLES `authusershortcuts` WRITE;
INSERT INTO `jAPS2Serv`.`authusershortcuts` VALUES  ('admin','<shortcuts>\n	<box pos=\"0\">core.component.user.list</box>\n	<box pos=\"1\">core.component.categories</box>\n	<box pos=\"2\">core.component.labels.list</box>\n	<box pos=\"3\">jacms.content.new</box>\n	<box pos=\"4\">jacms.content.list</box>\n	<box pos=\"5\">jacms.contentType</box>\n	<box pos=\"6\">core.portal.pageTree</box>\n	<box pos=\"7\">core.portal.showletType</box>\n	<box pos=\"8\">core.tools.entities</box>\n	<box pos=\"9\">core.tools.setting</box>\n</shortcuts>');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authusershortcuts` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
