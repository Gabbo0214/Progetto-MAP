package roomSet;

import base.Stobj;

public class DestinyRoom extends RoomWDoor {

    private boolean enigmaResolved = false; // Track if the enigma is resolved
    private Stobj anello; // Object representing Anello dell'eternità

    public DestinyRoom() {
        Stobj obj = new Stobj();

        obj.setName("pergamena");
        obj.setAlias(new String[]{"enigma", "indovinello"});
        obj.setDescription("Aprendo la pergamena riesci a leggere a stento:\n\n" +
                   "\"Tre mani del tempo si muovono senza sosta,\n" +
                   "Una traccia il passato, una il presente, una il futuro,\n" +
                   "Unisci i tre momenti in un solo pensiero,\n" +
                   "E svela la chiave che apre il tuo sentiero.\"\n\n" +
                   "Sapendo che la chiave risiede nella comprensione del passato, presente e futuro,\n" +
                   "ti prepari a risolvere l'enigma finale.");

        this.addObject(obj);

        // Initialize Anello dell'eternità but don't make it pickupable yet
        this.anello = new Stobj();
        this.anello.setName("Anello dell'eternità");
        this.anello.setAlias(new String[]{"anello"});
        this.anello.setDescription("Un anello misterioso che emana una luce tenue, simbolo di vita eterna.");
        this.anello.setPickupable(false); // Initially not pickupable
    }

    /**
     * Interazione per la risoluzione dell'enigma
     */
    public void riddle2() {
        this.setMsg("Non riesci a crederci, sembra un sogno! Sei riuscito a prendere l’anello dell’eternità, che ti permetterà di avere la vita eterna e svelare tutti gli enigmi di questo castello. Il tuo viaggio finalmente giunge al termine. Complimenti, giovane avventuriero, ce l'hai fatta!!!");
        for (int i = 0; i < this.getObjects().size(); i++) {
            if (this.getObjects().get(i).getName().equals("pergamena")) {
                this.getObjects().remove(i);
                break;
            }
        }

        // Mark the enigma as resolved
        this.enigmaResolved = true;

        // Now make Anello dell'eternità pickupable
        this.anello.setPickupable(true);
        // Add Anello dell'eternità to the room's objects (now it's visible)
        this.addObject(this.anello);
    }

    public boolean isEnigmaResolved() {
        return this.enigmaResolved;
    }
}