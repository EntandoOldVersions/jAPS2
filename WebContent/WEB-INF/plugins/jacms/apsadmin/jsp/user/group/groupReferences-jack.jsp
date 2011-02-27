<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="references['jacmsContentManagerUtilizers']">
<h3><s:text name="message.title.referencedContents" /></h3>
<ul>
<s:iterator var="content" value="references['jacmsContentManagerUtilizers']">
	<li><s:property value="#content.id" /> - <s:property value="#content.descr" /></li>
</s:iterator>
</ul>
</s:if>

<s:if test="references['jacmsResourceManagerUtilizers']">
<h3><s:text name="message.title.referencedResources" /></h3>
<ul>
<s:iterator var="resource" value="references['jacmsResourceManagerUtilizers']">
	<li><s:property value="#resource.id" /> - <s:property value="#resource.descr" /></li>
</s:iterator>
</ul>
</s:if>