package crio.bookrentalsystem.rentread.service.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crio.bookrentalsystem.rentread.config.ValidationChecks;
import crio.bookrentalsystem.rentread.dto.Roles;
import crio.bookrentalsystem.rentread.exception.RegistrationException;
import crio.bookrentalsystem.rentread.model.User;
import crio.bookrentalsystem.rentread.repository.IUserRepository;

@Service
public class UserRegisterLoginService {

    @Autowired
    private IUserRepository userRepository;

    public User userRegistrationS(String username, String password, String firstname, String lastname, String role) {
        // TODO Auto-generated method stub
        try {

            //Validation of fields
            if(!ValidationChecks.emailPatternCheck(username)){
                throw new RegistrationException("Username should be an email, please correct username format");
            }
            if(password == "" || password.isEmpty() || password == null){
                throw new RegistrationException("Password field should have some value");
            }
            if(!ValidationChecks.namePatternCheck(firstname)){
                throw new RegistrationException("Firstname field should have some value and alphabets only");
            }

            if(!ValidationChecks.namePatternCheck(lastname)){
                throw new RegistrationException("Lastname field should have some value and alphabets only");
            }

            if (role == null || role == "") {
                role = Roles.USER.toString();
            }

            if (!(role.equals(Roles.USER.toString())) && !(role.equals(Roles.ADMIN.toString()))) {
                throw new RegistrationException("Role can be ADMIN or USER");
            }

            if(userRepository.existsById(username)){
                throw new RegistrationException("Username already exist");
            }

            User user = new User(username, password, firstname, lastname, role);
            userRepository.save(user);

            return user;

            
        } catch (Exception e) {
            // TODO: handle exception
            throw new RegistrationException(e.getMessage());
        }
    }



}
