// CS4: Project 3 LetterBoxed, main program.
// version 1.0

// Finish this file

import java.io.*;
import java.util.*;

public class Program {
    public static Scanner console = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new FileInputStream("wordlistcs4.txt"));
        LetterBoxed lb = new LetterBoxed(fileScanner);
        fileScanner.close();
        String[] test = { "IOE", "WAQ", "SMK", "UNR"};

    
        Scanner scan = new Scanner(System.in);
        HashSet<String> set = new HashSet<String>();
        String line;
        String[] sides;
        String words[];

        while (true) {
            System.out.println("Enter the letters from the four sides separted by a comma, and no spaces, or quit: ");
            line = console.nextLine().toUpperCase();
            if(line.equals("QUIT")){
                break;
            }
            sides = line.split(",");
            System.out.println("Enter any excluded words seprated by a comma and no spaces: ");
            line = console.nextLine().toUpperCase();
            words = line.split(",");
            for(String word: words){
                set.add(word);
            }
            Iterable<String> ans = lb.findSolution(sides, set);
            
            if(ans != null){
                System.out.println("This is your answer: ");
                for(String word: ans){
                    System.out.println(word);
                }
            }
            else
                System.out.println("There is no solution");
      

           

        }
    }
}
