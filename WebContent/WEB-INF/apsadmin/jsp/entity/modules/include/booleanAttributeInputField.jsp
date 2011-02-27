<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>

<wpsf:radio name="%{#attributeTracer.getFormFieldName(#attribute)}" id="true_%{#attributeTracer.getFormFieldName(#attribute)}" value="true" checked="%{#attribute.value == true}" /><label for="true_<s:property value="#attributeTracer.getFormFieldName(#attribute)" />"><s:text name="label.yes"/></label><span class="noscreen">&nbsp;,&nbsp;</span><wpsf:radio name="%{#attributeTracer.getFormFieldName(#attribute)}" id="false_%{#attributeTracer.getFormFieldName(#attribute)}" value="false" checked="%{#attribute.value == false}" /><label for="false_<s:property value="#attributeTracer.getFormFieldName(#attribute)" />"><s:text name="label.no"/></label>
