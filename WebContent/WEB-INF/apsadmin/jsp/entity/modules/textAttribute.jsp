<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<wpsf:textfield id="%{#attributeTracer.getFormFieldName(#attribute)}" 
	name="%{#attributeTracer.getFormFieldName(#attribute)}" value="%{#attribute.getTextForLang(#lang.code)}"
	maxlength="254" cssClass="text" />