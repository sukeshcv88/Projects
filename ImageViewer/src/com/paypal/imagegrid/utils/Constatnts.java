package com.paypal.imagegrid.utils;

public class Constatnts {

	public static final String LOCAL_PREF = "local";
	public static final String SOCIAL_PREF = "social";
	
	public static final String CLIENT_ID = "0cb15a3a2e364fe4888defd227a77c36";
	public static final String CLIENT_SECRET = "3d8cf4ab3fc8456cb9d6ee78e7b7de48";
	public static final String WEBSITE_URL	= "http://www.ensoftek.com";
	public static final String REDIRECT_URI	= "http://localhost";

	private static final String AUTHURL = "https://api.instagram.com/oauth/authorize/";
	private static final String TOKENURL = "https://api.instagram.com/oauth/access_token";
	public static final String APIURL = "https://api.instagram.com/v1";
	public static String CALLBACKURL = "http://localhost";

	public static final String AUTH_URL_STRING = AUTHURL + "?client_id=" + CLIENT_ID + "&redirect_uri=" + CALLBACKURL + "&response_type=code&display=touch&scope=likes+comments+relationships";
	public static final String TOKEN_URL_STRING = TOKENURL + "?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&redirect_uri=" + CALLBACKURL + "&grant_type=authorization_code";
}
