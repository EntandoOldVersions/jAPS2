<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="c.tld" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="wpsa" uri="apsadmin-core.tld" %>

<%-- <c:out value="${sessionScope.myShortcuts_sessionParam}" /> --%>

<s:set var="userConfigVar" value="userConfig" />

<ul class="menu horizontal">

<s:iterator var="userShortcutCode" value="#userConfigVar" status="rowstatus">

<s:set var="userShortcut" value="%{getShortcut(#userShortcutCode)}"></s:set>

<li class="topbar-menusection-<s:property value="#userShortcut.menuSectionCode" />">

<%-- <dl class="maxiButton" id="fagiano_shortCut_<s:property value="#rowstatus.index"/>">  --%>

<s:if test="null != #userShortcut">

<s:set var="userShortcutSectionShortDescr"><s:if test="null != #userShortcut.menuSection.descriptionKey" ><s:text name="%{#userShortcut.menuSection.descriptionKey}" /></s:if><s:else><s:property value="%{#userShortcut.menuSection.description}" /></s:else></s:set>
<s:set var="userShortcutSectionLongDescr"><s:if test="null != #userShortcut.menuSection.longDescriptionKey" ><s:text name="%{#userShortcut.menuSection.longDescriptionKey}" /></s:if><s:else><s:property value="%{#userShortcut.menuSection.longDescription}" /></s:else></s:set>

<s:set var="userShortcutShortDescr"><s:if test="null != #userShortcut.descriptionKey" ><s:text name="%{#userShortcut.descriptionKey}" /></s:if><s:else><s:property value="%{#userShortcut.description}" /></s:else></s:set>
<s:set var="userShortcutLongDescr"><s:if test="null != #userShortcut.longDescriptionKey" ><s:text name="%{#userShortcut.longDescriptionKey}" /></s:if><s:else><s:property value="%{#userShortcut.longDescription}" /></s:else></s:set>

		<%-- SCRIVERE TAG PER AGGIUNGERE MAPPA PARAMETRI ALLA URL --%>
		<a href="<s:url action="%{#userShortcut.actionName}" namespace="%{#userShortcut.namespace}"><wpsa:paramMap map="#userShortcut.parameters" /></s:url>" 
				lang="en" title="<s:property value="%{#userShortcutLongDescr}" escapeHtml="true" />"><img src="<wp:resourceURL/>administration/common/img/icons/16x16/topbar-<s:property value="#userShortcut.menuSectionCode" />.png" width="16" height="16" alt="<s:property value="%{#userShortcutSectionShortDescr}" /> - " /><span class="toggle-ellipsis"><s:property value="%{#userShortcutShortDescr}" /></span></a>

</s:if>
<s:else>
	<a href="<s:url action="main" namespace="/do" />" class="noborder outlineNone" title="<s:text name="note.goToMainPage" />"><img src="<wp:resourceURL/>administration/common/img/icons/16x16/topbar-<s:property value="#userShortcut.menuSectionCode" />.png" width="16" height="16" alt=" " /><span class="toggle-ellipsis">&nbsp;</span></a></a>
</s:else>

</li>
</s:iterator>
</ul>