<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="aps-core.tld" prefix="wp" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>
<wpsa:actionParam action="addListElement" var="actionName" >
	<wpsa:actionSubParam name="attributeName" value="%{#attribute.name}" />
	<wpsa:actionSubParam name="listLangCode" value="%{#lang.code}" />
</wpsa:actionParam>

<wpsf:submit action="%{#actionName}" value="%{getText('label.add')}" cssClass="button" />