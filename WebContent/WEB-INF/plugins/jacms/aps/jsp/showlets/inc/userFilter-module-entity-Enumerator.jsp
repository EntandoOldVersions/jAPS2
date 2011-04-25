<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="c" uri="c.tld" %>

<c:set var="formFieldNameVar" value="${userFilterOptionVar.formFieldNames[0]}" />
<p>
	<c:set var="i18n_Attribute_Key" value="${userFilterOptionVar.attribute.name}" />
	<label for="<c:out value="${formFieldNameVar}" />"><wp:i18n key="${i18n_Attribute_Key}" /></label><br />
	<select name="${formFieldNameVar}" id="<c:out value="${formFieldNameVar}" />">
		<option value=""><wp:i18n key="ALL" /></option>
		<c:forEach items="${userFilterOptionVar.attribute.items}" var="enumeratorItemVar">
		<option value="<c:out value="${enumeratorItemVar}" />"><c:out value="${enumeratorItemVar}" /></option>
		</c:forEach>
	</select>
</p>