package com.food.cpg.models;

import org.springframework.lang.Nullable;

/**
 * @author Kartik Gevariya
 */
public enum Role {
    ADMIN("/admin"),
    MANUFACTURER("/vendors");

    Role(String landingPage) {
        this.landingPage = landingPage;
    }

    public String getLandingPage() {
        return landingPage;
    }

    @Nullable
    public static Role getRole(String authority) {
        for (Role role : values()) {
            if (role.name().equals(authority)) {
                return role;
            }
        }
        return null;
    }

    private final String landingPage;
}
