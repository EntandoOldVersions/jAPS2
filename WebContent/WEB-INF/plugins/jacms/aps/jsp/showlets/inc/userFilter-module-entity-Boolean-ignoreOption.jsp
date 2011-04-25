<%@ taglib prefix="c" uri="c.tld" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>

<c:set var="formFieldNameControlVar" value="${userFilterOptionVar.formFieldNames[2]}" />
<input name="<c:out value="${formFieldNameControlVar}" />" type="hidden" value="true" />

<c:set var="formFieldNameIgnoreVar" value="${userFilterOptionVar.formFieldNames[1]}" />
<c:set var="formFieldIgnoreValue" value="${userFilterOptionVar.formFieldValues[formFieldNameIgnoreVar]}" />
<c:set var="formFieldControlValue" value="${userFilterOptionVar.formFieldValues[formFieldNameControlVar]}" />
<input name="<c:out value="${userFilterOptionVar.formFieldNames[1]}" />" <c:if test="${(null == formFieldIgnoreValue && null == formFieldControlValue) || (null != formFieldControlValue && formFieldIgnoreValue == 'true')}">checked="checked"</c:if> value="true" type="checkbox" /> ** IGNORA ** 