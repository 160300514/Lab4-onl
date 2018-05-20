package edge;

import exception.InnerClassException;
import exception.MessageFieldIncorrectException;
import exception.NoSuchTypeException;
import exception.RepFieldException;
import vertex.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
Builder pattern, outside: factory, edge->vertices.

Using the factory pattern,
separate the input (String[], typename, label), String[] is the input
(either cmd or file) separated by regex. Label, typename is gotten by
 the regex and can also be found in String[].

createEdgeOfCertainType: judge the input legal or not, and construct the edge.

 */
public class EdgeFactory
{
    private static final String []edgeTypeSet = {"WordNeighborhood", "FriendTie", "CommentTie", "ForwardTie", "NetworkConnection", "MovieActorRelation", "MovieDirectorRelation", "SameMovieHyperEdge"};

    public Edge createEdgeOfCertainType(String EdgeTypeName, String label, String[] res, Map<String , Vertex> stov) throws Exception {

        //System.out.println("EdgeTypename:  "+EdgeTypeName+" "+res[1]);
        Pattern p = Pattern.compile("\\w+");
        Matcher m = p.matcher(label);
        if(!m.matches())
            throw new MessageFieldIncorrectException("[E] Label not confirm.");
        if((!EdgeTypeName.equals("SameMovieHyperEdge")) && res.length < 5)
        {
            throw new RepFieldException("[E] The edge not correct."+label+" "+EdgeTypeName);
        }
        if((!EdgeTypeName.equals("SameMovieHyperEdge")) && (stov.get(res[3])==null || stov.get(res[4])==null))
        {
            throw new MessageFieldIncorrectException("[E] EdgeFactory: Vertex Point not added.");
            //System.out.println("[E] EdgeFactory: Vertex Point not added.\nHalted.");
        }

        if(res[1].equals(edgeTypeSet[0]))
        {
            if(!res[5].equals("Yes"))
                throw new MessageFieldIncorrectException("[E] EdgeFactory: Directed value not \"Yes\".");
            try{
                WordNeighborhood n = new WordNeighborhood(label, Double.parseDouble(res[2]), (Word)stov.get(res[3]), (Word)stov.get(res[4]));
                return n;
            }
            catch (Exception e)
            {
                throw new MessageFieldIncorrectException("[E] Edge value not Double.");
            }
        }
        else if(res[1].equals(edgeTypeSet[1]))
        {
            if(!res[5].equals("Yes"))
                throw new MessageFieldIncorrectException("[E] EdgeFactory: Directed value not \"Yes\".");
            if(res[3].equals(res[4]))
            {
                //throw new MessageFieldIncorrectException("[E] FriendTie: Same label: self loops!");
                System.out.println("[W] FriendTie: Same label: self loops!Continue.");
                return null;
            }
            FriendTie ft;
            try{
                 ft = new FriendTie(label, Double.parseDouble(res[2]), (Person)stov.get(res[3]), (Person)stov.get(res[4]));
            }
            catch (Exception e)
            {
                throw new MessageFieldIncorrectException("[E] Edge value not Double.");
            }
            if(Double.parseDouble(res[2])>=1.0+1e-5 || Double.parseDouble(res[2])<=0.0-1e-6)
                throw new MessageFieldIncorrectException("[E] the edge on Social Graph should be within (0,1].From CommentTie.");
            return ft;
        }
        else if(res[1].equals(edgeTypeSet[2]))
        {
            if(res[3].equals(res[4]))
            {
                System.out.println("[W] CommentTie: Same label: self loops!Continue.");
                return null;
            }
            //int cnt = 0;
            //for(String s:res) System.out.println(s+String.valueOf(cnt++));
            if(!res[5].equals("Yes"))
                throw new MessageFieldIncorrectException("[E] EdgeFactory: Directed value not \"Yes\".");
            CommentTie ct;
            try{
                ct = new CommentTie(label, Double.parseDouble(res[2]), (Person)stov.get(res[3]), (Person)stov.get(res[4]));
            }
            catch (Exception e)
            {
                throw new MessageFieldIncorrectException("[E] Edge value not Double.");
            }
            if(Double.parseDouble(res[2])>=1.0+1e-5 || Double.parseDouble(res[2])<=0.0-1e-6)
                throw new MessageFieldIncorrectException("[E] the edge on Social Graph should be within (0,1].From CommentTie.");
            return ct;
        }
        else if(res[1].equals(edgeTypeSet[3]))
        {
            if(res[3].equals(res[4]))
            {
                //throw new MessageFieldIncorrectException("[E] FriendTie: Same label: self loops!");
                System.out.println("[W] ForwardTie: Same label: self loops!Continue.");
                return null;
            }
            if(!res[5].equals("Yes"))
                throw new MessageFieldIncorrectException("[E] EdgeFactory: Directed value not \"Yes\".");
            ForwardTie ft;
            try{
                ft = new ForwardTie(label, Double.parseDouble(res[2]), (Person)stov.get(res[3]), (Person)stov.get(res[4]));

            }
            catch (Exception e)
            {
                throw new MessageFieldIncorrectException("[E] Edge value not Double.");
            }
            if(Double.parseDouble(res[2])>=1.0+1e-5 || Double.parseDouble(res[2])<=0.0-1e-6)
                throw new MessageFieldIncorrectException("[E] the edge on Social Graph should be within (0,1].From CommentTie.");
            return ft;
        }
        else if(res[1].equals(edgeTypeSet[4]))
        {
            if(res[3].equals(res[4]))
            {
                //throw new MessageFieldIncorrectException("[E] NetworkConnection: Self loop is not allowed in network connection.");
                System.out.println("[W] NetworkConnection: Self loop is not allowed in network connection.Continue.");
                return null;
            }
            if(stov.get(res[3]).getClass()==stov.get(res[4]).getClass())
            {
                if(stov.get(res[3]).getClass().equals(Computer.class)||stov.get(res[3]).getClass().equals(Server.class))
                {
                    //throw new MessageFieldIncorrectException("[E] NetWorkConnection: Error on Server-Server Or Computer-Computer connection: Reason: not allowed.");
                    System.out.println("[W] NetWorkConnection: Error on Server-Server Or Computer-Computer connection: Reason: not allowed.Continue.");
                    return null;
                }
            }
            //if(!res[5].equals("No"))
            //    throw new MessageFieldIncorrectException("[E] EdgeFactory: Directed value not \"No\".");
            try{
                NetworkConnection nc = new NetworkConnection(label, Double.parseDouble(res[2]), (NetworkPather) stov.get(res[3]), (NetworkPather) stov.get(res[4]));
                return nc;
            }
            catch (Exception e)
            {
                throw new MessageFieldIncorrectException("[E] Edge value not Double.");
            }
        }
        else if(res[1].equals(edgeTypeSet[5]))
        {
            if(res[3].equals(res[4]))
            {
                //throw new MessageFieldIncorrectException("[E] Movie Actor Connection cannot allow any self loops.");
                System.out.println("[W] Movie Actor Connection cannot allow any self loops.Continue.");
                return null;
            }
            //if(!res[5].equals("No"))
            //    throw new MessageFieldIncorrectException("[E] EdgeFactory: Directed value not \"Yes\".");
            try{
                MovieActorRelation ma = new MovieActorRelation(label, Double.parseDouble(res[2]), (Movie)stov.get(res[3]), (Actor)stov.get(res[4]));
                return ma;
            }
            catch (Exception e)
            {
                throw new MessageFieldIncorrectException("[E] Edge value not Double.");
            }
        }
        else if(res[1].equals(edgeTypeSet[6]))
        {
            if(res[3].equals(res[4]))
            {
                //throw new MessageFieldIncorrectException("[E] Movie Director Connection: cannot allow any self loops.");

                System.out.println("[W] Movie Director Connection: cannot allow any self loops.Continue.");
                return null;
            }
            //if(!res[5].equals("No"))
            //    throw new MessageFieldIncorrectException("[E] EdgeFactory: Directed value not \"Yes\".");
            try{
                return new MovieDirectorRelation(label, (Movie)stov.get(res[3]), (Director)stov.get(res[4]));
            }
            catch (Exception e)
            {
                throw new MessageFieldIncorrectException("[E] Edge value not Double.");
            }
        }
        else if(res[1].equals(edgeTypeSet[7]))
        {
            if(res.length<=2)
                throw new MessageFieldIncorrectException("[E] HyperEdge cannot be built with one point.");
            Set<Actor> ver = new HashSet<>();
            for(int i=2;i<res.length;i++)
            {
                Actor mc = (Actor)stov.get(res[i]);
                if(ver.contains(mc))
                {
                    throw new MessageFieldIncorrectException("[E] HyperEdge: cannot allow a self loop.");
                    //System.out.println("[E] HyperEdge: cannot allow a self loop.");
                    //return null;
                }
                ver.add(mc);
            }
            return new SameMovieHyperEdge(label, ver);
        }
        else
        {
            throw new NoSuchTypeException("[E] In EdgeFactory: edge type not included.");
        }
        //throw new Exception("the edge type name not included.");
    }

