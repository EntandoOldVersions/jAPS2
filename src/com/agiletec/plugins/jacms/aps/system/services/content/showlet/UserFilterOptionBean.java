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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.common.entity.model.EntitySearchFilter;
import com.agiletec.aps.system.common.entity.model.IApsEntity;
import com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface;
import com.agiletec.aps.system.common.entity.model.attribute.BooleanAttribute;
import com.agiletec.aps.system.common.entity.model.attribute.DateAttribute;
import com.agiletec.aps.system.common.entity.model.attribute.ITextAttribute;
import com.agiletec.aps.system.common.entity.model.attribute.NumberAttribute;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.lang.Lang;
import com.agiletec.aps.util.DateConverter;
import com.agiletec.apsadmin.util.CheckFormatUtil;

/**
 * A user filter option of the list viewer showlet 
 * @author E.Santoboni
 */
public class UserFilterOptionBean {
	
	public UserFilterOptionBean(Properties properties, IApsEntity prototype) throws Throwable {
		this.setType(properties.getProperty(PARAM_TYPE));
		String type = this.getType();
		if (type == null || (!type.equals(TYPE_METADATA) && !type.equals(TYPE_ATTRIBUTE))) {
			throw new RuntimeException("Wrong user filter type '" + type + "'");
		}
		this.setKey(properties.getProperty(PARAM_KEY));
		String isAttributeFilter = properties.getProperty(PARAM_IS_ATTRIBUTE_FILTER);
		this.setAttributeFilter(null != isAttributeFilter && isAttributeFilter.equalsIgnoreCase("true"));
		if (this.getType().equals(TYPE_ATTRIBUTE) && null != this.getKey() && this.isAttributeFilter()) {
			this.setAttribute((AttributeInterface) prototype.getAttribute(this.getKey()));
			if (null == this.getAttribute()) {
				throw new ApsSystemException("Null attribute by key '" + this.getKey() + "'");
			}
		}
	}
	
	public UserFilterOptionBean(Properties properties, IApsEntity prototype, Integer currentFrame, Lang currentLang, HttpServletRequest request) throws Throwable {
		this(properties, prototype);
		this.setCurrentLang(currentLang);
		this.setCurrentFrame(currentFrame);
		this.extractFormParameters(request);
	}
	
	public String getKey() {
		return _key;
	}
	public void setKey(String key) {
		this._key = key;
	}
	public boolean isAttributeFilter() {
		return _attributeFilter;
	}
	public void setAttributeFilter(boolean attributeFilter) {
		this._attributeFilter = attributeFilter;
	}

	public void setType(String type) {
		this._type = type;
	}
	public String getType() {
		return _type;
	}
	
	public void setAttribute(AttributeInterface attribute) {
		this._attribute = attribute;
	}
	public AttributeInterface getAttribute() {
		return _attribute;
	}
	
	public Integer getCurrentFrame() {
		return _currentFrame;
	}
	protected void setCurrentFrame(Integer currentFrame) {
		this._currentFrame = currentFrame;
	}
	
	protected Lang getCurrentLang() {
		return _currentLang;
	}
	protected void setCurrentLang(Lang currentLang) {
		this._currentLang = currentLang;
	}
	
	protected void extractFormParameters(HttpServletRequest request) throws Throwable {
		String[] formFieldNames = null;
		try {
			String frameIdSuffix = (null != this.getCurrentFrame()) ? "_frame" + this.getCurrentFrame().toString() : "";
			if (this.getType().equals(TYPE_METADATA)) {
				formFieldNames = new String[1];
				String fieldName = null;
				if (this.getKey().equals(KEY_FULLTEXT)) {
					fieldName = TYPE_METADATA + "_fulltext" + frameIdSuffix;
				} else if (this.getKey().equals(KEY_CATEGORY)) {
					fieldName = TYPE_METADATA + "_category" + frameIdSuffix;
				}
				formFieldNames[0] = fieldName;
				String value = request.getParameter(fieldName);
				this.addFormValue(fieldName, value, formFieldNames.length);
			} else if (this.getType().equals(TYPE_ATTRIBUTE)) {
				AttributeInterface attribute = this.getAttribute();
				if (attribute instanceof ITextAttribute) {
					String[] fieldsSuffix = {"_textFieldName"};
					formFieldNames = this.extractAttributeParams(null, fieldsSuffix, frameIdSuffix, request);
				} else if (attribute instanceof DateAttribute) {
					String[] fieldsSuffix = {"_dateStartFieldName", "_dateEndFieldName"};
					formFieldNames = this.extractAttributeParams(attribute.getType(), fieldsSuffix, frameIdSuffix, request);
				} else if (attribute instanceof BooleanAttribute) {
					String[] fieldsSuffix = {"_booleanFieldName", "_booleanFieldName_ignore", "_booleanFieldName_control"};
					formFieldNames = this.extractAttributeParams(null, fieldsSuffix, frameIdSuffix, request);
				} else if (attribute instanceof NumberAttribute) {
					String[] fieldsSuffix = {"_numberStartFieldName", "_numberEndFieldName"};
					formFieldNames = this.extractAttributeParams(attribute.getType(), fieldsSuffix, frameIdSuffix, request);
				}
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "extractFormParameters");
			throw new ApsSystemException("Error extracting form parameters", t);
		}
		this.setFormFieldNames(formFieldNames);
	}
	
