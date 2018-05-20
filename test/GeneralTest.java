import application.GraphPoetApp;
import application.MovieGraphApp;
import application.NetworkTopologyApp;
import application.SocialNetworkApp;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringBufferInputStream;

public class GeneralTest
{

    @Test
    public synchronized void Tester() throws Exception
    {
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream("test/InlineCMD_SN.dat"));
        SocialNetworkApp mga = new SocialNetworkApp();
        mga.General(fis);
        fis.close();
        BufferedInputStream fis1 = new BufferedInputStream(new FileInputStream("test/InlineCMD_GP.dat"));
        GraphPoetApp gpa = new GraphPoetApp();
        gpa.General(fis1);
        fis1.close();
        BufferedInputStream fis2 = new BufferedInputStream(new FileInputStream("test/InlineCMD_NN.dat"));
        NetworkTopologyApp na = new NetworkTopologyApp();
        na.General(fis2);
        fis2.close();
        BufferedInputStream bfs = new BufferedInputStream(new FileInputStream("test/InlineCMD_MG.dat"));
        MovieGraphApp mg = new MovieGraphApp();
        mg.General(bfs);
        bfs.close();
    }
}
