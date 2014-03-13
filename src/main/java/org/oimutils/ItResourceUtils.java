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

import java.util.HashMap;

import oracle.iam.platform.Platform;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import Thor.API.tcResultSet;
import Thor.API.Operations.tcITResourceInstanceOperationsIntf;

/**
 * Utilities for working with OIM IT Resources
 * 
 * @author WatersJeremy 
 *
 */
public class ItResourceUtils
{
	private static Log log = LogFactory.getLog(ItResourceUtils.class);

	/**
	 * @param itResourceName
	 * @return a hashmap containing the IT resource properties
	 */
	public static HashMap<String, String> getItResourceProperties(String itResourceName)
	{
		log.debug("enter getItResourceProperties " + itResourceName);

		HashMap<String, String> itResourceProperties = new HashMap<String, String>();
		tcITResourceInstanceOperationsIntf itResOpsIntf = null;

		try
		{
			itResOpsIntf = Platform.getService(tcITResourceInstanceOperationsIntf.class);

			HashMap<String, String> itResSearchAttrs = new HashMap<String, String>();
			itResSearchAttrs.put("IT Resources.Name", itResourceName);

			tcResultSet iTResourceInstances = itResOpsIntf.findITResourceInstances(itResSearchAttrs);
			Long iTResourceKey = iTResourceInstances.getLongValue("IT Resource.Key");

			tcResultSet parameters = itResOpsIntf.getITResourceInstanceParameters(iTResourceKey);

			for (int i = 0; i < parameters.getRowCount(); i++)
			{
				parameters.goToRow(i);

				String paramName = StringUtils.trim(parameters.getStringValue("IT Resources Type Parameter.Name"));
				String paramValue = StringUtils.trim(parameters.getStringValue("IT Resource.Parameter.Value"));

				itResourceProperties.put(paramName, paramValue);
			}
		}
		catch (Throwable t)
		{
			log.error(t.getMessage(), t);
			throw new RuntimeException(t.getMessage(), t);
		}
		finally
		{
			if ( itResOpsIntf != null )
			{
				itResOpsIntf.close();
			}
		}

		return itResourceProperties;
	}

	/**
	 * @param itResourceName
	 * @param propertyName
	 * @param propertyValue
	 */
	public static void setItResourceProperty(String itResourceName, String propertyName, String propertyValue)
	{
		log.debug("enter setItResourceProperty " + itResourceName + ", " + propertyName + ", " + propertyValue);

		tcITResourceInstanceOperationsIntf itResOpsIntf = null;

		try
		{
			itResOpsIntf = Platform.getService(tcITResourceInstanceOperationsIntf.class);

			HashMap<String, String> itResSearchAttrs = new HashMap<String, String>();
			itResSearchAttrs.put("IT Resources.Name", itResourceName);

			tcResultSet iTResourceInstances = itResOpsIntf.findITResourceInstances(itResSearchAttrs);
			Long iTResourceKey = iTResourceInstances.getLongValue("IT Resource.Key");

			HashMap<String, String> iTResourceProperties = new HashMap<String, String>();
			iTResourceProperties.put(propertyName, propertyValue);

			itResOpsIntf.updateITResourceInstance(iTResourceKey, iTResourceProperties);
		}
		catch (Throwable t)
		{
			log.error(t.getMessage(), t);
			throw new RuntimeException(t.getMessage(), t);
		}
		finally
		{
			if ( itResOpsIntf != null )
			{
				itResOpsIntf.close();
			}
		}
	}

}
