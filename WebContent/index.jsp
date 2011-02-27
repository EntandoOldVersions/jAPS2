<%@ taglib uri="aps-core.tld" prefix="wp" %>
<%@ taglib prefix="c" uri="c.tld" %>

<c:set var="startLangCode"><wp:info key="startLang"></wp:info></c:set>
<jsp:forward page="/${startLangCode}/homepage.wp"/>