// PokemonArena.java
// By Lizhuo You
// Summary:
// This is the game PokemanArena, which is a simplified, text-based game. It uses Pokemon class to construct the game.
// It also imports the information about pokemons from a text file.
// In the game, the users choose pokemons and order their pokemons to defeat all other pokemons. The only way to win this
// game is to defeat all other pokemons. In each battle, a random wild pokemon will fight with users pokemon.
// Individual Features:
// 1. text-based graphics.
// 2. Freedom for user to input their names and choose the amount of pokemons.
// 3. Featured dialogue system.
// 4. User-friendly gaming process.
import java.util.*;
import java.io.*;
public class PokemonArena {
    private static Scanner kb = new Scanner(System.in);
    private static int amount, myPokeAmount;  // amount is total amount of pokemons.
    private static boolean[] usedPokemons;      // this ensures the each pokemon only has 1 life.
    private static ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>();      // all pokemons.
    private static ArrayList<Pokemon> goodGuys = new ArrayList<Pokemon>();      // user's pokemons.
    private static Pokemon good, bad;           // good represent user's pokemon, bad is enemy pokemon. They are used for battles.

    private static void Speak(String face, String words){
        // this displays a dialogue with an ASCII face and words.
        System.out.println(face);
        for(char c: (words).toCharArray()){
            System.out.print(c);
            AdvancedSleep(40);          // increase virtual experience.
        }
    }

