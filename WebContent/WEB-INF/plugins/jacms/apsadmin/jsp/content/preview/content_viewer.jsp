<%@ taglib prefix="jacms" uri="/WEB-INF/plugins/jacms/apsadmin/tld/jacms-apsadmin-core.tld" %>
<%@ taglib prefix="wpsa" uri="apsadmin-core.tld" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="wpsf" uri="apsadmin-form.tld" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<form id="forwardEntryContent" action="<wp:info key="systemParam" paramName="applicationBaseURL" />do/jacms/Content/entryContent.action" method="get">
<p style="border-bottom: 0.1em solid #000000; background-color: #eeeeee;">
	<span id="jAPSContentPreview" style="font-size: 1em; font-weight: bold; color: #000000"><s:text name="label.preview" /></span>
	<wpsf:submit value="%{getText('label.preview.backTo.editContent')}" />
</p>
</form>
<jacms:contentPreview />


<form id="forwardEntryContent" action="<wp:info key="systemParam" paramName="applicationBaseURL" />do/jacms/Content/entryContent.action" method="get">
<p style="border-top: 0.1em solid #000000; background-color: #eeeeee;">
	<span style="font-size: 1em; font-weight: bold; color: #000000"><s:text name="label.preview" /></span>
	<wpsf:submit value="%{getText('label.preview.backTo.editContent')}" />
</p>
</form>