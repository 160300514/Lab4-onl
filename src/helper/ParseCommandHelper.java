package helper;

import graph.Graph;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Parse the input string to the class:
parse input helper, and judge by the
following classes. The test case is
recommend not contains \”.
 */
public class ParseCommandHelper
{
    private static final String[] possibleinputhead={"vertex", "edge", "hyperedge"};
    private static final String[] possiblecm = {"--add", "--delete"};
    private static final String []vertexTypeSet = {"Word", "Person", "Computer", "Server", "Router", "Movie", "Actor", "Director"};
    private static final String []edgeTypeSet = {"WordNeighborhood", "FriendTie", "CommentTie", "ForwardTie", "NetworkConnection", "MovieActorRelation", "MovieDirectorRelation", "SameMovieHyperEdge"};
    private String label;
    private String type;
    private String regex;
    private Graph  ans;
    private String v1;
    private String v2;
    private Double weight;
    private boolean weighted;
    private boolean directed;

    public String ParseCommandHelperInput()
    {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public Graph parseAndExecuteCommand(ParserInputHelper existGraphBuilder, String headline, InputStream is) throws Exception {
        String []cmdhead = headline.split(" ");
        ans = existGraphBuilder.getAns();

        if(headline.contains("Quit"))
            return this.ans;

        if(cmdhead[0].equals(possibleinputhead[0]))
        {
            if(cmdhead[1].equals(possiblecm[0]))//vertex --all label type
            {
                if(cmdhead.length!=4)
                {
                    System.out.println("[E] Vertex add type: vertex --add <label> <type>.\nInput format not convinced.");
                    return this.ans;
                }
                this.label = cmdhead[2];
                this.type  = cmdhead[3];
                this.regex = null;
                if(!existGraphBuilder.judgeExist(this.label))
                    ans = cmdIOConstructorVertex_Adder(existGraphBuilder, is);
                else
                    System.out.println("[E] Exist Label.\nHalted");
            }
            else if(cmdhead[1].equals(possiblecm[1]))
            {
                if(cmdhead.length!=3)
                {
                    System.out.println("[E] Vertex delete type: vertex --delete <label> .\nInput format not convinced.");
                    return this.ans;
                }
                this.regex = cmdhead[2];
                ans = existGraphBuilder.cmdVertexDeleter(this.regex);
            }
        }
        else if(cmdhead[0].equals(possibleinputhead[1]))
        {
            //System.out.println(headline);
            if(cmdhead[1].equals(possiblecm[0]))//edge --add label type [weighted=Y/N] [weight] [directed=Y/N] v1, v2
            {
                if(cmdhead.length <= 7)
                {
                    System.out.println("[I;E] Please specify the information about: edge: weighted, weight, directed.");
                    System.out.println("[I;E] input format not supported.\nHalted.\nInput Formats: edge --add label type weighted=N directed=Y/N v1, v2 Or edge --add label type weighted=Y weight directed=Y/N v1, v2");
                    return this.ans;
                }
                this.label = cmdhead[2];
                this.type  = cmdhead[3];
                this.v1 = cmdhead[cmdhead.length-2];
                this.v2 = cmdhead[cmdhead.length-1];
                //System.out.println(v1+v2);
                if(cmdhead.length <= 7)
                {
                    System.out.println("[I;E] Please specify the information about: edge: weighted, weight, directed.");
                    System.out.println("[I;E] input format not supported.\nHalted.\nInput Formats: edge --add label type weighted=N directed=Y/N v1, v2 Or edge --add label type weighted=Y weight directed=Y/N v1, v2");
                }
                else
                {
                    Pattern p = Pattern.compile("weighted=\\s*[\\w]+|directed=\\s*[\\w]+|\\s+[\\d+\\.*\\d*]+\\s+");
                    Matcher m = p.matcher(headline);
                    Pattern num = Pattern.compile("[0-9]+");
                    boolean able = true;
                    while(m.find())
                    {
                        String pat = headline.substring(m.start(), m.end());
                        //System.out.println(pat);
                        String[] kv = pat.split("[ |=]");
                        if (pat.contains("weighted"))
                        {
                            weighted = kv[1].contains("Y");
                            if(!weighted) weight = -1.0;
                        } else if (pat.contains("directed"))
                        {
                            directed = kv[1].contains("Y");
                        } else if (num.matcher(pat.split("[\\s]")[1]).find())
                        {
                            if (weighted)
                            {
                                weight = Double.parseDouble(kv[1]);
                                //System.out.println(weight);
                            }
                            else {
                                able = false;
                                System.out.println("[E] Non-Weighted edge with weight.\nHalted.");
                                //throw new RepFieldException("[E] Non-Weighted edge with weight.\nHalted.");
                            }
                        }
                    }
                    if(able)
                    {
                        //System.out.println("INNN");
                        ans = cmdIOConstructorEdge_Adder(existGraphBuilder, cmdhead);
                        //for(String s:cmdhead) System.out.println(s);
                    }
                }
            }
            else if(cmdhead[1].equals(possiblecm[1]))
            {
                ans = existGraphBuilder.cmdEdgeDeleter(cmdhead[2]);
            }
        }
        else if(cmdhead[0].equals(possibleinputhead[2]))
        {
            this.label = cmdhead[2];
            this.type  = cmdhead[3];
            if(cmdhead[1].equals(possiblecm[0]))
            {
                ans = cmdIOConstructorEdge_Adder(existGraphBuilder, cmdhead);//4+
            }
            else if(cmdhead[1].equals(possiblecm[1]))
            {
                System.out.println("[E] Operation not supported: hyper-edge deleter.\nHalted.");
            }
        }
        return this.ans;
    }


    private Graph cmdIOConstructorEdge_Adder(ParserInputHelper pih, String[] cmdIn) throws Exception//edge --add label type [weighted=Y/N] [weight] [directed=Y/N] v1, v2
    {
        //test legal:edgeTypeSet = {"WordNeighborhood", "FriendTie",
        // "CommentTie", "ForwardTie", "NetworkConnection", "MovieActorRelation",
        // "MovieDirectorRelation", "SameMovieHyperEdge"};
        //System.out.println(v1+v2);
        if(!(pih.judgeExist(v1) && pih.judgeExist(v2)))
        {
            System.out.println("[E] Vertex not Exist.");
            return this.ans;
        }
        if(!this.type.equals(edgeTypeSet[7]))
        {
            pih.cmdEdgeAdder(type, label, weight, new String[]{label, type, String.valueOf(weight), v1, v2, (directed ? "Yes":"No")});
        }
        else if(this.type.equals(edgeTypeSet[7]))
        {
            Set<String> tmp = new HashSet<>();
            tmp.add(label);tmp.add(type);
            for(int i=4;i<cmdIn.length;i++) tmp.add(cmdIn[i]);
            String[] res = (String[]) tmp.toArray();
            pih.cmdEdgeAdder(type, label, 0, res);
        }
        else
        {
            System.out.println("[E] Illegal Edge type"+this.type+"\n.Halted.");
        }
        return this.ans;
    }

    private Graph cmdIOConstructorVertex_Adder(ParserInputHelper pih, InputStream is) throws Exception {
        Scanner sc = new Scanner(is);
        if(type.equals(vertexTypeSet[0]))
        {
            System.out.println("Word construct finished.");
            return pih.cmdVertexAdder(label, type, "Vertex", new String[]{label, type});
        }
        else if(type.equals(vertexTypeSet[1]))
        {
            System.out.println("Person Vertex needs two other params: Gender/Age: Input format: Gender/Age or Gender,age or in each line: ");
            String line = sc.nextLine();
            String[] split = line.split("[(\\s|/,)]");
            if(split.length==1)
            {
                String l = sc.nextLine();
                try{
                    Integer.parseInt(l);
                }
                catch(Exception e)
                {
                    System.out.println("Illegal Input.\nTerminated.");
                    return this.ans;
                }
            }
            else
            {
                try{
                    Integer.parseInt(split[1]);
                }
                catch(Exception e)
                {
                    System.out.println("Illegal Input.\nTerminated.");
                    return this.ans;
                }
            }
            return pih.cmdVertexAdder( label, type,"Vertex", new String[]{label, type, split[0], split[1]});
        }
        else if(type.equals(vertexTypeSet[2]))
        {
            System.out.println("Computer Vertex needs another parameter: IP address. Input Format: IP=A.B.C.D");
            String line = sc.nextLine();
            String[] ip = line.split("[.]+");
            return pih.cmdVertexAdder(label, type, "Vertex", new String[]{label, type, ip[0], ip[1], ip[2], ip[3]});
        }
        else if(type.equals(vertexTypeSet[3]))
        {
            System.out.println("Server Vertex needs another parameter: IP address. Input Format: IP=A.B.C.D");
            String line = sc.nextLine();
            String[] ip = line.split("[.]+");
            return pih.cmdVertexAdder(label, type, "Vertex", new String[]{label, type, ip[0], ip[1], ip[2], ip[3]});
        }
        else if(type.equals(vertexTypeSet[4]))
        {
            System.out.println("Router Vertex needs another parameter: IP address. Input Format: IP=A.B.C.D");
            String line = sc.nextLine();
            String[] ip = line.split("[.]+");
            return pih.cmdVertexAdder(label, type, "Vertex", new String[]{label, type, ip[0], ip[1], ip[2], ip[3]});
        }
        else if(type.equals(vertexTypeSet[5]))//movie
        {
            System.out.println("Movie Vertex needs three other parameters: Year/Country/IMDb Score : Input Format: Year/Country/IMDb Score or three lines.");
            String line = sc.nextLine();
            String[] sp = line.split("[/|\\s]+");
            int cnt = sp.length;
            String []res = new String[5];
            res[0] = label;
            res[1] = type;
            System.arraycopy(sp, 0, res, 2, sp.length);
            while(cnt<3)
            {
                String nl = sc.nextLine();
                String []spp = nl.split("[/|\\s]+");
                for(int i=0;i<spp.length;i++)res[cnt+i+2]= spp[i];
                cnt+=spp.length;
            }
            return pih.cmdVertexAdder(label, type, "Vertex", res);
        }
        else if(type.equals(vertexTypeSet[6]))//actor
        {
            System.out.println("Actor Vertex needs two other parameters: Age/Gender:(M/F) : Input Format: Age/Gender.");
            String line = sc.nextLine();
            String[] sp = line.split("[\\s|/]");
            if(sp.length!=2){
                System.out.println("Input Not Formatted.\nTermined");
                return this.ans;
            }
            return pih.cmdVertexAdder(label, type,"Vertex", new String[]{label, type, sp[0], sp[1]});
        }
        else if(type.equals(vertexTypeSet[7]))
        {
            System.out.println("Director Vertex needs two other parameters: Age/Gender:(M/F) : Input Format: Age/Gender.");
            String line = sc.nextLine();
            String[] sp = line.split("[\\s|/]");
            if(sp.length!=2){
                System.out.println("Input Not Formatted.\nTermined");
                return this.ans;
            }
            return pih.cmdVertexAdder(label, type,"Vertex", new String[]{label, type, sp[0], sp[1]});
        }
        System.out.println("No such type of vertex.\nHalted");
        return this.ans;
    }
}
