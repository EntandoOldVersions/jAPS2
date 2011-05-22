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
	
<wp:pager listName="result" objectName="groupContent" max="10" pagerIdFromFrame="true" advanced="true" offset="5">
	<c:set var="group" value="${groupContent}" scope="request" />
	
	<p><em><wp:i18n key="SEARCH_RESULTS_INTRO" /> <!-- infamous whitespace hack -->
	<c:out value="${groupContent.size}" /> <!-- infamous whitespace hack -->
	<wp:i18n key="SEARCH_RESULTS_OUTRO" /> [<c:out value="${groupContent.begin + 1}" /> &ndash; <c:out value="${groupContent.end + 1}" />]:</em></p>
	
	<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/pagerBlock.jsp" />
	
	<ul>
		<c:forEach var="contentId" items="${result}" begin="${groupContent.begin}" end="${groupContent.end}">
			<li><jacms:content contentId="${contentId}" modelId="list" /></li>
		</c:forEach>
	</ul>
	
	<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/pagerBlock.jsp" />
	
</wp:pager>	
	
</c:otherwise>
</c:choose>