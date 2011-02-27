<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>

<s:form action="search" >

	<p>
		<label for="pageCodeToken" class="basic-mint-label label-search"><s:text name="label.search.by"/>&#32;<s:text name="name.pageCode"/>:</label>
		<wpsf:textfield name="pageCodeToken" id="pageCodeToken" value="%{pageCodeToken}" cssClass="text" />
	</p>
	<p>
		<wpsf:submit value="%{getText('label.search')}" cssClass="button" />
	</p>
	
</s:form>

