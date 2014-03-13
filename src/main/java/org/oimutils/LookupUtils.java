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

import oracle.iam.platform.Platform;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import Thor.API.Operations.tcLookupOperationsIntf;

/**
 * Utilities for working with OIM Lookups
 * 
 * @author watersjeremy
 * 
 */
public class LookupUtils
{
	private static Log log = LogFactory.getLog(LookupUtils.class);

	/**
	 * retrieves a lookup value
	 * 
	 * @param lookupName
	 * @param code
	 * @return the "meaning" or "decode"; or null if not found or an error occurs
	 */
	public static String getLookupValue(String lookupName, String code)
	{
		log.debug("getLookupValue " + lookupName + "," + code);

		tcLookupOperationsIntf lookupOpsIntf = null;
		String value = null;

		try
		{
			lookupOpsIntf = Platform.getService(tcLookupOperationsIntf.class);
			value = lookupOpsIntf.getDecodedValueForEncodedValue(lookupName, code);
		}
		catch (Throwable t)
		{
			log.warn("error looking up " + lookupName + "," + code + ":" + t.getMessage(), t);
		}
		finally
		{
			if ( lookupOpsIntf != null )
			{
				lookupOpsIntf.close();
			}

		}
		log.debug("getLookupValue return " + value);
		return value;
	}
}
