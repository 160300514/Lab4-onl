package graph;

import edge.WordNeighborhood;
import vertex.Word;

import java.io.StringWriter;
import java.util.*;

public class GraphPoet extends ConcreteGraph<Word,WordNeighborhood> {

    private Map<Word, Set<WordNeighborhood> > map;//src->edge set
    private Set<Word> vertices;
    private String label;

    public String getLabel() {
        return label;
    }


    GraphPoet(String label)
    {
        super.setGraphName(label);
        this.label = label;
        map = new HashMap<>();
        vertices = new HashSet<>();
        super.setVertices(vertices);
        super.setMap(map);
    }

    GraphPoet(String label, Set<Word> s, Map<Word, Set<WordNeighborhood>> maps)
    {
        this.vertices = new HashSet<>(s);
        this.map = new HashMap<>(maps);
        this.label = label;
    }

    public GraphPoet clone()
    {
        return new GraphPoet(label, vertices, map);
    }

    @Override
    public boolean addVertex(Word v) {
        if(v!=null)
        {
            this.vertices.add(v);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeVertex(Word v) {
        if(v!=null)
        {
            this.vertices.remove(v);
            map.remove(v);
            for(Word w:map.keySet())
            {
                Iterator it = this.map.get(w).iterator();
                while(it.hasNext())
                {
                    WordNeighborhood wordNeighborhood = (WordNeighborhood) it.next();
                    if(wordNeighborhood.containVertex(v))
                    {
                        it.remove();
                    }
                }
                /*
                for(WordNeighborhood wn:map.get(w))
                {
                    if(wn.containVertex(v))
                        map.get(w).remove(wn);
                }*/
            }
            return true;
        }
        return false;
    }

    @Override
    public Set<Word> vertices() {
        return this.vertices;
    }

    @Override
    public Map<Word, List<Double>> sources(Word target) {
        Map<Word, List<Double>> ans = new HashMap<>();
        for(Word w: map.keySet())
        {
            for(WordNeighborhood wn: map.get(w))
            {
                if(wn.getTarget().equals(target))
                {
                    if(ans.containsKey(w))
                    {
                        ans.get(w).add(wn.getWeight());
                    }
                    else
                    {
                        List<Double> tmp = new ArrayList<>();
                        tmp.add(wn.getWeight());
                        ans.put(w, tmp);
                    }
                }
            }
        }
        return ans;
    }

    @Override
    public Map<Word, List<Double>> targets(Word source) {
        Map<Word, List<Double>> ans = new HashMap<>();
        if(map.keySet().contains(source))
        {
            for(WordNeighborhood wn: map.get(source))
            {
                if(ans.containsKey(wn.getTarget()))
                {
                    ans.get(wn.getTarget()).add(wn.getWeight());
                }
                else
                {
                    List<Double> tmp = new ArrayList<>();
                    tmp.add(wn.getWeight());
                    ans.put(wn.getTarget(), tmp);
                }
            }
        }
        return ans;
    }

    @Override
    public boolean addEdge(WordNeighborhood edge, Boolean filein) {
        if(!this.vertices.contains(edge.getTarget())) this.vertices.add(edge.getTarget());
        if(!this.vertices.contains(edge.getSource())) this.vertices.add(edge.getSource());
        if(map.keySet().contains(edge.getSource()))
        {
            if(map.get(edge.getSource()).contains(edge))
            {
                for(WordNeighborhood wn: map.get(edge.getSource()))
                {
                    if(wn.equals(edge))
                    {
                        wn.setWeight(wn.getWeight()+1);
                        break;
                    }
                }
            }
            else
                map.get(edge.getSource()).add(edge);
        }
        else
        {
            Set<WordNeighborhood> ans = new HashSet<>();
            ans.add(edge);
            map.put(edge.getSource(), ans);
        }
        return true;
    }

    @Override
    public boolean removeEdge(WordNeighborhood edge) {
        boolean removed;
        return map.keySet().contains(edge.getSource()) && map.get(edge.getSource()).remove(edge);
    }


    @Override
    public Set<WordNeighborhood> edges() {
        Set<WordNeighborhood> ans = new HashSet<>();
        for(Word w: map.keySet())
        {
            ans.addAll(map.get(w));
        }
        return ans;
    }

    @Override
    public String toString()
    {
        StringWriter swt = new StringWriter();
        swt.write("Graph: Poet, vertices:\n");
        vertices().forEach(word -> swt.write("\t"+word.toString()+"\n"));
        swt.write("Edges:\n");
        edges().forEach(wordNeighborhood -> swt.write("\t"+wordNeighborhood.toString()+"\n"));
        return swt.toString();
    }
}
