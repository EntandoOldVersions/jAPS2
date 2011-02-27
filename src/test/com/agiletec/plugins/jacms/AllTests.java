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
package test.com.agiletec.plugins.jacms;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.com.agiletec.plugins.jacms.aps.system.TestApplicationContext;
import test.com.agiletec.plugins.jacms.aps.system.services.content.TestCategoryUtilizer;
import test.com.agiletec.plugins.jacms.aps.system.services.content.TestContentDAO;
import test.com.agiletec.plugins.jacms.aps.system.services.content.TestContentManager;
import test.com.agiletec.plugins.jacms.aps.system.services.content.TestContentSearcherDAO;
import test.com.agiletec.plugins.jacms.aps.system.services.content.TestGroupUtilizer;
import test.com.agiletec.plugins.jacms.aps.system.services.content.TestPublicContentSearcherDAO;
import test.com.agiletec.plugins.jacms.aps.system.services.content.authorization.TestContentAuthorization;
import test.com.agiletec.plugins.jacms.aps.system.services.content.entity.TestContentEntityManager;
import test.com.agiletec.plugins.jacms.aps.system.services.content.parse.TestContentDOM;
import test.com.agiletec.plugins.jacms.aps.system.services.content.showlet.TestContentListHelper;
import test.com.agiletec.plugins.jacms.aps.system.services.content.showlet.TestContentViewerHelper;
import test.com.agiletec.plugins.jacms.aps.system.services.content.util.TestContentAttributeIterator;
import test.com.agiletec.plugins.jacms.aps.system.services.contentmodel.TestContentModelDAO;
import test.com.agiletec.plugins.jacms.aps.system.services.contentmodel.TestContentModelManager;
import test.com.agiletec.plugins.jacms.aps.system.services.contentpagemapper.TestContentPageMapperManager;
import test.com.agiletec.plugins.jacms.aps.system.services.dispenser.TestContentDispenser;
import test.com.agiletec.plugins.jacms.aps.system.services.linkresolver.TestLinkResolverManager;
import test.com.agiletec.plugins.jacms.aps.system.services.page.TestCmsPageDAO;
import test.com.agiletec.plugins.jacms.aps.system.services.resource.TestResourceDAO;
import test.com.agiletec.plugins.jacms.aps.system.services.resource.TestResourceManager;
import test.com.agiletec.plugins.jacms.aps.system.services.resource.parse.TestResourceDOM;
import test.com.agiletec.plugins.jacms.aps.system.services.searchengine.TestSearchEngineManager;
import test.com.agiletec.plugins.jacms.apsadmin.category.TestTrashReferencedCategory;
import test.com.agiletec.plugins.jacms.apsadmin.content.TestContentAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.TestContentAdminAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.TestContentFinderAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.TestContentGroupAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.TestContentInspectionAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.TestContentPreviewAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.TestIntroNewContentAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.attribute.TestContentLinkAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.attribute.TestExtendedResourceAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.attribute.TestExtendedResourceFinderAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.attribute.TestHypertextAttributeAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.attribute.TestLinkAttributeAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.attribute.TestListAttributeAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.attribute.TestPageLinkAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.attribute.TestResourceAttributeAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.attribute.TestUrlLinkAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.model.TestContentModelAction;
import test.com.agiletec.plugins.jacms.apsadmin.content.model.TestContentModelFinderAction;
import test.com.agiletec.plugins.jacms.apsadmin.portal.TestPageAction;
import test.com.agiletec.plugins.jacms.apsadmin.portal.TestTrashReferencedPage;
import test.com.agiletec.plugins.jacms.apsadmin.portal.specialshowlet.listviewer.TestBaseFilterAction;
import test.com.agiletec.plugins.jacms.apsadmin.portal.specialshowlet.listviewer.TestContentListViewerShowletAction;
import test.com.agiletec.plugins.jacms.apsadmin.portal.specialshowlet.listviewer.TestDateAttributeFilterAction;
import test.com.agiletec.plugins.jacms.apsadmin.portal.specialshowlet.listviewer.TestNumberAttributeFilterAction;
import test.com.agiletec.plugins.jacms.apsadmin.portal.specialshowlet.listviewer.TestTextAttributeFilterAction;
import test.com.agiletec.plugins.jacms.apsadmin.portal.specialshowlet.viewer.TestContentFinderViewerAction;
import test.com.agiletec.plugins.jacms.apsadmin.portal.specialshowlet.viewer.TestContentViewerShowletAction;
import test.com.agiletec.plugins.jacms.apsadmin.resource.TestResourceAction;
import test.com.agiletec.plugins.jacms.apsadmin.resource.TestResourceFinderAction;
import test.com.agiletec.plugins.jacms.apsadmin.system.entity.TestJacmsEntityAttributeConfigAction;
import test.com.agiletec.plugins.jacms.apsadmin.system.entity.TestJacmsEntityManagersAction;
import test.com.agiletec.plugins.jacms.apsadmin.system.entity.TestJacmsEntityTypeConfigAction;
import test.com.agiletec.plugins.jacms.apsadmin.user.group.TestTrashReferencedGroup;

