/*
 * Copyright 2014 Jeremy Waters.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.oimutils;

import java.util.HashSet;
import java.util.Set;

import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.platform.Platform;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utilities for working with OIM Users
 * 
 * @author watersjeremy
 * 
 */
public class UserUtils
{
	private static Log log = LogFactory.getLog(UserUtils.class);
	
	/**
	 * @param userID
	 *            usr_login or usr_key
	 * @param attributeName
	 *            the name of the attr to get
	 * @param isUserLogin
	 *            true is userID is usr_login
	 * @return attributeValue
	 */
	public static String getOimUsrAttribute(String userID, String attributeName, boolean isUserLogin)
	{
		log.debug("enter getOimUsrAttribute " + userID + ", " + attributeName);

		if ( StringUtils.isBlank(userID) || StringUtils.isBlank(attributeName) )
		{
			log.warn("missing parameters");
			return null;
		}

		String attributeValue = null;

		try
		{
			UserManager userManager = Platform.getService(UserManager.class);
			Set<String> attrNames = new HashSet<String>();
			attrNames.add(attributeName);
			User user = userManager.getDetails(userID, attrNames, isUserLogin);
			attributeValue = (String) user.getAttribute(attributeName);
		}
		catch (Throwable t)
		{
			log.error(t.getMessage(), t);
		}

		log.debug("attributeValue=" + attributeValue);

		return attributeValue;
	}
}
