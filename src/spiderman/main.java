/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderman;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ZxoR (Yonatan)
 */
public class main extends javax.swing.JFrame {

    final Object lock = new Object();
    final JFileChooser fc = new JFileChooser();
    final ArrayList<Thread> threads;
    int statsscanned = 0; //how much scanned pages.
    DefaultTableModel agentsmodel;
    long[] tasktime;
    private boolean threadsSuspended;
    final static DefaultTableModel regexs = new DefaultTableModel(0, 3);
//spiderSettings communication and defaults
    static int threadSleepTime = 1000;
    static int threadHTTPTimeout = 5000;
    static int threadTimeoutLimit = 25000;
//eof spiderSettings

    public main() {
        initComponents();
        this.setVisible(false);
        loadingDialog loading = new loadingDialog(this, true);
        loading.setVisible(true);
        loading.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                System.out.println("Window closed");
                try {
                    main.super.setVisible(true);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });
        threads = new ArrayList<Thread>();
        queueList.add("http://www.hometheater.co.il");
        queueList.add("http://www.israelweather.co.il/forecast/index.html"); //its duplicated.. for testing. not production!
        queueList.add("http://stackoverflow.com/questions/7042762/easier-way-to-synchronize-2-threads-in-java");
        agentsmodel = (DefaultTableModel) agentsTable.getModel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        queueList = new java.awt.List();
        emailsFound = new java.awt.List();
        startThreadsButton = new javax.swing.JButton();
        knownList = new java.awt.List();
        suspendButton = new javax.swing.JButton();
        unsuspendButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        destroyThreadsButton = new javax.swing.JButton();
        statsLable = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        agentsTable = new javax.swing.JTable();
        saveEmailsButton = new javax.swing.JButton();
        threadSpinner = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        regexListButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        cleanEmailsList = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SPIDERman - If you can't beat them - Sell them weapons. [BINARY]");

