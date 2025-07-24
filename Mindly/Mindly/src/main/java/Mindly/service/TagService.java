package Mindly.service;

import Mindly.model.Cliente;
import Mindly.model.Psicologo;
import Mindly.model.Risposta;
import Mindly.model.Tag;
import Mindly.repository.PsicologoRepository;
import Mindly.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagService {

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    private TagRepository tagRepository;

    private static final Map<String, String> dizionarioTag = Map.ofEntries(
            // Ansia e stress
            Map.entry("ansia", "Ansia"),
            Map.entry("stress", "Ansia"),

            // Depressione e tristezza
            Map.entry("triste", "Tristezza"),
            Map.entry("tristezza", "Tristezza"),
            Map.entry("depress", "Tristezza"),

            // Vita all'estero
            Map.entry("estero", "Estero"),
            Map.entry("fuori dall'italia", "Estero"),

            // Lavoro e studio
            Map.entry("lavoro", "Lavoro"),
            Map.entry("lavorativa", "Lavoro"),
            Map.entry("studio", "Lavoro"),
            Map.entry("decisioni", "Decisioni"),

            // Relazioni affettive e sociali
            Map.entry("affettiva", "Relazioni"),
            Map.entry("partner", "Relazioni"),
            Map.entry("familiari", "Relazioni"),
            Map.entry("amici", "Relazioni"),
            Map.entry("relazioni", "Relazioni"),
            Map.entry("sociali", "Relazioni"),

            // Crescita personale e identità
            Map.entry("crescita", "Crescita personale"),
            Map.entry("conoscermi", "Crescita personale"),
            Map.entry("svolta", "Crescita personale"),
            Map.entry("futuro", "Crescita personale"),

            // Autostima
            Map.entry("autostima", "Autostima"),
            Map.entry("credere in me", "Autostima"),
            Map.entry("percezione", "Autostima"),

            // Sessualità
            Map.entry("sessuale", "Sessualità"),
            Map.entry("sessualità", "Sessualità"),

            // Dipendenze
            Map.entry("dipendenza", "Dipendenze"),
            Map.entry("dipendenze", "Dipendenze"),

            // Trauma
            Map.entry("evento scioccante", "Trauma"),
            Map.entry("trauma", "Trauma"),
            Map.entry("spaventoso", "Trauma"),
            Map.entry("passato", "Trauma"),

            // Disturbi alimentari
            Map.entry("cibo", "Disturbi alimentari"),
            Map.entry("corpo", "Disturbi alimentari"),
            Map.entry("alimentazione", "Disturbi alimentari"),

            // Salute fisica
            Map.entry("fisiche", "Salute fisica"),
            Map.entry("mediche", "Salute fisica"),
            Map.entry("salute", "Salute fisica"),
            Map.entry("sonno", "Salute fisica"),

            // Emotività
            Map.entry("emotività", "Emotività"),
            Map.entry("emozioni", "Emotività"),

            // Difficoltà con la terapia
            Map.entry("giudizio", "Dubbi sulla terapia"),
            Map.entry("fraintenda", "Dubbi sulla terapia"),
            Map.entry("informazioni personali", "Dubbi sulla terapia"),
            Map.entry("aiutarmi", "Dubbi sulla terapia"),
            Map.entry("impegno", "Dubbi sulla terapia"),
            Map.entry("costo", "Dubbi sulla terapia"),

            // Comunicazione
            Map.entry("scrittura", "Comunicazione"),
            Map.entry("non verbali", "Comunicazione"),
            Map.entry("blocco", "Comunicazione")

    );


    public Psicologo assegnaPsicologo(Cliente cliente, List<Risposta> risposte) {
        List<String> tagDerivati = estraiTagDalleRisposte(risposte);
        String preferenzaGenere = estraiPreferenzaGenere(risposte);
        String preferenzaEta = estraiPreferenzaEta(risposte);

        List<Psicologo> tutti = psicologoRepository.findAll();

        Psicologo p = tutti.stream()
                .filter(ps -> preferenzaGenere == null || preferenzaGenere.equals("qualsiasi") || ps.getGenere().equalsIgnoreCase(preferenzaGenere))

                .filter(ps -> preferenzaEta.equals("qualsiasi") ||
                        (preferenzaEta.equals("over35") && ps.getEta() > 35) ||
                        (preferenzaEta.equals("under35") && ps.getEta() <= 35))
                .max(Comparator.comparingInt(ps -> contaMatch(ps.getTagList(), tagDerivati)))
                .orElseThrow(() -> new RuntimeException("Nessun psicologo compatibile"));


        System.out.println("\n Match trovato per il cliente: " + cliente.getUtente().getUsername());
        System.out.println(" Psicologo assegnato: " + p.getUtente().getUsername() + " (" + p.getGenere() + ", " + p.getEta() + " anni)");
        System.out.println(" Match su tag: " + tagDerivati);
        System.out.println(" Tag dello psicologo: " + p.getTagList().stream().map(Tag::getNome).toList());
        System.out.println(" Preferenze cliente - Genere: " + preferenzaGenere + " | Età: " + preferenzaEta);
        System.out.println("✔ Numero tag in comune: " + contaMatch(p.getTagList(), tagDerivati));
        System.out.println("--------------------------------------\n");

        return p;
    }


    private List<String> estraiTagDalleRisposte(List<Risposta> risposte) {
        Set<String> tagTrovati = new HashSet<>();
        for (Risposta r : risposte) {
            String risposta = r.getRisposta().toLowerCase();
            for (Map.Entry<String, String> entry : dizionarioTag.entrySet()) {
                if (risposta.contains(entry.getKey())) {
                    tagTrovati.add(entry.getValue());
                }
            }
        }
        return new ArrayList<>(tagTrovati);
    }

    private String estraiPreferenzaGenere(List<Risposta> risposte) {
        for (Risposta r : risposte) {
            if (r.getDomanda().toLowerCase().contains("genere")) {
                if (r.getRisposta().toLowerCase().contains("uomo")) return "uomo";
                if (r.getRisposta().toLowerCase().contains("donna")) return "donna";
            }
        }
        return "qualsiasi";
    }


    private String estraiPreferenzaEta(List<Risposta> risposte) {
        for (Risposta r : risposte) {
            if (r.getDomanda().toLowerCase().contains("età")) {
                if (r.getRisposta().contains("meno")) return "under35";
                if (r.getRisposta().contains("più")) return "over35";
            }
        }
        return "qualsiasi";
    }


    private int contaMatch(List<Tag> psicologoTags, List<String> clienteTags) {
        return (int) psicologoTags.stream()
                .map(Tag::getNome)
                .filter(clienteTags::contains)
                .count();
    }



    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
