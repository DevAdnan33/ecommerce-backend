package com.ecommerce.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.ecommerce.api.model.RegistrationBody;
import com.ecommerce.ecommerce.entities.LocalUser;
import com.ecommerce.ecommerce.exceptions.UserAlreadyExistsException;
import com.ecommerce.ecommerce.repository.LocalUserRepo;

@org.springframework.stereotype.Service
public class UserService {

    @Autowired
    private LocalUserRepo localUserRepo;

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {

        if (localUserRepo.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()
                || localUserRepo.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        LocalUser user = new LocalUser();
        user.setUsername(registrationBody.getUsername());
        user.setEmail(registrationBody.getEmail());
        user.setPassword(registrationBody.getPassword());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());

        return localUserRepo.save(user);
    }

}