    private static int getProperCommand(int amount){
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

    private static int convertCommand(String getIn, int amount){
        /// if returning -1, the input number is invalid. This method is for getProperCommand.
        for (int i = 1; i <= amount; ++i) {
            if(getIn.equals(""+i)){
                return i-1;         // since the index starts ar 0, so the returned value is reduced by 1.
            }
        }
        return -1;
    }

    private static void load() {
        // it loads information and sets up the ArrayList pokemons.
        try {
            Scanner pokeFile = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
            amount = Integer.parseInt(pokeFile.nextLine());
            for(int i=0; i< amount;++i){
                pokemons.add(new Pokemon(pokeFile.nextLine().split(",")));
            }
            pokeFile.close();

        } catch (IOException exception) {
            System.out.println("This file might not exist");
        }
    }

    private static void pickTeam(){
        // it lets the user to choose his/her pokemons to fight the rest of wild pokemons.

        // display all pokemons with their info in a table to help users to choose their pokemons.
        Speak("(◣_◢):", "Before starting brain storming, please choose your favorite pokemons from the table:\n\n");
        System.out.println("+---+-----------+-----------+-----------+-----------+-----------+");
        System.out.println("|num|       name|         hp|       type| resistance|   weakness|");
        System.out.println("|---|-----------|-----------|-----------|-----------|-----------|");
        for (int i = 0; i < amount; ++i) {
            System.out.printf("|%3d|%11s|%11s|%11s|%11s|%11s|\n",i+1, pokemons.get(i).getName(),pokemons.get(i).getHp(),
                    pokemons.get(i).getType(),pokemons.get(i).getResistance(),pokemons.get(i).getWeakness());
            AdvancedSleep(75);
        }
        System.out.println("+---+-----------+-----------+-----------+-----------+-----------+\n");

        Speak("(◣_◢):","how many pokemons you want to have?\n");
        myPokeAmount = getProperCommand(amount-1)+1;        // plus 1 since it is not index but an actual number

        // let user choose pokemons.
        for(int i = 0; i<myPokeAmount;++i){
            Speak("(◣_◢):", "Type in the number to select a pokemon("+(myPokeAmount-i)+" choice(s) left).\n");
            int command = getProperCommand(amount);
            while (usedPokemons[command]) {         // ensure not choosing same pokemon twice
                Speak("（；¬＿¬):", "Sorry, this pokemon is already chosen");
                command = getProperCommand(amount);
            }
            usedPokemons[command] = true;
            goodGuys.add(pokemons.get(command));
            Speak("٩(๑`^´๑)۶:", pokemons.get(command).getName()+" is on your team!\n");
        }
    }

    private static void IChooseYou(){
        // it lets user to choose a pokemon to fight the battle.
        Speak("(◣_◢):","I choose you--\n");

        // display good pokemons to let user decide.
        for(int i = 0; i < myPokeAmount;++i){
            Pokemon p = goodGuys.get(i);
            System.out.println("   "+(i+1)+". The "+p.getType()+" pokemon "+p.getName()+" with "+p.getHp()+" hp & "+ p.getEnergy()
                    +" energy.\n      It resists "+p.getResistance()+" but it is afraid of "+p.getWeakness()+".");
        }

        good = goodGuys.get(getProperCommand(myPokeAmount));
        System.out.println(good.getName()+",I choose you!");
    }

    private static void AdvancedSleep(int millis){
        // this sleep method is to make setting up sleep(pause time) easier.
        try {
            Thread.sleep(millis);
        } catch(InterruptedException ex){
            System.out.println("The sleep sucks.");
        }
    }

    private static void graphicsStart(){
        // These are intro graphics.
        char[] graphic1 =
                ("                                .::.\n"+
                        "                              .;:**'               \n"+
                        "                              `                   \n"+
                        "  .:XHHHHk.              db.   .;;.     dH  MX    \n"+
                        "oMMMMMMMMMMM       ~MM  dMMP :MMMMMR   MMM  MR      ~MRMN\n"+
                        "QMMMMMb  \"MMX       MMMMMMP !MX' :M~   MMM MMM  .oo. XMMM 'MMM\n"+
                        "  `MMMM.  )M> :X!Hk. MMMM   XMM.o\"  .  MMMMMMM X?XMMM MMM>!MMP\n"+
                        "   'MMMb.dM! XM M'?M MMMMMX.`MMMMMMMM~ MM MMM XM `\" MX MMXXMM     _____\n"+
                        "    ~MMMMM~ XMM. .XM XM`\"MMMb.~*?**~ .MMX M t MMbooMM XMMMMMP    /  _  \\_______   ____   ____ _____\n"+
                        "     ?MMM>  YMMMMMM! MM   `?MMRb.    `\"\"\"   !L\"MMMMM XM IMMM    /  /_\\  \\_  __ \\_/ __ \\ /    \\\\__  \\\n"+
                        "      MMMX   \"MMMM\"  MM       ~%:           !Mh.\"\"\" dMI IMMP   /    |    \\  | \\/\\  ___/|   |  \\/ __ \\_\n"+
                        "      'MMM.                                             IMX    \\____|__  /__|    \\___  >___|  (____  /\n"+
                        "       ~M!M                                             IMP            \\/            \\/     \\/     \\/ \n").toCharArray();
        for(char c: graphic1){
            System.out.print(c);
            AdvancedSleep(7);
        }

        String[] graphic2 = {
                "                    .\"-,.__\n" ,
                "                    `.     `.  ,\n" ,
                "                  .--'  .._,'\"-' `.\n" ,
                "                 .    .'         `'\n" ,
                "                 `.   /          ,'\n" ,
                "                  `  '--.   ,-\"'\n" ,
                "                   `\"`   |  \\\n" ,
                "                      -. \\, |\n" ,
                "                       `--Y.'      ___.\n" ,
                "                            \\     L._, \\\n" ,
                "                  _.,        `.   <  <\\                _\n" ,
                "                ,' '           `, `.   | \\            ( `\n" ,
                "             ../, `.            `  |    .\\`.           \\ \\_\n" ,
                "            ,' ,..  .           _.,'    ||\\l            )  '\".\n" ,
                "           , ,'   \\           ,'.-.`-._,'  |           .  _._`.\n" ,
                "         ,' /      \\ \\        `' ' `--/   | \\          / /   ..\\\n" ,
                "       .'  /        \\ .         |\\__ - _ ,'` `        / /     `.`.\n" ,
                "       |  '          ..         `-...-\"  |  `-'      / /        . `.\n" ,
                "       | /           |L__           |    |          / /          `. `.\n" ,
                "      , /            .   .          |    |         / /             ` `\n" ,
                "     / /          ,. ,`._ `-_       |    |  _   ,-' /               ` \\\n" ,
                "    / .           \\\"`_/. `-_ \\_,.  ,'    +-' `-'  _,        ..,-.    \\`.\n" ,
                "     '         .-f    ,'   `    '.       \\__.---'     _   .'   '     \\ \\\n" ,
                "   ' /          `.'    l     .' /          \\..      ,_|/   `.  ,'`     L`\n" ,
                "   |'      _.-\"\"` `.    \\ _,'  `            \\ `.___`.'\"`-.  , |   |    | \\\n" ,
                "   ||    ,'      `. `.   '       _,...._        `  |    `/ '  |   '     .|\n" ,
                "   ||  ,'          `. ;.,.---' ,'       `.   `.. `-'  .-' /_ .'    ;_   ||\n" ,
                "   || '              V      / /           `   | `   ,'   ,' '.    !  `. ||\n" ,
                "   ||/            _,-------7 '              . |  `-'    l         /    `||\n" ,
                "    |          ,' .-   ,' ||               | .-.        `.      .'     ||\n" ,
                "    `'        ,'    `\".'    |               |    `.        '. -.'       `'\n" ,
                "             /      ,'      |               |,'    \\-.._,.'/'\n" ,
                "             .     /        .               .       \\    .''\n" ,
                "           .`.    |         `.             /         :_,'.'\n" ,
                "             \\ `...\\   _     ,'-.        .'         /_.-'\n" ,
                "              `-.__ `,  `'   .  _.>----''.  _  __  /\n" ,
                "                   .'        /\"'          |  \"'   '_\n" ,
                "                  /_|.-'\\ ,\".             '.'`__'-( \\\n" ,
                "                    / ,\"'\"\\,'               `/  `-.|\" \\"};
        for(String s: graphic2){
            System.out.print(s);
            AdvancedSleep(100);
        }

        Speak("\n(￣▽￣)ノ:", "Welcome to Pokemon Arena!\n");

        Speak("\n(￣▽￣):", "PRESS ENTER TO CONTINUE\n");
        kb.nextLine();      // this is to pause the game so the user can interact with the program
    }

    private static void graphicsEnd(String name, int win){
        // These are ending graphics.
        // If win is 0, the game is won. Otherwise, the user lost.
        // The graphics vary based on winning/losing
        if(win == 0){
            String[] endGraphic = {
                    "               *    *\n" ,
                    "   *         '       *       .  *   '     .           * *\n" ,
                    "                                                               '\n" ,
                    "       *                *'          *          *        '\n" ,
                    "   .           *               |               /\n" ,
                    "               '.         |    |      '       |   '     *\n" ,
                    "                 \\*        \\   \\             /\n" ,
                    "       '          \\     '* |    |  *        |*                *  *\n" ,
                    "            *      `.       \\   |     *     /    *      '\n" ,
                    "  .                  \\      |   \\          /               *\n" ,
                    "     *'  *     '      \\      \\   '.       |\n" ,
                    "        -._            `                  /         *\n" ,
                    "  ' '      ``._   *                           '          .      '\n" ,
                    "   *           *\\*          * .   .      *\n" ,
                    "*  '        *    `-._                       .         _..:='        *\n" ,
                    "             .  '      *       *    *   .       _.:--'\n" ,
                    "          *           .     .     *         .-'         *\n" ,
                    "   .               '             . '   *           *         .\n" ,
                    "  *       ___.-=--..-._     *                '               '\n" ,
                    "                                  *       *\n" ,
                    "                *        _.'  .'       `.        '  *             *\n" ,
                    "     *              *_.-'   .'            `.               *\n" ,
                    "                   .'                       `._             *  '\n" ,
                    "   '       '                        .       .  `.     .\n" ,
                    "       .                      *                  `\n" ,
                    "               *        '             '                          .\n" ,
                    "     .                          *        .           *  *\n" ,
                    "             *        .                                    '"};
            for(String s: endGraphic){
                System.out.print(s);
                AdvancedSleep(75);
            }

            Speak("\n°˖✧◝(⁰▿⁰)◜✧˖°:", "Congratulations, "+name+"! You win the game!\n");

        } else {
            String[] endGraphics={
                    "                         ,-------.                 /\n" ,
                    "                       ,'         `.           ,--'\n" ,
                    "                     ,'             `.      ,-;--        _.-\n" ,
                    "               pow! /                 \\ ---;-'  _.=.---''\n" ,
                    "  +-------------+  ;    X        X     ---=-----'' _.-------\n" ,
                    "  |    -----    |--|                   \\-----=---:i-\n" ,
                    "  +XX|'i:''''''''  :                   ;`--._ ''---':----\n" ,
                    "  /X+-)             \\   \\         /   /      ''--._  `-\n" ,
                    " .XXX|)              `.  `.     ,'  ,'             ''---.\n" ,
                    "   X\\/)                `.  '---'  ,'                     `-\n" ,
                    "     \\                   `---+---'\n" ,
                    "      \\                      |\n" ,
                    "       \\.                    |\n" ,
                    "         `-------------------+\n"
            };
            for(String s: endGraphics){
                System.out.print(s);
                AdvancedSleep(100);
            }

            Speak("\nヽ(￣ω￣(。。 )ゝ:","I'm sorry to see your failure, "+name+". Try hard next time.\n");
        }
    }

    private static int getBadInd() {
        // this is to randomly get a bad pokemon's index
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < usedPokemons.length; i++) {
            if (!usedPokemons[i]) {      // check if the pokemon can be chosen.
                indexes.add(i);
            }
        }
        int a = (int)(Math.random()*indexes.size());
        return indexes.get(a);
    }

