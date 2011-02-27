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
package com.agiletec.plugins.jacms.aps.system.services.resource.model.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.baseconfig.ConfigInterface;
import com.agiletec.plugins.jacms.aps.system.services.resource.model.ResourceDataBean;
import com.agiletec.plugins.jacms.aps.system.services.resource.model.ResourceInterface;

/**
 * Classe Helper per la gestione dei file relativi alle istanze delle risorse.
 * @author E.Santoboni
 */
public class ResourceInstanceFileHelper implements IResourceInstanceHelper {
	
	@Override
	public void save(String filePath, ResourceDataBean bean) throws ApsSystemException {
    	try {
    		InputStream stream = bean.getInputStream();
    		FileOutputStream outStream = new FileOutputStream(filePath);
    		while (stream.available() > 0) {
    			outStream.write(stream.read());
    		}
    		outStream.close();
    		stream.close();
    	} catch (Throwable t) {
    		ApsSystemUtils.logThrowable(t, this, "save");
    		throw new ApsSystemException("Error on saving file", t);
    	}
    }
    
	@Override
	public String getFileExtension(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf('.')+1).trim();
		return extension;
	}
	
	@Override
	public String getResourceDiskFolder(ResourceInterface resource) {
		String resDiskFolder = resource.getDiskFolder();
		File dir = new File(resDiskFolder);
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}
		return resDiskFolder;
	}
	
	protected ConfigInterface getConfigManager() {
		return _configManager;
	}
	public void setConfigManager(ConfigInterface configService) {
		this._configManager = configService;
	}
    
	private ConfigInterface _configManager;
	
}
