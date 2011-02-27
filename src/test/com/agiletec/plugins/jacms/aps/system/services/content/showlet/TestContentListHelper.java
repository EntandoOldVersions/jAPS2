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
package test.com.agiletec.plugins.jacms.aps.system.services.content.showlet;

import test.com.agiletec.aps.BaseTestCase;

import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.common.entity.model.EntitySearchFilter;
import com.agiletec.aps.system.services.lang.Lang;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.page.IPageManager;
import com.agiletec.aps.system.services.page.Showlet;
import com.agiletec.aps.system.services.showlettype.IShowletTypeManager;
import com.agiletec.aps.system.services.showlettype.ShowletType;
import com.agiletec.aps.util.ApsProperties;
import com.agiletec.aps.util.DateConverter;
import com.agiletec.plugins.jacms.aps.system.JacmsSystemConstants;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.IContentListHelper;

/**
 * @version 1.0
 * @author E.Santoboni
 */
public class TestContentListHelper extends BaseTestCase {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this._helper = (IContentListHelper) this.getApplicationContext().getBean(JacmsSystemConstants.CONTENT_LIST_HELPER);
	}
	
	public void testGetFilters() throws Throwable {
    	String filtersShowletParam = "(key=DataInizio;attributeFilter=true;start=21/10/2007;order=DESC)+(key=Titolo;attributeFilter=true;order=ASC)";
    	EntitySearchFilter[] filters = this._helper.getFilters("EVN", filtersShowletParam, this.getRequestContext());
    	assertEquals(2, filters.length);
    	EntitySearchFilter filter = filters[0];
    	assertEquals("DataInizio", filter.getKey());
    	assertEquals(DateConverter.parseDate("21/10/2007", "dd/MM/yyyy"), filter.getStart());
    	assertNull(filter.getEnd());
    	assertNull(filter.getValue());
    	assertEquals("DESC", filter.getOrder());
    }
	
	public void testGetFilters_OneDefinition() {
		RequestContext reqCtx = this.getRequestContext();
		String contentType = "ART";
		String showletParam = "(key=Titolo;attributeFilter=TRUE;start=START;end=END;like=FALSE;order=ASC)";
		EntitySearchFilter[] filters = this._helper.getFilters(contentType, showletParam, reqCtx);

		assertNotNull(filters);
		assertEquals(1, filters.length);

		EntitySearchFilter entitySearchFilter = filters[0];
		assertNotNull(entitySearchFilter);

		assertEquals("Titolo", entitySearchFilter.getKey());
		assertEquals("START", entitySearchFilter.getStart());
		assertEquals("END", entitySearchFilter.getEnd());
		assertEquals("ASC", entitySearchFilter.getOrder());

		contentType = "ART";
		showletParam = "(key=Titolo;attributeFilter=TRUE;start=START;end=END;like=FALSE;order=DESC)";
		filters = this._helper.getFilters(contentType, showletParam, reqCtx);

		assertNotNull(filters);
		assertEquals(1, filters.length);

		entitySearchFilter = filters[0];
		assertNotNull(entitySearchFilter);

		assertEquals("Titolo", entitySearchFilter.getKey());
		assertEquals("START", entitySearchFilter.getStart());
		assertEquals("END", entitySearchFilter.getEnd());
		assertEquals("DESC", entitySearchFilter.getOrder());
		
		
		contentType = "ART";
		showletParam = "(key=descr;value=VALUE;attributeFilter=FALSE;order=ASC)";
		filters = this._helper.getFilters(contentType, showletParam, reqCtx);

		assertNotNull(filters);
		assertEquals(1, filters.length);

		entitySearchFilter = filters[0];
		assertNotNull(entitySearchFilter);

		assertEquals("descr", entitySearchFilter.getKey());
		assertEquals(null, entitySearchFilter.getStart());
		assertEquals(null, entitySearchFilter.getEnd());
		assertEquals("ASC", entitySearchFilter.getOrder());
	}
	
	public void testGetFilters_TwoDefinition() {
		RequestContext reqCtx = this.getRequestContext();
		String contentType = "ART";
		String showletParam = "(key=Titolo;attributeFilter=TRUE;start=START;end=END;like=FALSE;order=ASC)+(key=descr;value=VALUE;attributeFilter=FALSE;order=ASC)";
		EntitySearchFilter[] filters = this._helper.getFilters(contentType, showletParam, reqCtx);

		assertNotNull(filters);
		assertEquals(2, filters.length);

		EntitySearchFilter entitySearchFilter = filters[0];
		assertNotNull(entitySearchFilter);

		assertEquals("Titolo", entitySearchFilter.getKey());
		assertEquals("START", entitySearchFilter.getStart());
		assertEquals("END", entitySearchFilter.getEnd());
		assertEquals("ASC", entitySearchFilter.getOrder());
		assertEquals(null, entitySearchFilter.getValue());
		assertTrue(entitySearchFilter.isAttributeFilter());
		
		entitySearchFilter = filters[1];
		assertNotNull(entitySearchFilter);

		assertEquals("descr", entitySearchFilter.getKey());
		assertEquals(null, entitySearchFilter.getStart());
		assertEquals(null, entitySearchFilter.getEnd());
		assertEquals("ASC", entitySearchFilter.getOrder());
		assertFalse(entitySearchFilter.isAttributeFilter());
		Object obj = entitySearchFilter.getValue();
		assertNotNull(obj);
		assertEquals(String.class, obj.getClass());
		assertEquals("VALUE", (String)obj);
	}
	
	private IContentListHelper _helper;
	
}