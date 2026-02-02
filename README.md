# Sistem za rezervaciju sportskih termina

Projekat je razvijen u okviru fakultetskog predmeta sa ciljem implementacije mikroservisne aplikacije za upravljanje i rezervaciju sportskih termina i treninga.
Sistem omogućava korisnicima pregled i rezervaciju termina, dok treneri i administratori upravljaju sportskim grupama, terenima i rasporedima treninga.
Aplikacija je realizovana kao skup mikroservisa razvijenih u Java Spring okruženju, uz korišćenje API Gateway-a i Service Discovery mehanizama za komunikaciju između servisa.

## Servisi
- Korisnički servis – registracija, prijava i upravljanje korisnicima
- Servis za sportske termine – upravljanje terenima, trenerima i terminima
- Servis za notifikacije – slanje obaveštenja o rezervacijama i izmenama termina

## Tehnologije
Java, Spring Boot, REST API, API Gateway, Eureka Service Discovery, JWT autentifikacija.

## Pokretanje projekta
Svaki servis se pokreće kao zasebna Spring Boot aplikacija pomoću Maven komandi ili direktno iz IDE okruženja.
