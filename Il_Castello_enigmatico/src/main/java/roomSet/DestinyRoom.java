package roomSet;

import base.Stobj;

public class DestinyRoom extends RoomWDoor {

    private boolean enigmaResolved = false;
    private Stobj anello;

    public DestinyRoom() {
        Stobj obj = new Stobj();

        obj.setName("pergamena");
        obj.setAlias(new String[]{"enigma", "indovinello"});
        obj.setDescription("Aprendo la pergamena riesci a leggere a stento:\n\n" +
                   "\"Tre mani del tempo si muovono senza sosta,\n" +
                   "Una traccia il passato, una il presente, una il futuro,\n" +
                   "Unisci i tre momenti in un solo pensiero,\n" +
                   "E svela la chiave che apre il tuo sentiero.\"\n\n" +
                   "Le parole della guardia all'entrata del castello risuonano nella tua mente,\n" +
                   "ti prepari a risolvere l'enigma finale.");
        this.addObject(obj);

        this.anello = new Stobj();
        this.anello.setName("Anello dell'eternità");
        this.anello.setAlias(new String[]{"anello"});
        this.anello.setDescription("Un anello misterioso che emana una luce tenue, simbolo di vita eterna.");
        this.anello.setPickupable(false);
    }

    /**
     * Interazione per la risoluzione dell'enigma
     */
    public void riddle2() {
        this.setMsg("Non riesci a crederci, sembra un sogno! L'anello è ora davanti a te. Raccoglilo ed indossalo per per raggiungere il tuo scopo!");
        for (int i = 0; i < this.getObjects().size(); i++) {
            if (this.getObjects().get(i).getName().equals("pergamena")) {
                this.getObjects().remove(i);
                break;
            }
        }

        this.enigmaResolved = true;
        this.anello.setPickupable(true);
        this.addObject(this.anello);
    }

    public boolean isEnigmaResolved() {
        return this.enigmaResolved;
    }
}