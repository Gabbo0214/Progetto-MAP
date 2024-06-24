package roomSet;

import base.Stobj;
import gameCore.Player;

public class EternityRoom extends RoomWDoor {

    private boolean g = false; // Variabile di istanza per mantenere lo stato tra le chiamate
    private boolean h = false; // Variabile di istanza per mantenere lo stato tra le chiamate

    public EternityRoom() {
        Stobj obj = new Stobj();
        obj.setName("Serratura");
        obj.setAlias(new String[] { "buco" });
        obj.setDescription("Una semplice serratura, sarebbe bello avere la chiave corretta per poterla aprire...");
        this.addObject(obj);
        
        Stobj weirdDoor = new Stobj();
        weirdDoor.setName("strana porta");
        weirdDoor.setDescription("Una porta nera con un incavo a forma di cuore. Non sembra possa essere aperta.");
        weirdDoor.setVisible(false);
        this.addObject(weirdDoor);
    }

    @Override
    public void insert(Player p) {
        int k = -1;
        int l = -1;
        
        // Cerca la chiave nell'inventario
        if (!g) { // Solo se g è false, cerchiamo la chiave
            for (int i = 0; i < p.getInventory().size(); i++) {
                if (p.getInventory().get(i).getName().equals("Chiave")) {
                    g = true;
                    k = i;
                    break;
                }
            }
        }
        
        // Cerca il cuore pulsante nell'inventario
        if (g && !h) { // Solo se g è true e h è false, cerchiamo il cuore pulsante
            for (int i = 0; i < p.getInventory().size(); i++) {
                if (p.getInventory().get(i).getName().equals("Cuore pulsante")) {
                    h = true;
                    l = i;
                    break;
                }
            }
        }
        
        // Gestisci l'inserimento degli oggetti in base allo stato di g e h
        if (g && h) {
            this.setMsg("Inserisci nell'incavo sulla porta il cuore pulsante.\nDopo pochi istanti, il cuore smette di pulsare e la porta si apre.\nOra puoi proseguire verso nord.");
            if (l != -1) {
                p.removeFromInventory(l);
            }
            this.getObjects().removeIf(obj -> obj.getName().equals("strana porta"));
            this.setNorth(this.getNextNorth());
        } else if (g) {
            this.setMsg("Appena avvicini la chiave nella serratura, si inserisce di colpo, come se attratta da un magnete.\nNon riesci a capire cosa stia succedendo.\nLa parete a nord che sembrava essere così lontana è improvvisamente di fronte a te.\nOra che la parete è così vicina, vedi finalmente la porta.");
            if (k != -1) {
                p.removeFromInventory(k);
            }
            this.getObjects().removeIf(obj -> obj.getName().equals("Serratura"));
            for (Stobj obj : this.getObjects()) {
                if (obj.getName().equals("strana porta")) {
                    obj.setVisible(true);
                    break;
                }
            }
        } else {
            this.setMsg("Non hai nulla da inserire da alcuna parte.");
        }
    }
}