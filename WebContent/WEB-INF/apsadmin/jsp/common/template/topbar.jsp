<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="c.tld" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="wpsa" uri="apsadmin-core.tld" %>

<wpsa:userShortcutsConfig var="userConfigVar" />

<ul class="menu horizontal">

<s:iterator var="userShortcutCode" value="#userConfigVar.config" status="rowstatus">

<wpsa:shortcut key="%{#userShortcutCode}" var="userShortcutVar" />

<li class="topbar-menusection-<s:property value="#userShortcutVar.menuSectionCode" />">

<%-- <dl class="maxiButton" id="fagiano_shortCut_<s:property value="#rowstatus.index"/>">  --%>

<s:if test="null != #userShortcutVar">

<s:set var="userShortcutSectionShortDescr"><s:if test="null != #userShortcutVar.menuSection.descriptionKey" ><s:text name="%{#userShortcutVar.menuSection.descriptionKey}" /></s:if><s:else><s:property value="%{#userShortcutVar.menuSection.description}" /></s:else></s:set>
<s:set var="userShortcutSectionLongDescr"><s:if test="null != #userShortcutVar.menuSection.longDescriptionKey" ><s:text name="%{#userShortcutVar.menuSection.longDescriptionKey}" /></s:if><s:else><s:property value="%{#userShortcutVar.menuSection.longDescription}" /></s:else></s:set>

<s:set var="userShortcutShortDescr"><s:if test="null != #userShortcutVar.descriptionKey" ><s:text name="%{#userShortcutVar.descriptionKey}" /></s:if><s:else><s:property value="%{#userShortcutVar.description}" /></s:else></s:set>
<s:set var="userShortcutLongDescr"><s:if test="null != #userShortcutVar.longDescriptionKey" ><s:text name="%{#userShortcutVar.longDescriptionKey}" /></s:if><s:else><s:property value="%{#userShortcutVar.longDescription}" /></s:else></s:set>

		<%-- SCRIVERE TAG PER AGGIUNGERE MAPPA PARAMETRI ALLA URL --%>
		<a href="<s:url action="%{#userShortcutVar.actionName}" namespace="%{#userShortcutVar.namespace}"><wpsa:paramMap map="#userShortcutVar.parameters" /></s:url>" 
				lang="en" title="<s:property value="%{#userShortcutLongDescr}" escapeHtml="true" />"><img src="<wp:resourceURL/>administration/common/img/icons/16x16/topbar-<s:property value="#userShortcutVar.menuSectionCode" />.png" width="16" height="16" alt="<s:property value="%{#userShortcutSectionShortDescr}" /> - " /><span class="toggle-ellipsis"><s:property value="%{#userShortcutShortDescr}" /></span></a>

</s:if>
<s:else>
	<a href="<s:url action="main" namespace="/do" />" class="noborder outlineNone" title="<s:text name="note.goToMainPage" />"><img src="<wp:resourceURL/>administration/common/img/icons/16x16/topbar-<s:property value="#userShortcutVar.menuSectionCode" />.png" width="16" height="16" alt=" " /><span class="toggle-ellipsis">&nbsp;</span></a></a>
</s:else>

</li>
</s:iterator>
</ul>