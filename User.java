import java.util.*;
public class User {
    String Name;
    String emailID;
    String password;
    int mailNo=1;
    int sentmailNo=1;
    Stack<String> Inbox=new Stack<String>();
    Stack<String> Sent=new Stack<String>();
    Map<Integer,Boolean> isRead=new HashMap<Integer,Boolean>();
    Map<Integer,Integer> SentRecieved=new HashMap<Integer,Integer>();
    Map<Integer,String> inboxmp=new HashMap<Integer,String>();
    Map<Integer,String> sentmp=new HashMap<Integer,String>();
    Map<Integer,User> sendermp=new HashMap<Integer,User>();
    Map<Integer,Integer> RecievedSent=new HashMap<Integer,Integer>();


    User(String Name,String emailID,String password)
    {
        this.Name=Name;
        this.emailID=emailID;
        this.password=password;
    }
    void senduserMail(String emailID,String mailSubject,String mailContent,User u)
    {
        String mail=u.mailNo+"   "+this.emailID+"       "+u.emailID+"   "+mailSubject+"   "+mailContent;
        isRead.put(u.mailNo,false);
        u.inboxmp.put(u.mailNo,mail);
        u.sendermp.put(u.mailNo,this);
        u.mailNo++;
        u.Inbox.push(mail);
        mail=this.sentmailNo+"   "+this.emailID+"       "+u.emailID+"   "+mailSubject+"   "+mailContent; 
        this.sentmp.put(sentmailNo,mail);
        this.sentmailNo++;
        Sent.push(mail);
        this.SentRecieved.put(sentmailNo-1,u.mailNo-1);
        u.RecievedSent.put(u.mailNo-1,sentmailNo-1);
    }
    void sendgroupMail(String emailID,String mailSubject,String mailContent,Group g)
    {
       
        for(User u:g.groupMembers)
        {
            senduserMail(emailID, mailSubject, mailContent, u);
        }   
    }
    void viewInbox()
    {
        System.out.println("SNO.   FROM         TO         SUBJECT          CONTENT");
        String arr[]=new String[Inbox.size()];
        Inbox.copyInto(arr);
        for(int i=arr.length-1;i>=0;i--)
        {
            char ch[]=arr[i].toCharArray();
            int id=0,j=0;
            while(ch[j]!=' ')
            {
                id=id*10+Character.getNumericValue(ch[j]);
                j++;
            }
            sendermp.get(id).isRead.put(this.RecievedSent.get(id),true);
            System.out.println(arr[i]);
        }
    }
    void viewSent()
    {
        System.out.println("SNO.   FROM         TO         SUBJECT          CONTENT   SEEN Status");
        String arr[]=new String[Sent.size()];
        Sent.copyInto(arr);
        for(int i=arr.length-1;i>=0;i--)
        {
            char ch[]=arr[i].toCharArray();
            int id=0,j=0;
            while(ch[j]!=' ')
            {
                id=id*10+Character.getNumericValue(ch[j]);
                j++;
            }
            System.out.print(arr[i]);
            if(isRead.get(id))
                System.out.println("   Seen");
            else  
                System.out.println("   Not Seen");
        }
    }
    void delete(int mailNo)
    {
        Inbox.remove(inboxmp.get(mailNo));
    }
    void recall(String emailId,int mailNo,User to)
    {
        Sent.remove(sentmp.get(mailNo));
        to.delete(SentRecieved.get(mailNo));
    }
}
