<%@ taglib prefix="c" uri="c.tld" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>

-------------<br />
CHECKBOX
<p>
<c:set var="formFieldNameVar" value="${userFilterOptionVar.formFieldNames[0]}" />
<c:set var="formFieldValue" value="${userFilterOptionVar.formFieldValues[formFieldNameVar]}" />
<input name="<c:out value="${userFilterOptionVar.formFieldNames[0]}" />" <c:if test="${null != formFieldValue || formFieldValue == 'true'}">checked="checked"</c:if> value="true" type="checkbox" /> <c:out value="${userFilterOptionVar.attribute.name}" />
</p>

<p>
<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/userFilter-module-entity-Boolean-ignoreOption.jsp" />
</p>