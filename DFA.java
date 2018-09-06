import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

class DFA {

    private int noOfStates;
    private ArrayList<String> states = new ArrayList<>();
    private int sizeOfAlphabet;
    private ArrayList<String> alphabet = new ArrayList<>();
    private ArrayList<ArrayList<String>> transitionFunctions = new ArrayList<ArrayList<String>>();
    private String startState;
    private int noOfAcceptStates;
    private ArrayList<String> acceptStates = new ArrayList<>();

    public DFA(String textFile){
        try {
            Scanner sc = new Scanner(new File(textFile));
            noOfStates = Integer.parseInt(sc.nextLine());
            Collections.addAll(states, sc.nextLine().split("\\s+"));
            sizeOfAlphabet = Integer.parseInt(sc.nextLine());
            Collections.addAll(alphabet, sc.nextLine().split("\\s+"));
            for(int x = 0; x < noOfStates; x++){
                transitionFunctions.add(new ArrayList<String>());
                Collections.addAll(transitionFunctions.get(x), sc.nextLine().split("\\s+"));
            }
            startState = sc.nextLine();
            noOfAcceptStates = Integer.parseInt(sc.nextLine());
            if (noOfAcceptStates > 0){
                Collections.addAll(acceptStates, sc.nextLine().split("\\s+"));
            }

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public DFA(){
    }

    public DFA(DFA dfa){
        this.noOfStates = dfa.noOfStates;
        this.states = dfa.states;
        this.sizeOfAlphabet = dfa.sizeOfAlphabet;
        this.alphabet = dfa.alphabet;
        this.transitionFunctions = dfa.transitionFunctions;
        this.startState = dfa.startState;
        this.noOfAcceptStates = dfa.noOfAcceptStates;
        this.acceptStates = dfa.acceptStates;
    }

    public void printDFA(){
        System.out.println(Integer.toString(noOfStates));
        System.out.println(array2String(states));
        System.out.println(Integer.toString(sizeOfAlphabet));
        System.out.println(array2String(alphabet));
        for (ArrayList<String> array : transitionFunctions){
            System.out.println(array2String(array));
        }
        System.out.println(startState);
        System.out.println(noOfAcceptStates);
        System.out.print(array2String(acceptStates));
    }

    private String array2String(ArrayList<String> array){
        String convString = array.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
        return convString;
    }

    public int getNoOfStates() {
        return noOfStates;
    }

    public void setNoOfStates(int noOfStates) {
        this.noOfStates = noOfStates;
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public void setStates(ArrayList<String> states) {
        this.states = states;
    }

    public int getSizeOfAlphabet() {
        return sizeOfAlphabet;
    }

    public void setSizeOfAlphabet(int sizeOfAlphabet) {
        this.sizeOfAlphabet = sizeOfAlphabet;
    }

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(ArrayList<String> alphabet) {
        this.alphabet = alphabet;
    }

    public ArrayList<ArrayList<String>> getTransitionFunctions() {
        return transitionFunctions;
    }

    public void setTransitionFunctions(ArrayList<ArrayList<String>> transitionFunctions) {
        this.transitionFunctions = transitionFunctions;
    }

    public String getStartState() {
        return startState;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public int getNoOfAcceptStates() {
        return noOfAcceptStates;
    }

    public void setNoOfAcceptStates(int noOfAcceptStates) {
        this.noOfAcceptStates = noOfAcceptStates;
    }

    public ArrayList<String> getAcceptStates() {
        return acceptStates;
    }

    public void setAcceptStates(ArrayList<String> acceptStates) {
        this.acceptStates = acceptStates;
    }
}