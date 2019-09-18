/*
	The imported libraries are below this comment block.
 */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// Creating the class RPSLSServer
public class RPSLSServer extends Application {

	// Declaring everything that is needed for the Server GUI
	Button startServer;
	Button quit;
	ListView<String> listItems;
	ListView<String> onlineClients;
	Scene scene1;
	Scene scene2;
	String portAddress;
	int portAddresses;

	Server serverConnection;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("RPSLS Server");

		// Creating the first window for port number
		startServer = new Button("Turn On");
		TextField port = new TextField();
		Label portAdd = new Label("Port");
		portAdd.setLabelFor(port);
		TilePane r = new TilePane();
		r.getChildren().add(portAdd);
		r.getChildren().add(port);
		r.getChildren().add(startServer);
		r.setAlignment(Pos.CENTER);

		quit = new Button("Quit");

		scene1 = new Scene(r,200,200);

		listItems = new ListView<>();
		onlineClients = new ListView();

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setCenter(listItems);
		pane.setBottom(quit);
		pane.setRight(onlineClients);

		scene2 = new Scene(pane,600,600);

		// Creating the listview so the data sent from the clients can be seen
		startServer.setOnAction(e->{ primaryStage.setScene(scene2);
			portAddress = port.getText();
			portAddresses = Integer.parseInt(portAddress);
            serverConnection = new Server(portAddresses,data -> {
                Platform.runLater(()->{
                    listItems.getItems().add(data.toString());
                });
            }, data2 ->
				Platform.runLater(()-> {
					onlineClients.getItems().clear();
					onlineClients.refresh();
					onlineClients.getItems().add(data2.toString());
			}));
        });
		
		//quit button exits the program
		quit.setOnAction(f -> {
			Platform.exit();
			System.exit(0);
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

}
