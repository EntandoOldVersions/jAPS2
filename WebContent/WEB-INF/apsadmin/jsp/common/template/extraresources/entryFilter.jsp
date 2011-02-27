<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<s:include value="/WEB-INF/apsadmin/jsp/common/template/defaultExtraResources.jsp" />

<!-- per filtro Attributo Date -->
<script type="text/javascript" src="<wp:resourceURL />administration/js/calendar_wiz.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<wp:resourceURL />administration/css/calendar.css" />
<!--[if gte IE 7]>
	<link rel="stylesheet" type="text/css" media="screen" href="<wp:resourceURL />administration/css/calendar_ie.css" />
<![endif]-->

<script type="text/javascript">
<!--//--><![CDATA[//><!--

//per filtro Attributo Date
window.addEvent('domready', function() {

	$$('input[id$=_cal]').each(function(item){
	        var obj = {};
	        obj[item.getProperty('id')] = "d/m/Y";
	        var myCal = new Calendar(obj);
	        myCal.options.navigation = 1; 
			myCal.options.months = ['<s:text name="calendar.month.gen" />','<s:text name="calendar.month.feb" />','<s:text name="calendar.month.mar" />','<s:text name="calendar.month.apr" />','<s:text name="calendar.month.may" />','<s:text name="calendar.month.jun" />','<s:text name="calendar.month.jul" />','<s:text name="calendar.month.aug" />','<s:text name="calendar.month.sep" />','<s:text name="calendar.month.oct" />','<s:text name="calendar.month.nov" />','<s:text name="calendar.month.dec" />'];
			myCal.options.days = ['<s:text name="calendar.week.sun" />','<s:text name="calendar.week.mon" />','<s:text name="calendar.week.tue" />','<s:text name="calendar.week.wen" />','<s:text name="calendar.week.thu" />','<s:text name="calendar.week.fri" />','<s:text name="calendar.week.sat" />'];
			myCal.options.prevText = "<s:text name="calendar.label.prevText" />";	//Mese precedente 
			myCal.options.nextText = "<s:text name="calendar.label.nextText" />";	//Mese successivo
			myCal.options.introText = "<s:text name="calendar.label.introText" />";	//Benvenuto nel calendario
	});
});	

//fine filtro Attributo Date

//--><!]]></script>