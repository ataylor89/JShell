package jshell;

import java.io.File;

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
        System.out.println(text);
        System.out.println(text.length());
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
                    echo(words[1]);
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
    
    public void openGUI() {
        gui = new JShellGUI(this);
        gui.setVisible(true);
    }

    public static void main(String[] args) {
        JShell jShell = new JShell(); 
        jShell.openGUI();
    }
}
