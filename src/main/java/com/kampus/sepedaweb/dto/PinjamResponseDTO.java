package com.kampus.sepedaweb.dto;

import java.time.LocalDateTime;

public class PinjamResponseDTO {
    private Integer idPinjam;
    private Integer durasi;
    private LocalDateTime waktuPinjam;

    private String merkSepeda;
    private String username;
    private String nim;

    public PinjamResponseDTO(Integer idPinjam, Integer durasi,LocalDateTime waktuPinjam,String merkSepeda,String username, String nim){
        this.durasi=durasi;this.idPinjam=idPinjam;this.merkSepeda=merkSepeda;this.nim=nim;this.username=username; this.waktuPinjam=waktuPinjam;
    }

    public void setDurasi(Integer  durasi) {
        this.durasi = durasi;
    }
    public void setIdPinjam(Integer idPinjam) {
        this.idPinjam = idPinjam;
    }
    public void setMerkSepeda(String merkSepeda) {
        this.merkSepeda = merkSepeda;
    }
    public void setNim(String nim) {
        this.nim = nim;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setWaktuPinjam(LocalDateTime waktuPinjam) {
        this.waktuPinjam = waktuPinjam;
    }

    public Integer  getDurasi() {
        return durasi;
    }
    public Integer getIdPinjam() {
        return idPinjam;
    }
    public String getMerkSepeda() {
        return merkSepeda;
    }
    public String getNim() {
        return nim;
    }
    public String getUsername() {
        return username;
    }
    public LocalDateTime getWaktuPinjam() {
        return waktuPinjam;
    }
}
