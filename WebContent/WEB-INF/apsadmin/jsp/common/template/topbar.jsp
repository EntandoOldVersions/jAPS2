<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="c.tld" %>
<%@ taglib prefix="wpsa" uri="apsadmin-core.tld" %>

<c:out value="${sessionScope.myShortcuts_sessionParam}" />

<s:set var="userConfigVar" value="userConfig" />

<ul>

<s:iterator var="userShortcutCode" value="#userConfigVar" status="rowstatus">

<li>

<s:set var="userShortcut" value="%{getShortcut(#userShortcutCode)}"></s:set>

<%-- <dl class="maxiButton" id="fagiano_shortCut_<s:property value="#rowstatus.index"/>">  --%>

<s:if test="null != #userShortcut">

<s:set var="userShortcutSectionShortDescr"><s:if test="null != #userShortcut.menuSection.descriptionKey" ><s:text name="%{#userShortcut.menuSection.descriptionKey}" /></s:if><s:else><s:property value="%{#userShortcut.menuSection.description}" /></s:else></s:set>
<s:set var="userShortcutSectionLongDescr"><s:if test="null != #userShortcut.menuSection.longDescriptionKey" ><s:text name="%{#userShortcut.menuSection.longDescriptionKey}" /></s:if><s:else><s:property value="%{#userShortcut.menuSection.longDescription}" /></s:else></s:set>

<s:set var="userShortcutShortDescr"><s:if test="null != #userShortcut.descriptionKey" ><s:text name="%{#userShortcut.descriptionKey}" /></s:if><s:else><s:property value="%{#userShortcut.description}" /></s:else></s:set>
<s:set var="userShortcutLongDescr"><s:if test="null != #userShortcut.longDescriptionKey" ><s:text name="%{#userShortcut.longDescriptionKey}" /></s:if><s:else><s:property value="%{#userShortcut.longDescription}" /></s:else></s:set>

<s:property value="#userShortcut.menuSectionCode" />##

		<%-- SCRIVERE TAG PER AGGIUNGERE MAPPA PARAMETRI ALLA URL --%>
		<a href="<s:url action="%{#userShortcut.actionName}" namespace="%{#userShortcut.namespace}"><wpsa:paramMap map="#userShortcut.parameters" /></s:url>" 
				lang="en" title="<s:property value="%{#userShortcutShortDescr}" />">
			<s:property value="%{#userShortcutSectionShortDescr}" />§§<s:property value="%{#userShortcutShortDescr}" />
		</a>##
<s:property value="%{#userShortcutLongDescr}" />

</s:if>
<s:else>
empty
</s:else>

</li>
</s:iterator>
</ul>