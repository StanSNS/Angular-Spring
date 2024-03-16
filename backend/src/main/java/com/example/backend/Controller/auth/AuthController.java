package com.example.backend.Controller.auth;

import com.example.backend.DTO.auth.LoginDTO;
import com.example.backend.DTO.auth.RegisterDTO;
import com.example.backend.DTO.auth.UserDTO;
import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Utils.JwtUtil;
import com.example.backend.Service.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthService authService;


    @PostMapping("/login")
    public void createAuthToken(@RequestBody LoginDTO loginDTO, HttpServletResponse response) throws IOException, JSONException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
        Optional<User> firstByEmail = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());


        if (firstByEmail.isPresent()) {
            response.getWriter().write(new JSONObject()
                    .put("userID", firstByEmail.get().getId())
                    .put("role", firstByEmail.get().getRole())
                    .toString());
        }

        response.addHeader("Access-Control-Expose-Headers","Authorization");
        response.addHeader("Access-Control-Allow-Headers","Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
        response.addHeader("Authorization", "Bearer " + jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){
        if(authService.hasUserWithEmail(registerDTO.getEmail())){
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(authService.createUser(registerDTO), HttpStatus.OK);
    }

}
