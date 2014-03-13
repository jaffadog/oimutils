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

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utilities for working with OIM Passwords
 * 
 * @author WatersJeremy
 * 
 */
public class PasswordUtils
{
	private static Log log = LogFactory.getLog(PasswordUtils.class);

	/**
	 * Generates a random alphanumeric password that contains at least 1 upper, 1 lower, 1 digit; and excludes ambiguous
	 * characters (e.g. 0/O, I/1/l)
	 * 
	 * @return password
	 */
	public static String generatePassword()
	{
		int passwordLength = 8;
		String password = null;

		char[] password2 = new char[passwordLength];

		// with ambiguous characters removed (e.g. 0/O, I/1/l)
		String upper = "ABCDEFGHJKLMNPQRSTUVWXYZ";
		String lower = "abcdefghijkmnopqrstuvwxyz";
		String digit = "23456789";

		// set 1 of each upper/lower/digit
		fillPasswordChar(passwordLength, password2, upper);
		fillPasswordChar(passwordLength, password2, lower);
		fillPasswordChar(passwordLength, password2, digit);

		// fill the remainder of the blanks in the password
		for (int i = 0; i < passwordLength; i++)
		{
			if ( password2[i] == '\0' )
			{
				password2[i] = getRandomChar(upper + lower + digit);
			}
		}

		log.info(new String(password2));

		return password;
	}

	/**
	 * fills one random character within the password with a random character chosen from the input set
	 * 
	 * @param passwordLength
	 * @param password2
	 * @param upper
	 */
	private static void fillPasswordChar(int passwordLength, char[] password2, String string)
	{
		int pos;
		do
		{
			pos = (int) (Math.random() * passwordLength);
		}
		while (password2[pos] != '\0');

		password2[pos] = getRandomChar(string);
	}

	/**
	 * gets a random character from the input set
	 * 
	 * @param string
	 * @return
	 */
	private static char getRandomChar(String string)
	{
		int pos = (int) (Math.random() * StringUtils.length(string));
		return CharUtils.toChar(StringUtils.substring(string, pos, pos + 1));
	}
}
