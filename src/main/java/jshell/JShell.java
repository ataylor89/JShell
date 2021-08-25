package jshell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrewtaylor
 */
public class JShell {
    
    private File directory;
    private JShellGUI gui;
    
    public JShell() {
        directory = new File(System.getProperty("user.home"));
    }
    
    public void process(String text) {
        String[] words = text.split(" ");
        String firstWord = words[0].toUpperCase();
        try {
            Command command = Command.valueOf(firstWord);
        
            switch (command) {
                case CD:
                    cd(words[1]);
                    break;
                case LS:
                    ls();
                    break;
                case ECHO:
                    echo(text.substring(5));
                    break;
                case CAT:
                    cat(text.substring(4));
            }
        } catch (IllegalArgumentException e) {
            gui.append("Error: unrecognized command\n$ ");
        }
    }
    
    private void cd(String path) {
        File f = new File(directory, path);
        if (f.isDirectory()) {
            this.directory = f;
            gui.append("$ ");
        }
        else            
            gui.append("Error: must cd to a valid directory\n$ ");
    }
    
    private void ls() {
        for (String s : directory.list())
            gui.append(s + " ");
        gui.append("\n$ ");
    }
    
    private void echo(String text) {
        gui.append(text + "\n$ ");
    }
    
    private void cat(String path) {
        File f;
        if (path.startsWith(File.pathSeparator)) 
            f = new File(path);
        else
            f = new File(directory, path);

        if (f.isFile()) {
            try {
                String text = new String(Files.readAllBytes(f.toPath()));
                gui.append(text);
                gui.append("\n$ ");
            } catch (IOException ex) {
                Logger.getLogger(JShell.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            gui.append("Error: " + path + "is not a file\n$ ");
        }
    }
    
    public void openGUI() {
        gui = new JShellGUI(this);
        gui.setVisible(true);
    }

    public static void main(String[] args) {
        JShell jShell = new JShell(); 
        jShell.openGUI();
    }
}