    private static boolean rounds(boolean goodAttackFirst){
        // It is the rounds in a battle for user to fight a wild pokemon
        // goodAttackFirst determines which side attacks first.
        while (true) {
            String[] damage = {"failed", "0", ""};            // this is to denote the damage from a pokemon.
            if(goodAttackFirst){
                // user's turn
                int action = good.selectMotion();
                if (action == 0) {                  // good pokemon attack
                    damage = good.goodAttack();
                } else if (action == 1) {           // retreat pokemon
                    Speak("(◣_◢):", good.getName()+", come back!");
                    IChooseYou();
                }
                bad.beAttacked(damage);
                if (Integer.parseInt(bad.getHp()) <= 0) {       // if bad is knocked down, this battle ends.
                    Speak("● ﹏☉:", "Bad pokemon "+bad.getName()+" is knocked out!\n");
                    return true;
                }

                // bad pokemon's turn.
                damage = bad.badAttack();
                good.beAttacked(damage);

                if (Integer.parseInt(good.getHp()) <= 0) {    // if good is knocked down, user need to choose another one
                    Speak("(‘﹏*๑):","Your pokemon "+good.getName()+" is knocked out!\n");
                    goodGuys.remove(good);      // the knocked down pokemon cannot be chosen again.
                    myPokeAmount--;
                    if(myPokeAmount > 0) {      // if all good guys are knocked down, this game ends.
                        IChooseYou();
                    } else{
                        return false;       // this means losing the game (can't continue the gam).
                    }
                }

            } else {                        // same contents except the attacking sequence.
                damage = bad.badAttack();
                good.beAttacked(damage);
                if (Integer.parseInt(good.getHp()) <= 0) {
                    Speak("(‘﹏*๑):","Your pokemon "+good.getName()+" is knocked out!\n");
                    goodGuys.remove(good);
                    myPokeAmount--;
                    if(myPokeAmount > 0) {
                        IChooseYou();
                    } else{
                        return false;
                    }
                }

                damage = new String[]{"failed", "0", ""};
                int action = good.selectMotion();
                if (action == 0) {
                    damage = good.goodAttack();
                } else if (action == 1) {
                    IChooseYou();
                }

                bad.beAttacked(damage);
                if (Integer.parseInt(bad.getHp()) <= 0) {
                    Speak("(◣_◢):", "Bad pokemon "+bad.getName()+" is knocked out!\n");
                    return true;
                }
            }

            // let both sides' pokemons recover after a round.
            for (int i = 0; i < goodGuys.size(); ++i) {
                goodGuys.get(i).recoverAfterRound();
            }
            bad.recoverAfterRound();
        }
    }

