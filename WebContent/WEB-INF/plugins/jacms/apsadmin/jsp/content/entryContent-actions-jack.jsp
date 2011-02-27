<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="aps-core.tld" prefix="wp" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>

<p class="buttons">
	<s:set name="iconImagePath" id="iconImagePath"><wp:resourceURL/>administration/common/img/icons/32x32/save.png</s:set>
	<wpsf:submit action="save" type="image" src="%{#iconImagePath}" value="%{getText('label.save')}" title="%{getText('note.button.saveContent')}" />
<wp:ifauthorized permission="validateContents">
	<s:set name="iconImagePath" id="iconImagePath"><wp:resourceURL/>administration/common/img/icons/32x32/approve.png</s:set>
	<wpsf:submit action="saveAndApprove" type="image" src="%{#iconImagePath}" value="%{getText('label.saveAndApprove')}" title="%{getText('note.button.saveAndApprove')}" />
	<s:if test="content.onLine">
		<s:set name="iconImagePath" id="iconImagePath"><wp:resourceURL/>administration/common/img/icons/32x32/suspend.png</s:set>
		<wpsf:submit action="suspend" type="image" src="%{#iconImagePath}" value="%{getText('label.suspend')}" title="%{getText('note.button.suspend')}" />
	</s:if>
</wp:ifauthorized>
</p>