<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<wpsf:textarea cols="50" rows="3" id="%{#attributeTracer.getFormFieldName(#attribute)}" 
	name="%{#attributeTracer.getFormFieldName(#attribute)}" value="%{#attribute.getTextForLang(#lang.code)}" cssClass="text" />