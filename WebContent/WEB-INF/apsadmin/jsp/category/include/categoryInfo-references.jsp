<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>

<wpsa:hookPoint key="core.categoryReferences" objectName="hookPointElements_core_categoryReferences">
<s:iterator value="#hookPointElements_core_categoryReferences" var="hookPointElement">
	<wpsa:include value="%{#hookPointElement.filePath}"></wpsa:include>
</s:iterator>
</wpsa:hookPoint>