<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>

** CATEGORY DETAILS **

<div id="main">

<br /><br />

<dl class="table-display">
	<dt><s:text name="name.categoryCode" /></dt>
		<dd><s:property value="categoryCode" /></dd>
	<dt><s:text name="name.categoryTitle" /></dt>
		<dd>
<s:iterator value="langs" status="categoryInfo_rowStatus" var="lang">
		<s:if test="#categoryInfo_rowStatus.index != 0">, </s:if><span class="monospace">(<abbr title="<s:property value="descr" />"><s:property value="code" /></abbr>)</span> <s:property value="titles[#lang.code]" />
</s:iterator>
		</dd>
</dl>

<br /><br />

<wpsa:hookPoint key="core.categoryDetails" objectName="hookPointElements_core_categoryDetails">
<s:iterator value="#hookPointElements_core_categoryDetails" var="hookPointElement">
	<wpsa:include value="%{#hookPointElement.filePath}"></wpsa:include>
</s:iterator>
</wpsa:hookPoint>

<s:include value="/WEB-INF/apsadmin/jsp/category/include/categoryInfo-references.jsp" />

</div>