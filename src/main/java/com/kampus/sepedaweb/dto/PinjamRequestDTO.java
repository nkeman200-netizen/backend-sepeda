package com.kampus.sepedaweb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PinjamRequestDTO {
    @NotNull(message="Duraasi peminjaman harus diisi")
    @Min(value=1, message="Durasi peminjaman minimal 1 jam")
    private Integer durasi;

    public void setDurasi(Integer durasi) {
        this.durasi = durasi;
    }
    public Integer getDurasi() {
        return durasi;
    }

    
}
