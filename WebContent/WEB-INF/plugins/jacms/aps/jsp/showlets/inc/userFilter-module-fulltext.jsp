<%@ taglib prefix="c" uri="c.tld" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>

<c:set var="formFieldNameVar" value="${userFilterOptionVar.formFieldNames[0]}" />

<p>
	<label for="<c:out value="${formFieldNameVar}" />"><wp:i18n key="TEXT" /></label><br />
	<input name="<c:out value="${formFieldNameVar}" />" id="<c:out value="${formFieldNameVar}" />" value="${userFilterOptionVar.formFieldValues[formFieldNameVar]}" type="text" class="text"/>
</p>
