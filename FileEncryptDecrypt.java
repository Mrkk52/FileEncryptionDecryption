//===============Package========================================================

package fileencryptdecrypt;

//=======input/ouput============================================================
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

//========Swing=================================================================
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

//==========awt=================================================================
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;


public class FileEncryptDecrypt extends JFrame {
    private JLabel resultLabel;
    JTextField inputFileField;
    JButton cancle,clear,encryptButton,decryptButton;

    public FileEncryptDecrypt() {
        setTitle("File Encryption/Decryption");
        getContentPane().setBackground(new Color(255, 255, 240));
        setLayout(null);
        setSize(560,500);
        setLocation(150,20);
        setVisible(true);
        
        //============Title=====================================================
        
        JLabel title = new JLabel("File Encryption/Decryption");
        title.setBounds(90,10,400,40);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 25));
        add(title);
        
        //===============Choose File============================================

        JLabel inputLabel = new JLabel("Select File");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 18));
        inputLabel.setBounds(55,100,150,30);
        inputLabel.setForeground(Color.blue);
        inputLabel.setBackground(Color.white);
        add(inputLabel);
        
        //=============inputField===============================================
        
        inputFileField = new JTextField();
        inputFileField.setBounds(150, 100, 240,30);
        inputFileField.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        inputFileField.setOpaque(false);
        inputFileField.setFont(new Font("Arial", Font.BOLD, 14));        
        add(inputFileField);
        
        //==================Browse==============================================

        JButton browseInputButton = new JButton("Browse");
        browseInputButton.setBounds(390, 100, 80,30);
        browseInputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    inputFileField.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        add(browseInputButton);
        
        //========================Encryption=Button=============================

        encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(50,400,90,30);
        encryptButton.setBackground(Color.black);
        encryptButton.setFont(new Font("Sans-sarif", Font.BOLD, 13));
        encryptButton.setForeground(Color.white);
        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputFilePath = inputFileField.getText();
                performFileOperation(inputFilePath, true);
            }
        });
        add(encryptButton);
        
        //=========================Decryption=Button============================

        decryptButton = new JButton("Decrypt");
        decryptButton.setBounds(170,400,90,30);
        decryptButton.setBackground(Color.black);
        decryptButton.setFont(new Font("Sans-sarif", Font.BOLD, 13));        
        decryptButton.setForeground(Color.white);
        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputFilePath = inputFileField.getText();
                performFileOperation(inputFilePath, false);
            }
        });
        add(decryptButton);
        
        //====================Clear=Button======================================
        
        clear = new JButton("Clear");
        clear.setBounds(290,400,80,30);
        clear.setBackground(Color.black);
        clear.setFont(new Font("Sans-sarif", Font.BOLD, 13));        
        clear.setForeground(Color.white);
        add(clear);
        
        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               resultLabel.setText("");
               inputFileField.setText("");
            }
        });
        
        //====================Cancel=Button=====================================
        
        cancle = new JButton("Exit");
        cancle.setBounds(404,400,80,30);
        cancle.setBackground(Color.black);
        cancle.setFont(new Font("Sans-sarif", Font.BOLD, 13));        
        cancle.setForeground(Color.white);
        add(cancle);
        
        cancle.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                setVisible(false);
            }
        });
        
        
        //=====================ResultLabel======================================
        
        resultLabel = new JLabel();
        resultLabel.setBounds(28,235,500,30);
        resultLabel.setBackground(Color.cyan);
        resultLabel.setForeground(Color.magenta);
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(resultLabel);

        setVisible(true);
    }

    private void performFileOperation(String inputFilePath, boolean isEncrypt) {
        File inputFile = new File(inputFilePath);
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
            String content = contentBuilder.toString();
            String modifiedContent = isEncrypt ? encrypt(content) : decrypt(content);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
                writer.write(modifiedContent);
                resultLabel.setText("File " + (isEncrypt ? "encrypted" : "decrypted") + " successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                resultLabel.setText("Error occurred while writing to the file.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            resultLabel.setText("Error occurred while reading the file.");
        }
    }

    private String encrypt(String content) {
        StringBuilder encryptedContent = new StringBuilder();
        for (char ch : content.toCharArray()) {
            encryptedContent.append((char) (ch + 3));
        }
        return encryptedContent.toString();
    }

    private String decrypt(String content) {
       StringBuilder decryptedContent = new StringBuilder();
       for (char ch : content.toCharArray()) {
           if (ch == '\n') {
               decryptedContent.append(ch);
           } else {
               decryptedContent.append((char) (ch - 3));
           }
       }
       return decryptedContent.toString();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FileEncryptDecrypt();
            }
        });
    }
}