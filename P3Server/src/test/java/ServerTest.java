import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.function.Consumer;

// Creating the class for the Server tests
class ServerTest {
	RPSLSServer server;
	Server servers;
	GameInfo gameInfo;
	int port = 5;
	Consumer<Serializable> call;
	Consumer<Serializable> call2;


	@BeforeEach
	void init() {
		server = new RPSLSServer();
		servers = new Server(port,call,call2);
		gameInfo = new GameInfo();
	}

	// Testing for the right class
    @Test
    void testRPSLSServer() {
        assertEquals("RPSLSServer",server.getClass().getName(),"The names match.");
    }

    // Testing for the right class
    @Test
    void testServer() {
        assertEquals("Server",servers.getClass().getName(),"The names match.");
    }

    // Testing for the right class
    @Test
    void testGameInfo() {
        assertEquals("GameInfo",gameInfo.getClass().getName(),"The names match.");
    }

    // Testing for the right port
    @Test
    void testPort() {
        assertEquals(5,port,"The ports match.");
    }

    // Testing to see if the ports don't match
    @Test
    void testPort2() {
	    assertNotEquals(7,port,"The values do not match.");
    }
}
