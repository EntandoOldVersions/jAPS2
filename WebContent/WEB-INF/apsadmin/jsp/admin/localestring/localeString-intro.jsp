<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="aps-core.tld" prefix="wp" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<s:set var="targetNS" value="%{'/do/LocaleString'}" />
<h1><s:text name="title.languageAdmin.labels" /><s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" /></h1>

<div id="main">

<div class="intro localeString">
	<p><s:text name="note.localeString.intro.html" /></p>	
</div>

</div>