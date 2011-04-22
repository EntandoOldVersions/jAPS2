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

<s:if test="#myClient == 'advanced'">
<%-- detail function --%>
	<link rel="stylesheet" type="text/css" href="<wp:resourceURL />administration/mint/js/darkwing-LightFace-89eadac/Assets/LightFace.css" media="screen" />
	<script type="text/javascript" src="<wp:resourceURL />administration/mint/js/darkwing-LightFace-89eadac/Source/LightFace.js"></script>
	<script type="text/javascript" src="<wp:resourceURL />administration/mint/js/darkwing-LightFace-89eadac/Source/LightFace.Static.js"></script>

	<link rel="stylesheet" href="<wp:resourceURL />administration/mint/js/codemirror/codemirror.css">
	<link rel="stylesheet" href="<wp:resourceURL />administration/mint/js/codemirror/mode/xml/xml.css">
	<script src="<wp:resourceURL />administration/mint/js/codemirror/codemirror-compressed.js"></script>
	
	<script type="text/javascript" src="<wp:resourceURL />administration/mint/js/downloadify/js/swfobject.js"></script>
	<script type="text/javascript" src="<wp:resourceURL />administration/mint/js/downloadify/js/downloadify.min.js"></script>
	
	<style>
		.CodeMirror {
			max-width: 900px;
		}
	</style>
	
	<script type="text/javascript">
	var myModalLightFace = null;
window.addEvent('domready', function(){
//domready
<%
//TODO fix the height of the object with chrome, make i18n labels for "Close" and "Download as VM File"
%>
	var downloadTitleString = "Download as VM file";
	var closeTitleString = "Close";
	
	var details_shortcut = document.id("jacms-content-model-detail-shortcut");

	/////////////
	var titleString = document.getElement('label[for="newModel_contentShape"]').get("text")+ " " 
	+document.id("newModel_id").get("value")+ " " +
	document.id("newModel_contentType").getElement('option[selected="selected"]').get("text")+ " " +
	document.id('newModel_description').get("value")+ " ";

	var getVMfilename = function(){
		return document.id("newModel_contentType").getElement('option[selected="selected"]').get("value")
		+"-"+ document.id("newModel_description").get("value").replace(/\s{1,}/g,"-").replace(/-{1,}/g,"-")
		+"-"+ document.id("newModel_id").get("value")+".vm";
	};

	myModalLightFace = new LightFace({
		title: titleString,
		content: "",
		draggable: true,
		buttons: [
			{ title: downloadTitleString },
			{
				title: 'Close', 
				event: function() { 
					this.close(); 
				} 
			}
		]
	});

	//var heightButton = 22;
	var heightButton = myModalLightFace.buttons[closeTitleString].getDimensions().y;
	var widthButton = 36;
	var downloadButton = myModalLightFace.buttons[downloadTitleString].getParent();

	downloadButton.set("title",downloadTitleString);
	
	downloadButton.setStyles({
		width: widthButton+"px",
		height: heightButton+"px",
		"background-image": 'url("<wp:resourceURL />administration/common/img/icons/go-down.png")',
		"background-repeat": 'no-repeat',
		"background-position": 'center center'
	});

	downloadButton.downloadify({
		filename: getVMfilename,
		data: function(){ 
		  return document.id("newModel_contentShape").get("value");
		},
		onComplete: function(){ 
			myModalLightFace.close();
		},
		onCancel: function(){ 
			//alert('You have cancelled the saving of this file.');
		},
		onError: function(){ 
			//alert('Error!  Damn!'); 
		},
		transparent: true,
		swf: '<wp:resourceURL />administration/mint/js/downloadify/media/downloadify.swf',
		downloadImage: '<wp:resourceURL />administration/mint/img/downloadify-download-sprite.png',
		width: widthButton,
		height: heightButton,
		append: false
	});

	downloadButton.getElements("object").set("title",downloadTitleString);
	downloadButton.getElements("object").setStyles({
		"cursor": "pointer",
		"margin": 0,
		"padding": 0
	});
	
	var myCodeMirrorElement = new Element("div").inject(myModalLightFace.contentBox.getElement(".lightfaceMessageBox"));
	var myCodeMirrorOptions = {
		mode: "xml",
		value: document.id("newModel_contentShape").get("value"),
		readOnly: true,
		lineNumbers: true,
		gutter: true
	};
	
	var myCodeMirror = CodeMirror(myCodeMirrorElement,myCodeMirrorOptions);
	
	details_shortcut.addEvent("click", function(ev){
		ev.preventDefault();
		myCodeMirror.setValue(document.id("newModel_contentShape").get("value"));
		myModalLightFace.open();
	});

//domready
});
	</script>
</s:if>
