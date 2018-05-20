package helper;


import graph.Graph;

public class State
{
    private int timStamp;
    private Graph tobeStored;
    private String label;

    State(int time, Graph Storage, String label)
    {
        this.timStamp = time;
        this.tobeStored = Storage;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Graph getStored() {
        return tobeStored;
    }

    public int getTimStamp() {
        return timStamp;
    }

    public void setTobeStored(Graph tobeStored) {
        this.tobeStored = tobeStored;
    }

    public void setTimStamp(int timStamp) {
        this.timStamp = timStamp;
    }
}
