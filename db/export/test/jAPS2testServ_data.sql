--
-- PostgreSQL database dump
--

-- Started on 2011-01-02 14:30:49 CET

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

--
-- TOC entry 1772 (class 0 OID 2673485)
-- Dependencies: 1478
-- Data for Name: authgroups; Type: TABLE DATA; Schema: public; Owner: agile
--

INSERT INTO authgroups (groupname, descr) VALUES ('administrators', 'Amministratori');
INSERT INTO authgroups (groupname, descr) VALUES ('coach', 'Coach');
INSERT INTO authgroups (groupname, descr) VALUES ('customers', 'Customers');
INSERT INTO authgroups (groupname, descr) VALUES ('free', 'Accesso Libero');
INSERT INTO authgroups (groupname, descr) VALUES ('helpdesk', 'Helpdesk');
INSERT INTO authgroups (groupname, descr) VALUES ('management', 'Management');


--
-- TOC entry 1773 (class 0 OID 2673488)
-- Dependencies: 1479
-- Data for Name: authpermissions; Type: TABLE DATA; Schema: public; Owner: agile
--

INSERT INTO authpermissions (permissionname, descr) VALUES ('managePages', 'Operazioni su Pagine');
INSERT INTO authpermissions (permissionname, descr) VALUES ('enterBackend', 'Accesso all''Area di Amministrazione');
INSERT INTO authpermissions (permissionname, descr) VALUES ('manageResources', 'Operazioni su Risorse');
INSERT INTO authpermissions (permissionname, descr) VALUES ('editContents', 'Redazione di Contenuti');
INSERT INTO authpermissions (permissionname, descr) VALUES ('validateContents', 'Supervisione di Contenuti');
INSERT INTO authpermissions (permissionname, descr) VALUES ('superuser', 'Tutte le funzioni');
INSERT INTO authpermissions (permissionname, descr) VALUES ('manageCategories', 'Operazioni su Categorie');


--
-- TOC entry 1774 (class 0 OID 2673491)
-- Dependencies: 1480
-- Data for Name: authrolepermissions; Type: TABLE DATA; Schema: public; Owner: agile
--

INSERT INTO authrolepermissions (rolename, permissionname) VALUES ('admin', 'superuser');
INSERT INTO authrolepermissions (rolename, permissionname) VALUES ('pageManager', 'enterBackend');
INSERT INTO authrolepermissions (rolename, permissionname) VALUES ('editor', 'enterBackend');
INSERT INTO authrolepermissions (rolename, permissionname) VALUES ('supervisor', 'enterBackend');
INSERT INTO authrolepermissions (rolename, permissionname) VALUES ('pageManager', 'managePages');
INSERT INTO authrolepermissions (rolename, permissionname) VALUES ('supervisor', 'editContents');
INSERT INTO authrolepermissions (rolename, permissionname) VALUES ('editor', 'editContents');
INSERT INTO authrolepermissions (rolename, permissionname) VALUES ('supervisor', 'validateContents');
INSERT INTO authrolepermissions (rolename, permissionname) VALUES ('editor', 'manageResources');


--
-- TOC entry 1775 (class 0 OID 2673494)
-- Dependencies: 1481
-- Data for Name: authroles; Type: TABLE DATA; Schema: public; Owner: agile
--

INSERT INTO authroles (rolename, descr) VALUES ('admin', 'Tutte le funzioni');
INSERT INTO authroles (rolename, descr) VALUES ('editor', 'Gestore di Contenuti e Risorse');
INSERT INTO authroles (rolename, descr) VALUES ('supervisor', 'Supervisore di Contenuti');
INSERT INTO authroles (rolename, descr) VALUES ('pageManager', 'Gestore di Pagine');


--
-- TOC entry 1776 (class 0 OID 2673497)
-- Dependencies: 1482
-- Data for Name: authusergroups; Type: TABLE DATA; Schema: public; Owner: agile
--

