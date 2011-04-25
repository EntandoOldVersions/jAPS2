<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="c" uri="c.tld" %>
<%@ taglib prefix="jacms" uri="/WEB-INF/plugins/jacms/apsadmin/tld/jacms-apsadmin-core.tld" %>

<tr>
	<jacms:content contentId="%{#currentContentRecordVar.id}" var="currentContentVar" />
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