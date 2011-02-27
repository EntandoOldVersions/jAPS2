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

package test.com.agiletec.aps.util;

import test.com.agiletec.aps.BaseTestCase;

import com.agiletec.aps.util.DefaultApsEncrypter;


public class TestDefaultApsEncrypter extends BaseTestCase {

	/**
	 * We test the decryption method too, even if the implementation is not mandatory
	 * @throws Throwable
	 */
	public void testEncodeDecode() throws Throwable {
		String testString = "PointerOfBaseAction"; 
		String encoded = null;
		try {
			encoded = DefaultApsEncrypter.encryptString(testString);
			assertTrue(!testString.equals(encoded));
			assertEquals(testString, DefaultApsEncrypter.decrypt(encoded));
		} catch (Throwable t) {
			throw t;
		}
	}

}
