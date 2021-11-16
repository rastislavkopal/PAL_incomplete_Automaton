package pal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static int n_automaton_states;
    private static int alphabet_size;
    private static int n_final_states;
    private static int n_positive_examples;
    private static int n_negative_examples;
    private static int example_len;

    private static int[][] transition;

    private static  String[] positiveExamples;
    private static  String[] negativeExamples;

    private static List<Set<Integer>> solutionSet;

    // runs the automaton from selected start state
    // follows the edges based on selected word
    // returns final state int, where automaton ended the word
    private static int runAutomaton(int startState, String word){
        int currentState = startState;
        // iterate the whole word and move the counter
        // cast char to ascii value and move it as a = 0 ==> ascii - 97
        for (int i = 0; i < word.length(); i++){
            int asciiChar = (int) word.charAt(i) - 97;
            currentState = transition[currentState][asciiChar];
        }

        return currentState;
    }

    private static void findSolutions() {
    // check each starting state
        for (int i = 0; i < n_automaton_states; i++){
            // for each word in positive_examples
            for (int j = 0; j < n_positive_examples; j++){
                int finalState = runAutomaton(i, positiveExamples[j]);
                solutionSet.get(i).add(finalState);
            }
        }
    }

    private static void printSolutions(){
        for (int i = 0; i < n_automaton_states; i++){
            Set<Integer> sols = solutionSet.get(i);
            if (sols.size() != n_final_states)
                continue;
            System.out.print(i + " ");
            sols.forEach(item -> System.out.print(item + " "));
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());

        n_automaton_states = Integer.parseInt(tokenizer.nextToken());
        alphabet_size = Integer.parseInt(tokenizer.nextToken());
        n_final_states = Integer.parseInt(tokenizer.nextToken());
        n_positive_examples = Integer.parseInt(tokenizer.nextToken());
        n_negative_examples = Integer.parseInt(tokenizer.nextToken());
        example_len = Integer.parseInt(tokenizer.nextToken());

        solutionSet = new ArrayList<>(n_automaton_states);
        for (int i = 0; i < n_automaton_states; i++)
            solutionSet.add(i, new TreeSet<>());
        transition = new int[n_automaton_states][alphabet_size];

        positiveExamples = new String[n_positive_examples];
        negativeExamples = new String[n_negative_examples];

        // for each state of the automaton
        for (int i = 0; i < n_automaton_states; i++){
            tokenizer = new StringTokenizer(br.readLine());
            int transition_sate = Integer.parseInt(tokenizer.nextToken());
            // for each char from automaton's alphabet
            for (int j = 0; j < alphabet_size; j++ ){
                transition[transition_sate][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }


        // for each positive example
        for (int i = 0; i < n_positive_examples; i++){
            tokenizer = new StringTokenizer(br.readLine());
            positiveExamples[i] = tokenizer.nextToken();
        }
        // for each negative example
        for (int i = 0; i < n_negative_examples; i++){
            tokenizer = new StringTokenizer(br.readLine());
            negativeExamples[i] = tokenizer.nextToken();;
        }

        findSolutions();
        printSolutions();

    }
}
