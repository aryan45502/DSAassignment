package six;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.SwingUtilities;

public class ImageDownloaderApplication extends javax.swing.JFrame {

    private ExecutorService executorService;
    private static final String DOWNLOAD_DIRECTORY = "./downloaded_file/";
    private List<Future<?>> downloadTasks;
    private Map<Future<?>, DownloadInfo> downloadInfoMap;
    private javax.swing.JButton cancel_btn;
    private javax.swing.JButton download_btn;
    private javax.swing.JTextField inputarea;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton pause_btn;
    private javax.swing.JProgressBar progressindicator;
    private javax.swing.JButton resume_btn;
    private javax.swing.JLabel textImage;
    private javax.swing.JLabel statusLabel; // New JLabel for displaying status messages

    public ImageDownloaderApplication() {
        initComponents();
        executorService = Executors.newFixedThreadPool(5);
        downloadTasks = new CopyOnWriteArrayList<>();
        downloadInfoMap = new ConcurrentHashMap<>();

        // Additional customization for the JFrame
        setSize(800, 600); // Set the initial size of the JFrame
        setLocationRelativeTo(null); // Center the JFrame on the screen
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        textImage = new javax.swing.JLabel();
        inputarea = new javax.swing.JTextField();
        progressindicator = new javax.swing.JProgressBar();
        cancel_btn = new javax.swing.JButton();
        download_btn = new javax.swing.JButton();
        pause_btn = new javax.swing.JButton();
        resume_btn = new javax.swing.JButton();
        statusLabel = new javax.swing.JLabel(); // New JLabel for displaying status messages

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600)); // Set the preferred size of the JFrame
        getContentPane().setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(234, 241, 248));
        jPanel1.setLayout(null);

        textImage.setFont(new java.awt.Font("Candara Light", 1, 24)); // Adjusted font size
        textImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textImage.setText("Image Downloader");
        jPanel1.add(textImage);
        textImage.setBounds(220, 50, 360, 30);

        inputarea.setFont(new java.awt.Font("Candara Light", 0, 14));
        jPanel1.add(inputarea);
        inputarea.setBounds(80, 120, 640, 30);

        progressindicator.setBackground(new java.awt.Color(34, 172, 243));
        jPanel1.add(progressindicator);
        progressindicator.setBounds(80, 260, 640, 42);

        cancel_btn.setFont(new java.awt.Font("Candara Light", 1, 18)); // Adjusted font size
        cancel_btn.setText("Cancel");
        cancel_btn.setBorder(null);
        cancel_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_btnActionPerformed(evt);
            }
        });
        jPanel1.add(cancel_btn);
        cancel_btn.setBounds(510, 330, 150, 40);

        download_btn.setFont(new java.awt.Font("Candara Light", 1, 18)); // Adjusted font size
        download_btn.setText("Download");
        download_btn.setBorder(null);
        download_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        download_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                download_btnActionPerformed(evt);
            }
        });
        jPanel1.add(download_btn);
        download_btn.setBounds(80, 330, 150, 40);

        pause_btn.setFont(new java.awt.Font("Candara Light", 1, 18)); // Adjusted font size
        pause_btn.setText("Pause");
        pause_btn.setBorder(null);
        pause_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pause_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pause_btnActionPerformed(evt);
            }
        });
        jPanel1.add(pause_btn);
        pause_btn.setBounds(250, 330, 150, 40);

        resume_btn.setFont(new java.awt.Font("Candara Light", 1, 18)); // Adjusted font size
        resume_btn.setText("Resume");
        resume_btn.setBorder(null);
        resume_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        resume_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resume_btnActionPerformed(evt);
            }
        });
        jPanel1.add(resume_btn);
        resume_btn.setBounds(380, 330, 150, 40);

        statusLabel.setFont(new java.awt.Font("Candara Light", 0, 16)); // Adjusted font size
        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setText("Download status will be displayed here");
        jPanel1.add(statusLabel);
        statusLabel.setBounds(80, 400, 640, 30);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_btnActionPerformed
        cancelDownloads();
    }//GEN-LAST:event_cancel_btnActionPerformed

    private void download_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_download_btnActionPerformed
        String urlsText = inputarea.getText();
        String[] urls = urlsText.split("[,\\s]+"); // Split the text by commas or whitespace
        for (String url : urls) {
            if (!url.isEmpty()) {
                downloadImage(url);
            }
        }
    }//GEN-LAST:event_download_btnActionPerformed

    private void pause_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pause_btnActionPerformed
        pauseDownloads();
    }//GEN-LAST:event_pause_btnActionPerformed

    private void resume_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resume_btnActionPerformed
        resumeDownloads();
    }//GEN-LAST:event_resume_btnActionPerformed

    private void downloadImage(String urlString) {
        Runnable downloadTask = new Runnable() {
            @Override
            public void run() {
                DownloadInfo downloadInfo = downloadInfoMap.get(Thread.currentThread());
                int progress = downloadInfo != null ? downloadInfo.getProgress() : 0;
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                    if (progress > 0) {
                        connection.setRequestProperty("Range", "bytes=" + progress + "-");
                    }

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_PARTIAL) {
                        int contentLength = connection.getContentLength();
                        InputStream inputStream = connection.getInputStream();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                            progress += bytesRead;
                            int currentProgress = (int) ((progress / (double) contentLength) * 100);
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    progressindicator.setValue(currentProgress);
                                }
                            });

                            if (Thread.currentThread().isInterrupted()) {
                                throw new InterruptedException("Download interrupted");
                            }

                            Thread.sleep(50);
                        }

                        String fileName = "image_" + System.currentTimeMillis() + ".jpg";
                        saveImage(outputStream.toByteArray(), fileName);

                        inputStream.close();
                        outputStream.close();

                        // Update status label when download is successful
                        updateStatusLabel("Download completed: " + fileName);
                    } else {
                        throw new IOException("Failed to download image. Response code: " + responseCode);
                    }
                } catch (IOException | InterruptedException e) {
                    if (e instanceof InterruptedException) {
                        Thread.currentThread().interrupt();
                    }
                    if (!(e instanceof InterruptedException)) {
                        e.printStackTrace();
                    }

                    // Update status label when there is an error
                    updateStatusLabel("Error downloading image: " + e.getMessage());
                }
            }
        };

        Future<?> task = executorService.submit(downloadTask);
        downloadTasks.add(task);
        downloadInfoMap.put(task, new DownloadInfo(urlString, 0));
    }

    private void saveImage(byte[] imageData, String fileName) {
        File directory = new File(DOWNLOAD_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fullPath = DOWNLOAD_DIRECTORY + fileName;

        try {
            FileOutputStream outputStream = new FileOutputStream(fullPath);
            outputStream.write(imageData);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resumeDownloads() {
        for (Future<?> task : downloadTasks) {
            if (task.isCancelled()) {
                DownloadInfo downloadInfo = downloadInfoMap.get(task);
                if (downloadInfo != null) {
                    downloadImage(downloadInfo.getUrl());
                }
            }
        }
    }

    private void pauseDownloads() {
        for (Future<?> task : downloadTasks) {
            if (!task.isDone() && !task.isCancelled()) {
                task.cancel(true);
            }
        }
    }

    private void cancelDownloads() {
        for (Future<?> task : downloadTasks) {
            task.cancel(true);
        }
        progressindicator.setValue(0);
    }

    private class DownloadInfo {
        private String url;
        private int progress;

        public DownloadInfo(String url, int progress) {
            this.url = url;
            this.progress = progress;
        }

        public String getUrl() {
            return url;
        }

        public int getProgress() {
            return progress;
        }
    }

    private void updateStatusLabel(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                statusLabel.setText(message);
            }
        });
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ImageDownloaderApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ImageDownloaderApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ImageDownloaderApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImageDownloaderApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ImageDownloaderApplication().setVisible(true);
            }
        });
    }
}
