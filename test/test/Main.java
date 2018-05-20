package test;

import application.GraphPoetApp;
import application.MovieGraphApp;
import application.SocialNetworkApp;

public class Main
{
      public static void main(String[] argv) throws Exception  {
          //GraphPoetApp gpa = new GraphPoetApp();
          //gpa.General(System.in);
          //MovieGraphApp mga = new MovieGraphApp();
          //mga.General(System.in);
          SocialNetworkApp snp = new SocialNetworkApp();
          snp.General(System.in);
    }
}
