/**
 * Definizione di sala del tempo
 */
package roomSet;

import base.Stobj;
import gameCore.Player;
import objectSet.Door;

public class TimeRoom extends RoomWDoor {

    public TimeRoom() {
        Stobj bookPast = new Stobj();
        Stobj bookPresent = new Stobj();
        Stobj bookFuture = new Stobj();
        Door door = new Door();

        bookPast.setName("Libro del passato");
        bookPast.setAlias(new String[] { "Sinistra", "passato" });
        bookPast.setDescription(
                "Un libro situato sul lato sinistro della stanza.\nLa pagina aperta recita: Per quanto provi, l'uomo è incapace di cambiare il passato.");
        this.addObject(bookPast);

        bookPresent.setName("Libro del presente");
        bookPresent.setAlias(new String[] { "Centro", "presente" });
        bookPresent.setDescription(
                "Un libro situato su un piedistallo al centro della stanza.\nLa pagina aperta recita: L'uomo lavora nel presente, e di conseguenza, cambia anche il suo futuro.");
        this.addObject(bookPresent);

        bookFuture.setName("Libro del futuro");
        bookFuture.setAlias(new String[] { "Destra", "futuro" });
        bookFuture.setDescription(
                "Un libro situato sul lato destro della stanza.\nLa pagina aperta recita: Non sapendo cosa l'aspetti, l'uomo non è capace di cambiare il suo destino.");
        this.addObject(bookFuture);

        door.setName("Porta");
        door.setDescription("Una porta di metallo molto spessa. Non è possibile capire cosa si nasconda dietro");
        door.setDirection("e");
        door.setOpen(false);
        this.addObject(door);
    }

    /**
     * Interazione per l'apertura della porta. Non è possibile aprire la porta se non
     * viene risolto prima l'enigma nella stanza delle profezie.
     * 
     * @param idir
     * @param player
     */
    @Override
    public void openDoor(String idir, Player player) {

        boolean hasMemoryOfThePast = player.getInventory().stream()
                .anyMatch(item -> item.getName().equals("Memoria del passato"));

        if (hasMemoryOfThePast) {
            player.getInventory().forEach(item -> {
                if (item.getName().equals("Ascia")) {
                    player.removeFromInventory(item);
                    this.setMsg(
                            "Come se fossero passati anni in un istante, il manico della vecchia Ascia cade in rovina e si distrugge.\n Ora è inutilizzabile.");
                }
            });
        } else {
            this.setMsg("Un meccanismo sembra bloccare la porta.");
            return;
        }

        super.openDoor(idir, player); // chiama la superclasse per aprire la porta
    }
}