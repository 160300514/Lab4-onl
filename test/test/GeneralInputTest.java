package test;

import graph.Graph;
import helper.GeneralInputHelper;
import org.junit.Test;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GeneralInputTest
{
    GeneralInputHelper gih = new GeneralInputHelper();
    String []file = new String[]{"data/NetworkTopology.dat", "data/MovieGraph.dat", "data/WordGraph.dat", "data/SocialNetwork.dat"};
    @Test
    public void GTEST() throws Exception {
        Set<Graph> g = new HashSet<>();
        Graph gg = gih.fileReadConfig(file[0]);
        g.add(gg);
        System.out.println(gg.toString());
        gih.SaveChange(gg, "FirstIn");
        Scanner sc=  new Scanner(System.in);
        String addedge = "edge --add SSC NetworkConnection weighted=Y 15 directed=N Computer1 Router1";
        String addvertex = "vertex --add Computer2 Computer";
        gg = gih.listenCmdInput(addedge, System.in);
        //System.out.println(gg.toString());
        gg = gih.recallSpecGra("FirstIn");
        //System.out.println(gg.toString());
        //System.out.println(gg.getLabel());
    }
}
