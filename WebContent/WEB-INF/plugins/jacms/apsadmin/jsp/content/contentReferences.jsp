<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="c.tld" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="wpsa" uri="apsadmin-core.tld" %>

<s:set var="targetNS" value="%{'/do/jacms/Content'}" />
<h1><s:text name="jacms.menu.contentAdmin" /><s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" /></h1>

<s:form>

<div id="main">

<div class="message message_error">
<h2><s:text name="message.title.ActionErrors" /></h2>
<p><s:text name="message.note.resolveReferences" />:</p>

</div>

<s:if test="references['jacmsContentManagerUtilizers']">
<h3><s:text name="message.title.referencedContents" /></h3>
<s:set var="referencingContentsId" value="references['jacmsContentManagerUtilizers']" />
<s:include value="/WEB-INF/plugins/jacms/apsadmin/jsp/portal/include/referencingContents.jsp" />
</s:if>

<s:if test="references['PageManagerUtilizers']">
<h3><s:text name="message.title.referencedPages" /></h3>

<wpsa:subset source="references['PageManagerUtilizers']" count="10" objectName="pageReferences" advanced="true" offset="5" pagerId="pageManagerReferences">
<s:set name="group" value="#pageReferences" />

<div class="pager">
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pagerInfo.jsp" />
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pager_formBlock.jsp" />
</div>

<table class="generic" id="pageListTable" summary="<s:text name="note.group.referencedPages.summary" />">
<caption><span><s:text name="title.pageList" /></span></caption>
	<tr>
		<th>** PATH **</th>
		<th>** GROUP **</th>
	</tr>
	<s:iterator var="currentPageVar" >
		<s:set var="canEditCurrentPage" value="%{false}" />
		<c:set var="currentPageGroup"><s:property value="#currentPageVar.group" escape="false"/></c:set>
		<wp:ifauthorized groupName="${currentPageGroup}" permission="managePages"><s:set var="canEditCurrentPage" value="%{true}" /></wp:ifauthorized>
		<tr>
			<td>
				<s:property value="%{#currentPageVar.getFullTitle(currentLang.code)}" />
				<s:if test="#canEditCurrentPage">
					<a href="<s:url namespace="/do/Page" action="viewTree"><s:param name="selectedNode" value="#currentPageVar.code" /></s:url>"><img src="<wp:resourceURL />administration/common/img/icons/node-leaf.png" alt="<s:text name="note.goToSomewhere" />: <s:property value="%{#currentPageVar.getFullTitle(currentLang.code)}" />" title="<s:text name="note.goToSomewhere" />: <s:property value="%{#currentPageVar.getFullTitle(currentLang.code)}" />" /></a>
					<a href="<s:url namespace="/do/Page" action="configure"><s:param name="pageCode" value="#currentPageVar.code" /></s:url>"><img src="<wp:resourceURL />administration/common/img/icons/page-configure.png" alt="<s:text name="title.configPage" />: <s:property value="%{#currentPageVar.getFullTitle(currentLang.code)}" />" title="<s:text name="title.configPage" />: <s:property value="%{#currentPageVar.getFullTitle(currentLang.code)}" />" /></a>
				</s:if>
				<s:else></s:else>
			</td>
			<td>
				<s:property value="#currentPageVar.group" />
			</td>
		</tr>
	</s:iterator>
</table>

<div class="pager">
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pager_formBlock.jsp" />
</div>

</wpsa:subset>

</s:if>

<wpsa:hookPoint key="jacms.contentReferences" objectName="hookPointElements_jacms_contentReferences">
<s:iterator value="#hookPointElements_jacms_contentReferences" var="hookPointElement">
	<wpsa:include value="%{#hookPointElement.filePath}"></wpsa:include>
</s:iterator>
</wpsa:hookPoint>

</div>

</s:form>