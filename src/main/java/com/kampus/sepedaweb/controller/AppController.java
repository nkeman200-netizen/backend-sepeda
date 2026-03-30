package com.kampus.sepedaweb.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Page<Sepeda> lihatSemuaSepeda(
            @RequestParam (defaultValue = "0") Integer page, 
            @RequestParam (defaultValue = "10") Integer size, 
            @RequestParam(required = false) String merk,
            // 1. Tambahkan dua penangkap parameter ini:
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        // 2. Logika menerjemahkan string "asc"/"desc" menjadi perintah Sort Spring Boot
        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        
        // 3. Masukkan 'direction' dan 'sortBy' ke dalam PageRequest
        Pageable halaman = PageRequest.of(page, size, Sort.by(direction, sortBy));
        if (merk!=null && !merk.isEmpty()) {
            return sepedaRepository.sepedaCari(merk, "dihapus", halaman);
        }
        return sepedaRepository.findAllByStatusNot("dihapus", halaman);
    }
    
    @PostMapping("/sepeda")
    Sepeda tambahSepeda(@RequestBody Sepeda sepedaBaru) {
        if (sepedaBaru.getStatus() == null) {
            sepedaBaru.setStatus("tersedia");
        }
        return sepedaRepository.save(sepedaBaru); 
    }

    @DeleteMapping("/sepeda/{id}")
    ResponseEntity<Map<String ,String >>  hapusSepeda(@PathVariable Integer id){ //ubah ke response entity agar jika eror masuk ke catch dan program tidak berat 
        Map<String , String > response=new HashMap<>();

        if (pinjamRepository.existsBySepedaIdAndWaktuKembaliIsNull(id)) {
            response.put("pesan", "Sepeda gagal dihapus karena sedang dipinjam mahasiswa.");
            return ResponseEntity.badRequest().body(response); //badrequest = requeest ditolak
            // throw new IllegalArgumentException("Tidak bisa hapus, Sepeda sedang dipinjam");
        }
        Sepeda sepeda=sepedaRepository.findSepedaById(id);
        if (sepeda != null) {
            sepeda.setStatus("dihapus");
            sepedaRepository.save(sepeda);
            response.put("pesan", "Sepeda berhasil dihapus");
            return ResponseEntity.ok(response);
        }
        response.put("pesan", "Sepeda tidak ditemukan");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); //not found = tidak ditemukan
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
    public RiwayatResponsDTO isPinjam(Authentication authentication){
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
    Page<RiwayatResponsDTO> riwayatSemua(
        @RequestParam (defaultValue = "0") Integer page, 
        @RequestParam (defaultValue = "11") Integer size, 
        // 1. Tambahkan dua penangkap parameter ini:
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir,
        // Ubah tipe data menjadi LocalDate (hapus @DateTimeFormat karena format bawaan spring boot untuk LocalDate sudah yyyy-MM-dd)
        @RequestParam(required = false) LocalDate awal,
        @RequestParam(required = false) LocalDate akhir
    ){
        // 2. Logika "Default 7 Hari Terakhir"
        if (akhir == null) {
            akhir = LocalDate.now(); // Jika kosong, set ke hari ini, detik ini
        }
        if (awal == null) {
            awal = akhir.minusDays(7); // Jika kosong, set ke 7 hari sebelum tanggal akhir
        }

        // 2. UBAH KE DATETIME! 
        // Awal = 00:00:00, Akhir = 23:59:59
        LocalDateTime awalDateTime = awal.atStartOfDay(); 
        LocalDateTime akhirDateTime = akhir.atTime(23, 59, 59);
        return pinjamService.riwayatSemua(page,size,sortBy,sortDir,awalDateTime,akhirDateTime);
    }

    @GetMapping("/pinjam/riwayat") //riwayat pribadi (MAHASISWA)
    Page<RiwayatResponsDTO> riwayatUser(
        Authentication authentication,
        @RequestParam (defaultValue = "0") Integer page, 
        @RequestParam (defaultValue = "11") Integer size, 
        // 1. Tambahkan dua penangkap parameter ini:
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir,
        // Ubah tipe data menjadi LocalDate (hapus @DateTimeFormat karena format bawaan spring boot untuk LocalDate sudah yyyy-MM-dd)
        @RequestParam(required = false) LocalDate awal,
        @RequestParam(required = false) LocalDate akhir
    ){
        Integer idUser = (Integer) authentication.getCredentials();
        // 2. Logika "Default 7 Hari Terakhir"
        if (akhir == null) {
            akhir = LocalDate.now(); // Jika kosong, set ke hari ini, detik ini
        }
        if (awal == null) {
            awal = akhir.minusDays(7); // Jika kosong, set ke 7 hari sebelum tanggal akhir
        }

        // 2. UBAH KE DATETIME! 
        // Awal = 00:00:00, Akhir = 23:59:59
        LocalDateTime awalDateTime = awal.atStartOfDay(); 
        LocalDateTime akhirDateTime = akhir.atTime(23, 59, 59);
        return pinjamService.riwayatUser(idUser,page,size,sortBy,sortDir,awalDateTime,akhirDateTime);
    }
    
    @GetMapping("/pinjam/riwayat/stat")
    Map<String ,Long> riwayatStat(){
        Map<String ,Long> response=new HashMap<>();
        Long tersedia=  sepedaRepository.countByStatus("tersedia");
        Long dihapus= sepedaRepository.countByStatus("dihapus");
        Long dipinjam= sepedaRepository.countByStatus("dipinjam");

        response.put("dipinjam", dipinjam);
        response.put("dihapus", dihapus);
        response.put("tersedia", tersedia);
        return response;
    }

}