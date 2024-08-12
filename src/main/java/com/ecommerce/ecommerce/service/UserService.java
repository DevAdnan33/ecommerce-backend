package com.ecommerce.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.ecommerce.api.model.LoginBody;
import com.ecommerce.ecommerce.api.model.RegistrationBody;
import com.ecommerce.ecommerce.entities.LocalUser;
import com.ecommerce.ecommerce.exceptions.UserAlreadyExistsException;
import com.ecommerce.ecommerce.repository.LocalUserRepo;

@org.springframework.stereotype.Service
public class UserService {

    @Autowired
    private LocalUserRepo localUserRepo;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private JWTService jwtService;

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {

        if (localUserRepo.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()
                || localUserRepo.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        LocalUser user = new LocalUser();
        user.setUsername(registrationBody.getUsername());
        user.setEmail(registrationBody.getEmail());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());

        return localUserRepo.save(user);
    }

    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> opUser = localUserRepo.findByUsernameIgnoreCase(loginBody.getUsername());
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }

        return null;
    }

}
