<%@ taglib prefix="jacms" uri="/WEB-INF/plugins/jacms/aps/tld/jacms-aps-core.tld" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="c" uri="c.tld" %>
<%
/*  
	Author: William Ghelfi <william.ghelfi@elicriso.biz> - 2005/05/23
	Author: Eugenio Santoboni <eugeniosant@tiscali.it>
	
	Erogatore automatico di contenuti.
*/  		
%>

<jacms:contentList listName="contentList" />

<c:if test="${contentList != null}">

	<wp:pager listName="contentList" objectName="groupContent" pagerIdFromFrame="true" >
		<ul>
		<c:forEach var="contentId" items="${contentList}" begin="${groupContent.begin}" end="${groupContent.end}">
			<li><jacms:content contentId="${contentId}" /></li>
		</c:forEach>	
		</ul>
		<c:if test="${groupContent.size > groupContent.max}">
			<p class="paginazione">
				<c:choose>
				<c:when test="${'1' == groupContent.currItem}">&lt;&lt; <wp:i18n key="PREV" /></c:when>
				<c:otherwise><a href="<wp:url paramRepeat="true" ><wp:parameter name="${groupContent.paramItemName}" ><c:out value="${groupContent.prevItem}"/></wp:parameter></wp:url>">&lt;&lt; <wp:i18n key="PREV" /></a></c:otherwise>					
				</c:choose>
				<c:forEach var="item" items="${groupContent.items}">
					<c:choose>
					<c:when test="${item == groupContent.currItem}">&#32;[<c:out value="${item}"/>]&#32;</c:when>
					<c:otherwise>&#32;<a href="<wp:url paramRepeat="true" ><wp:parameter name="${groupContent.paramItemName}" ><c:out value="${item}"/></wp:parameter></wp:url>"><c:out value="${item}"/></a>&#32;</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:choose>
				<c:when test="${groupContent.maxItem == groupContent.currItem}"><wp:i18n key="NEXT" /> &gt;&gt;</c:when>
				<c:otherwise><a href="<wp:url paramRepeat="true" ><wp:parameter name="${groupContent.paramItemName}" ><c:out value="${groupContent.nextItem}"/></wp:parameter></wp:url>"><wp:i18n key="NEXT" /> &gt;&gt;</a></c:otherwise>					
				</c:choose>
			</p>
		</c:if>
		
	</wp:pager>
	
</c:if>