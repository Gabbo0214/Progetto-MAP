package monsterSet;

import base.SuperMonster;

public class Monster_Lupo extends SuperMonster {
    
   public Monster_Lupo(){
        this.setName("Lupo");
        this.setDescription("un lupo solitario, probabilmente in cerca di\ncibo...e tu sembri essere meglio di niente.");
        this.setImage("                        ,     ,\n" +
"                        |\\---/|\n" +
"                       /  , , |\n" +
"                  __.-'|  / \\ /\n" +
"         __ ___.-'        ._O|\n" +
"      .-'  '        :      _/\n" +
"     / ,    .        .     |\n" +
"    :  ;    :        :   _/\n" +
"    |  |   .'     __:   /\n" +
"    |  :   /'----'| \\  |\n" +
"    \\  |\\  |      | /| |\n" +
"     '.'| /       || \\ |\n" +
"     | /|.'       '.l \\\\_\n" +
"     || ||             '-'\n" +
"     '-''-");
        this.setDeadName("Carcassa del lupo");
        this.setDeadDescription("Il cadavere del lupo che hai sconfitto. La tua prima vittoria");
        this.setHp(10); // Ridotto i punti vita per renderlo più facile da sconfiggere
        this.setTotHp(10); // Anche la vita totale viene impostata a 10
        this.setAttack(5);
        this.setAttackMessage(new String[]{"Ti azzanna con i suoi canini affilati", "Ti lacera la pelle con i suoi artigli", "Ti salta addosso"});
        this.setDodgeMessage(new String[]{"schiva con destrezza!", "evita il tuo colpo"});
        this.setHitRate(80);
        this.setDodgeRate(10); // Ridotto il tasso di schivata per renderlo più facile da colpire
    }
}
