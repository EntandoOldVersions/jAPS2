<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="references['jacmsContentManagerUtilizers']">
<p class="important"><s:text name="message.title.referencedContents" />:</p>
<ul>
<s:iterator var="content" value="references['jacmsContentManagerUtilizers']">
	<li><s:property value="#content.id" /> - <s:property value="#content.descr" /></li>
</s:iterator>
</ul>
</s:if>

<s:if test="references['jacmsResourceManagerUtilizers']">
<p class="important"><s:text name="message.title.referencedResources" />:</p>
<ul>
<s:iterator var="resource" value="references['jacmsResourceManagerUtilizers']">
	<li><s:property value="#resource.id" /> - <s:property value="#resource.descr" /></li>
</s:iterator>
</ul>
</s:if>