	private String[] extractAttributeParams(String noTextType, String[] fieldsSuffix, String frameIdSuffix, HttpServletRequest request) {
		String[] formFieldNames = new String[fieldsSuffix.length];
		for (int i = 0; i < fieldsSuffix.length; i++) {
			String fieldSuffix = fieldsSuffix[i];
			String fieldName = this.getAttribute().getName() + fieldSuffix + frameIdSuffix;
			formFieldNames[i] = fieldName;
			String value = request.getParameter(fieldName);
			this.addFormValue(fieldName, value, fieldsSuffix.length);
			if (null != noTextType) {
				boolean isDateAttribute = (noTextType.equals("Date"));
				this.checkNoTextAttributeFormValue(isDateAttribute, value, fieldName, this.getAttribute().getName() + fieldSuffix);
			}
		}
		return formFieldNames;
	}
	
	private void checkNoTextAttributeFormValue(boolean isDateAttribute, String value, String formFieldName, String errorKey) {
		if (value != null && value.trim().length() > 0) {
			boolean check = (isDateAttribute) ? CheckFormatUtil.isValidDate(value.trim()) : CheckFormatUtil.isValidNumber(value.trim());
			if (!check) {
				if (null == this.getFormFieldErrors()) {
					this.setFormFieldErrors(new HashMap<String, String>(2));
				}
				this.getFormFieldErrors().put(formFieldName, errorKey);
			}
		}
	}
	
	private void addFormValue(String key, String value, Integer formFields) {
		if (null != value && value.trim().length() > 0) {
			if (null == this.getFormFieldValues()) {
				this.setFormFieldValues(new HashMap<String, String>(formFields));
			}
			this.getFormFieldValues().put(key, value.trim());
		}
	}
	
	public EntitySearchFilter getEntityFilter() throws ApsSystemException {
		EntitySearchFilter filter = null;
		try {
			if (!this.getType().equals(TYPE_ATTRIBUTE) || null == this.getFormFieldValues()) {
				return null;
			}
			AttributeInterface attribute = this.getAttribute();
			if (attribute instanceof ITextAttribute) {
				String text = this.getFormFieldValues().get(this.getFormFieldNames()[0]);
				filter = new EntitySearchFilter(attribute.getName(), true, text, true);
				filter.setLangCode(this.getCurrentLang().getCode());
			} else if (attribute instanceof DateAttribute) {
				String start = this.getFormFieldValues().get(this.getFormFieldNames()[0]);
				String end = this.getFormFieldValues().get(this.getFormFieldNames()[1]);
				Date startDate = DateConverter.parseDate(start, "dd/MM/yyyy");
				Date endDate = DateConverter.parseDate(end, "dd/MM/yyyy");
				filter = new EntitySearchFilter(attribute.getName(), true, startDate, endDate);
			} else if (attribute instanceof BooleanAttribute) {
				String value = this.getFormFieldValues().get(this.getFormFieldNames()[0]);
				String ignore = this.getFormFieldValues().get(this.getFormFieldNames()[1]);
				if (null != ignore) {
					return null;
				} else if (null == value 
						|| value.equals("both")) {//special option for three state Attribute
					filter = new EntitySearchFilter(attribute.getName(), true);
					filter.setNullOption(true);
				} else {
					filter = new EntitySearchFilter(attribute.getName(), true, value, false);
				}
			} else if (attribute instanceof NumberAttribute) {
				String start = this.getFormFieldValues().get(this.getFormFieldNames()[0]);
				String end = this.getFormFieldValues().get(this.getFormFieldNames()[1]);
				Integer startNumber = null;
				try {
					startNumber = Integer.parseInt(start);
				} catch (Throwable t) {}
				Integer endNumber = null;
				try {
					endNumber = Integer.parseInt(end);
				} catch (Throwable t) {}
				filter = new EntitySearchFilter(attribute.getName(), true, startNumber, endNumber);
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "getFilter");
			throw new ApsSystemException("Error extracting entity search filters", t);
		}
		return filter;
	}
	
	public String[] getFormFieldNames() {
		return _formFieldNames;
	}
	public void setFormFieldNames(String[] formFieldNames) {
		this._formFieldNames = formFieldNames;
	}
	
	public Map<String, String> getFormFieldValues() {
		return _formFieldValues;
	}
	public void setFormFieldValues(Map<String, String> formFieldValues) {
		this._formFieldValues = formFieldValues;
	}
	
	public Map<String, String> getFormFieldErrors() {
		return _formFieldErrors;
	}
	public void setFormFieldErrors(Map<String, String> formFieldErrors) {
		this._formFieldErrors = formFieldErrors;
	}
	
	private String _type; //'metadata' || 'attribute'
	private String _key; //'fulltext' || 'category' || a name of attribute
	private boolean _attributeFilter;
	private AttributeInterface _attribute;
	
	private Integer _currentFrame;
	private Lang _currentLang;
	
	private String[] _formFieldNames;
	private Map<String, String> _formFieldValues;
	private Map<String, String> _formFieldErrors;
	
	public static final String PARAM_TYPE = "type";
	public static final String PARAM_KEY = "key";
	public static final String PARAM_IS_ATTRIBUTE_FILTER = "attributeFilter";
	
	public static final String TYPE_METADATA = "metadata";
	public static final String TYPE_ATTRIBUTE = "attribute";
	
	public static final String KEY_FULLTEXT = "fulltext";
	public static final String KEY_CATEGORY = "category";
	
}