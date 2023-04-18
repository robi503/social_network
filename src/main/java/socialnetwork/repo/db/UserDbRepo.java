package socialnetwork.repo.db;

import socialnetwork.domain.User;
import socialnetwork.repo.Repository;
import socialnetwork.validation.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
public class UserDbRepo implements Repository<Integer, User> {
    private String url;
    private String userName;
    private String parola;
    private Validator<User> validator;

    public UserDbRepo(String url, String userName, String parola, Validator<User> validator){
        this.url = url;
        this.userName = userName;
        this.parola = parola;
        this.validator = validator;
    }
    @Override
    public User findOne(Integer id) {
        try (Connection connection = DriverManager.getConnection(url,userName,parola);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE \"UserId\" = ?")){
            statement.setInt(1,id);
            try (ResultSet resultSet = statement.executeQuery()){
                if(!resultSet.next())
                    return null;
                Integer id1 = resultSet.getInt("UserId");
                String name = resultSet.getString("nume");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("password");
                User user_nou = new User(name,username,password);
                user_nou.setId(id1);
                return user_nou;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public User findOneByUsername(String userName) {
        try (Connection connection = DriverManager.getConnection(url,this.userName,parola);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE \"Username\" = ?")){
            statement.setString(1,userName);
            try (ResultSet resultSet = statement.executeQuery()){
                if(!resultSet.next())
                    return null;
                Integer id1 = resultSet.getInt("UserId");
                String name = resultSet.getString("nume");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("password");
                User user_nou = new User(name,username,password);
                user_nou.setId(id1);
                return user_nou;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<User> findAll() {
        Set<User> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url,userName,parola);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Integer id = resultSet.getInt("UserId");
                String name = resultSet.getString("nume");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("password");
                User user_nou = new User(name,username,password);
                user_nou.setId(id);
                users.add(user_nou);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User save(User entity) {
        String sql = "insert into users(\"Nume\",\"Username\",\"password\") values (?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, userName, parola);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getNume());
            ps.setString(2, entity.getUserName());
            ps.setString(3, entity.getPasssword());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public User delete(Integer id) {
        try (Connection connection = DriverManager.getConnection(url,userName,parola);
             PreparedStatement statement = connection.prepareStatement("delete from users  WHERE \"UserId\" = ?")){
            statement.setInt(1,id);
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }
}
