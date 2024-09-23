// CS4: Project 3 LetterBoxed, LetterBoxed class that stores dictionary
//      and finds shortest solutions to NYT Letter Boxed puzzles using
//      breadth-first search over an implicit digraph.
// version 1.0

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;



public class LetterBoxed {

    private ArrayList<String> wordListUpper;

    public LetterBoxed(Scanner wordListScanner) {
        
        wordListUpper = new ArrayList<String>();
        while(wordListScanner.hasNextLine()){
            String word = wordListScanner.nextLine();
            wordListUpper.add(word.toUpperCase());
        }
        wordListScanner.close();
    }

    private boolean includeWord(String word, String[] sides, Set<String> excludeWords) {
        if(!(word.length() > 2)){return false;}// 1. at least 3 letters
        if(excludeWords != null && excludeWords.contains(word)){return false;}// 2. not in the excludeWords set,
        boolean present = false;
        String[] letters = word.split("");
        int lastSide =0;
        int curSide;
        
        for(int i = 0; i < letters.length; i++){
            curSide =0;
            for(String side: sides){
                curSide++;
                int index = side.indexOf(letters[i]);
                if(index >= 0){
                    if(curSide == lastSide){// 4. no two consecutive letters from the same side
                        return false;
                    }
                   
                    present = true;
                    lastSide = curSide;
                    break;
                }
                
            }
            if(!present){// 3. only include letters from the sides
                return false;
            }
            present = false;
        }
        return true; 
    }

    public Iterable<String> findSolution(String[] sides, Set<String> excludeWords) {
        ArrayList<String> words = new ArrayList<>(); 
        ArrayList<String>[] startsWith = (ArrayList<String>[]) new ArrayList[26]; 
        int targetLetterSet = 0; 
        HashSet<Node> nodesEnqueued = new HashSet<>(); 
        ArrayDeque<Node> queue = new ArrayDeque<>();
  
        for(int i = 0; i < startsWith.length; i++){ 
            startsWith[i] = new ArrayList<String>();
        }

        for(int i = 0; i < wordListUpper.size(); i++){
            String word = wordListUpper.get(i);
            if(includeWord(word, sides, excludeWords)){
                words.add(word);
                startsWith[word.charAt(0) - 'A'].add(word);
                
            }
        }

        for(String side: sides){
            char[] characters = side.toCharArray();
            for(char c: characters){
                targetLetterSet |= (1 << (c - 'A'));
            }
        }

        for(int i = 0; i < words.size(); i++){
            Node node = new Node(words.get(i), null);
            if (!nodesEnqueued.contains(node)){
                nodesEnqueued.add(node);
                queue.offer(node);

            }
        }

        ArrayList<String> ret = new ArrayList<String>();
        while(!queue.isEmpty()){
            Node node = queue.poll();
            if(targetLetterSet == node.getLetterSet()){
                while(true){
                    String word = node.getWord();
                    ret.add(0, word);
                    if(node.getParent() == null){
                        break;
                    }
                    else
                        node = node.getParent();
                }
                return ret;
            }
            else {
                String word = node.getWord();
                char lastCharacter = word.charAt(word.length() -1 );
                int index = lastCharacter - 'A';
                ArrayList<String> list = startsWith[index];
                Node par = node;
                for(int i = 0; i <list.size(); i++){
                    
                    word =list.get(i);
                    node = new Node(word, par);
                    if (!nodesEnqueued.contains(node)){
                        nodesEnqueued.add(node);
                        queue.offer(node);
        
                    }
                }          
            }
        }
        return null; 
    }

    public boolean checkSolution(String[] sides, Set<String> excludeWords, Iterable<String> solution) {
        String lastLetter = null;
        boolean[][] used = new boolean[4][3];
        for(String word: solution){
            if(lastLetter != null && !word.substring(0, 1).equals(lastLetter)){// 6. every word after the first starts with the last letter of the previous word
                return false;
            }
            int length = word.length();
            lastLetter = word.substring(length -1);
            
        if(excludeWords != null && excludeWords.contains(word)){return false;}// 2. not in the excludeWords set,
        boolean present = false;
        String[] letters = word.split("");
        
        
        int lastSide =0;
        int curSide;
        
        for(int i = 0; i < letters.length; i++){
            curSide =0;
            for(String side: sides){
                curSide++;
                int index = side.indexOf(letters[i]);
                if(index >= 0){
                    if(curSide == lastSide){// 4. no two consecutive letters from the same side
                        return false;
                    }
                    used[curSide -1 ][index] = true;
                    present = true;
                    lastSide = curSide;
                    break;
                }
                
            }
            if(!present){// 3. only include letters from the sides
                return false;
            }
            present = false;
        }
            if(length < 3 || !wordListUpper.contains(word)){// 1. sequence of words with at least three letters each from wordListUpper
                return false;
            }
        }
        for(int r =0; r < 4; r++){
            for(int c = 0; c <3; c++){
                if(used[r][c] == false){// 4. includes every letter from every side
                    return false;
                }
            }
        }
        

        return true;
    }
    public static void main(String[] args) {
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new FileInputStream("wordlistcs4.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LetterBoxed lb = new LetterBoxed(fileScanner);
        fileScanner.close();
        String[] test = { "IOE", "WAQ", "UNR", "SMK" };

        // System.out.println(lb.includeWord("MORES", test, null));
    }
}
