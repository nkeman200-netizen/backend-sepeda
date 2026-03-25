package com.kampus.sepedaweb.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User{
    private Integer kodePegawai;

    public String getRole(){
        return "admin";
    }
    public void setKodePegawai(Integer kodePegawai) {
        this.kodePegawai = kodePegawai;
    }
    public Integer getKodePegawai() {
        return kodePegawai;
    }
}
