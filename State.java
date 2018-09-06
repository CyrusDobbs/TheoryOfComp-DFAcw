// Cyrus Dobbs 1529854
import java.util.ArrayList;

public class State {

    private String stateName;
    private boolean visited;
    private ArrayList<State> connectedStates;
    private ArrayList<String> connectionStrings;
    private boolean startState;
    private boolean acceptState;

    public State(String stateName, boolean startState, boolean acceptState){
        this.stateName = stateName;
        this.connectedStates = new ArrayList<>();
        this.connectionStrings = new ArrayList<>();
        this.startState = startState;
        this.acceptState = acceptState;
    }

    public void addConnection(State addedState){
        this.connectedStates.add(addedState);
    }

    public void addConnectionString(String addedConnectionString) {
        this.connectionStrings.add(addedConnectionString);
    }

    public ArrayList<State> getConnectedStates() {
        return connectedStates;
    }

    public void setConnectedStates(ArrayList<State> connectedStates) {
        this.connectedStates = connectedStates;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    public boolean isStartState() {
        return startState;
    }

    public void setStartState(boolean startState) {
        this.startState = startState;
    }

    public boolean isAcceptState() {
        return acceptState;
    }

    public void setAcceptState(boolean acceptState) {
        this.acceptState = acceptState;
    }

    public ArrayList<String> getConnectionStrings() {
        return connectionStrings;
    }

    public void setConnectionStrings(ArrayList<String> connectionStrings) {
        this.connectionStrings = connectionStrings;
    }
}