INSERT INTO authusergroups (username, groupname) VALUES ('pageManagerCoach', 'coach');
INSERT INTO authusergroups (username, groupname) VALUES ('pageManagerCustomers', 'customers');
INSERT INTO authusergroups (username, groupname) VALUES ('supervisorCoach', 'coach');
INSERT INTO authusergroups (username, groupname) VALUES ('supervisorCustomers', 'customers');
INSERT INTO authusergroups (username, groupname) VALUES ('editorCoach', 'coach');
INSERT INTO authusergroups (username, groupname) VALUES ('editorCustomers', 'customers');
INSERT INTO authusergroups (username, groupname) VALUES ('supervisorCoach', 'customers');
INSERT INTO authusergroups (username, groupname) VALUES ('editorCoach', 'customers');
INSERT INTO authusergroups (username, groupname) VALUES ('mainEditor', 'administrators');
INSERT INTO authusergroups (username, groupname) VALUES ('pageManagerCoach', 'customers');
INSERT INTO authusergroups (username, groupname) VALUES ('admin', 'administrators');


--
-- TOC entry 1777 (class 0 OID 2673500)
-- Dependencies: 1483
-- Data for Name: authuserroles; Type: TABLE DATA; Schema: public; Owner: agile
--

INSERT INTO authuserroles (username, rolename) VALUES ('admin', 'admin');
INSERT INTO authuserroles (username, rolename) VALUES ('editorCoach', 'editor');
INSERT INTO authuserroles (username, rolename) VALUES ('editorCustomers', 'editor');
INSERT INTO authuserroles (username, rolename) VALUES ('mainEditor', 'editor');
INSERT INTO authuserroles (username, rolename) VALUES ('supervisorCoach', 'supervisor');
INSERT INTO authuserroles (username, rolename) VALUES ('supervisorCustomers', 'supervisor');
INSERT INTO authuserroles (username, rolename) VALUES ('pageManagerCoach', 'pageManager');
INSERT INTO authuserroles (username, rolename) VALUES ('pageManagerCustomers', 'pageManager');


--
-- TOC entry 1778 (class 0 OID 2673503)
-- Dependencies: 1484
-- Data for Name: authusers; Type: TABLE DATA; Schema: public; Owner: agile
--

INSERT INTO authusers (username, passwd, registrationdate, lastaccess, lastpasswordchange, active) VALUES ('supervisorCoach', 'supervisorCoach', '2008-09-25', '2009-01-30', NULL, 1);
INSERT INTO authusers (username, passwd, registrationdate, lastaccess, lastpasswordchange, active) VALUES ('mainEditor', 'mainEditor', '2008-09-25', '2009-01-30', NULL, 1);
INSERT INTO authusers (username, passwd, registrationdate, lastaccess, lastpasswordchange, active) VALUES ('pageManagerCoach', 'pageManagerCoach', '2008-09-25', '2009-01-30', NULL, 1);
INSERT INTO authusers (username, passwd, registrationdate, lastaccess, lastpasswordchange, active) VALUES ('supervisorCustomers', 'supervisorCustomers', '2008-09-25', '2009-01-30', NULL, 1);
INSERT INTO authusers (username, passwd, registrationdate, lastaccess, lastpasswordchange, active) VALUES ('pageManagerCustomers', 'pageManagerCustomers', '2008-09-25', '2009-01-30', NULL, 1);
INSERT INTO authusers (username, passwd, registrationdate, lastaccess, lastpasswordchange, active) VALUES ('editorCustomers', 'editorCustomers', '2008-09-25', '2009-07-02', NULL, 1);
INSERT INTO authusers (username, passwd, registrationdate, lastaccess, lastpasswordchange, active) VALUES ('editorCoach', 'editorCoach', '2008-09-25', '2009-07-02', NULL, 1);
INSERT INTO authusers (username, passwd, registrationdate, lastaccess, lastpasswordchange, active) VALUES ('admin', 'admin', '2008-09-25', '2009-12-16', NULL, 1);


--
-- TOC entry 1779 (class 0 OID 2673594)
-- Dependencies: 1485
-- Data for Name: authusershortcuts; Type: TABLE DATA; Schema: public; Owner: agile
--

INSERT INTO authusershortcuts (username, config) VALUES ('admin', '<shortcuts>
	<box pos="0"></box>
	<box pos="1">core.component.user.list</box>
	<box pos="2">jacms.content.new</box>
	<box pos="3">jacms.content.list</box>
	<box pos="4">core.portal.pageTree</box>
	<box pos="5">core.portal.showletType</box>
	<box pos="6">core.tools.setting</box>
	<box pos="7">core.tools.entities</box>
</shortcuts>');


-- Completed on 2011-01-02 14:30:49 CET

--
-- PostgreSQL database dump complete
--

