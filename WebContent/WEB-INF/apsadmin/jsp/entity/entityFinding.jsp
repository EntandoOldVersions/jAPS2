<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="apsadmin-core.tld" prefix="wpsa" %>
<%@ taglib uri="apsadmin-form.tld" prefix="wpsf" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %>
<%@ taglib uri="/WEB-INF/aps/tld/c.tld" prefix="c" %>
<%--
raggiungibile con:
http://localhost:8080/PortalExample/do/Entity/search.action?entityManagerName=jacmsContentManager
 --%>
<h1>**ENTITIES FROM MANAGER** <s:property value="entityManagerName" /> </h1>

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

<s:form action="search">
	
	<fieldset>
		<legend class="accordion_toggler"><s:text name="title.searchFilters" /></legend>  
		
		<div class="accordion_element">
			
			<s:if test="null != entityTypeCode && entityTypeCode != ''">
				<s:set var="entityPrototype" value="entityPrototype" />
				*<s:property value="#entityPrototype.typeDescr" />* <wpsf:submit value="CAMBIA" cssClass="button" action="changeEntityType" />
				<wpsf:hidden name="entityTypeCode" />
			</s:if>
			<s:else>
				<wpsf:select list="entityPrototypes" name="entityTypeCode" headerKey="" headerValue="" 
					listKey="typeCode" listValue="typeDescr"></wpsf:select>
			</s:else>
			
			<s:set var="searcheableAttributes" value="searcheableAttributes" ></s:set>
			
			<s:if test="null != #searcheableAttributes && #searcheableAttributes.size() > 0">
				
				<s:iterator var="attribute" value="#searcheableAttributes">
					<s:set var="currentFieldId">entityFinding_<s:property value="#attribute.name" /></s:set> 
					
					<s:if test="#attribute.textAttribute"> 
						<p>
							<label for="<s:property value="currentFieldId" />"><s:property value="#attribute.name" /></label><br />
							<s:set name="textInputFieldName"><s:property value="#attribute.name" />_textFieldName</s:set>
							<wpsf:textfield id="%{currentFieldId}" cssClass="text" name="%{#textInputFieldName}" value="%{getSearchFormFieldValue(#textInputFieldName)}" /><br />
						</p>
					</s:if>
					
					<s:elseif test="#attribute.type == 'Date'">
						<s:set name="dateStartInputFieldName" ><s:property value="#attribute.name" />_dateStartFieldName</s:set>
						<s:set name="dateEndInputFieldName" ><s:property value="#attribute.name" />_dateEndFieldName</s:set>
						<p>
							<label for="<s:property value="%{currentFieldId}" />_dateStartFieldName"><s:property value="#attribute.name" /> ** from date **</label>:<br />
							<wpsf:textfield id="%{currentFieldId}_dateStartFieldName" cssClass="text" name="%{#dateStartInputFieldName}" value="%{getSearchFormFieldValue(#dateStartInputFieldName)}" /><span class="inlineNote">dd/MM/yyyy</span>
						</p>
						<p>
							<label for="<s:property value="%{currentFieldId}" />_dateEndFieldName"><s:property value="#attribute.name" />** to date **</label>:<br />
							<wpsf:textfield id="%{currentFieldId}_dateEndFieldName" cssClass="text" name="%{#dateEndInputFieldName}" value="%{getSearchFormFieldValue(#dateEndInputFieldName)}" /><span class="inlineNote">dd/MM/yyyy</span>
						</p>
					</s:elseif>
					
					<s:elseif test="#attribute.type == 'Number'">
						<s:set name="numberStartInputFieldName" ><s:property value="#attribute.name" />_numberStartFieldName</s:set>
						<s:set name="numberEndInputFieldName" ><s:property value="#attribute.name" />_numberEndFieldName</s:set>
						<p>
							<label for="<s:property value="currentFieldId" />_start"><s:property value="#attribute.name" /> ** from value **</label>:<br />
							<wpsf:textfield id="%{currentFieldId}_start" cssClass="text" name="%{#numberStartInputFieldName}" value="%{getSearchFormFieldValue(#numberStartInputFieldName)}" /><br />
						</p>
						<p>
							<label for="<s:property value="currentFieldId" />_end"><s:property value="#attribute.name" /> ** to value **</label>:<br />
							<wpsf:textfield id="%{currentFieldId}_end" cssClass="text" name="%{#numberEndInputFieldName}" value="%{getSearchFormFieldValue(#numberEndInputFieldName)}" /><br />
						</p>
					</s:elseif>
					
					<s:elseif test="#attribute.type == 'Boolean' || #attribute.type == 'ThreeState'"> 
						<p>
							<span class="important"><s:property value="#attribute.name" /></span><br />
						</p>
						<s:set name="booleanInputFieldName" ><s:property value="#attribute.name" />_booleanFieldName</s:set>
						<s:set name="booleanInputFieldValue" ><s:property value="%{getSearchFormFieldValue(#booleanInputFieldName)}" /></s:set>
						<ul class="noBullet">
							<li><wpsf:radio id="none_%{#booleanInputFieldName}" name="%{#booleanInputFieldName}" value="" checked="%{!#booleanInputFieldValue.equals('true') && !#booleanInputFieldValue.equals('false')}" cssClass="radio" /><label for="none_<s:property value="#booleanInputFieldName" />" class="normal" ><s:text name="label.bothYesAndNo"/></label></li>
							<li><wpsf:radio id="true_%{#booleanInputFieldName}" name="%{#booleanInputFieldName}" value="true" checked="%{#booleanInputFieldValue == 'true'}" cssClass="radio" /><label for="true_<s:property value="#booleanInputFieldName" />" class="normal" ><s:text name="label.yes"/></label></li>
							<li><wpsf:radio id="false_%{#booleanInputFieldName}" name="%{#booleanInputFieldName}" value="false" checked="%{#booleanInputFieldValue == 'false'}" cssClass="radio" /><label for="false_<s:property value="#booleanInputFieldName" />" class="normal"><s:text name="label.no"/></label></li>
						</ul>
					</s:elseif>
					
				</s:iterator>
				
			</s:if>
			
			<p><wpsf:submit value="Cerca" cssClass="button" action="search" /></p>
		</div>
	</fieldset>
