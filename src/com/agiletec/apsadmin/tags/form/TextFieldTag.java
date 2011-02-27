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
package com.agiletec.apsadmin.tags.form;

import javax.servlet.jsp.JspException;

import com.agiletec.apsadmin.tags.util.AutoIndexingTagHelper;
import com.agiletec.apsadmin.tags.util.IAutoIndexingTag;

/**
 * This class extends the org.apache.struts2.views.jsp.ui.TextFieldTag in order to handle auto-incrementing Tabindex 
 * @author E.Santoboni
 */
public class TextFieldTag extends org.apache.struts2.views.jsp.ui.TextFieldTag implements IAutoIndexingTag {
	
	public int doStartTag() throws JspException {
		String currentCounter = this.getCurrentIndex();
		if (null != currentCounter) {
			this.setTabindex(currentCounter);
		}
		return super.doStartTag();
	}
	
	public String getCurrentIndex() {
		return AutoIndexingTagHelper.getCurrentIndex(this, this.pageContext.getRequest());
	}
	
	public int getStep() {
		return _step;
	}
	public void setStep(int step) {
		this._step = step;
	}
	
	private int _step = 1;
	
}
