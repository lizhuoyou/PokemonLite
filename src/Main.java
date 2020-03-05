import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        try {
            Scanner pokeFile = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
            int amount = Integer.parseInt(pokeFile.nextLine());
            ArrayList<String[]> pokemons = new ArrayList<String[]>();
            ArrayList<String[]> goodGuys = new ArrayList<String[]>();
            for(int i=0; i< amount;++i){
                pokemons.add(pokeFile.nextLine().split(","));
            }
            pokeFile.close();

            char[] graphic1 =
                    ("                                .::.\n"+
                    "                              .;:**'               \n"+
                    "                              `                   \n"+
                    "  .:XHHHHk.              db.   .;;.     dH  MX    \n"+
                    "oMMMMMMMMMMM       ~MM  dMMP :MMMMMR   MMM  MR      ~MRMN\n"+
                    "QMMMMMb  \"MMX       MMMMMMP !MX' :M~   MMM MMM  .oo. XMMM 'MMM\n"+
                    "  `MMMM.  )M> :X!Hk. MMMM   XMM.o\"  .  MMMMMMM X?XMMM MMM>!MMP\n"+
                    "   'MMMb.dM! XM M'?M MMMMMX.`MMMMMMMM~ MM MMM XM `\" MX MMXXMM\n"+
                    "    ~MMMMM~ XMM. .XM XM`\"MMMb.~*?**~ .MMX M t MMbooMM XMMMMMP\n"+
                    "     ?MMM>  YMMMMMM! MM   `?MMRb.    `\"\"\"   !L\"MMMMM XM IMMM\n"+
                    "      MMMX   \"MMMM\"  MM       ~%:           !Mh.\"\"\" dMI IMMP\n"+
                    "      'MMM.                                             IMX\n"+
                    "       ~M!M                                             IMP\n").toCharArray();
            for(char c: graphic1){
                System.out.print(c);
                Thread.sleep(7);
            }

            String[] graphic2 = {
                    "                 .\"-,.__\n" ,
                    "                 `.     `.  ,\n" ,
                    "              .--'  .._,'\"-' `.\n" ,
                    "             .    .'         `'\n" ,
                    "             `.   /          ,'\n" ,
                    "               `  '--.   ,-\"'\n" ,
                    "                `\"`   |  \\\n" ,
                    "                   -. \\, |\n" ,
                    "                    `--Y.'      ___.\n" ,
                    "                         \\     L._, \\\n" ,
                    "               _.,        `.   <  <\\                _\n" ,
                    "             ,' '           `, `.   | \\            ( `\n" ,
                    "          ../, `.            `  |    .\\`.           \\ \\_\n" ,
                    "         ,' ,..  .           _.,'    ||\\l            )  '\".\n" ,
                    "        , ,'   \\           ,'.-.`-._,'  |           .  _._`.\n" ,
                    "      ,' /      \\ \\        `' ' `--/   | \\          / /   ..\\\n" ,
                    "    .'  /        \\ .         |\\__ - _ ,'` `        / /     `.`.\n" ,
                    "    |  '          ..         `-...-\"  |  `-'      / /        . `.\n" ,
                    "    | /           |L__           |    |          / /          `. `.\n" ,
                    "   , /            .   .          |    |         / /             ` `\n" ,
                    "  / /          ,. ,`._ `-_       |    |  _   ,-' /               ` \\\n" ,
                    " / .           \\\"`_/. `-_ \\_,.  ,'    +-' `-'  _,        ..,-.    \\`.\n" ,
                    "  '         .-f    ,'   `    '.       \\__.---'     _   .'   '     \\ \\\n" ,
                    "' /          `.'    l     .' /          \\..      ,_|/   `.  ,'`     L`\n" ,
                    "|'      _.-\"\"` `.    \\ _,'  `            \\ `.___`.'\"`-.  , |   |    | \\\n" ,
                    "||    ,'      `. `.   '       _,...._        `  |    `/ '  |   '     .|\n" ,
                    "||  ,'          `. ;.,.---' ,'       `.   `.. `-'  .-' /_ .'    ;_   ||\n" ,
                    "|| '              V      / /           `   | `   ,'   ,' '.    !  `. ||\n" ,
                    "||/            _,-------7 '              . |  `-'    l         /    `||\n" ,
                    " |          ,' .-   ,' ||               | .-.        `.      .'     ||\n" ,
                    " `'        ,'    `\".'    |               |    `.        '. -.'       `'\n" ,
                    "          /      ,'      |               |,'    \\-.._,.'/'\n" ,
                    "          .     /        .               .       \\    .''\n" ,
                    "        .`.    |         `.             /         :_,'.'\n" ,
                    "          \\ `...\\   _     ,'-.        .'         /_.-'\n" ,
                    "           `-.__ `,  `'   .  _.>----''.  _  __  /\n" ,
                    "                .'        /\"'          |  \"'   '_\n" ,
                    "               /_|.-'\\ ,\".             '.'`__'-( \\\n" ,
                    "                 / ,\"'\"\\,'               `/  `-.|\" \\"};
            for(String s: graphic2){
                System.out.print(s);
                Thread.sleep(100);
            }

            System.out.println("\n(￣▽￣)ノ: Welcome to Pokemon Arena!");

            System.out.println("\n(￣▽￣): PRESS ENTER TO CONTINUE");
            kb.nextLine();      // this is to pause the game so the user can interact with the program

            String name;
            System.out.println("(´• ω •`): First of all, what's your name?");
            name = kb.nextLine();

            System.out.println("It's my honour to meet you here, "+name+"!");
            System.out.println("(◣_◢): Before starting brain storming, please choose your top 4 favorites from the table:");
            kb.nextLine();

            System.out.println("+---+-----------+-----------+-----------+-----------+-----------+");
            System.out.println("|num|       name|         hp|       type| resistance|   weakness|");
            System.out.println("|---|-----------|-----------|-----------|-----------|-----------|");
            for (int i = 0; i < amount; ++i) {
                System.out.printf("|%3d|%11s|%11s|%11s|%11s|%11s|\n",i+1, pokemons.get(i)[0],pokemons.get(i)[1],pokemons.get(i)[2],pokemons.get(i)[3],pokemons.get(i)[4]);
            }
            System.out.println("+---+-----------+-----------+-----------+-----------+-----------+\n");

            boolean[] usedPokemons = new boolean[amount];       // ensures no repeat
            String[] names = new String[4]; // for displaying good guys and picking pokemon

            for(int i = 0; i<4;++i){
                System.out.println("(◣_◢): Type in the number to select a pokemon("+(4-i)+" choice(s) left):");
                int a = kb.nextInt()-1;
                while (usedPokemons[a] || a<0 || a >=amount){
                    System.out.println("((╬◣﹏◢)): This pokemon cannot be chosen. Try again:");
                    a = kb.nextInt()-1;
                }
                goodGuys.add(pokemons.get(a));
                usedPokemons[a] = true;
                names[i] = pokemons.get(a)[0];
            }
            amount-=4;
            /*
            Pokemon good1 = new Pokemon();
            Pokemon good1 = new Pokemon();
            Pokemon good1 = new Pokemon();
            Pokemon good1 = new Pokemon();
            */
            System.out.println("(◣_◢): Now you are all set, "+name+"! Let's start the epic fight!\n");
            kb.nextLine();

            int battle = 1;
            while (amount>0){
                System.out.println("ψ(▼へ▼メ)～→: BATTLE No."+battle+":");


                amount = 0;
                battle++;
            }

            if(amount == 0){
                System.out.println("°˖✧◝(⁰▿⁰)◜✧˖°: Congratulations, "+name+"! You win the game!");
            } else {
                System.out.println("ヽ(￣ω￣(。。 )ゝ: Haha you're such a loser, "+name+". Try hard next time.");
            }

        } catch (IOException exception) {
            System.out.println("This file might not exist");
        }
        catch(InterruptedException ex){
            System.out.println("The sleep sucks.");
        }

    }
}


