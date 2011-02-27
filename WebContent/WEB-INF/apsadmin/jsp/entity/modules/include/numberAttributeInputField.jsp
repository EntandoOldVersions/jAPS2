<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>

<s:if test="#attribute.failedNumberString == null">
<s:set name="numberAttributeValue" value="#attribute.value"></s:set>
</s:if>
<s:else>
<s:set name="numberAttributeValue" value="#attribute.failedNumberString"></s:set>
</s:else>
<wpsf:textfield id="%{#attributeTracer.getFormFieldName(#attribute)}" 
		name="%{#attributeTracer.getFormFieldName(#attribute)}" value="%{#numberAttributeValue}"
		maxlength="254" cssClass="text"></wpsf:textfield>