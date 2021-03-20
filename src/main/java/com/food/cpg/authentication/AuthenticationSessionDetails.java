package com.food.cpg.authentication;

public class AuthenticationSessionDetails {

    private int authenticatedUserId;

    private AuthenticationSessionDetails() {

    }

    private static final class LazyClassHolder {
        private static final AuthenticationSessionDetails AUTHENTICATION_SESSION_DETAILS = new AuthenticationSessionDetails();
    }

    public static AuthenticationSessionDetails getInstance() {
        return LazyClassHolder.AUTHENTICATION_SESSION_DETAILS;
    }

    public int getAuthenticatedUserId() {
        return authenticatedUserId;
    }

    public void setAuthenticatedUserId(int authenticatedUserId) {
        this.authenticatedUserId = authenticatedUserId;
    }
}
