package com.kampus.sepedaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;;

@SpringBootApplication
public class SepedawebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SepedawebApplication.class, args);
    }

    // Fungsi ini akan otomatis berjalan satu kali setiap server menyala
    // @Bean
    // public CommandLineRunner insertAdmin(JdbcTemplate jdbcTemplate) {
    //     return args -> {
    //         try {
    //             // 1. Bersihkan user lama yang cacat
    //             jdbcTemplate.execute("DELETE FROM users WHERE username = 'sss'");
                
    //             // 2. Tembakkan user baru dari dalam sistem
    //             jdbcTemplate.execute("INSERT INTO users (id, username, password, role, kode_pegawai) VALUES (99, 'sss', '$2a$10$JOUj3w1eG4DsGFBa45vawuo3c2dRy9o1U78l8J7V0jjKtOyxbJ6O6', 'admin', 1212)");
                
    //             System.out.println("=========================================");
    //             System.out.println("✅ BERHASIL: AKUN ADMIN SUDAH DIBUAT!");
    //             System.out.println("=========================================");
    //         } catch (Exception e) {
    //             System.out.println("Gagal membuat akun: " + e.getMessage());
    //         }
    //     };
    // }

    // @Bean
    // public CommandLineRunner insertMahasiswa(JdbcTemplate jdbcTemplate) {
    //     return args -> {
    //         try {
    //             // 1. Hapus dulu akun ddd yang cacat (terpotong)
    //             jdbcTemplate.execute("DELETE FROM users WHERE username = 'ddd'");
                
    //             // 2. Tembakkan user mahasiswa dengan password BCrypt yang utuh
    //             jdbcTemplate.execute("INSERT INTO users (id, username, password, role, nim) VALUES (2, 'ddd', '$2a$12$PID8h9FD9JxS8mtLEhatC.SxlfOFeu/gD7N0EJHrTsxQb7tv83qrq', 'mahasiswa', '250102125')");
                
    //             System.out.println("=========================================");
    //             System.out.println("✅ BERHASIL: AKUN MAHASISWA (ddd) DIBUAT!");
    //             System.out.println("=========================================");
    //         } catch (Exception e) {
    //             System.out.println("Gagal membuat akun mahasiswa: " + e.getMessage());
    //         }
    //     };
    // }
}