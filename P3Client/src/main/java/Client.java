import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

// Creating the client class
public class Client extends Thread {

    Socket socketClient;
    private Consumer<Serializable> callback;
    private Consumer<Serializable> callback2;

    ImageView image2 = new ImageView();
    ImageView image1 = new ImageView();

    String ip;
    int port;

    boolean ready = false;

    RPSLSClient clients = new RPSLSClient();

    ObjectInputStream in;
    ObjectOutputStream out;

    GameInfo gameInfo = new GameInfo();

    //Creating the constructor for the client class
    public Client(String ip, int port, Consumer<Serializable> call, Consumer<Serializable> call2) {
        callback = call;
        callback2 = call2;
        this.ip = ip;
        this.port = port;
    }

    // Creating the run method where the data is read in that is sent from the server
    public void run() {
        try {
            socketClient = new Socket(ip,port);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);

            while(true) {

                gameInfo = (GameInfo)in.readObject();
                callback2.accept(gameInfo.clientList);

                if(gameInfo.p1Choice != " " && gameInfo.p2Choice != " ") {

                    setImages1();
                    setImages2();

                    ready = true;
                    
                    if(gameInfo.client1 == 0 && gameInfo.client2 == 0
                    		&& gameInfo.challenged == 0 && gameInfo.challengedBy == 0) {
                    	
                    }
                    else if(gameInfo.client1 == 0 || gameInfo.client2 == 0) {
                        callback.accept(" Message: " + gameInfo.message + "\n Client " + gameInfo.challengedBy + ": " +
                                gameInfo.p1Choice + "\n Client " + gameInfo.challenged + ": " + gameInfo.p2Choice + "\n");
                    }
                    else if(gameInfo.client1 != 0 && gameInfo.client2 != 0) {
                        callback.accept(" Message: " + gameInfo.message + "\n Client " + gameInfo.client1 + ": " +
                                gameInfo.p1Choice + "\n Client " + gameInfo.client2 + ": " + gameInfo.p2Choice + "\n");
                        gameInfo.client1 = 0;
                        gameInfo.client2 = 0;
                        gameInfo.challenged = 0;
                        gameInfo.challengedBy = 0;
                        gameInfo.p1Choice = " ";
                        gameInfo.p2Choice = " ";
                    }
                    gameInfo.message = " ";
                }
                
            }
        } catch(Exception e) {

        }
    }

    // Creating the send method where it sends out the data to the server
    public void send(String data) {
        ready = false;
        try {
            if(gameInfo.have2players == false) {
                gameInfo.p1Plays = data;
                out.writeObject(gameInfo);
            } else {
                gameInfo.p2Plays = data;
                out.writeObject(gameInfo);
            }
        } catch(IOException f) {

        }
    }

    public void send2(Integer x) {
        ready = false;
        try {
            gameInfo.challenged = x;
            out.writeObject(gameInfo);
        } catch(Exception a) {

        }

    }

    void setImages1() {
        if (gameInfo.p1Choice.intern() == "Rock" && gameInfo.p2Choice.intern() != " ") {
            image1.setImage(clients.rockImage);
        } else if (gameInfo.p1Choice.intern() == "Paper" && gameInfo.p2Choice.intern() != " ") {
            image1.setImage(clients.paperImage);
        } else if (gameInfo.p1Choice.intern() == "Scissors" && gameInfo.p2Choice.intern() != " ") {
            image1.setImage(clients.scissorsImage);
        } else if (gameInfo.p1Choice.intern() == "Lizard" && gameInfo.p2Choice.intern() != " ") {
            image1.setImage(clients.lizardImage);
        } else if (gameInfo.p1Choice.intern() == "Spock" && gameInfo.p2Choice.intern() != " ") {
            image1.setImage(clients.spockImage);
        } else {
            image1.setAccessibleText("Image is Coming");
        }
    }

    void setImages2() {
        if(gameInfo.p2Choice.intern() == "Rock" && gameInfo.p1Choice.intern() != " ") {
            image2.setImage(clients.rockImage);
        } else if (gameInfo.p2Choice.intern() == "Paper" && gameInfo.p1Choice.intern() != " ") {
            image2.setImage(clients.paperImage);
        } else if (gameInfo.p2Choice.intern() == "Scissors" && gameInfo.p1Choice.intern() != " ") {
            image2.setImage(clients.scissorsImage);
        } else if (gameInfo.p2Choice.intern() == "Lizard" && gameInfo.p1Choice.intern() != " ") {
            image2.setImage(clients.lizardImage);
        } else if (gameInfo.p2Choice.intern() == "Spock" && gameInfo.p1Choice.intern() != " ") {
            image2.setImage(clients.spockImage);
        } else {
            image2.setAccessibleText("Image is Coming");
        }
    }

    ImageView getImage1() {
        return image1;
    }

    ImageView getImage2() {
        return image2;
    }
}