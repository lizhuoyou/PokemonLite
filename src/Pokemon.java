// Pokemon.java
// By Lizhuo You
/*
This is a pokemon class which creates a pokemon which can output skill's and its status information and change its
own status based on input.
It is used for PokemonArena.
 */

import java.util.ArrayList;
import java.util.*;
class Pokemon{
    private Scanner kb = new Scanner(System.in);
    private String NAME, TYPE, RESISTANCE, WEAKNESS;                    // upper case stays constant
    private int energy=50, hp, initialHp, numAttacks, disabled = 0;     // lower case can be change
    private ArrayList<String[]> ATTACKS = new ArrayList<String[]>();
    private boolean stunned = false;

    public Pokemon(String[] stats){
        // fill the information of the pokemon
        NAME = stats[0];
        hp = Integer.parseInt(stats[1]);
        initialHp = hp;
        TYPE = stats[2];
        if (!stats[3].equals(" ")) {
            RESISTANCE = stats[3];
        } else {
            RESISTANCE = "nothing";
        }
        if (!stats[4].equals(" ")) {
            WEAKNESS = stats[4];
        } else {
            WEAKNESS = "nothing";
        }
        numAttacks = Integer.parseInt(stats[5]);
        int statesIndex = 6;         // used to trace the index of the input string
        String[] attack;
        for(int i=0; i<numAttacks; ++i){            // base on the number of attacks to fill ATTACK.
            attack = new String[4];
            for (int j = 0; j < 4; ++j) {
                attack[j] = stats[statesIndex];     // get info for one of the attack fist
                statesIndex++;
            }
            ATTACKS.add(attack);
        }
    }

    // the following six methods get a string of its states at the moment
    public String getName() {
        return NAME;
    }

    public String getHp() {
        return ""+hp;
    }

    public String getEnergy() {
        return ""+energy;
    }

    public String getType() {
        return TYPE;
    }

    public String getResistance(){
        return RESISTANCE;
    }

    public String getWeakness(){
        return WEAKNESS;
    }

    public void recoverAfterRound(){
        // after a round, living pokemon recovers energy.
        if (energy + 10 <= 50) {
            energy += 10;
        } else {
            energy = 50;
        }
    }

    public void recoverAfterBattle() {
        // after a battle, living pokemon recovers hp and romove stunned/disable.
        // it also recovers since it's always after a round.
        recoverAfterRound();
        if (hp + 20 <= initialHp) {
            hp += 20;
        } else {
            hp = initialHp;
        }
        // special effects should be removed after a battle.
        stunned =  false;
        disabled = 0;
    }

    @Override
    public String toString(){
        // easily show the pokemon's state
        return NAME+" now has "+hp+" hp and "+energy+" energy.";
    }

    private void AdvancedSleep(int millis){
        // this sleep method is to make setting up sleep(pause time) easier.
        try {
            Thread.sleep(millis);
        } catch(InterruptedException ex){
            System.out.println("The sleep sucks.");
        }
    }

    private void Speak(String face, String words){
        // this displays a dialogue with an ASCII face and words.
        System.out.println(face);
        for(char c: (words).toCharArray()){
            System.out.print(c);
            AdvancedSleep(40);          // increase virtual experience.
        }
    }

    private int getProperCommand(int amount){
        // get the proper index for user to select an option.
        // amount indicates the range of the input, which should be between 1 & amount.
        String getIn = kb.nextLine();
        int ans = convertCommand(getIn, amount);
        while(ans==-1){
            Speak("((╬◣﹏◢)):", "THIS command is not valid. Try again please[use proper numbers].\n");
            getIn = kb.nextLine();                  // try to get a new command.
            ans = convertCommand(getIn, amount);
        }
        return ans;
    }

    private int convertCommand(String getIn, int amount){
        // if returning -1, the input number is invalid. This method is for getProperCommand.
        for (int i = 1; i <= amount; ++i) {
            if(getIn.equals(""+i)){
                return i-1;         // since the index starts ar 0, so the returned value is reduced by 1.
            }
        }
        return -1;
    }

    public int selectMotion(){
        // this is only used for users to choose what he/she wants to do during the round.
        // returns 0, 1, 2 which each indicates an action--attack, retreat, or pass.
        if (stunned){
            stunned = false;
            Speak("(눈_눈):", NAME+" is stunned and you couldn't do anything in this round...\n");
            return 2;
        }
        Speak("←~(Ψ▼ｰ▼)∈:", "You want "+NAME+" to?\n");
        System.out.println("    1. Let "+NAME+" attack!");
        System.out.println("    2. Retreat "+NAME+".");
        System.out.println("    3. Let "+NAME+" pass the round to gain energy.");

        return getProperCommand(3);
    }

