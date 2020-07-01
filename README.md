# UniversalParser
Project for SimbirSoft<br/>
This project represents an algorithm of parsing web pages by Http request 
on the internet. To parse HTML pages you can copy *URL* from your web browser and paste it into specific place of program code. If you want you can download *HTML*
code from web page and save it to computer memory. Then the algorithm
will find all the words on the page that the user sees and count the number
of repetitions of each unique word on a web page. Also in this project a database is implemented for storing and retrieving data from it.

Technologies that were used in this project : <br/>

- Java 8
- Maven
- Git

Connected libraries : <br/>

- **[JSoup Java HTML Parser](https://mvnrepository.com/artifact/org.jsoup/jsoup)**
- **[SQLite JDBC](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc)**

Functional descript : 

- main class : **[Main.java](./src/main/java/Main.java)**
- the class **[Page.java](./src/main/java/Page.java)** contains the basic methods of working with the database and processing the received unique words and their number 
- **[Main.java](./src/main/java/Main.java)** contains private fields that contain constants for testing program such as<br/>
    ```
    private final static String EXAMPLE_1 = "https://www.simbirsoft.com/";
    private final static String EXAMPLE_2 = "http://yandex.com";
    private final static String EXAMPLE_3 = "https://jsoup.org/cookbook/extracting-data/selector-syntax";
    ```          
  
    for parsing *HTML* page from *URL* and 
    ```
    private final static String EXAMPLE_1_PATH = "htmlExamples\\exampleHtml_1";
    private final static String EXAMPLE_2_PATH = "htmlExamples\\exampleHtml_2";
    private final static String EXAMPLE_3_PATH = "htmlExamples\\exampleHtml_3";
    ```
    for parsing *HTML* page from the file
- You can save your *HTML* page you downloaded into file using method <br/>
    `public static void createHtml(String html)`
- **[Downloaded *HTML* pages](./htmlExamples)**<br/>

Project Testing Steps : 

1. Go to **[Main.java](./src/main/java/Main.java)** and find : <br/>
    `Document document = Jsoup.parse(new File(EXAMPLE_1_PATH), "UTF-8");`(line 31).<br/>
    You can substitute your path to the file instead of the *EXAMPLE_1_PATH*, 
    which is intended for testing already downloaded *HTML* page. If you want parse 
    *HTML* page by using *URL* you should comment previous line and uncomment these lines :<br/>
    ```
   Connection connection = Jsoup.connect(EXAMPLE_1);
   connection.userAgent("Chrome");
   connection.timeout(5000);
   Document document = connection.get();
   ```
   (lines 26-29) where you can substitute *URL* you want instead of *EXAMPLE_1*.  
2. Run program

Project made by Roman Grigoriev<br/>
Contacts : **roma.grigorev.2015@inbox.ru**
