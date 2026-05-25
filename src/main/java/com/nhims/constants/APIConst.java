package com.nhims.constants;

/**
 * API-related constants: endpoints and response codes.
 * Base URL is loaded from environment-specific properties via {@link com.nhims.utils.HFile}.
 */
public class APIConst {
	// Endpoints
	public static final String CREATE_ACCOUNT_ENDPOINT = "/createAccount";
	public static final String DELETE_ACCOUNT_ENDPOINT = "/deleteAccount";

	// Response Codes
	public static final int RESOURCE_CREATED = 201;
	public static final int SUCCESS = 200;
}
