/**
 * Definizione degli oggetti di tipo Chiave
 */
package objectSet;

import base.Stobj;
import gameCore.Player;

public class Key extends Stobj {

    /**
     * Metodo per l'utilizzo della chiave.
     * (Ad esempio, può sbloccare una porta o fare qualcos'altro nel gioco)
     * @param p
     * @return String messaggio di utilizzo della chiave
     */
    @Override
    public String use(Player p){
        // Logica per l'utilizzo della chiave
        // (Questo dipende da come vuoi implementare l'uso della chiave nel gioco)
        return "Hai usato la chiave.";
    }

}
