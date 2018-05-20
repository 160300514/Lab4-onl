package helper;

import graph.Graph;
import vertex.Vertex;

import java.io.InputStream;
import java.util.Set;

public class GeneralInputHelper
{
    private Memento mem;
    private ParserInputHelper pih;
    private ParseCommandHelper pch;

    public GeneralInputHelper()
    {
        mem = new Memento();
        pih = new ParserInputHelper();
        pch = new ParseCommandHelper();
    }

    public String SaveChange(Graph gra) throws CloneNotSupportedException {
        return mem.save(gra);
    }

    public void SaveChange(Graph gra,String Storelabel) throws CloneNotSupportedException {
        mem.save(gra, Storelabel);
    }

    public Set<String> SaveChange(Graph[] gra) throws CloneNotSupportedException {
        return mem.save(gra);
    }

    public void SaveChange(Graph[] gra,String Storelabel) throws CloneNotSupportedException {
        mem.save(gra, Storelabel);
    }

    public Graph recallSpecGra(String GraphStoreLabel)
    {
        Set<State> tmp = recallAll();
        for(State s: tmp)
        {
            if(s.getLabel().equals(GraphStoreLabel))
                return s.getStored();
        }
        return null;
    }

    public Set<State> recallAll()
    {
        return mem.restore();
    }

    public Vertex stov(String label)
    {
        return pih.LabeltoVertex(label);
    }

    public Graph listenCmdInput(String cmdIn, InputStream is) throws Exception {
        return pch.parseAndExecuteCommand(pih, cmdIn, is);//use "Quit" to Exit
    }

    public Graph fileReadConfig(String fileName) throws Exception
    {
        return pih.establishGraph(fileName);
    }

    public Graph LabelModifier(String typeName, String preLabel, String modifyLabel) throws Exception {
        return pih.cmdLabelModify(typeName,preLabel,modifyLabel);
    }

    public void ClearCaches()
    {
        pih.Clear();
    }
}
