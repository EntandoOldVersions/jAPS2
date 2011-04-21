<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>
<h1><a href="<s:url action="viewShowlets" namespace="/do/Portal/ShowletType" />" title="<s:text name="note.goToSomewhere" />: <s:text name="title.showletManagement" />"><s:text name="title.showletManagement" /></a></h1>

<div id="main">

<wpsa:showletType key="%{showletTypeCode}" var="showletTypeVar" />
<h2><s:text name="title.showletManagement.edit" />:&#32;<s:property value="#showletTypeVar.titles[currentLang.code]" /></h2>

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

<s:form action="saveShowletTitles" >

<p class="noscreen">
	<wpsf:hidden name="showletTypeCode" />
	<wpsf:hidden name="strutsAction" />
</p>

<fieldset class="margin-more-top"><legend><s:text name="label.info" /></legend>

<p>
	<label for="showlet-title-en" class="basic-mint-label"><span class="monospace">(en)</span> <s:text name="label.title" />:</label>
	<wpsf:textfield id="showlet-title-en" name="englishTitle" cssClass="text" />
</p>

<p>
	<label for="showlet-title-it" class="basic-mint-label"><span class="monospace">(it)</span> <s:text name="label.title" />:</label>
	<wpsf:textfield id="showlet-title-it" name="italianTitle" cssClass="text" />
</p>

<s:if test="#showletTypeVar.logic && null != #showletTypeVar.parentType">
<p>
	<s:text name="note.showletType.origin" />: <s:property value="#showletTypeVar.parentType.titles[currentLang.code]" />
</p>
</s:if>

</fieldset>

<s:if test="#showletTypeVar.logic">
<fieldset><legend><s:text name="title.showletType.settings" /></legend>
	<s:iterator value="#showletTypeVar.parentType.typeParameters" id="showletParam" >
	<p>
		<s:if test="#showletParam.descr != ''">
			<em><s:property value="#showletParam.descr" />:</em><br />
		</s:if>
		<em class="important"><s:property value="#showletParam.name" /></em>:&#32;
		<s:property value="%{#showletTypeVar.config[#showletParam.name]}" />
	</p>
	</s:iterator>
</fieldset>
</s:if>

<wpsa:hookPoint key="core.showletType.entry" objectName="hookPointElements_core_showletType_entry">
<s:iterator value="#hookPointElements_core_showletType_entry" var="hookPointElement">
	<wpsa:include value="%{#hookPointElement.filePath}"></wpsa:include>
</s:iterator>
</wpsa:hookPoint>

<p class="centerText">
	<wpsf:submit value="%{getText('label.save')}" cssClass="button" />
</p>

</s:form>

</div>