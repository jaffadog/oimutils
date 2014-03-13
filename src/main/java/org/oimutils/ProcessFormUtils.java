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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import Thor.API.Operations.tcFormInstanceOperationsIntf;

/**
 * @author WatersJeremy
 * 
 */
public class ProcessFormUtils
{
	private static Log log = LogFactory.getLog(ProcessFormUtils.class);

	/**
	 * @param processInstanceKey
	 * @param properties
	 */
	public static void updateProcessForm(String processInstanceKey, HashMap<String, String> properties)
	{
		log.debug("enter updateProcessForm " + processInstanceKey);
		tcFormInstanceOperationsIntf formOpsIntf = null;

		try
		{
			formOpsIntf = Platform.getService(tcFormInstanceOperationsIntf.class);
			formOpsIntf.setProcessFormData(Long.parseLong(processInstanceKey), properties);
		}
		catch (Throwable t)
		{
			log.error(t.getMessage(), t);
		}
		finally
		{
			if ( formOpsIntf != null )
			{
				formOpsIntf.close();
			}
		}
	}

	/**
	 * @param processInstanceKey
	 * @param attributeName
	 * @param attributeValue
	 */
	public static void updateProcessForm(String processInstanceKey, String attributeName, String attributeValue)
	{
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put(attributeName, attributeValue);
		updateProcessForm(processInstanceKey, properties);
	}

}
