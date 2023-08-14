package com.login.login.Service;
import com.login.login.Common.APIResponse;
import com.login.login.DTO.LoginRequestDto;
import com.login.login.DTO.SignUpRequestDto;
import com.login.login.Entity.User;
import com.login.login.Entity.UserLogin;
import com.login.login.Repository.UserRepository;
import com.login.login.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    private final Map<String, String> loggedInUsers = new HashMap<>();
    public APIResponse signUp(SignUpRequestDto signUpRequestDto) {
        APIResponse apiResponse=new APIResponse();

        //validation

        //dto to entity
        User userEntity=new User();
        userEntity.setEmail(signUpRequestDto.getEmail());
        userEntity.setPassword(signUpRequestDto.getPassword());
        userEntity.setRoleNo(signUpRequestDto.getRoleNo());
        userEntity.setCompanyNo(signUpRequestDto.getCompanyNo());

        //store entity

        userEntity=userRepository.save(userEntity);
        //generate jwt
        String token=jwtUtils.generateJwt(userEntity);
        Map<String, Object> data = new HashMap<>();
        data.put("accessToken", token);

        apiResponse.setData(data);
        apiResponse.setData(userEntity);

        //return
        return apiResponse;
    }

    public APIResponse login(LoginRequestDto loginRequestDto) {
        APIResponse apiResponse = new APIResponse();

        //validation

        //verify user exist with given email and password
        User user = userRepository.findOneByEmailIgnoreCaseAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());


        //response
        if (user == null) {
            apiResponse.setData("user login failed");
            return apiResponse;
        }
        //generate jwt
        String token=jwtUtils.generateJwt(user);
        Map<String, Object> data = new HashMap<>();
        data.put("accessToken", token);

        apiResponse.setData(data);
        return apiResponse;
    }


//    public void generateVerificationToken(UserLogin userLogin) {
//        // Generate a verification token, such as a random UUID or a unique string
//        String verificationToken = UUID.randomUUID().toString();
//
//        // Save the verification token to the user entity
//        userLogin.setVerificationToken(verificationToken);
//
//        // Update the user in the repository
//        userRepository.save(userLogin);
//    }
//
//    public boolean verifyCredentials(String username, String password) {
//        User user = userRepository.findByUsername(username);
//
//        // Check if the user exists and the password matches
//        if (user != null && user.getPassword().equals(password)) {
//            return true;
//        }
//
//        return false;
//    }
//
//
//    public String login(String email, String password) {
//        UserLogin userLogin = userRepository.findByEmail(email);
//
//        if (userLogin != null && userLogin.getPassword().equals(password)) {
//            if (loggedInUsers.containsValue(email)) {
//                return "Unauthorized person: User already logged in.";
//            } else {
//                String token = generateToken();
//                loggedInUsers.put(token, email);
//                return "Token: " + token;
//            }
//        }
//
//        return "Invalid email or password.";
//    }
//
//
//    public String accessProtectedResource(String token) {
//        String email = loggedInUsers.get(token);
//        if (email != null) {
//            // Authorized access to protected resource
//            return "Welcome, " + email + "! You have accessed the protected resource.";
//        } else {
//            return "Unauthorized person: Please log in with the correct user.";
//        }
//    }
//
//    private String generateToken() {
//
//        return UUID.randomUUID().toString();
//    }
public String login(String email, String password) {
    UserLogin userLogin = userRepository.findByEmail(email);
    if (userLogin != null && userLogin.getPassword().equals(password)) {
        String accessToken = generateAccessToken();
        loggedInUsers.put(accessToken, email);
        return accessToken;
    } else {
        return "Unauthorized";
    }
}

    public boolean isValidAccessToken(String accessToken) {
        return loggedInUsers.containsKey(accessToken);
    }

    public String getEmailFromAccessToken(String accessToken) {
        return loggedInUsers.getOrDefault(accessToken, null);
    }

    private String generateAccessToken() {
        return UUID.randomUUID().toString();
    }

}



