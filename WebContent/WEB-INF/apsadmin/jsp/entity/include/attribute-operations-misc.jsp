<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="aps-core.tld" prefix="wp" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>

<td class="icon">
	<wpsa:actionParam action="moveAttribute" var="actionName" >
		<wpsa:actionSubParam name="attributeIndex" value="%{#elementIndex}" />
		<wpsa:actionSubParam name="movement" value="UP" />
	</wpsa:actionParam>
	<s:set name="iconImagePath" id="iconImagePath"><wp:resourceURL/>administration/common/img/icons/go-up.png</s:set>
	<wpsf:submit action="%{#actionName}" type="image" src="%{#iconImagePath}" value="%{getText('label.moveUp')}" title="%{getText('label.moveInPositionNumber')}: %{#elementIndex}" />
</td>

<td class="icon">
	<wpsa:actionParam action="moveAttribute" var="actionName" >
		<wpsa:actionSubParam name="attributeIndex" value="%{#elementIndex}" />
		<wpsa:actionSubParam name="movement" value="DOWN" />
	</wpsa:actionParam>
	<s:set name="iconImagePath" id="iconImagePath"><wp:resourceURL/>administration/common/img/icons/go-down.png</s:set>
	<wpsf:submit action="%{#actionName}" type="image" src="%{#iconImagePath}" value="%{getText('label.moveDown')}" title="%{getText('label.moveInPositionNumber')}: %{#elementIndex+2}" />
</td>

<td class="icon">
	<wpsa:actionParam action="removeAttribute" var="actionName" >
		<wpsa:actionSubParam name="attributeIndex" value="%{#elementIndex}" />
	</wpsa:actionParam>
	<s:set name="iconImagePath" id="iconImagePath"><wp:resourceURL/>administration/common/img/icons/delete.png</s:set>
	<wpsf:submit action="%{#actionName}" type="image" src="%{#iconImagePath}" value="%{getText('label.remove')}" title="%{getText('label.remove')}" />
</td>