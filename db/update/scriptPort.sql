INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('YES', 'en', 'Yes');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('YES', 'it', 'Si');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('NO', 'en', 'No');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('NO', 'it', 'No');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('BOTH', 'en', 'Both');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('BOTH', 'it', 'Entrambi');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('IGNORE', 'en', 'Ignore this field');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('IGNORE', 'it', 'Ignora questo campo');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('CATEGORY', 'en', 'Category');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('CATEGORY', 'it', 'Categoria');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('ERRORS', 'en', 'Errors');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('ERRORS', 'it', 'Errori');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('LIST_VIEWER_EMPTY', 'en', 'No results found. Check your search filters.');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('LIST_VIEWER_EMPTY', 'it', 'Nessun risultato trovato. Controlla i tuoi filtri di ricerca.');

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

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('END', 'en', 'To');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('END', 'it', 'Fino a');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('START', 'en', 'From');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('START', 'it', 'Da');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jacms_LIST_VIEWER_INVALID_FORMAT', 'en', 'has a format that is not valid.');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jacms_LIST_VIEWER_INVALID_FORMAT', 'it', 'ha un formato che risulta non valido.');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jacms_LIST_VIEWER_INVALID_RANGE', 'en', 'has a value not consistent. Check and start a new search.');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jacms_LIST_VIEWER_INVALID_RANGE', 'it', 'ha un valore non coerente. Ricontrolla ed effettua una nuova ricerca.');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jacms_LIST_VIEWER_FIELD', 'en', 'The field');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jacms_LIST_VIEWER_FIELD', 'it', 'Il campo');

