package ru.netology.core.homework03.task2;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static final String PATH = "D:/java/Java Core/Homework 1/3.1/src/ru/netology/core/homework03/Games/savegames";

    public static void main(String[] args) {
        GameProgress game1 = new GameProgress(70, 3, 70, 5.9);
        GameProgress game2 = new GameProgress(40, 4, 60, 3.7);
        GameProgress game3 = new GameProgress(60, 5, 25, 4.5);

        saveGame(PATH + "/saveGame1.1.dat", game1);
        saveGame(PATH + "/saveGame1.2.dat", game1);
        saveGame(PATH + "/saveGame2.1.dat", game2);
        saveGame(PATH + "/saveGame2.2.dat", game2);
        saveGame(PATH + "/saveGame3.1.dat", game3);
        saveGame(PATH + "/saveGame3.2.dat", game3);

        zipFiles(Arrays.asList(PATH + "/saveGame1.1.dat", PATH + "/saveGame2.1.dat", PATH + "/saveGame3.1.dat"));

        removeNonZip(PATH);
    }

    private static void saveGame(String path, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipFiles(List<String> filePaths) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("D:/java/Java Core/Homework 1/3.1/src/ru/netology/core/homework03/Games/savegames/savegames.zip"))) {
            for (String filePath : filePaths) {
                File file = new File(filePath);
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void removeNonZip(String path) {
        Arrays.stream(new File(path).listFiles())
                .filter(item -> !item.getName().endsWith("zip"))
                .forEach(File::delete);
    }
}