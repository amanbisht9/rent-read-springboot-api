package crio.bookrentalsystem.rentread.controller.pub;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crio.bookrentalsystem.rentread.dto.StatusMessage;
import crio.bookrentalsystem.rentread.dto.UserRegisterRequest;
import crio.bookrentalsystem.rentread.model.User;
import crio.bookrentalsystem.rentread.service.pub.UserRegisterLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/user")
public class UserRegisterLoginController {

    @Autowired
    private UserRegisterLoginService userRegisterLoginService;

    @PostMapping("/register")
    public ResponseEntity<User> userRegistration(@RequestBody UserRegisterRequest entity) {
        //TODO: process POST request
        String username = entity.getUsername();
        String password = entity.getPassword();
        String firstname = entity.getFirstname();
        String lastname = entity.getLastname();
        String role = entity.getRole();

        User resp = userRegisterLoginService.userRegistrationS(username, password, firstname, lastname, role);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<StatusMessage> userLogin(@RequestHeader(value = "username", defaultValue = "") String username, @RequestHeader(value = "password", defaultValue = "") String password) {
        //TODO: process POST request
        StatusMessage resp = userRegisterLoginService.userLoginS(username, password);
        return new ResponseEntity<>(resp , HttpStatus.OK);
    }
    
    
}
