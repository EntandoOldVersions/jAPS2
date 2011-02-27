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
package com.agiletec.apsadmin.tags.util;

import java.util.Collection;

import javax.servlet.ServletRequest;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.tags.util.IPagerVO;
import com.agiletec.aps.tags.util.PagerTagHelper;
import com.agiletec.apsadmin.util.ApsRequestParamsUtil;

/**
 * Helper class for the pager for administration interface.
 * @version 1.0
 * @author E.Santoboni
 */
public class AdminPagerTagHelper extends PagerTagHelper {
	
	public IPagerVO getPagerVO(Collection collection, int max, boolean isAdvanced, 
			int offset, ServletRequest request) throws ApsSystemException {
		IPagerVO pagerVo = null;
		try {
			int item = this.getItemNumber(request);
			pagerVo = this.buildPageVO(collection, item, max, null, isAdvanced, offset);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "getPagerVO");
			throw new ApsSystemException("Errore in fase di preparazione del pagerVo", t);
		}
		return pagerVo;
	}
	
	protected int getItemNumber(ServletRequest request) {
		String stringItem = null;
		String[] params = ApsRequestParamsUtil.getApsParams("pagerItem", "_", request);
		if (params != null && params.length == 2) {
			stringItem = params[1];
		} else {
			stringItem = request.getParameter("pagerItem");
		}
		int item = 0;
		if (stringItem != null) {
			try {
				item = Integer.parseInt(stringItem);
			} catch (NumberFormatException e) {
				ApsSystemUtils.getLogger().severe("Errore in parsing stringItem " + stringItem);
			}
		}
		return item;
	}
	
}
