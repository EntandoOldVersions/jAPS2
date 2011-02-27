<%@ taglib prefix="jacms" uri="/WEB-INF/plugins/jacms/aps/tld/jacms-aps-core.tld" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="c" uri="c.tld" %>

<h2><wp:i18n key="SEARCH_RESULTS" /></h2>
<jacms:searcher listName="result" />
<p><wp:i18n key="SEARCHED_FOR" />: <em><strong><c:out value="${search}" /></strong></em></p>
<c:choose>

	<c:when test="${empty result}">
	<p><em><wp:i18n key="SEARCH_NOTHING_FOUND" /></em></p>
	</c:when>
	<c:otherwise>
	
<wp:pager listName="result" objectName="groupContent" max="10" pagerIdFromFrame="true" >
	<p><em><wp:i18n key="SEARCH_RESULTS_INTRO" /> <!-- infamous whitespace hack -->
	<c:out value="${groupContent.size}" /> <!-- infamous whitespace hack -->
	<wp:i18n key="SEARCH_RESULTS_OUTRO" /> [<c:out value="${groupContent.begin + 1}" /> &ndash; <c:out value="${groupContent.end + 1}" />]:</em></p>
	<c:if test="${groupContent.size > groupContent.max}">
		<p class="paginazione">
			<c:choose>
			<c:when test="${'1' == groupContent.currItem}">&laquo; <wp:i18n key="PREV" /></c:when>
			<c:otherwise><a href="<wp:url paramRepeat="true" ><wp:parameter name="${groupContent.paramItemName}" ><c:out value="${groupContent.prevItem}"/></wp:parameter></wp:url>">&laquo; <wp:i18n key="PREV" /></a></c:otherwise>					
			</c:choose>
			<c:forEach var="item" items="${groupContent.items}">
				<c:choose>
				<c:when test="${item == groupContent.currItem}"> [<c:out value="${item}"/>] </c:when>
				<c:otherwise> <a href="<wp:url paramRepeat="true" ><wp:parameter name="${groupContent.paramItemName}" ><c:out value="${item}"/></wp:parameter></wp:url>"><c:out value="${item}"/></a> </c:otherwise>
				</c:choose>
			</c:forEach>
			<c:choose>
			<c:when test="${groupContent.maxItem == groupContent.currItem}"><wp:i18n key="NEXT" /> &raquo;</c:when>
			<c:otherwise><a href="<wp:url paramRepeat="true" ><wp:parameter name="${groupContent.paramItemName}" ><c:out value="${groupContent.nextItem}"/></wp:parameter></wp:url>"><wp:i18n key="NEXT" /> &raquo;</a></c:otherwise>					
			</c:choose>
		</p>
	</c:if>		
	<ul>
		<c:forEach var="contentId" items="${result}" begin="${groupContent.begin}" end="${groupContent.end}">
			<li><jacms:content contentId="${contentId}" modelId="list" /></li>
		</c:forEach>
	</ul>
	
	<c:if test="${groupContent.size > groupContent.max}">
		<p class="paginazione">
			<c:choose>
			<c:when test="${'1' == groupContent.currItem}">&laquo; <wp:i18n key="PREV" /></c:when>
			<c:otherwise><a href="<wp:url paramRepeat="true" ><wp:parameter name="${groupContent.paramItemName}" ><c:out value="${groupContent.prevItem}"/></wp:parameter></wp:url>">&laquo; <wp:i18n key="PREV" /></a></c:otherwise>					
			</c:choose>
			<c:forEach var="item" items="${groupContent.items}">
				<c:choose>
				<c:when test="${item == groupContent.currItem}"> [<c:out value="${item}"/>] </c:when>
				<c:otherwise> <a href="<wp:url paramRepeat="true" ><wp:parameter name="${groupContent.paramItemName}" ><c:out value="${item}"/></wp:parameter></wp:url>"><c:out value="${item}"/></a> </c:otherwise>
				</c:choose>
			</c:forEach>
			<c:choose>
			<c:when test="${groupContent.maxItem == groupContent.currItem}"><wp:i18n key="NEXT" /> &raquo;</c:when>
			<c:otherwise><a href="<wp:url paramRepeat="true" ><wp:parameter name="${groupContent.paramItemName}" ><c:out value="${groupContent.nextItem}"/></wp:parameter></wp:url>"><wp:i18n key="NEXT" /> &raquo;</a></c:otherwise>					
			</c:choose>
		</p>
	</c:if>	
		
</wp:pager>	
	
</c:otherwise>
</c:choose>