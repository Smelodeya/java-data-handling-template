package com.epam.izh.rd.online.repository;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleFileRepository implements FileRepository {

    //private String path;

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        long count = 0;
        File f;

        URL res = this.getClass().getClassLoader().getResource(path);
        f = new File(res.getPath());
        //System.out.println(f.getAbsolutePath());


            if (f != null) {
                File[] files = f.listFiles();

                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            count++;
                        }
                        if (file.isDirectory()) {
                            count += countFilesInDirectory(file.getAbsolutePath());
                        }
                    }
                }
            }


        return count;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {

        long count = 1;
        File f =new File(path);
        File[] files = f.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    count += countDirsInDirectory(file.getAbsolutePath());
                }
            }
            //System.out.println(count);
        }  return count;
        // return 0;
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

        Path file = Paths.get(path + File.separator + name);
        boolean fileIsCreated = false;

        if (!Files.exists(file.getParent())) {
            try {
                Files.createDirectory(file.getParent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!Files.exists(file)){
            try {
                Files.createFile(file);
                fileIsCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }}

        return fileIsCreated;

        /*File dir = new File(path);
        File newFile = new File(path + File.separator + name);
        boolean created = false;

        if (!dir.exists()) {
            dir.mkdir();
        }

        if (!newFile.exists()){
            try {
                created = newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return created;*/
        //return false;

    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        String path = "src/main/resources";
        StringBuilder fileContent = null;

        try (FileReader in = new FileReader(path + File.separator + fileName);
             BufferedReader reader = new BufferedReader(in)) {
            while (reader.ready()) {
                fileContent = new StringBuilder(reader.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent.toString();
    }
}
