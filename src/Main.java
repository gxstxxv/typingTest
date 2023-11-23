import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;


public class Main {

    public static void main(String[/*(╯°□°)╯︵ ┻━┻*/] args) {

        Scanner scanner = new Scanner(System.in);

        String abfrage = "";

        //gameloop
        while (abfrage.isEmpty()) {

            game();
            System.out.println();
            abfrage = scanner.nextLine();

        }

    }

    public static void game() {

        //clear screen befor every game
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);
        String eingabe;

        int sentenceLength = 7 + (int)(Math.random() * (15 - 7 + 1));

        String[] sentence = new String[sentenceLength];

        //print out the givin sentence
        for (int i = 0; i != sentenceLength; i++){
            sentence[i] = createWord();
            System.out.print(sentence[i] + " ");
        }

        //wait for player to press enter -> start
        if(scanner.nextLine().isEmpty()) {

            //start timer - is uses for wpm
            Instant starts = Instant.now();

            //takes input and splits it into words
            eingabe = scanner.nextLine();
            String[] entry = eingabe.split("\\s+");

            //variables used for calculating accuracy
            int correctLetters = 0;
            int letters = 0;


            //checks if entered sentence is equally long as givin sentence, if true starts checking word after word
            if (entry.length == sentence.length) {

                for (int i = 0; i != sentenceLength; i++) {

                    //used for displaying spelling mistakes
                    if (sentence[i].length() == entry[i].length()) {

                        for (int j = 0; j != sentence[i].length(); j++){

                            letters++;
                            if(sentence[i].charAt(j) == entry[i].charAt(j)) {
                                correctLetters++;
                                System.out.print(" ");
                            }
                            else {
                                System.out.print("^");
                            }

                        }

                    }
                    //used for displaying missing letters
                    else if(sentence[i].length() >= entry[i].length()){

                        for (int a = 0; a != entry[i].length(); a++) {
                            System.out.print(" ");
                        }

                        for (int a = 0; a != (sentence[i].length() - entry[i].length()); a++) {
                            System.out.print("~");
                        }

                        return;

                    }
                    //used for displaying unnecessary letters
                    else {

                        int overlength = entry[i].length() - sentence[i].length();

                        for (int a = 0; a != sentence[i].length(); a++) {
                            System.out.print(" ");
                        }

                        for (int a = 0; a != overlength; a++) {
                            System.out.print("~");
                        }

                        correctLetters = correctLetters - overlength;

                    }

                    System.out.print(" ");

                }

            }
            //used for displaying missing words
            else {

                for (int i = 0; i != entry.length; i++) {

                    for (int j = 0; j != entry[i].length(); j++){
                        System.out.print(" ");
                    }
                    System.out.print(" ");

                }

                for (int i = entry.length; i != sentence.length; i++) {

                    for (int j = 0; j != sentence[i].length(); j++){
                        System.out.print("~");
                    }
                    System.out.print(" ");

                }

                return;
            }

            //calculate accuracy
            double accuracy = (double) correctLetters / letters * 100;
            System.out.println("\n[Accuracy: " + (int)accuracy + "%]");

            //calculate Words per Minute
            Instant ends = Instant.now();
            long seconds = Duration.between(starts, ends).getSeconds();
            double minutes = (double) seconds / 60;
            int wpm = (int) (sentenceLength / minutes);
            System.out.print("[WPM: " + wpm + "]");

        }

    }

    //methode to create Random Words
    public static String createWord() {

        String word = "";

        while (word.length() <= 2) {

            try{
                word = Files.readAllLines(Paths.get("/Users/gg1/Documents/Code/Java/typingTest/src/wordsHarryPotter1.txt")).get((int) (Math.random() * (75320 + 1)));
            }
            catch(IOException e){
                System.out.println(e);
            }

        }

        return word;

    }

}