import java.util.ArrayList;
import java.util.Stack;

public class DFS {

    private Stack<State> stack;
    private ArrayList<String> inputString;

    public DFS(){
        // Initialise stack and inputString Array
        this.stack = new Stack<>();
        this.inputString = new ArrayList<>();
    }

    public boolean dfs(ArrayList<State> stateList, boolean print){
        // Start the search from the startStart
        return dfsWithStack(startStateSearch(stateList), print);
    }

    private State startStateSearch(ArrayList<State> stateList) {
        // Searches through the states to find the startState
        for (State currentState : stateList) {
            if (currentState.isStartState()) {
                return currentState;
            }
        }
        return null;
    }

    private boolean dfsWithStack(State startState, boolean print) {

        boolean acceptStateFound = false;

        // Starts the start with the startState
        this.stack.add(startState);
        startState.setVisited(true);
        // If the start start is an accept state then the empty set is accepted
        if (startState.isAcceptState()){

            if (print){
                System.out.println("language non-empty - e accepted");
            }

            acceptStateFound = true;
            this.stack.push(startState);
        }

        // Keep going until no States are left on the start
        while (!stack.isEmpty() & !acceptStateFound) {

            State currentState = this.stack.pop();

            // When we reach an accept state then we print the input and end application
            if (currentState.isAcceptState()){

                if (print){
                    System.out.println("language non-empty - " + String.join("", inputString) + " accepted");
                }

                acceptStateFound = true;
            }

            // Iterate through connected States until one is found that has not been visited
            int count = 0;
            for (State s : currentState.getConnectedStates()) {
                if (!s.isVisited()) {
                    // Here we add the input that caused the transition from one state to the next
                    inputString.add(s.getConnectionStrings().get(count));
                    count++;
                    s.setVisited(true);
                    // Push onto the stack
                    this.stack.push(s);
                }
            }
        }

        // If the stack gets to empty and no accept state has been found
        // then the DFA has an empty language.
        if (!acceptStateFound & print){
            System.out.println("language empty");
        }

        return !acceptStateFound;
    }
}
