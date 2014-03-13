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
import oracle.iam.scheduler.api.SchedulerService;
import oracle.iam.scheduler.vo.JobDetails;
import oracle.iam.scheduler.vo.JobParameter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utilities for working with OIM Scheduled Jobs
 * 
 * @author WatersJeremy
 * 
 */
public class JobUtils
{
	private static Log log = LogFactory.getLog(JobUtils.class);

	/**
	 * @param jobName
	 * @param parameterName
	 * @param parameterValue
	 */
	public static void setJobParameter(String jobName, String parameterName, String parameterValue)
	{
		log.debug("enter setJobParameter " + jobName + ", " + parameterName + ", " + parameterValue);

		try
		{
			SchedulerService schedulerService = Platform.getService(SchedulerService.class);
			JobDetails jobDetails = schedulerService.getJobDetail(jobName);
			HashMap<String, JobParameter> jobParameters = jobDetails.getParams();
			JobParameter jobParameter = jobParameters.get(parameterName);
			jobParameter.setValue(parameterValue);
			jobParameters.put(parameterName, jobParameter);
			jobDetails.setParams(jobParameters);
			schedulerService.updateJob(jobDetails);
		}
		catch (Throwable t)
		{
			log.error(t.getMessage(), t);
			throw new RuntimeException(t.getMessage(), t);
		}
	}
}
