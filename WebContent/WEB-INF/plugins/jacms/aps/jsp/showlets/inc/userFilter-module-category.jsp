<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="c" uri="c.tld" %>

<wp:categories var="systemCategories" titleStyle="prettyFull" />
<c:set var="formFieldNameVar" value="${userFilterOptionVar.formFieldNames[0]}" />
<c:set var="formFieldValue" value="${userFilterOptionVar.formFieldValues[formFieldNameVar]}" />
<p>
	<label for="category"><wp:i18n key="CATEGORY" /></label><br />
	<select id="category" name="<c:out value="${formFieldNameVar}" />">
		<option value=""><wp:i18n key="ALL" /></option>
		<c:forEach items="${systemCategories}" var="systemCategory">
		<option value="<c:out value="${systemCategory.key}" />" <c:if test="${formFieldValue == systemCategory.key}">selected="selected"</c:if> ><c:out value="${systemCategory.value}" /></option>
		</c:forEach>
	</select>
</p>