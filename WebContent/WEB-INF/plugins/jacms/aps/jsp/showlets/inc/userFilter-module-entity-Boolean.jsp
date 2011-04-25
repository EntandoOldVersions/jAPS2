<%@ taglib prefix="c" uri="c.tld" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>

-------------<br />
BOOLEAN
<p>
<c:set var="formFieldNameVar" value="${userFilterOptionVar.formFieldNames[0]}" />
<c:set var="formFieldValue" value="${userFilterOptionVar.formFieldValues[formFieldNameVar]}" />
<input name="<c:out value="${formFieldNameVar}" />" <c:if test="${null != formFieldValue && formFieldValue == 'true'}">checked="checked"</c:if> value="true" type="radio" />** SI **
<input name="<c:out value="${formFieldNameVar}" />" <c:if test="${null == formFieldValue || formFieldValue == 'false'}">checked="checked"</c:if> value="false" type="radio" />** NO **
</p>

<p>
<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/userFilter-module-entity-Boolean-ignoreOption.jsp" />
</p>