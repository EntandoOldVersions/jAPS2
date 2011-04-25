INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('DATE_FROM', 'en', 'From');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('DATE_FROM', 'it', 'Da');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('DATE_TO', 'en', 'To');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('DATE_TO', 'it', 'A');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('TEXT', 'en', 'Text');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('TEXT', 'it', 'Testo');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('ALL', 'en', 'All');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('ALL', 'it', 'Tutte');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('NUMBER_FROM', 'en', 'From');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('NUMBER_FROM', 'it', 'Da');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('NUMBER_TO', 'en', 'To');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('NUMBER_TO', 'it', 'A');

UPDATE showletcatalog SET parameters='<config>
	<parameter name="contentType">Content Type (mandatory)</parameter>
	<parameter name="modelId">Content Model</parameter>
	<parameter name="userFilters">Front-End user filter options</parameter>
	<parameter name="category">Content Category **deprecated**</parameter>
	<parameter name="categories">Content Category codes (comma separeted)</parameter>
	<parameter name="maxElemForItem">Contents for each page</parameter>
	<parameter name="filters" />
	<parameter name="title_{lang}">Showlet Title in lang {lang}</parameter>
	<parameter name="pageLink">The code of the Page to link</parameter>
	<parameter name="linkDescr_{lang}">Link description in lang {lang}</parameter>
	<action name="listViewerConfig"/>
</config>' WHERE code='content_viewer_list';


	
