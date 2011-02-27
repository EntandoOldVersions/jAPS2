<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="aps-core.tld" prefix="wp" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>

<s:include value="linkAttributeConfigIntro.jsp"></s:include>
<h3 class="margin-more-bottom"><s:text name="title.configureLinkAttribute" />&#32;(<s:text name="title.step2of2" />)</h3>
<s:include value="linkAttributeConfigReminder.jsp"></s:include>

<s:form>
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
<fieldset><legend><s:text name="title.insertURL" /></legend>
<p><s:text name="note.typeValidURL" /></p>

<p>
	<label for="url" class="basic-mint-label"><s:text name="label.url" />:</label>
	<wpsf:textfield name="url" id="url" cssClass="text" />
</p>

<p class="centerText">
	<wpsf:submit action="joinUrlLink" value="%{getText('label.confirm')}" cssClass="button" />
</p>
</fieldset>
</s:form>

</div>