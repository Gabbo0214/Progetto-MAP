/**
 * Definizione della casa come partenza e fine del gioco
 */
package roomSet;

import base.Stobj;
import gameCore.Player;
import objectSet.Weapon;

public class CastleEntrance extends RoomWDoor{
    
    private boolean open = false;
    private boolean hasInitialDialogOccurred = false;

    public CastleEntrance(){

        Weapon sword = new Weapon();
        Stobj Guardian = new Stobj("Guardiano", "Un guardiano imponente in armatura scintillante ti blocca, con occhi luminosi e una spada incantata pronta a difendere il luogo sacro.");

        sword.setName("Spada");
        sword.setAlias(new String[]{"arma"});
        sword.setDescription("Una spada lunga e affilata, con una lama lucente e un'elsa intarsiata di gemme scintillanti, ma non particolarmente potente.");
        sword.setPickupable(true);
        this.addObject(sword);

        this.addObject(Guardian);
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
        if (person.getName().equals("Guardiano")) {
            // Gestisce il dialogo iniziale
            if (!hasInitialDialogOccurred) {
                this.setMsg(
                        "Il guardiano ti osserva attentamente e dice: 'Benvenuto, avventuriero. Sei giunto qui in cerca di risposte, e io posso aiutarti. Ascolta attentamente, perché ciò che ti sto per dire sarà fondamentale per il tuo cammino. So che stai provando a recuperare il famoso “anello dell' eternità”. Per poter affrontare i vari enigmi del castello avrai bisogno di molta saggezza ed astuzia. Ti darò un indizio che potrebbe rivelarsi cruciale per la tua ricerca: 'Se ti trovi in tempi duri e non sai cosa dire, ADESSO è la risposta.'\n");
                hasInitialDialogOccurred = true;
            }

            // Controlli per l'arma
            boolean a = false;
            for (Stobj inv : p.getInventory()) {
                if (inv.getName().equals("Spada")) {
                    a = true;
                    break;
                }
            }

            if (!this.open) {
                if (a) {
                    this.setMsg(this.getMsg() + "Il custode apre la porta e ti consente di procedere.\n");
                    this.setMsg(this.getMsg()
                            + "\nLa porta verso nord viene aperta. Buona fortuna!");
                    this.open = true;
                    this.setNorth(this.getNextNorth());
                    this.setDescription(
                            "Ti avvicini all'entrata del castello. Dato che hai con te un'arma per difenderti, il custode ti ha concesso di passare aprendoti la porta.");
                } else {
                    this.setMsg(this.getMsg()
                            + "Il custode ti blocca e ti dice che hai bisogno di un'arma per la tua sicurezza. Prova almeno a raccogliere qualcosa nei dintorni!");
                }
            } else if (a) {
                this.setMsg("Il custode non ti risponde ma fa cenno con il capo in segno di saluto.");
            }
        }
    }
}
