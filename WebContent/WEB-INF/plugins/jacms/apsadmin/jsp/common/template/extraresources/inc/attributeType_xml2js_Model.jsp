<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="aps-core.tld" %><%--
<words>
	<key>
		<name>$content</name>
		<s:iterator value="allowedPublicContentMethods" var="contentMethod">
			<key>
				<name><s:property value="#contentMethod" /></name>
			</key>
		</s:iterator>
		<s:set var="contentPrototype" value="contentPrototype"></s:set>
		<s:if test="#contentPrototype != null">
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
--%><s:set var="xmlString"><words><key><name>$content</name><s:iterator value="allowedPublicContentMethods" var="contentMethod"><key><name><s:property value="#contentMethod" /></name></key></s:iterator><s:set var="contentPrototype" value="contentPrototype"></s:set><s:if test="#contentPrototype != null"><s:iterator value="#contentPrototype.attributeList" var="attribute"><s:set var="allowedMethods" value="%{getAllowedAttributeMethods(#contentPrototype, #attribute.name)}" ></s:set><key><name><s:property value="#attribute.name" /></name><s:if test="#allowedMethods.empty"></s:if><s:else><s:iterator value="#allowedMethods" id="method" status="status"><key><name><s:property value="method" escapeJavaScript="false" escape="true" /></name></key></s:iterator></s:else></key></s:iterator></s:if></key><key><name>$i18n</name><key><name>getLabel()</name></key></key></words></s:set>
<s:property value="#xmlString" escape="false" escapeJavaScript="false" />