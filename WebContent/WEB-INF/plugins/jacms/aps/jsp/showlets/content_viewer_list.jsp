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

<c:if test="${null != userFilterOptionsVar && !empty userFilterOptionsVar}">

<c:set var="hasUserFilterError" value="${false}" />
<c:forEach var="userFilterOptionVar" items="${userFilterOptionsVar}">
<c:if test="${null != userFilterOptionVar.formFieldErrors && !empty userFilterOptionVar.formFieldErrors}"><c:set var="hasUserFilterError" value="${true}" /></c:if>
</c:forEach>

<c:if test="${hasUserFilterError}">
	<h3><wp:i18n key="ERRORS" /></h3>
	<ul>
		<c:forEach var="userFilterOptionVar" items="${userFilterOptionsVar}">
			<c:if test="${null != userFilterOptionVar.formFieldErrors}">
			<c:forEach var="formFieldError" items="${userFilterOptionVar.formFieldErrors}">
			<li><c:out value="${formFieldError}" /></li>
			</c:forEach>
			</c:if>
		</c:forEach>
	</ul>
</c:if>
<c:set var="hasUserFilterError" value="${false}" />

<%-- search form with user filters --%>
<form action="<wp:url />" method="post">
	<c:forEach var="userFilterOptionVar" items="${userFilterOptionsVar}">
		<c:set var="userFilterOptionVar" value="${userFilterOptionVar}" scope="request" />
		<c:choose>
			<c:when test="${userFilterOptionVar.type == 'metadata' && (userFilterOptionVar.key == 'fulltext' || userFilterOptionVar.key == 'category')}">
				<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/userFilter-module-${userFilterOptionVar.key}.jsp" />
			</c:when>
			<c:when test="${userFilterOptionVar.type == 'attribute'}">
				<c:choose>
					<c:when test="${userFilterOptionVar.attribute.type == 'Monotext' || userFilterOptionVar.attribute.type == 'Text' || userFilterOptionVar.attribute.type == 'Longtext' || userFilterOptionVar.attribute.type == 'Hypertext'}">
						<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/userFilter-module-entity-Text.jsp" />
					</c:when>
					<c:when test="${userFilterOptionVar.attribute.type == 'Enumerator' }">
						<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/userFilter-module-entity-Enumerator.jsp" />
					</c:when>
					<c:when test="${userFilterOptionVar.attribute.type == 'Number'}">
						<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/userFilter-module-entity-Number.jsp" />
					</c:when>
					<c:when test="${userFilterOptionVar.attribute.type == 'Date'}">
						<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/userFilter-module-entity-Date.jsp" />
					</c:when>
					<c:when test="${userFilterOptionVar.attribute.type == 'Boolean' }">
						<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/userFilter-module-entity-Boolean.jsp" />
					</c:when>
					<c:when test="${userFilterOptionVar.attribute.type == 'CheckBox'}">
						<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/userFilter-module-entity-CheckBox.jsp" />
					</c:when>
					<c:when test="${userFilterOptionVar.attribute.type == 'ThreeState'}">
						<c:import url="/WEB-INF/plugins/jacms/aps/jsp/showlets/inc/userFilter-module-entity-ThreeState.jsp" />
					</c:when>
				</c:choose>
			</c:when>
		</c:choose>
	</c:forEach>
	<p>
		<input type="submit" value="<wp:i18n key="SEARCH" />" class="button" />
	</p>
</form>
</c:if>

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

<c:if test="${null != pageLinkVar && null != pageLinkDescriptionVar}">
	<p><a href="<wp:url page="${pageLinkVar}"/>"><c:out value="${pageLinkDescriptionVar}" /></a></p>
</c:if>