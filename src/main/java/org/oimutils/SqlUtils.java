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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utilities for working with OIM Database Schema Queries
 * 
 * @author WatersJeremy
 * 
 */
public class SqlUtils
{
	private static Log log = LogFactory.getLog(SqlUtils.class);

	private static final String JDBC_OIM = "jdbc/ApplicationDBDS";

	/**
	 * executes the sql query and builds a list of strings with the results. returns the first column only.
	 * 
	 * @param sql
	 * @return a list of strings; an empty list if there are no results or an error occurs
	 */
	public static List<String> queryAsListOfStrings(String sql)
	{
		log.debug("enter queryAsListOfStrings sql=" + sql);

		List<String> listOfStrings = new ArrayList<String>();

		Connection connection = null;
		Statement stmt = null;

		try
		{
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup(JDBC_OIM);
			connection = dataSource.getConnection();

			stmt = connection.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			String selectAttributeValue;

			while (resultSet.next())
			{
				selectAttributeValue = resultSet.getString(1);
				listOfStrings.add(selectAttributeValue);
			}
		}
		catch (Throwable t)
		{
			log.error("error: " + t.getMessage(), t);
		}
		finally
		{
			if ( stmt != null )
			{
				try
				{
					stmt.close();
				}
				catch (SQLException e)
				{
					log.error("error closing stmt " + e.getMessage(), e);
				}
			}
			if ( connection != null )
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					log.error("error closing conn " + e.getMessage(), e);
				}
			}

		}

		return listOfStrings;
	}

}
