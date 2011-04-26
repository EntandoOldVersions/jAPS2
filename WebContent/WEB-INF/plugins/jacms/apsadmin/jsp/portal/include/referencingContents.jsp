<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wpsa" uri="apsadmin-core.tld" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="c" uri="c.tld" %>
<%@ taglib prefix="jacms" uri="/WEB-INF/plugins/jacms/apsadmin/tld/jacms-apsadmin-core.tld" %>

<h3><s:text name="title.referencedContent" /></h3>

<s:if test="!#referencingContentsId.empty">
<wpsa:subset source="#referencingContentsId" count="10" objectName="contentReferencesGroup" advanced="true" offset="5" pagerId="referencingContentsId">
<s:set name="group" value="#contentReferencesGroup" />
<div class="pager">
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pagerInfo.jsp" />
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pager_formBlock.jsp" />
</div>

<table class="generic" id="contentListTable" summary="<s:text name="note.content.referencingContent.summary" />">
<caption><span><s:text name="title.contentList" /></span></caption>
	<s:include value="/WEB-INF/plugins/jacms/apsadmin/jsp/common/contentReferencesTable_header.jsp" />
	<s:iterator var="currentContentIdVar" value="#referencingContentsId">
		<jacms:content contentId="%{currentContentIdVar}" record="true" var="currentContentRecordVar" />
		<s:include value="/WEB-INF/plugins/jacms/apsadmin/jsp/common/contentReferencesTable_row.jsp" />
	</s:iterator>
</table>

<div class="pager">
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pager_formBlock.jsp" />
</div>
</wpsa:subset>

</s:if>
<s:else>
<p><s:text name="note.referencedContent.empty" /></p>
</s:else>