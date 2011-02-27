<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %><%--
<words>
	<key>
		<name>$content</name>
		<s:set var="contentPrototype" value="contentPrototype"></s:set>
		<s:if test="#contentPrototype != null">
			<key>
				<name>categories()</name>
			</key>
			<s:iterator value="#contentPrototype.attributeList" var="attribute">
				<s:set var="allowedMethods"
					value="%{getAllowedAttributeMethods(#contentPrototype, #attribute.name)}"></s:set>
				<key>
					<name>
						<s:property value="#attribute.name" />
					</name>
					<s:if test="#allowedMethods.empty"></s:if>
					<s:else>
						<s:iterator value="#allowedMethods" id="method" status="status">
							<key>
								<name>
									<s:property value="method" escapeJavaScript="false"
										escape="true" />
								</name>
							</key>
						</s:iterator>
					</s:else>
				</key>
			</s:iterator>
		</s:if>
	</key>
	<key>
		<name>$i18n</name>
		<key>
			<name>getLabel()</name>
		</key>
	</key>
</words>
--%><s:set var="xmlString"><words><key><name>$content</name><s:set var="contentPrototype" value="contentPrototype"></s:set><s:if test="#contentPrototype != null"><key><name>categories()</name></key><s:iterator value="#contentPrototype.attributeList" var="attribute"><s:set var="allowedMethods" value="%{getAllowedAttributeMethods(#contentPrototype, #attribute.name)}" ></s:set><key><name><s:property value="#attribute.name" /></name><s:if test="#allowedMethods.empty"></s:if><s:else><s:iterator value="#allowedMethods" id="method" status="status"><key><name><s:property value="method" escapeJavaScript="false" escape="true" /></name></key></s:iterator></s:else></key></s:iterator></s:if></key><key><name>$i18n</name><key><name>getLabel()</name></key></key></words></s:set>
<s:property value="#xmlString" escape="false" escapeJavaScript="false" />