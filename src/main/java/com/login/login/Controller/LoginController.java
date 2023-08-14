package com.login.login.Controller;

import com.login.login.Common.APIResponse;
import com.login.login.DTO.LoginRequestDto;
import com.login.login.DTO.SignUpRequestDto;
import com.login.login.Entity.UserLogin;
import com.login.login.Repository.UserRepository;
import com.login.login.Service.LoginService;
import com.login.login.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller

public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        APIResponse apiResponse=loginService.signUp(signUpRequestDto);
        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }
//    @PostMapping("/login")
//    public ResponseEntity<APIResponse> login(@RequestBody LoginRequestDto loginRequestDto){
//        APIResponse apiResponse=loginService.login(loginRequestDto);
//        return ResponseEntity
//                .status(apiResponse.getStatus())
//                .body(apiResponse);
//    }
     @GetMapping("/privateAPI")
    public ResponseEntity<APIResponse> privateApi(@RequestHeader (value = "authorization", defaultValue="")String auth) throws Exception {
        APIResponse apiResponse= new APIResponse();


        jwtUtils.verify(auth);

        apiResponse.setData("this is private api");
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
     }
//    @PostMapping("/login")
//    public ResponseEntity<APIResponse> loginUser(@RequestBody UserLogin userLogin) {
//        APIResponse apiResponse = new APIResponse();
//
//        // Validation
//
//        // Verify user exists with the given email and password
//        UserLogin userLogin1 = userRepository.findOneByUserNameIgnoreCaseAndPassword(userLogin.getUserName(), userLogin.getPassword());
//
//        // Response
//        if (userLogin == null) {
//            apiResponse.setData("User login failed");
//            return ResponseEntity.ok(apiResponse);
//        }
//
//        // Generate verification token for the user
//        loginService.generateVerificationToken(userLogin);
//
//        // Generate JWT
//        String token = jwtUtils.generateJwt(userLogin);
//        Map<String, Object> data = new HashMap<>();
//        data.put("accessToken", token);
//
//        apiResponse.setData(data);
//        return ResponseEntity.ok(apiResponse);
//    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
//        UserLogin userLogin = userRepository.findByEmail(email);
//
//        if (userLogin != null && userLogin.getPassword().equals(password)) {
//            if (loggedInUsers.containsValue(email)) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized person: User already logged in.");
//            } else {
//                String token = generateToken();
//                loggedInUsers.put(token, email);
//                return ResponseEntity.ok("Token: " + token);
//            }
//        }
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
//    }
//
//    @GetMapping("/protected-resource")
//    public ResponseEntity<String> protectedResource(@RequestHeader("Authorization") String token) {
//        String email = loggedInUsers.get(token);
//        if (email != null) {
//            // Authorized access to protected resource
//            return ResponseEntity.ok("Welcome, " + email + "! You have accessed the protected resource.");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized person: Please log in with the correct user.");
//        }
//    }
//    private String generateToken() {
//
//        return UUID.randomUUID().toString();
//    }

 @PostMapping("/login")
    public String login(@RequestBody String email, @RequestBody String password) {
        return loginService.login(email, password);
    }

    @GetMapping("/protected-resource")
    public String protectedResource(@RequestHeader("Authorization") String accessToken) {
        if (loginService.isValidAccessToken(accessToken)) {
            String email = loginService.getEmailFromAccessToken(accessToken);
            return "Access granted for user: " + email;
        } else {
            return "Unauthorized Person loggedIn";
        }
    }

}
