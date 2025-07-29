import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.awt.FlowLayout;

public class ImageEncryptorUI extends JFrame {
    JButton encryptButton, decryptButton;
    JFileChooser fileChooser;
    JTextField keyField;

    public ImageEncryptorUI() {
        setTitle("Image Encryptor (AES-128)");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        fileChooser = new JFileChooser();
        keyField = new JTextField(16);
        encryptButton = new JButton("Encrypt Image");
        decryptButton = new JButton("Decrypt Image");

        add(new JLabel("Enter 16-char AES Key:"));
        add(keyField);
        add(encryptButton);
        add(decryptButton);

        encryptButton.addActionListener(e -> performAction(true));
        decryptButton.addActionListener(e -> performAction(false));
    }

    private void performAction(boolean encrypt) {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File inputFile = fileChooser.getSelectedFile();
            String key = keyField.getText();

            if (key.length() != 16) {
                JOptionPane.showMessageDialog(this, "Key must be 16 characters long.");
                return;
            }

            try {
                File outputFile = new File(inputFile.getParent(),
                        (encrypt ? "encrypted_" : "decrypted_") + inputFile.getName());

                if (encrypt) {
                    ImageEncryptor.encrypt(inputFile, outputFile, key);
                    JOptionPane.showMessageDialog(this, "Encryption successful!\nSaved as: " + outputFile.getName());
                } else {
                    ImageEncryptor.decrypt(inputFile, outputFile, key);
                    JOptionPane.showMessageDialog(this, "Decryption successful!\nSaved as: " + outputFile.getName());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
}
