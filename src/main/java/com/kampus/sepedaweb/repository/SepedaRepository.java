package com.kampus.sepedaweb.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kampus.sepedaweb.entity.Sepeda;

public interface SepedaRepository extends JpaRepository<Sepeda, Integer> {
    List<Sepeda> findSepedaByStatus(String status);
    Page<Sepeda> findAllByStatusNot(String status,Pageable halaman);
    Sepeda findSepedaById(Integer id);
}