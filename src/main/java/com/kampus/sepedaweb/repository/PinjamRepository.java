package com.kampus.sepedaweb.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kampus.sepedaweb.entity.Pinjam;

public interface PinjamRepository extends JpaRepository<Pinjam,Integer > {
    Pinjam findBySepedaIdAndWaktuKembaliIsNull(Integer id);
    boolean existsByUserIdAndWaktuKembaliIsNull(Integer id);
    boolean existsBySepedaIdAndWaktuKembaliIsNull(Integer id);
    //cari peminjaman dari iduser yang blm dikembaliin
    Optional<Pinjam> findByUserIdAndWaktuKembaliIsNull(Integer id);
    
    List<Pinjam> findByUserId(Integer id);
    Page<Pinjam> findByUserIdAndWaktuPinjamBetween(Integer idUser, LocalDateTime awal, LocalDateTime akhir, Pageable halaman);
    Page<Pinjam> findByWaktuPinjamBetween(LocalDateTime awal, LocalDateTime akhir, Pageable halaman);
} 
