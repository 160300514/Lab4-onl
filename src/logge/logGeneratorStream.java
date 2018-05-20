package logge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;
import java.util.regex.Pattern;

public class logGeneratorStream
{
    private XMLFormatter fm = new XMLFormatter();
    private Logger log;
    private FileHandler fh;// = new FileHandler("log/");
    private String file;

    public logGeneratorStream(String className) throws IOException {
        log = Logger.getLogger(className);
        log.setLevel(Level.INFO);
        file = "log/"+className+".log";
        //File f = new File("log/"+className+".log");
        fh = new FileHandler("log/"+className+".log");
        log.addHandler(fh);
    }
    public void Appand(String Information)
    {
        if(Information.contains("[E]"))
        {
            log.severe(Information);
        }
        else if(Information.contains("[W]"))
        {
            log.warning(Information);
        }
        else
        {
            log.info(Information);
        }
    }

    public Set<String> resolve(Instant st, Instant end) throws FileNotFoundException
    {
        Set<String> ans = new HashSet<>();
        Set<Messages> mess = xmlDecoder();
        for(Messages m:mess)
        {
            if(m.getDate().isAfter(st) && m.getDate().isBefore(end))
                ans.add(m.getMessages());
        }
        return ans;
    }

    public Set<String> resolve(Instant st) throws FileNotFoundException//>=st
    {
        Set<Messages> mess = xmlDecoder();
        Set<String> ans = new HashSet<>();
        for(Messages m: mess)
        {
            if(m.getDate().isAfter(st))
                ans.add(m.getMessages());
        }
        return ans;
    }

    public Set<String> resolve(String regex) throws FileNotFoundException {
        Set<Messages> mess = xmlDecoder();
        Set<String> ans = new HashSet<>();
        for(Messages m: mess)
        {
            if(m.getMessages().contains(regex))
                ans.add(m.getMessages());
        }
        return ans;
    }

    public Set<Messages> xmlDecoder() throws FileNotFoundException {
        File f= new File(file);
        Scanner sc=  new Scanner(f);
        Set<Messages> ans = new HashSet<>();
        while(sc.hasNext())
        {
            String str = sc.nextLine();
            Pattern p0 = Pattern.compile("<record>");
            if(p0.matcher(str).matches())
            {
                Messages tmp = new Messages();
                while(!str.equals("</record>"))
                {
                    str = sc.nextLine();
                    if(str.contains("<date>"))
                    {
                        String[] s = str.split("<date>|</date>");
                        tmp.setDate(s[1]);
                    }
                    if(str.contains("<message>"))
                    {
                        String []a = str.split("<message>|</message>");
                        tmp.setMessages(a[1]);
                    }
                }
                ans.add(tmp);
            }
        }
        return ans;
    }
}

class Messages
{
    ////Date date;
    Instant it;
    String messages;
    public Messages(){}
    public Messages(String dates, String mess)
    {
        it = Instant.parse(dates);
        //messages = mess;
    }

    public Instant getDate() {
        return it;
    }

    public String getMessages() {
        return messages;
    }

    public void setDate(String date) {
        this.it = Instant.parse(date);
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
