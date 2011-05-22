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
package com.agiletec.plugins.jacms.aps.system.services.content.showlet.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.common.entity.model.EntitySearchFilter;
import com.agiletec.aps.system.common.entity.model.IApsEntity;
import com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface;
import com.agiletec.aps.system.services.lang.Lang;
import com.agiletec.plugins.jacms.aps.system.services.content.IContentManager;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.IContentListFilterBean;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.UserFilterOptionBean;

/**
 * Provides utility methods for content filters.
 * @author E.Santoboni
 */
public class FilterUtils {
	
	/**
	 * Return the showlet parameters in the form of property list
	 * @param filtersShowletParam The string to convert into a property list
	 * @return The property list.
	 */
	public static List<Properties> getFiltersProperties(String filtersShowletParam) {
		if (null == filtersShowletParam) return new ArrayList<Properties>();
		String[] filterStrings = filtersShowletParam.split("\\+");
		List<Properties> properties = new ArrayList<Properties>(filterStrings.length);
		for (int i=0; i<filterStrings.length; i++) {
			String fullFilterString = filterStrings[i];
			String filterString = fullFilterString.substring(1, fullFilterString.length()-1);
			Properties props = getProperties(filterString, DEFAULT_FILTER_PARAM_SEPARATOR);
			properties.add(props);
		}
		return properties;
	}
	
	public static List<UserFilterOptionBean> getUserFilters(String userFiltersParam, Integer currentFrame, Lang currentLang, IApsEntity prototype, HttpServletRequest request) {
		if (null == userFiltersParam) return new ArrayList<UserFilterOptionBean>();
		List<UserFilterOptionBean> list = new ArrayList<UserFilterOptionBean>();
		String[] filterStrings = userFiltersParam.split("\\+");
		for (int i = 0; i < filterStrings.length; i++) {
			String fullFilterString = filterStrings[i];
			try {
				String toStringFilter = fullFilterString.substring(1, fullFilterString.length()-1);
				Properties properties = getProperties(toStringFilter, DEFAULT_FILTER_PARAM_SEPARATOR);
				UserFilterOptionBean filterBean = new UserFilterOptionBean(properties, prototype, currentFrame, currentLang, request);
				list.add(filterBean);
			} catch (Throwable t) {
				ApsSystemUtils.logThrowable(t, FilterUtils.class, "getUserFilters", 
						"Error extracting user filter by string '" + fullFilterString + "' for type '" + prototype.getTypeCode() + "'");
			}
		}
		return list;
	}
	
	public EntitySearchFilter[] getFilters(String contentType, String showletParam, IContentManager contentManager, String langCode) {
		if (null == contentType) return null;
		IApsEntity prototype = contentManager.createContentType(contentType);
		if (null == prototype) return null;
		List<Properties> properties = getFiltersProperties(showletParam);
		EntitySearchFilter[] filters = new EntitySearchFilter[properties.size()];
		for (int i=0; i<properties.size(); i++) {
			Properties props = properties.get(i);
			EntitySearchFilter filter = EntitySearchFilter.getInstance(prototype, props);
			this.attachLangFilter(prototype, filter, props, langCode);
			filters[i] = filter;
		}
		return filters;
	}
	
	public EntitySearchFilter getFilter(String contentType, IContentListFilterBean bean, IContentManager contentManager, String langCode) {
		IApsEntity prototype = contentManager.createContentType(contentType);
		Properties props = new Properties();
		props.setProperty(EntitySearchFilter.KEY_PARAM, bean.getKey());
		props.setProperty(EntitySearchFilter.FILTER_TYPE_PARAM, String.valueOf(bean.isAttributeFilter()));
		props.setProperty(EntitySearchFilter.LIKE_OPTION_PARAM, String.valueOf(bean.getLikeOption()));
		if (null != bean.getValue()) props.setProperty(EntitySearchFilter.VALUE_PARAM, bean.getValue());
		if (null != bean.getStart()) props.setProperty(EntitySearchFilter.START_PARAM, bean.getStart());
		if (null != bean.getEnd()) props.setProperty(EntitySearchFilter.END_PARAM, bean.getEnd());
		if (null != bean.getOrder()) props.setProperty(EntitySearchFilter.ORDER_PARAM, bean.getOrder());
		EntitySearchFilter filter = EntitySearchFilter.getInstance(prototype, props);
		this.attachLangFilter(prototype, filter, props, langCode);
		return filter;
	}
	
