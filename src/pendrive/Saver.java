/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pendrive;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author abcdef65g
 */
public class Saver {

    public static void main(String[] args) throws Exception {
        Queue<File> q = new java.util.LinkedList<>();
        File f;
        int saved=0,howmany=0;
        List ignore = Arrays.asList(new String[]{"Thumbs.db","thumbs.db","Desktop.ini","desktop.ini"});
        String list = "";
        String path = JOptionPane.showInputDialog(null, "(Note: Does not need to be a pendrive, will work on any folder with hidden files,\n but keep in mind this will also display system files which could be dangerous)\n\nPlease write the path to the infected folder below:", "Pendrive Saver - Saving infected pendrives since 2013!", JOptionPane.QUESTION_MESSAGE);
        if(path==null){
            Runtime.getRuntime().exit(0);
        }
        f = new File(path);
        if(!f.isDirectory()){
            JOptionPane.showMessageDialog(null, "There does not seem to be any directories at the specified path.", "Error", JOptionPane.WARNING_MESSAGE);
            Runtime.getRuntime().exit(0);
        }
        q.offer(f);
        while (!q.isEmpty()) {
            f = q.poll();
            if (f.isDirectory()) {
                File[] fx = f.listFiles();
                if(fx!=null){
                    int fnum = fx.length;
                    for (int i=0;i<fnum;i++) {
                        if(fx[i]!=null){
                            //if(fx[i].isDirectory()) System.out.println("Adding " + fx[i].getAbsolutePath() + ".");
                            q.offer(fx[i]);
                        }
                    }
                }
            }
            if (f.isHidden() && !ignore.contains(f.getName())){
                String where = f.getAbsolutePath();
                String message = "Found hidden file \"" + where + "\"!";
                System.out.println(message);
                JOptionPane.showMessageDialog(null, message, "Hidden File Detected! Hulk Smash!", JOptionPane.WARNING_MESSAGE);
                howmany++;
                try{
                    Runtime.getRuntime().exec("attrib -s -h \"" + where + "\"");
                    saved++;
                    list = list + where + " (SAVED)\n";
                }catch(IOException io){
                    list = list + where + " (NOT SAVED)\n";
                }
                //DosFileAttributes dfa = Files.readAttributes(Paths.get(f.getParent()), DosFileAttributes.class);
            }
        }
        JOptionPane.showMessageDialog(null, "Task complete! " + howmany + " hidden files found and " + saved + " brought back into existence.", "Just another day of pendrive saving...", JOptionPane.INFORMATION_MESSAGE);
        if(howmany>0){
            JScrollPane scroll = new JScrollPane();
            JTextArea area = new JTextArea();
            area.setText(list.substring(0, list.lastIndexOf("\n")));
            area.setRows(Math.min(8, howmany));
            scroll.setViewportView(area);
            JOptionPane.showMessageDialog(null, scroll, "These were the guys I found.", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
