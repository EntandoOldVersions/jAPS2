<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<s:include value="/WEB-INF/apsadmin/jsp/common/template/defaultExtraResources.jsp" />

<script type="text/javascript"><!--
//--><![CDATA[//>
window.addEvent('domready', function(){
	$$("span.shortcut-toolbar").each(function(toolbar) {
		toolbar.inject(toolbar.getParent(), 'top');
	});
});
//<!]]>-->--</script>

<s:if test='#myClient == "advanced"'>
<s:set var="allowedShortcutsVar" value="allowedShortcuts" />
<s:if test="(null != #allowedShortcutsVar) && (#allowedShortcutsVar.size() > 0) && (null != position)">

<link rel="stylesheet" type="text/css" href="<wp:resourceURL />administration/mint/js/darkwing-LightFace-89eadac/Assets/LightFace.css" media="screen" />
<script type="text/javascript" src="<wp:resourceURL />administration/mint/js/darkwing-LightFace-89eadac/Source/LightFace.js"></script>
<script type="text/javascript" src="<wp:resourceURL />administration/mint/js/darkwing-LightFace-89eadac/Source/LightFace.Static.js"></script>

<script type="text/javascript"><!--
//--><![CDATA[//>	
<%-- Here today, in defaultExtraResources someday --%>
window.addEvent('domready', function(){

	var myModalSource = $("shortcut-configure-container"); 
	$("shortcut-configure-container").dispose();
	var myModalTitle = myModalSource.getElement("h2").get("html");
	myModalSource.getElement("h2").dispose();
	
	
	// Create instance
	var modalConfigure = new LightFace({
		title: myModalTitle,
		content: myModalSource.get("html"),
		draggable: true,
		buttons: [{ title: '<s:text name="label.close" />', event: function() { this.close(); } }]
	});

	// Open Sesame!
	modalConfigure.open();

	//Update Content
	//modal.load('This is different content....');
		

	<%-- Temporaneo per vedere se funziona --%>
	//console.log($$('span.shortcut-toolbar a[rel="shortcut-configure"]'));

});
//<!]]>-->--</script>
</s:if>
<%-- shortcuts //start --%>
<link rel="stylesheet" type="text/css" href="<wp:resourceURL />administration/mint/css/shortcuts-drag-drop.css" media="screen" />
<script type="text/javascript" src="<wp:resourceURL />/administration/mint/js/jAPS-shortcuts.js"></script>
<script type="text/javascript"><!--
//--><![CDATA[//>
window.addEvent('domready', function(){
	if (document.getElements("dl.maxiButton") != null) {
		new jAPSShortCuts({
			"ajaxSwapActionUrl": "<s:url action="swapMyShortcutAjax" namespace="/do/MyShortcut" />?strutsAction=2",
			"prefixId": "fagiano_shortCut_",
			"draggables": ".maxiButton",
			"droppables": ".maxiButton",
			"handler": "img.move",
			"css": {
				"dragWhileDraggin": "dragged",
				"dropOver": "dropping",
				"clone": "dragClone",
				"dragLoading": "dragLoading",
				"dropLoading": "dropLoading",
				"divMessage": "message message_error"
			},
			"cloneOffsets": {
				"top": -19 ,
				"left": -140
			},
			"params": {
				"start": "positionTarget",
				"end": "positionDest"
			},
			"labels": {
				"messageTitle": "<s:text name="message.title.ActionErrors" />",
				"genericError": "<s:text name="core.shortcut.message.error.generic" />",
				"timeoutError": "<s:text name="core.shortcut.message.error.timeout" />"
			},
			onCompleteSwap: function(drag,drop) {
				//event fired when swap is done successfully.
				var dragAnchors = drag.getElements(".shortcut-toolbar a");
				var dropAnchors = drop.getElements(".shortcut-toolbar a");
				var dragPos = drag.get("id").split(this.options.prefixId)[1];
				var dropPos = drop.get("id").split(this.options.prefixId)[1];
				for (var i = 0;i<dragAnchors.length;i++) {
					var a = dragAnchors[i];
					a.set("href",a.get("href").replace("position="+dropPos,"position="+dragPos));
					a.set("href",a.get("href").replace("positionTarget="+dropPos,"position="+dragPos));
					a.set("title",a.get("title").replace((dropPos.toInt()+1),(dragPos.toInt()+1)));
				}
				for (var i = 0; i < dropAnchors.length; i++) {
					var a = dropAnchors[i];
					a.set("href",a.get("href").replace("position="+dragPos,"position="+dropPos));
					a.set("href",a.get("href").replace("positionTarget="+dragPos,"position="+dropPos));
					a.set("title",a.get("title").replace((dragPos.toInt()+1),(dropPos.toInt()+1)));
				} 
			}
		});	
	}
});
//<!]]>-->--</script>
<%-- shortcuts //end --%>
</s:if>