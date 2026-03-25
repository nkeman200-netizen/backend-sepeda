package com.kampus.sepedaweb.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pinjam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer durasi;
    private LocalDateTime waktuPinjam= LocalDateTime.now();
    private LocalDateTime waktuKembali;

    @ManyToOne
    @JoinColumn(name = "id_spd")
    private Sepeda sepeda;
    
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    
    public Integer getDurasi() {
        return durasi;
    }
    public Integer getId() {
        return id;
    }
    public Sepeda getSepeda() {
        return sepeda;
    }
    public LocalDateTime getWaktuPinjam() {
        return waktuPinjam;
    }
    public LocalDateTime getWaktuKembali() {
        return waktuKembali;
    }
    public User getUser() {
        return user;
    }

    public void setDurasi(Integer durasi) {
        this.durasi = durasi;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setSepeda(Sepeda sepeda) {
        this.sepeda = sepeda;
    }
    public void setWaktuPinjam(LocalDateTime waktuPinjam) {
        this.waktuPinjam = waktuPinjam;
    }
    public void setWaktuKembali(LocalDateTime waktuKembali) {
        this.waktuKembali = waktuKembali;
    }
    public void setUser(User user) {
        this.user = user;
    }


}
