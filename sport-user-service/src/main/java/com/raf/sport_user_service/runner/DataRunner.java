package com.raf.sport_user_service.runner;

import com.raf.sport_user_service.domain.Role;
import com.raf.sport_user_service.domain.User;
import com.raf.sport_user_service.repository.RoleRepository;
import com.raf.sport_user_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataRunner implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DataRunner(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Proverimo i ubacimo uloge ako ne postoje
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN").orElse(null);
        Role roleClient = roleRepository.findByName("ROLE_CLIENT").orElse(null);
        Role roleTrainer = roleRepository.findByName("ROLE_TRAINER").orElse(null);

        if (roleAdmin == null) {
            roleAdmin = new Role("ROLE_ADMIN", "Administrator sistema");
            roleRepository.save(roleAdmin);
        }
        if (roleClient == null) {
            roleClient = new Role("ROLE_CLIENT", "Korisnik koji zakazuje treninge");
            roleRepository.save(roleClient);
        }
        if (roleTrainer == null) {
            roleTrainer = new Role("ROLE_TRAINER", "Trener koji drzi treninge");
            roleRepository.save(roleTrainer);
        }

        // 2. Napravimo jednog Admina da imamo s čim da se ulogujemo odmah

        // OVDE JE PROMENA: Trazimo admina, a ako ga nema, dobijamo NULL
        User existingAdmin = userRepository.findByUsername("admin").orElse(null);

        // Ako je existingAdmin null, to znači da korisnik ne postoji i pravimo ga
        if (existingAdmin == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword("admin123");

            Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
            admin.getRoles().add(adminRole);

            userRepository.save(admin);
            System.out.println("Admin korisnik kreiran: admin / admin123");
        }
    }


}
