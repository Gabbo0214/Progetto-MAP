/**
 * Classe che traduce i comandi ricevuti in interazione di gioco
 */
package gameCore;

import base.Stobj;
import db.DatabaseConnection;
import gameInterface.VisibilityManager;
import objectSet.Door;
import roomSet.DarkCrypt;
import parser.ParserOutput;
import gameInterface.UI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.stream.Collectors;

public class Story {

    private Thread timerThread;
    private Thread speedrunTimerThread;
    private DarkCrypt.Timer timer;
    private UI ui;
    private SpeedRunTimer speedrunTimer;
    private boolean speedrunActive;

    public Story(UI ui) {
        this.timerThread = null;
        this.speedrunTimerThread = null;
        this.timer = null;
        this.ui = ui; // Initialize ui
        this.speedrunTimer = new SpeedRunTimer();
        this.speedrunActive = false;
        setUpNameInputListener();// Imposta Story come listener
    }

    private void setUpNameInputListener() {
        ui.setNameInputListener(new UI.NameInputListener() {
            @Override
            public void onNameInput(String name) {
                String nome = name;
                int timeINT = speedrunTimer.getSeconds();

                Time time = convertIntToTime(timeINT);
                
                insertIntoDatabase(nome, time);
            }
        });
    }

    // New method to stop the speedrun timer
    public void stopSpeedrunTimer() {
        if (speedrunActive) {
            speedrunTimer.stop();
            try {
                speedrunTimerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            speedrunActive = false;
        }
    }

    // New method to reset the speedrun timer
    public void resetSpeedrunTimer() {
        stopSpeedrunTimer();
        speedrunTimer = new SpeedRunTimer();
    }

    /**
     * Traduzione dei comandi ottenuti in ingresso già processati dal Parser e
     * contenuti in par. I comandi attivano determinate interazioni di gioco a
     * seconda della posizione nella mappa (Map map), eventualmente modificando
     * attributi del giocatore (Player p) per poi aggiornare l'interfaccia con
     * specifici messaggi tramite i metodi di VisbilityManager (vm)
     * 
     * @param par
     * @param p
     * @param map
     * @param vm
     * @return boolean end. True se si è raggiunta l'interazione finale del gioco,
     *         false altrimenti
     */
    public boolean nextMove(ParserOutput par, Player p, Map map, VisibilityManager vm) {
        boolean end = false;
        String uitxt = "";
        if (par.getCommand() == null) { // Se il parser non ha riconosciuto il comando inserito
            vm.writeOnScreen("Comando non riconosciuto.");
        } else {
            boolean noroom = false;
            boolean move = false;
            // Inserimento "nord" (o sinonimi). Modifica la stanza corrente con il relativo
            // nord
            if (par.getCommand().getName().equals("nord")) {
                if (map.getCurrentRoom().getNorth() != null) {
                    map.setPreviousRoom(map.getCurrentRoom());
                    map.setCurrentRoom(map.getCurrentRoom().getNorth());
                    move = true;
                } else {
                    noroom = true;
                }
            }

            // Inserimento "sud" (o sinonimi). Modifica la stanza corrente con il relativo
            // sud
            if (par.getCommand().getName().equals("sud")) {
                if (map.getCurrentRoom().getSouth() != null) {
                    map.setPreviousRoom(map.getCurrentRoom());
                    map.setCurrentRoom(map.getCurrentRoom().getSouth());
                    move = true;
                } else {
                    noroom = true;
                }
            }

            // Inserimento "est" (o sinonimi). Modifica la stanza corrente con il relativo
            // est
            if (par.getCommand().getName().equals("est")) {
                if (map.getCurrentRoom().getEast() != null) {
                    map.setPreviousRoom(map.getCurrentRoom());
                    map.setCurrentRoom(map.getCurrentRoom().getEast());
                    move = true;
                } else {
                    noroom = true;
                }
            }

            // Verifica se il giocatore è entrato nella stanza DarkCrypt e fa partire il
            // timer
            synchronized (this) {
                if (map.getCurrentRoom() == map.getDarkCrypt()) {
                    // Avvia il timer solo se non è già attivo
                    if (timerThread == null || !timerThread.isAlive()) {
                        DarkCrypt darkCrypt = (DarkCrypt) map.getCurrentRoom();
                        timer = darkCrypt.new Timer(p, ui, map);
                        timerThread = new Thread(timer);
                        timerThread.start();
                    }
                } else {
                    // Se usciamo dalla DarkCrypt, fermiamo il timer
                    if (timerThread != null && timerThread.isAlive()) {
                        timer.stop();
                        timerThread.interrupt();
                        timerThread = null;
                        timer = null;
                        ui.updateTimer(-1);
                    }
                }
            }

            // Inserimento "ovest" (o sinonimi). Modifica la stanza corrente con il relativo
            // ovest
            if (par.getCommand().getName().equals("ovest")) {
                if (map.getCurrentRoom().getWest() != null) {
                    map.setPreviousRoom(map.getCurrentRoom());
                    map.setCurrentRoom(map.getCurrentRoom().getWest());
                    move = true;
                } else {
                    noroom = true;
                }
            }

            // Inserimento "osserva" o sinonimi. Consente di osservare la stanza corrente,
            // elencandone gli oggetti presenti, o un oggetto specifico (della stanza o
            // nell'inventario).
            if (par.getCommand().getName().equals("osserva")) {
                if (par.getObject() == null) {
                    boolean forward = false;
                    uitxt = (map.getCurrentRoom().getDescription() + "\n\nLe direzioni in cui puoi proseguire sono: ");
                    if (map.getCurrentRoom().getNorth() != null) {
                        uitxt = (uitxt + " [Nord] ");
                        forward = true;
                    }
                    if (map.getCurrentRoom().getSouth() != null) {
                        uitxt = (uitxt + " [Sud] ");
                        forward = true;
                    }
                    if (map.getCurrentRoom().getWest() != null) {
                        uitxt = (uitxt + " [Ovest] ");
                        forward = true;
                    }
                    if (map.getCurrentRoom().getEast() != null) {
                        uitxt = (uitxt + " [Est]");
                        forward = true;
                    }
                    if (!forward)
                        uitxt = (map.getCurrentRoom().getDescription()
                                + "\n\nNon puoi proseguire in nessuna direzione: può darsi che il passaggio sia bloccato.");
                    if (map.getCurrentRoom().getObjects().size() > 0) {
                        uitxt = (uitxt + "\n\nCio' con cui puoi interagire in questo luogo: ");
                        uitxt += map.getCurrentRoom().getObjects().stream()
                                .filter(Stobj::isVisible)
                                .map(robj -> {
                                    StringBuilder sb = new StringBuilder("\n- " + robj.getName());
                                    if (robj.getName().equals("Porta")) {
                                        switch (((Door) robj).getDirection()) {
                                            case "n":
                                                sb.append(" (verso nord");
                                                break;
                                            case "s":
                                                sb.append(" (verso sud");
                                                break;
                                            case "w":
                                                sb.append(" (verso ovest");
                                                break;
                                            case "e":
                                                sb.append(" (verso est");
                                                break;
                                        }
                                        if (((Door) robj).isOpen())
                                            sb.append(", è aperta)");
                                        else
                                            sb.append(", è chiusa)");
                                    }
                                    return sb.toString();
                                })
                                .collect(Collectors.joining());
                    }
                } else if (par.getObject() != null && par.getObject().isVisible()) {
                    uitxt = map.getCurrentRoom().getObjects().stream()
                            .filter(obj -> obj.getName().equals(par.getObject().getName()))
                            .findFirst()
                            .map(Stobj::getDescription)
                            .orElseGet(() -> p.getInventory().stream()
                                    .filter(obj -> obj.getName().equals(par.getObject().getName()))
                                    .findFirst()
                                    .map(Stobj::getDescription)
                                    .orElse("Oggetto non trovato"));
                }
                vm.writeOnScreen(uitxt);
            }

            // Inserimento "raccogli" o sinonimi. Consente di raccogliere l'oggetto
            // specificato se presente nella stanza
            if (par.getCommand().getName().equals("raccogli")) {
                if (par.getObject() == null) {
                    vm.writeOnScreen("Non ho capito cosa vorresti raccogliere. Specifica cosa raccogliere.");
                } else {
                    for (int i = 0; i < map.getCurrentRoom().getObjects().size(); i++) {
                        if (map.getCurrentRoom().getObjects().get(i).getName().equals(par.getObject().getName())) {
                            if (map.getCurrentRoom().getObjects().get(i).isPickupable()) {
                                p.addToInventory(map.getCurrentRoom().getObjects().get(i));
                                vm.writeOnScreen(map.getCurrentRoom().getObjects().get(i).getName()
                                        + " aggiunto al tuo inventario");
                                map.getCurrentRoom().getObjects()
                                        .removeIf(obj -> obj.getName().equals(par.getObject().getName()));
                            } else {
                                vm.writeOnScreen("Non puoi raccogliere questo oggetto");
                            }
                        }
                    }
                }
            }

            // Inserimento "premi" o sinonimi SOLO per l'attivazione delle leve
            if (par.getCommand().getName().equals("premi")) {
                if (par.getObject() != null) {
                    if (par.getObject().getName().equals("Leva destra")
                            || par.getObject().getName().equals("Leva sinistra")
                            || par.getObject().getName().equals("Leva centrale")) {
                        map.getCurrentRoom().activate(par.getObject().getName().split("\\s")[1], p); // p.cR = map.pR,
                                                                                                     // p.pR = map.cR
                        if (par.getObject().getName().split("\\s")[1].equals("sinistra")
                                && par.getObject().isUsable()) {
                        }
                    }
                } else {
                    vm.writeOnScreen("Specifica con cosa vorresti interagire");
                }
            }

            // Inserimento "apri" o sinonimi (vale solo per le porte)
            if (par.getCommand().getName().equals("apri")) {
                if (par.getObject() != null) {
                    if (par.getObject().getName().equals("Porta")) {
                        map.getCurrentRoom().openDoor(par.getDir(), p);
                    }
                } else {
                    vm.writeOnScreen(
                            "L'oggetto a cui ti riferisci non esiste, non è apribile o lo hai scritto in modo incorretto.\nSpecifica cosa vorresti aprire");
                }
            }

            // Inserimento "chiudi" o sinonimi (vale solo per le porte)
            if (par.getCommand().getName().equals("chiudi")) {
                if (par.getObject() != null) {
                    if (par.getObject().getName().equals("Porta")) {
                        map.getCurrentRoom().closeDoor(par.getDir());
                    }
                } else {
                    vm.writeOnScreen(
                            "L'oggetto a cui ti riferisci non esiste, non è richiudibile o lo hai scritto in modo incorretto.\nSpecifica cosa vorresti chiudere");
                }
            }

            // Inserimento "silenzio " per risoluzione di riddle
            if (par.getCommand().getName().equals("silenzio")) {
                map.getCurrentRoom().riddle();
            }

            // Inserimento "adesso " per risoluzione di riddle2
            if (par.getCommand().getName().equals("adesso")) {
                map.getCurrentRoom().riddle2();
            }

            // Inserimento "indietro" o sinonimi. Porta alla stanza precedente
            if (par.getCommand().getName().equals("indietro")) {
                if (map.getPreviousRoom() != null) {
                    map.back();
                    move = true;
                } else {
                    vm.writeOnScreen("Non puoi tornare indietro.");
                }
            }

            // Inserimento "parla". Attiva l'interazione del dialogo con la persona
            // specificata
            if (par.getCommand().getName().equals("parla")) {
                if (par.getObject() != null) {
                    map.getCurrentRoom().talkTo(p, par.getObject());
                } else {
                    vm.writeOnScreen("Con chi vorresti parlare?");
                }
            }

            if (par.getCommand().getName().equals("infila")) {
                int k = -1;
                boolean usedRing = false;
                for (int i = 0; i < p.getInventory().size() && k == -1; i++) {
                    if (p.getInventory().get(i).getName().equals("Anello dell'eternità")) {
                        k = i;
                        usedRing = true;
                        end = true;
                    }
                }
                if (!usedRing) {
                    vm.writeOnScreen("Non hai nulla da indossare.");
                }
            }

            // Inserimento "usa" o sinonimi (vale solo per le pozioni e per il consiglio)
            if (par.getCommand().getName().equals("usa")) {
                if (par.getObject() != null) {
                    if (par.getObject().getName().equals("Pozione")) {
                        if (p.getCurrentHp() < p.getTotHp()) {
                            int k = -1;
                            for (int i = 0; i < p.getInventory().size() && k == -1; i++) {
                                if (p.getInventory().get(i).getName().equals("Pozione")) {
                                    k = i;
                                }
                            }
                            if (k > -1) {
                                String heal = p.getInventory().get(k).use(p);
                                vm.writeOnScreen(
                                        "Hai usato una pozione. La tua salute è stata ripristinata di " + heal + "HP");
                                p.removeFromInventory(k);
                            } else {
                                vm.writeOnScreen("Non hai pozioni");
                            }
                        } else {
                            vm.writeOnScreen("La tua salute è già al massimo, non hai bisogno di usare pozioni");
                        }
                    } else {
                        boolean use = false;
                        for (Stobj inv : p.getInventory()) {
                            if (par.getObject().isUsable()) {
                                if (inv.getName().equals(par.getObject().getName())) {
                                    vm.writeOnScreen(inv.use(p));
                                    use = true;
                                    break;
                                }
                            }
                        }
                        if (!use) {
                            vm.writeOnScreen("Non succede niente");
                        }
                    }
                } else {
                    vm.writeOnScreen("Specifica cosa vorresti usare");
                }
            }

            // Inserimento "inserisci". Attiva l'interazione per l'inserimento degli oggetti
            if (par.getCommand().getName().equals("inserisci")) {
                map.getCurrentRoom().insert(p);
            }

            if (!map.getCurrentRoom().getMsg().equals("")) { // Fa visualizzare il messaggio accumulato nelle
                                                             // interazioni delle diverse stanze sulla interfaccia
                if (map.getCurrentRoom().getMsg().equals("FINE"))
                    end = true;
                else {
                    vm.writeOnScreen(map.getCurrentRoom().getMsg());
                    if (p.getCurrentHp() <= 0) { // In caso di trappole che diminuiscono la vita, se la vita diventa <=
                                                 // 0, ciò viene notificato su interfaccia
                        vm.writeOnScreen(map.getCurrentRoom().getMsg() + " ma sei morto per la ferita...");
                        vm.defeatScreen();
                    }
                    map.getCurrentRoom().setMsg("");
                }
            }

            if (noroom) // In caso fosse impossibile proseguire nella direzione specificata
                vm.writeOnScreen("Non puoi proseguire in quella direzione");
            if (move) { // In caso fosse possibile proseguire nella direzione specificata, ne vengono
                        // visualizzati nome e descrizione della stanza di arrivo.
                uitxt = (map.getCurrentRoom().getName() + "\n================================================\n"
                        + map.getCurrentRoom().getDescription());
                vm.writeOnScreen(uitxt);
            }
        }
        return end;
    }

    public void start(VisibilityManager vm) {
        vm.writeOnScreen(
                "In questo gioco impersonerai un avventuriero in un ambiente fantasy.\nIl tuo obbiettivo è semplice quanto assurdo: trovare l'anello mistico che ti renderà immortale.\n\n"
                        +
                        "Nel caso avessi dubbi sul come proseguire, 'osservare' ciò che ti circonda può aiutare.\nPuoi 'usare' solo gli oggetti che possiedi nel tuo inventario.\n"
                        +
                        "Per spostarti 'prosegui'' in direzione dei punti cardinali NORD, SUD, EST e OVEST.\n(L'avventuriero seguirà i tuoi comandi coniugati all'imperativo seconda persona singolare)\n\nBuona Fortuna!");

        // Avvia la speedrun
        resetSpeedrunTimer();
        speedrunActive = true;
        speedrunTimerThread = new Thread(speedrunTimer);
        speedrunTimerThread.start();
    }

    public void ending(Player p, VisibilityManager vm) {

        String uitxt;
        uitxt = "Finalmente, l'anello dell'eternità è ora tra le tue mani. Una volta messo al dito la tua visione sparisce... cadi in un sonno profondo.\n";
        // Ferma la speedrun
        stopSpeedrunTimer();
        if(speedrunTimer.getSeconds() == 0){
            vm.writeOnExitScreen(uitxt
            + "\nQuanto tempo è passato? Al tuo risveglio, l'anello è ancora al tuo dito, ma non hai idea di dove ti trovi.\nUn vasto, piano prato di erba verde si estende per kilometri, niente oltre che l'erba è visibile fino all'orizzonte.\nIl cielo è illuminato da una grande luna piena. Cosa succederà ora?");
        }else{
            vm.writeOnNameScreen(uitxt
            + "\nQuanto tempo è passato? Al tuo risveglio, l'anello è ancora al tuo dito, ma non hai idea di dove ti trovi.\nUn vasto, piano prato di erba verde si estende per kilometri, niente oltre che l'erba è visibile fino all'orizzonte.\nIl cielo è illuminato da una grande luna piena. Cosa succederà ora?\n\n(Hai completato una run senza mai uscire!\nPer salvare il tuo tempo inserisci il tuo nome.)"); 
            vm.showNameInputScreen();  
        }
    }

    
    /**
     * Inserisce un record nella tabella CLASSIFICA.
     *
     * @param nome il nome da inserire
     * @param tempo il tempo da inserire
     */
    private void insertIntoDatabase(String nome, Time tempo) {
        String insertSQL = "INSERT INTO CLASSIFICA (USERNAME, TEMPO) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, nome);
            pstmt.setTime(2, tempo);

            pstmt.executeUpdate();
            conn.commit();

            DatabaseConnection.printClassificaFromDB();
            
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'inserimento nel database", e);
        }
    }
    
    public static Time convertIntToTime(int intTime) {
        // Estrai i minuti e i secondi dall'int
        int minutes = intTime / 100; // Ottiene i minuti
        int seconds = intTime % 100; // Ottiene i secondi

        // Costruisci un oggetto Time utilizzando valueOf
        String timeString = String.format("00:%02d:%02d", minutes, seconds);
        Time time = Time.valueOf(timeString); // Time.valueOf(String timeString)

        return time;
    }
}