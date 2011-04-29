<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>

<h1><s:text name="title.showletManagement" /></h1>

<div id="main">

	<s:if test="hasFieldErrors()">
<div class="message message_error">
<h2><s:text name="message.title.FieldErrors" /></h2>	
	<ul>
	<s:iterator value="fieldErrors">
		<s:iterator value="value">
		<li><s:property escape="false" /></li>
		</s:iterator>
	</s:iterator>
	</ul>
</div>
	</s:if>
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

<s:set var="pluginTitleCheck" value="'false'" ></s:set>
<s:set var="showletFlavours" value="showletFlavours" ></s:set>

<s:iterator var="showletFlavour" value="#showletFlavours">
<s:set var="firstType" value="%{#showletFlavour.get(0)}"></s:set>

<s:if test="%{#firstType.optgroup == 'stockShowletCode'}">
	<h2><span lang="en"><s:text name="title.showletManagement.showlets.stock" /></span></h2>
	<table class="generic" summary="<s:text name="note.showletManagement.showlets.summary" />">
		<caption><span><s:text name="name.showlets" /></span></caption>
</s:if>

<s:elseif test="%{#firstType.optgroup == 'customShowletCode'}">
	<h2><span lang="en"><s:text name="title.showletManagement.showlets.custom" /></span></h2>
	<table class="generic" summary="<s:text name="note.showletManagement.showlets.summary" />">
		<caption><span><s:text name="name.showlets" /></span></caption>

</s:elseif>

<s:elseif test="%{#firstType.optgroup == 'userShowletCode'}">
	<h2><span lang="en"><s:text name="title.showletManagement.showlets.user" /></span></h2>
	<table class="generic" summary="<s:text name="note.showletManagement.showlets.summary" />">
		<caption><span><s:text name="name.showlets" /></span></caption>

</s:elseif>

<s:else>
	<s:if test="#pluginTitleCheck.equals('false')">
		<h2><span lang="en"><s:text name="title.showletManagement.showlets.plugin" /></span></h2>
	</s:if>
	<table class="generic" summary="<s:text name="note.showletManagement.showlets.summary" />">
	<s:set var="pluginTitleCheck" value="'true'" ></s:set>

	<wpsa:set var="pluginPropertyName" value="%{getText(#firstType.optgroup + '.name')}" />	
	<wpsa:set var="pluginPropertyCode" value="%{getText(#firstType.optgroup + '.code')}" />		
	
	<caption><span>(<s:text name="#pluginPropertyCode" />) <s:text name="#pluginPropertyName" /></span></caption>
</s:else>

<tr>
	<th class="tinyColumn60"><abbr title="<s:text name="title.showletManagement.howmanypages.long" />"><s:text name="title.showletManagement.howmanypages.short" /></abbr></th>
	<th><s:text name="label.description" /></th>

<wpsa:hookPoint key="core.showletType.list.table.th" objectName="hookPointElements_core_showletType_list_table_th">
<s:iterator value="#hookPointElements_core_showletType_list_table_th" var="hookPointElement">
	<wpsa:include value="%{#hookPointElement.filePath}"></wpsa:include>
</s:iterator>
</wpsa:hookPoint>	

	<wp:ifauthorized permission="superuser">
	<th class="icon"><abbr title="<s:text name="label.actions" />">&ndash;</abbr></th>
	</wp:ifauthorized>
</tr>		
<s:iterator var="showletType" value="#showletFlavour" >
	<tr>
	<s:set var="showletUtilizers" value="getShowletUtilizers(#showletType.key)" ></s:set>
	<s:if test="#showletUtilizers != null && #showletUtilizers.size() > 0">
		<td class="tinyColumn60 rightText">
			<a href="<s:url namespace="/do/Portal/ShowletType" action="viewShowletUtilizers"><s:param name="showletTypeCode" value="#showletType.key" /></s:url>" title="<s:text name="title.showletManagement.howmanypages.goToSee" />: <s:property value="#showletType.value" />"><s:property value="#showletUtilizers.size()" />&#32;<img src="<wp:resourceURL />administration/common/img/icons/16x16/detail.png" alt=" " /></a>
		</td>
	</s:if>
	<s:else>
		<td class="tinyColumn60 centerText"><abbr title="<s:text name="label.none" />">&ndash;</abbr></td>
	</s:else>
		<td>
		<a href="<s:url namespace="/do/Portal/ShowletType" action="editShowletTitles"><s:param name="showletTypeCode" value="#showletType.key" /></s:url>" ><s:property value="#showletType.value" /></a>
		</td>

<wpsa:hookPoint key="core.showletType.list.table.td" objectName="hookPointElements_core_showletType_list_table_td">
<s:iterator value="#hookPointElements_core_showletType_list_table_td" var="hookPointElement">
	<wpsa:include value="%{#hookPointElement.filePath}"></wpsa:include>
</s:iterator>
</wpsa:hookPoint>
		
		<wp:ifauthorized permission="superuser">
		<td class="icon">
		<s:set var="concreteShowletType" value="%{getShowletType(#showletType.key)}"></s:set>
		<s:if test="null != #concreteShowletType.typeParameters && #concreteShowletType.typeParameters.size() > 0">
			<a href="<s:url namespace="/do/Portal/ShowletType" action="newShowletType"><s:param name="parentShowletTypeCode" value="#showletType.key" /></s:url>" title="<s:text name="label.userShowlet.new.from" />: <s:property value="#showletType.value" />" ><img src="<wp:resourceURL/>administration/common/img/icons/22x22/showlet-user-new.png" alt="<s:text name="label.userShowlet.new" />" /></a>
		</s:if>
		<s:if test="#firstType.optgroup == 'userShowletCode' && !#concreteShowletType.isLocked() && (#showletUtilizers == null || #showletUtilizers.size() == 0)">
			<a href="<s:url namespace="/do/Portal/ShowletType" action="trashShowletType"><s:param name="showletTypeCode" value="#showletType.key" /></s:url>" title="<s:text name="label.remove" />: <s:property value="#showletType.value" />" ><img src="<wp:resourceURL/>administration/common/img/icons/delete.png" alt="<s:text name="label.remove" />" /></a>
		</s:if>
		</td>
		</wp:ifauthorized>
	</tr>
</s:iterator>
<s:set var="showletUtilizers"></s:set>
</table>

</s:iterator>

</div>