import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class SnoozeMatrixApp {
    private JFrame frame;
    private JPanel panel;
    //private GridBagConstraints position;
    private List<JButton> buttons;
    private List<Component> components;
    //private int currentChoice = 1; // Track the current choice level
    private final String PATH = System.getProperty("user.dir");
    private String path = "";
    //private String goBack = "";
    private String fileLocation = "";
    private final int TARGETREDRED = 204;
    private final int TARGETREDGREEN = 0;
    private final int TARGETREDBLUE = 0;
    Color targetRed = new Color(TARGETREDRED, TARGETREDGREEN, TARGETREDBLUE);
    private final int TARGETWHITERED = 255;
    private final int TARGETWHITEGREEN = 255;
    private final int TARGETWHITEBLUE = 255;
    Color targetWhite = new Color(TARGETWHITERED, TARGETWHITEGREEN, TARGETWHITEBLUE);
    private final int TARGETDARKGRAYRED = 51;
    private final int TARGETDARKGRAYGREEN = 51;
    private final int TARGETDARKGRAYBLUE = 51;
    Color targetDarkGray = new Color(TARGETDARKGRAYRED, TARGETDARKGRAYGREEN, TARGETDARKGRAYBLUE);
    private final int TARGETGRAYRED = 128;
    private final int TARGETGRAYGREEN = 130;
    private final int TARGETGRAYBLUE = 133;
    Color targetGray = new Color(TARGETGRAYRED, TARGETGRAYGREEN, TARGETGRAYBLUE);
    private final int TARGETLIGHTGRAYRED = 191;
    private final int TARGETLIGHTGRAYGREEN = 194;
    private final int TARGETLIGHTGRAYBLUE = 198;
    Color targetLightGray = new Color(TARGETLIGHTGRAYRED, TARGETLIGHTGRAYGREEN, TARGETLIGHTGRAYBLUE);
    

    public SnoozeMatrixApp() {
        setUIFont(new FontUIResource(new Font("Helvetica", 0, 14)));
        frame = new JFrame("Snooze Matrix");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(800, 900);
        

        createMenuBar();

        ImageIcon img = new ImageIcon(String.format("%s\\lib\\Image\\bullseye.png", PATH));
        frame.setIconImage(img.getImage());

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(targetWhite);
        buttons = new ArrayList<>();
        components = new ArrayList<>();

        

        //position = new GridBagConstraints();

        fileLocation = String.format("%s\\Database\\initial_buttons.txt", PATH);
        loadButtonsFromFile(fileLocation); // Load initial buttons
        addVerticalSpacing(10);

        
        frame.add(panel);

        frame.pack();

        frame.setPreferredSize(new Dimension(800, 900));

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu filMenu = new JMenu("File");
        menuBar.add(filMenu);

        JMenuItem goBackMenuItem = new JMenuItem("Go Back");
        goBackMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        filMenu.add(goBackMenuItem);

        JMenuItem startOverMenuItem = new JMenuItem("Start Over");
        startOverMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startOver();
            }
        });
        filMenu.add(startOverMenuItem);

        frame.setJMenuBar(menuBar);
    }

    public static void setUIFont(FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }

    private String goBackFile() {
        String[] parts = fileLocation.split("_");
        String[] result = new String[parts.length];

        for (int i = 0; i < parts.length; i++) {
            result[i] = parts[i].replaceAll("\\.txt$", "");
        }
        /**
        for (String part : result) {
            System.out.println(part);
        }
        */
        String newPath = result[0];
        path = "";
        for (int i = 1; i < result.length - 1; i++) {
            newPath = newPath + "_" + result[i];
            path = path + "_" + result[i];
        }
        newPath = newPath + ".txt";
        //System.out.printf("newPath: %s\n", newPath);
        return newPath;
    }

    private void goBack() {
        panel.removeAll();
        addVerticalSpacing(10);
        fileLocation = goBackFile();
        //loadButtonsFromFile(String.format("%s\\Database\\options%s.txt", PATH, goBack));
        loadButtonsFromFile(fileLocation);
        //path = goBack;
        frame.revalidate();
        frame.repaint();
    }

    private void startOver() {
        path = "";
        panel.removeAll();
        addVerticalSpacing(10);
        loadButtonsFromFile(String.format("%s\\Database\\initial_buttons.txt", PATH));
        frame.revalidate();
        frame.repaint();
    }

    // Load buttons from a file and add them to the panel
    private void loadButtonsFromFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("Final")) {
                    displayMessage(filename);
                    //loadButtonsFromFile(String.format("%s\\Database\\initial_buttons.txt", PATH));
                    //path = "";
                    //System.out.print(String.format("%s\\Database\\options%s.txt", PATH, goBack));
                    fileLocation = goBackFile();
                    //path = goBack;
                    panel.removeAll();
                    //loadButtonsFromFile(String.format("%s\\Database\\options%s.txt", PATH, goBack));
                    loadButtonsFromFile(fileLocation);
                    frame.revalidate();
                    frame.repaint();
                    break;
                }
                else if (line.startsWith("- -")) {
                    // Process as text with a note
                    JTextArea textArea = new JTextArea(4, 40);
                    textArea.setWrapStyleWord(true);
                    textArea.setLineWrap(true);
                    textArea.setText(line.substring(3).trim());
                    textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
                    //Font font = new Font("Segoe Script", Font.BOLD, 20);
                    //textArea.setFont(font);
                    textArea.setEditable(false);
                    components.add(textArea);
                    panel.add(textArea);
                }
                else if (line.startsWith("-h1")) {
                    // Process as text with a note
                    JTextArea textArea = new JTextArea(4, 40);
                    textArea.setWrapStyleWord(true);
                    textArea.setLineWrap(true);
                    textArea.setText(line.substring(3).trim());
                    textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
                    Font font = new Font("Helvetica", Font.BOLD, 20);
                    textArea.setFont(font);
                    textArea.setForeground(targetRed);
                    textArea.setEditable(false);
                    components.add(textArea);
                    panel.add(textArea);
                }
                else if (line.startsWith("-h2")) {
                    // Process as text with a note
                    JTextArea textArea = new JTextArea(4, 40);
                    textArea.setWrapStyleWord(true);
                    textArea.setLineWrap(true);
                    textArea.setText(line.substring(3).trim());
                    textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
                    Font font = new Font("Helvetica", Font.PLAIN, 12);
                    textArea.setFont(font);
                    textArea.setForeground(targetRed);
                    textArea.setEditable(false);
                    components.add(textArea);
                    panel.add(textArea);
                }
                else if (line.startsWith("-h3")) {
                    // Process as text with a note
                    JTextArea textArea = new JTextArea(4, 40);
                    textArea.setWrapStyleWord(true);
                    textArea.setLineWrap(true);
                    textArea.setText(line.substring(3).trim());
                    textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
                    Font font = new Font("Helvetica", Font.PLAIN, 12);
                    textArea.setFont(font);
                    textArea.setEditable(false);
                    components.add(textArea);
                    panel.add(textArea);
                }
                else {
                    JButton button = new JButton(line);
                    button.setAlignmentX(Component.LEFT_ALIGNMENT);
                    button.addActionListener(new ButtonClickListener());
                    //button.setBackground(targetGray);
                    //button.setForeground(targetRed);
                    //button.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 0));
                    buttons.add(button);

                    Component verticalStrut = Box.createRigidArea(new Dimension(0, 10));
                    panel.add(verticalStrut);
                    panel.add(button);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            startOver();
        }
        //System.out.printf("in load path %s\n", path);
        //System.out.printf("in load goback %s\n", goBack);
        //System.out.printf("filelocation: %s\n", fileLocation);
        /**
        JButton goBackButton = new JButton("Go Back");
        goBackButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        JButton StartOverButton = new JButton("Start Over");
        StartOverButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        StartOverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startOver();
            }
        });
        panel.add(goBackButton);
        panel.add(StartOverButton);
        */
    }

    // Method to add bertical spacing between buttons
    private void addVerticalSpacing(int pixels) {
        Component spacer = Box.createVerticalStrut(pixels);
        panel.add(spacer);
    }

    // ActionListener for the buttons
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            String[] arrOfStrs = sourceButton.getText().split(" ");
            //goBack = path;
            path = path + "_" + arrOfStrs[0];
            //String optionFilename = PATH + "\\Database\\options" + path + ".txt";
            fileLocation = PATH + "\\Database\\options" + path + ".txt";
            //System.out.printf("path %s\n", path);
            //System.out.printf("goback %s\n", goBack);
            //System.out.printf("filelocation: %s\n", fileLocation);
            panel.removeAll(); // Remove existing buttons

            loadButtonsFromFile(fileLocation); // Load options buttons
            frame.pack();
            frame.revalidate();
            frame.repaint();
        }
    }

    private void displayMessage(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            StringBuilder contentExceptFirstLine = new StringBuilder();
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                } else {
                    contentExceptFirstLine.append(line).append("\n");
                }
            }

            reader.close();

            JOptionPane.showMessageDialog(null, contentExceptFirstLine.toString(), "Snooze", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SnoozeMatrixApp());
    }
}

