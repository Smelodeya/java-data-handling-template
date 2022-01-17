package com.epam.izh.rd.online.repository;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        long counter = 0;
        URL resource = this.getClass().getClassLoader().getResource(path);
        if (resource != null) {
            File directory = new File(resource.getPath());
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        counter++;
                    }
                    if (file.isDirectory()) {
                        counter += countFilesInDirectory(path + File.separator + file.getName());
                    }
                }
            }
        }
        return counter;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        long counter = 1;
        URL resource = this.getClass().getClassLoader().getResource(path);
        if (resource != null) {
            File directory = new File(resource.getPath());
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        counter += countDirsInDirectory(path + File.separator + file.getName());
                    }
                }
            }
        }
        return counter;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        Path sourceFile = Paths.get(from);
        Path copyFile = Paths.get(to);

        if (!Files.exists(copyFile.getParent())) {
            try {
                Files.createDirectory(copyFile.getParent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.copy(sourceFile, copyFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        File directory = new File(getClass().getResource("/").getPath() + path);
        File newFile = new File(directory.getAbsolutePath() + File.separator + name);
        boolean created = false;

        if (!newFile.exists()) {
            try {
                created = newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return created;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        URL resources = this.getClass().getClassLoader().getResource(fileName);
        StringBuilder fileContent = null;
        if (resources != null) {
        try (FileReader in = new FileReader(resources.getPath());
             BufferedReader reader = new BufferedReader(in)) {
            while (reader.ready()) {
                fileContent = new StringBuilder(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        return fileContent.toString();
    }
}
