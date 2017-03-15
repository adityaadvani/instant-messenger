
import java.awt.CardLayout;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Main class that handles the client side functions.
 *
 * @author Aditya Advani
 * @author Mohita Jethwani
 * @author Rohit Giyanani
 */
public class Client extends javax.swing.JFrame implements Runnable, Serializable {

    // variables
    static String destinationClient;
    static boolean authorized;
    public static String serverAddress = "";
    public static int serverPort = 0;
    static String key;
    public static HashSet<String> onlineList = new HashSet<String>();
    public static HashMap<String, String> ongoingChats = new HashMap<String, String>();
    public static String username;
    public static String password;
    static BigInteger e;
    static BigInteger n;
    static BigInteger d;

    // switcher decides the task
    /*
     * 0: initial Contact to Server
     * 1: only message
     * 2: only message + History
     * 3: only updateList
     * 4: message + history + updateList
     * 5: credentials 
     * 6: credentialSuccess
     * 7: credentialFailure
     */
    public int switcher;

    /*
     * 0: noneSelected
     * 1: login
     * 2: register
     */
    //instance variables
    public int loginOrRegister = 0;
    public DatagramSocket clientSocket, clientSocket1;
    public static int clientListeningPort;
    public byte[] clientReceivingDataArray = new byte[1024];
    public byte[] clientSendingDataArray = new byte[1024];
    public static InetAddress IPAddressOfServer;
    public int clientSendingPort = 0;
    public int clientPort = (int) (5001 + (Math.random() * 6000));
    public HeaderToClient header;
    public static Scanner sc = new Scanner(System.in);

    public String sendMessage = "";
    public String destination;

    /**
     * Creates new UI for a new client
     */
    public Client() {
        initComponents();
        list_contacts.addListSelectionListener(listSelectionListener);
        text_area_message_history.setVisible(false);
    }

