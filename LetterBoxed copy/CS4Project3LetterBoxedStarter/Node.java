// CS4: Project 3 LetterBoxed, Node class for breadth-first search queue
// version 1.0

// Finish this file

import java.util.*;

public class Node {
    private int letterSet; // cumulative letters for path including this node
    private char lastChar; // last char in word
    private String word; // not part of identity: a word consistent with identity
    private Node parent; // not part of identity: node that put it on queue

    public Node(String word, Node parent) {

        this.word = word;
        this.parent = parent;
        int length = word.length();
        char[] letters = word.toCharArray();
        this.lastChar = letters[length-1];
        if(parent == null){
            for(char c: letters){
                letterSet |= (1 << (c - 'A'));
            }
        }
        else{
            this.letterSet = parent.getLetterSet();
            for(char c: letters){
                letterSet |= (1 << (c - 'A'));
                
            }
        }

    }

    public int getLetterSet() { return letterSet; }

    public char getLastChar() { return lastChar; }

    public String getWord() { return word; }

    public Node getParent() { return parent; }

    public boolean equals(Object obj) {
        if(obj == this ) 
            return true; 
        if(obj == null) 
            return false;
        if (obj.getClass() != this.getClass()) 
            return false;
        Node node = (Node) obj;
        if(this.letterSet == node.getLetterSet() && this.lastChar == node.getLastChar()){
            return true;
        }
        else 
            return false;
    }

    public int hashCode() {
        return Objects.hash(this.letterSet, this.lastChar); 
    }
    public static void main(String[] args) {
        Node node = new Node("cat", null);
        System.out.println(node.getLetterSet());//524293
        Node node2 = new Node("CAT", node);
        System.out.println(node2.getLetterSet());
    }
}