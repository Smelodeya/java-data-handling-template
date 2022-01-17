package com.epam.izh.rd.online.service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        URL resources = this.getClass().getClassLoader().getResource("sensitive_data.txt");
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
            if (fileContent != null) {
                Pattern pattern = Pattern.compile("(\\d{4}\\s)\\d{4}\\s\\d{4}\\s(\\d{4})");
                Matcher matcher = pattern.matcher(fileContent.toString());
                fileContent = new StringBuilder(matcher.replaceAll("$1**** **** $2"));
            }
        }
        return fileContent.toString();
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        URL resources = this.getClass().getClassLoader().getResource("sensitive_data.txt");
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

            Pattern pattern = Pattern.compile("\\$\\{\\w+}");
            if (fileContent != null) {
                Matcher matcher = pattern.matcher(fileContent.toString());
                fileContent = new StringBuilder(matcher.replaceFirst(String.valueOf(Math.round(paymentAmount))));
                matcher = pattern.matcher(fileContent.toString());
                fileContent = new StringBuilder(matcher.replaceFirst(String.valueOf(Math.round(balance))));
            }
        }
        return fileContent.toString();
    }
}
