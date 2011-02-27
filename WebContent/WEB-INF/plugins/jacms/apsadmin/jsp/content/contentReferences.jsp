<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>

<s:set var="targetNS" value="%{'/do/jacms/Content'}" />
<h1><s:text name="jacms.menu.contentAdmin" /><s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" /></h1>

<div class="message message_error">
<h2><s:text name="message.title.ActionErrors" /></h2>
<p><s:text name="message.note.resolveReferences" />:</p>

<s:if test="references['jacmsContentManagerUtilizers']">
<h3><s:text name="message.title.referencedContents" /></h3>
<ul>
<s:iterator var="content" value="references['jacmsContentManagerUtilizers']" >
	<li><s:property value="#content.id" /> - <s:property value="#content.descr" /></li>
</s:iterator>
</ul>
</s:if>

<s:if test="references['PageManagerUtilizers']">
<h3><s:text name="message.title.referencedPages" /></h3>
<ul>
<s:iterator var="page" value="references['PageManagerUtilizers']" >
	<li><s:property value="#page.code" /> - <s:property value="#page.titles[currentLang.code]" /></li>
</s:iterator>
</ul>
</s:if>

<wpsa:hookPoint key="jacms.contentReferences" objectName="hookPointElements_jacms_contentReferences">
<s:iterator value="#hookPointElements_jacms_contentReferences" var="hookPointElement">
	<wpsa:include value="%{#hookPointElement.filePath}"></wpsa:include>
</s:iterator>
</wpsa:hookPoint>

</div>