
package test;

import edge.CommentTie;
import edge.SameMovieHyperEdge;
import exception.RepFieldException;
import graph.Graph;
import helper.ParserInputHelper;
import org.junit.Test;
import vertex.Actor;
import vertex.Movie;
import vertex.Person;
import vertex.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class parserInputTest
{
/*
    @Test
    public void parserTest() throws Exception
    {
        ParserInputHelper pih = new ParserInputHelper();
        Graph g  = pih.establishGraph("data/NetworkTopologyTest.dat");
        System.out.println(g.toString());
        ParserInputHelper pii = new ParserInputHelper();
        Graph gg  = pii.establishGraph("data/MovieGraph.dat");
        System.out.println(gg.toString());
        ParserInputHelper piw = new ParserInputHelper();
        Graph ggg  = piw.establishGraph("data/WordGraph.dat");
        System.out.println(ggg.toString());
        ParserInputHelper pis = new ParserInputHelper();
        Graph gggg  = pis.establishGraph("data/SocialNetwork.dat");
        System.out.println(gggg.toString());
    }
*/
    @Test
    public void TestClone() throws CloneNotSupportedException, RepFieldException {
        String input = "Vertex = <TomHanks, Actor, <62, M>>";
        Actor act = new Actor("nlp", 20, "F");
        Actor cloner = (Actor) act.clone();
        CommentTie ct = new CommentTie("ppl", 0.9, new Person("ads", "F", 21), new Person("adss", "F", 21));
        CommentTie ctcl  = (CommentTie) ct.clone();
        ctcl.vertices().add(new Person("adsasf", "M", 20));
        for(Vertex p: ctcl.vertices())
        {
            p = new Person("sadsadsa", "F", 12);
            break;
        }
        //Set<Movie> as = new HashSet<>();
        //as.add(new Movie("21", 2011, "dsa", 9.2));
        //SameMovieHyperEdge sa = new SameMovieHyperEdge("sassd", as);
        //SameMovieHyperEdge ssa = (SameMovieHyperEdge) sa.clone();
        List<Vertex> s = new ArrayList<>();
        s.add(new Movie("215", 2010, "da", 9.4));
        //ssa.addVertices(s);
        System.out.println(ctcl.vertices().size());
        System.out.println(cloner.toString());
        System.out.println(ctcl.toString());
        //System.out.println(ssa.toString());
    }
}