package com.food.cpg.authentication;

public abstract class AuthenticationFactory {

    private static AuthenticationFactory authenticationFactory;

    public static AuthenticationFactory instance() {
        return authenticationFactory;
    }

    public static void setAuthenticationFactory(AuthenticationFactory factory) {
        authenticationFactory = factory;
    }

    public abstract IAuthNavigator makeAuthNavigator();
}
