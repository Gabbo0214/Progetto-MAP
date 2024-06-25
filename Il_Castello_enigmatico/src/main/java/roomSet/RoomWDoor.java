package roomSet;

import base.Room;
import gameCore.Player;
import objectSet.Door;

public class RoomWDoor extends Room {

    /**
     * Interazione per l'apertura delle porte. In caso di presenza di una singola porta nella lista degli oggetti della stanza, ne avviene la semplice apertura quindi viene sbloccato il passaggio alla stanza su cui la porta affaccia.
     * In caso di presenza di più porte nella stanza e mancanza di una specifica direzione idir, restituisce come Msg l'elenco delle porte interagibili e il loro stato.
     * In caso di presenza di più porte e avendo specificato una direzione, quella specifica porta viene aperta.
     * Nel caso la porta a cui ci si riferisce è già aperta, restituisce un messaggio che lo notifica
     * @param idir
     */
    @Override
    public void openDoor(String idir, Player p) {
        // Conta il numero di porte nella stanza
        int numd = (int) this.getObjects().stream()
                .filter(obj -> obj instanceof Door && obj.getName().equals("Porta"))
                .count();

        if (numd > 1) {
            // Se ci sono più porte e idir non è specificato, genera il testo per il messaggio
            if (idir.equals("")) {
                String uitxt = "Ci sono " + numd + " porte che si affacciano su queste direzioni:\n" +
                        // Utilizzo di reduce per costruire dinamicamente il testo
                        this.getObjects().stream()
                                .filter(obj -> obj instanceof Door && obj.getName().equals("Porta"))
                                .map(obj -> ((Door) obj).getDirection())
                                .reduce("", (acc, direction) -> {
                                    switch (direction) {
                                        case "s":
                                            return acc + "- Sud\n";
                                        case "n":
                                            return acc + "- Nord\n";
                                        case "w":
                                            return acc + "- Ovest\n";
                                        case "e":
                                            return acc + "- Est\n";
                                        default:
                                            return acc;
                                    }
                                });
                this.setMsg(uitxt + "Specifica quale porta vorresti aprire.");
            } else {
                // Se è specificata una direzione, apre la porta corrispondente
                this.getObjects().stream()
                        .filter(obj -> obj instanceof Door && obj.getName().equals("Porta") && ((Door) obj).getDirection().equals(idir))
                        .findFirst()
                        .ifPresent(obj -> {
                            Door door = (Door) obj;
                            if (door.isOpen()) {
                                this.setMsg("Questa porta è già aperta");
                            } else {
                                // Lambda expression per aprire la porta e aggiornare il messaggio di feedback
                                door.setOpen(true);
                                door.setDescription(door.getDescription().substring(0, door.getDescription().length() - 6) + "aperta");
                                switch (idir) {
                                    case "n":
                                        this.setMsg("Hai aperto la porta verso nord");
                                        this.setNorth(this.getNextNorth());
                                        break;
                                    case "s":
                                        this.setMsg("Hai aperto la porta verso sud");
                                        this.setSouth(this.getNextSouth());
                                        break;
                                    case "e":
                                        this.setMsg("Hai aperto la porta verso est");
                                        this.setEast(this.getNextEast());
                                        break;
                                    case "w":
                                        this.setMsg("Hai aperto la porta verso ovest");
                                        this.setWest(this.getNextWest());
                                        break;
                                }
                            }
                        });
            }
        } else {
            // Se c'è una sola porta, apre quella
            Door door = (Door) this.getObjects().stream()
                    .filter(obj -> obj instanceof Door && obj.getName().equals("Porta"))
                    .findFirst()
                    .orElse(null);

            if (door != null) {
                if (door.isOpen()) {
                    this.setMsg("La porta è già aperta");
                } else {
                    // Lambda expression per aprire la porta e aggiornare il messaggio di feedback
                    door.setOpen(true);
                    door.setDescription(door.getDescription().substring(0, door.getDescription().length() - 6) + "aperta");
                    switch (door.getDirection()) {
                        case "e":
                            this.setEast(this.getNextEast());
                            break;
                        case "w":
                        case "o":
                            this.setWest(this.getNextWest());
                            break;
                        case "n":
                            this.setNorth(this.getNextNorth());
                            break;
                        case "s":
                            this.setSouth(this.getNextSouth());
                            break;
                    }
                    this.setMsg("Hai aperto la porta");
                }
            }
        }
    }

