import java.util.*;
public class Group extends User {
    String description;
    List<User> groupMembers=new ArrayList<User>();
    Group(String userName,String emailID,String password,String description)
    {
        super(userName, emailID, password);
        this.description=description;
    }
    
}
