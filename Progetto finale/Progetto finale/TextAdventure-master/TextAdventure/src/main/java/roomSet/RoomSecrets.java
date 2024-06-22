package roomSet;

import base.Room;
import base.Stobj;
import gameCore.Player;

public class RoomSecrets extends Room {

    private boolean open = false;
    private boolean hasInitialDialogOccurred = false;

    public RoomSecrets() {
        Stobj goldCoin = new Stobj();
        Stobj Custode = new Stobj("Custode", "Il custode del villaggio. Sorveglia il castello da una vita.");
        Custode.setAlias(new String[] { "soldato" });
        this.addObject(Custode);

        goldCoin.setName("Moneta dorata");
        goldCoin.setAlias(
                new String[] { "monetadorata", "monetad'oro", "monetadioro", "monetad", "monetao", "monetauno" });
        goldCoin.setDescription(
                "Una moneta dorata raffigurante il profilo di una tigre imponente /n I rilievi sembrano essere fatti a mano.");
        goldCoin.setPickupable(true);
        this.addObject(goldCoin);
    }

    /**
     * Interazione di dialogo con la guardia. In caso di possesso di un oggetto
     * Weapon nell'inventario del Player p, viene aperto il passaggio nord. In caso
     * contrario viene negato l'accesso a nord.
     * 
     * @param p
     * @param person
     */
    @Override
    public void talkTo(Player p, Stobj person) {
        if (person.getName().equals("Custode")) {
            // Gestisce il dialogo iniziale
            if (!hasInitialDialogOccurred) {
                this.setMsg(
                        "Il custode ti osserva attentamente e dice: 'Benvenuto, avventuriero. Sono il custode dei segreti antichi di questo luogo. Sei giunto qui in cerca di risposte, e io posso aiutarti. Ascolta attentamente, perché ciò che ti sto per dire sarà fondamentale per il tuo cammino. So che stai provando a recuperare il famoso “anello dell’eternità”, non sarà per niente facile. Per poter affrontare l'enigma avrai bisogno di molta saggezza e conoscenza. Ti darò un indizio che potrebbe rivelarsi cruciale per la tua ricerca: 'Nel silenzio dei segreti, troverai la verità nascosta.' Che la saggezza del passato ti guidi verso la tua destinazione.'\n");
                hasInitialDialogOccurred = true;
            }

            // Controlli per l'arma
            boolean a = false;
            boolean c = false;
            for (Stobj inv : p.getInventory()) {
                if (inv.getName().equals("Spada") || inv.getName().equals("Ascia")) {
                    a = true;
                    if (inv.getName().equals("Spada"))
                        c = true;
                    break;
                }
            }

            if (!this.open) {
                if (a) {
                    this.setMsg(this.getMsg() + "Il custode apre la porta e ti consente di procedere.\n");
                    if (c)
                        this.setMsg(this.getMsg()
                                + "Notando che porti con te solo una misera spada, ti avvisa che potrebbe servire un'arma migliore...");
                    this.setMsg(this.getMsg()
                            + "\nLa porta verso nord viene aperta. Forse l'arma richiesta dal custode servirà a breve!! Buona fortuna!");
                    this.open = true;
                    this.setNorth(this.getNextNorth());
                    this.setDescription(
                            "Ti avvicini a nord della Sala della luna. Dato che hai con te un'arma per difenderti, il custode ti ha concesso di passare aprendoti la porta.");
                } else {
                    this.setMsg(this.getMsg()
                            + "Il custode ti blocca e ti dice che hai bisogno di un'arma per difenderti. Ti consiglia di andare nell'unica armeria del castello o almeno cercare qualcosa da usare nell'entrata del castello!");
                }
            } else if (a) {
                this.setMsg("Il custode non ti risponde ma fa cenno con il capo in segno di saluto.");
            }
        }
    }
}
