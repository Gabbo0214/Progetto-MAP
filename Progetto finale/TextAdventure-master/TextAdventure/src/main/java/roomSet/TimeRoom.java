/**
 * Definizione di sala del tempo
 */
package roomSet;

import base.Room;
//import base.Stobj;
//import gameCore.Player;
import base.Stobj;

public class TimeRoom extends Room {

    public TimeRoom() {
        Stobj bookPast = new Stobj();
        Stobj bookPresent = new Stobj();
        Stobj bookFuture = new Stobj();

        bookPast.setName("Libro del passato");
        bookPast.setAlias(new String[] { "Sinistra",  "passato" });
        bookPast.setDescription("Un libro situato sul lato sinistro della stanza.\nLa pagina aperta recita: Per quanto provi, l'uomo è incapace di cambiare il passato.");
        this.addObject(bookPast);

        bookPresent.setName("Libro del presente");
        bookPresent.setAlias(new String[] { "Centro", "presente" });
        bookPresent.setDescription("Un libro situato su un piedistallo al centro della stanza.\nLa pagina aperta recita: L'uomo lavora nel presente, e di conseguenza, cambia anche il suo futuro.");
        this.addObject(bookPresent);

        bookFuture.setName("Libro del futuro");
        bookFuture.setAlias(new String[] { "Destra", "futuro" });
        bookFuture.setDescription("Un libro situato sul lato destro della stanza.\nLa pagina aperta recita: Non sapendo cosa l'aspetti, l'uomo non è capace di cambiare il suo destino.");
        this.addObject(bookFuture);
    }
}