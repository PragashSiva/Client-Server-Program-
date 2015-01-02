import hsa.Console;

public class Client
{
  public static Console c;
  
  public static void sendMessage()
  {
    String str1 = System.getProperty("user.name");
    c.println("Enter Recipient's ID");
    String str2 = c.readLine();
    
    c.println("Enter Message");
    String str3 = c.readLine();
    
    String str4 = "S" + str1 + str2 + str3;
    if (str4.charAt(0) == 'S')
    {
      if (str1.length() == 9)
      {
        if (str2.length() == 9)
        {
          if (str3.length() > 0)
          {
            NetIO localNetIO = new NetIO();
            localNetIO.sendRequest(str4, "10.194.2.129");
          }
          else
          {
            c.println("***Text is empty***");
          }
        }
        else {
          c.println("***RecipientID is incorrect length***");
        }
      }
      else {
        c.println("***SenderID is incorrect length***");
      }
    }
    else {
      c.println("***First character is invalid***");
    }
  }
  
  public static void receiveMessages()
  {
    NetIO localNetIO = new NetIO();
    
    String str = "R" + System.getProperty("user.name");
    localNetIO.sendRequest(str, "10.194.2.129");
    
    String[] arrayOfString = new String[1];
    arrayOfString[0] = "10.194.2.129";
    str = localNetIO.receiveRequest(arrayOfString);
    c.println(str.substring(18));
  }
  
  public static void main(String[] paramArrayOfString)
  {
    c = new Console();
    int i;
    do
    {
      c.println("1: Send Message, 2: Read Messages, 3: Quit");
      i = c.readInt();
      switch (i)
      {
      case 1: 
        sendMessage();
        break;
      case 2: 
        receiveMessages();
      }
      c.println("----------------------------------------------------");
    } while (i != 3);
  }
}
import hsa.Console;

public class Client
{
  public static Console c;
  
  public static void sendMessage()
  {
    String str1 = System.getProperty("user.name");
    c.println("Enter Recipient's ID");
    String str2 = c.readLine();
    
    c.println("Enter Message");
    String str3 = c.readLine();
    
    String str4 = "S" + str1 + str2 + str3;
    if (str4.charAt(0) == 'S')
    {
      if (str1.length() == 9)
      {
        if (str2.length() == 9)
        {
          if (str3.length() > 0)
          {
            NetIO localNetIO = new NetIO();
            localNetIO.sendRequest(str4, "10.194.2.129");
          }
          else
          {
            c.println("***Text is empty***");
          }
        }
        else {
          c.println("***RecipientID is incorrect length***");
        }
      }
      else {
        c.println("***SenderID is incorrect length***");
      }
    }
    else {
      c.println("***First character is invalid***");
    }
  }
  
  public static void receiveMessages()
  {
    NetIO localNetIO = new NetIO();
    
    String str = "R" + System.getProperty("user.name");
    localNetIO.sendRequest(str, "10.194.2.129");
    
    String[] arrayOfString = new String[1];
    arrayOfString[0] = "10.194.2.129";
    str = localNetIO.receiveRequest(arrayOfString);
    c.println(str.substring(18));
  }
  
  public static void main(String[] paramArrayOfString)
  {
    c = new Console();
    int i;
    do
    {
      c.println("1: Send Message, 2: Read Messages, 3: Quit");
      i = c.readInt();
      switch (i)
      {
      case 1: 
        sendMessage();
        break;
      case 2: 
        receiveMessages();
      }
      c.println("----------------------------------------------------");
    } while (i != 3);
  }
}
