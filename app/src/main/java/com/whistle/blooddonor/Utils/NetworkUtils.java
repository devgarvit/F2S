package com.whistle.blooddonor.Utils;

public class NetworkUtils {

	public static final String BASE_URL				= "http://whistle-dev.herokuapp.com";

	public static final String CALL_URL				= BASE_URL + "/api";

	public static final String USER_URL				= CALL_URL + "/user";

	public static final String LOGIN_URL				= USER_URL + "/login";

	public static final String WHISTLERLIST_URL		= CALL_URL + "/whistlerlist";

	public static final String WHISTLE_URL				= CALL_URL + "/whistle";

	public static final String WHISTLE_URL_AUTHORIZED	= CALL_URL + "/whistle";

	public static final String WHISTLE_SIGNUP_VERIFY	= USER_URL + "/verifySignup";

	public static final String WHISTLE_PROFILE_UPLOAD	= CALL_URL + "/photoUploadCredentials";

	public static final String WHISTLE_CATEGORY		= CALL_URL + "/categories";

	public static final String WHISTLE_FACTS			= CALL_URL + "/facts";

	public static final String CREDENTIALS				= "http://whistle-dev.herokuapp.com/api/cloudinaryAuth/";

	public static final String WHISTLE_GET_URL			= "http://whistle-dev.herokuapp.com/api/config/android";

	public static String URL_USER_INFO() {
		return CALL_URL + "/user";
	}

	public static String URL_CODE_VERIFY() {
		return WHISTLE_SIGNUP_VERIFY;
	}

	public static String URL_WHISTLES_AUTHORIZED() {
		return WHISTLE_URL_AUTHORIZED;
	}

	public static String URL_WHISTLES_UNAUTHORIZED() {
		return WHISTLERLIST_URL;
	}

}