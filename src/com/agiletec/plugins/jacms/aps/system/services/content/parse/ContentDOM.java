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
package com.agiletec.plugins.jacms.aps.system.services.content.parse;

import org.jdom.Element;

import com.agiletec.aps.system.common.entity.parse.ApsEntityDOM;

/**
 * Classe JDOM per la scrittura di un oggetto tipo Content in xml.
 * @author M.Morini - S.Didaci - E.Santoboni
 */
public class ContentDOM extends ApsEntityDOM {
	
	/**
	 * Setta lo stato del contenuto.
	 * @param status Lo stato del contenuto.
	 */
	public void setStatus(String status){
		if (this._root.getChild(TAG_STATUS) == null) {
			Element tag = new Element(TAG_STATUS);
			this._root.addContent(tag);
		}
		this._root.getChild(TAG_STATUS).setText(status);
	}
	
	public void setVersion(String version) {
		if (this._root.getChild(TAG_VERSION) == null) {
			Element tag = new Element(TAG_VERSION);
			this._root.addContent(tag);
		}
		this._root.getChild(TAG_VERSION).setText(version);
	}
	
	private final static String TAG_STATUS = "status";
	private final static String TAG_VERSION = "version";
	
}