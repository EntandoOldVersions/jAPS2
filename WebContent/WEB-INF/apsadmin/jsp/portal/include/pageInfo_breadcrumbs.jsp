<%@ taglib prefix="s" uri="/struts-tags" %>

<p><s:text name="note.youAreHere" />: 

<s:set value="%{getBreadCrumbsTargets(#breadcrumbs_pivotPageCode)}" name="breadCrumbsTargets" ></s:set>
<s:iterator value="#breadCrumbsTargets" id="target" status="rowstatus">
<s:if test="%{#rowstatus.index != 0}"> &raquo; </s:if>
<s:if test="%{(#rowstatus.index == (#breadCrumbsTargets.size()-1) && strutsAction == 2) || !isUserAllowed(#target)}">
<s:property value="getTitle(#target.code, #target.titles)" />
</s:if>
<s:else>
<a href="<s:url namespace="/do/Page" action="viewTree" ><s:param name="selectedNode"><s:property value="#target.code" /></s:param></s:url>" title="<s:text name="note.goToSomewhere" />: <s:property value="getTitle(#target.code, #target.titles)" />"><s:property value="getTitle(#target.code, #target.titles)" /></a>
</s:else>
</s:iterator>

</p>