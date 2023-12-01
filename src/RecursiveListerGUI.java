import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecursiveListerGUI extends JFrame {
    private JButton startBtn;
    private JButton quitBtn;
    private JTextArea displayFileTA;
    private JScrollPane scrollPane;
    private JLabel titleLbl;
    private JPanel topPnl;
    private JPanel buttonPnl;

   public RecursiveListerGUI() {
       startBtn = new JButton("Start");
       quitBtn = new JButton("Quit");
       displayFileTA = new JTextArea();
       displayFileTA.setEditable(false);

       scrollPane = new JScrollPane(displayFileTA);
       titleLbl = new JLabel(" Recursive File Lister");

       setLayout(new BorderLayout());

       topPnl = new JPanel();
       topPnl.add(titleLbl);

       buttonPnl = new JPanel();
       buttonPnl.add(startBtn);
       buttonPnl.add(quitBtn);

       add(topPnl, BorderLayout.NORTH);
       add(buttonPnl, BorderLayout.SOUTH);
       add(scrollPane, BorderLayout.CENTER);


       startBtn.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               displayFileChooser();
           }
       });

       quitBtn.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               System.exit(0);
           }
       });
   }

    private void displayFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            listFiles(selectedDirectory);
        }
    }
    private void listFiles(File directory) {
        displayFileTA.setText("");

        if (directory.isDirectory()) {
            appendTA("Listing files in: " + directory.getAbsolutePath());
            listFilesRecursive(directory);
        } else {
            appendTA("Invalid directory selected.");
        }
    }
    private void listFilesRecursive(File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    listFilesRecursive(file);
                } else {
                    appendTA(file.getAbsolutePath());
                }
            }
        }
    }
    private void appendTA(String text) {
        displayFileTA.append(text + "\n");
        displayFileTA.setCaretPosition(displayFileTA.getDocument().getLength()); // Auto-scroll to the bottom
    }
}
