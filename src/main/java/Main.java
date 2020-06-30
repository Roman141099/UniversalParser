
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * В данном задании использовалась библиотека Jsoup для парсинга сайта, а также регулярные выражения для предобработки данных и вывода символов в базу данных
 * Также использовался JDBC драйвер для подключения к базе данных SQLite
 */
public class Main {
    //примеры URL, с которых данные могут быть получены
    final static String EXAMPLE_1 = "https://www.simbirsoft.com/";
    final static String EXAMPLE_2 = "http://yandex.com";
    final static String EXAMPLE_3 = "https://jsoup.org/cookbook/extracting-data/selector-syntax";

    //примеры путей к файлам, с которых данные могут быть получены
    final static String EXAMPLE_1_PATH = "htmlExamples\\exampleHtml_1";
    final static String EXAMPLE_2_PATH = "htmlExamples\\exampleHtml_2";
    final static String EXAMPLE_3_PATH = "htmlExamples\\exampleHtml_3";

    public static void main(String[] args) throws IOException {
//        Connection connection = Jsoup.connect(EXAMPLE_1);//Используется для парсинга с сайта с использованием URL
//        connection.userAgent("Chrome");
//        connection.timeout(5000);
//        Document document = connection.get();

        Document document = Jsoup.parse(new File(EXAMPLE_2_PATH), "UTF-8");//Используется для парсинга из текстового документа

//        String htmlPage = document.outerHtml();//Получение html код в строку
//        createHtml(htmlPage);//использовался для записи html страницы в файл

        String docText = document.body().text();//Строка со всеми экранированными символами на странице
        Pattern patternSpace = Pattern.compile("([a-zа-яё\\d]+)([ЁА-ЯA-Z])");//Обработка данных для разделения слов
        docText = docText.replaceAll(patternSpace.pattern(), "$1 $2");
        Pattern pattern = Pattern.compile("(?<=\\W)([a-zA-Zа-яА-ЯёЁ]+)(?=\\W)", Pattern.UNICODE_CHARACTER_CLASS);//поиск символов, соответствующих кодировке UNICODE с учетом регистра
        Matcher matcher = pattern.matcher(docText);
        Set<String> words = new HashSet<>();//добавление слов в Set как уникальные значения
        while (matcher.find()) {
            words.add(matcher.group());
        }
        Object lines[] = words.toArray();//Получение массива уникальных слов
        Page page = new Page();
        page.connectDataBase("words.db", true);//Подключение к бд
        page.insert(page.countWords(lines, docText));//Пишем полученный результат в бд
        page.select();//Получаем данные из бд
        page.close();
    }

    public static void createHtml(String html) throws IOException {
        String htmlPage = "htmlExamples\\exampleHtml_1";
        File file = new File(htmlPage);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(htmlPage);
        fileWriter.write(html);
        fileWriter.close();
    }
}
