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
package com.agiletec.plugins.jacms.aps.system.services.cache;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.common.entity.event.EntityTypesChangingEvent;
import com.agiletec.aps.system.common.entity.event.EntityTypesChangingObserver;
import com.agiletec.aps.system.common.entity.model.IApsEntity;
import com.agiletec.aps.system.services.cache.ICacheManager;
import com.agiletec.aps.system.services.lang.ILangManager;
import com.agiletec.aps.system.services.lang.Lang;
import com.agiletec.plugins.jacms.aps.system.JacmsSystemConstants;
import com.agiletec.plugins.jacms.aps.system.services.content.ContentManager;
import com.agiletec.plugins.jacms.aps.system.services.content.event.PublicContentChangedEvent;
import com.agiletec.plugins.jacms.aps.system.services.content.event.PublicContentChangedObserver;
import com.agiletec.plugins.jacms.aps.system.services.content.model.Content;
import com.agiletec.plugins.jacms.aps.system.services.contentmodel.ContentModel;
import com.agiletec.plugins.jacms.aps.system.services.contentmodel.IContentModelManager;
import com.agiletec.plugins.jacms.aps.system.services.contentmodel.event.ContentModelChangedEvent;
import com.agiletec.plugins.jacms.aps.system.services.contentmodel.event.ContentModelChangedObserver;

/**
 * Cache Wrapper Manager for plugin jacms
 * @author E.Santoboni
 */
public class CmsCacheWrapperManager extends AbstractService 
		implements PublicContentChangedObserver, ContentModelChangedObserver, EntityTypesChangingObserver {
	
	@Override
	public void init() throws Exception {
		ApsSystemUtils.getLogger().config(this.getClass().getName() + ": initialized");
	}
	
	@Override
	public void updateFromPublicContentChanged(PublicContentChangedEvent event) {
		Content content = event.getContent();
		Logger log = ApsSystemUtils.getLogger();
		if (log.isLoggable(Level.FINEST)) {
			log.info("Notified public content update : type " + content.getId());
		}
		String authInfokey = ContentManager.getContentAuthInfoCacheKey(content.getId());
		this.getCacheManager().flushEntry(authInfokey);
		this.getCacheManager().flushGroup(JacmsSystemConstants.CONTENTS_ID_CACHE_GROUP_PREFIX + content.getTypeCode());
		List<Lang> langs = this.getLangManager().getLangs();
		List<ContentModel> models = this.getContentModelManager().getModelsForContentType(content.getTypeCode());
		for (int i = 0; i < langs.size(); i++) {
			Lang lang = langs.get(i);
			for (int j = 0; j < models.size(); j++) {
				ContentModel contentModel = models.get(j);
				String key = ContentManager.getRenderedContentCacheKey(content.getId(), contentModel.getId(), lang.getCode());
				this.getCacheManager().flushEntry(key);
			}
		}
	}
	
	@Override
	public void updateFromContentModelChanged(ContentModelChangedEvent event) {
		ContentModel model = event.getContentModel();
		Logger log = ApsSystemUtils.getLogger();
		if (log.isLoggable(Level.FINEST)) {
			log.info("Notified content model update : type " + model.getId());
		}
		String cacheGroupKey = JacmsSystemConstants.CONTENT_MODEL_CACHE_GROUP_PREFIX + model.getId();
		this.getCacheManager().flushGroup(cacheGroupKey);
	}
	
	@Override
	public void updateFromEntityTypesChanging(EntityTypesChangingEvent event) {
		String entityManagerName = event.getEntityManagerName();
		if (!entityManagerName.equals(JacmsSystemConstants.CONTENT_MANAGER)) return;
		if (event.getOperationCode() == EntityTypesChangingEvent.INSERT_OPERATION_CODE) return;
		IApsEntity oldEntityType = event.getOldEntityType();
		Logger log = ApsSystemUtils.getLogger();
		if (log.isLoggable(Level.FINEST)) {
			log.info("Notified content type modify : type " + oldEntityType.getTypeCode());
		}
		String typeGroupKey = JacmsSystemConstants.CONTENTS_TYPE_CACHE_GROUP_PREFIX + oldEntityType.getTypeCode();
		this.getCacheManager().flushGroup(typeGroupKey);
	}
	
	protected ICacheManager getCacheManager() {
		return _cacheManager;
	}
	public void setCacheManager(ICacheManager cacheManager) {
		this._cacheManager = cacheManager;
	}
	
	protected ILangManager getLangManager() {
		return _langManager;
	}
	public void setLangManager(ILangManager langManager) {
		this._langManager = langManager;
	}
	
	protected IContentModelManager getContentModelManager() {
		return _contentModelManager;
	}
	public void setContentModelManager(IContentModelManager contentModelManager) {
		this._contentModelManager = contentModelManager;
	}
	
	private ICacheManager _cacheManager;
	private ILangManager _langManager;
	private IContentModelManager _contentModelManager;
	
}