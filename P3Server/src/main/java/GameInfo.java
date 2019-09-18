import java.io.Serializable;

// Creating the GameInfo class so communication between the server and client can go smoothly
class GameInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    int p1Points = 0;
    int p2Points = 0;
    String p1Plays = " ";
    String p2Plays = " ";
    String p1Choice = " ";
    String p2Choice = " ";
    String message = " ";
    Boolean have2players = false;
    Integer challenged = 0;
    Integer challengedBy = 0;
    int client1 = 0;
    int client2 = 0;
    String clientList = "";
}
