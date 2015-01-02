import java.io.*;
import java.net.*;

public class NetIO {
    public NetIO ()
    {
    }

    public String sendRequest (String message, String destinationIPAddress)
    {
        Socket me;  // id of the application that data is sent to
        DataOutputStream output;
        DataInputStream input;
        String reply = new String (Constants.NULL_STR);
        
        long start = System.currentTimeMillis();
        do {
            try
            {
                me = new Socket (destinationIPAddress, Constants.LOCAL_PORT);
            
                if (Constants.DEBUG_ON)
                    System.out.println ("In sendRequest. Connected to: " + me.getInetAddress ().getHostAddress ());

                output = new DataOutputStream (me.getOutputStream ());
                output.writeUTF (message); // standard UTF encoding system
                input = new DataInputStream (me.getInputStream ());
                reply = input.readUTF ();  // standard UTF encoding system
            
                if (Constants.DEBUG_ON)
                    System.out.println ("In sendRequest. Reply: " + reply);
                
                me.close ();
            }
            catch (IOException e)
            {
                if (Constants.DEBUG_ON) {
                    System.out.println("Exception thrown in sendRequest");
                    e.printStackTrace ();
                }    
                reply = Constants.NULL_STR;
            }
        } while (reply.equals(Constants.NULL_STR) && 
                (System.currentTimeMillis() - start) <= Constants.TIME_OUT);
        
        if ((System.currentTimeMillis() - start) > Constants.TIME_OUT)
            reply = "***error: Send request timeout/server not responding";
        
        return reply;
    }

    // This method returns a reply containing the message sent by the source.
    // It sets sourceIPAddress to the incoming address of the source that sent the message
    // This ipAddress is not sent by the source through the parameter but through the
    // message that goes on the network line.
    // This method is particularly designed for the e-mail server with a particular
    // message structure. It responds to the client with error messages if needed 
    // Every request sent by the client and received by this method starts with a character
    // that is a command to the server. For now we have 3 commands:
    //
    // SEND_REQUEST with a value of 'S' -> the message is to be saved in the server 
    // READ_REQUEST with a value of 'R' -> the client wants to read their mail
    // PASS_THROUGH with a value of 'P' -> the message passes through. the command 'P' is removed here
    // MESSAGE_ERROR_CODE is the character that all error messages must have at position 0. This is checked by the client
    
    public String receiveRequest (String[] sourceIPAddress)
    {
        ServerSocket me;
        Socket connection;
        DataInputStream input;
        DataOutputStream output;
        String message = new String (Constants.NULL_STR);

        try
        {
            me = new ServerSocket (Constants.LOCAL_PORT, 100);                      // Step 1: Create Server Socket

            if (Constants.DEBUG_ON)
                System.out.println ("Waiting...");

            connection = me.accept ();                                              // Step 2: Wait for connection
            sourceIPAddress [0] = connection.getInetAddress ().getHostAddress ();

            input = new DataInputStream (connection.getInputStream ());             // Step 3: Get input and output streams
            message = input.readUTF ();

            if (Constants.DEBUG_ON)
                System.out.println ("In receiveRequest. Message: " + message + " from " + sourceIPAddress [0]);

            if (message.length() > 0) { 
                switch(message.charAt(Constants.SERVER_COMMAND_POS)) {
                    case Constants.SEND_REQUEST :
                        if (message.length() < Constants.MINIMUM_SENDERS_MSG_LEN)   // S123456789123456789
                            message = "***error: Invalid message length";                            
                        break;
                        
                    case Constants.READ_REQUEST :             
                        if (message.length() < Constants.MINIMUM_READERS_MSG_LEN)   // R123456789
                            message = "***error: Invalid message length";
                        break;
                        
                    case Constants.PASS_THROUGH :                                             // Z<anything>
                        if (message.length() > 1)
                            message = message.substring(1);                         // remove the Z code and pass the message on
                        else
                            message = Constants.NULL_STR;
                        break;
                        
                    default :
                        message =  "***error: Server command code not found";
                        break;
                }
            }
            else
                message = "***error: null message";
                            
            output = new DataOutputStream (connection.getOutputStream ());          
            output.writeUTF (message);
            connection.close ();                                            // Step 5: Close connection
            me.close ();
        }
        catch (IOException e)
        {
            if (Constants.DEBUG_ON) {            
                System.out.println("Exception thrown in receiveRequest");
                e.printStackTrace ();
            }
            message = "***error: system failure in receiving message";      // Careful. The TicTacToe needs this to be NULL_STR here
        }
        return message;
    }
}
