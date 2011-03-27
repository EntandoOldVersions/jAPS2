<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="c" uri="c.tld" %>
<%@ taglib prefix="jacms" uri="/WEB-INF/plugins/jacms/apsadmin/tld/jacms-apsadmin-core.tld" %>

<h3><s:text name="title.publishedContent" /></h3>

<s:set var="publishedContents" value="getPublishedContents(selectedNode)" />

<s:if test="!#publishedContents.empty">
<table class="generic" id="contentListTable" summary="<s:text name="note.content.publishedContent.summary" />">
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
	<s:iterator var="currentContent" value="#publishedContents">
	<tr>
		<s:set var="canEditCurrentContent" value="%{false}" />
		<c:set var="currentContentGroup"><s:property value="#currentContent.mainGroup" escape="false"/></c:set>
		<wp:ifauthorized groupName="${currentContentGroup}" permission="editContents"><s:set var="canEditCurrentContent" value="%{true}" /></wp:ifauthorized>
		<td>
			<s:if test="#canEditCurrentContent">
				<a href="<s:url action="edit" namespace="/do/jacms/Content"><s:param name="contentId" value="#currentContent.id" /></s:url>" title="<s:text name="label.edit" />:&#32;<s:property value="#currentContent.descr"/>"><s:property value="#currentContent.descr"/></a>
			</s:if>
			<s:else>
				<s:property value="#currentContent.descr"/>
			</s:else>
		</td>
		<td><span class="monospace"><s:property value="#currentContent.id"/></span></td> 
		<td><s:property value="#currentContent.typeDescr"/></td>
		<jacms:content contentId="%{#currentContent.id}" record="true" property="modify" var="modifyVar"/>
		<td class="icon"><span class="monospace"><s:date name="#modifyVar" format="dd/MM/yyyy" /></span></td>
	</tr>
	</s:iterator>
</table>
</s:if>
<s:else>
<p><s:text name="note.publishedContent.empty" /></p>
</s:else>

<h3><s:text name="title.referencedContent" /></h3>
<s:set var="referencingContents" value="getReferencingContents(selectedNode)" />

<s:if test="!#referencingContents.empty">

<table class="generic" id="contentListTable" summary="<s:text name="note.content.referencedContent.summary" />">
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
	<s:iterator var="currentContentRecordVar" value="#referencingContents">
		<tr>
			<jacms:content contentId="%{#currentContentRecordVar.id}" var="currentContentVar" />
			<s:set var="canEditCurrentContent" value="%{false}" />
			<c:set var="currentContentGroup"><s:property value="#currentContentVar.mainGroup" escape="false"/></c:set>
			<td>
				<wp:ifauthorized groupName="${currentContentGroup}" permission="editContents"><s:set var="canEditCurrentContent" value="%{true}" /></wp:ifauthorized>
				<s:if test="#canEditCurrentContent">
					<a href="<s:url action="edit" namespace="/do/jacms/Content"><s:param name="contentId" value="#currentContentVar.id" /></s:url>" title="<s:text name="label.edit" />:&#32;<s:property value="#currentContentVar.descr"/>"><s:property value="#currentContentVar.descr"/></a>
				</s:if>
				<s:else>
					<s:property value="#currentContentVar.descr"/>
				</s:else>
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
</s:if>
<s:else>
<p><s:text name="note.referencedContent.empty" /></p>
</s:else>