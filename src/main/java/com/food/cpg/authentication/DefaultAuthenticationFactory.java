package com.food.cpg.authentication;

public class DefaultAuthenticationFactory extends AuthenticationFactory {

    @Override
    public IAuthNavigator makeAuthNavigator() {
        return new AuthNavigator();
    }
}
