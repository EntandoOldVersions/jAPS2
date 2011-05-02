<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="jacmswpsa" uri="/WEB-INF/plugins/jacms/apsadmin/tld/jacms-apsadmin-core.tld" %>

<tr>
	<jacmswpsa:content contentId="%{#currentContentRecordVar.id}" var="currentContentVar" authToEditVar="isAuthToEditVar" workVersion="true" />
	<td>
		<s:if test="#isAuthToEditVar">
			<a href="<s:url action="edit" namespace="/do/jacms/Content"><s:param name="contentId" value="#currentContentVar.id" /></s:url>" title="<s:text name="label.edit" />:&#32;<s:property value="#currentContentVar.descr"/>"><s:property value="#currentContentVar.descr"/></a>
		</s:if>
		<s:else><s:property value="#currentContentVar.descr"/></s:else>
		<s:set var="isAuthToEditVar" value="%{false}" />
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