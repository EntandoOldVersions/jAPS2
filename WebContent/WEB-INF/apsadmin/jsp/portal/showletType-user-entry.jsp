<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>

<h1><s:text name="title.newShowletType" /></h1>

<div id="main">
<h2><s:text name="title.newShowletType.from" />:&#32;
<s:if test="strutsAction == 1">
<wpsa:showletType var="parentShowletTypeVar" key="%{parentShowletTypeCode}" />
<em><s:property value="%{getTitle(#parentShowletTypeVar.code, #parentShowletTypeVar.titles)}" /></em>
</s:if>
<s:elseif test="strutsAction == 3">
<em><s:property value="%{getTitle(showletToCopy.type.code, showletToCopy.type.titles)}" /></em>
</s:elseif>
</h2>

<s:if test="strutsAction == 3">
	<wpsa:page var="pageVar" key="%{pageCode}" />
	<p>
		<s:text name="note.showletType.page"/>:&#32;<em class="important"><s:property value="%{getTitle(#pageVar.code, #pageVar.titles)}" /></em>,&#32;<s:text name="note.showletType.position" />:&#32;<em class="important"><s:property value="framePos" /></em>
	</p>
</s:if>

<s:form action="saveUserShowlet" namespace="/do/Portal/ShowletType" >
<s:if test="hasActionErrors()">
	<div class="message message_error">
	<h3><s:text name="message.title.ActionErrors" /></h3>	
		<ul>
		<s:iterator value="actionErrors">
			<li><s:property escape="false" /></li>
		</s:iterator>
		</ul>
	</div>
</s:if>
<s:if test="hasFieldErrors()">
	<div class="message message_error">
	<h3><s:text name="message.title.FieldErrors" /></h3>	
		<ul>
		<s:iterator value="fieldErrors">
			<s:iterator value="value">
			<li><s:property escape="false" /></li>
			</s:iterator>
		</s:iterator>
		</ul>
	</div>
</s:if>
<p class="noscreen">
	<wpsf:hidden name="strutsAction" />
	<s:if test="strutsAction == 1">
	<wpsf:hidden name="parentShowletTypeCode" />
	</s:if>
	<s:elseif test="strutsAction == 3">
	<wpsf:hidden name="pageCode" />
	<wpsf:hidden name="framePos" />
	</s:elseif>
</p>

<fieldset class="margin-more-top"><legend><s:text name="label.info" /></legend>
	<p>
		<label for="showletTypeCode" class="basic-mint-label"><s:text name="label.code" />:</label>
		<wpsf:textfield id="showletTypeCode" name="showletTypeCode" cssClass="text" />
	</p>
	
	<p>
		<label for="showlet-title-en" class="basic-mint-label"><span class="monospace">(en)</span> <s:text name="label.title" />:</label>
		<wpsf:textfield id="showlet-title-en" name="englishTitle" cssClass="text" />
	</p>
	
	<p>
		<label for="showlet-title-it" class="basic-mint-label"><span class="monospace">(it)</span> <s:text name="label.title" />:</label>
		<wpsf:textfield id="showlet-title-it" name="italianTitle" cssClass="text" />
	</p>
</fieldset>

<fieldset><legend><s:text name="title.showletType.settings" /></legend>
	<s:if test="strutsAction == 1">
		<s:set var="parentShowletType" value="%{getShowletType(parentShowletTypeCode)}" />
		<s:iterator value="#parentShowletType.typeParameters" id="showletParam" >
			<p>
				<s:if test="#showletParam.descr != ''">
					<em><s:property value="#showletParam.descr" />:</em><br />
				</s:if>
				<label for="<s:property value="#showletParam.name" />" class="basic-mint-label"><s:property value="#showletParam.name" /></label>
				<wpsf:textfield id="%{#showletParam.name}" name="%{#showletParam.name}" value="%{#request.parameters[#showletParam.name]}" cssClass="text" />
			</p>
		</s:iterator>
	</s:if>
	<s:elseif test="strutsAction == 3">
		<s:iterator value="showletToCopy.type.typeParameters" id="showletParam" >
		<p>
			<s:if test="#showletParam.descr != ''">
				<em><s:property value="#showletParam.descr" />:</em><br />
			</s:if>
			<em class="important"><s:property value="#showletParam.name" /></em>:&#32;
			<s:property value="%{showletToCopy.config[#showletParam.name]}" />
		</p>
		</s:iterator>
	</s:elseif>
</fieldset>

<p class="centerText"><wpsf:submit value="%{getText('label.save')}" cssClass="button" />

<s:if test="strutsAction == 3">
<wpsa:actionParam action="saveUserShowlet" var="actionName" >
	<wpsa:actionSubParam name="replaceOnPage" value="true" />
</wpsa:actionParam>
<wpsf:submit action="%{#actionName}" value="%{getText('label.save.replace')}" cssClass="button"/></p>
</s:if>
<s:else></p></s:else>

</s:form>
</div>