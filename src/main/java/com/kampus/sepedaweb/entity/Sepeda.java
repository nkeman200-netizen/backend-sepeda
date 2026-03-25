package com.kampus.sepedaweb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Mantra yang mengubah class ini menjadi Tabel Database!
public class Sepeda {

    @Id // Menandakan ini adalah Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Menandakan Auto Increment
    private Integer id;
    
    private String merk;
    private String status;

    // --- Bawahnya cukup isi Getter dan Setter biasa ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getMerk() { return merk; }
    public void setMerk(String merk) { this.merk = merk; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    
}