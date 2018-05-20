package application;

import exception.MessageFieldIncorrectException;
import exception.NoSuchTypeException;
import exception.RepFieldException;
import graph.SocialNetwork;
import helper.GeneralInputHelper;
import helper.GraphMetrics;
import logge.logGeneratorStream;
import vertex.Person;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.time.Instant;
import java.util.Scanner;

public class SocialNetworkApp
{
    private SocialNetwork gp;
    private GeneralInputHelper generalInputHelper = new GeneralInputHelper();
    private logGeneratorStream logs;
    private Boolean flag = false;

    public void General(InputStream is) throws Exception {
        try{
            logs = new logGeneratorStream(SocialNetworkApp.class.getName());
        }
        catch(Exception e)
        {
            //logs.Appand(e.getMessage());
            System.out.println("[E] Fatal Error.");
        }
        //Calendar cl = new GregorianCalendar();
        System.out.println("SocialGraph App\nVersion=1.0.\nAuthor=marisuki\nUse \"SocialGraph --help\" to get details.");
        Scanner sc = new Scanner(is);
        System.out.println("Input: FilePath(Absolute path recommended)");
        String fPath = sc.nextLine();
        logs.Appand("[I] Input: file: "+fPath);
        File files = new File(fPath);
        if(files.exists())
        {
            try
            {
                gp = (SocialNetwork) generalInputHelper.fileReadConfig(fPath);
                //flag = true;
                if(gp!=null)
                    this.gp.checkRep();
                flag = true;
            }
            catch (MessageFieldIncorrectException | NoSuchTypeException mfe)
            {
                System.out.println("[I] File Read Failed.Choose another file, using file --in [FileName]");
                logs.Appand(mfe.getMessage());
                //logs.Appand();
                generalInputHelper.ClearCaches();
            }
            catch(RepFieldException e)
            {
                System.out.println("[I] File Read Failed.Choose another file, use file --in [FileName]");
                logs.Appand(e.getMessage());
                this.gp = null;
                generalInputHelper.ClearCaches();
            }
            catch( Exception e)
            {
                logs.Appand(e.getMessage());
            }
        }
        else
        {
            System.out.println("[E] File Path not exists.");
            logs.Appand("[E] File Path not exists.");
        }
        if(gp!=null) System.out.println(gp.toString());
        while(sc.hasNext())
        {
            boolean show = false;
            String str = sc.nextLine();
            logs.Appand("[I] Input CMD: "+str);
            if(str.contains("SocialGraph --help"))
                GraphPoetHelper();
            else if(str.contains("file --in"))
            {
                if(flag)
                {
                    logs.Appand("[E] file read succeed, cannot append another file.");
                    continue;
                }
                String filePath = "";
                try{
                    filePath = str.split(" ")[2];
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                    logs.Appand("[E]"+e.toString());
                }
                File file = new File(filePath);
                if(file.exists())
                {
                    try{
                        gp = (SocialNetwork) generalInputHelper.fileReadConfig(filePath);
                    }
                    catch (MessageFieldIncorrectException | NoSuchTypeException mfe)
                    {
                        System.out.println("[I] File Read Failed.Choose another file, use file --in [FileName]");
                        logs.Appand(mfe.getMessage());
                        generalInputHelper.ClearCaches();
                    }
                    catch (Exception e)
                    {
                        logs.Appand(e.getMessage());
                    }
                }
                else
                {
                    System.out.println("[E] File Path not exists.");
                    logs.Appand("[E] File Path not exists.");
                }
                try
                {
                    if(gp!=null)
                    {
                        this.gp.checkRep();
                        flag = true;
                        System.out.println("Graph Establish complete.\nUse \"SocialGraph --help\" to see operates supported.");
                    }
                }
                catch(RepFieldException e)
                {
                    System.out.println("[I] File Read Failed.Choose another file, use file --in [FileName]");
                    this.gp = null;
                    logs.Appand(e.getMessage());
                    generalInputHelper.ClearCaches();
                }
                if(gp!=null)
                    System.out.println(gp.toString());
            }
            else if(str.contains("modify"))
            {
                if(!flag)
                {
                    logs.Appand("[E] file input not completed.");
                    continue;
                }
                String[] a = str.split(" ");
                if(a.length!=4)
                    System.out.println("[E] Input Format Error, See --help for details.");
                if(a[1].equals("edge") || a[1].equals("Edge"))
                {
                    gp = (SocialNetwork) generalInputHelper.LabelModifier("edge", a[2], a[3]);
                }
                else if(a[1].equals("vertex")||a[1].equals("Vertex"))
                {
                    gp = (SocialNetwork) generalInputHelper.LabelModifier("vertex", a[2], a[3]);
                }
                System.out.println(gp.toString());
            }
            else if(str.contains("degreeCentrality"))
            {
                if(!flag)
                {
                    logs.Appand("[E] file input not completed.");
                    continue;
                }
                String []a = str.split(" ");
                System.out.println("DegreeCentrality Equals to:");
                if(a.length==1)
                    System.out.println(GraphMetrics.degreeCentrality(gp));
                else
                {
                    Person tmp = (Person) generalInputHelper.stov(a[1]);
                    if(tmp!=null)
                        System.out.println(GraphMetrics.degreeCentrality(gp, tmp));
                }
            }
            else if(str.contains("closenessCentrality"))
            {
                if(!flag)
                {
                    logs.Appand("[E] file input not completed.");
                    continue;
                }
                String []a = str.split(" ");
                assert a.length==2;
                Person tmp = (Person) generalInputHelper.stov(a[1]);
                if(tmp!=null)
                {
                    System.out.println("clossness Centrality: "+GraphMetrics.closenessCentrality(gp, tmp));
                }
            }
            else if(str.contains("betweennessCentrality"))
            {
                if(!flag)
                {
                    logs.Appand("[E] file input not completed.");
                    continue;
                }
                String[] a = str.split(" ");
                assert a.length==2;
                Person tmp = (Person) generalInputHelper.stov(a[1]);
                if(tmp!=null)
                {
                    System.out.println("betweeness Centrality:"+GraphMetrics.betweennessCentrality(gp, tmp));
                }
            }
            else if(str.contains("inDegreeCentrality"))
            {
                if(!flag)
                {
                    logs.Appand("[E] file input not completed.");
                    continue;
                }
                String[] a = str.split(" ");
                assert a.length==2;
                Person tmp = (Person) generalInputHelper.stov(a[1]);
                if(tmp!=null)
                {
                    System.out.println("indegree Centrality:"+GraphMetrics.inDegreeCentrality(gp, tmp));
                }
            }
            else if(str.contains("outDegreeCentrality"))
            {
                if(!flag)
                {
                    logs.Appand("[E] file input not completed.");
                    continue;
                }
                String[] a = str.split(" ");
                assert a.length==2;
                Person tmp = (Person) generalInputHelper.stov(a[1]);
                if(tmp!=null)
                {
                    System.out.println("outdegree Centrality:"+GraphMetrics.outDegreeCentrality(gp, tmp));
                }
            }
            else if(str.contains("distance"))
            {
                if(!flag)
                {
                    logs.Appand("[E] file input not completed.");
                    continue;
                }
                String[] a = str.split(" ");
                assert a.length==3;
                Person tmp1 = (Person) generalInputHelper.stov(a[1]);
                Person tmp2 = (Person) generalInputHelper.stov(a[2]);
                if(tmp1!=null&&tmp2!=null)
                {
                    System.out.println("distance:"+GraphMetrics.distance(gp, tmp1, tmp2));
                }
            }
            else if(str.contains("eccentricity"))
            {
                if(!flag)
                {
                    logs.Appand("[E] file input not completed.");
                    continue;
                }
                String[] a = str.split(" ");
                assert a.length==2;
                Person tmp = (Person) generalInputHelper.stov(a[1]);
                if(tmp!=null)
                {
                    System.out.println("eccentricity:"+GraphMetrics.eccentricity(gp, tmp));
                }
            }
            else if(str.contains("radius"))
            {
                if(!flag)
                {
                    logs.Appand("[E] file input not completed.");
                    continue;
                }
                String[] a = str.split(" ");
                assert a.length==1;
                System.out.println("radius:"+GraphMetrics.radius(gp));
            }
            else if(str.contains("diameter"))
            {
                if(!flag)
                {
                    logs.Appand("[E] file input not completed.");
                    continue;
                }
                String[] a = str.split(" ");
                assert a.length==1;
                System.out.println("diameter:"+GraphMetrics.diameter(gp));
            }
            else if(str.contains("save"))
            {
                if(!flag)
                {
                    logs.Appand("[E] file input not completed.");
                    continue;
                }
                String []a = str.split(" ");
                if(a[1].equals("-s"))
                {
                    generalInputHelper.SaveChange(gp, a[2]);
                    System.out.println("Save finished.");
                }
                else if(a[1].equals("-recall"))
                {
                    gp = (SocialNetwork) generalInputHelper.recallSpecGra(a[2]);
                }
            }
            else if(str.contains("log"))
            {
                String[] a = str.split(" ");
                String key = "";
                try{
                    key = a[2];
                }
                catch (Exception e)
                {
                    logs.Appand("[E]"+e.getMessage());
                    System.out.println("[E] log input not convinced.");
                }
                if(key.contains("date")||key.contains("time"))
                {
                    System.out.println("Input a fixed time scale or range or input: pass to search the logs for the whole time");
                    System.out.println("Input can be:YYYY-MM-DD HH:MM");
                    System.out.println("If the range is needed, please use the format: Time1,Time2");
                    String time = sc.nextLine();
                    String []t = time.split(", ");
                    try{
                        String s1 = t[0];String s2 = "";
                        if(t.length==1)
                        {
                            String []ss = s1.split(" ");
                            s1 = ss[0]+"T"+ss[1]+"Z";
                        }
                        else if(t.length==2)
                        {
                            s1 = t[0];
                            s2 = t[1];
                            String []ss = s1.split(" ");
                            s1 = ss[0]+"T"+ss[1]+"Z";
                            ss = s2.split(" ");
                            s2 = ss[0]+"T"+ss[1]+"Z";
                        }
                        Instant it1 = Instant.parse(s1);
                        Instant it2 = Instant.now();
                        if(t.length==2)
                        {
                            it2 = Instant.parse(s2);
                        }
                        //it1 = Instant.parse(t[0])
                        if(t.length==1)
                        {
                            System.out.println("The logs asked:");
                            for(String s:logs.resolve(it1))
                            {
                                System.out.println(s);
                            }
                        }
                        else if(t.length==2)
                        {
                            if(it1.isAfter(it2))
                            {
                                Instant it = it2;
                                it2 = it1;
                                it1 = it;
                            }
                            System.out.println("The logs asked:");
                            for(String s: logs.resolve(it1,it2))
                            {
                                System.out.println(s);
                            }
                        }
                        else
                        {
                            logs.Appand("[E] Time Input format Error.");
                        }
                    }
                    catch (Exception e)
                    {
                        logs.Appand("[E] "+e.getMessage());
                    }
                }
                else if(key.contains("information"))
                {
                    System.out.println("The logs asked:");
                    for(String s:logs.resolve("[I]"))
                        System.out.println(s);
                }
                else if(key.contains("error"))
                {
                    System.out.println("The logs asked:");
                    for(String s:logs.resolve("[E]"))
                        System.out.println(s);
                }
                else if(key.contains("warning"))
                {
                    System.out.println("The logs asked:");
                    for(String s:logs.resolve("[W]"))
                        System.out.println(s);
                }
                else
                {
                    System.out.println("The logs asked:");
                    for(String s:logs.resolve(key))
                        System.out.println(s);
                }
            }
            else if(str.contains("exit"))
            {
                is.close();
                sc.close();
                return;
            }
            else
            {
                if(!flag)
                {
                    logs.Appand("[E] file input not completed.");
                    continue;
                }
                try{
                    generalInputHelper.listenCmdInput(str, is);
                }
                catch (Exception e)
                {
                    logs.Appand(e.getMessage());
                }
                //logs.Appand("[I] Input CMD: "+str);
                if(gp!=null) System.out.println(gp.toString());
            }
        }
    }

