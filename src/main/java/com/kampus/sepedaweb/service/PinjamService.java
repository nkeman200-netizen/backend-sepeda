package com.kampus.sepedaweb.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kampus.sepedaweb.dto.KembaliResponseDTO;
import com.kampus.sepedaweb.dto.PinjamRequestDTO;
import com.kampus.sepedaweb.dto.PinjamResponseDTO;
import com.kampus.sepedaweb.dto.RiwayatResponsDTO;
import com.kampus.sepedaweb.entity.*;
import com.kampus.sepedaweb.repository.*;

@Service
public class PinjamService {
    @Autowired
    private SepedaRepository sepedaRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PinjamRepository pinjamRepository;
    
    @Transactional
    public PinjamResponseDTO pinjam(Integer idSpd, Integer idUser, PinjamRequestDTO pinjamReq){
        Sepeda spdPijam=sepedaRepository.findSepedaById(idSpd);
        User mhs=userRepository.findUserById(idUser);
        Pinjam pinjam=new Pinjam();

        if (pinjamRepository.existsByUserIdAndWaktuKembaliIsNull(idUser)) {
            throw new IllegalArgumentException("Gagal: User "+mhs.getUsername()+" sedang meminjam sepeda");
        }
        if (mhs == null) {
            throw new IllegalArgumentException("Gagal: user "+idUser+" tidak ditemukan");
        }
        if (spdPijam == null) {
            throw new IllegalArgumentException("Gagal: sepeda "+idSpd+" tidak ditemukan");
        }
        if (!spdPijam.getStatus().equalsIgnoreCase("tersedia")) {
            throw new IllegalArgumentException("Gagal: Sepeda "+spdPijam.getMerk()+" sedang dipinjam");
        }
        String nim="-";
        if (mhs instanceof Mahasiswa mahasiswa) {
            nim=mahasiswa.getNim();
        }

        pinjam.setDurasi(pinjamReq.getDurasi());
        pinjam.setSepeda(spdPijam);
        pinjam.setUser(mhs);
        spdPijam.setStatus("dipinjam");
        
        sepedaRepository.save(spdPijam);
        Pinjam hasilSimpan = pinjamRepository.save(pinjam);
        PinjamResponseDTO hasilAman=new PinjamResponseDTO(
            hasilSimpan.getId(),
            hasilSimpan.getDurasi(),
            hasilSimpan.getWaktuPinjam(),
            spdPijam.getMerk(),
            mhs.getUsername(),
            nim
        );
        return hasilAman;
    }
    @Transactional
    public KembaliResponseDTO kembali(Integer  id, Integer idUser){
        Pinjam peminjaman=pinjamRepository.findById(id).orElse(null);
        User user=userRepository.findUserById(idUser);
        
        if (peminjaman == null) {
            throw new IllegalArgumentException("Gagal: peminjaman "+id+" tidak ditemukan");
        }
        if(peminjaman.getWaktuKembali() != null){
            throw new IllegalArgumentException("Gagal: Sepeda "+id+" sudah dikembalikan");
        }
        if (!peminjaman.getUser().getId().equals(idUser)) {
            throw new IllegalArgumentException("Gagal: user "+user.getUsername()+" bukan peminjam");
        }
        
        peminjaman.setWaktuKembali(LocalDateTime.now());
        Sepeda spd=peminjaman.getSepeda();
        pinjamRepository.save(peminjaman);

        spd.setStatus("tersedia");
        sepedaRepository.save(spd);
        KembaliResponseDTO responseAman=new KembaliResponseDTO(id, peminjaman.getSepeda().getMerk(), peminjaman.getUser().getUsername(), peminjaman.getWaktuPinjam(), peminjaman.getWaktuKembali());
        return responseAman;
    }
    
    public RiwayatResponsDTO riwayat(Integer idSepeda){
        Pinjam peminjaman=pinjamRepository.findBySepedaIdAndWaktuKembaliIsNull(idSepeda);
        if (peminjaman == null) {
            throw new IllegalArgumentException("Gagal: peminjaman sepeda "+idSepeda+" tidak ditemukan");
        }

        RiwayatResponsDTO responseAman=new RiwayatResponsDTO(peminjaman.getId(),peminjaman.getSepeda().getMerk(),peminjaman.getUser().getUsername(),peminjaman.getWaktuPinjam(),peminjaman.getWaktuKembali());
        return responseAman;
    }

    public List<RiwayatResponsDTO> riwayatSemua(){
        List<Pinjam> pinjams=pinjamRepository.findAll();
        List<RiwayatResponsDTO> allRiwayat = new ArrayList<>();
        for (Pinjam pinjam : pinjams) {
            RiwayatResponsDTO riwayat=new RiwayatResponsDTO(pinjam.getId(), pinjam.getSepeda().getMerk(), pinjam.getUser().getUsername(), pinjam.getWaktuPinjam(), pinjam.getWaktuKembali());
            allRiwayat.add(riwayat);
        }
        return allRiwayat;
    }
    
    public Integer isPinjam(Integer id){
        Optional<Pinjam> pinjamOpt = pinjamRepository.findByIdUserAndWaktuKembaliIsNull(id);
        
        // Jangan cek != null, tapi gunakan .isPresent()
        if (pinjamOpt.isPresent()) {
            // Gunakan .get() untuk mengambil objek asli di dalam kotak Optional
            return pinjamOpt.get().getId(); 
        }
        
        return null; // Atau kembalikan 0 jika tidak ada pinjaman aktif
    } 
}