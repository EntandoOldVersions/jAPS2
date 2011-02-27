<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="aps-core.tld" prefix="wp" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<h1><s:text name="title.myProfile" /></h1>

<div id="main">
<h2 class="margin-more-bottom"><s:text name="title.changePassword" /></h2>
<s:form action="changePassword">

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

<p class="noscreen">
	<wpsf:hidden name="username" />
</p>

<fieldset><legend><s:text name="label.info" /></legend>
<p>
	<label for="oldPassword" class="basic-mint-label"><s:text name="label.oldPassword" />:</label>
	<wpsf:password name="oldPassword" id="oldPassword" cssClass="text" />
</p>

<p>
	<label for="password" class="basic-mint-label"><s:text name="label.password" />:</label>
	<wpsf:password name="password" id="password" cssClass="text" />
</p>

<p>
	<label for="passwordConfirm" class="basic-mint-label"><s:text name="label.passwordConfirm" />:</label>
	<wpsf:password name="passwordConfirm" id="passwordConfirm" cssClass="text" />
</p>
</fieldset>

<p class="centerText"><wpsf:submit value="%{getText('label.save')}" cssClass="button" /></p>

</s:form>
</div>