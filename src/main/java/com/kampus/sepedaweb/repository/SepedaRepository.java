package com.kampus.sepedaweb.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kampus.sepedaweb.entity.Sepeda;

public interface SepedaRepository extends JpaRepository<Sepeda, Integer> {
    List<Sepeda> findSepedaByStatus(String status);
    Page<Sepeda> findAllByStatusNot(String status,Pageable halaman);
    Sepeda findSepedaById(Integer id);
    
    @Query(
        "select s from Sepeda s "+ //tambahin spasi di sambungannya, JANGAN LUPA!!
        "where LOWER(s.merk) like LOWER(concat('%',:merk,'%')) "+
        "and s.status != :status "
    )
    Page<Sepeda> sepedaCari(
        @Param("merk") String merk,
        @Param("status") String notStatus,
        Pageable pageable
    );

    long countByStatusNot(String status);
    long countByStatus(String status);
}