package org.mail;

import javax.swing.*;
import javax.swing.event.*;

public class SidebarExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sidebar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel for the sidebar
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));

        // Create a list of pages
        String[] pages = { "Page 1", "Page 2", "Page 3" };
        JList<String> pageList = new JList<>(pages);
        pageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pageList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                // Get the selected page and switch to its panel
                String selectedPage = pageList.getSelectedValue();
                if (selectedPage.equals("Page 1")) {
                    // Show panel for Page 1
                    System.out.println("Showing Page 1");
                } else if (selectedPage.equals("Page 2")) {
                    // Show panel for Page 2
                    System.out.println("Showing Page 2");
                } else if (selectedPage.equals("Page 3")) {
                    // Show panel for Page 3
                    System.out.println("Showing Page 3");
                }
            }
        });

        // Add the page list to the sidebar
        sidebarPanel.add(pageList);

        // Create panels for each page
        JPanel page1Panel = new JPanel();
        page1Panel.add(new JLabel("Page 1"));
        JPanel page2Panel = new JPanel();
        page2Panel.add(new JLabel("Page 2"));
        JPanel page3Panel = new JPanel();
        page3Panel.add(new JLabel("Page 3"));

        // Add the panels to a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Page 1", page1Panel);
        tabbedPane.add("Page 2", page2Panel);
        tabbedPane.add("Page 3", page3Panel);

        // Create a main panel to hold the sidebar and tabbed pane
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(sidebarPanel);
        mainPanel.add(tabbedPane);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
