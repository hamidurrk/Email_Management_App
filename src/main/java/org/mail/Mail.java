package org.mail;
//can only parse emails from user's inbox and show it on the interface. password is removed from here.


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Set;
import javax.mail.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Mail extends JFrame {

    private static final String host = "imap.gmail.com";
    private static final String username = "hamidurrk@gmail.com";
    private static final String password = " ";
    private final int width = 1920;
    private final int height = 1080;

    public void MainWindow() {
        setTitle("Email Management");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel("Email Management");
        titleLabel.setBounds(100, height/8, 500, 80);
        titleLabel.setFont(titleLabel.getFont().deriveFont(48f));
        add(titleLabel);

        // login button
        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(175, height*4/8, 300, 30);
        loginButton.setFont(loginButton.getFont().deriveFont(18f));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    MenuWindow();
                } catch (MessagingException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });
        add(loginButton);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(175, height*5/8, 300, 30);
        createAccountButton.setFont(createAccountButton.getFont().deriveFont(18f));
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //CreateAccountWindow();
                dispose();
            }
        });
        add(createAccountButton);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        setVisible(true);
    }

    public void MenuWindow() throws MessagingException {
        JFrame frame = new JFrame("Menu - " + username);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

/*        JButton refresh = new JButton("Refresh");
        refresh.setFont(refresh.getFont().deriveFont(18f));
        refresh.setBounds(1880, 100, 30, 10);
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Refresh");
            }
        });*/

        // Create a panel for the sidebar
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));

        Properties properties = new Properties();
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.starttls.enable", "true");

        // Get a session with the Gmail server
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });


            // Connect to the server
            Store store = session.getStore("imaps");
            store.connect(host, username, password);
            Folder defaultFolder = store.getDefaultFolder();

            // Get a list of all the folders in the mail store
            Folder[] folders = defaultFolder.list("*");
            JList<Folder> pageList = new JList<>(folders);
            pageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        pageList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                // Get the selected page and switch to its panel
                Folder selectedPage = pageList.getSelectedValue();
                if (selectedPage.getName().equals("INBOX")) {
                    // Show panel for Page 1
                    System.out.println("Showing Page 1");
                } else if (selectedPage.getName().equals("All Mail")) {
                    // Show panel for Page 2
                    System.out.println("Showing Page 2");
                } else if (selectedPage.getName().equals("Drafts")) {
                    // Show panel for Page 3
                    System.out.println("Showing Page 3");
                } else if (selectedPage.getName().equals("Important")) {
                    // Show panel for Page 3
                    System.out.println("Showing Page 4");
                } else if (selectedPage.getName().equals("Sent Mail")) {
                    // Show panel for Page 3
                    System.out.println("Showing Page 5");
                } else if (selectedPage.getName().equals("Spam")) {
                    // Show panel for Page 3
                    System.out.println("Showing Page 6");
                } else if (selectedPage.getName().equals("Starred")) {
                    // Show panel for Page 3
                    System.out.println("Showing Page 7");
                } else if (selectedPage.getName().equals("Trash")) {
                    // Show panel for Page 3
                    System.out.println("Showing Page 8");
                }
            }
        });

        //sidebarPanel.add();

        // Open the inbox folder
        Folder inbox = store.getFolder("inbox");
        inbox.open(Folder.READ_ONLY);
        // Get all the messages in the inbox
        int messageCount = inbox.getMessageCount();
        Message[] messages = inbox.getMessages(messageCount - 50, messageCount);

        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (int i = messages.length - 1; i >= 0; i--) {
            Message message = messages[i];
            String subject = message.getSubject();
            Address[] fromAddresses = message.getFrom();
            // Get the sender's address
            String sender = (fromAddresses.length > 0) ? fromAddresses[0].toString() : "";

            listModel.addElement(subject);
            listModel.addElement(sender);
            listModel.addElement(" ");
            System.out.println("Sender: " + sender);
            System.out.println("Subject: " + subject);
            System.out.println();
        }
        // Create a JList using the list model
        JList<String> emailList = new JList<>(listModel);
        emailList.setFont(emailList.getFont().deriveFont(24f));
        // Create panels for each page
        JPanel page1Panel = new JPanel();
        page1Panel.setLayout(new BoxLayout(page1Panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane();
        page1Panel.add(scrollPane);
        scrollPane.setViewportView(emailList);
        scrollPane.setFont(scrollPane.getFont().deriveFont(18f));




        JPanel page2Panel = new JPanel();
        page2Panel.add(new JLabel("All Mail"));
        JPanel page3Panel = new JPanel();
        page3Panel.add(new JLabel("Drafts"));
        JPanel page4Panel = new JPanel();
        page4Panel.add(new JLabel("Important"));
        JPanel page5Panel = new JPanel();
        page5Panel.add(new JLabel("Sent Mail"));
        JPanel page6Panel = new JPanel();
        page6Panel.add(new JLabel("Spam"));
        JPanel page7Panel = new JPanel();
        page7Panel.add(new JLabel("Starred"));
        JPanel page8Panel = new JPanel();
        page8Panel.add(new JLabel("Trash"));

        // Add the panels to a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("INBOX", page1Panel);
        tabbedPane.setFont(tabbedPane.getFont().deriveFont(18f));
        tabbedPane.setSize(100, 20);
        tabbedPane.add("All Mail", page2Panel);
        tabbedPane.add("Drafts", page3Panel);
        tabbedPane.add("Important", page4Panel);
        tabbedPane.add("Sent Mail", page5Panel);
        tabbedPane.add("Spam", page6Panel);
        tabbedPane.add("Starred", page7Panel);
        tabbedPane.add("Trash", page8Panel);

        // Create a main panel to hold the sidebar and tabbed pane
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(sidebarPanel);
        mainPanel.add(tabbedPane);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);

    }

    public void InboxPanel() {
        JFrame frame = new JFrame("Inbox - " + username);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the properties for the IMAP server
        Properties properties = new Properties();
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.starttls.enable", "true");

        // Get a session with the Gmail server
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Connect to the server
            Store store = session.getStore("imaps");
            store.connect(host, username, password);

            // Open the inbox folder
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);

            // Get all the messages in the inbox
            int messageCount = inbox.getMessageCount();
            Message[] messages = inbox.getMessages(messageCount - 10, messageCount);

            DefaultListModel<String> listModel = new DefaultListModel<>();

            for (int i = messages.length - 1; i >= 0; i--) {
                Message message = messages[i];
                String subject = message.getSubject();
                Address[] fromAddresses = message.getFrom();
                // Get the sender's address
                String sender = (fromAddresses.length > 0) ? fromAddresses[0].toString() : "";

                listModel.addElement(sender + " " + subject);
                System.out.println("Sender: " + sender);
                System.out.println("Subject: " + subject);
                System.out.println();
            }
            // Create a JList using the list model
            JList<String> emailList = new JList<>(listModel);

            // Create a scroll pane and add the JList to it
            JScrollPane scrollPane = new JScrollPane(emailList);

            frame.add(scrollPane);
            frame.pack();
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        Mail mail = new Mail();
        mail.MainWindow();
           /*                                                                                     // Set the properties for the IMAP server
        Properties properties = new Properties();
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.starttls.enable", "true");
                                                                                                // Get a session with the Gmail server
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
                                                                                                // Connect to the server
            Store store = session.getStore("imaps");
            store.connect(host, username, password);
            Folder defaultFolder = store.getDefaultFolder();
                                                                                                // Get a list of all the folders in the mail store
            Folder[] folders = defaultFolder.list("*");
                                                                                                // Print the names of all the folders
            for (Folder folder : folders) {
                System.out.println(folder.getName());
            }
                                                                                                // Open the inbox folder
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
                                                                                                // Get all the messages in the inbox
            int messageCount = inbox.getMessageCount();
            Message[] messages = inbox.getMessages(messageCount - 10, messageCount);

            for (int i = messages.length - 1; i >= 0; i--) {
                Message message = messages[i];
                String subject = message.getSubject();
                Address[] fromAddresses = message.getFrom();
                                                                                                // Get the sender's address
                String sender = (fromAddresses.length > 0) ? fromAddresses[0].toString() : "";

                System.out.println("Sender: " + sender);
                System.out.println("Subject: " + subject);
                System.out.println();
            }
                                                                                                // Disconnect from the server
            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        } */
    }
}
