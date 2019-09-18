/*
	The imported libraries are below this comment head.
 */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// Creating the class RPSLSClient
public class RPSLSClient extends Application{
	// Declaring all the buttons needed for gameplay
	Button play;
	Button playAgain;
	Button quit;
	Button rock;
	Button paper;
	Button scissors;
	Button lizard;
	Button spock;

	// Creating the scenes,listview,text,textfields,and the object for the client class so the game can run smoothly.
	int portAdds;

	ListView<String> clientView;
	ListView<String> onlineClients;
	ListView<ImageView> imagesPlayed;

	Scene scene1;
	Scene scene2;
	Scene scene3;

	String ipAdd;
	String portAdd;
	String choice;
	
	Integer challenges;

	Text welcomeMessage;
	Text rulesMessage;

	TextField ipAddress;
	TextField portNumber;

	Client clients;



	// Creating the images for the buttons
	Image rockImage = new Image("rock.png");
	Image paperImage = new Image("paper.png");
	Image scissorsImage = new Image("scissors.png");
	Image lizardImage = new Image("lizard.png");
	Image spockImage = new Image("spock.png");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
        primaryStage.setTitle("Rock - Paper - Scissors - Lizard - Spock");

        welcomeMessage = new Text("Welcome to Rock Paper Scissors Lizard Spock!");
        welcomeMessage.setFont(Font.font("", FontPosture.ITALIC, 18));
        rulesMessage = new Text(
                "Scissors cuts paper, paper covers rock, rock crushes lizard, lizard poisons Spock, Spock smashes " +
                        "scissors, scissors decapitates lizard, lizard eats paper, paper disproves Spock, Spock " +
                        "vaporizes rock, and rock crushes scissors.");



        // Setting up the buttons with the images
        rock = new Button();
        rock.setGraphic(new ImageView(rockImage));
        paper = new Button();
        paper.setGraphic(new ImageView(paperImage));
        scissors = new Button();
        scissors.setGraphic(new ImageView(scissorsImage));
        lizard = new Button();
        lizard.setGraphic(new ImageView(lizardImage));
        spock = new Button();
        spock.setGraphic(new ImageView(spockImage));
        playAgain = new Button("Play Again");
        quit = new Button("Quit");
        play = new Button("Play");

        // Setting up the textfields for the first scene
        ipAddress = new TextField();
        portNumber = new TextField();
        Label label1 = new Label("IP Address");
        Label label2 = new Label("Port");
        label1.setLabelFor(ipAddress);
        label2.setLabelFor(portNumber);
		label1.setMnemonicParsing(true);
		label2.setMnemonicParsing(true);

		TilePane first = new TilePane();
		first.getChildren().add(label1);
		first.getChildren().add(ipAddress);
		first.getChildren().add(label2);
		first.getChildren().add(portNumber);
		first.getChildren().add(play);
		first.setAlignment(Pos.CENTER);

		// Making the scenes look better
		VBox vbox = new VBox();
		vbox.getChildren().addAll(welcomeMessage, rulesMessage);
		vbox.setAlignment(Pos.CENTER);

		HBox hbox1 = new HBox();
		hbox1.getChildren().addAll(rock, paper, scissors, lizard, spock);
		hbox1.setAlignment(Pos.CENTER);
		hbox1.setPadding(new Insets(5, 5, 5, 5));
		hbox1.setSpacing(15);

		HBox hbox3 = new HBox();
		hbox3.getChildren().addAll(quit);
		hbox3.setAlignment(Pos.BOTTOM_CENTER);
		hbox3.setSpacing(15);

		HBox hbox4 = new HBox();


		// Declaring the listview needed for the program
		clientView = new ListView<String>();
		onlineClients = new ListView<String>();
		imagesPlayed = new ListView<ImageView>();
		imagesPlayed.setOrientation(Orientation.HORIZONTAL);

		Text challengeText = new Text("Pick a client to challenge:");
		TextField challenge = new TextField();
		challenge.setMaxWidth(100);
		Button challengeButton = new Button("Challenge");
		
		
		
		VBox main = new VBox(5);
		main.getChildren().addAll(vbox, hbox1, imagesPlayed, clientView, hbox3, challengeText, challenge, challengeButton);
		main.setAlignment(Pos.CENTER);
		main.setPadding(new Insets(15, 15, 15, 15));

		BorderPane border = new BorderPane();
		border.setCenter(main);
		Text clientListText = new Text("Client List:\n");
		VBox clientBox = new VBox(clientListText,onlineClients);
		clientBox.setPadding(new Insets(70,60,20,60));
		border.setRight(clientBox);

		scene1 = new Scene(first,200,200);

		scene2 = new Scene(border,1920,1080);

		setDisable();
		
		challengeButton.setOnAction(e-> {
			challenges = Integer.parseInt(challenge.getText());
			try {
				challenge.clear();
				clients.send2(challenges);
			}
			catch(Exception f) {
				
			}
			
			
		});

		// Setting up the Actions for when the buttons are pressed
		play.setOnAction(g->{
			ipAdd = ipAddress.getText();
			portAdd = portNumber.getText();
			portAdds = Integer.parseInt(portAdd);
			clients = new Client(ipAdd,portAdds,data -> 
				Platform.runLater(()-> {
					clientView.getItems().add(data.toString());
					
					if(clients.ready == true) {
						imagesPlayed.getItems().clear();
						imagesPlayed.refresh();
						imagesPlayed.getItems().addAll(clients.getImage1(),clients.getImage2());
					}
					setEnable();
			}), data2 ->
				Platform.runLater(()-> {
					onlineClients.getItems().clear();
					onlineClients.refresh();
					onlineClients.getItems().add(data2.toString());
				}));
			clients.start();
			primaryStage.setScene(scene2);

		});

		rock.setOnAction(e -> {
			choice = "Rock";
			setDisable();
			try {
				clients.send(choice);
			}
			catch(Exception rock) {

			}
		});

		paper.setOnAction(e -> {
			choice = "Paper";
			setDisable();
			try {
				clients.send(choice);
			}
			catch(Exception p) {

			}
		});

		scissors.setOnAction(e -> {
			choice = "Scissors";
			setDisable();
			try {
				clients.send(choice);
			}
			catch(Exception s) {

			}
		});

		lizard.setOnAction(e -> {
			choice = "Lizard";
			setDisable();
			try {
				clients.send(choice);
			}
			catch(Exception l) {

			}
		});

		spock.setOnAction(e -> {
			choice = "Spock";
			setDisable();
			try {
				clients.send(choice);
			}
			catch(Exception sp) {

			}
		});

		playAgain.setOnAction(e -> {
			choice = "Play Again";
			try {
				clients.send(choice);
			} catch (Exception playAgain) {

			}
		});

		//this exits the program
		quit.setOnAction(e -> {
			choice = "Quit";
			try {
				clients.send(choice);
			} catch (Exception quit) {

			}
			Platform.exit();
			System.exit(0);
			try {

			} catch (Exception close) {

			}
		});
		
				
		//this closes up all the threads when exiting the program
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

		primaryStage.setScene(scene1);
		primaryStage.show();
	}

	// Creating two functions that disable and enable the buttons when needed
	void setDisable() {
		rock.setDisable(true);
		paper.setDisable(true);
		scissors.setDisable(true);
		lizard.setDisable(true);
		spock.setDisable(true);
	}

	void setEnable() {
		rock.setDisable(false);
		paper.setDisable(false);
		scissors.setDisable(false);
		lizard.setDisable(false);
		spock.setDisable(false);
	}


}


