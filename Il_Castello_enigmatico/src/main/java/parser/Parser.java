package parser;

import java.util.List;
import java.util.stream.IntStream;  // Import necessario per l'utilizzo di IntStream

import base.Command;
import base.Stobj;

public class Parser {

    /**
     * Confronta il token con la lista dei comandi per contestualizzarlo. Ritorna la posizione del comando trovato nella lista
     *
     * @param token    parola da analizzare
     * @param commands lista dei comandi
     * @return posizione del comando trovato nella lista
     */
    private int checkForCommand(String token, List<Command> commands) {
        // Utilizzo di IntStream.range per creare un flusso di indici da 0 a commands.size()-1
        return IntStream.range(0, commands.size())
                // Filtraggio degli indici basato sulla condizione lambda
                .filter(i -> commands.get(i).getName().toLowerCase().equals(token) ||
                             (commands.get(i).getAlias() != null && commands.get(i).getAlias().contains(token)))
                // Ritorna il primo indice trovato o -1 se nessun elemento soddisfa la condizione
                .findFirst()
                .orElse(-1);
    }

    /**
     * Confronta il token con la lista degli oggetti di una specifica stanza per contestualizzarlo. Ritorna la posizione dell'oggetto trovato nella lista
     *
     * @param token   parola da analizzare
     * @param objects lista degli oggetti della stanza
     * @return posizione dell'oggetto trovato nella lista
     */
    private int checkForObject(String token, List<Stobj> objects) {
        // Utilizzo di IntStream.range per creare un flusso di indici da 0 a objects.size()-1
        return IntStream.range(0, objects.size())
                // Filtraggio degli indici basato sulla condizione lambda
                .filter(i -> objects.get(i).getName().toLowerCase().equals(token) ||
                             (objects.get(i).getAlias() != null && objects.get(i).getAlias().contains(token)))
                // Ritorna il primo indice trovato o -1 se nessun elemento soddisfa la condizione
                .findFirst()
                .orElse(-1);
    }

    /**
     * Identifica la direzione indicata nel token. Restituisce una stringa con una lettera rappresentativa
     *
     * @param token parola da analizzare
     * @return stringa con la lettera rappresentativa della direzione
     */
    private String checkForDir(String token) {
        String dir = "";
        switch (token) {
            case "n":
            case "nord":
            case "north":
                dir = "n";
                break;
            case "s":
            case "sud":
            case "south":
                dir = "s";
                break;
            case "e":
            case "est":
            case "east":
                dir = "e";
                break;
            case "o":
            case "ovest":
            case "w":
            case "west":
                dir = "w";
                break;
        }
        return dir;
    }

    /**
     * Classe principale del parser. Esegue le istruzioni di controllo di tutte le parole ottenute in ingresso
     *
     * @param command   stringa in ingresso
     * @param commands  lista dei comandi
     * @param objects   lista degli oggetti nella stanza
     * @param inventory lista degli oggetti nell'inventario
     * @return ParserOutput oggetto contenente gli eventuali comandi, oggetti con cui interagire e direzioni
     */
    public ParserOutput parse(String command, List<Command> commands, List<Stobj> objects, List<Stobj> inventory) {
        String cmd = command.toLowerCase().trim();
        String[] tokens = cmd.split("\\s+");
        int i = 0;
        int io = -1, ic = -1, ioinv = -1;
        String idir = "";
        if (tokens.length > 0) {
            while (ic < 0 && i < tokens.length) {
                // Chiamata al metodo checkForCommand con utilizzo delle lambda expressions
                ic = checkForCommand(tokens[i], commands);
                i++;
            }
            if (ic > -1) {
                i = 0; // riparte dalla prima parola
                if (tokens.length > 1) {
                    while (io < 0 && i < tokens.length) {
                        // Chiamata al metodo checkForObject con utilizzo delle lambda expressions
                        io = checkForObject(tokens[i], objects);
                        i++;
                    }
                    if (io < 0) {
                        i = 0;
                        while (ioinv < 0 && i < tokens.length) {
                            // Chiamata al metodo checkForObject per inventory con utilizzo delle lambda expressions
                            ioinv = checkForObject(tokens[i], inventory);
                            i++;
                        }
                    }
                    if (io > -1 && objects.get(io).getName().equals("Porta")) {
                        int nd = 0;
                        for (Stobj inv : objects) {
                            if (inv.getName().equals("Porta")) {
                                nd++;
                            }
                        }
                        if (nd > 1) {
                            i = 0;
                            if (tokens.length > 2) {
                                while (idir.equals("") && i < tokens.length) {
                                    // Chiamata al metodo checkForDir
                                    idir = checkForDir(tokens[i]);
                                    i++;
                                }
                            }
                            return new ParserOutput(commands.get(ic), objects.get(io), idir);
                        }
                    }
                    if (io > -1) {
                        return new ParserOutput(commands.get(ic), objects.get(io), "");
                    } else if (ioinv > -1) {
                        return new ParserOutput(commands.get(ic), inventory.get(ioinv), "");
                    } else {
                        return new ParserOutput(commands.get(ic), null, "");
                    }
                } else {
                    return new ParserOutput(commands.get(ic), null, "");
                }
            } else {
                return new ParserOutput(null, null, "");
            }
        } else {
            return null;
        }
    }

}