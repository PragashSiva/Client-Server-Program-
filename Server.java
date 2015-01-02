import java.io.*;
public class Server
{
    public static int markedMessages = 0;
    static RandomAccessFile messagesFile;
    static RandomAccessFile tempFile;
    public static void openFile (String fileName)  // creates a .txt file to store messages
    {
	try
	{
	    messagesFile = new RandomAccessFile (fileName, "rw");
	}
	catch (IOException e)
	{
	}
    }




    public static void addMessageToFile (String msg)  // puts messages into the file
    {
	try
	{
	    messagesFile.seek (messagesFile.length ());
	    messagesFile.writeBytes (msg.substring (1) + "\n");
	}
	catch (IOException e)
	{
	}
    }




    public static String findMessageFromFile (String studentNumber)  //searches for messages in file with a linear search algorithm
    {
	String message = new String ("");
	try
	{
	    messagesFile.seek (0);
	    do
	    {
		message = messagesFile.readLine ();
	    }
	    while (message != null && !message.substring (9, 18).equals (studentNumber));
	}
	catch (IOException e)
	{
	}
	return message;
    }




    public static void deleteMailMsg (String studentNumber)
    {
	String message = null;
	try
	{
	    messagesFile.seek (0);
	    long fp = 0;
	    do
	    {
		fp = messagesFile.getFilePointer ();
		message = messagesFile.readLine ();
	    }
	    while (message != null && !(message.substring (9, 18)).equals (studentNumber));
	    if (message != null)
	    {
		messagesFile.seek (fp + Constants.MARK_DELETE_POS);
		messagesFile.writeByte ('*');
	    }
	    else
		System.out.print ("Fatal error in method deleteMailMsg(): Message not found");
	}
	catch (IOException e)
	{
	    System.out.print ("System error in method deleteMailMsg()");
	}
    }




    public static void packMessagesFile ()
    {
	String message = null;
	try
	{
	    tempFile = new RandomAccessFile ("tmpMessages.txt", "rw");
	    tempFile.setLength (0);


	    messagesFile.seek (0);
	    message = messagesFile.readLine ();
	    while (message != null)
	    {
		if (message.indexOf ("*") == -1)
		{
		    tempFile.writeBytes (message);
		    tempFile.writeBytes ("\n");
		}
		message = messagesFile.readLine ();
	    }
	    tempFile.close ();
	    messagesFile.close ();


	    File oldFile = new File ("messages.txt");
	    oldFile.delete ();
	    oldFile = new File ("tmpMessages.txt");
	    File newFile = new File ("messages.txt");
	    oldFile.renameTo (newFile);
	    openFile ("messages.txt");
	}
	catch (IOException e)
	{
	    System.out.println ("System error in method packMessagesFile()");
	}
    }




    public static void main (String[] args)
    {
	openFile ("messages.txt"); //checks or creates a messages file
	NetIO me = new NetIO ();   //allows the client to send things to the user
	String[] clientIPAddress = new String [1];


	do
	{


	    String message = me.receiveRequest (clientIPAddress);
	    switch (message.charAt (0)) //checks the type of request
	    {
		case Constants.SEND_REQUEST: //Sending messages
		    System.out.println ("Client wants to send mail");
		    addMessageToFile (message); //saves message to 'messages' file
		    System.out.println (message + "\nfrom " + clientIPAddress [0]); //prints message
		    System.out.println ();
		    break;


		case Constants.READ_REQUEST: //Reading messages
		    String mailMsg = findMessageFromFile (message.substring (1, 10));
		    if (mailMsg == null)
			mailMsg = "                  ***no more messages";
		    else
		    {
			deleteMailMsg (message.substring (1, 10));
			markedMessages++;
			if (markedMessages >= 100)
			{ // pack when 100 entries have been marked
			    packMessagesFile ();
			    markedMessages = 0;
			}
		    }
		    System.out.println ("Client wants to read mail");
		    me.sendRequest (Constants.PASS_THROUGH + mailMsg, clientIPAddress [0]);
		    System.out.println (mailMsg);
		    break;
	    }
	    System.out.print ("------------------------------------------------------------\n");
	}
	while (true); //because this is an infinite loop, no code can go after here
    }
}


