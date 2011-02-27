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
package test.com.agiletec.apsadmin;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.com.agiletec.apsadmin.admin.TestBaseAdminAction;
import test.com.agiletec.apsadmin.admin.TestSystemParamsUtils;
import test.com.agiletec.apsadmin.admin.lang.TestLangAction;
import test.com.agiletec.apsadmin.admin.lang.TestLangFinderAction;
import test.com.agiletec.apsadmin.admin.localestring.TestLocaleStringAction;
import test.com.agiletec.apsadmin.admin.localestring.TestLocaleStringFinderAction;
import test.com.agiletec.apsadmin.category.TestCategoryAction;
import test.com.agiletec.apsadmin.common.TestBaseCommonAction;
import test.com.agiletec.apsadmin.common.TestDispatchForward;
import test.com.agiletec.apsadmin.common.TestLoginAction;
import test.com.agiletec.apsadmin.common.TestShortcutConfigAction;
import test.com.agiletec.apsadmin.portal.TestPageAction;
import test.com.agiletec.apsadmin.portal.TestPageConfigAction;
import test.com.agiletec.apsadmin.portal.TestPageTreeAction;
import test.com.agiletec.apsadmin.portal.TestShowletTypeAction;
import test.com.agiletec.apsadmin.portal.TestShowletsViewerAction;
import test.com.agiletec.apsadmin.portal.specialshowlet.TestSimpleShowletConfigAction;
import test.com.agiletec.apsadmin.portal.specialshowlet.navigator.TestNavigatorShowletConfigAction;
import test.com.agiletec.apsadmin.system.entity.TestEntityManagersAction;
import test.com.agiletec.apsadmin.system.services.TestShortcutManager;
import test.com.agiletec.apsadmin.user.TestAuthorityToUsersAction;
import test.com.agiletec.apsadmin.user.TestUserAction;
import test.com.agiletec.apsadmin.user.TestUserFinderAction;
import test.com.agiletec.apsadmin.user.TestUserToAuthoritiesAction;
import test.com.agiletec.apsadmin.user.group.TestGroupAction;
import test.com.agiletec.apsadmin.user.group.TestGroupFinderAction;
import test.com.agiletec.apsadmin.user.role.TestRoleAction;
import test.com.agiletec.apsadmin.user.role.TestRoleFinderAction;

public class AllTests {
	
    public static Test suite() {
		TestSuite suite = new TestSuite("Test for apsadmin");
		System.out.println("Test for apsadmin");
		
		// Lang
		suite.addTestSuite(TestLangAction.class);
		suite.addTestSuite(TestLangFinderAction.class);
		
		// LocalString
		suite.addTestSuite(TestLocaleStringAction.class);
		suite.addTestSuite(TestLocaleStringFinderAction.class);
		
		suite.addTestSuite(TestBaseAdminAction.class);
		suite.addTestSuite(TestSystemParamsUtils.class);
		
		// Common
		suite.addTestSuite(TestDispatchForward.class);
		suite.addTestSuite(TestLoginAction.class);
		suite.addTestSuite(TestBaseCommonAction.class);
		suite.addTestSuite(TestShortcutConfigAction.class);
		
		//Category
		suite.addTestSuite(TestCategoryAction.class);
		
		// Page
		suite.addTestSuite(TestPageAction.class);
		suite.addTestSuite(TestPageConfigAction.class);
		suite.addTestSuite(TestPageTreeAction.class);
		suite.addTestSuite(TestShowletsViewerAction.class);
		suite.addTestSuite(TestShowletTypeAction.class);
		suite.addTestSuite(TestSimpleShowletConfigAction.class);
		suite.addTestSuite(TestNavigatorShowletConfigAction.class);
		
		//Entity
		suite.addTestSuite(TestEntityManagersAction.class);
		
		//Admin Area Manager
		suite.addTestSuite(TestShortcutManager.class);
		
		//User
		suite.addTestSuite(TestUserAction.class);
		suite.addTestSuite(TestUserFinderAction.class);
		suite.addTestSuite(TestUserToAuthoritiesAction.class);
		suite.addTestSuite(TestAuthorityToUsersAction.class);
		
		//Group
		suite.addTestSuite(TestGroupAction.class);
		suite.addTestSuite(TestGroupFinderAction.class);
		
		//Role
		suite.addTestSuite(TestRoleAction.class);
		suite.addTestSuite(TestRoleFinderAction.class);
		
		return suite;
	}
    
}
