<%@page contentType="text/html"%>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="c" uri="c.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<title><wp:currentPage param="title" /></title>
</head>
<body>
<h1>Pagina di errore</h1>

<a href="<wp:url page="homepage" />" >Home</a><br>
<div><wp:show frame="0" /></div>
</body>
</html>
