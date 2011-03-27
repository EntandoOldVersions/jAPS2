<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>

** GROUP DETAILS **

<div id="main">

<br /><br />

<dl class="table-display">
	<dt><s:text name="name.groupCode" /></dt>
		<dd><s:property value="name" /></dd>
	<dt><s:text name="name.groupDescription" /></dt>
		<dd><s:property value="description" /></dd>
</dl>

<br /><br />

<wpsa:hookPoint key="core.groupDetails" objectName="hookPointElements_core_groupDetails">
<s:iterator value="#hookPointElements_core_groupDetails" var="hookPointElement">
	<wpsa:include value="%{#hookPointElement.filePath}"></wpsa:include>
</s:iterator>
</wpsa:hookPoint>

<s:include value="/WEB-INF/apsadmin/jsp/user/group/include/groupInfo-references.jsp" />

</div>