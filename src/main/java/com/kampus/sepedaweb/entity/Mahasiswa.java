package com.kampus.sepedaweb.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("mahasiswa")
public class Mahasiswa extends User{
    private String nim;

    public String getRole(){
        return "mahasiswa";
    }
    public void setNim(String nim) {
        this.nim = nim;
    }
    public String getNim() {
        return nim;
    }
}
