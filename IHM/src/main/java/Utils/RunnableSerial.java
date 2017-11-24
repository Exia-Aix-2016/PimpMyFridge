package Utils;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Observable;

public class RunnableSerial extends Observable implements Runnable {

    private SerialPort comPort;
    private boolean exit = false;
    private OutputStream out;
    private InputStream in;

    private String[] requiredKeyList = {"Ta", "Tp", "H", "Pr", "Pw", "Tt", "Kp", "Ki", "Kd"};

    private static RunnableSerial instance;

    private RunnableSerial() {
        SerialPort[] comPorts = SerialPort.getCommPorts();
        if (comPorts.length > 0) {
            comPort = SerialPort.getCommPorts()[0];
            comPort.openPort();
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);

            out = comPort.getOutputStream();
            in = comPort.getInputStream();
        } else {
            System.err.println("No com port please restart the app");
        }
    }

    public static RunnableSerial getInstance() {
        if (instance == null) instance = new RunnableSerial();
        return instance;
    }

    @Override
    public void run() {

        try
        {
            String res = "";
            Boolean incoming = false;

            while (!exit) {
                char c = (char)in.read();
                switch (c) {
                    case '<':
                        res = "";
                        incoming = true;
                        break;
                    case '>':
                        HashMap<String, Double> hashMap = extractData(res);
                        incoming = false;

                        //Test if packet contain all required key
                        boolean packetComplete = true;
                        for (String key : requiredKeyList) {
                            if(!hashMap.containsKey(key)){
                                packetComplete = false;
                            }
                        }

                        // Notify observers
                        if (packetComplete) {
                            setChanged();
                            notifyObservers(extractData(res));
                        }

                        break;
                    default:
                        if (incoming) {
                            res += c;
                        }
                }
            }
            shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void write(String s) {
        try {
            out.write(s.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        exit = true;
    }

    private void shutdown() throws IOException {
        out.close();
        in.close();
        deleteObservers();
        comPort.closePort();
        System.out.println("port closed");
    }

    private HashMap<String, Double> extractData(String packet) {
        HashMap<String, Double> hashMap = new HashMap<>();

        for (String s: packet.split("\\|")) {
            String[] kv = s.split(":");
            if (kv.length == 2) {
                try {
                    Double val = Double.parseDouble(kv[1]);
                    hashMap.put(kv[0], val);
                } catch (Exception e) {
                    //System.err.println(e);
                }
            }
        }

        return hashMap;
    }
}