    /**
     * Interazione per la chiusura delle porte. In caso di presenza di una singola porta nella lista degli oggetti della stanza, ne avviene la semplice chiusura quindi viene bloccato il passaggio alla stanza su cui la porta affaccia.
     * In caso di presenza di più porte nella stanza e mancanza di una specifica direzione idir, restituisce come Msg l'elenco delle porte interagibili e il loro stato.
     * In caso di presenza di più porte e avendo specificato una direzione, quella specifica porta viene chiusa.
     * Nel caso la porta a cui ci si riferisce è già chiusa, restituisce un messaggio che lo notifica
     * @param idir
     */
    @Override
    public void closeDoor(String idir) {
        // Conta il numero di porte nella stanza
        int numd = (int) this.getObjects().stream()
                .filter(obj -> obj instanceof Door && obj.getName().equals("Porta"))
                .count();

        if (numd > 1) {
            // Se ci sono più porte e idir non è specificato, genera il testo per il messaggio
            if (idir.equals("")) {
                String uitxt = "Ci sono " + numd + " porte che si affacciano su queste direzioni:\n" +
                        // Utilizzo di reduce per costruire dinamicamente il testo
                        this.getObjects().stream()
                                .filter(obj -> obj instanceof Door && obj.getName().equals("Porta"))
                                .map(obj -> ((Door) obj).getDirection())
                                .reduce("", (acc, direction) -> {
                                    switch (direction) {
                                        case "s":
                                            return acc + "- Sud\n";
                                        case "n":
                                            return acc + "- Nord\n";
                                        case "w":
                                            return acc + "- Ovest\n";
                                        case "e":
                                            return acc + "- Est\n";
                                        default:
                                            return acc;
                                    }
                                });
                this.setMsg(uitxt + "Specifica quale porta vorresti chiudere.");
            } else {
                // Se è specificata una direzione, chiude la porta corrispondente
                this.getObjects().stream()
                        .filter(obj -> obj instanceof Door && obj.getName().equals("Porta") && ((Door) obj).getDirection().equals(idir))
                        .findFirst()
                        .ifPresent(obj -> {
                            Door door = (Door) obj;
                            if (!door.isOpen()) {
                                this.setMsg("Questa porta è già chiusa");
                            } else {
                                // Lambda expression per chiudere la porta e aggiornare il messaggio di feedback
                                door.setOpen(false);
                                door.setDescription(door.getDescription().substring(0, door.getDescription().length() - 6) + "chiusa");
                                switch (idir) {
                                    case "n":
                                        this.setMsg("Hai chiuso la porta verso nord");
                                        this.setNorth(null);
                                        break;
                                    case "s":
                                        this.setMsg("Hai chiuso la porta verso sud");
                                        this.setSouth(null);
                                        break;
                                    case "e":
                                        this.setMsg("Hai chiuso la porta verso est");
                                        this.setEast(null);
                                        break;
                                    case "w":
                                        this.setMsg("Hai chiuso la porta verso ovest");
                                        this.setWest(null);
                                        break;
                                }
                            }
                        });
            }
        } else {
            // Se c'è una sola porta, chiude quella
            Door door = (Door) this.getObjects().stream()
                    .filter(obj -> obj instanceof Door && obj.getName().equals("Porta"))
                    .findFirst()
                    .orElse(null);

            if (door != null) {
                if (!door.isOpen()) {
                    this.setMsg("La porta è già chiusa");
                } else {
                    // Lambda expression per chiudere la porta e aggiornare il messaggio di feedback
                    door.setOpen(false);
                    door.setDescription(door.getDescription().substring(0, door.getDescription().length() - 6) + "chiusa");
                    switch (door.getDirection()) {
                        case "e":
                            this.setEast(null);
                            break;
                        case "w":
                        case "o":
                            this.setWest(null);
                            break;
                        case "n":
                            this.setNorth(null);
                            break;
                        case "s":
                            this.setSouth(null);
                            break;
                    }
                    this.setMsg("Hai chiuso la porta");
                }
            }
        }
    }
}