</s:form>

<s:form action="search">
	<p class="noscreen">
		<wpsf:hidden name="entityTypeCode" />
		<s:iterator var="attribute" value="#searcheableAttributes">
			<s:if test="#attribute.textAttribute">
				<s:set name="textInputFieldName" ><s:property value="#attribute.name" />_textFieldName</s:set>
				<wpsf:hidden name="%{#textInputFieldName}" value="%{getSearchFormFieldValue(#textInputFieldName)}" />
			</s:if>
			<s:elseif test="#attribute.type == 'Date'">
				<s:set name="dateStartInputFieldName" ><s:property value="#attribute.name" />_dateStartFieldName</s:set>
				<s:set name="dateEndInputFieldName" ><s:property value="#attribute.name" />_dateEndFieldName</s:set>
				<wpsf:hidden name="%{#dateStartInputFieldName}" value="%{getSearchFormFieldValue(#dateStartInputFieldName)}" />
				<wpsf:hidden name="%{#dateEndInputFieldName}" value="%{getSearchFormFieldValue(#dateEndInputFieldName)}" />
			</s:elseif>
			<s:elseif test="#attribute.type == 'Number'">
				<s:set name="numberStartInputFieldName" ><s:property value="#attribute.name" />_numberStartFieldName</s:set>
				<s:set name="numberEndInputFieldName" ><s:property value="#attribute.name" />_numberEndFieldName</s:set>
				<wpsf:hidden name="%{#numberStartInputFieldName}" value="%{getSearchFormFieldValue(#numberStartInputFieldName)}" />
				<wpsf:hidden name="%{#numberEndInputFieldName}" value="%{getSearchFormFieldValue(#numberEndInputFieldName)}" />
			</s:elseif>
			<s:elseif test="#attribute.type == 'Boolean' || #attribute.type == 'ThreeState'"> 
				<s:set name="booleanInputFieldName" ><s:property value="#attribute.name" />_booleanFieldName</s:set>
				<wpsf:hidden name="%{#booleanInputFieldName}" value="%{getSearchFormFieldValue(#booleanInputFieldName)}" />
			</s:elseif>
		</s:iterator>
	</p>
	
	<s:set var="entityIds" value="searchResult" />
	<%-- <s:if test="#entityIds.isEmpty()">no risultati </s:if> --%>
	<wpsa:subset source="#entityIds" count="15" objectName="entityGroup" advanced="true" offset="5">
		<s:set name="group" value="#entityGroup" />
		
		<div class="pager">
			<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pagerInfo.jsp" />
			<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pager_formBlock.jsp" />
		</div>   
		
		<br/><br/>
		
		<s:iterator var="entityId">
			<s:property value="#entityId" />
			<br />
		</s:iterator>
		
		<div class="pager">
			<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pager_formBlock.jsp" />
		</div>
		
	</wpsa:subset>
	
</s:form>