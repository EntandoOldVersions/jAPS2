/*
*
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
* This file is part of jAPS software.
* jAPS is a free software; 
* you can redistribute it and/or modify it
* under the terms of the GNU General Public License (GPL) as published by the Free Software Foundation; version 2.
* 
* See the file License for the specific language governing permissions   
* and limitations under the License
* 
* 
* 
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
*/
package com.agiletec.plugins.jacms.aps.system.services.content.showlet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.common.entity.model.EntitySearchFilter;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.authorization.IAuthorizationManager;
import com.agiletec.aps.system.services.cache.ICacheManager;
import com.agiletec.aps.system.services.group.Group;
import com.agiletec.aps.system.services.lang.Lang;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.page.Showlet;
import com.agiletec.aps.system.services.user.UserDetails;
import com.agiletec.aps.util.ApsProperties;
import com.agiletec.aps.util.ApsWebApplicationUtils;
import com.agiletec.plugins.jacms.aps.system.JacmsSystemConstants;
import com.agiletec.plugins.jacms.aps.system.services.content.IContentManager;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.util.EntitySearchFilterDOM;

/**
 * Classe helper per la showlet di erogazione contenuti in lista.
 * @author E.Santoboni
 */
public class ContentListHelper implements IContentListHelper {
	
	@Override
	public EntitySearchFilter[] getFilters(String contentType, String showletParam, RequestContext reqCtx) {
		if (null == showletParam || showletParam.trim().length()==0) return null;
		EntitySearchFilterDOM dom = new EntitySearchFilterDOM();
		Lang currentLang = (Lang) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_LANG);
		return dom.getFilters(contentType, showletParam, this.getContentManager(), currentLang.getCode());
	}
	
	@Override
	public EntitySearchFilter getFilter(String contentType, IContentListFilterBean bean, RequestContext reqCtx) {
		EntitySearchFilterDOM dom = new EntitySearchFilterDOM();
		Lang currentLang = (Lang) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_LANG);
		return dom.getFilter(contentType, bean, this.getContentManager(), currentLang.getCode());
	}
	
	@Override
	public String getShowletParam(EntitySearchFilter[] filters) {
		EntitySearchFilterDOM dom = new EntitySearchFilterDOM();
		return dom.getShowletParam(filters);
	}
	
	@Override
	public List<String> getContentsId(IContentListBean bean, RequestContext reqCtx) throws Throwable {
		List<String> contentsId = null;
		try {
			Showlet showlet = (Showlet) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_SHOWLET);
			ApsProperties config = showlet.getConfig();
			if (null == bean.getContentType() && null != config) {
				bean.setContentType(config.getProperty("contentType"));
			}
			if (null == bean.getContentType()) {
				throw new ApsSystemException("Tipo contenuto non definito");
			}
			if (null == bean.getCategory() && null != config) {
				bean.setCategory(config.getProperty("category"));
			}
			this.addShowletFilters(bean, config, "filters", reqCtx);
			String[] categories = this.getCategories(bean.getCategory(), config);
			contentsId = this.getContentsId(bean, categories, reqCtx);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "getContentsId");
			throw new ApsSystemException("Errore in preparazione lista contenuti", t);
		}
		return contentsId;
	}
	
	protected String[] getCategories(String category, ApsProperties config) {
		if (null != category && category.trim().length()>0) {
			String[] categories = {category};
			return categories;
		}
		return null;
	}
	
	protected void addShowletFilters(IContentListBean bean, ApsProperties showletParams, String showletParamName, RequestContext reqCtx) {
		if (null == showletParams) return; 
		String showletFilters = showletParams.getProperty(showletParamName);
		EntitySearchFilter[] filters = this.getFilters(bean.getContentType(), showletFilters, reqCtx);
		if (null == filters) return;
		for (int i=0; i<filters.length; i++) {
			bean.addFilter(filters[i]);
		}
	}
	
	protected List<String> getContentsId(IContentListBean bean, String[] categories, RequestContext reqCtx) throws Throwable {
		Collection<String> userGroupCodes = this.getAllowedGroups(reqCtx);
		List<String> contentsId = this.getContentManager().loadPublicContentsId(bean.getContentType(), categories, bean.getFilters(), userGroupCodes);
		if (bean.isCacheable()) {
			String cacheKey = this.buildCacheKey(bean.getListName(), userGroupCodes, reqCtx);
			this.putListInCache(bean.getContentType(), reqCtx, contentsId, cacheKey);
		}
		return contentsId;
	}
	
	private void putListInCache(String contentType, RequestContext reqCtx, List<String> contentsId, String cacheKey) {
		if (this.getCacheManager() != null && contentsId != null) {
			IPage page = (IPage) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_PAGE);
			String pageCacheGroupName = SystemConstants.PAGES_CACHE_GROUP_PREFIX+page.getCode();
			String contentTypeCacheGroupName = JacmsSystemConstants.CONTENTS_ID_CACHE_GROUP_PREFIX+contentType;
			String[] groups = {contentTypeCacheGroupName, pageCacheGroupName};
			this.getCacheManager().putInCache(cacheKey, contentsId, groups);
		}
	}
	
	protected Collection<String> getAllowedGroups(RequestContext reqCtx) {
		IAuthorizationManager authManager = (IAuthorizationManager) ApsWebApplicationUtils.getBean(SystemConstants.AUTHORIZATION_SERVICE, reqCtx.getRequest());
		UserDetails currentUser = (UserDetails) reqCtx.getRequest().getSession().getAttribute(SystemConstants.SESSIONPARAM_CURRENT_USER);
		List<Group> groups = authManager.getGroupsOfUser(currentUser);
		Set<String> allowedGroup = new HashSet<String>();
		Iterator<Group> iter = groups.iterator();
    	while (iter.hasNext()) {
    		Group group = iter.next();
    		allowedGroup.add(group.getName());
    	}
		allowedGroup.add(Group.FREE_GROUP_NAME);
		return allowedGroup;
	}
	
	@Override
	public List<String> searchInCache(String listName, RequestContext reqCtx) throws Throwable {
		Collection<String> userGroupCodes = this.getAllowedGroups(reqCtx);
		String cacheKey = this.buildCacheKey(listName, userGroupCodes, reqCtx);
		Object object = this.getCacheManager().getFromCache(cacheKey, 1800);//refresh ogni 30min
		if (null != object && (object instanceof List)) {
			return (List) object;
		}
		return null;
	}
	
	protected String buildCacheKey(String listName, Collection<String> userGroupCodes, RequestContext reqCtx) {
		IPage page = (IPage) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_PAGE);
		StringBuffer cacheKey = new StringBuffer(page.getCode());
		Showlet currentShowlet = (Showlet) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_SHOWLET);
		cacheKey.append("_").append(currentShowlet.getType().getCode());
		Integer frame = (Integer) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_FRAME);
		cacheKey.append("_").append(frame.intValue());
		Lang currentLang = (Lang) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_LANG);
		cacheKey.append("_LANG").append(currentLang.getCode()).append("_");
		List<String> groupCodes = new ArrayList<String>(userGroupCodes);
		if (!groupCodes.contains(Group.FREE_GROUP_NAME)) groupCodes.add(Group.FREE_GROUP_NAME);
		Collections.sort(groupCodes);
		for (int i=0; i<groupCodes.size(); i++) {
			String code = (String) groupCodes.get(i);
			cacheKey.append("_").append(code);
		}
		if (null != currentShowlet.getConfig()) {
			List<String> paramKeys = new ArrayList(currentShowlet.getConfig().keySet());
			Collections.sort(paramKeys);
			for (int i=0; i<paramKeys.size(); i++) {
				if (i==0) {
					cacheKey.append("_SHOWLETPARAM");
				} else {
					cacheKey.append(",");
				}
				String paramkey = (String) paramKeys.get(i);
				cacheKey.append(paramkey).append("=").append(currentShowlet.getConfig().getProperty(paramkey));
			}
		}
		if (null != listName) {
			cacheKey.append("_LISTNAME").append(listName);
		}
		return cacheKey.toString();
	}
	
	protected ICacheManager getCacheManager() {
		return _cacheManager;
	}
	public void setCacheManager(ICacheManager cacheManager) {
		this._cacheManager = cacheManager;
	}
	
	protected IContentManager getContentManager() {
		return _contentManager;
	}
	public void setContentManager(IContentManager contentManager) {
		this._contentManager = contentManager;
	}
	
	private ICacheManager _cacheManager;
	private IContentManager _contentManager;
	
}