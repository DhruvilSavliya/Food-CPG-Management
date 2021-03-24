package com.food.cpg.manufacturer.registration;

import java.util.List;

import com.food.cpg.manufacturer.registration.Registration;

public interface IRegistrationPersistence {

    List<Registration> getAll();

    void approve(String email);

    void block(String email);
}