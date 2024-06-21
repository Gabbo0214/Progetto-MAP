package monsterSet;

import base.SuperMonster;

public class Monster_Gatto extends SuperMonster {
    
   public Monster_Gatto(){
        this.setName("Gatto trappola");
        this.setDescription("Dracula è un elegante gatto dal manto nero come la notte, occhi rossi brillanti e canini affilati. Con movimenti silenziosi e una presenza misteriosa, si nasconde spesso nelle ombre.");
        this.setImage(
            "                         _\n" +
            "                       | \\\n" +
            "                       | |\n" +
            "                       | |\n" +
            "  |\\                   | |\n" +
            " /, ~\\                / /\n" +
            "X     `-.....-------./ /\n" +
            " ~-. ~  ~              |\n" +
            "    \\             /    |\n" +
            "     \\  /_     ___\\   /\n" +
            "     | /\\ ~~~~~   \\ |\n" +
            "     | | \\        || |\n" +
            "     | |\\ \\       || )\n" +
            "    (_/ (_/      ((_/");

        this.setDeadName("Corpo del gatto");
        this.setDeadDescription("Il malefico gatto che hai sconfitto. È stata una dura battaglia");
        this.setHp(10); // Ridotto i punti vita per renderlo più facile da sconfiggere
        this.setTotHp(10); // Anche la vita totale viene impostata a 10
        this.setAttack(3);
        this.setAttackMessage(new String[]{"I suoi artigli ti colpiscono con forza", "Con i suoi artigli ti provoca delle pesanti ferite"});
        this.setDodgeMessage(new String[]{"Dracula schiva il colpo, sembra essere molto agile..."});
        this.setHitRate(80);
        this.setDodgeRate(10); // Ridotto il tasso di schivata per renderlo più facile da colpire
    }
}
