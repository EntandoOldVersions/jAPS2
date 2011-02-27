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
package test.com.agiletec.plugins.jacms.apsadmin.category;

import java.util.List;
import java.util.Map;

import test.com.agiletec.apsadmin.ApsAdminBaseTestCase;

import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.services.category.Category;
import com.agiletec.aps.system.services.category.ICategoryManager;
import com.agiletec.apsadmin.category.CategoryAction;
import com.agiletec.plugins.jacms.aps.system.JacmsSystemConstants;
import com.agiletec.plugins.jacms.aps.system.services.content.model.ContentRecordVO;

/**
 * @author E.Santoboni
 */
public class TestTrashReferencedCategory extends ApsAdminBaseTestCase {
	
	protected void setUp() throws Exception {
		super.setUp();
		this.init();
	}
	
	public void testTrashReferencedCategory() throws Throwable {
		String categoryCode = "evento";
		Category masterCategory = this._categoryManager.getCategory(categoryCode);
		assertNotNull(masterCategory);
		try {
			this.setUserOnSession("admin");
			this.initAction("/do/Category", "trash");
			this.addParameter("selectedNode", categoryCode);
			String result = this.executeAction();
			assertEquals("references", result);
			
			CategoryAction action = (CategoryAction) this.getAction();
			Map<String, List> references = action.getReferences();
			assertEquals(1, references.size());
			
			List contentReferences = references.get(JacmsSystemConstants.CONTENT_MANAGER+"Utilizers");
			assertEquals(2, contentReferences.size());
			for (int i=0; i<contentReferences.size(); i++) {
				ContentRecordVO content = (ContentRecordVO) contentReferences.get(i);
				assertTrue(content.getId().equals("EVN193") || content.getId().equals("EVN192"));
			}
		} catch (Throwable t) {
			throw t;
		}
	}
	
	private void init() throws Exception {
		this._categoryManager = (ICategoryManager) this.getService(SystemConstants.CATEGORY_MANAGER);
	}
	
	private ICategoryManager _categoryManager;

}
