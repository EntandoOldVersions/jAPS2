<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<s:include value="/WEB-INF/apsadmin/jsp/common/template/defaultExtraResources.jsp" />

<link type="text/css" rel="stylesheet" href="<wp:resourceURL />administration/common/css/MooContentAssist_themes.css" />
 
<script type="text/javascript" src="<wp:resourceURL />administration/common/js/mootools-xml.js"></script> 
<script type="text/javascript" src="<wp:resourceURL />administration/common/js/MooContentAssist.js"></script>
<script type="text/javascript">
<!--//--><![CDATA[//><!--

/************ client script ************/
window.addEvent("domready",function(){ 
	var a = new MooContentAssist("newModel_contentShape",{
		css: {
		"activeItem": "theme_japs_activeItem",
		"completedItem": "theme_japs_completedItem",
		"assistList": "theme_japs_assistList",
		"occurence": "theme_japs_occurence",
		"completedText": "theme_japs_completedText",
		"assistWindow": "theme_japs_assistWindow",
		"messages": "theme_japs_messages"
		},
		words: "<s:include value="/WEB-INF/plugins/jacms/apsadmin/jsp/common/template/extraresources/inc/attributeType_xml2js_Model.jsp" />",
		messages: { 
			nothingFound: "<s:text name="note.contentAssist.nothingFound" />"	
		}
	});
});
//--><!]]></script>