<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="aps-core.tld" prefix="wp" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>

<s:set var="targetNS" value="%{'/do/jacms/ContentModel'}" />
<h1><s:text name="title.generalSettings.contentModels" /><s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" /></h1>

<div id="main">

<div class="intro contentModel">
<s:text name="note.contentModel.intro.html" />
</div>

</div>