<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<h1><a href="<s:url action="viewTree" namespace="/do/Page" />" title="<s:text name="note.goToSomewhere" />: <s:text name="title.pageManagement" />"><s:text name="title.pageManagement" /></a></h1>

<div id="main">
<h2><s:text name="title.configPage" /></h2>

<s:set var="breadcrumbs_pivotPageCode" value="pageCode" />
<s:include value="/WEB-INF/apsadmin/jsp/portal/include/pageInfo_breadcrumbs.jsp" />

<div class="subsection-light">
<h3><s:text name="title.configPage.youAreDoing" /></h3>

<s:action namespace="/do/Page" name="printPageDetails" executeResult="true" ignoreContextParams="true"><s:param name="selectedNode" value="pageCode"></s:param></s:action>
<s:include value="/WEB-INF/apsadmin/jsp/portal/include/frameInfo.jsp" />

<h3 class="margin-more-top margin-bit-bottom"><s:text name="name.showlet" />:&#32;<s:property value="%{getTitle(showlet.type.code, showlet.type.titles)}" /></h3>

<s:form action="saveViewerConfig" namespace="/do/jacms/Page/SpecialShowlet/Viewer">
<p class="noscreen">
	<wpsf:hidden name="pageCode" />
	<wpsf:hidden name="frame" />
	<wpsf:hidden name="showletTypeCode" value="%{showlet.type.code}" />
</p>

	<s:if test="hasFieldErrors()">
<div class="message message_error">
<h4><s:text name="message.title.FieldErrors" /></h4>	
	<ul>
	<s:iterator value="fieldErrors">
		<s:iterator value="value">
		<li><s:property escape="false" /></li>
		</s:iterator>
	</s:iterator>
	</ul>
</div>
	</s:if>

<s:set name="showletParams" value="showlet.type.parameter" />

<s:property value="#showletParams['contentId'].descr" />
<h4><s:text name="title.configContentViewer.settings" /></h4>

<s:if test="showlet.config['contentId'] != null">
<s:set name="content" value="%{getContentVo(showlet.config['contentId'])}"></s:set>
<div class="centerText">
<dl class="table-display">
	<dt><s:text name="label.code" /></dt>
	<dd><s:property value="#content.id" /></dd>

	<dt><s:text name="label.description" /></dt>
	<dd><s:property value="#content.descr" /></dd>
</dl>
</div>
<p class="noscreen clear">
	<wpsf:hidden name="contentId" value="%{getShowlet().getConfig().get('contentId')}" />
</p>

<p class="margin-more-bottom"><wpsf:submit action="searchContents" value="%{getText('label.change')}" cssClass="button" /></p>

<fieldset><legend><s:text name="title.publishingOptions" /></legend>
<p>
	<label for="modelId" clasS="basic-mint-label"><s:text name="label.contentModel" />:</label>
	<wpsf:select id="modelId" name="modelId" value="%{getShowlet().getConfig().get('modelId')}" 
	list="%{getModelsForContent(showlet.config['contentId'])}" headerKey="" headerValue="%{getText('label.default')}" listKey="id" listValue="description" cssClass="text" />
</p>
</fieldset>
</s:if>
<s:else>
<p>
	<s:text name="note.noContentSet" />
</p>
<p>
	<wpsf:submit action="searchContents" value="%{getText('label.choose')}" cssClass="button" />
</p>
</s:else>

<p class="centerText">
	<wpsf:submit value="%{getText('label.confirm')}" cssClass="button" />
</p>

</s:form>

</div>
</div>