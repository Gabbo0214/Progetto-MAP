/**
 * Classe main. Definisce le basi del gioco. 
 */
package gameCore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import fileManager.FileHandler;
import gameInterface.UI;
import gameInterface.VisibilityManager;
import parser.Parser;
import parser.ParserOutput;

public class Game {
    ChoiceHandler cHandler= new ChoiceHandler();
    UI ui= new UI();
    VisibilityManager vm= new VisibilityManager(ui);
    Story story= new Story();
    Player player= new Player();
    Map map = new Map();
    File mapfile = new File(".\\MapSaveFile.txt");
    File playerfile = new File(".\\PlayerSaveFile.txt");
    FileHandler fw = new FileHandler();
    Parser parser = new Parser();
    CommandList cl = new CommandList();
    String inventoryStatus = "";
    boolean combat = false;

    public static void main(String[] args){
        new Game();
    }
    
    public Game(){
        ui.createUI(cHandler);  //accediamo ai metodi della classe UI = crea la finestra di dialogo
        vm.showTitleScreen();   //accediamo ai metodi della classe VisibilityManager = mostra la schermata iniziale
    }
    
    /**
     * Inner Class della classe Game. Gestisce gli avvenimenti nel gioco basandosi sui comandi ottenuti in ingresso
     */
    public class ChoiceHandler implements ActionListener{ //gestore per le scelte che aspetta l'evento(cioè il click)
         @Override
         public void actionPerformed(ActionEvent event){
            String yourChoice= event.getActionCommand();
            
            switch(yourChoice){
                  case "start": //Click su "Nuovo gioco"
                       vm.setMainTextPanelSize(50,100,650,420);
                       vm.hideExitConfirm();
                       vm.showMainGameScreen();
                       vm.setNormalFont(18);
                       vm.titleToInput();
                       story.start(vm);
                       break;

                  case "continua": //Click su "Carica"
                      vm.setMainTextPanelSize(50,100,650,300);
                      vm.hideExitConfirm();
                      vm.showMainGameScreen();
                      vm.setNormalFont(18);
                      vm.titleToGame();
                      try {
                          map.setMap(fw.loadMapDataFromFile(mapfile));
                          player.setPlayer(fw.loadPlayerDataFromFile(playerfile));
                          loadSetup();
                      } catch (NullPointerException | ClassNotFoundException | IOException e){
                          vm.backToMenu();
                          vm.writeOnExitScreen("                                 Caricamento fallito. Dati inesistenti o danneggiati :(");
                      }
                      break;

                  case "start1": //Click su "Avanti"
                      vm.setNormalFont(18);
                      vm.setMainTextPanelSize(50,100,650,300);
                      vm.hideSaved();
                      vm.titleToGame();
                      defaultSetup();
                      break;

                  case "submit": //Click su "Submit " o pressione del tasto Invio (Enter)
                      vm.hideSaved();
                      ParserOutput par = parser.parse(vm.getCampoTxt(), cl.getCommands(), map.getCurrentRoom().getObjects(), player.getInventory());
                      
                      //Se viene inserito il comando fine o eventuali sinonimi, viene chiusa la finestra
                      if (par.getCommand()!=null && par.getCommand().getName().equals("fine"))
                          System.exit(0);

                      boolean end = story.nextMove(par, player, map, vm); //true se si è raggiunti l'ultima interazione, false altrimenti
                      vm.setHpLabel(player.getCurrentHp(),player.getTotHp());
                      vm.campoFocus();
                      
                      //Se end == true viene visualizzata l'ultima interazione prima della schermata finale
                      if (end){
                          vm.backToMenu();
                          story.ending(player, vm);
                          }
                      break;


                  case "salva"://Click su "Salva"
                      vm.showSaved(fw.saveMapDataToFile(mapfile, map), fw.savePlayerDataToFile(playerfile, player));
                      vm.campoFocus();
                      break;

                   case "exitMenu": //Click su "Esci". Porta alla schermata di conferma dell'uscita
                       vm.hideSaved();
                       vm.exitConfirm();
                       vm.writeOnExitScreen("                                        Sei sicuro di voler abbandonare la partita?\n                                       Tutti i progressi non salvati andranno persi.");
                       break;

                   case "yes": //Click su "Sì" nella schermata di conferma dell'uscita. Esce dal gioco e torna al menù principale
                       vm.setMainTextPanelSize(50,100,650,300);
                       vm.showTitleScreen();
                       map = new Map();
                       player = new Player();
                       vm.hideExitConfirm();
                       combat = false;
                       break;

                   case "no": //Click su "No" nella schermata di conferma dell'uscita. Torna al gioco
                       vm.hideExitConfirm();
                       vm.showMainGameScreen();
                       if (combat)
                           vm.showFightButtons();
                       break;

                   case "exit": //Click su "Esci" nel menù principale. Chiude la finestra del gioco
                       System.exit(0);
                       break;  

                   case "BackToMenu": //Click su "Torna al menù principale" dalla schermata del caricamento fallito
                        vm.showTitleScreen();
                       break;

                   case "inventoryButton": //Click su "Zaino". Mostra il contenuto della lista Inventory del Player in un set di JLabel a scomparsa
                       vm.hideSaved();
                       if(inventoryStatus.equals("close")){
                           vm.openInventory();
                           vm.campoFocus();
                           if (!player.getInventory().isEmpty()){
                               for (int i = 0; i<player.getInventory().size(); i++ ){
                                   if (player.getInventory().get(i)!=null){
                                       vm.getInvLableComponent(i).setText(player.getInventory().get(i).getName());
                                   }
                               }
                           }
                           vm.getInvLableComponent(player.getInventory().size()).setText("");
                           inventoryStatus= "open";
                       } else if (inventoryStatus.equals("open")){
                           vm.closeInventory();
                           vm.campoFocus();
                           inventoryStatus="close";
                       }
                       break;
               }
           }
    }
        /**
         * Metodo per l'impostazione dei paramentri iniziali ad un nuovo inzio del gioco.
         */
         public void defaultSetup(){ 
            map = new Map();
            player = new Player();
            
            vm.setHpLabel(player.getCurrentHp(),player.getTotHp());
            inventoryStatus="close";
            vm.writeOnScreen(map.getCurrentRoom().getName() + "\n================================================\n" + map.getCurrentRoom().getDescription());
            vm.campoFocus();
    }
        /**
         * Metodo per l'impostazione dei paramentri al caricamento di un salvataggio.
         */
        public void loadSetup(){
        String uitxt = "";
            vm.setHpLabel(player.getCurrentHp(),player.getTotHp());
            inventoryStatus="close";
            uitxt = (map.getCurrentRoom().getName() + "\n================================================\n" + map.getCurrentRoom().getDescription());
            vm.writeOnScreen(uitxt);
            vm.campoFocus();
    }
}
              
    


