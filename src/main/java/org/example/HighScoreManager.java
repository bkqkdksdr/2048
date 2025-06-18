package org.example;

import java.io.*;
import java.util.Properties;

public class HighScoreManager {
// 最佳纪录存储在当前项目的 src/main/resources 目录中的 highscore.properties 文件里
private static final String SCORE_FILE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "highscore.properties";
private static final String HIGH_SCORE_KEY = "highScore";
    public static int loadHighScore() {
        File file = new File(SCORE_FILE_PATH);
        if (!file.exists()) {
            return 0;
        }

        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(file)) {
            properties.load(input);
            return Integer.parseInt(properties.getProperty(HIGH_SCORE_KEY, "0"));
        } catch (IOException | NumberFormatException e) {
            System.err.println("Failed to load high score: " + e.getMessage());
            return 0;
        }
    }

    public static void saveHighScore(int score) {
        File file = new File(SCORE_FILE_PATH);
        File parentDir = file.getParentFile();
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            System.err.println("Failed to create directories for high score file");
            return;
        }

        Properties properties = new Properties();
        properties.setProperty(HIGH_SCORE_KEY, String.valueOf(score));

        try (OutputStream output = new FileOutputStream(file)) {
            properties.store(output, "2048 Game High Score");
        } catch (IOException e) {
            System.err.println("Failed to save high score: " + e.getMessage());
        }
    }
}