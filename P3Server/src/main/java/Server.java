import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

// Creating the class for the Server
public class Server {

    int count = 1;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    RPSLSServer theServer;
    TheServer server;
    private Consumer<Serializable> callback;
    private Consumer<Serializable> callback2;
    int port;
    int clientCount = 0;


    // Creating the constructor for the Server
    Server(int ports, Consumer<Serializable> call, Consumer<Serializable> call2) {

        callback = call;
        callback2 = call2;
        this.port = ports;
        server = new TheServer();
        server.start();
    }

    // Creating the class TheServer which extends the Thread class
    public class TheServer extends Thread {

        public void run() {

            try (ServerSocket mysocket = new ServerSocket(port)) {

                while (true) {

                    ClientThread c = new ClientThread(mysocket.accept(), count,false);
                    callback.accept("Client has connected to server: " + "Client #" + count);
                    clients.add(c);
                    c.start();
                    count++;

                }
            }//end of try
            catch (Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }

    // Creating the ClientThread class so data can be read in correctly
    class ClientThread extends Thread {


        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;
        GameInfo gameInfo = new GameInfo();
        boolean available;




        ClientThread(Socket s, int count, boolean isAvailable) {
            this.connection = s;
            this.count = count;
            this.available = isAvailable;
        }

        public void setOutputStream(ObjectOutputStream o2) {
            this.out = o2;
        }

        public ObjectOutputStream getOutputStream() {
            return out;
        }

        public void setInputStream(ObjectInputStream i2) {
            this.in = i2;
        }

        public ObjectInputStream getInputStream() {
            return in;
        }

        // Creating the run method so the comparing of the two clients can take place correctly
        public void run() {

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);

                setOutputStream(out);
                setInputStream(in);

                clientCount++;
                generateClients(gameInfo);
                callback2.accept(gameInfo.clientList);

            } catch (Exception e) {

            }

            while (true) {
                try {

                	gameInfo = (GameInfo)in.readObject();
                    //generateClients(gameInfo);
                    
                    while(true) {
                        if(clients.get(gameInfo.challenged - 1).available == false){
                            clients.get(gameInfo.challenged - 1).available = true;
                            clients.get(count - 1).available = true;
                            gameInfo.challengedBy = count;
                            gameInfo.message = "Client " + count + " Challenges " + gameInfo.challenged +
                                    ". Client " + count + " you may play first.";
                            clients.get(gameInfo.challengedBy - 1).getOutputStream().writeObject(gameInfo);
                            clients.get(gameInfo.challenged - 1).getOutputStream().writeObject(gameInfo);
                            callback.accept(gameInfo.message);
                            gameInfo = (GameInfo)in.readObject();
                            break;
                        }
                        else if (clients.get(count - 1).available == true ) {
                            break;
                        } else {
                            gameInfo.message = "Challenged client is currently playing a game.\n"
                                    + "Please wait for client to finish, or challenge another client";
                            out.writeObject(gameInfo);
                            gameInfo = (GameInfo) in.readObject();

                        }
                    }


                    int temp1 = gameInfo.challengedBy;
                    int temp2 = gameInfo.challenged;

                    // CLIENT 1 WIN CONDITIONS BEGIN HERE
                    if (gameInfo.p1Plays.intern() == "Rock" && gameInfo.p2Plays.intern() == "Scissors") {
                        gameInfo.message = "Client " + gameInfo.challengedBy + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p1Plays.intern() == "Rock" && gameInfo.p2Plays.intern() == "Lizard") {
                        gameInfo.message = "Client " + gameInfo.challengedBy + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p1Plays.intern() == "Paper" && gameInfo.p2Plays.intern() == "Rock") {
                        gameInfo.message = "Client " + gameInfo.challengedBy + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p1Plays.intern() == "Paper" && gameInfo.p2Plays.intern() == "Spock") {
                        gameInfo.message = "Client " + gameInfo.challengedBy + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p1Plays.intern() == "Scissors" && gameInfo.p2Plays.intern() == "Lizard") {
                        gameInfo.message = "Client " + gameInfo.challengedBy + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p1Plays.intern() == "Scissors" && gameInfo.p2Plays.intern() == "Paper") {
                        gameInfo.message = "Client " + gameInfo.challengedBy + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p1Plays.intern() == "Lizard" && gameInfo.p2Plays.intern() == "Paper") {
                        gameInfo.message = "Client " + gameInfo.challengedBy + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p1Plays.intern() == "Lizard" && gameInfo.p2Plays.intern() == "Spock") {
                        gameInfo.message = "Client " + gameInfo.challengedBy + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p1Plays.intern() == "Spock" && gameInfo.p2Plays.intern() == "Rock") {
                        gameInfo.message = "Client " + gameInfo.challengedBy + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p1Plays.intern() == "Spock" && gameInfo.p2Plays.intern() == "Scissors") {
                        gameInfo.message = "Client " + gameInfo.challengedBy + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } // CLIENT 1 WIN CONDITIONS END HERE
                    // *************************************************
                    // CLIENT 2 WIN CONDITIONS BEGIN HERE

                    else if (gameInfo.p2Plays.intern() == "Rock" && gameInfo.p1Plays.intern() == "Scissors") {
                        gameInfo.message = "Client " + gameInfo.challenged + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p2Plays.intern() == "Rock" && gameInfo.p1Plays.intern() == "Lizard") {
                        gameInfo.message = "Client " + gameInfo.challenged + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p2Plays.intern() == "Paper" && gameInfo.p1Plays.intern() == "Rock") {
                        gameInfo.message = "Client " + gameInfo.challenged + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p2Plays.intern() == "Paper" && gameInfo.p1Plays.intern() == "Spock") {
                        gameInfo.message = "Client " + gameInfo.challenged + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p2Plays.intern() == "Scissors" && gameInfo.p1Plays.intern() == "Lizard") {
                        gameInfo.message = "Client " + gameInfo.challenged + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p2Plays.intern() == "Scissors" && gameInfo.p1Plays.intern() == "Paper") {
                        gameInfo.message = "Client " + gameInfo.challenged + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p2Plays.intern() == "Lizard" && gameInfo.p1Plays.intern() == "Paper") {
                        gameInfo.message = "Client " + gameInfo.challenged + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p2Plays.intern() == "Lizard" && gameInfo.p1Plays.intern() == "Spock") {
                        gameInfo.message = "Client " + gameInfo.challenged + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p2Plays.intern() == "Spock" && gameInfo.p1Plays.intern() == "Rock") {
                        gameInfo.message = "Client " + gameInfo.challenged + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p2Plays.intern() == "Spock" && gameInfo.p1Plays.intern() == "Scissors") {
                        gameInfo.message = "Client " + gameInfo.challenged + " has won this round.";
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } // CLIENT 2 WIN CONDITIONS END HERE
                    // ***********************************
                    // TIE CONDITIONS BEGIN HERE

                    else if (gameInfo.p1Plays.intern() == "Rock" && gameInfo.p2Plays.intern() == "Rock") {
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.message = "This round was tied.";
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p1Plays.intern() == "Paper" && gameInfo.p2Plays.intern() == "Paper") {
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.message = "This round was tied.";
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p1Plays.intern() == "Scissors" && gameInfo.p2Plays.intern() == "Scissors") {
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.message = "This round was tied.";
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p1Plays.intern() == "Lizard" && gameInfo.p2Plays.intern() == "Lizard") {
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.message = "This round was tied.";
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } else if (gameInfo.p1Plays.intern() == "Spock" && gameInfo.p2Plays.intern() == "Spock") {
                        clients.get(gameInfo.challenged - 1).gameInfo.challengedBy = 0;
                        gameInfo.message = "This round was tied.";
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = gameInfo.p1Plays;
                        gameInfo.p2Choice = gameInfo.p2Plays;
                        gameInfo.p1Plays = " ";
                        gameInfo.p2Plays = " ";
                        gameInfo.challengedBy = 0;
                        gameInfo.challenged = 0;
                        gameInfo.client1 = temp1;
                        gameInfo.client2 = temp2;
                        clients.get(temp2 - 1).available = false;
                        clients.get(temp1 - 1).available = false;
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                    } // TIE CONDITIONS END HERE
                    // **********************************

                    else if(gameInfo.p1Plays.intern() != " " || gameInfo.p2Plays.intern() != " ") {
                        gameInfo.have2players = false;
                        gameInfo.p1Choice = " ";
                        gameInfo.p2Choice = " ";
                        clients.get(temp1-1).getOutputStream().writeObject(gameInfo);
                        gameInfo.have2players = true;
                        clients.get(temp2-1).getOutputStream().writeObject(gameInfo);
                        if(gameInfo.client1 == 0 || gameInfo.client2 == 0) {
                            callback.accept(" Message: There are " + clientCount + " clients connected." + "\n Client " + gameInfo.challengedBy +
                                    ": " + gameInfo.p1Choice + "\n Client " + gameInfo.challenged + ": " + gameInfo.p2Choice + "\n");
                        }
                    }

                    if(gameInfo.client1 != 0 && gameInfo.client2 != 0) {
                        callback.accept(" Message: There are " + clientCount + " clients connected." + "\n Client " + gameInfo.client1 +
                                ": " + gameInfo.p1Choice + "\n Client " + gameInfo.client2 + ": " + gameInfo.p2Choice + "\n");
                    }


                } catch (Exception e) {
                    clientCount--;
                    for(int i = 0; i < clients.size(); i++) {
                        if(clients.get(i).count == count) {
                            clients.remove(i);
                        }
                    }
                    gameInfo.clientList = " ";
                    generateClients(gameInfo);
                    callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                    callback2.accept(gameInfo.clientList);
                    break;
                }
            }
        }//end of run

    }//end of client thread

    private void generateClients(GameInfo g) {
    	for(ClientThread cl : clients) {
    		g.clientList = g.clientList + "Client " + cl.count + "\n";
    	}
    	for(ClientThread cl: clients) {
    		//cl.gameInfo.clientList = g.clientList;
    		try {
    			cl.getOutputStream().writeObject(g);
    		}
    		catch (Exception e) {
    			
    		}
    	}
    }
    
    
}