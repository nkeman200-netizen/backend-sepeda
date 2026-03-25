package com.kampus.sepedaweb.dto;

import java.time.LocalDateTime;

public class KembaliResponseDTO {
    private Integer idPinjam;
    private String merkSepeda;
    private String username;
    private LocalDateTime waktuPinjam;
    private LocalDateTime waktuKembali;

    public KembaliResponseDTO(Integer idPinjam, String merkSepeda,String username,LocalDateTime waktuPinjam, LocalDateTime waktuKembali){
        this.idPinjam=idPinjam;this.merkSepeda=merkSepeda;this.username=username;this.waktuKembali=waktuKembali;this.waktuPinjam=waktuPinjam;
    }

    public void setIdPinjam(Integer idPinjam) {
        this.idPinjam = idPinjam;
    }
    public void setMerkSepeda(String merkSepeda) {
        this.merkSepeda = merkSepeda;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setWaktuKembali(LocalDateTime waktuKembali) {
        this.waktuKembali = waktuKembali;
    }
    public void setWaktuPinjam(LocalDateTime waktuPinjam) {
        this.waktuPinjam = waktuPinjam;
    }
    public Integer getIdPinjam() {
        return idPinjam;
    }
    public String getMerkSepeda() {
        return merkSepeda;
    }
    public String getUsername() {
        return username;
    }
    public LocalDateTime getWaktuKembali() {
        return waktuKembali;
    }
    public LocalDateTime getWaktuPinjam() {
        return waktuPinjam;
    }
}
