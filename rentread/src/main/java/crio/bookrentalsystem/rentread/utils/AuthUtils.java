package crio.bookrentalsystem.rentread.utils;

import java.util.Base64;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import jakarta.servlet.http.HttpServletRequest;

public class AuthUtils {

    // public static String[] extractBasicAuthCredentials(HttpServletRequest request) {
    //     // Get the Authorization header
    //     String authHeader = request.getHeader("Authorization");

    //     if (authHeader != null && authHeader.startsWith("Basic ")) {
    //         // Decode the Base64 encoded credentials
    //         String base64Credentials = authHeader.substring("Basic ".length()).trim();
    //         String credentials = new String(Base64.getDecoder().decode(base64Credentials));

    //         // Split the credentials into username and password
    //         String[] values = credentials.split(":", 2);
    //         if (values.length == 2) {
    //             String userName = values[0];
    //             String password = values[1];
    //             return new String[]{userName, password};  // Return the username and password
    //         } else {
    //             throw new IllegalArgumentException("Invalid Basic Authentication header");
    //         }
    //     } else {
    //         throw new IllegalArgumentException("Missing or invalid Authorization header");
    //     }
    // }
    
}
