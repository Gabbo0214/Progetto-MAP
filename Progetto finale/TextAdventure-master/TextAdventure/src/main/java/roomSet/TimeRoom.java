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

    @Override
    public void openDoor(String idir, Player player) {
        boolean hasMemoryOfThePast = false;

        for (int i = 0; i < player.getInventory().size(); i++) {
            if (player.getInventory().get(i).getName().equals("Memoria del passato")) {
                hasMemoryOfThePast = true;
            }
        }

        if (!hasMemoryOfThePast) {
            this.setMsg("Un meccanismo sembra bloccare la porta.");
            return;
        }

        super.openDoor(idir, player); // chiama la superclasse per aprire la porta
    }
}