	private void attachLangFilter(IApsEntity prototype, EntitySearchFilter filter, Properties props, String langCode) {
		String filterType = (String) props.get(EntitySearchFilter.FILTER_TYPE_PARAM);
		boolean isAttributeFilter = Boolean.getBoolean(filterType);
		if (isAttributeFilter) {
			String attributeName = (String) props.get(EntitySearchFilter.KEY_PARAM);
			AttributeInterface attribute = (AttributeInterface) prototype.getAttribute(attributeName);
			if (attribute.isMultilingual()) {
				filter.setLangCode(langCode);
			}
		}
	}
	
	public UserFilterOptionBean getUserFilter(String contentType, 
			IContentListFilterBean bean, IContentManager contentManager, RequestContext reqCtx) {
		UserFilterOptionBean filter = null;
		try {
			IApsEntity prototype = contentManager.createContentType(contentType);
			Properties props = new Properties();
			props.setProperty(UserFilterOptionBean.PARAM_KEY, bean.getKey());
			props.setProperty(UserFilterOptionBean.PARAM_IS_ATTRIBUTE_FILTER, String.valueOf(bean.isAttributeFilter()));
			Lang currentLang = (Lang) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_LANG);
			Integer currentFrame = (Integer) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_FRAME);
			filter = new UserFilterOptionBean(props, prototype, currentFrame, currentLang, reqCtx.getRequest());
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, FilterUtils.class, "getUserFilter", "Error creating user filter");
		}
		return filter;
	}
	
	/**
	 * Crea il parametro di configurazione della showlet, caratteristico per la rappresentazione dei filtri.
	 * Il parametro viene ricavato in base alla lista di filtri specificati.
	 * @param filters I filtri applicati.
	 * @return Il parametro di configurazione della showlet.
	 */
	public String getShowletParam(EntitySearchFilter[] filters) {
		StringBuffer param = new StringBuffer("");
		for (int i = 0; i < filters.length; i++) {
			if (i!=0) param.append("+");
			String element = filters[i].toString(); 
			param.append("(");
			param.append(element);
			param.append(")");
		}
		return param.toString();
	}
	
	/**
	 * Crea il parametro di configurazione della showlet, caratteristico per la rappresentazione dei filtri.
	 * Il parametro viene ricavato in base alla lista di properties specificata.
	 * @param properties Le properties rappresentanti ciascuna un filtro.
	 * @return Il parametro di configurazione della showlet.
	 */
	public static String getShowletParam(List<Properties> properties) {
		return getShowletParam(properties, DEFAULT_FILTER_PARAM_SEPARATOR);
	}
	
	public static String getShowletParam(List<Properties> properties, String separator) {
		StringBuffer param = new StringBuffer("");
		for (int i = 0; i < properties.size(); i++) {
			if (i!=0) param.append("+");
			Properties props = properties.get(i);
			String element = createElement(props, separator); 
			param.append("(");
			param.append(element);
			param.append(")");
		}
		return param.toString();
	}
	
	private static String createElement(Properties props, String separator) {
		StringBuffer param = new StringBuffer();
		Iterator<Object> keys = props.keySet().iterator();
		boolean init = true;
		while (keys.hasNext()) {
			String key = (String) keys.next();
			if (!init) param.append(separator);
			param.append(key).append("=").append(props.getProperty(key));
			init = false;
		}
		return param.toString();
	}
	
	private static Properties getProperties(String toStringFilter, String separator) {
		Properties props = new Properties();
		String[] params = toStringFilter.split(separator);
		for (int i=0; i<params.length; i++) {
			String[] elements = params[i].split("=");
			if (elements.length != 2) continue;
			props.setProperty(elements[0], elements[1]);
		}
		return props;
	}
	
	public static final String DEFAULT_FILTER_PARAM_SEPARATOR = ";";
	
}