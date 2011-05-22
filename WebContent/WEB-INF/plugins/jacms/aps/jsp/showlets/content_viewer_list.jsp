<%@ taglib prefix="jacms" uri="/WEB-INF/plugins/jacms/aps/tld/jacms-aps-core.tld" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="c" uri="c.tld" %>
<%
/*
	Author: William Ghelfi <william.ghelfi@elicriso.biz> - 2005/05/23
	Author: Eugenio Santoboni <eugeniosant@tiscali.it>
	Content list viewer
*/
%>

<jacms:contentList listName="contentList" titleVar="titleVar" 
	pageLinkVar="pageLinkVar" pageLinkDescriptionVar="pageLinkDescriptionVar" userFilterOptionsVar="userFilterOptionsVar" />

<c:if test="${null != titleVar}">
	<h2><span><c:out value="${titleVar}" /></span></h2>
</c:if>

<c:set var="userFilterOptionsVar" value="${userFilterOptionsVar}" scope="request" />
<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/userFilter-module.jsp" />

<c:choose>
<c:when test="${contentList != null && !empty contentList}">
	<wp:pager listName="contentList" objectName="groupContent" pagerIdFromFrame="true" advanced="true" offset="5">
		<c:set var="group" value="${groupContent}" scope="request" />
		<ul>
		<c:forEach var="contentId" items="${contentList}" begin="${groupContent.begin}" end="${groupContent.end}">
			<li><jacms:content contentId="${contentId}" /></li>
		</c:forEach>	
		</ul>
		<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/pagerBlock.jsp" />
	</wp:pager>
</c:when>
<c:otherwise><p><wp:i18n key="LIST_VIEWER_EMPTY" /></p></c:otherwise>
</c:choose>

<c:if test="${null != pageLinkVar && null != pageLinkDescriptionVar}">
	<p><a href="<wp:url page="${pageLinkVar}"/>"><c:out value="${pageLinkDescriptionVar}" /></a></p>
</c:if>