public class AllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for jACMS");
		
		System.out.println("Test for jACMS plugin");
		
		// 
		suite.addTestSuite(TestContentAuthorization.class);
		suite.addTestSuite(TestContentEntityManager.class);
		suite.addTestSuite(TestContentDOM.class);
		suite.addTestSuite(TestContentListHelper.class);
		suite.addTestSuite(TestContentViewerHelper.class);
		suite.addTestSuite(TestContentAttributeIterator.class);
		suite.addTestSuite(TestContentDAO.class);
		suite.addTestSuite(TestContentManager.class);
		suite.addTestSuite(TestContentSearcherDAO.class);
		suite.addTestSuite(TestPublicContentSearcherDAO.class);
		//
		suite.addTestSuite(TestContentModelDAO.class);
		suite.addTestSuite(TestContentModelManager.class);
		//
		suite.addTestSuite(TestContentPageMapperManager.class);
		//
		suite.addTestSuite(TestContentDispenser.class);
		//
		suite.addTestSuite(TestLinkResolverManager.class);
		//
		suite.addTestSuite(TestCmsPageDAO.class);
		//
		suite.addTestSuite(TestResourceDOM.class);
		suite.addTestSuite(TestResourceDAO.class);
		suite.addTestSuite(TestResourceManager.class);
		//
		suite.addTestSuite(TestSearchEngineManager.class);
		suite.addTestSuite(TestApplicationContext.class);
		
		// Test cross utilizers
		suite.addTestSuite(TestCategoryUtilizer.class);
		suite.addTestSuite(TestGroupUtilizer.class);
		suite.addTestSuite(TestTrashReferencedCategory.class);
		
		// Content
		suite.addTestSuite(TestListAttributeAction.class);
		suite.addTestSuite(TestResourceAttributeAction.class);
		suite.addTestSuite(TestExtendedResourceAction.class);
		suite.addTestSuite(TestExtendedResourceFinderAction.class);
		suite.addTestSuite(TestHypertextAttributeAction.class);
		suite.addTestSuite(TestLinkAttributeAction.class);
		suite.addTestSuite(TestPageLinkAction.class);
		suite.addTestSuite(TestContentLinkAction.class);
		suite.addTestSuite(TestUrlLinkAction.class);
		suite.addTestSuite(TestContentModelAction.class);
		suite.addTestSuite(TestContentModelFinderAction.class);
		suite.addTestSuite(TestContentAction.class);
		suite.addTestSuite(TestContentAdminAction.class);
		suite.addTestSuite(TestContentFinderAction.class);
		suite.addTestSuite(TestContentGroupAction.class);
		suite.addTestSuite(TestContentInspectionAction.class);
		suite.addTestSuite(TestContentPreviewAction.class);
		suite.addTestSuite(TestIntroNewContentAction.class);
		
		// Page
		suite.addTestSuite(TestContentListViewerShowletAction.class);
		suite.addTestSuite(TestBaseFilterAction.class);
		suite.addTestSuite(TestDateAttributeFilterAction.class);
		suite.addTestSuite(TestNumberAttributeFilterAction.class);
		suite.addTestSuite(TestTextAttributeFilterAction.class);
		suite.addTestSuite(TestContentFinderViewerAction.class);
		suite.addTestSuite(TestContentViewerShowletAction.class);
		suite.addTestSuite(TestPageAction.class);
		suite.addTestSuite(TestTrashReferencedPage.class);
		
		//Resource
		suite.addTestSuite(TestResourceAction.class);
		suite.addTestSuite(TestResourceFinderAction.class);
		
		//Entity
		suite.addTestSuite(TestJacmsEntityAttributeConfigAction.class);
		suite.addTestSuite(TestJacmsEntityTypeConfigAction.class);
		suite.addTestSuite(TestJacmsEntityManagersAction.class);
		
		//Group
		suite.addTestSuite(TestTrashReferencedGroup.class);
		
		return suite;
	}

}
