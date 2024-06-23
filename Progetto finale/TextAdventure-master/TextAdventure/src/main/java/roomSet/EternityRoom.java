/**
 * Definizione della stanza di gioco
 */
package roomSet;

import base.Stobj;
import gameCore.Player;
import objectSet.Door;

public class EternityRoom extends RoomWDoor {

    boolean g = false;
    boolean h = false;
    public EternityRoom() {
        Door door = new Door();
        door.setName("Porta");
        door.setDescription("Una semplice porta di legno. E' aperta.");
        door.setOpen(true);
        door.setDirection("s");
        this.addObject(door);
        Stobj obj = new Stobj();
        obj.setName("Serratura");
        obj.setAlias(new String[] { "buco" });
        obj.setDescription("Una semplice serattura , sarebbe bello avere la chiave corretta per poterla aprire...");
        this.addObject(obj);
        Stobj weirdDoor = new Stobj();
        weirdDoor.setName("Strana porta");
        weirdDoor.setDescription("Una porta nera con un incavo a forma di cuore. Non sembra possa essere aperta.");
    }

    /**
     * Interazione per l'inserimento dello Stobj con nome "Chiave" e dello Stobj "Cuore pulsante"
     * se presente nella lista Inventory del Player p. 
     * Al momento dell'inserimento, viene aperto un passaggio.
     * 
     * @param p
     */
    @Override
    public void insert(Player p) {
        int k = -1;
        int l = -1;
        for (int i = 0; i < p.getInventory().size(); i++) {
            if (p.getInventory().get(i).getName().equals("Chiave") && !g) {
                g = true;
                k = i;
            }
            
            if (p.getInventory().get(i).getName().equals("Cuore pulsante") && g) {
                h = true;
                l = i;
            }
        }
        if (g && h) {
            this.setMsg("Inserisci nell'incavo sulla porta il cuore pulsante.\nDopo pochi istanti, il cuore smette di pulsare e la porta si apre.\nOra puoi proseguire verso nord.");
            p.removeFromInventory(l);
            this.getObjects().removeIf(obj -> obj.getName().equals("Strana porta"));
            this.setNorth(this.getNextNorth());
        } else if(g && !h){
            this.setMsg("Appena avvicini la chiave nella serratura, si inserisce di colpo, come se attratta da un magnete.\n Non riesci a capire cosa stia succedendo.\nLa parete a nord che sembrava essere così lontana è improvvisamente di fronte a te.\nOra che la parete è così vicina, vedi finalmente la porta.");
            p.removeFromInventory(k);
            this.getObjects().removeIf(obj -> obj.getName().equals("Serratura"));
            for (Stobj obj : this.getObjects()) {
                if (obj.getName().equals("Strana porta")) {
                    this.addObject(obj);
                    break;
                }
            }
        } else{
            this.setMsg("Non hai nulla da inserire da alcuna parte.");
        }
    }
}
