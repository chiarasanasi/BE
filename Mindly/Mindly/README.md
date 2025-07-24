# Mindly – Take Care of You | Backend

Questo è il backend del progetto **Mindly**, una piattaforma fullstack ispirata a servizi come [unoBravo](https://www.unobravo.com/) e [Serenis](https://www.serenis.it/), pensata per mettere in contatto utenti e psicologi.

**Link repository frontend**: [https://github.com/chiarasanasi/FE](https://github.com/chiarasanasi/FE)  
**Link repository backend**: [https://github.com/chiarasanasi/BE](https://github.com/chiarasanasi/BE)


## Presentazione del progetto

Mindly permette agli utenti di registrarsi, compilare un questionario e ricevere uno psicologo compatibile. Dopo il login, gli utenti possono:
- inviare richieste di colloquio;
- gestire il proprio diario personale (note aggiungibili, modificabili e cancellabili);
- visualizzare gli appuntamenti confermati e lo storico.

Gli psicologi, invece, hanno accesso a un’area riservata in cui possono:
- consultare il proprio calendario;
- accettare o rifiutare richieste;
- visualizzare i profili dei clienti assegnati e le loro risposte al questionario;
- aggiungere note per ogni cliente, come osservazioni sul percorso terapeutico.


### Tecnologie utilizzate - Backend

- Java 17
- Spring Boot 3.5.3
- Spring Security con JWT
- Spring Data JPA
- PostgreSQL (hostato su Koyeb)
- Validazione con `spring-boot-starter-validation`
- Spring Mail
- Maven
- Lombok
- Test su Postman


## Deploy

- Koyeb per hosting backend e database PostgreSQL
- GitHub per il versionamento del codice


## Configurazione dell'ambiente locale

Per avviare correttamente il backend in locale, è **obbligatorio** creare un file chiamato `env.properties` nella directory principale del progetto.

Questo file contiene le variabili di ambiente necessarie per la connessione al database e per la gestione dei token JWT.

### Esempio di `env.properties`

pgUrl=jdbc:postgresql://localhost:5432/Mindly
postgresqlpassword=laTuaPasswordQui
jwtSecretKey=laTuaChiaveSegretaQui


##  Autrice

**Chiara Sanasi**  
 chiarasanasi.work@gmail.com  
[LinkedIn](https://www.linkedin.com/in/chiarasanasi/)
