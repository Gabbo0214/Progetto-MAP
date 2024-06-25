/**
 * Classe che traduce i comandi ricevuti in interazione di gioco
 */
package gameCore;

import base.Stobj;
import gameInterface.VisibilityManager;
import objectSet.Door;
import roomSet.DarkCrypt;
import parser.ParserOutput;
import gameInterface.UI;

public class Story {

    private Thread timerThread;
    private DarkCrypt.Timer timer;
    private UI ui;

    public Story(UI ui) {
        this.timerThread = null;
        this.timer = null;
        this.ui = ui; // Initialize ui
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

            // Verifica se il giocatore è entrato nella stanza DarkCrypt e fa partire il timer
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
            // nell'inventario). Inoltre raccoglie le monete
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
                        for (Stobj robj : map.getCurrentRoom().getObjects()) {
                            if (robj.isVisible()) {
                                uitxt = (uitxt + "\n- " + robj.getName());
                                if (robj.getName().equals("Porta")) {
                                    switch (((Door) robj).getDirection()) {
                                        case "n":
                                            uitxt = (uitxt + " (verso nord");
                                            break;
                                        case "s":
                                            uitxt = (uitxt + " (verso sud");
                                            break;
                                        case "w":
                                            uitxt = (uitxt + " (verso ovest");
                                            break;
                                        case "e":
                                            uitxt = (uitxt + " (verso est");
                                            break;
                                    }
                                    if (((Door) robj).isOpen())
                                        uitxt = (uitxt + ", è aperta)");
                                    else
                                        uitxt = (uitxt + ", è chiusa)");
                                }
                            }
                        }
                    }
                } else if (par.getObject() != null && par.getObject().isVisible()) {
                    int k = -1;
                    for (int i = 0; i < map.getCurrentRoom().getObjects().size() && k == -1; i++) {
                        if (map.getCurrentRoom().getObjects().get(i).getName().equals(par.getObject().getName())) {
                            k = i;
                        }
                    }
                    if (k > -1) {
                        uitxt = (map.getCurrentRoom().getObjects().get(k).getDescription());
                    } else {
                        if (p.getInventory() != null) {
                            for (int i = 0; i < p.getInventory().size() && k == -1; i++) {
                                if (p.getInventory().get(i).getName().equals(par.getObject().getName())) {
                                    k = i;
                                }
                            }
                            uitxt = (p.getInventory().get(k).getDescription());
                        }
                    }
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
            // Inserimento "inventario". Consente di visualizzare l'elenco di ciò che è
            // contenuto nell'inventario
            if (par.getCommand().getName().equals("inventario")) {
                if (!p.getInventory().isEmpty()) {
                    uitxt = ("Oggetti attualmente nel tuo inventario:");
                    for (Stobj inv : p.getInventory()) {
                        uitxt = (uitxt + "\n- " + inv.getName());
                    }
                } else
                    uitxt = ("Non hai niente nello zaino");
                vm.writeOnScreen(uitxt);
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

            // Inserimento "compra" o sinonimi (vale solo in blksmith o alchemshop). Attiva
            // l'interazione per l'acquisto dell'oggetto specificato
            if (par.getCommand().getName().equals("compra")) {
                if (par.getObject() != null) {
                    map.getCurrentRoom().buy(p, par.getObject());
                } else {
                    vm.writeOnScreen(
                            "Devi specificare cosa vuoi comprare, nessuno può capire i tuoi bisogni meglio di te.\nPuò darsi che tu abbia già comprato quell'articolo o che non sia disponibile.");
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

            // Inserimento "bevi" (vale solo per le pozioni)
            if (par.getCommand().getName().equals("bevi")) {
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
                                        "Hai bevuto una pozione. La tua salute è stata ripristinata di " + heal + "HP");
                                p.removeFromInventory(k);
                            } else {
                                vm.writeOnScreen("Non hai pozioni.");
                            }
                        } else {
                            vm.writeOnScreen("La tua salute è già al massimo, non hai bisogno di bere pozioni.");
                        }
                    } else {
                        vm.writeOnScreen("Non puoi berlo.");
                    }
                } else {
                    int pot = 0;
                    for (Stobj inv : p.getInventory()) {
                        if (inv.getName().equals("Pozione")) {
                            pot++;
                        }
                    }
                    if (pot > 0) {
                        vm.writeOnScreen("Hai ancora " + pot + "x Pozione che puoi bere.\n");
                    } else {
                        vm.writeOnScreen("Non hai nulla da bere.");
                    }
                }
            }

            // Inserimento "leggi" (vale solo per il consiglio della ninfa)
            if (par.getCommand().getName().equals("leggi")) {
                if (par.getObject() != null) {
                    if (par.getObject().getName().equals("Consiglio")) {
                        for (Stobj inv : p.getInventory()) {
                            if (inv.getName().equals("Consiglio")) {
                                vm.writeOnScreen(inv.getDescription());
                                break;
                            }
                        }
                    } else {
                        vm.writeOnScreen("Non puoi leggerlo.");
                    }
                } else {
                    boolean adv = false;
                    for (Stobj inv : p.getInventory()) {
                        if (inv.getName().equals("Consiglio")) {
                            adv = true;
                            break;
                        }
                    }
                    if (adv) {
                        vm.writeOnScreen("L'unica cosa che hai da leggere è il consiglio che ti ha dato la ninfa.");
                    } else {
                        vm.writeOnScreen("Non c'è nulla dal leggere");
                    }
                }
            }

            // Inserimento "mangia" (vale solo per il frutto)
            if (par.getCommand().getName().equals("mangia")) {
                if (par.getObject() != null) {
                    if (par.getObject().getName().equals("Frutto")) {
                        if (p.getCurrentHp() < p.getTotHp()) {
                            for (Stobj inv : p.getInventory()) {
                                if (inv.getName().equals("Frutto")) {
                                    int x = p.getCurrentHp() + 20 - p.getTotHp();
                                    if (x >= 0) {
                                        p.setCurrentHp(p.getTotHp());
                                        vm.writeOnScreen("Hai mangiato il frutto che ti ha saziato completamente!");
                                    } else {
                                        p.setCurrentHp(p.getCurrentHp() + 20);
                                        vm.writeOnScreen("Hai mangiato il frutto e hai recuperato 20 HP");
                                    }
                                    p.getInventory().remove(inv);
                                    break;
                                }
                            }
                        } else {
                            vm.writeOnScreen("La tua salute è già al massimo, non hai particolarmente fame.");
                        }
                    } else {
                        vm.writeOnScreen("Non puoi mangiarlo");
                    }
                } else {
                    boolean f = false;
                    for (Stobj inv : p.getInventory()) {
                        if (inv.getName().equals("Frutto")) {
                            f = true;
                            break;
                        }
                    }
                    if (f) {
                        vm.writeOnScreen("L'unica cosa che hai da mangiare è il frutto che hai raccolto.");
                    } else {
                        vm.writeOnScreen("Non hai nulla da mangiare");
                    }
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
    }

    public void ending(Player p, VisibilityManager vm) {
        String uitxt;
        uitxt = "Finalmente, l'anello dell'eternità è ora tra le tue mani. Una volta messo al dito la tua visione sparisce... cadi in un sonno profondo.\n";
        vm.writeOnExitScreen(uitxt
                + "\nQuanto tempo è passato? Al tuo risveglio, l'anello è ancora al tuo dito, ma non hai idea di dove ti trovi.\nUn vasto, piano prato di erba verde si estende per kilometri, niente oltre che l'erba è visibile fino all'orizzonte.\nIl cielo è illuminato da una grande luna piena. Cosa succederà ora?");
    }
}