    private void GraphPoetHelper()
    {
        StringWriter swt = new StringWriter();
        swt.write("SocialNetwork App\nVersion=1.0.\nAuthor=marisuki\n");
        swt.write("\"fileinput\"\t: file --in FilePath[StringType: Absolute Path Recommended]\n");
        swt.write("\"Using CMD as Graph Input: must as an adaptive method of file input\"\n\t\t: choice1: vertex --add label type\n");
        swt.write("\t\tchoice2: vertex --delete [regex]\n\t\tchoice3: edge --add label type [weighted] weight [directed] labelV1, labelV2\n");
        swt.write("choice4: edge --delete regex\n");
        swt.write("Usage to calculate Graph:\n");
        swt.write("choice1: \"degreeCentrality\" Or \"degreeCentrality label[Pattern of an Vertex]\"\n");
        swt.write("choice2: \"closenessCentrality label[Pattern of an Vertex]\nchoice3: \"betweennessCentrality label[Pattern of an Vertex]\"\n");
        swt.write("choice3: \"inDegreeCentrality label\" Or \"outDegreeCentrality label\"\n");
        swt.write("choice4: \"distance label1 label2\"\nchoice5: \"eccentricity label\"\n");
        swt.write("choice6: \"radius\"\nchoice7: \"diameter\"\n");
        swt.write("choice8: modify vertex/edge [preLabel] [modifiedLabel]\n");
        swt.write("choice9: exit: exit the program\n");
        swt.write("Log ask: log --get [key]: key choice: date/time, ClassName, information/error/warning, other keys. Time:UTC\n");
        swt.write("Memory-hold on: \"save -s Save_label\" to save temporary graph. Or \"save -recall Save_label\" to call back a history savage.\n");
        swt.write("While you input commands, please DO NOT add ->\"<-.\n");
        swt.write("CopyRight. 2018-5\n");
        System.out.println(swt.toString());
    }
}