    public static void main(String[]args) {
        load();
        usedPokemons = new boolean[amount];       // ensures no repeat

        graphicsStart();

        // get user name
        String name;
        Speak("(´• ω •`):", "First of all, what's your name?\n");
        name = kb.nextLine();

        Speak("＼(＾▽＾)／:", "It's my honour to meet you here, "+name+"!\n\n");

        pickTeam();
        amount-=myPokeAmount;

        Speak("\n(◣_◢):", "Now you are all set, "+name+"! Let's start the epic fight!\n");
        kb.nextLine();

        // set up bad pokemon
        int badInd = getBadInd();      // randomly get an index of a bad pokemon
        usedPokemons[badInd] = true;
        bad = pokemons.get(badInd);

        // graphics
        System.out.print("Loading [");
        for(char c: ("█▒█▒█▒█▒█▒█▒█▒█▒█▒█").toCharArray()){
            System.out.print(c);
            AdvancedSleep(100);
        }
        System.out.println("]");

        // this is where the actual battle starts.
        int battle = 1;     // count number of battles.
        while (amount>0){
            Speak("\nψ(▼へ▼メ)～→:", "BATTLE No."+battle+". Ready--go!\n\n");

            Speak("( ▀ 益 ▀ ):", "Your enemy is "+bad.getType()+" Pokemon "+bad.getName()+" who is resistant to "+
                    bad.getResistance()+" but afraid of "+bad.getWeakness()+".\n\n");

            IChooseYou();      // get a pokemon to fight.

            boolean gameContinue;      // indicate if user still have pokemon to continue the game.
            if ((int) (Math.random()*2) == 0) {     // random choose if bad Pokemon attack first or not
                gameContinue = rounds(true);

            } else {
                gameContinue = rounds(false);
            }

            if (gameContinue) {     // user still has pokemon(s).
                amount--;       // one bad pokemon is gone

                if (amount == 0) {      // game should end at this point
                    break;
                }

                // reset bad for the next battle
                badInd = getBadInd();
                usedPokemons[badInd] = true;
                bad = pokemons.get(badInd);

                battle++;

                Speak("(;｀O´)o:", "check out your pokemons' situations:\n");
                for(int i = 0; i<goodGuys.size(); ++i){
                    goodGuys.get(i).recoverAfterBattle();       // recove users pokemon
                    System.out.println("    "+(i+1)+"."+goodGuys.get(i).toString()+"\n");
                }

            }   else {
                Speak("\no͡͡͡͡͡͡╮(｡>口<｡)╭o͡͡͡͡͡͡ ᵑ৹!:", "All your pokemons are defeated!No---------\n");
                amount = -1;        // this means lose the game
            }
        }

        graphicsEnd(name, amount);
    }
}