    /**
     * Code to send message
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jFileChooser2 = new javax.swing.JFileChooser();
        jFrame1 = new javax.swing.JFrame();
        MainCard = new javax.swing.JPanel();
        Auth = new javax.swing.JPanel();
        button_sign_in = new javax.swing.JButton();
        label_instant_messenger_title = new javax.swing.JLabel();
        button_sign_up = new javax.swing.JButton();
        text_field_username = new javax.swing.JTextField();
        label_username = new javax.swing.JLabel();
        label_password = new javax.swing.JLabel();
        text_field_password = new javax.swing.JTextField();
        label_error_message = new javax.swing.JLabel();
        IM = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        text_area_message_history = new javax.swing.JTextArea();
        text_field_type_message = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        list_contacts = new javax.swing.JList();
        label_contacts = new javax.swing.JLabel();
        button_send_message = new javax.swing.JButton();
        button_update_list = new javax.swing.JButton();
        button_terminate_conn = new javax.swing.JButton();
        button_broadcast_message = new javax.swing.JButton();
        label_chat_name = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
                jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
                jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Instant Messenger");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                onExit(evt);
            }
        });

        MainCard.setLayout(new java.awt.CardLayout());

        Auth.setBackground(new java.awt.Color(102, 204, 255));

        button_sign_in.setText("Sign In");
        button_sign_in.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_sign_inActionPerformed(evt);
            }
        });

        label_instant_messenger_title.setText("Instant Messenger");

        button_sign_up.setText("Sign Up");
        button_sign_up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_sign_upActionPerformed(evt);
            }
        });

        label_username.setText("Username");

        label_password.setText("Password");

        label_error_message.setForeground(new java.awt.Color(255, 0, 0));
        label_error_message.setText(" ");

        javax.swing.GroupLayout AuthLayout = new javax.swing.GroupLayout(Auth);
        Auth.setLayout(AuthLayout);
        AuthLayout.setHorizontalGroup(
                AuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AuthLayout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addGroup(AuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(AuthLayout.createSequentialGroup()
                                        .addComponent(button_sign_in, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(67, 67, 67)
                                        .addComponent(button_sign_up, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(AuthLayout.createSequentialGroup()
                                        .addGroup(AuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(label_password, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(label_username, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(AuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(text_field_username, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                                .addComponent(text_field_password)))
                                .addComponent(label_error_message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(159, 159, 159))
                .addGroup(AuthLayout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(label_instant_messenger_title)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AuthLayout.setVerticalGroup(
                AuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AuthLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label_instant_messenger_title, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addGap(37, 37, 37)
                        .addComponent(label_error_message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(AuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(text_field_username)
                                .addComponent(label_username, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(AuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(label_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(text_field_password))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addGroup(AuthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(button_sign_in, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(button_sign_up, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(60, 60, 60))
        );

        MainCard.add(Auth, "registration");

        IM.setBackground(new java.awt.Color(51, 153, 255));

        text_area_message_history.setBackground(new java.awt.Color(102, 204, 255));
        text_area_message_history.setColumns(20);
        text_area_message_history.setRows(5);
        text_area_message_history.setText("  ");
        jScrollPane3.setViewportView(text_area_message_history);

        text_field_type_message.setText("You have been logged in.");
        text_field_type_message.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_type_messageActionPerformed(evt);
            }
        });

        list_contacts.setBackground(new java.awt.Color(102, 204, 255));
        list_contacts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(list_contacts);

        label_contacts.setText("Your Contact List");

        button_send_message.setBackground(new java.awt.Color(153, 255, 0));
        button_send_message.setText("Send Message");
        button_send_message.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_send_messageActionPerformed(evt);
            }
        });

        button_update_list.setBackground(new java.awt.Color(153, 255, 0));
        button_update_list.setText("Update List");
        button_update_list.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_update_listActionPerformed(evt);
            }
        });

        button_terminate_conn.setBackground(new java.awt.Color(255, 51, 0));
        button_terminate_conn.setText("Terminate");
        button_terminate_conn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_terminate_connActionPerformed(evt);
            }
        });

        button_broadcast_message.setBackground(new java.awt.Color(255, 255, 0));
        button_broadcast_message.setText("Broadcast");
        button_broadcast_message.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_broadcast_messageActionPerformed(evt);
            }
        });

        label_chat_name.setText("No chats selected");

        javax.swing.GroupLayout IMLayout = new javax.swing.GroupLayout(IM);
        IM.setLayout(IMLayout);
        IMLayout.setHorizontalGroup(
                IMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, IMLayout.createSequentialGroup()
                        .addGroup(IMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(IMLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(IMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(label_contacts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jScrollPane4)))
                                .addGroup(IMLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(button_terminate_conn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(37, 37, 37)
                                        .addComponent(button_update_list, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(IMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, IMLayout.createSequentialGroup()
                                        .addComponent(label_chat_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(89, 89, 89))
                                .addGroup(IMLayout.createSequentialGroup()
                                        .addGroup(IMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(IMLayout.createSequentialGroup()
                                                        .addComponent(button_broadcast_message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                                        .addComponent(button_send_message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addComponent(text_field_type_message, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPane3))
                                        .addContainerGap())))
        );
        IMLayout.setVerticalGroup(
                IMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(IMLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(IMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(label_contacts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label_chat_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(IMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(IMLayout.createSequentialGroup()
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addGap(0, 0, 0)
                                        .addComponent(text_field_type_message, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(IMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(IMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button_update_list, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(button_terminate_conn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(IMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button_broadcast_message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(button_send_message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 29, Short.MAX_VALUE))
        );

        MainCard.add(IM, "IM");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(MainCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(MainCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>                        

    // on window close this method takes the action
    private void onExit(java.awt.event.WindowEvent evt) {
        new Thread(new Client(10, 0, header)).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void text_field_type_messageActionPerformed(java.awt.event.ActionEvent evt) {
    }

    // when the send message button is pressed this method takes the action
    private void button_send_messageActionPerformed(java.awt.event.ActionEvent evt) {
        String message = text_field_type_message.getText();
        text_field_type_message.setText("");
        String chat = "";

        //display messages with chat history
        if (ongoingChats.containsKey(destinationClient)) {
            chat = ongoingChats.get(destinationClient);
        }
        chat += username + ": " + message + "\n";
        text_area_message_history.setText(chat);

        ongoingChats.put(destinationClient, chat);
        message = username + ": " + message + "\n";

        //get destinationClient from focused list item.
        new Thread(new Client(9, message, destinationClient)).start();
    }

    // when the update list button is pressed, this method takes the action
    private void button_update_listActionPerformed(java.awt.event.ActionEvent evt) {
        requestUpdate();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("error in button_update_list: " + e);
        }

        //get items from hashSet and populate list elements.
        DefaultListModel model = new DefaultListModel();
        list_contacts.setModel(model);
        list_contacts.setFixedCellHeight(75);
        list_contacts.setFixedCellWidth(100);
        Iterator it = onlineList.iterator();
        String user = "";
        while (it.hasNext()) {
            user = it.next().toString();
            if (!user.equals(username)) {
                //create a new list of users and add only the currently active 
                //users to this list making sure the current logged in user is 
                //not displayed as one of the active users in their list
                model.addElement(user);
            }
        }
    }

    // when terminate button is pressed, this method takes action and terminates the user's session.
    private void button_terminate_connActionPerformed(java.awt.event.ActionEvent evt) {
        new Thread(new Client(10, 0, header)).start();
        CardLayout card = (CardLayout) MainCard.getLayout();
        card.show(MainCard, "registration");
    }

    // when broadcast button is pressed, this method takes action and forwards 
    //the message present in the textbox to all the currently online users.
    private void button_broadcast_messageActionPerformed(java.awt.event.ActionEvent evt) {
        String message = text_field_type_message.getText();
        text_field_type_message.setText("");
        message = username + ": " + message + "\n";
        new Thread(new Client(11, message, "")).start();
    }

    // when the sign in button os pressed, this method takes action to validate 
    //the query and let the user sign in if all the conditions are met
    private void button_sign_inActionPerformed(java.awt.event.ActionEvent evt) {

        authorized = false;

        //get username and password from the textbox
        username = text_field_username.getText();
        password = text_field_password.getText();

        System.out.println(username + " " + password);
        HeaderToServer header;

        try {
            // forward the query to the server
            IPAddressOfServer = InetAddress.getByName(serverAddress);
            header = new HeaderToServer(5, "", "", InetAddress.getLocalHost().getHostAddress(), clientListeningPort, username, password, "");
            byte[] sendData = HeaderToServer.getByteArray(header);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfServer, serverPort);
            DatagramSocket sendingSocket = new DatagramSocket();
            sendingSocket.send(sendPacket);
            sendingSocket.close();
            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println("error in sign in: " + e);
        }

        //after user is authorized, log in and move to chat screen
        if (authorized) {
            CardLayout card = (CardLayout) MainCard.getLayout();
            card.show(MainCard, "IM");
        }

        // if user is not authorized, give an error and ask to retry with available options.
        if (!authorized) {
            label_error_message.setText("Error Signing in, username or password wrong. If you you are a new user, select Sign Up.");
            text_field_username.setText("");
            text_field_password.setText("");
            username = "";
            password = "";

        }
    }

    // when the sign up button is pressed, this method takes action to validate 
    //the query and let the user sign up if all the conditions are met 
    private void button_sign_upActionPerformed(java.awt.event.ActionEvent evt) {

        authorized = false;

        //get username and password from the textbox
        username = text_field_username.getText();
        password = text_field_password.getText();

        System.out.println(username + " " + password);
        HeaderToServer header;

        try {
            //forward query to the server
            IPAddressOfServer = InetAddress.getByName(serverAddress);
            header = new HeaderToServer(6, "", "", InetAddress.getLocalHost().getHostAddress(), clientListeningPort, username, password, "");
            byte[] sendData = HeaderToServer.getByteArray(header);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfServer, serverPort);
            DatagramSocket sendingSocket = new DatagramSocket();
            sendingSocket.send(sendPacket);
            sendingSocket.close();
            Thread.sleep(100);
        } catch (Exception e) {
            System.out.println("error in sign in: " + e);
        }

        //after user is authorized, log in and move to chat screen
        if (authorized) {
            CardLayout card = (CardLayout) MainCard.getLayout();
            card.show(MainCard, "IM");
        }

        // if user is not authorized, give an error and ask to retry with available options.
        if (!authorized) {
            label_error_message.setText("Error Signing up, username already taken, try another username. If you you are a returning user, select Sign In.");
            text_field_username.setText("");
            text_field_password.setText("");
            username = "";
            password = "";

        }
    }

    public static String clientEnteredServerAddress;
    public static int clientEnteredServerPort;

    /**
     * Main Method
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        //get arguments from user
        //first argument is the server's address
        clientEnteredServerAddress = args[0];
        //second argument is the server's port number to connect to
        clientEnteredServerPort = Integer.parseInt(args[1]);
        //third argument is the client's port to connect from
        clientListeningPort = Integer.parseInt(args[2]);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
                new Thread(new Client(clientEnteredServerAddress, clientEnteredServerPort, 0, clientListeningPort)).start();
                new Thread(new Client(clientEnteredServerAddress, clientEnteredServerPort, 8, clientListeningPort)).start();
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JPanel Auth;
    private javax.swing.JPanel IM;
    private javax.swing.JPanel MainCard;
    private javax.swing.JButton button_broadcast_message;
    private javax.swing.JButton button_send_message;
    private javax.swing.JButton button_sign_in;
    private javax.swing.JButton button_sign_up;
    private javax.swing.JButton button_terminate_conn;
    private javax.swing.JButton button_update_list;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFileChooser jFileChooser2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel label_chat_name;
    private javax.swing.JLabel label_contacts;
    private javax.swing.JLabel label_error_message;
    private javax.swing.JLabel label_instant_messenger_title;
    private javax.swing.JLabel label_password;
    private javax.swing.JLabel label_username;
    private javax.swing.JList list_contacts;
    static javax.swing.JTextArea text_area_message_history;
    private javax.swing.JTextField text_field_password;
    private javax.swing.JTextField text_field_type_message;
    private javax.swing.JTextField text_field_username;
    // End of variables declaration                   

    //This part of code will change the chat window based on which user is selected
    ListSelectionListener listSelectionListener = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            boolean adjust = listSelectionEvent.getValueIsAdjusting();
            if (!adjust) {
                text_area_message_history.setVisible(true);

                JList list = (JList) listSelectionEvent.getSource();
                int selections[] = list.getSelectedIndices();
                Object selectionValues[];
                selectionValues = list.getSelectedValues();
                for (int i = 0, n = selections.length; i < n; i++) {

                    destinationClient = (String) selectionValues[i];
                    label_chat_name.setText("Your chat with: " + destinationClient);
                    String chat = ongoingChats.get(destinationClient);
                    text_area_message_history.setText(chat);
                }
            }
        }
    };

    // client constructor
    public Client(String ServerAddress, int ServerPort, int senderOrReceiver, int clientListeningPort) {
        this.serverAddress = ServerAddress;
        this.serverPort = ServerPort;
        this.switcher = senderOrReceiver;
        this.clientListeningPort = clientListeningPort;

    }

    //client constructor
    public Client(int switcher, int loginOrRegister, HeaderToClient header) {
        this.switcher = switcher;
        this.loginOrRegister = loginOrRegister;
        this.header = header;
    }

    //client constructor
    public Client(int switcher, String message, String destination) {
        this.switcher = switcher;
        this.sendMessage = message;
        this.destination = destination;
    }

    // this method contains the steps to send a message
    public void sendMessage() {
        try {
            clientSocket = new DatagramSocket();
            IPAddressOfServer = InetAddress.getByName(serverAddress);
            System.out.println("Sending the following message" + sendMessage + " from " + username + " to " + destinationClient);
            String eMessage = encrypt(sendMessage);
            HeaderToServer header = new HeaderToServer(2, username, destination, InetAddress.getLocalHost().getHostAddress(), clientListeningPort, "", "", sendMessage);
            byte[] sendData = HeaderToServer.getByteArray(header);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfServer, serverPort);
            DatagramSocket sendingSocket = new DatagramSocket();
            sendingSocket.send(sendPacket);
            sendingSocket.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // this method encrypts the messages
    public String encrypt(String input) {
        BigInteger data = new BigInteger(input.getBytes());
        String temp = data.toString();
        return temp;
    }

    // this method generates the private key
    public void generatePrivateKey(BigInteger p, BigInteger q) {
        BigInteger a1 = new BigInteger("1");
        BigInteger phi = p.subtract((BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e = e.add(a1).add(a1);
        }
        BigInteger d = e.modInverse(phi);
    }

    // this method generates the public key
    public void generatePublicKey() {
        Random random = new Random();
        BigInteger p = BigInteger.probablePrime(512, random);
        BigInteger q = BigInteger.probablePrime(512, random);
        e = BigInteger.probablePrime(256, random);
        n = p.multiply(q);
        generatePrivateKey(p, q);
    }

    // this method decrypts the encrypted message
    public String decrypt(String text) {
        String timp = text;
        BigInteger in = new BigInteger(text);
        String temp = in.toString();
        return timp;
    }

    // this method is used for broadcasting the messages to all the online users
    public void broadcast() {
        try {
            clientSocket = new DatagramSocket();
            IPAddressOfServer = InetAddress.getByName(serverAddress);
            System.out.println("Sending the following message" + sendMessage + " from " + username + " to " + destinationClient);
            String eMessage = encrypt(sendMessage);
            HeaderToServer header = new HeaderToServer(3, username, "", InetAddress.getLocalHost().getHostAddress(), clientListeningPort, "", "", sendMessage);
            byte[] sendData = HeaderToServer.getByteArray(header);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfServer, serverPort);
            DatagramSocket sendingSocket = new DatagramSocket();
            sendingSocket.send(sendPacket);
            sendingSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this method is used to make the initial connection with the server
    public void initialContact() {
        try {
            IPAddressOfServer = InetAddress.getByName(serverAddress);
            HeaderToServer header = new HeaderToServer(1, "", "", InetAddress.getLocalHost().getHostAddress(), clientListeningPort, "", "", "");
            System.out.println("ClientListeningPort: " + clientListeningPort);
            byte[] sendData = HeaderToServer.getByteArray(header);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfServer, serverPort);
            DatagramSocket sendingSocket = new DatagramSocket();
            sendingSocket.send(sendPacket);
            sendingSocket.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // this method handles the sign in and sign up part
    public void loginRegister() {
        try {
            int inputType = 0;
            clientSocket = new DatagramSocket();
            IPAddressOfServer = InetAddress.getByName(serverAddress);
            if (loginOrRegister == 0) {
                System.out.println("Menu");
                System.out.println("1: Login");
                System.out.println("2: Register");
                inputType = sc.nextInt();
                if (inputType == 1) {
                    inputType = 5;
                } else {
                    inputType = 6;
                }
            } else if (loginOrRegister == 1) {
                System.out.println("Menu");
                System.out.println("1: Login");
                System.out.println("2: Register");
                inputType = sc.nextInt();
                if (inputType == 1) {
                    inputType = 5;
                } else {
                    inputType = 6;
                }
            } else {
                System.out.println("Register");
                inputType = 6;
            }

            System.out.print("Enter username:	");
            username = sc.next();
            System.out.println("Enter password:	");
            password = sc.next();

            HeaderToServer header = new HeaderToServer(inputType, "", "", InetAddress.getLocalHost().getHostAddress(), clientListeningPort, username, password, "");
            System.out.println("ClientListeningPort: " + clientListeningPort);

            byte[] sendData = HeaderToServer.getByteArray(header);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfServer, serverPort);
            DatagramSocket sendingSocket = new DatagramSocket();
            sendingSocket.send(sendPacket);
            sendingSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // this method handles the unknown number of randomly incoming messages
    public void handleIncomingMessage() {
        String data = header.message;
        if (header.message != null) {
            System.out.println("The incoming message is" + header.message);
        } else {
            System.out.println("The incoming message is NULL");
        }
        String chat = ongoingChats.get(destinationClient);
        text_area_message_history.setText(chat);
        String sourceName = header.sourceName;
        //display chat histoey
        if (ongoingChats.containsKey(sourceName)) {
            System.out.println("Ongoing Chat exists");
            String currentChat = ongoingChats.get(sourceName);
            currentChat += header.message;
            System.out.println("Current chat" + currentChat);

            ongoingChats.put(sourceName, currentChat);

            chat = ongoingChats.get(destinationClient);
            text_area_message_history.setText(chat);
            //if no chat history
        } else {
            System.out.println("Ongoing chat does not exist");
            String currentChat = header.message;
            System.out.println("SourceName" + sourceName + " username" + username);
            ongoingChats.put(sourceName, currentChat);
        }
    }

    // this method helps to update the list of online users
    public void updateOnlineList() {
        onlineList = header.onlineList;
    }

    //this method helps to update the chat history of every user pair
    public void updateHistoryChat() {
        Iterator it = header.history.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
            if (!ongoingChats.containsKey(pair.getKey())) {
                ongoingChats.put(pair.getKey(), pair.getValue());
            }
        }
    }

    //when the window is closed ot the session is terminated, this method id used
    public void terminateConnection() {
        try {
            IPAddressOfServer = InetAddress.getByName(serverAddress);
            System.out.println("terminate user " + username);
            HeaderToServer header = new HeaderToServer(4, username, "", InetAddress.getLocalHost().getHostAddress(), clientListeningPort, "", "", "");
            byte[] sendData = HeaderToServer.getByteArray(header);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfServer, serverPort);
            DatagramSocket sendingSocket = new DatagramSocket();
            sendingSocket.send(sendPacket);
            sendingSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this method is used to request an updated list of currently online users
    public void requestUpdate() {
        try {
            clientSocket = new DatagramSocket();
            IPAddressOfServer = InetAddress.getByName(serverAddress);
            HeaderToServer header = new HeaderToServer(7, username, "", InetAddress.getLocalHost().getHostAddress(), clientListeningPort, "", "", "");
            byte[] sendData = HeaderToServer.getByteArray(header);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddressOfServer, serverPort);
            DatagramSocket sendingSocket = new DatagramSocket();
            sendingSocket.send(sendPacket);
            sendingSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this is the main logic of the client class's thread
    public void run() {

        //sender thread
        if (switcher == 0) {
            initialContact();

        } else if (switcher == 1) {
            handleIncomingMessage();

        } else if (switcher == 2) {
            updateHistoryChat();

        } else if (switcher == 3) {
            updateOnlineList();

        } else if (switcher == 5) {
        // now taken care of in the GUI

        } else if (switcher == 6) {

            if (header.message.equals("Login")) {
                System.out.println("You have successfully logged in");
                authorized = true;

            } else {
                System.out.println("You have successfully registered and logged in");
                authorized = true;
            }

            updateOnlineList();
            updateHistoryChat();

        } else if (switcher == 7) {

            if (loginOrRegister == 1) {
                System.out.println("Incorrect username and/or password");

            } else {
                System.out.println("Username already taken");
            }

            loginRegister();
        } //receiver thread
        else if (switcher == 8) {

            try {
                clientSocket1 = new DatagramSocket(clientListeningPort);

                while (true) {
                    System.out.println("waiting for reply from server");
                    DatagramPacket receivePacket = new DatagramPacket(clientReceivingDataArray, clientReceivingDataArray.length);
                    clientSocket1.receive(receivePacket);

                    HeaderToClient headerIncoming = (HeaderToClient) HeaderToClient.deserialize(clientReceivingDataArray);
                    int typeOfTask = headerIncoming.type;
                    System.out.println("Type of incoming message is" + typeOfTask);

                    if (typeOfTask == 7) {
                        if (headerIncoming.message.equals("Login")) {
                            new Thread(new Client(typeOfTask, 1, headerIncoming)).start();
                        } else {
                            new Thread(new Client(typeOfTask, 2, headerIncoming)).start();
                        }
                    } else {
                        new Thread(new Client(typeOfTask, 0, headerIncoming)).start();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (switcher == 9) {
            sendMessage();

        } else if (switcher == 10) {
            terminateConnection();

        } else if (switcher == 11) {
            broadcast();
        }
    }
}
