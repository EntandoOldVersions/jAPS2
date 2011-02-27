<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>

<s:set var="targetNS" value="%{'/do/jacms/Resource'}" />
<s:set var="targetParamName" value="%{'resourceTypeCode'}" />
<s:set var="targetParamValue" value="resourceTypeCode" />	
<h1><s:property value="%{getText('title.resourceManagement.' + resourceTypeCode)}" /><s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" /></h1>

<div id="main">

<div class="message message_error">
<h2><s:text name="message.title.ActionErrors" /></h2>
<p><s:text name="message.note.resolveReferences" />:</p>
<s:if test="references['jacmsContentManagerUtilizers']">
<h3><s:text name="message.title.referencedContents" /></h3>
<ul>
<s:iterator value="references['jacmsContentManagerUtilizers']" id="content">
	<li><s:property value="#content.id" /> - <s:property value="#content.descr" /></li>
</s:iterator>
</ul>
</s:if>
</div>
</div>