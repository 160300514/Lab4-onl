package graph;

import edge.*;
import exception.InnerClassException;
import exception.NoSuchTypeException;
import vertex.*;

import java.util.Set;

/*
Using the build pattern, judge graph input legal,
and the vertex and edge construct is also put in
this class, keep the addVertex and addEdge method
the input is Vertex and Edge constructed by Vertex
Factory and Edge Factory., and judge this parameter
legal or not.

 */
public class GraphFactory
{
    private final String []graphTypeSet = {"GraphPoet", "SocialNetwork", "NetworkTopology", "MovieGraph"};
    private Graph ans;
    private String typeName;
    private Integer cnt = 0;

    // return empty graph
    public Graph createGraph(String label, String typeName) throws Exception {
        this.typeName = typeName;
        //System.out.println(this.typeName);
        if(typeName.equals(this.graphTypeSet[0]))
        {
            this.ans = new GraphPoet(label);
        }
        else if(typeName.equals(this.graphTypeSet[1]))
        {
            this.ans = new SocialNetwork(label);
        }
        else if(typeName.equals(this.graphTypeSet[2]))
        {
            this.ans = new NetworkTopology(label);
        }
        else if(typeName.equals(this.graphTypeSet[3]))
        {
            this.ans = new MovieGraph(label);
        }
        else
        {
            throw new NoSuchTypeException("[E] Graph typename not known.");
        }
        return this.ans;
    }

    public Graph exportGraph()
    {
        return this.ans;
    }

    private String GenerateUnconflictLabel(String Label)
    {
        boolean lk = false;
        for(Vertex v:(Set<Vertex>)this.ans.vertices())
        {
            if(v.getLabel().equals(Label))
            {
                lk = true;
                break;
            }
        }
        if(lk)
        {
            cnt+=1;
            return Label+String.valueOf(cnt);
        }
        return Label;
    }

    public Graph addVertex(Vertex vadd) throws NoSuchTypeException, InnerClassException
    {
        //vadd.setLabel(GenerateUnconflictLabel(vadd.getLabel()));

        if(this.typeName.equals(this.graphTypeSet[0]))
        {
            if(vadd.getClass() != Word.class)
                throw new NoSuchTypeException("[E] vertex to be added not convinced with the Graph.");
            this.ans.addVertex(vadd);
        }
        else if(this.typeName.equals(this.graphTypeSet[1]))
        {
            if(vadd.getClass() != Person.class)
                throw new NoSuchTypeException("[E] vertex to be added not convinced with the Graph.");
            this.ans.addVertex(vadd);
        }
        else if(this.typeName.equals(this.graphTypeSet[2]))
        {
            if(!(vadd.getClass() == Computer.class || vadd.getClass() == Router.class || vadd.getClass() == Server.class))
                throw new NoSuchTypeException("[E] vertex to be added not convinced with the Graph.");
            this.ans.addVertex(vadd);
        }
        else if(this.typeName.equals(this.graphTypeSet[3]))
        {
            if(!(vadd.getClass() == Movie.class || vadd.getClass() == Actor.class || vadd.getClass() == Director.class))
                throw new NoSuchTypeException("[E] vertex to be added not convinced with the Graph.");
            this.ans.addVertex(vadd);
        }
        else
        {
            throw new InnerClassException("Vertex adder");
            //System.out.println("[E] Vertex cannot be added: different type.");
        }
        return this.ans;
    }

    public Graph addEdge(Edge eadd, Boolean filein) throws CloneNotSupportedException, NoSuchTypeException, InnerClassException
    {
        if(!this.typeName.equals("SocialNetwork"))
        {
            Set<Edge> edges = (Set<Edge>) this.ans.edges();
            for(Edge e:edges)
            {
                for(Vertex v1: e.sourceVertices())
                {
                    for(Vertex v2:eadd.sourceVertices())
                    {
                        if(v1.equals(v2))
                        {
                            for(Vertex vv:eadd.targetVertices())
                                if(e.targetVertices().contains(vv))
                                    return this.ans;
                        }
                    }
                }
            }
        }
        if(typeName.equals(this.graphTypeSet[0]))
        {
            if(eadd.getClass().equals(SameMovieHyperEdge.class))
                return this.ans;
            if(eadd.getClass() != WordNeighborhood.class)
                throw new NoSuchTypeException("[E] edge to be added not convinced with the Graph.");
            this.ans.addEdge(eadd, filein);
        }
        else if(typeName.equals(this.graphTypeSet[1]))
        {
            if(eadd.getClass().equals(SameMovieHyperEdge.class))
                return this.ans;
            if(!(eadd.getClass() == FriendTie.class || eadd.getClass() == CommentTie.class || eadd.getClass() == ForwardTie.class))
                throw new NoSuchTypeException("[E] edge to be added not convinced with the Graph.");
            this.ans.addEdge(eadd, filein);
        }
        else if(typeName.equals(this.graphTypeSet[2]))
        {
            if(eadd.getClass().equals(SameMovieHyperEdge.class))
                return this.ans;
            if(eadd.getClass() != NetworkConnection.class)
                throw new NoSuchTypeException("[E] edge to be added not convinced with the Graph.");
            this.ans.addEdge(eadd, filein);
        }
        else if(typeName.equals(this.graphTypeSet[3]))
        {
            if(!(eadd.getClass() == MovieActorRelation.class || eadd.getClass() == MovieDirectorRelation.class || eadd.getClass() == SameMovieHyperEdge.class))
                throw new NoSuchTypeException("[E] edge to be added not convinced with the Graph.");
            this.ans.addEdge(eadd, filein);
        }
        else
        {
            throw new InnerClassException("Edge adder");
            //System.out.println("[E] Edge cannot be added: different type.");
        }
        return this.ans;
    }
}
