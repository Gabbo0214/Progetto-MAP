package roomSet;

import base.Stobj;

public class ArmourRoom extends RoomWDoor {

    public ArmourRoom() {
        Stobj obj = new Stobj();
        obj.setName("Libro con enigma");
        obj.setAlias(new String[]{"enigma", "indovinello", "libro"});
        obj.setDescription("Non fa rumore, non emette suoni e se parli lo rompi,\n" +
                "Eppure senza di lui è difficile dormire.\n" +
                "Che cos'è?");
        obj.setPickupable(false);
        this.addObject(obj);

        Stobj stand = new Stobj();
        stand.setName("armatura");
        stand.setAlias(new String[]{"corazza"});
        stand.setDescription("Una delle tante armature. Il suo scintillio ti suggerisce appartenesse a qualcuno di molto importante, forse un valoroso eroe.\nCome fa ad essere ancora in condizioni perfette?");
        stand.setPickupable(false);
        this.addObject(stand);
    }

    /**
     * Interazione per la risoluzione dell'enigma
     */
    public void riddle() {
        this.setMsg("Nel momento in cui pronunci la risposta, il libro si chiude svanendo! Il castello sta iniziando a metterti paura.\nPurtroppo, dopo la risoluzione del rompicapo, questo strano libro non sembra averti dato nulla in cambio!!");

        // Utilizzo di stream e lambda per rimuovere l'oggetto "Libro con enigma"
        this.getObjects().removeIf(obj -> obj.getName().equals("Libro con enigma"));
    }
}