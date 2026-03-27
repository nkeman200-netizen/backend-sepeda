package com.kampus.sepedaweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kampus.sepedaweb.dto.KembaliResponseDTO;
import com.kampus.sepedaweb.dto.PinjamRequestDTO;
import com.kampus.sepedaweb.dto.PinjamResponseDTO;
import com.kampus.sepedaweb.dto.RiwayatResponsDTO;
import com.kampus.sepedaweb.entity.Admin;
import com.kampus.sepedaweb.entity.Sepeda;
import com.kampus.sepedaweb.repository.PinjamRepository;
import com.kampus.sepedaweb.repository.SepedaRepository;
import com.kampus.sepedaweb.repository.UserRepository;
import com.kampus.sepedaweb.service.PinjamService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class AppController {

    // Memanggil SepedaRepository secara otomatis (Dependency Injection)
    @Autowired 
    private SepedaRepository sepedaRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PinjamRepository pinjamRepository;
    @Autowired
    private PinjamService pinjamService;

    @GetMapping("/sepeda")
    public Page<Sepeda> lihatSemuaSepeda(@RequestParam (defaultValue = "0") Integer page, @RequestParam (defaultValue = "10") Integer size) {
        Pageable halaman=PageRequest.of(page, size, Sort.by("id").descending());
        return sepedaRepository.findAllByStatusNot("dihapus", halaman);
    }
    
    @GetMapping("/sepeda/tersedia")
    List<Sepeda> sepedaSedia(){
        return sepedaRepository.findSepedaByStatus("tersedia");
    }
    
    @PostMapping("/sepeda")
    Sepeda tambahSepeda(@RequestBody Sepeda sepedaBaru) {
        if (sepedaBaru.getStatus() == null) {
            sepedaBaru.setStatus("tersedia");
        }
        return sepedaRepository.save(sepedaBaru); 
    }

    @DeleteMapping("/sepeda/{id}")
    Map<String ,String >  hapusSepeda(@PathVariable Integer id){
        Map<String , String > response=new HashMap<>();
        if (pinjamRepository.existsBySepedaIdAndWaktuKembaliIsNull(id)) {
            response.put("status", "Gagal");
            response.put("pesan", "Sepeda gagal dihapus, sepeda sedang dipinjam");
            response.put("icon", "error");
            return response;
            // throw new IllegalArgumentException("Tidak bisa hapus, Sepeda sedang dipinjam");
        }
        Sepeda sepeda=sepedaRepository.findSepedaById(id);
        if (sepeda != null) {
            sepeda.setStatus("dihapus");
            sepedaRepository.save(sepeda);
            response.put("status", "Berhasil");
            response.put("pesan", "Sepeda berhasil dihapus");        
            response.put("icon", "success");
            return response;
        }
        response.put("status", "Tidak ditemukan");
        response.put("pesan", "Sepeda gagal dihapus, sepeda tidak ditemukan");
        response.put("icon", "error");
        return response;
    }

    @PutMapping("/sepeda/{id}")
    Sepeda editSepeda(@PathVariable Integer id,@RequestBody Sepeda spd){
        Sepeda spdLama=sepedaRepository.findById(id).orElse(null);
        if (spdLama != null) {
            spdLama.setMerk(spd.getMerk());
            spdLama.setStatus(spd.getStatus());
            return sepedaRepository.save(spdLama);
        }
        return null;
    }

    @PostMapping("/pinjam/{idSpd}")
    public PinjamResponseDTO pinjam(@PathVariable Integer idSpd,@Valid @RequestBody PinjamRequestDTO pinjamReq, Authentication authentication ){
        Integer idUser= (Integer) authentication.getCredentials();
        
        return pinjamService.pinjam(idSpd, idUser, pinjamReq);
    }
    
    @GetMapping("/pinjam/aktif")
    public Integer isPinjam(Authentication authentication){
        Integer idUser= (Integer) authentication.getCredentials();
        return pinjamService.isPinjam(idUser);
    }
    
    @PutMapping("/pinjam/{idSpd}/kembali")
    public KembaliResponseDTO kembali(@PathVariable Integer idSpd, Authentication authentication)
    {
        Integer idUser=(Integer) authentication.getCredentials();
        return pinjamService.kembali(idSpd,idUser);
    }

    @PostMapping("register/admin")
    Admin registerAdmin(@RequestBody Admin admin)
    {
        return userRepository.save(admin);
    }

    @GetMapping("pinjam/riwayat/{id}") //riwayat satu sepeda yang agi dipinjem (ADMIN)
    RiwayatResponsDTO riwayatPinjam(@PathVariable Integer id){
        return pinjamService.riwayat(id);
    }
    
    @GetMapping("/pinjam/semua") //semua riwayat (ADMIN)
    List<RiwayatResponsDTO> riwayatSemua(){
        return pinjamService.riwayatSemua();
    }

    @GetMapping("/pinjam/riwayat")
    List<RiwayatResponsDTO> riwayatSemua(Authentication authentication){
        Integer idUser=(Integer ) authentication.getCredentials();
        return pinjamService.riwayatUser(idUser);
    }
    
}