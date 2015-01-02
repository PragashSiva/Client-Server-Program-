public class Constants {
    public static final String NULL_STR         = "";
    public static final char BLANK_CHAR         = ' ';
    public static final char ZERO_CHAR          = '0';
    public static final char DEL_MARK_CHAR      = '*';
    public static final char MESSAGE_ERROR_CHAR = '*';      // returned errors from NetIO must start with this character
    public static final char CR = '\n';

    public static final boolean DEBUG_ON = false; 
    
    public static final char SEND_REQUEST = 'S';        // client sends a message
    public static final char READ_REQUEST = 'R';        // client wants to read a message
    public static final char PASS_THROUGH = 'Z';        // server sends a message to the client
    public static final char SERVER_QUIT  = 'Q';        // command to quit the server from a server thread
    
    public static final int SERVER_COMMAND_POS = 0;
    
    public static final int SEND_MESSAGE_OPTION = 1;
    public static final int READ_MESSAGE_OPTION = 2;    
    public static final int QUIT_OPTION         = 3;
    
    public static final String SERVER_ADDRESS = "10.194.2.56";
    public static final int LOCAL_PORT = 5000;
    public static final int TIME_OUT   = 7000;             // send request with a 7000 millisecond timeout
    
    public static final int ID_LEN = 9;
    public static final int MARK_DELETE_POS = 9;            // Position in message that message is marked deleted
    public static final int MAX_DELETED_MESSAGES = 250;
        
    public static final int MINIMUM_SENDERS_MSG_LEN = 19;   // SEND_REQUEST + sender id + recipient id
    public static final int MINIMUM_READERS_MSG_LEN = 10;   // READ_REQUEST + recipient id
    
    public static final String MESSAGES_FILE = "messages.txt";
    public static final String TEMP_TXT_FILE = "temporaryFile.txt";
    
    public static final boolean HOME_NETWORK = false;
}
