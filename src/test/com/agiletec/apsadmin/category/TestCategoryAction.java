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
package test.com.agiletec.apsadmin.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.com.agiletec.apsadmin.ApsAdminBaseTestCase;

import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.common.tree.ITreeNode;
import com.agiletec.aps.system.services.category.Category;
import com.agiletec.aps.system.services.category.ICategoryManager;
import com.agiletec.apsadmin.category.CategoryAction;
import com.agiletec.apsadmin.system.ApsAdminSystemConstants;
import com.opensymphony.xwork2.Action;

/**
 * @author E.Santoboni - G.Cocco
 */
public class TestCategoryAction extends ApsAdminBaseTestCase {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.init();
	}
	
	public void testNewCategory() throws Throwable {
		this.setUserOnSession("admin");
		this.initAction("/do/Category", "new");
		String result = this.executeAction();
		assertEquals("categoryTree", result);
		CategoryAction action = (CategoryAction) this.getAction();
		assertEquals(1, action.getActionErrors().size());
		
		this.setUserOnSession("admin");
		this.initAction("/do/Category", "new");
		this.addParameter("selectedNode", this._categoryManager.getRoot().getCode());
		result = this.executeAction();
		assertEquals(Action.SUCCESS, result);
		action = (CategoryAction) this.getAction();
		assertEquals(this._categoryManager.getRoot().getCode(), action.getParentCategoryCode());
		assertEquals(0, action.getTitles().size());
		assertEquals(ApsAdminSystemConstants.ADD, action.getStrutsAction());
	}
	
	public void testEditCategory() throws Throwable {
		this.setUserOnSession("admin");
		this.initAction("/do/Category", "edit");
		String result = this.executeAction();
		assertEquals("categoryTree", result);
		CategoryAction action = (CategoryAction) this.getAction();
		assertEquals(1, action.getActionErrors().size());
		
		this.setUserOnSession("admin");
		this.initAction("/do/Category", "edit");
		this.addParameter("selectedNode", "evento");
		result = this.executeAction();
		assertEquals(Action.SUCCESS, result);
		action = (CategoryAction) this.getAction();
		assertEquals(this._categoryManager.getRoot().getCode(), action.getParentCategoryCode());
		assertEquals(2, action.getTitles().size());
		assertEquals(ApsAdminSystemConstants.EDIT, action.getStrutsAction());
	}
	
	public void testViewTree() throws Throwable {
		this.setUserOnSession("pageManagerCoach");
		this.initAction("/do/Category", "viewTree");
		String result = this.executeAction();
		assertEquals("userNotAllowed", result);
		
		this.setUserOnSession("admin");
		this.initAction("/do/Category", "viewTree");
		result = this.executeAction();
		assertEquals(Action.SUCCESS, result);
		CategoryAction action = (CategoryAction) this.getAction();
		ITreeNode root = action.getTreeRootNode();
		assertEquals(this._categoryManager.getRoot().getCode(), root.getCode());
	}
	
	public void testValidateAddCategory_1() throws Throwable {
		Map<String, String> params = new HashMap<String, String>();
		params.put("parentCategoryCode", this._categoryManager.getRoot().getCode());
		params.put("strutsAction", "1");
		params.put("categoryCode", "");
		params.put("langit", "");
		params.put("langen", "");
		String result = this.executeSaveCategory("admin", params);
		assertEquals(Action.INPUT, result);
		Map<String, List<String>> fieldErrors = this.getAction().getFieldErrors();
		assertEquals(3, fieldErrors.size());
		assertEquals(1, fieldErrors.get("categoryCode").size());
		assertEquals(1, fieldErrors.get("langit").size());
		assertEquals(1, fieldErrors.get("langen").size());
	}
	
	public void testValidateAddCategory_2() throws Throwable {
		String categoryCode = "veryLongCategoryCode_veryLongCategoryCode";
		assertNull(this._categoryManager.getCategory(categoryCode));
		Map<String, String> params = new HashMap<String, String>();
		params.put("parentCategoryCode", this._categoryManager.getRoot().getCode());
		params.put("strutsAction", "1");
		params.put("categoryCode", categoryCode);//long category code
		params.put("langit", "Titolo in Italiano");
		params.put("langen", "English Title");
		String result = this.executeSaveCategory("admin", params);
		assertEquals(Action.INPUT, result);
		Map<String, List<String>> fieldErrors = this.getAction().getFieldErrors();
		assertEquals(1, fieldErrors.size());
		assertEquals(1, fieldErrors.get("categoryCode").size());
	}
	
	public void testValidateAddCategory_3() throws Throwable {
		assertNotNull(this._categoryManager.getCategory("evento"));
		Map<String, String> params = new HashMap<String, String>();
		params.put("parentCategoryCode", this._categoryManager.getRoot().getCode());
		params.put("strutsAction", "1");
		params.put("categoryCode", "evento");//duplicate Code
		params.put("langit", "Titolo categoria");
		params.put("langen", "English Title");
		String result = this.executeSaveCategory("admin", params);
		assertEquals(Action.INPUT, result);
		Map<String, List<String>> fieldErrors = this.getAction().getFieldErrors();
		assertEquals(1, fieldErrors.size());
		assertEquals(1, fieldErrors.get("categoryCode").size());
	}
	
	public void testValidateAddCategory_4() throws Throwable {
		String categoryCode = "cat_temp2";
		assertNull(this._categoryManager.getCategory(categoryCode));
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("parentCategoryCode", this._categoryManager.getRoot().getCode());
			params.put("strutsAction", "1");
			params.put("categoryCode", categoryCode);
			params.put("langit", "Titolo categoria seconda");
			params.put("langen", "");//empty English title field
			String result = this.executeSaveCategory("admin", params);
			assertEquals(Action.INPUT, result);
			Map<String, List<String>> fieldErrors = this.getAction().getFieldErrors();
			assertEquals(1, fieldErrors.size());
			assertEquals(1, fieldErrors.get("langen").size());
		} catch (Throwable t) {
			this._categoryManager.deleteCategory(categoryCode);
			assertNotNull(this._categoryManager.getCategory(categoryCode));
			throw t;
		}
	}
	
	public void testAddCategory() throws Throwable {
		String categoryCode = "cat_temp";
		assertNull(this._categoryManager.getCategory(categoryCode));
		try {
			String result = this.saveNewCategory("admin", categoryCode);
			assertEquals(Action.SUCCESS, result);
			
			Category category = this._categoryManager.getCategory(categoryCode);
			assertNotNull(category);
			assertEquals("Titolo categoria In Italiano", category.getTitles().getProperty("it"));
			assertEquals(this._categoryManager.getRoot().getCode(), category.getParent().getCode());
		} catch (Throwable t) {
			throw t;
		} finally {
			this._categoryManager.deleteCategory(categoryCode);
			assertNull(this._categoryManager.getCategory(categoryCode));
		}
	}
	
	public void testTrashCategory() throws Throwable {
		String categoryCode = "cat_temp";
		assertNull(this._categoryManager.getCategory(categoryCode));
		try {
			String result = this.saveNewCategory("admin", categoryCode);
			assertEquals(Action.SUCCESS, result);
			Category category = this._categoryManager.getCategory(categoryCode);
			assertNotNull(category);
			
			this.initAction("/do/Category", "trash");
			this.addParameter("selectedNode", categoryCode);
			result = this.executeAction();
			assertEquals(Action.SUCCESS, result);
			category = this._categoryManager.getCategory(categoryCode);
			assertNotNull(category);
		} catch (Throwable t) {
			throw t;
		} finally {			
			this._categoryManager.deleteCategory(categoryCode);
			assertNull(this._categoryManager.getCategory(categoryCode));
		}
	}
	
	
	public void testDeleteCategory() throws Throwable {
		String categoryCode = "cat_temp";
		assertNull(this._categoryManager.getCategory(categoryCode));
		try {
			String result = this.saveNewCategory("admin", categoryCode);
			assertEquals(Action.SUCCESS, result);
			Category category = this._categoryManager.getCategory(categoryCode);
			assertNotNull(category);
			
			this.initAction("/do/Category", "trash");
			this.addParameter("selectedNode", categoryCode);
			result = this.executeAction();
			assertEquals(Action.SUCCESS, result);
			category = this._categoryManager.getCategory(categoryCode);
			assertNotNull(category);
			
			this.initAction("/do/Category", "delete");
			this.addParameter("selectedNode", categoryCode);
			result = this.executeAction();
			assertEquals(Action.SUCCESS, result);
			category = this._categoryManager.getCategory(categoryCode);
			assertNull(category);
		} catch (Throwable t) {
			this._categoryManager.deleteCategory(categoryCode);
			assertNull(this._categoryManager.getCategory(categoryCode));
			throw t;
		}
	}
	
	
	private String saveNewCategory(String userName, String categoryCode) throws Throwable {
		Map<String, String> params = new HashMap<String, String>();
		params.put("parentCategoryCode", this._categoryManager.getRoot().getCode());
		params.put("strutsAction", "1");
		params.put("categoryCode", categoryCode);
		params.put("langit", "Titolo categoria In Italiano");
		params.put("langen", "Titolo categoria In Inglese");
		String result = this.executeSaveCategory(userName, params);
		return result;
	}
	
	private String executeSaveCategory(String userName, Map<String, String> params) throws Throwable {
		this.setUserOnSession(userName);
		this.initAction("/do/Category", "save");
		this.addParameters(params);
		String result = this.executeAction();
		return result;
	}
	
	private void init() throws Exception {
		this._categoryManager = (ICategoryManager) this.getService(SystemConstants.CATEGORY_MANAGER);
	}
	
	private ICategoryManager _categoryManager;
	
}