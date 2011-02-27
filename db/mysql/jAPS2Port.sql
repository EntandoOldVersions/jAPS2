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
-- Create schema jAPS2Port
--

CREATE DATABASE IF NOT EXISTS jAPS2Port;
USE jAPS2Port;
CREATE TABLE  `jAPS2Port`.`categories` (
  `catcode` varchar(30) NOT NULL,
  `parentcode` varchar(30) NOT NULL,
  `titles` longtext NOT NULL,
  PRIMARY KEY (`catcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `jAPS2Port`.`categories` VALUES  ('home','home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"it\">Generale</property>\n<property key=\"en\">All</property>\n</properties>\n\n');
CREATE TABLE  `jAPS2Port`.`contentmodels` (
  `modelid` int(11) NOT NULL,
  `contenttype` varchar(30) NOT NULL,
  `descr` varchar(50) NOT NULL,
  `model` longtext,
  `stylesheet` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`modelid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE  `jAPS2Port`.`contentrelations` (
  `contentid` varchar(16) NOT NULL,
  `refpage` varchar(30) DEFAULT NULL,
  `refcontent` varchar(16) DEFAULT NULL,
  `refresource` varchar(16) DEFAULT NULL,
  `refcategory` varchar(30) DEFAULT NULL,
  `refgroup` varchar(20) DEFAULT NULL,
  KEY `contentrelations_contentid_fkey` (`contentid`),
  KEY `contentrelations_refcategory_fkey` (`refcategory`),
  KEY `contentrelations_refcontent_fkey` (`refcontent`),
  KEY `contentrelations_refpage_fkey` (`refpage`),
  KEY `contentrelations_refresource_fkey` (`refresource`),
  CONSTRAINT `contentrelations_contentid_fkey` FOREIGN KEY (`contentid`) REFERENCES `contents` (`contentid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contentrelations_refcategory_fkey` FOREIGN KEY (`refcategory`) REFERENCES `categories` (`catcode`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contentrelations_refcontent_fkey` FOREIGN KEY (`refcontent`) REFERENCES `contents` (`contentid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contentrelations_refpage_fkey` FOREIGN KEY (`refpage`) REFERENCES `pages` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contentrelations_refresource_fkey` FOREIGN KEY (`refresource`) REFERENCES `resources` (`resid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE  `jAPS2Port`.`contents` (
  `contentid` varchar(16) NOT NULL,
  `contenttype` varchar(30) NOT NULL,
  `descr` varchar(260) NOT NULL,
  `status` varchar(12) NOT NULL,
  `workxml` longtext NOT NULL,
  `created` varchar(20) DEFAULT NULL,
  `lastmodified` varchar(20) DEFAULT NULL,
  `onlinexml` longtext,
  `maingroup` varchar(20) NOT NULL,
  `currentversion` varchar(7) NOT NULL,
  `lasteditor` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`contentid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE  `jAPS2Port`.`contentsearch` (
  `contentid` varchar(16) NOT NULL,
  `attrname` varchar(30) NOT NULL,
  `textvalue` varchar(255) DEFAULT NULL,
  `datevalue` date DEFAULT NULL,
  `numvalue` int(11) DEFAULT NULL,
  `langcode` varchar(2) DEFAULT NULL,
  KEY `contentsearch_contentid_fkey` (`contentid`),
  CONSTRAINT `contentsearch_contentid_fkey` FOREIGN KEY (`contentid`) REFERENCES `contents` (`contentid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE  `jAPS2Port`.`localstrings` (
  `keycode` varchar(50) NOT NULL,
  `langcode` varchar(2) NOT NULL,
  `stringvalue` longtext NOT NULL,
  PRIMARY KEY (`keycode`,`langcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `jAPS2Port`.`localstrings` VALUES  ('ADMINISTRATION_BASIC','en','Normal'),
 ('ADMINISTRATION_BASIC','it','Normale'),
 ('ADMINISTRATION_BASIC_GOTO','en','Go to the administration with normal client'),
 ('ADMINISTRATION_BASIC_GOTO','it','Accedi con client normale'),
 ('ADMINISTRATION_MINT','en','Advanced'),
 ('ADMINISTRATION_MINT','it','Avanzata'),
 ('ADMINISTRATION_MINT_GOTO','en','Go to the administration with advanced client'),
 ('ADMINISTRATION_MINT_GOTO','it','Accedi con client avanzato'),
 ('GENERIC_ERROR','en','Generic Error'),
 ('GENERIC_ERROR','it','Errore Generico'),
 ('PAGE','en','page'),
 ('PAGE','it','pagina'),
 ('PAGE_MODEL','en','page model'),
 ('PAGE_MODEL','it','modello pagina'),
 ('PAGE_NOT_FOUND','en','Page not found'),
 ('PAGE_NOT_FOUND','it','Pagina non trovata'),
 ('PAGE_TITLE','en','page title'),
 ('PAGE_TITLE','it','titolo pagina'),
 ('USER_NOT_ALLOWED','en','User not allowed'),
 ('USER_NOT_ALLOWED','it','Utente non autorizzato');
CREATE TABLE  `jAPS2Port`.`pagemodels` (
  `code` varchar(40) NOT NULL,
  `descr` varchar(50) NOT NULL,
  `frames` longtext,
  `plugincode` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `jAPS2Port`.`pagemodels` VALUES  ('home','Home Page',NULL,NULL),
 ('service','Service Page','<frames>\n	<frame pos=\"0\">\n		<descr>Sample Frame</descr>\n	</frame>	\n</frames>',NULL);
CREATE TABLE  `jAPS2Port`.`pages` (
  `code` varchar(30) NOT NULL,
  `parentcode` varchar(30) DEFAULT NULL,
  `pos` int(11) NOT NULL,
  `modelcode` varchar(40) NOT NULL,
  `titles` longtext,
  `groupcode` varchar(30) NOT NULL,
  `showinmenu` tinyint(4) NOT NULL,
  `extraconfig` longtext,
  PRIMARY KEY (`code`),
  KEY `pages_modelcode_fkey` (`modelcode`),
  CONSTRAINT `pages_modelcode_fkey` FOREIGN KEY (`modelcode`) REFERENCES `pagemodels` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `jAPS2Port`.`pages` VALUES  ('errorpage','service',5,'service','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Error page</property>\n<property key=\"it\">Pagina di errore</property>\n</properties>\n\n','free',1,NULL),
 ('homepage','homepage',-1,'home','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Home</property>\n<property key=\"it\">Home</property>\n</properties>\n\n','free',1,NULL),
 ('login','service',6,'service','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"it\">Pagina di login</property>\n<property key=\"en\">Login</property>\n</properties>','free',1,NULL),
 ('notfound','service',4,'service','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Page not found</property>\n<property key=\"it\">Pagina non trovata</property>\n</properties>\n\n','free',1,NULL),
 ('service','homepage',5,'service','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"it\">Pagine di Servizio</property>\n<property key=\"en\">Service</property>\n</properties>','free',0,NULL);
CREATE TABLE  `jAPS2Port`.`resourcerelations` (
  `resid` varchar(16) NOT NULL,
  `refcategory` varchar(30) DEFAULT NULL,
  KEY `resourcerelations_refcategory_fkey` (`refcategory`),
  KEY `resourcerelations_resid_fkey` (`resid`),
  CONSTRAINT `resourcerelations_refcategory_fkey` FOREIGN KEY (`refcategory`) REFERENCES `categories` (`catcode`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `resourcerelations_resid_fkey` FOREIGN KEY (`resid`) REFERENCES `resources` (`resid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE  `jAPS2Port`.`resources` (
  `resid` varchar(16) NOT NULL,
  `restype` varchar(30) NOT NULL,
  `descr` varchar(260) NOT NULL,
  `maingroup` varchar(20) NOT NULL,
  `xml` longtext NOT NULL,
  `masterfilename` varchar(100) NOT NULL,
  PRIMARY KEY (`resid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE  `jAPS2Port`.`showletcatalog` (
  `code` varchar(40) NOT NULL,
  `titles` longtext NOT NULL,
  `parameters` longtext,
  `plugincode` varchar(30) DEFAULT NULL,
  `parenttypecode` varchar(40) DEFAULT NULL,
  `defaultconfig` longtext,
  `locked` tinyint(4) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `jAPS2Port`.`showletcatalog` VALUES  ('content_viewer','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Contents - Publish a Content</property>\n<property key=\"it\">Contenuti - Pubblica un Contenuto</property>\n</properties>','<config>\n	<parameter name=\"contentId\">Content ID</parameter>\n	<parameter name=\"modelId\">Content Model ID</parameter>\n	<action name=\"viewerConfig\"/>\n</config>','jacms',NULL,NULL,1),
 ('content_viewer_list','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Contents - Publish a List of Contents</property>\n<property key=\"it\">Contenuti - Pubblica una Lista di Contenuti</property>\n</properties>','<config>\n	<parameter name=\"contentType\">Content Type (mandatory)</parameter>\n	<parameter name=\"modelId\">Content Model</parameter>\n	<parameter name=\"category\">Content Category</parameter>\n	<parameter name=\"maxElemForItem\">Contents for each page</parameter>\n	<parameter name=\"filters\" />\n	<action name=\"listViewerConfig\"/>\n</config>','jacms',NULL,NULL,1),
 ('formAction','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Internal Servlet</property>\n<property key=\"it\">Invocazione di una Servlet Interna</property>\n</properties>','<config>\n	<parameter name=\"actionPath\">\n		Path to an action or to a JSP. You must prepend \'/ExtStr2\' to any Struts2 action path\n	</parameter>\n	<action name=\"configSimpleParameter\"/>\n</config>',NULL,NULL,NULL,1),
 ('login_form','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Login Form</property>\n<property key=\"it\">Form di Login</property>\n</properties>',NULL,NULL,NULL,NULL,1),
 ('messages_system','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">System Messages</property>\n<property key=\"it\">Messaggi di Sistema</property>\n</properties>',NULL,NULL,NULL,NULL,1),
 ('search_result','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<properties>\n<property key=\"en\">Search - Search Result</property>\n<property key=\"it\">Ricerca - Risultati della Ricerca</property>\n</properties>',NULL,'jacms',NULL,NULL,1);
CREATE TABLE  `jAPS2Port`.`showletconfig` (
  `pagecode` varchar(30) NOT NULL,
  `framepos` int(11) NOT NULL,
  `showletcode` varchar(40) NOT NULL,
  `config` longtext,
  `publishedcontent` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`pagecode`,`framepos`),
  KEY `showletconfig_showletcode_fkey` (`showletcode`),
  CONSTRAINT `showletconfig_pagecode_fkey` FOREIGN KEY (`pagecode`) REFERENCES `pages` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `showletconfig_showletcode_fkey` FOREIGN KEY (`showletcode`) REFERENCES `showletcatalog` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE  `jAPS2Port`.`sysconfig` (
  `version` varchar(10) NOT NULL,
  `item` varchar(40) NOT NULL,
  `descr` varchar(100) DEFAULT NULL,
  `config` longtext,
  PRIMARY KEY (`version`,`item`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `jAPS2Port`.`sysconfig` VALUES  ('production','contentTypes','Definition of the Content Types','<contenttypes>\n</contenttypes>'),
 ('production','imageDimensions','Definition of the resized image dimensions','<Dimensions>\n	<Dimension>\n		<id>1</id>\n		<dimx>90</dimx>\n		<dimy>90</dimy>\n	</Dimension>\n	<Dimension>\n		<id>2</id>\n		<dimx>130</dimx>\n		<dimy>130</dimy>\n	</Dimension>\n	<Dimension>\n		<id>3</id>\n		<dimx>150</dimx>\n		<dimy>150</dimy>\n	</Dimension>\n</Dimensions>\n'),
 ('production','langs','Definition of the system languages','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Langs>\n	<Lang>\n		<code>it</code>\n		<descr>Italiano</descr>\n	</Lang>\n	<Lang>\n		<code>en</code>\n		<descr>English</descr>\n		<default>true</default>\n	</Lang>\n</Langs>\n\n'),
 ('production','params','Configuration params. Tags other than \"Param\" are ignored','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<Params>\r\n	<Param name=\"urlStyle\">classic</Param>\r\n	<Param name=\"hypertextEditor\">none</Param>\r\n	<Param name=\"treeStyle_page\">classic</Param>\r\n	<Param name=\"treeStyle_category\">classic</Param>\r\n	<Param name=\"startLangFromBrowser\">false</Param>\r\n	<SpecialPages>\r\n		<Param name=\"notFoundPageCode\">notfound</Param>\r\n		<Param name=\"homePageCode\">homepage</Param>\r\n		<Param name=\"errorPageCode\">errorpage</Param>\r\n		<Param name=\"loginPageCode\">login</Param>\r\n	</SpecialPages>\r\n	<ExtendendPrivacyModule>\r\n		<Param name=\"extendedPrivacyModuleEnabled\">false</Param>\r\n		<Param name=\"maxMonthsSinceLastAccess\">6</Param>\r\n		<Param name=\"maxMonthsSinceLastPasswordChange\">3</Param>        \r\n	</ExtendendPrivacyModule>\r\n</Params>\r\n'),
 ('production','subIndexDir','Name of the sub-directory containing content indexing files','index');
CREATE TABLE  `jAPS2Port`.`uniquekeys` (
  `id` int(11) NOT NULL DEFAULT '0',
  `keyvalue` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `jAPS2Port`.`uniquekeys` VALUES  (1,1);
CREATE TABLE  `jAPS2Port`.`workcontentrelations` (
  `contentid` varchar(16) NOT NULL,
  `refcategory` varchar(30) DEFAULT NULL,
  KEY `workcontentrelations_contentid_fkey` (`contentid`),
  KEY `workcontentrelations_refcategory_fkey` (`refcategory`),
  CONSTRAINT `workcontentrelations_contentid_fkey` FOREIGN KEY (`contentid`) REFERENCES `contents` (`contentid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE  `jAPS2Port`.`workcontentsearch` (
  `contentid` varchar(16) DEFAULT NULL,
  `attrname` varchar(30) NOT NULL,
  `textvalue` varchar(255) DEFAULT NULL,
  `datevalue` date DEFAULT NULL,
  `numvalue` int(11) DEFAULT NULL,
  `langcode` varchar(2) DEFAULT NULL,
  KEY `workcontentsearch_contentid_fkey` (`contentid`),
  CONSTRAINT `workcontentsearch_contentid_fkey` FOREIGN KEY (`contentid`) REFERENCES `contents` (`contentid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
