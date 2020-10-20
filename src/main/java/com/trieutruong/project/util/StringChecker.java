package com.trieutruong.project.util;

import java.util.regex.Pattern;

public class StringChecker {
	private static final String numRegex = ".*[0-9].*";
	private static final String letterRegex = ".*[a-zA-Z].*";
	private static final String lowerRegex = ".*[a-z].*";
	private static final String upperRegex = ".*[A-Z].*";
	private static final String specialCharRegex = ".*[^A-Za-z0-9].*";
	
	public static boolean hasNumber(String s)
	{
		Pattern pattern = Pattern.compile(numRegex);
		return pattern.matcher(s).matches();
	}
	
	public static boolean hasLower(String s)
	{
		Pattern pattern = Pattern.compile(lowerRegex);
		return pattern.matcher(s).matches();
	}
	
	public static boolean hasUpper(String s)
	{
		Pattern pattern = Pattern.compile(upperRegex);
		return pattern.matcher(s).matches();
	}
	
	public static boolean hasLetter(String s)
	{
		Pattern pattern = Pattern.compile(letterRegex);
		return pattern.matcher(s).matches();
	}
	
	public static boolean hasSpecialChar(String s)
	{
		Pattern pattern = Pattern.compile(specialCharRegex);
		return pattern.matcher(s).matches();
	}
	
}
