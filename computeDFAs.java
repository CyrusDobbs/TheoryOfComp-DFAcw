// Cyrus Dobbs 1529854
import java.util.ArrayList;

class computeDFAs {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Incorrect use of arguments. Please see README.txt.");
            System.exit(-1);
        }
        if(args[0].equals("-c")) {

            if (args.length != 2){
                System.err.println("Please enter 2 arguments. See README.txt for help.");
                System.exit(-1);
            }

            // D1
            DFA compDFA = new DFA(new DFA(args[1]));
            // D1 compliment
            Complementation(compDFA).printDFA();
        }
        else if(args[0].equals("-i")) {

            if (args.length != 3){
                System.err.println("Please enter 3 arguments. See README.txt for help.");
                System.exit(-1);
            }

            // Intersection of D1 & D2
            setOperation(new DFA(args[1]), new DFA(args[2]), "intersection").printDFA();
        }
        else if(args[0].equals("-s")) {

            if (args.length != 3){
                System.err.println("Please enter 3 arguments. See README.txt for help.");
                System.exit(-1);
            }

            // Symmetric Difference of D1 & D2
            symmetricDifference(new DFA(args[1]), new DFA(args[2])).printDFA();
        }

        else if(args[0].equals("-ne")) {

            if (args.length != 2){
                System.err.println("Please enter 2 arguments. See README.txt for help.");
                System.exit(-1);
            }

            // Check if D1's language is empty
            emptyCheck(new DFA(args[1]), true);
        }
        else if(args[0].equals("-e")){

            if (args.length != 3){
                System.err.println("Please enter 3 arguments. See README.txt for help.");
                System.exit(-1);
            }

            DFA dfa1 = new DFA(symmetricDifference(new DFA(args[1]), new DFA(args[2])));

            if (emptyCheck(dfa1, false)){
                System.out.println("equivalent");
            }
            else{
                System.out.println("not equivalent");
            }
        }
        else {
            System.err.println("Incorrect use of arguments. Please see README.txt.");
            System.exit(-1);
        }
    }

    private static DFA Complementation(DFA dfa) {

        // Temporary array to hold modified accept state(s)
        ArrayList<String> newAcceptStates = new ArrayList<String>();

        // Iterate through states, if it isn't an accept state, now make it an accept state.
        int count = 0;
        for (String state : dfa.getStates()){
            if(!checkContains(dfa.getAcceptStates(), state)){
                newAcceptStates.add(state);
                count++;
            }
        }

        // Update DFA with new accept state(s)
        dfa.setAcceptStates(newAcceptStates);
        dfa.setNoOfAcceptStates(count);

        return  dfa;
    }

    private static DFA setOperation(DFA dfa1, DFA dfa2, String operation) {
        // Create new empty DFA
        DFA operatedDFA = new DFA();

        // intersection/union alphabet
        // = alphabet is always a b for this coursework
        operatedDFA.setAlphabet(dfa1.getAlphabet());
        operatedDFA.setSizeOfAlphabet(dfa1.getSizeOfAlphabet());

        // Intersection/union of set of states
        // = all combinations of states
        operatedDFA.setStates(multiplyArray(dfa1.getStates(), dfa2.getStates()));
        operatedDFA.setNoOfStates(operatedDFA.getStates().size());

        // Intersection/union of start state
        // = both start states combined
        operatedDFA.setStartState(dfa1.getStartState()+dfa2.getStartState());

        // Intersection of accept states
        if (operation.equals("intersection")){
            // Intersection is just all combinations of accept states
            operatedDFA.setAcceptStates(multiplyArray(dfa1.getAcceptStates(), dfa2.getAcceptStates()));
        }
        else if (operation.equals("union")){

            // Temporary acceptStates
            ArrayList<String> acceptStates = new ArrayList<>();

            // Union is any combinations of states as long as one of the combined states is an accept state
            for (String state1 : dfa1.getStates()) {
                for (String state2 : dfa2.getStates()){
                    // Check if either of the states are accept states
                    if (checkContains(dfa1.getAcceptStates(), state1) || checkContains(dfa2.getAcceptStates(), state2))
                    acceptStates.add(state1+state2);
                }
            }
            // Update with new accept states
            operatedDFA.setAcceptStates(acceptStates);
        }

        // Update noOfAcceptStates
        operatedDFA.setNoOfAcceptStates(operatedDFA.getAcceptStates().size());

        // Intersection/union of transitional functions
        ArrayList<ArrayList<String>> transitionalFunctionArray = new ArrayList<>();

        for (ArrayList<String> row1 : dfa1.getTransitionFunctions()) {
            for (ArrayList<String> row2: dfa2.getTransitionFunctions()){
                // Each child array represents a line of transitional function text
                ArrayList<String> newRowOfTransitionalArray = new ArrayList<>();
                for (int x = 0; x < operatedDFA.getSizeOfAlphabet(); x++){
                    newRowOfTransitionalArray.add(row1.get(x) + row2.get(x));
                }
                // Add to father array
                transitionalFunctionArray.add(newRowOfTransitionalArray);
            }
        }

        // Update transitional functions
        operatedDFA.setTransitionFunctions(transitionalFunctionArray);

        // Return operatedDFA
        return operatedDFA;
    }

    public static DFA symmetricDifference(DFA dfa1, DFA dfa2){

        // Union of D1 & D2 2 3
        DFA D1UD2 = new DFA(setOperation(dfa1, dfa2, "union"));
        // Intersection of D1 & D2
        DFA D1intD2 = new DFA(setOperation(dfa1, dfa2, "intersection"));

        // We now need to remove any common accept states shared between (D1 U D2) & (D1 int D2)
        ArrayList<String> dfa1AcceptStates = D1UD2.getAcceptStates();
        ArrayList<String> dfa2AcceptStates = D1intD2.getAcceptStates();

        // We use the logic of union - intersection = symmetric difference

        // Union
        ArrayList<String> unionAcceptStates = new ArrayList<String>(dfa1AcceptStates);
        unionAcceptStates.addAll(dfa2AcceptStates);

        //Intersection
        ArrayList<String> intersectionAcceptStates = new ArrayList<String>(dfa1AcceptStates);
        intersectionAcceptStates.retainAll(dfa2AcceptStates);

        // Union - Intersection
        unionAcceptStates.removeAll(intersectionAcceptStates);

        D1UD2.setAcceptStates(unionAcceptStates);
        D1UD2.setNoOfAcceptStates(unionAcceptStates.size());

        return D1UD2;
    }

    private static boolean emptyCheck(DFA dfa, boolean print){
        // populate states
        ArrayList<State> stateList = new ArrayList<>();
        for (String state : dfa.getStates()) {
            // add state
            stateList.add(new State(state, false, false));
        }

        // add connections
        int stateCount = 0;
        // Iterate through lines of encoded transitions
        // NOTE: each line corresponds to two connections to a state in the stateList
        // Do to the stateList and the corresponding lines of encoded transitions being in
        // the same order, we use a count.
        for (ArrayList<String> transitionLine : dfa.getTransitionFunctions()) {

            // here we add each of the two connections on the transitionLine to the corresponding state
            boolean connectionStringIsA = true; // The first & 2nd transition of a line corresponds to 'a' & 'b' input respectively.
            for (String transition : transitionLine) {
                stateList.get(stateCount).addConnection(stateList.get(stateSearch(transition, stateList)));
                if (connectionStringIsA){
                    stateList.get(stateCount).addConnectionString("a");
                    connectionStringIsA = false;
                }
                else{
                    stateList.get(stateCount).addConnectionString("b");
                }
            }
            stateCount++;
        }

        // set start state
        stateList.get(stateSearch(dfa.getStartState(), stateList)).setStartState(true);

        // set accept state(s)
        for (String state : dfa.getAcceptStates()) {
            stateList.get(stateSearch(state, stateList)).setAcceptState(true);
        }

        DFS dfs = new DFS();
        if (print){
            return dfs.dfs(stateList, true);
        }
        else {
            return dfs.dfs(stateList, false);
        }

    }

    private static int stateSearch(String stateName, ArrayList<State> stateList) {
        int indexOfState = -1;
        for (State currentState : stateList) {
            indexOfState += 1;
            if (currentState.getStateName().equals(stateName)) {
                return indexOfState;
            }
        }
        return -1;
    }

    // Method to produce the product of two arrays.
    private static ArrayList<String> multiplyArray(ArrayList<String> array1, ArrayList<String> array2){
        ArrayList<String> multipliedArray = new ArrayList<>();
        for (String state1 : array1) {
            for (String state2 : array2){
                multipliedArray.add(state1+state2);
            }
        }
        return multipliedArray;
    }

    private static Boolean checkContains(ArrayList<String> statesToSearchThrough, String toCheck){
        // Iterate through ArrayList to check if it contains a String
        for (String state : statesToSearchThrough){
            if (state.equals(toCheck)){
                // Return true if found
                return true;
            }
        }
        // Return false if not found
        return false;
    }
}