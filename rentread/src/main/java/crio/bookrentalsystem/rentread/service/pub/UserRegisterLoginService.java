package crio.bookrentalsystem.rentread.service.pub;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import crio.bookrentalsystem.rentread.dto.Roles;
import crio.bookrentalsystem.rentread.dto.StatusMessage;
import crio.bookrentalsystem.rentread.exception.LoginsException;
import crio.bookrentalsystem.rentread.exception.RegistrationException;
import crio.bookrentalsystem.rentread.model.User;
import crio.bookrentalsystem.rentread.repository.IUserRepository;
import crio.bookrentalsystem.rentread.utils.ValidationChecks;

@Service
public class UserRegisterLoginService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
            
            //encrypting password
            String encryptedPassword = passwordEncoder.encode(password);

            User user = new User();
            user.setUsername(username);
            user.setPassword(encryptedPassword);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setRole(role);
            
            userRepository.save(user);

            return user;

            
        } catch (Exception e) {
            // TODO: handle exception
            throw new RegistrationException(e.getMessage());
        }
    }

    public StatusMessage userLoginS(String username, String password) {
        // TODO Auto-generated method stub
        try {

            if(username == ""){
                throw new LoginsException("username header is empty, please pass correct value");
            }

            if(!ValidationChecks.emailPatternCheck(username)){
                throw new LoginsException("username header should be an email, please correct username format");
            }

            if(password == ""){
                throw new LoginsException("password header is empty, please pass correct value");
            }

            Optional<User> user = userRepository.findById(username);
            if(!user.isPresent()){
                throw new LoginsException("username is incorrect, please pass registered username");
            }

            User userVal = user.get();

            if(!passwordEncoder.matches(password, userVal.getPassword())){
                throw new LoginsException("password is incorrect, please pass correct password");
            }

            return new StatusMessage("Successful login");
            
        } catch (Exception e) {
            throw new LoginsException(e.getMessage());
        }
        
    }



}
