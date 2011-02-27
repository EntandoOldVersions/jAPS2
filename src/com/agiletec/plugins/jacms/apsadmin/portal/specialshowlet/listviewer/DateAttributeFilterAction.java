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

import java.util.Date;
import java.util.Properties;

import com.agiletec.aps.system.common.entity.model.EntitySearchFilter;
import com.agiletec.aps.util.DateConverter;

/**
 * Classe action per la configurazione del filtro su attributo tipo "Date".
 * @author E.Santoboni
 */
public class DateAttributeFilterAction extends BaseFilterAction {
	
	@Override
	protected Properties createFilterProperties() {
		Properties properties =  super.createFilterProperties();
		int filterOption = this.getFilterOptionId();
		if (filterOption == VALUE_FILTER_OPTION) {
			this.setDateFilterParam(properties, EntitySearchFilter.VALUE_PARAM, this.getDateValueType(), this.getDateValue());
		}
		if (filterOption == RANGE_FILTER_OPTION) {
			this.setDateFilterParam(properties, EntitySearchFilter.START_PARAM, this.getDateStartType(), this.getDateStart());
			this.setDateFilterParam(properties, EntitySearchFilter.END_PARAM, this.getDateEndType(), this.getDateEnd());
		}
		return properties;
	}
	
	protected void setDateFilterParam(Properties properties, String paramName, int dateType, Date date) {
		if (CURRENT_DATE_FILTER == dateType) {
			properties.put(paramName, "today");
		} else if (INSERTED_DATE_FILTER == dateType && null != date) {
			properties.put(paramName, DateConverter.getFormattedDate(date, EntitySearchFilter.DATE_PATTERN));
		}
	}
	
	@Override
	public int getFilterTypeId() {
		return DATE_ATTRIBUTE_FILTER_TYPE;
	}
	
	public int getDateValueType() {
		return _dateValueType;
	}
	public void setDateValueType(int dateValueType) {
		this._dateValueType = dateValueType;
	}
	
	public Date getDateValue() {
		return _dateValue;
	}
	public void setDateValue(Date dateValue) {
		this._dateValue = dateValue;
	}
	
	public int getDateStartType() {
		return _dateStartType;
	}
	public void setDateStartType(int dateStartType) {
		this._dateStartType = dateStartType;
	}
	
	public Date getDateStart() {
		return _dateStart;
	}
	public void setDateStart(Date dateStart) {
		this._dateStart = dateStart;
	}
	
	public int getDateEndType() {
		return _dateEndType;
	}
	public void setDateEndType(int dateEndType) {
		this._dateEndType = dateEndType;
	}
	
	public Date getDateEnd() {
		return _dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this._dateEnd = dateEnd;
	}
	
	private int _dateValueType = -1;
	private Date _dateValue;
	private int _dateStartType = -1;
	private Date _dateStart;
	private int _dateEndType = -1;
	private Date _dateEnd;
	
}
