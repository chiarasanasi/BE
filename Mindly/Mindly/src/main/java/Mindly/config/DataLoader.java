package Mindly.config;

import Mindly.enumaration.Ruolo;
import Mindly.model.Psicologo;
import Mindly.model.Tag;
import Mindly.model.Utente;
import Mindly.repository.PsicologoRepository;
import Mindly.repository.TagRepository;
import Mindly.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UtenteRepository utenteRepo;

    @Autowired
    private PsicologoRepository psicologoRepo;

    @Autowired
    private TagRepository tagRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (tagRepo.count() == 0) {
            List<String> nomiTag = List.of(
                    "Ansia",
                    "Tristezza",
                    "Estero",
                    "Lavoro",
                    "Relazioni",
                    "Crescita personale",
                    "Autostima",
                    "Sessualità",
                    "Dipendenze",
                    "Trauma",
                    "Disturbi alimentari",
                    "Salute fisica",
                    "Emotività",
                    "Dubbi sulla terapia",
                    "Comunicazione"
            );

            for (String nome : nomiTag) {
                Tag t = new Tag();
                t.setNome(nome);
                tagRepo.save(t);
            }
        }

        List<Tag> tuttiTag = tagRepo.findAll();

        if (psicologoRepo.count() == 0) {
            List<Psicologo> psicologi = new ArrayList<>();

            psicologi.add(nuovoPsicologo("Mario", "Rossi", "uomo", 45, "Psicoterapia cognitivo-comportamentale", List.of("Ansia", "Depressione"), "mario.rossi", "Psicoterapeuta con oltre 20 anni di esperienza, Mario Rossi è specializzato in disturbi d'ansia e depressione. Il suo approccio cognitivo-comportamentale mira a fornire strumenti pratici e strategie efficaci per affrontare pensieri negativi, attacchi di panico e sintomi depressivi. Ha lavorato sia in contesti clinici che privati, seguendo pazienti adulti in percorsi di consapevolezza e resilienza emotiva. Il suo stile è diretto ma empatico, puntando a creare un'alleanza terapeutica forte e duratura. Appassionato di neuroscienze, integra la psicoeducazione nei suoi percorsi per aumentare la comprensione del proprio funzionamento mentale. Lavora anche con tecniche di mindfulness e rilassamento per aiutare i pazienti a gestire lo stress quotidiano."));

            psicologi.add(nuovoPsicologo("Lucia", "Bianchi", "donna", 32, "Disturbi d'ansia", List.of("Ansia", "Autostima"), "lucia.bianchi", "Lucia Bianchi è una psicologa dinamica e sensibile, specializzata nei disturbi d’ansia e nell’incremento dell’autostima. Utilizza approcci integrati tra cognitivo-comportamentale e terapia centrata sulla persona. Aiuta i suoi pazienti a riconoscere e affrontare i propri schemi di pensiero disfunzionali e a ritrovare fiducia nelle proprie capacità. Ha una particolare esperienza nel lavoro con giovani adulti e persone in transizione di vita. Il suo lavoro terapeutico è orientato alla costruzione di un senso di sicurezza interiore, affrontando le paure e potenziando la capacità di autodeterminazione. È conosciuta per il suo ascolto profondo e per la capacità di mettere a proprio agio anche chi è alla prima esperienza in terapia."));

            psicologi.add(nuovoPsicologo("Giulia", "Verdi", "donna", 29, "Supporto adolescenziale", List.of("Adolescenza", "Problemi relazionali"), "giulia.verdi", "Giulia Verdi si dedica al supporto psicologico di adolescenti e giovani adulti, affrontando problematiche legate alla crescita, all’identità e ai conflitti relazionali. Il suo stile terapeutico è accogliente, autentico e rispettoso delle sfumature dell’età evolutiva. Lavora per creare un ambiente sicuro dove i ragazzi possano esprimersi senza giudizio, esplorando emozioni, paure e sogni. Ha esperienza in contesti scolastici e in centri di ascolto, e si occupa anche di mediazione familiare. Il suo obiettivo è rafforzare l’autonomia emotiva dei giovani, aiutandoli a costruire relazioni più sane e consapevoli. Integra nel suo lavoro elementi di arte-terapia e comunicazione non violenta."));

            psicologi.add(nuovoPsicologo("Francesco", "Gallo", "uomo", 38, "Dipendenze", List.of("Dipendenze", "Trauma"), "francesco.gallo", "Con una formazione mirata sui comportamenti compulsivi, Francesco Gallo lavora da oltre 15 anni nel campo delle dipendenze e dei traumi. Ha seguito persone con problematiche legate a sostanze, gioco d’azzardo, dipendenze affettive e traumi complessi. Il suo approccio è centrato sulla persona, con attenzione alla storia di vita e alla ricostruzione dell’identità personale. Utilizza la terapia EMDR per l’elaborazione di eventi traumatici e promuove percorsi di empowerment e ricostruzione dell’autoefficacia. Crede nella forza della relazione terapeutica come motore del cambiamento e accompagna i pazienti in un lavoro profondo ma rispettoso dei tempi individuali."));

            psicologi.add(nuovoPsicologo("Sofia", "Russo", "donna", 41, "Terapia di coppia", List.of("Problemi relazionali", "Lutto"), "sofia.russo", "Specializzata in terapia di coppia e relazioni affettive, Sofia Russo aiuta individui e partner a comprendere le dinamiche disfunzionali, comunicare in modo efficace e ritrovare la connessione emotiva. Lavora anche con pazienti singoli che stanno elaborando lutti o separazioni dolorose. La sua formazione sistemico-relazionale le consente di esplorare i legami familiari e gli schemi relazionali inconsci che si ripetono nel tempo. Ha una sensibilità particolare nel trattare le tematiche di perdita, con uno stile empatico e contenitivo. Favorisce l’elaborazione del dolore attraverso tecniche narrative e simboliche, aiutando le persone a ricostruire un senso dopo la perdita."));

            psicologi.add(nuovoPsicologo("Luca", "Ferrari", "uomo", 34, "Disturbi alimentari", List.of("Disturbi alimentari", "Autostima"), "luca.ferrari", "Luca Ferrari è un terapeuta che lavora con persone che affrontano difficoltà legate all’alimentazione, all’immagine corporea e all’autostima. Il suo approccio si basa sulla comprensione empatica e sulla promozione di un rapporto più sano con il proprio corpo. Ha lavorato in centri per disturbi alimentari e segue percorsi individuali e familiari. Integrazione tra mente e corpo è al centro della sua pratica: utilizza tecniche cognitivo-comportamentali ma anche strumenti come il diario alimentare emotivo e la psicoeducazione. Lavora anche su dinamiche di controllo e perfezionismo, promuovendo accettazione e flessibilità psicologica."));

            psicologi.add(nuovoPsicologo("Alessia", "Marini", "donna", 27, "Crescita personale", List.of("Autostima", "Ansia"), "alessia.marini", "Psicologa giovane ma già molto apprezzata, Alessia Marini si occupa di crescita personale e sviluppo dell’autostima. Il suo lavoro è centrato sull’attivazione delle risorse personali, aiutando i pazienti a identificare i propri punti di forza e superare le insicurezze. È particolarmente attenta al linguaggio interno e alla narrativa personale, guidando le persone in percorsi di riscrittura di sé e delle proprie esperienze. Si rivolge a chi sente di essere in un momento di stallo, confusione o bassa motivazione, e desidera riscoprire un senso nella propria vita. Lavora con esercizi pratici, visualizzazioni e obiettivi concreti."));

            psicologi.add(nuovoPsicologo("Paolo", "Colombo", "uomo", 50, "Traumi e lutto", List.of("Trauma", "Lutto"), "paolo.colombo", "Paolo Colombo è un terapeuta esperto in elaborazione del lutto e traumi relazionali. Ha una lunga carriera alle spalle e ha affiancato persone in momenti di grande vulnerabilità, come la perdita di una persona cara, separazioni o esperienze traumatiche non elaborate. Il suo approccio è profondo e rispettoso, con un’attenzione particolare alla dimensione emotiva e simbolica del dolore. Integra tecniche di rielaborazione narrativa con un ascolto attento e non giudicante. Lavora anche con adulti che hanno vissuto traumi infantili, aiutandoli a ricostruire sicurezza interna e stabilità relazionale. È noto per la sua presenza calma e rassicurante."));

            psicologi.add(nuovoPsicologo("Martina", "Romano", "donna", 36, "Burnout da lavoro", List.of("Stress lavoro-correlato", "Depressione"), "martina.romano", "Martina Romano si occupa di prevenzione e trattamento del burnout e dei disturbi dell’umore legati al lavoro. Lavora con professionisti stressati, caregiver, insegnanti, operatori sanitari e chiunque senta di aver perso il senso delle proprie giornate. Il suo lavoro parte dall’ascolto del corpo e delle emozioni per ricostruire un equilibrio tra dovere e piacere. Usa strumenti di gestione dello stress, assertività e tecniche di mindfulness. Aiuta i pazienti a riscoprire la loro motivazione e a impostare obiettivi più sostenibili. Ha una grande capacità di contenimento e lavora in modo personalizzato in base alla fase di vita."));

            psicologi.add(nuovoPsicologo("Giorgio", "Conti", "uomo", 30, "Problemi relazionali", List.of("Problemi relazionali", "Ansia"), "giorgio.conti", "Giorgio Conti è uno psicologo specializzato in relazioni interpersonali e dinamiche familiari. Aiuta persone che vivono difficoltà nella coppia, con i genitori o nei rapporti di lavoro. Ha una visione sistemica dei problemi, che integra con elementi di psicoterapia breve strategica. Favorisce una comunicazione più efficace, la gestione dei conflitti e la comprensione delle emozioni sottostanti. Il suo lavoro è concreto e orientato al cambiamento: propone esercizi tra le sedute e strategie da applicare nella quotidianità. È molto apprezzato per la sua capacità di creare alleanza terapeutica anche con i pazienti più diffidenti."));

            psicologoRepo.saveAll(psicologi.stream().filter(Objects::nonNull).toList());
        }
    }

    private Psicologo nuovoPsicologo(String nome, String cognome, String genere, int eta, String specializzazione, List<String> tagNomi, String username, String descrizione) {

        if (utenteRepo.findByUsername(username).isPresent()) {
            System.out.println("Utente già esistente, salto: " + username);
            return null;
        }

        Utente u = new Utente();
        u.setNome(nome);
        u.setCognome(cognome);
        u.setEmail(username + "@mindly.it");
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode("password"));
        u.setRuoli(Set.of(Ruolo.PSICOLOGO));
        u.setImmagineProfilo("https://api.dicebear.com/7.x/thumbs/svg?seed=" + username);
        u = utenteRepo.saveAndFlush(u);

        List<Tag> tagList = tagRepo.findAll().stream()
                .filter(tag -> tagNomi.contains(tag.getNome()))
                .toList();

        Psicologo p = new Psicologo();
        p.setUtente(u);
        p.setGenere(genere);
        p.setEta(eta);
        p.setSpecializzazione(specializzazione);
        p.setTagList(tagList);
        p.setDescrizione(descrizione);

        return p;
    }
}