        startThreadsButton.setText("Start threads");
        startThreadsButton.setMaximumSize(new java.awt.Dimension(119, 25));
        startThreadsButton.setMinimumSize(new java.awt.Dimension(119, 25));
        startThreadsButton.setPreferredSize(new java.awt.Dimension(119, 25));
        startThreadsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startThreadsButtonActionPerformed(evt);
            }
        });

        knownList.setVisible(false);

        suspendButton.setText("Suspend");
        suspendButton.setToolTipText("");
        suspendButton.setEnabled(false);
        suspendButton.setPreferredSize(new java.awt.Dimension(119, 25));
        suspendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suspendButtonActionPerformed(evt);
            }
        });

        unsuspendButton.setText("Resume");
        unsuspendButton.setEnabled(false);
        unsuspendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unsuspendButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Emails found:");

        jLabel2.setText("Queue:");

        destroyThreadsButton.setText("Destroy threads");
        destroyThreadsButton.setEnabled(false);
        destroyThreadsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destroyThreadsButtonActionPerformed(evt);
            }
        });

        statsLable.setForeground(new java.awt.Color(0, 24, 255));
        statsLable.setText("Statistics:");

        agentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Job", "Speed"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        agentsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(agentsTable);
        if (agentsTable.getColumnModel().getColumnCount() > 0) {
            agentsTable.getColumnModel().getColumn(0).setResizable(false);
            agentsTable.getColumnModel().getColumn(0).setPreferredWidth(20);
            agentsTable.getColumnModel().getColumn(1).setResizable(false);
        }

        saveEmailsButton.setText("Save emails to file");
        saveEmailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveEmailsButtonActionPerformed(evt);
            }
        });

        threadSpinner.setModel(new javax.swing.SpinnerNumberModel(2, 1, 10, 1));

        jLabel3.setText("Agents to run:");

        jLabel4.setText("Agents:");

        regexListButton.setText("Regex list");
        regexListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regexListButtonActionPerformed(evt);
            }
        });

        jButton1.setText("spiderman Settings");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cleanEmailsList.setText("Clear emails");
        cleanEmailsList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanEmailsListActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(knownList, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statsLable, javax.swing.GroupLayout.PREFERRED_SIZE, 917, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(threadSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(startThreadsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(suspendButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(unsuspendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(destroyThreadsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(emailsFound, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2)
                                    .addGap(66, 66, 66)
                                    .addComponent(cleanEmailsList)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(saveEmailsButton))
                                .addComponent(queueList, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(regexListButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(emailsFound, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(saveEmailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cleanEmailsList)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(queueList, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(suspendButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(threadSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(unsuspendButton, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(startThreadsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(destroyThreadsButton)
                            .addComponent(regexListButton)
                            .addComponent(jButton1)))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(knownList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(statsLable)
                        .addContainerGap())))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void startThreadsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startThreadsButtonActionPerformed
        tasktime = new long[Integer.parseInt(threadSpinner.getValue().toString())];
        agentsmodel.setRowCount(0);
        for (int i = 0; i < Integer.parseInt(threadSpinner.getValue().toString()); i++) {
            threads.add(new Thread(new Task(i)));
            agentsmodel.addRow(new Object[]{i + 1, "Created", "---"});
        }
        for (Thread thread : threads) {
            thread.start();
        }

        threadSpinner.setEnabled(false);
        suspendButton.setEnabled(true);
        destroyThreadsButton.setEnabled(true);
        startThreadsButton.setEnabled(false);
    }//GEN-LAST:event_startThreadsButtonActionPerformed

    private void suspendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suspendButtonActionPerformed
        threadsSuspended = true;
        unsuspendButton.setEnabled(true);
        suspendButton.setEnabled(false);

    }//GEN-LAST:event_suspendButtonActionPerformed

    private void unsuspendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unsuspendButtonActionPerformed
        threadsSuspended = false;
        for (Thread thread : threads) {
            thread.resume();
        }
        unsuspendButton.setEnabled(false);
        suspendButton.setEnabled(true);

    }//GEN-LAST:event_unsuspendButtonActionPerformed

    private void saveEmailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveEmailsButtonActionPerformed
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            PrintWriter writer = null;
            try {
                File file = fc.getSelectedFile();
                writer = new PrintWriter(file, "UTF-8");
                for (int i = 0; i <= (emailsFound.getItemCount() - 1); i++) {
                    writer.println(emailsFound.getItem(i));
                }
            } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                writer.close();
            }
        }
    }//GEN-LAST:event_saveEmailsButtonActionPerformed

    private void destroyThreadsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destroyThreadsButtonActionPerformed
        for (Thread thread : threads) {
            thread.stop();
        }
        threads.clear();
        threadsSuspended = false;
        threadSpinner.setEnabled(true);
        suspendButton.setEnabled(false);
        destroyThreadsButton.setEnabled(false);
        startThreadsButton.setEnabled(true);
        unsuspendButton.setEnabled(false);
        updateStats();
        agentsmodel.setRowCount(0);
    }//GEN-LAST:event_destroyThreadsButtonActionPerformed

    private void regexListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regexListButtonActionPerformed
        regexSettings diag = new regexSettings(this, true);
        diag.setLocationRelativeTo(this);
        diag.setVisible(true);
    }//GEN-LAST:event_regexListButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        spiderSettings diag = new spiderSettings(this, true);
        diag.setLocationRelativeTo(this);
        diag.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cleanEmailsListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanEmailsListActionPerformed
        emailsFound.removeAll();
    }//GEN-LAST:event_cleanEmailsListActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            saveSettings();
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable agentsTable;
    private javax.swing.JButton cleanEmailsList;
    private javax.swing.JButton destroyThreadsButton;
    private java.awt.List emailsFound;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private java.awt.List knownList;
    private java.awt.List queueList;
    private javax.swing.JButton regexListButton;
    private javax.swing.JButton saveEmailsButton;
    private javax.swing.JButton startThreadsButton;
    private javax.swing.JLabel statsLable;
    private javax.swing.JButton suspendButton;
    private javax.swing.JSpinner threadSpinner;
    private javax.swing.JButton unsuspendButton;
    // End of variables declaration//GEN-END:variables
   public String getHTML(String urlToRead) throws MalformedURLException {

        URL url;
        final HttpURLConnection conn;
        final BufferedReader rd;
        String line;
        String result = "";
        url = new URL(urlToRead);

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        rd.close();
                    } catch (IOException ex) {
                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    conn.disconnect();
                    System.out.println("Timeout has been handled.");
                }
            }, main.threadHTTPTimeout);
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            timer.cancel();
            timer.purge();
            rd.close();
            conn.disconnect();
            System.gc();
        } catch (Exception e) {
            System.err.println("getHTML exception when trying to read: " + url + ". ERROR MESSAGE: " + e.getMessage().toString());
        }
        return result;
    }

    public boolean isInList(java.awt.List list, String string) {
        for (int x = 0; x < list.getItemCount(); x++) {
            if (list.getItem(x).equals(string)) {
                return true;
            }
        }
        return false;
    }

    public void extracturls(String sourceCode, String originalurl) throws MalformedURLException {
        Pattern p = Pattern.compile("href=\"(.*?)\"");
        Matcher m = p.matcher(sourceCode);
        String url;

        while (m.find()) {
            if (!m.group(1).contains("#")) {
                if ((m.group(1).toString().contains(".html")) || (m.group(1).toString().contains(".php"))) {
                    URL aURL = new URL(originalurl);
                    url = m.group(1); // this variable should contain the link URL
                    if (url.startsWith("/")) {
                        url = "http://" + aURL.getHost() + url;
                    } else if (!url.startsWith("http://")) {
                        url = "http://" + aURL.getHost() + "/" + url; //problem with HTTPS and ftp:// and ssh:// and smb:// ftps:// ---- PORTS???
                    }
                    /*if (!isInList(knownList, url)) {
                     queueList.add(url);
                     knownList.add(url);
                     }*/
                    String MD5_URL = toMD5(url);
                    if (!isInSortedList(knownList, MD5_URL)) {
                        queueList.add(url);
                        //queueList.add(MD5_URL); // only used to see if working , please dont ever do this
                        addToList(knownList, MD5_URL);
                    }
                }
            }
        }
    }

    public void updateStats() {
        if (threads.isEmpty()) {
            statsLable.setText("Statistics: ");
            return;
        }
        long total = 0;
        for (int x = 0; x < tasktime.length; x++) {
            total += tasktime[x];
        }
        total = total / tasktime.length;
        total = (TimeUnit.MINUTES.toNanos(1) / total) * tasktime.length;
        statsLable.setText("Statistics: Agents running: " + threads.size() + ". Queued: " + queueList.countItems() + ". Emails found: " + emailsFound.countItems() + ". Cached: " + knownList.countItems() + ". Already scanned: " + statsscanned + ". Threads Per Minute: " + total + "T/pm.");
    }

    public void extractemails(String sourceCode) {
        Pattern p = Pattern.compile("([a-zA-Z0-9.]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,5})))");
        Matcher m = p.matcher(sourceCode);
        String email = null;
        while (m.find()) {
            email = m.group(1); // this variable should contain the link URL
            if (!isInList(emailsFound, email)) {
                emailsFound.add(email);
            }
        }
    }

    class Task extends Thread {

        int id;

        public Task(int i) {
            this.id = i;
        }

        @Override
        public void run() {
            String url;
            String source;
            long ctime;
            agentsmodel.setValueAt("Starting", id, 1);
            try {
                Thread.sleep(id * 1000); //1 seconds between threads
            } catch (InterruptedException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (true) {
                while (threadsSuspended) {
                    agentsmodel.setValueAt("Suspended", id, 1);
                    agentsmodel.setValueAt("---", id, 2);
                    this.suspend();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                ctime = System.nanoTime();
                try {
                    agentsmodel.setValueAt("Working", id, 1);
                    //Thread.sleep(id * 1000);
                    synchronized (lock) {
                        if (queueList.getItemCount() > 0) {
                            url = queueList.getItem(0);
                            queueList.delItem(0);
                        } else {
                            url = "http://www.hometheater.co.il";
                        }
                    }
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            synchronized (threads) {
                                //threads.get(id).interrupt();
                                threads.set(id, null); //nullify thread
                                threads.set(id, new Thread(new Task(id))); //recreate
                                try {
                                    Thread.sleep(5000); //give me 5 seconds before recreat.. 
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                threads.get(id).start(); //then run it again
                                System.out.println("Socket crash has been handled and recreated!!!");
                            }
                        }

                    }, main.threadTimeoutLimit);
                    source = getHTML(url);

                    try {
                        extracturls(source, url);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    extractemails(source);
                    statsscanned++;
                    tasktime[id] = (System.nanoTime()
                            - ctime);
                    agentsmodel.setValueAt(
                            (TimeUnit.MINUTES.toNanos(1) / tasktime[id]) + "T/pm", id, 2);
                    updateStats();

                    timer.cancel();

                    timer.purge();

                    try {
                        agentsmodel.setValueAt("Sleeping", id, 1);
                        Thread.sleep(main.threadSleepTime);
                    } catch (InterruptedException ex) {
                        System.err.println("thread id: " + id + " exception when trying to proccess " + url + ". ERROR MESSAGE: " + ex.getMessage().toString());
                    }
                } catch (MalformedURLException ex) {
                    System.err.println("thread id: " + id + " exception when trying to proccess everything. ERROR MESSAGE: " + ex.getMessage().toString());
                }

            }
        }
    }

// if it returns a positive number it exists in the 
    public boolean isInSortedList(java.awt.List list, String string) {
        return !(BinarySearch(list, string) < 0);
    }

    //adds to sorted list
    public void addToList(java.awt.List list, String key) {
        //first time we add
        if (list.getItemCount() == 0) {
            list.add(key);
            return;
        }
        int position = 0;
        int lo = 0;
        boolean hasBroken = false;
        int mid = 0;
        int hi = list.getItemCount() - 1;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            if (key.compareToIgnoreCase(list.getItem(mid)) < 0) {
                hi = mid - 1;
            } else if (key.compareToIgnoreCase(list.getItem(mid)) > 0) {
                lo = mid + 1;
            } else {
                position = mid;
                hasBroken = true;
                break;

            }
        }
        position = hasBroken ? position : mid;

        if (position >= 0) {
            list.add(key, position);
            return;
        }
        position = ~position;
        list.add(key, position);

    }

    //performs a binary swearch
    public int BinarySearch(java.awt.List list, String key) {
        int lo = 0;
        int hi = list.getItemCount() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key.compareToIgnoreCase(list.getItem(mid)) < 0) {
                hi = mid - 1;
            } else if (key.compareToIgnoreCase(list.getItem(mid)) > 0) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public String toMD5(String message) {
        return (MD5.toHexString(MD5.computeMD5(message.toLowerCase().getBytes())));
    }

    public static void loadSettings() throws SQLException {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        String sql = "CREATE TABLE IF NOT EXISTS \"settings\" ( \"variable\" TEXT, \"value\" TEXT ); CREATE TABLE IF NOT EXISTS \"regexs\" ( \"description\" TEXT, \"regex\" TEXT, \"enabled\" TEXT );"; //if tables not exists so we need to create them so we can communicate.
        stmt = c.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        ResultSet rs = stmt.executeQuery("SELECT * FROM settings;"); //get the settings.
        String variable;
        String value;
        while (rs.next()) {
            variable = rs.getString("variable");
            value = rs.getString("value");
            //set variables
            if (variable.equals("sleepTime")) {
                threadSleepTime = Integer.parseInt(value);
            } else if (variable.equals("HTTPTimeout")) {
                threadHTTPTimeout = Integer.parseInt(value);
            } else if (variable.equals("threadTimeoutLimit")) {
                threadTimeoutLimit = Integer.parseInt(value);
            }
        }
        System.out.println("Settings loaded succesfully.");

        rs.close();
        rs = stmt.executeQuery("SELECT * FROM regexs;");
        String descr;
        String regex;
        String enabled;
        while (rs.next()) {
            descr = rs.getString("description");
            regex = rs.getString("regex");
            enabled = rs.getString("enabled");
            regexs.addRow(new Object[]{descr, regex, Boolean.parseBoolean(enabled)}); //ad new regex from database
        }
        if (regexs.getRowCount() == 0) { //it means that no regex found so we need to add default emails regex
            regexs.addRow(new Object[]{"Default emails regex", "([a-zA-Z0-9.]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,5})))", true});
        }
        System.out.println("Regexs loaded succesfully.");
        c.close();
    }

    public static void saveSettings() throws SQLException {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        String sql = "CREATE TABLE IF NOT EXISTS \"settings\" ( \"variable\" TEXT, \"value\" TEXT ); CREATE TABLE IF NOT EXISTS \"regexs\" ( \"description\" TEXT, \"regex\" TEXT, \"enabled\" TEXT );"; //if tables not exists so we need to create them so we can communicate.
        stmt = c.createStatement();
        stmt.executeUpdate(sql);
        sql = "DELETE FROM settings; DELETE FROM regexs;"; //delete all from the tables
        stmt.executeUpdate(sql);
        //start to insert
        sql = "INSERT INTO settings (\"variable\", \"value\") VALUES (\"sleepTime\",\"" + threadSleepTime + "\");";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO settings (\"variable\", \"value\") VALUES (\"HTTPTimeout\",\"" + threadHTTPTimeout + "\");";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO settings (\"variable\", \"value\") VALUES (\"threadTimeoutLimit\",\"" + threadTimeoutLimit + "\");";
        stmt.executeUpdate(sql);
        for (int x = 0; x < regexs.getRowCount(); x++) {
            sql = "INSERT INTO regexs (\"description\", \"regex\", \"enabled\") VALUES (\""+regexs.getValueAt(x, 0)+"\",\""+regexs.getValueAt(x, 1)+"\",\""+regexs.getValueAt(x, 2)+"\");";
            stmt.executeUpdate(sql);
        }
        stmt.close();
        //save the settings
        c.close();
    }
}
