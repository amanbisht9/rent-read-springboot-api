package crio.bookrentalsystem.rentread.service;

import java.util.Optional;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import crio.bookrentalsystem.rentread.exception.FieldException;
import crio.bookrentalsystem.rentread.model.User;
import crio.bookrentalsystem.rentread.repository.IUserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> user = userRepository.findById(username);
            if(!user.isPresent()){
                throw new UsernameNotFoundException("Username not found");
            }
            return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(),
                user.get().getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.get().getRole()))
                );

        } catch (Exception e) {
            throw new FieldException(e.getMessage());
        }
    }
    
}
