package com.raf.sport_user_service.service.impl;
import com.raf.sport_user_service.domain.Role;
import com.raf.sport_user_service.domain.User;
import com.raf.sport_user_service.dto.*;
import com.raf.sport_user_service.mapper.UserMapper;
import com.raf.sport_user_service.repository.RoleRepository;
import com.raf.sport_user_service.repository.UserRepository;
import com.raf.sport_user_service.security.service.TokenService;
import com.raf.sport_user_service.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional //sve operacije nad bazom unutar jedne metode izvršiti kao jedna celina
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final UserMapper userMapper;
    private final RestTemplate restTemplate; // Za poziv drugom servisu

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           TokenService tokenService, UserMapper userMapper, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDto register(UserCreateDto userCreateDto) {
        // 1. Mapiranje DTO u User entitet
        User user = userMapper.userCreateDtoToUser(userCreateDto);

        // 2. Pronalaženje uloge u bazi
        //  ROLE_CLIENT, ROLE_TRAINER
        Role role = roleRepository.findByName(userCreateDto.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found: " + userCreateDto.getRoleName()));

        user.getRoles().add(role);
        user.setRegistrationDate(LocalDate.now());

        // 3. Čuvanje u bazu (Auth Service)
        User savedUser = userRepository.save(user);

        // 4. SINHRONA KOMUNIKACIJA - SAMO ZA TRENERA
        // Ako je korisnik trener, šaljemo njegove podatke u Servis za Sportske Termine
        if ("ROLE_TRAINER".equals(role.getName())) {
            try {
                System.out.println("Detektovan trener! Šaljem podatke u Sports Service...");
                // URL drugog servisa (koristim ime servisa 'sports-service' jer imam Eureku)
                String sportsServiceUrl = "http://sports-service/api/trainers";//KAD NAPRAVIM EUREKU

               // String sportsServiceUrl = "http://localhost:8081/api/trainers";//PRIVREMENO DOK NE BUDE EUREKAA
                // Šaljem ceo DTO (jer on sadrži ime, prezime, specijalnost)
                restTemplate.postForEntity(sportsServiceUrl, userCreateDto, Void.class);

                System.out.println("Trener uspešno poslat!");
            } catch (Exception e) {
                // Samo logujemo grešku da ne srušimo registraciju
                System.err.println("GREŠKA pri slanju trenera: " + e.getMessage());
            }
        }

        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        // Tražimo korisnika po email-u
        User user = userRepository.findByEmail(tokenRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Korisnik sa tim email-om ne postoji!"));

        // Provera lozinke
        if (!user.getPassword().equals(tokenRequestDto.getPassword())) {
            throw new RuntimeException("Pogrešna lozinka!");
        }

        // Generisanje tokena
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        if (!user.getRoles().isEmpty()) {
            claims.put("role", user.getRoles().get(0).getName());
        }

        return new TokenResponseDto(tokenService.generate(claims));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto update(Long id, UserCreateDto userCreateDto) {
        // 1. Nađi korisnika
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Ažuriraj podatke (samo one koji smeju da se menjaju, mogu sve osim uloge)
        if (userCreateDto.getUsername() != null) user.setUsername(userCreateDto.getUsername());
        if (userCreateDto.getEmail() != null) user.setEmail(userCreateDto.getEmail());

        // Ako šalje novu šifru
        if (userCreateDto.getPassword() != null && !userCreateDto.getPassword().isEmpty()) {
            user.setPassword(userCreateDto.getPassword());
        }

        // Napomena: Ne menjamo ulogu (user.getRoles()) jer to admin radi posebno

        // 3. Sačuvaj
        User savedUser = userRepository.save(user);

        // 4. Vrati DTO
        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public void createRole(CreateRoleDto createRoleDto) {
        // Proverimo da li uloga već postoji
        if (roleRepository.findByName(createRoleDto.getName()).isPresent()) {
            throw new RuntimeException("Role already exists: " + createRoleDto.getName());
        }
        // Kreiramo i čuvamo
        Role role = new Role(createRoleDto.getName(), createRoleDto.getDescription());
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void assignRole(AssignRoleDto assignRoleDto) {
        // 1. Nađemo korisnika
        User user = userRepository.findByEmail(assignRoleDto.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Nađemo ulogu
        Role role = roleRepository.findByName(assignRoleDto.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // 3. Dodamo ulogu u listu (ako je već nema)
        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userRepository.save(user);
        }

    }
}
