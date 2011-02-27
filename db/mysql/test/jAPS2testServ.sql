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
-- Create schema jAPS2testServ
--

CREATE DATABASE IF NOT EXISTS jAPS2testServ;
USE jAPS2testServ;

--
-- Definition of table `jAPS2testServ`.`authgroups`
--

DROP TABLE IF EXISTS `jAPS2testServ`.`authgroups`;
CREATE TABLE  `jAPS2testServ`.`authgroups` (
  `groupname` varchar(20) NOT NULL,
  `descr` varchar(50) NOT NULL,
  PRIMARY KEY (`groupname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2testServ`.`authgroups`
--

/*!40000 ALTER TABLE `authgroups` DISABLE KEYS */;
LOCK TABLES `authgroups` WRITE;
INSERT INTO `jAPS2testServ`.`authgroups` VALUES  ('administrators','Amministratori'),
 ('coach','Coach'),
 ('customers','Customers'),
 ('free','Accesso Libero'),
 ('helpdesk','Helpdesk'),
 ('management','Management');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authgroups` ENABLE KEYS */;


--
-- Definition of table `jAPS2testServ`.`authpermissions`
--

DROP TABLE IF EXISTS `jAPS2testServ`.`authpermissions`;
CREATE TABLE  `jAPS2testServ`.`authpermissions` (
  `permissionname` varchar(30) NOT NULL,
  `descr` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`permissionname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2testServ`.`authpermissions`
--

/*!40000 ALTER TABLE `authpermissions` DISABLE KEYS */;
LOCK TABLES `authpermissions` WRITE;
INSERT INTO `jAPS2testServ`.`authpermissions` VALUES  ('editContents','Redazione di Contenuti'),
 ('enterBackend','Accesso all\'Area di Amministrazione'),
 ('manageCategories','Operazioni su Categorie'),
 ('managePages','Operazioni su Pagine'),
 ('manageResources','Operazioni su Risorse'),
 ('superuser','Tutte le funzioni'),
 ('validateContents','Supervisione di Contenuti');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authpermissions` ENABLE KEYS */;


--
-- Definition of table `jAPS2testServ`.`authrolepermissions`
--

DROP TABLE IF EXISTS `jAPS2testServ`.`authrolepermissions`;
CREATE TABLE  `jAPS2testServ`.`authrolepermissions` (
  `rolename` varchar(30) NOT NULL,
  `permissionname` varchar(30) NOT NULL,
  PRIMARY KEY (`rolename`,`permissionname`),
  KEY `authrolepermissions_permissionname_fkey` (`permissionname`),
  CONSTRAINT `authrolepermissions_permissionname_fkey` FOREIGN KEY (`permissionname`) REFERENCES `authpermissions` (`permissionname`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `authrolepermissions_rolename_fkey` FOREIGN KEY (`rolename`) REFERENCES `authroles` (`rolename`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2testServ`.`authrolepermissions`
--

/*!40000 ALTER TABLE `authrolepermissions` DISABLE KEYS */;
LOCK TABLES `authrolepermissions` WRITE;
INSERT INTO `jAPS2testServ`.`authrolepermissions` VALUES  ('editor','editContents'),
 ('supervisor','editContents'),
 ('editor','enterBackend'),
 ('pageManager','enterBackend'),
 ('supervisor','enterBackend'),
 ('pageManager','managePages'),
 ('editor','manageResources'),
 ('admin','superuser'),
 ('supervisor','validateContents');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authrolepermissions` ENABLE KEYS */;


--
-- Definition of table `jAPS2testServ`.`authroles`
--

DROP TABLE IF EXISTS `jAPS2testServ`.`authroles`;
CREATE TABLE  `jAPS2testServ`.`authroles` (
  `rolename` varchar(30) NOT NULL,
  `descr` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2testServ`.`authroles`
--

/*!40000 ALTER TABLE `authroles` DISABLE KEYS */;
LOCK TABLES `authroles` WRITE;
INSERT INTO `jAPS2testServ`.`authroles` VALUES  ('admin','Tutte le funzioni'),
 ('editor','Gestore di Contenuti e Risorse'),
 ('pageManager','Gestore di Pagine'),
 ('supervisor','Supervisore di Contenuti');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authroles` ENABLE KEYS */;


--
-- Definition of table `jAPS2testServ`.`authusergroups`
--

DROP TABLE IF EXISTS `jAPS2testServ`.`authusergroups`;
CREATE TABLE  `jAPS2testServ`.`authusergroups` (
  `username` varchar(40) NOT NULL,
  `groupname` varchar(20) NOT NULL,
  PRIMARY KEY (`username`,`groupname`),
  KEY `new_fk_constraint` (`groupname`),
  CONSTRAINT `new_fk_constraint` FOREIGN KEY (`groupname`) REFERENCES `authgroups` (`groupname`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2testServ`.`authusergroups`
--

/*!40000 ALTER TABLE `authusergroups` DISABLE KEYS */;
LOCK TABLES `authusergroups` WRITE;
INSERT INTO `jAPS2testServ`.`authusergroups` VALUES  ('admin','administrators'),
 ('mainEditor','administrators'),
 ('editorCoach','coach'),
 ('pageManagerCoach','coach'),
 ('supervisorCoach','coach'),
 ('editorCoach','customers'),
 ('editorCustomers','customers'),
 ('pageManagerCoach','customers'),
 ('pageManagerCustomers','customers'),
 ('supervisorCoach','customers'),
 ('supervisorCustomers','customers');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authusergroups` ENABLE KEYS */;


--
-- Definition of table `jAPS2testServ`.`authuserroles`
--

DROP TABLE IF EXISTS `jAPS2testServ`.`authuserroles`;
CREATE TABLE  `jAPS2testServ`.`authuserroles` (
  `username` varchar(40) NOT NULL,
  `rolename` varchar(30) NOT NULL,
  PRIMARY KEY (`username`,`rolename`),
  KEY `authuserroles_rolename_fkey` (`rolename`),
  CONSTRAINT `authuserroles_rolename_fkey` FOREIGN KEY (`rolename`) REFERENCES `authroles` (`rolename`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2testServ`.`authuserroles`
--

/*!40000 ALTER TABLE `authuserroles` DISABLE KEYS */;
LOCK TABLES `authuserroles` WRITE;
INSERT INTO `jAPS2testServ`.`authuserroles` VALUES  ('admin','admin'),
 ('editorCoach','editor'),
 ('editorCustomers','editor'),
 ('mainEditor','editor'),
 ('pageManagerCoach','pageManager'),
 ('pageManagerCustomers','pageManager'),
 ('supervisorCoach','supervisor'),
 ('supervisorCustomers','supervisor');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authuserroles` ENABLE KEYS */;


--
-- Definition of table `jAPS2testServ`.`authusers`
--

DROP TABLE IF EXISTS `jAPS2testServ`.`authusers`;
CREATE TABLE  `jAPS2testServ`.`authusers` (
  `username` varchar(40) NOT NULL,
  `passwd` varchar(40) DEFAULT NULL,
  `registrationdate` date NOT NULL,
  `lastaccess` date DEFAULT NULL,
  `lastpasswordchange` date DEFAULT NULL,
  `active` tinyint(4) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2testServ`.`authusers`
--

/*!40000 ALTER TABLE `authusers` DISABLE KEYS */;
LOCK TABLES `authusers` WRITE;
INSERT INTO `jAPS2testServ`.`authusers` VALUES  ('admin','admin','2008-09-25','2010-11-30',NULL,1),
 ('editorCoach','editorCoach','2008-09-25','2010-11-30',NULL,1),
 ('editorCustomers','editorCustomers','2008-09-25','2010-11-30',NULL,1),
 ('mainEditor','mainEditor','2008-09-25','2010-11-30',NULL,1),
 ('pageManagerCoach','pageManagerCoach','2008-09-25','2010-11-30',NULL,1),
 ('pageManagerCustomers','pageManagerCustomers','2008-09-25','2010-11-30',NULL,1),
 ('supervisorCoach','supervisorCoach','2008-09-25','2010-11-30',NULL,1),
 ('supervisorCustomers','supervisorCustomers','2008-09-25','2010-11-30',NULL,1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `authusers` ENABLE KEYS */;


--
-- Definition of table `jAPS2testServ`.`authusershortcuts`
--

DROP TABLE IF EXISTS `jAPS2testServ`.`authusershortcuts`;
CREATE TABLE  `jAPS2testServ`.`authusershortcuts` (
  `username` varchar(40) NOT NULL,
  `config` longtext NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jAPS2testServ`.`authusershortcuts`
--

/*!40000 ALTER TABLE `authusershortcuts` DISABLE KEYS */;
LOCK TABLES `authusershortcuts` WRITE;
INSERT INTO `jAPS2testServ`.`authusershortcuts` VALUES  ('admin','<shortcuts>\n	<box pos=\"0\"></box>\n	<box pos=\"1\">core.component.user.list</box>\n	<box pos=\"2\">jacms.content.new</box>\n	<box pos=\"3\">jacms.content.list</box>\n	<box pos=\"4\">core.portal.pageTree</box>\n	<box pos=\"5\">core.portal.showletType</box>\n	<box pos=\"6\">core.tools.setting</box>\n	<box pos=\"7\">core.tools.entities</box>\n</shortcuts>');
UNLOCK TABLES;
/*!40000 ALTER TABLE `authusershortcuts` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
