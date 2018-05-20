package helper;

import graph.ConcreteGraph;
import graph.Graph;
import javafx.util.Pair;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/*
Using the command to save the graph,
the class: memento:
keeps the timescale and the Graph saved and keep theee label of saving,
when it is asked to get back the stored graph, the memento return Graph
with the label at the first place and if there is no label attached, you
can use the return value of save graph.

For details:
String Save(Graph): with no label, and the string returned is the label,
you can use this value to get Graph stored,

and the method void Save(Graph, Label): attach a label to the graph to
store and get graph stored, which is strongly recommended.

Method:save,restore,constructor.

Inherited from the Wiki and using two algorithm:
Dijkstra and Floyd to calculate the value.
 */
public class Memento
{
    private PriorityQueue<Pair<Integer, State>> timeQue;
    private int currentTime;
    private boolean cleanTag;
    //private int batch;

    public Memento()
    {
        currentTime = 0;
        cleanTag = false;
        //batch = 0;
        timeQue = new PriorityQueue<>(Comparator.comparing(Pair::getKey));
    }

    public boolean save(Graph[] graphToBeSaved, String label) throws CloneNotSupportedException {
        if(cleanTag){
            timeQue.clear();
            currentTime=0;
            cleanTag=false;
        }
        for(Graph g: graphToBeSaved)
        {
            this.timeQue.add(new Pair<>(currentTime, new State(currentTime, ((ConcreteGraph)g).clone(), label)));
        }
        currentTime++;
        return true;
    }

    public Set<String> save(Graph[] graphToBeSaved) throws CloneNotSupportedException {
        if(cleanTag){
            timeQue.clear();
            currentTime=0;
            cleanTag=false;
        }
        Set<String> lab = new HashSet<>();
        for(Graph g: graphToBeSaved)
        {
            String label = String.valueOf((new HashEncoderHelper()).hash(g.toString()+String.valueOf(currentTime)));
            this.timeQue.add(new Pair<>(currentTime, new State(currentTime, ((ConcreteGraph)g).clone(), label)));
            lab.add(label);
        }
        currentTime++;
        return lab;
    }

    public boolean save(Graph graphToBeSaved, String label) throws CloneNotSupportedException {
        if(cleanTag){
            timeQue.clear();
            currentTime=0;
            cleanTag=false;
        }
        this.timeQue.add(new Pair<>(currentTime, new State(currentTime, ((ConcreteGraph)graphToBeSaved).clone(), label)));
        currentTime++;
        return true;
    }

    public String save(Graph graphToBeSaved) throws CloneNotSupportedException {
        if(cleanTag){
            timeQue.clear();
            currentTime=0;
            cleanTag=false;
        }
        String label = String.valueOf((new HashEncoderHelper()).hash(graphToBeSaved.toString()+String.valueOf(currentTime)));
        this.timeQue.add(new Pair<>(currentTime, new State(currentTime, ((ConcreteGraph)graphToBeSaved).clone(), label)));
        currentTime++;
        return label;
    }

    public Set<State> restore()
    {
        Set<State> ans = new HashSet<>();
        cleanTag = true;
        int tag = timeQue.peek().getKey();
        while(timeQue.size()>0&&timeQue.peek().getKey()==tag)
        {
            ans.add(timeQue.poll().getValue());
        }
        return ans;
    }

    public State getState()
    {
        return this.timeQue.peek().getValue();
    }
}