    public String[] goodAttack() {
        // it returns an array which contains the information of an attack.
        // the user will check and choose which attack she/he wants the pokemon to perform.
        Speak("←~(Ψ▼ｰ▼)∈:", NAME+", use--\n");
        String[] attack;
        for (int i = 0; i <numAttacks; ++i) {       // display different attacks.
            attack = ATTACKS.get(i);
            System.out.println("    "+(i+1)+".Use "+attack[0]+", costing "+attack[1]+" energy, dealing "+attack[2]+" damage.");
            if(!attack[3].equals(" ")){             // check if there is a special attack.
                System.out.println("              It has special effect: "+attack[3]+"!");
            }
        }

        attack = ATTACKS.get(getProperCommand(numAttacks));
        Speak("(☄ฺ◣д◢)☄ฺ:", NAME+",use "+attack[0]+"!!!\n");
        if(energy-Integer.parseInt(attack[1])>=0){          // check if the energy is enough to perform the attack.
            energy -= Integer.parseInt(attack[1]);
            return attacking(attack);
        }

        System.out.println(NAME+" doesn't have enough energy to perform the attack, do you want it to take another attempt[y/n]?");
        String yn = kb.nextLine().toLowerCase();
        if(yn.toLowerCase().equals("y")){       // allow user to choose another attack
            return goodAttack();
        }
        return new String[] {"failed", "0", ""};        // attack value for attacking enemy
    }

    public String[] badAttack() {
        // this is the attack from bad pokemon. It randomly choose an applicable attack or return a failed attack value.
        if (stunned) {
            System.out.println(NAME+" is stunned so it won't do anything");
            return new String[] {"failed", "0", ""};
        }
        ArrayList<String[]> appliableAttacks = new ArrayList<String[]>();
        for (int i = 0; i < numAttacks; ++i) {
            if (energy - Integer.parseInt(ATTACKS.get(i)[1]) >= 0) {    // check which attack can be performed.
                appliableAttacks.add(ATTACKS.get(i));
            }
        }

        int size = appliableAttacks.size();     // number of applicable attacks
        if (size > 0) {     // greater than 0 means there is attack to choose
            String[] attack = appliableAttacks.get((int) (Math.random() * size));   // randomly attack
            energy -= Integer.parseInt(attack[1]);
            return attacking(attack);
        }

        System.out.println(NAME+" doesn't have enough energy to perform any attack!");
        return new String[] {"failed", "0", ""};        // attack value for attacking enemy
    }

    private String[] attacking(String[] attack){
        // it is for good/badAttack.
        // it outputs the terminal values for a valid attack, with the type of the pokemon, modified damage value and
        // special effect.
        System.out.println(NAME+" used "+attack[0]+"!");
        int damage = Integer.parseInt(attack[2]);

        if(attack[3].toLowerCase().equals("wild card")){
            if((int)(Math.random()*2)==0){      // 50% of success
                if(disabled == 10){     // ensure the damage won't be negative
                    damage = 10;
                } else{
                    damage = 0;
                }
                System.out.println("Wild Card failed!No damage is dealt.");
            } else {
                System.out.println("Wild Card succeeded!");
            }

        } else if (attack[3].toLowerCase().equals("wild storm")) {
            int luck = (int)(Math.random()*2);      // luck determines if wild storm can succeed
            int initialDamge = damage;      // to accumulate damage
            while (luck == 0) {
                damage += initialDamge;
                System.out.println(NAME+" perfected Wild Storm!");
                luck = (int)(Math.random()*2);
            }

        } else if (attack[3].toLowerCase().equals("recharge")) { // recharge energy
            if (energy + 20 <= 50) {
                energy += 20;
            } else {
                energy = 50;
            }
            System.out.println(NAME+" recharged itself!");
        }

        return new String[] {TYPE, ""+(damage-disabled), attack[3]};
    }

    public void beAttacked(String[] attackInfo){
        // change pokemon's states after being attacked by another pokemon.
        // attackInfo is a String[] contains attack pokemon's type, damage & special effect.
        String attackType = attackInfo[0], special = attackInfo[2];
        int attackDamage = Integer.parseInt(attackInfo[1]), previousHp = hp;

        // base on type, modifies damage taken.
        if(attackType.equals(RESISTANCE)){
            hp -= attackDamage/2;
        } else if(attackType.equals(WEAKNESS)){
            hp -= attackDamage*2;
        } else {
            hp -= attackDamage;
        }

        if (hp < previousHp) {
            System.out.println(NAME+" lost "+(previousHp-hp)+" hp!");       // display how much hp is lost
        }

        // change the pokemon's states if it is effected by the special affect
        if(special.equals("stun")){
            int luck = (int)(Math.random()*2);
            if (luck == 0) {
                stunned = true;
                System.out.println(NAME+" is stunned!");
            }
        } else if(special.equals("disable")){
            disabled = 10;
            System.out.println(NAME+" is disabled!");
        }
    }
}