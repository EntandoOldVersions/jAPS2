<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="#lang.default">
	<wpsf:select name="%{#attributeTracer.getFormFieldName(#attribute)}" id="%{#attributeTracer.getFormFieldName(#attribute)}"  
		list="#attribute.items" value="%{#attribute.getText()}" />
</s:if>
<s:else>
	<s:if test="#attributeTracer.listElement">
		<wpsf:select name="%{#attributeTracer.getFormFieldName(#attribute)}" id="%{#attributeTracer.getFormFieldName(#attribute)}" 
			list="#attribute.items" value="%{#attribute.getText()}" />
	</s:if>
	<s:else>
		<s:text name="note.editContent.doThisInTheDefaultLanguage.must" />.
	</s:else>
</s:else>