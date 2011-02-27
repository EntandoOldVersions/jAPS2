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
package com.agiletec.plugins.jacms.apsadmin.portal.specialshowlet.listviewer;

import java.util.List;
import java.util.Properties;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.services.category.Category;
import com.agiletec.aps.system.services.category.ICategoryManager;
import com.agiletec.aps.system.services.page.Showlet;
import com.agiletec.apsadmin.portal.specialshowlet.SimpleShowletConfigAction;
import com.agiletec.plugins.jacms.aps.system.services.content.IContentManager;
import com.agiletec.plugins.jacms.aps.system.services.content.model.SmallContentType;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.util.EntitySearchFilterDOM;
import com.agiletec.plugins.jacms.aps.system.services.contentmodel.ContentModel;
import com.agiletec.plugins.jacms.aps.system.services.contentmodel.IContentModelManager;

/**
 * Action per la gestione della configurazione della showlet erogatore avanzato lista contenuti.
 * @author E.Santoboni
 */
public class ContentListViewerShowletAction extends SimpleShowletConfigAction implements IContentListViewerShowletAction {
	
	@Override
	public void validate() {
		super.validate();
		if (null == this.getFieldErrors().get("contentType")) {
			if (null == this.getContentManager().createContentType(this.getContentType())) {
				this.addFieldError("contentType", this.getText("error.showlet.listViewer.invalidContentType", new String[]{this.getContentType()}));
			}
		}
		if (this.getActionErrors().size()>0 || this.getFieldErrors().size()>0) {
			try {
				Showlet showlet = super.createNewShowlet();
				this.setShowlet(showlet);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
	}
	
	@Override
	public String init() {
		try {
			super.init();
			String filters = this.getShowlet().getConfig().getProperty("filters");
			List<Properties> properties = EntitySearchFilterDOM.getPropertiesFilters(filters);
			this.setFiltersProperties(properties);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "init");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String configContentType() {
		try {
			Showlet showlet = super.createNewShowlet();
			showlet.getConfig().setProperty("contentType", this.getContentType());
			this.setShowlet(showlet);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "init");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String changeContentType() {
		try {
			Showlet showlet = super.createNewShowlet();
			this.setShowlet(showlet);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "changeContentType");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String addFilter() {
		try {
			super.createValuedShowlet();
			String filters = this.getShowlet().getConfig().getProperty("filters");
			List<Properties> properties = EntitySearchFilterDOM.getPropertiesFilters(filters);
			properties.add(this.getNewFilter());
			String newShowletParam = EntitySearchFilterDOM.getShowletParam(properties);
			this.getShowlet().getConfig().setProperty("filters", newShowletParam);
			this.setFiltersProperties(properties);
		} catch (Throwable t) {
			t.printStackTrace();
			ApsSystemUtils.logThrowable(t, this, "addFilter");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String moveFilter() {
		try {
			//ESTRAI "filters" campo testo
			String filters = this.getFilters();
			
			//Estrai lista properties da testo
			List<Properties> properties = EntitySearchFilterDOM.getPropertiesFilters(filters);
			
			//FAI LO SPOSTAMENTO.
			int filterIndex = this.getFilterIndex();
			Properties element = properties.get(filterIndex);
			if (getMovement().equalsIgnoreCase(MOVEMENT_UP_CODE)){
				if (filterIndex > 0) {
					properties.remove(filterIndex);
					properties.add(filterIndex -1, element);
				}
			} else if (getMovement().equalsIgnoreCase(MOVEMENT_DOWN_CODE)) {
				if (filterIndex < properties.size() -1) {
					properties.remove(filterIndex);
					properties.add(filterIndex + 1, element);
				}
			}
			
			//Setta Properties
			this.setFiltersProperties(properties);
			
			//crea nuovo "filters" String
			String newShowletParam = EntitySearchFilterDOM.getShowletParam(properties);
			this.setFilters(newShowletParam);
			
			//Crea showlet valorizzata 
			this.createValuedShowlet();
			this.getShowlet().getConfig().setProperty("filters", newShowletParam);
			
			//SETTA property FILTERS nella showlet
			this.setFiltersProperties(properties);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "moveFilter");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String removeFilter() {
		try {
			//ESTRAI "filters" campo testo
			String filters = this.getFilters();
			
			//Estrai lista properties da testo
			List<Properties> properties = EntitySearchFilterDOM.getPropertiesFilters(filters);
			
			//ELIMINA
			int filterIndex = this.getFilterIndex();
			properties.remove(filterIndex);
			
			//Setta Properties
			this.setFiltersProperties(properties);
			
			//crea nuovo "filters" String
			String newShowletParam = EntitySearchFilterDOM.getShowletParam(properties);
			this.setFilters(newShowletParam);
			
			//Crea showlet valorizzata 
			this.createValuedShowlet();
			this.getShowlet().getConfig().setProperty("filters", newShowletParam);
			
			//SETTA property FILTERS nella showlet
			this.setFiltersProperties(properties);
			
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "removeFilter");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	/**
	 * Restituisce la lista di contenuti (in forma small) definiti nel sistema.
	 * Il metodo è a servizio delle jsp che richiedono questo dato per fornire 
	 * una corretta visualizzazione della pagina.
	 * @return La lista di tipi di contenuto (in forma small) definiti nel sistema.
	 */
	public List<SmallContentType> getContentTypes() {
		return this.getContentManager().getSmallContentTypes();
	}
	
	/**
	 * Restituisce la lista di categorie definite nel sistema.
	 * @return La lista di categorie definite nel sistema.
	 */
	public List<Category> getCategories() {
		return this.getCategoryManager().getCategoriesList();
	}
	
	/**
	 * Restituisce la lista di Modelli di Contenuto compatibili con il tipo di contenuto specificato.
	 * @param contentType Il tipo di contenuto cui restituire i modelli compatibili.
	 * @return La lista di Modelli di Contenuto compatibili con il tipo di contenuto specificato.
	 */
	public List<ContentModel> getModelsForContentType(String contentType) {
		return this.getContentModelManager().getModelsForContentType(contentType);
	}
	
	public String getContentType() {
		return _contentType;
	}
	public void setContentType(String contentType) {
		this._contentType = contentType;
	}
	
	public String getModelId() {
		return _modelId;
	}
	public void setModelId(String modelId) {
		this._modelId = modelId;
	}
	
	public String getCategory() {
		return _category;
	}
	public void setCategory(String category) {
		this._category = category;
	}
	
	public String getMaxElemForItem() {
		return _maxElemForItem;
	}
	public void setMaxElemForItem(String maxElemForItem) {
		this._maxElemForItem = maxElemForItem;
	}
	
	public String getFilters() {
		return _filters;
	}
	public void setFilters(String filters) {
		this._filters = filters;
	}
	
	public int getFilterIndex() {
		return _filterIndex;
	}
	public void setFilterIndex(int filterIndex) {
		this._filterIndex = filterIndex;
	}
	
	public String getMovement() {
		return _movement;
	}
	public void setMovement(String movement) {
		this._movement = movement;
	}
	
	public List<Properties> getFiltersProperties() {
		return _filtersProperties;
	}
	public void setFiltersProperties(List<Properties> filtersProperties) {
		this._filtersProperties = filtersProperties;
	}
	
	public Properties getNewFilter() {
		return _newFilter;
	}
	public void setNewFilter(Properties newFilter) {
		this._newFilter = newFilter;
	}
	
	protected IContentModelManager getContentModelManager() {
		return _contentModelManager;
	}
	public void setContentModelManager(IContentModelManager contentModelManager) {
		this._contentModelManager = contentModelManager;
	}
	
	protected IContentManager getContentManager() {
		return _contentManager;
	}
	public void setContentManager(IContentManager contentManager) {
		this._contentManager = contentManager;
	}
	
	protected ICategoryManager getCategoryManager() {
		return _categoryManager;
	}
	public void setCategoryManager(ICategoryManager categoryManager) {
		this._categoryManager = categoryManager;
	}
	
	private String _contentType;
	private String _modelId;
	private String _category;
	private String _maxElemForItem;
	private String _filters;
	
	private int _filterIndex;
	private String _movement;
	
	private List<Properties> _filtersProperties;
	
	private Properties _newFilter;
	
	private IContentModelManager _contentModelManager;
	private IContentManager _contentManager;
	private ICategoryManager _categoryManager;
	
}