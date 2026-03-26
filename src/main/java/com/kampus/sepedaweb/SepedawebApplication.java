package com.kampus.sepedaweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

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
}