import java.util.*;
import java.io.*;
public class Main {

    static public boolean isusernameExists(List<User>users,String username)
    {
        for(User u:users)
        {
            if(u.Name.equals(username))
                return true;
        }
        return false;
    }
    static public boolean isGroupusernameExists(List<Group>groups,String username)
    {
        for(Group g:groups)
        {
            if(g.Name.equals(username))
                return true;
        }
        return false;
    }
    static public boolean isvalidUserEmail(List<User>users,String email,List<Group>groups)
    {
        for(User u:users)
        {
            if(u.emailID.equals(email))
                return false;
        }
        for(Group g:groups)
        {
            if(g.emailID.equals(email))
                return false;
        }
        int c1=0,c2=0;
        char at='@',dot='.';
        char[] ch=email.toCharArray();
        for(int i=0;i<email.length();i++)
        {
            if(ch[i]==at)
                c1++;
            if(ch[i]==dot)
                c2++;
        }
        if(c1==1&&c2==1)
            return true;

        return false;
    }
    static public boolean isvalidGroupEmail(List<Group>groups,String email,List<User>users)
    {
        for(Group g:groups)
        {
            if(g.emailID.equals(email))
                return false;
        }
        for(User u:users)
        {
            if(u.emailID.equals(email))
                return false;
        }
        int c1=0,c2=0;
        char at='@',dot='.';
        char[] ch=email.toCharArray();
        for(int i=0;i<email.length();i++)
        {
            if(ch[i]==at)
                c1++;
            if(ch[i]==dot)
                c2++;
        }
        if(c1==1&&c2==1)
            return true;

        return false;
    }
    public static void AddorRemoveMember(String groupName,String userName,List<Group>groups,List<User>users,String memberRequest)
    {
        User u=null;
        Group g=null;
        for(User us:users)
            if(us.Name.equals(userName))
                u=us;
        for(Group gr:groups)
            if(gr.Name.equals(groupName))
                g=gr;
        if(u==null)
            System.out.println("User does not exist");
        else if(g==null)
            System.out.println("Group does not exist");
        else if(memberRequest.equals("Add"))
        {
            g.groupMembers.add(u);
            System.out.println("User added to the group");
        }
        else 
        {
            if(g.groupMembers.contains(u))
            {
                g.groupMembers.remove(u);
                System.out.println("User removed from the group");
            }
            else 
              System.out.println("User is not a group member");
        }
    }
    public static void sendMail(String userName,String emailID,String mailSubject,String mailContent,List<Group>groups,List<User>users)
    {
        User u=null;
        for(User us:users)
            if(us.Name.equals(userName))
                u=us;
        User touser=null;
        Group togroup=null;
        for(User us:users)
            if(us.emailID.equals(emailID))
                touser=us;
        for(Group g:groups)
            {
                if(g.emailID.equals(emailID))
                  togroup=g;
            }
        if(u==null)
            System.out.println("Invalid from username");
        else if(touser==null&&togroup==null)
            System.out.println("Invalid to mailID");
        else if(togroup==null)
        {
            u.senduserMail(emailID, mailSubject, mailContent, touser);
            System.out.println("Mail sent successfully");
        }
        else 
        {
            u.sendgroupMail(emailID, mailSubject, mailContent, togroup);
            System.out.println("Mail sent successfully");
        }

    }
    public static void viewInbox(String userName,List<User>users)
    {
        User u=null;
        for(User us:users)
            if(us.Name.equals(userName))
                u=us;
        if(u==null)
            System.out.println("Invalid username");
        else
            u.viewInbox();
        
    }
    
