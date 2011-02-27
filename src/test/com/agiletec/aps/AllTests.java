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

/**
 * 
 * @author W.Ambu
 */
package test.com.agiletec.aps;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.com.agiletec.aps.system.TestApplicationContext;
import test.com.agiletec.aps.system.common.entity.TestEntityManager;
import test.com.agiletec.aps.system.services.authorization.TestAuthorityManager;
import test.com.agiletec.aps.system.services.authorization.TestAuthorizationManager;
import test.com.agiletec.aps.system.services.baseconfig.TestBaseConfigService;
import test.com.agiletec.aps.system.services.baseconfig.TestConfigItemDAO;
import test.com.agiletec.aps.system.services.cache.TestCacheManager;
import test.com.agiletec.aps.system.services.category.TestCategoryManager;
import test.com.agiletec.aps.system.services.controller.TestControllerManager;
import test.com.agiletec.aps.system.services.controller.control.TestAuthenticator;
import test.com.agiletec.aps.system.services.controller.control.TestErrorManager;
import test.com.agiletec.aps.system.services.controller.control.TestExecutor;
import test.com.agiletec.aps.system.services.controller.control.TestRequestAuthorizator;
import test.com.agiletec.aps.system.services.controller.control.TestRequestValidator;
import test.com.agiletec.aps.system.services.group.TestGroupManager;
import test.com.agiletec.aps.system.services.group.TestGroupUtilizer;
import test.com.agiletec.aps.system.services.i18n.TestI18nManager;
import test.com.agiletec.aps.system.services.keygenerator.TestKeyGeneratorDAO;
import test.com.agiletec.aps.system.services.keygenerator.TestKeyGeneratorManager;
import test.com.agiletec.aps.system.services.lang.TestLangManager;
import test.com.agiletec.aps.system.services.page.TestPageDAO;
import test.com.agiletec.aps.system.services.page.TestPageManager;
import test.com.agiletec.aps.system.services.page.showlet.TestNavigatorExpression;
import test.com.agiletec.aps.system.services.page.showlet.TestNavigatorParser;
import test.com.agiletec.aps.system.services.pagemodel.TestPageModelDAO;
import test.com.agiletec.aps.system.services.pagemodel.TestPageModelDOM;
import test.com.agiletec.aps.system.services.pagemodel.TestPageModelManager;
import test.com.agiletec.aps.system.services.role.TestPermissionDAO;
import test.com.agiletec.aps.system.services.role.TestRoleDAO;
import test.com.agiletec.aps.system.services.role.TestRoleManager;
import test.com.agiletec.aps.system.services.showlettype.TestShowletTypeDAO;
import test.com.agiletec.aps.system.services.showlettype.TestShowletTypeDOM;
import test.com.agiletec.aps.system.services.showlettype.TestShowletTypeManager;
import test.com.agiletec.aps.system.services.url.TestURLManager;
import test.com.agiletec.aps.system.services.user.TestAuthenticationProviderManager;
import test.com.agiletec.aps.system.services.user.TestUserDAO;
import test.com.agiletec.aps.system.services.user.TestUserManager;
import test.com.agiletec.aps.util.TestHtmlHandler;

public class AllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for APS");
		
		//
		suite.addTestSuite(TestEntityManager.class);
		//
		suite.addTestSuite(TestAuthorizationManager.class);
		suite.addTestSuite(TestAuthorityManager.class);
		//
		suite.addTestSuite(TestBaseConfigService.class);
		suite.addTestSuite(TestConfigItemDAO.class);
		//
		suite.addTestSuite(TestCacheManager.class);
		//
		suite.addTestSuite(TestCategoryManager.class);
		//
		suite.addTestSuite(TestAuthenticator.class);
		suite.addTestSuite(TestRequestAuthorizator.class);
		suite.addTestSuite(TestErrorManager.class);
		suite.addTestSuite(TestExecutor.class);
		suite.addTestSuite(TestRequestValidator.class);
		suite.addTestSuite(TestControllerManager.class);
		//
		suite.addTestSuite(TestGroupManager.class);
		suite.addTestSuite(TestGroupUtilizer.class);
		//
		suite.addTestSuite(TestI18nManager.class);
		//
		suite.addTestSuite(TestKeyGeneratorDAO.class);
		suite.addTestSuite(TestKeyGeneratorManager.class);
		//
		suite.addTestSuite(TestLangManager.class);
		//
		suite.addTestSuite(TestPageDAO.class);
		suite.addTestSuite(TestPageManager.class);
		suite.addTestSuite(TestNavigatorExpression.class);
		suite.addTestSuite(TestNavigatorParser.class);
		//
		suite.addTestSuite(TestPageModelDAO.class);
		suite.addTestSuite(TestPageModelDOM.class);
		suite.addTestSuite(TestPageModelManager.class);
		//
		suite.addTestSuite(TestPermissionDAO.class);
		suite.addTestSuite(TestRoleDAO.class);
		suite.addTestSuite(TestRoleManager.class);
		
		//
		suite.addTestSuite(TestShowletTypeDAO.class);
		suite.addTestSuite(TestShowletTypeDOM.class);
		suite.addTestSuite(TestShowletTypeManager.class);
		//
		suite.addTestSuite(TestURLManager.class);
		//
		suite.addTestSuite(TestAuthenticationProviderManager.class);
		suite.addTestSuite(TestUserDAO.class);
		suite.addTestSuite(TestUserManager.class);
		//
		suite.addTestSuite(TestApplicationContext.class);	
		//
		suite.addTestSuite(TestHtmlHandler.class);
		
		return suite;
	}
}
