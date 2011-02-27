<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>

<s:set var="targetNS" value="%{'/do/Group'}" />
<h1><s:text name="title.groupManagement" /><s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" /></h1>

<div id="main">

<div class="message message_error">
<h2><s:text name="message.title.ActionErrors" /></h2>
<p><s:text name="message.note.resolveReferences" />:</p>

<s:if test="references['PageManagerUtilizers']">
<h3><s:text name="message.title.referencedPages" /></h3>
<ul>
<s:iterator value="references['PageManagerUtilizers']" id="page">
	<li><s:property value="#page.code" /> - <s:property value="#page.titles[currentLang.code]" /></li>
</s:iterator>
</ul>
</s:if>

<s:if test="references['UserManagerUtilizers']">
<h3><s:text name="message.title.referencedUsers" /></h3>
<ul>
<s:iterator value="references['UserManagerUtilizers']" id="user">
	<li><s:property value="#user.username" /></li>
</s:iterator>
</ul>
</s:if>

<wpsa:hookPoint key="core.groupReferences" objectName="hookPointElements_core_groupReferences">
<s:iterator value="#hookPointElements_core_groupReferences" var="hookPointElement">
	<wpsa:include value="%{#hookPointElement.filePath}"></wpsa:include>
</s:iterator>
</wpsa:hookPoint>

</div>

</div>