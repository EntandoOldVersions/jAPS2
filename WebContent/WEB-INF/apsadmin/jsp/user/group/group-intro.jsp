<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="aps-core.tld" prefix="wp" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>

<s:set var="targetNS" value="%{'/do/Group'}" />
<h1><s:text name="title.groupManagement" /><s:include value="/WEB-INF/apsadmin/jsp/common/inc/operations-context-general.jsp" /></h1>

<div id="main">

<div class="intro group">
<s:text name="note.group.intro.html" />
</div>

</div>