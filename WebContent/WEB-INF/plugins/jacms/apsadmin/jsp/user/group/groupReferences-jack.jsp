<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="c" uri="c.tld" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wpsa" uri="apsadmin-core.tld" %>
<%@ taglib prefix="jacmswpsa" uri="/WEB-INF/plugins/jacms/apsadmin/tld/jacms-apsadmin-core.tld" %>

<s:if test="null != references['jacmsContentManagerUtilizers']">
<wpsa:subset source="references['jacmsContentManagerUtilizers']" count="10" objectName="contentReferences" advanced="true" offset="5">
<s:set name="group" value="#contentReferences" />

<div class="pager">
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pagerInfo.jsp" />
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pager_formBlock.jsp" />
</div>

<table class="generic" id="contentListTable" summary="<s:text name="note.group.referencedContents.summary" />">
<caption><span><s:text name="title.contentList" /></span></caption>
	<tr>
		<th>
			<s:text name="label.description" />
		</th>
		<th>
			<s:text name="label.code" />
		</th>
		<th>
			<s:text name="label.type" />
		</th>
		<th>
			<s:text name="label.lastEdit" />
		</th>
	</tr>
	<s:iterator var="currentContentRecordVar" >
		<tr>
			<jacmswpsa:content contentId="%{#currentContentRecordVar.id}" var="currentContentVar" />
			<s:set var="canEditCurrentContent" value="%{false}" />
			<c:set var="currentContentGroup"><s:property value="#currentContentVar.mainGroup" escape="false"/></c:set>
			<td>
				<wp:ifauthorized groupName="${currentContentGroup}" permission="editContents"><s:set var="canEditCurrentContent" value="%{true}" /></wp:ifauthorized>
				<s:if test="#canEditCurrentContent">
					<a href="<s:url action="edit" namespace="/do/jacms/Content"><s:param name="contentId" value="#currentContentVar.id" /></s:url>" title="<s:text name="label.edit" />:&#32;<s:property value="#currentContentVar.descr"/>"><s:property value="#currentContentVar.descr"/></a>
				</s:if>
				<s:else><s:property value="#currentContentVar.descr"/></s:else>
			</td>
			<td>
				<span class="monospace"><s:property value="#currentContentVar.id"/></span>
			</td>
			<td>
				<s:property value="#currentContentVar.typeDescr"/>
			</td>
			<td class="icon">
				<span class="monospace"><s:date name="#currentContentRecordVar.modify" format="dd/MM/yyyy" /></span>
			</td>
		</tr>
	</s:iterator>
</table>

<div class="pager">
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pager_formBlock.jsp" />
</div>

</wpsa:subset>
</s:if>
<s:else>
<p><s:text name="note.group.referencedContents.empty" /></p>
</s:else>

<s:if test="null != references['jacmsResourceManagerUtilizers']">
<wpsa:subset source="references['jacmsResourceManagerUtilizers']" count="10" objectName="resourceReferences" advanced="true" offset="5">
<s:set name="group" value="#resourceReferences" />

<div class="pager">
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pagerInfo.jsp" />
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pager_formBlock.jsp" />
</div>

<table class="generic" id="contentListTable" summary="<s:text name="note.group.referencedResources.summary" />">
<caption><span><s:text name="title.resourceList" /></span></caption>
	<tr>
		<th>
			<s:text name="label.description" />
		</th>
		<th>
			<s:text name="label.code" />
		</th>
		<th>
			<s:text name="label.type" />
		</th>
	</tr>
	<s:iterator var="currentResourceVar" >
		<tr>
			<s:set var="canEditCurrentResource" value="%{false}" />
			<c:set var="currentResourceGroup"><s:property value="#currentResourceVar.mainGroup" escape="false"/></c:set>
			<td>
				<wp:ifauthorized groupName="${currentResourceGroup}" permission="manageResources"><s:set var="canEditCurrentResource" value="%{true}" /></wp:ifauthorized>
				<s:if test="#canEditCurrentResource">
					<a href="<s:url action="edit" namespace="/do/jacms/Resource"><s:param name="resourceId" value="#currentResourceVar.id" /></s:url>" title="<s:text name="label.edit" />:&#32;<s:property value="#currentResourceVar.descr"/>"><s:property value="#currentResourceVar.descr"/></a>
				</s:if>
				<s:else><s:property value="#currentResourceVar.descr"/></s:else>
			</td>
			<td>
				<span class="monospace"><s:property value="#currentResourceVar.id"/></span>
			</td>
			<td>
				<s:property value="#currentResourceVar.type"/>
			</td>
		</tr>
	</s:iterator>
</table>

<div class="pager">
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pager_formBlock.jsp" />
</div>

</wpsa:subset>
</s:if>
<s:else>
<p><s:text name="note.group.referencedResources.empty" /></p>
</s:else>