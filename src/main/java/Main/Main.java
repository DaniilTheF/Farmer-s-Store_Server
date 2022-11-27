package Main;

import Server.Connection;

public class Main {
        public static final int PORT_WORK = 8008;

        public static void main(String[] args) {
            Connection server = new Connection(PORT_WORK);
            new Thread(server).start();
//        server.stop();
        }
    }


