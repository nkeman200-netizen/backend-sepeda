package com.kampus.sepedaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kampus.sepedaweb.entity.Pinjam;

public interface PinjamRepository extends JpaRepository<Pinjam,Integer > {
    Pinjam findByUserId(Integer id);
    Pinjam findBySepedaIdAndWaktuKembaliIsNull(Integer id);
    boolean existsByUserIdAndWaktuKembaliIsNull(Integer id);
    boolean existsBySepedaIdAndWaktuKembaliIsNull(Integer id);
    
} 
