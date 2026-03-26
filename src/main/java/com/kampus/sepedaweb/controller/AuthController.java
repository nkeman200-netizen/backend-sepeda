package com.kampus.sepedaweb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kampus.sepedaweb.dto.LoginRequestDTO;
import com.kampus.sepedaweb.entity.Admin;
import com.kampus.sepedaweb.entity.Mahasiswa;
import com.kampus.sepedaweb.entity.User;
import com.kampus.sepedaweb.repository.UserRepository;
import com.kampus.sepedaweb.security.JwtUtil;

@RestController
@RequestMapping("/auth")
// @CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Map<String ,String >> login(@RequestBody LoginRequestDTO loginData){
        // System.out.println(">>> HASH UNTUK PASSWORD INI: " + passwordEncoder.encode(loginData.getPassword()));
        User user=userRepository.findUserByUsername(loginData.getUsername());
        if (user != null && passwordEncoder.matches(loginData.getPassword(), user.getPassword())) {
            String role=user.getRole();
            String token=jwtUtil.generateToken(user.getUsername(), user.getId());
            Map<String ,String > response=new HashMap<>();
            response.put("token", token);
            response.put("role", role);
            return ResponseEntity.ok(response);
        }else{
            Map<String ,String > erorResponse=new HashMap<>();
            erorResponse.put("pesan", "Username atau password salah");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erorResponse);
        }
    }

    @PostMapping("register/mahasiswa")  
    public Mahasiswa registerMahasiswa(@RequestBody Mahasiswa mahasiswa)    
    {
        mahasiswa.setPassword(passwordEncoder.encode(mahasiswa.getPassword()));
        return userRepository.save(mahasiswa);
    }

    @PostMapping("register/admin")  
    public Admin registerAdmin(@RequestBody Admin admin)
    {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return userRepository.save(admin);
    }

}
