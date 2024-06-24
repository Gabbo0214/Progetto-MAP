package roomSet;

import gameInterface.UI;
import base.Room;
import base.Stobj;
import gameCore.Player;

public class DarkCrypt extends Room {

    private UI ui; // Riferimento all'istanza di UI

    public DarkCrypt(UI ui) {
        this.ui = ui; // Inizializzazione dell'istanza di UI
    }

    public DarkCrypt() {
        Stobj weepingStatue = new Stobj();
        Stobj plaque = new Stobj();
        Stobj demonStatue = new Stobj();
        Stobj pulsatingHeart = new Stobj();

        weepingStatue.setName("statua piangente");
        weepingStatue.setAlias(new String[] { "statua", "figura", "scultura" });
        weepingStatue
                .setDescription("La statua di una donna piangente.\nUna larga fessura è presente nel mezzo del petto.");
        weepingStatue.setPickupable(false);
        this.addObject(weepingStatue);

        plaque.setName("strana roccia");
        plaque.setAlias(new String[] { "masso", "sasso", "roccia" });
        plaque.setDescription(
                "Ispezionando più da vicino la roccia, è possibile scorgere una scritta incavata sulla superficie.\n" + //
                        "Recita: Il malvagio si maschera con le pelli dei giusti e degli innocenti.\n" + //
                        "Trafiggerai il suo cuore?");
        plaque.setPickupable(false);
        this.addObject(plaque);

        demonStatue.setName("statua inquietante");
        demonStatue.setAlias(new String[] { "statua", "demone", "scultura" });
        demonStatue.setDescription("La figura irrequieta trafitta dalla spada fa accaponare la pelle.\n" + //
                "Ti passa immediatamente la voglia di fissarla.");
        demonStatue.setPickupable(false);
        demonStatue.setVisible(false);
        this.addObject(demonStatue);

        pulsatingHeart.setName("cuore pulsante");
        pulsatingHeart.setAlias(new String[] { "cuore" });
        pulsatingHeart.setDescription("Ciò che sembra essere un cuore pulsante fatto di pietra.\n" + //
                "La bocca della statua lo ha lasciato cadere una volta che è stata trafitta.");
        pulsatingHeart.setPickupable(false);
        pulsatingHeart.setVisible(false);
        this.addObject(pulsatingHeart);
    }

    @Override
    public void insert(Player p) {
        boolean g = false;
        int k = -1;
        for (int i = 0; i < p.getInventory().size(); i++) {
            if (p.getInventory().get(i).getName().equals("Spada")) {
                g = true;
                k = i;
                break;
            }
        }
        if (g) {
            this.setMsg(
                    "Hai trafitto la statua con la tua spada.\nImprovvisamente, la faccia della donna sembra trasformarsi\nnel ghigno di quello che pare essere un demone.\nDalla bocca della statua cade qualcosa.");
            p.removeFromInventory(k);
            this.getObjects().removeIf(obj -> obj.getName().equals("statua piangente"));
            for (Stobj obj : this.getObjects()) {
                if (obj.getName().equals("statua inquietante")) {
                    obj.setVisible(true);
                    break;
                }
            }

            for (Stobj obj : this.getObjects()) {
                if (obj.getName().equals("cuore pulsante")) {
                    obj.setPickupable(true);
                    obj.setVisible(true);
                    break;
                }
            }
        } else {
            this.setMsg("Non hai nulla da inserire");
        }
    }

    // Classe Timer che implementa Runnable per un countdown tramite l'uso di un thread
    public class Timer implements Runnable {
        private volatile boolean running = true;
        private Player player;
    
        public Timer(Player player) {
            this.player = player;
        }
    
        @Override
        public void run() {
            int count = 30;
            while (count >= 0 && running) {
                System.out.println("Timer: " + count);
                ui.updateTimer(count); // Aggiorna l'interfaccia utente ogni secondo
                count--;
                try {
                    Thread.sleep(1000); // Pausa di un secondo
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Reimposta lo stato di interruzione
                    break; // Esce dal ciclo se interrotto
                }
            }
    
            if (running && count == 0) { // Verifica se il timer non è stato fermato
                handleTimeout();
            }
        }
    
        public void stop() {
            running = false;
        }
    
        private void handleTimeout() {
            player.setCurrentHp(player.getCurrentHp() - 10);
            DarkCrypt.this.getEast().setMsg("Le presenze sono diventate irrequiete! Sei rimasto nella stanza per troppo a lungo e ti hanno cacciato!\n\n" + "Sei tornato a: " + DarkCrypt.this.getEast().getName());
        }
    }
}