    public Edge createEdgeFromPreviousEdge(Edge pre, String newLabel) throws Exception {
        if(pre.getClass().equals(CommentTie.class))
        {
            CommentTie ct = (CommentTie) pre;
            return new CommentTie(newLabel, ct.getWeight(), ct.getSrc(), ct.getTar());
        }
        else if(pre.getClass().equals(ForwardTie.class))
        {
            ForwardTie ft = (ForwardTie) pre;
            return new ForwardTie(newLabel, ft.getWeight(), ft.getSrc(), ft.getTar());
        }
        else if(pre.getClass().equals(FriendTie.class))
        {
            FriendTie fft = (FriendTie) pre;
            return new FriendTie(newLabel, fft.getWeight(), fft.getSrc(), fft.getTar());
        }
        else if(pre.getClass().equals(MovieActorRelation.class))
        {
            MovieActorRelation mar = (MovieActorRelation) pre;
            return new MovieActorRelation(newLabel, mar.getWeight(), mar.getMovie(), mar.getAct());
        }
        else if(pre.getClass().equals(MovieDirectorRelation.class))
        {
            MovieDirectorRelation md = (MovieDirectorRelation) pre;
            return new MovieDirectorRelation(newLabel, md.getMovie(), md.getDirector());
        }
        else if(pre.getClass().equals(NetworkConnection.class))
        {
            NetworkConnection nc = (NetworkConnection) pre;
            return new NetworkConnection(newLabel, nc.getWeight(), (NetworkPather) nc.sourceVertices().toArray()[0], (NetworkPather) nc.sourceVertices().toArray()[1]);
        }
        else if(pre.getClass().equals(SameMovieHyperEdge.class))
        {
            SameMovieHyperEdge sm = (SameMovieHyperEdge) pre;
            return new SameMovieHyperEdge(newLabel, sm.getVerticespassiing());
        }
        else if(pre.getClass().equals(WordNeighborhood.class))
        {
            WordNeighborhood wn = (WordNeighborhood) pre;
            return new WordNeighborhood(newLabel, wn.getWeight(), wn.getSource(), wn.getTarget());
        }
        else
        {
            throw new InnerClassException("EdgeFactory");
            //System.out.println("[E] Inner Error, please connect Administrator, error on EdgeFactory.");
            //return null;
        }
    }
}
