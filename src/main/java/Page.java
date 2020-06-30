import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Page {

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private java.sql.Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public Map<String, Integer> countWords(Object lines[], String line) {
        HashMap<String, Integer> wordCard = new HashMap<>();//в Map будем писать слова и их количество(ключ-слово, значение-количество слов на странице)
        Pattern pattern;
        Matcher matcher;
        for (int i = 0; i < lines.length; i++) {
            int countWord = 0;
            pattern = Pattern.compile("(?<=\\W)" + lines[i] + "(?=\\W)", Pattern.UNICODE_CHARACTER_CLASS);
            matcher = pattern.matcher(line);
            while (matcher.find()) {
                wordCard.put(matcher.group(), ++countWord);
            }
            matcher.reset();
        }
        return wordCard;
    }

    public void connectDataBase(String dbUrl, boolean showConnectStatus) {//Подключение к бд
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + dbUrl);
            if (showConnectStatus) System.out.println("Connected to data base");
        } catch (ClassNotFoundException | SQLException e) {
            if (showConnectStatus) System.out.println(e.getMessage());
        }
    }

    public void close() {//закрытие бд
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insert(Map<String, Integer> map){//удаление старых данных и добавление новых данных
        Set<String> wordsSet = map.keySet();//Set уникальных слов
        List<Integer> wordsCount = new ArrayList<>();//List значений(кол-во слов)
        Iterator<String> iterator = wordsSet.iterator();//Обход по Set для добавления в List значений
        while (iterator.hasNext()) {
            wordsCount.add(map.get(iterator.next()));
        }
        int index = 0;
        String query;
        Statement statement = null;
        try {
            statement = connection.createStatement();

        String queryDelete = "DELETE FROM words";
        System.out.println("Data base cleared");
        statement.executeUpdate(queryDelete);
        for (String word :
                wordsSet) {
            query =
                    "INSERT INTO words (word, count)" +
                            "VALUES ('" + word + "', " + wordsCount.get(index++) + ");";
            statement.executeUpdate(query);
        }
        statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Update complete");
    }

    public void select() {//Получение данных из бд
        try {
            Statement statement = connection.createStatement();
            String querySelect = "SELECT word, count FROM words";
            ResultSet resultSet = statement.executeQuery(querySelect);
            while (resultSet.next()) {
                System.out.println(String.format("%-25s<=|=>%4d",
                        resultSet.getString("word"),
                        resultSet.getInt("count")));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Data recieved");
    }
}