    public static void viewSent(String userName,List<User>users)
    {
        User u=null;
        for(User us:users)
            if(us.Name.equals(userName))
                u=us;
        if(u==null)
            System.out.println("Invalid username");
        else
            u.viewSent();
        
    }
    public static void delete(String userName,int mailNo,List<User>users)
    {
        User u=null;
        for(User us:users)
            if(us.Name.equals(userName))
                u=us;
        if(u==null)
            System.out.println("Invalid username");
        else
        {
            u.delete(mailNo);
            System.out.println("Deleted Successfully");
        }

    }
    public static void recall(String userName,String emailId,int mailNo,List<User>users)
    {
        User u=null,to=null;
        for(User us:users)
        {
            if(us.Name.equals(userName))
                u=us;
            if(us.emailID.equals(emailId))
                to=us;
        }
        if(u==null)
            System.out.println("Invalid username");
        else if(to==null)
            System.out.println("Invalid to mailid");
        else 
        {
            u.recall(emailId,mailNo,to);
            System.out.println("Recalled Successfully");
        }
    }
    public static String getInput(String prompt){
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
  
        System.out.print(prompt);
        System.out.flush();
  
        try{
            return stdin.readLine();
        } catch (Exception e){
          return "Error: " + e.getMessage();
        }
      }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
         List<User> users=new ArrayList<User>();
         List<Group> groups=new ArrayList<Group>();
        boolean loop=true;
        while(loop)
        {
            System.out.println("1) Create User");
            System.out.println("2) Create Group");
            System.out.println("3) Group Assignment");
            System.out.println("4) Compose Mail");
            System.out.println("5) View Inbox");
            System.out.println("6) View Sent Mails");
            System.out.println("7) Delete Mail");
            System.out.println("8) Recall");
            System.out.println("9) Exit");
            System.out.println("Select any option");
            int ch=sc.nextInt();
            int mailno;
            String userName,emailID,password,groupName,memberRequest,mailSubject,mailContent="";
            switch(ch)
            {
                
                case 1:
                    System.out.println("Enter User Name:");
                     userName=sc.next();
                    System.out.println("Enter Email ID:");
                     emailID=sc.next();
                    System.out.println("Enter Password:");
                     password=sc.next();
                    if(isusernameExists(users, userName))
                    {
                        System.out.println("Username already exists");
                    }
                    else if(!isvalidUserEmail(users, emailID,groups))
                    {
                        System.out.println("Invalid Email or Email already exists");
                    }
                    else 
                    {
                        User u=new User(userName,emailID,password);
                        users.add(u);
                        System.out.println("User Created"); 
                    }  
                    break;
                case 2:
                    System.out.println("Enter Group Name:");
                     groupName=sc.next();
                    System.out.println("Enter Group Mail ID:");
                     emailID=sc.next();
                    System.out.println("Enter Group Mail Password:");
                     password=sc.next();
                    System.out.println("Enter Group Description");
                    String description=sc.next();
                    if(isGroupusernameExists(groups, groupName))
                    {
                        System.out.println("groupName already exists");
                    }
                    else if(!isvalidGroupEmail(groups, emailID,users))
                    {
                        System.out.println("Invalid Email or Email already exists");
                    }
                    else 
                    {
                        Group g=new Group(groupName,emailID,password,description);
                        groups.add(g);
                        System.out.println("Group Created"); 
                    }  
                    break;
                case 3:
                    System.out.println("Enter Group Name:");
                    groupName=sc.next();
                    System.out.println("Enter User Name:");
                    userName=sc.next();
                    System.out.println("Add / Remove");
                    memberRequest=sc.next();
                    if(memberRequest.equals("Add")||memberRequest.equals("Remove"))
                        AddorRemoveMember(groupName,userName,groups,users,memberRequest);
                    else 
                        System.out.println("Invalid Request");
                    break;
                case 4:
                    System.out.println("Enter From user:");
                    userName=sc.next();
                    System.out.println("Enter To address:");
                    emailID=sc.next();
                    System.out.println("Enter subject:");
                    mailSubject=sc.next();
                    mailContent = getInput("Enter content: ");    
                    sendMail(userName,emailID,mailSubject,mailContent,groups,users);
                    break;
                case 5:
                    System.out.print("Enter user name:");
                    userName=sc.next();
                    viewInbox(userName,users);
                    break;
                case 6:
                    System.out.print("Enter user name:");
                    userName=sc.next();
                    viewSent(userName,users);
                    break;
                case 7:
                    System.out.println("Enter user name:");
                    userName=sc.next();
                    System.out.println("Enter inboxmailno:");
                    mailno=sc.nextInt();
                    delete(userName,mailno,users);
                    break;
                case 8:
                    System.out.println("Enter from user name:");
                    userName=sc.next();
                    System.out.println("Enter To address:");
                    emailID=sc.next();
                    System.out.println("Enter sentmailno:");
                    mailno=sc.nextInt();
                    recall(userName,emailID,mailno,users);
                    break;

                default:
                    loop=false;

            }

        }
       
    }
}
