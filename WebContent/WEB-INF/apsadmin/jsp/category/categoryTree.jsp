<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib prefix="wpsf" uri="apsadmin-form.tld" %>
<h1><s:text name="title.categoryManagement" /></h1>

<div id="main">

<s:form cssClass="action-form">

<s:if test="hasActionErrors()">
<div class="message message_error">
<h2><s:text name="message.title.ActionErrors" /></h2>
<ul>
	<s:iterator value="actionErrors">
		<li><s:property escape="false" /></li>
	</s:iterator>
</ul>
</div>
</s:if>

<fieldset class="margin-more-top"><legend><s:text name="title.categoryTree" /></legend>

<s:set var="categoryTreeStyleVar" ><wp:info key="systemParam" paramName="treeStyle_category" /></s:set>

<ul id="categoryTree">
	<s:set name="inputFieldName" value="'selectedNode'" />
	<s:set name="selectedTreeNode" value="selectedNode" />
	<s:set name="liClassName" value="'category'" />
	<s:if test="#categoryTreeStyleVar == 'classic'">
	<s:set name="currentRoot" value="treeRootNode" />
	<s:include value="/WEB-INF/apsadmin/jsp/common/treeBuilder.jsp" />
	</s:if>
	<s:elseif test="#categoryTreeStyleVar == 'request'">
	<s:set name="openTreeActionName" value="'openCloseCategoryTree'" />
	<s:set name="closeTreeActionName" value="'openCloseCategoryTree'" />
	<s:set name="currentRoot" value="showableTree" />
	<s:include value="/WEB-INF/apsadmin/jsp/common/treeBuilder-request-links.jsp" />
	</s:elseif>
</ul>

</fieldset>

<fieldset id="actions-container"><legend><s:text name="title.categoryActions" /></legend>
<p class="noscreen"><s:text name="title.categoryActionsIntro" /></p>

<p class="buttons">
	<s:set var="iconImagePath"><wp:resourceURL/>administration/common/img/icons/32x32/page-new.png</s:set>
	<wpsf:submit action="new" type="image" src="%{#iconImagePath}" value="%{getText('category.options.new')}" title="%{getText('category.options.new')}" />
	<s:set var="iconImagePath"><wp:resourceURL/>administration/common/img/icons/32x32/page-edit.png</s:set>
	<wpsf:submit action="edit" type="image" src="%{#iconImagePath}" value="%{getText('category.options.modify')}" title="%{getText('category.options.modify')}" />
	<s:set var="iconImagePath"><wp:resourceURL/>administration/common/img/icons/32x32/detail.png</s:set>
	<wpsf:submit action="detail" type="image" src="%{#iconImagePath}" value="%{getText('category.options.detail')}" title="%{getText('category.options.detail')}" />
	<s:set var="iconImagePath"><wp:resourceURL/>administration/common/img/icons/32x32/delete.png</s:set>
	<wpsf:submit action="trash" type="image" src="%{#iconImagePath}" value="%{getText('category.options.delete')}" title="%{getText('category.options.delete')}" />
</p>
</fieldset>
</s:form>

</div>