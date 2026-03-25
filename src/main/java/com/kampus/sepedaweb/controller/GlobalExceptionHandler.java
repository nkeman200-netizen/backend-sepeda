package com.kampus.sepedaweb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // buat nangkep ilegal argumen
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String ,String >> handleValidasiError(IllegalArgumentException ex) {
        // Kita racik bentuk JSON balasannya
        Map<String, String> response = new HashMap<>();
        response.put("status", "error masukan data yang bener dah");
        response.put("pesan", ex.getMessage()); // Mengambil pesan dari Service tadi
        
        // Kembalikan JSON tersebut dengan status HTTP 400 (Bad Request)
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }   
    //buat nangkep data yang dimasukin ga lolos @valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String ,String >> handleInputTidakValid(MethodArgumentNotValidException ex){
        Map<String ,String > respons=new HashMap<>();
        respons.put("status", "eror");

        String responEror=ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        respons.put("pesan", responEror);
        return new ResponseEntity<>(respons,HttpStatus.BAD_REQUEST);
    }
}
