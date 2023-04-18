package socialnetwork.repo.db;

import socialnetwork.domain.Friend;
import socialnetwork.repo.Repository;
import socialnetwork.tools.Tuple;
import socialnetwork.validation.Validator;
import socialnetwork.validation.exceptions.ValidationException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class FriendDbRepo implements Repository<Tuple<Integer>, Friend> {
    private String url;
    private String userName;
    private String parola;
    private Validator<Friend> validator;

    public FriendDbRepo(String url, String userName, String parola, Validator<Friend> validator){
        this.url = url;
        this.userName = userName;
        this.parola = parola;
        this.validator = validator;
    }
    @Override
    public Friend findOne(Tuple<Integer> friendId) {
        try (Connection connection = DriverManager.getConnection(url,userName,parola);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM friends WHERE \"id1\" = ? and \"id2\" = ?")){
            statement.setInt(1,friendId.getX());
            statement.setInt(2,friendId.getY());
            try (ResultSet resultSet = statement.executeQuery()){
                if(!resultSet.next())
                    return null;
                Integer id1 = resultSet.getInt("id1");
                Integer id2 = resultSet.getInt("id2");
                LocalDateTime friendsFrom = resultSet.getTimestamp("friendsFrom").toLocalDateTime();
                Boolean pending = resultSet.getBoolean("pending");
                Boolean accepted = resultSet.getBoolean("accepted");
                Friend f = new Friend(id1, id2);
                f.setFriendsFrom(friendsFrom);
                f.setPending(pending);
                f.setAccepted(accepted);
                return f;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Friend> findAll() {
        Set<Friend> friends = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url,userName,parola);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM friends");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Integer id1 = resultSet.getInt("id1");
                Integer id2 = resultSet.getInt("id2");
                LocalDateTime friendsFrom = resultSet.getTimestamp("friendsFrom").toLocalDateTime();
                Boolean pending = resultSet.getBoolean("pending");
                Boolean accepted = resultSet.getBoolean("accepted");
                Friend f = new Friend(id1, id2);
                f.setFriendsFrom(friendsFrom);
                f.setPending(pending);
                f.setAccepted(accepted);
                friends.add(f);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return friends;
    }

    public Iterable<Friend> findAllOfOne(Integer id) {
        Set<Friend> friends = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url,userName,parola);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM friends WHERE (\"id1\" = ? or \"id2\" = ?) and \"pending\"=false")){
            statement.setInt(1,id);
            statement.setInt(2,id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer id1 = resultSet.getInt("id1");
                    Integer id2 = resultSet.getInt("id2");
                    LocalDateTime friendsFrom = resultSet.getTimestamp("friendsFrom").toLocalDateTime();
                    Boolean pending = resultSet.getBoolean("pending");
                    Boolean accepted = resultSet.getBoolean("accepted");
                    Friend f = new Friend(id1, id2);
                    f.setFriendsFrom(friendsFrom);
                    f.setPending(pending);
                    f.setAccepted(accepted);
                    friends.add(f);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return friends;
    }

    public Iterable<Friend> findAllRequests(Integer id) {
        Set<Friend> friends = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url,userName,parola);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM friends WHERE \"id2\" = ? and \"pending\"=true")){
            statement.setInt(1,id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer id1 = resultSet.getInt("id1");
                    Integer id2 = resultSet.getInt("id2");
                    LocalDateTime friendsFrom = resultSet.getTimestamp("friendsFrom").toLocalDateTime();
                    Boolean pending = resultSet.getBoolean("pending");
                    Boolean accepted = resultSet.getBoolean("accepted");
                    Friend f = new Friend(id1, id2);
                    f.setFriendsFrom(friendsFrom);
                    f.setPending(pending);
                    f.setAccepted(accepted);
                    friends.add(f);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return friends;
    }

    @Override
    public Friend save(Friend entity) {
        String sql = "insert into friends values (?,?,?,?,?)";
        validator.validate(entity);
        if(findOne(new Tuple<>(entity.getId2(), entity.getId1())) != null){
            throw new ValidationException("Cei doi useri sunt deja prieteni");
        }
        if(findOne(new Tuple<>(entity.getId1(), entity.getId2())) != null){
            throw new ValidationException("Cei doi useri sunt deja prieteni");
        }
        try (Connection connection = DriverManager.getConnection(url, userName, parola);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getId1());
            ps.setInt(2, entity.getId2());
            ps.setTimestamp(3, Timestamp.valueOf(entity.getFriendsFrom()));
            ps.setBoolean(4,entity.getPending());
            ps.setBoolean(5,entity.getAccepted());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public Friend delete(Tuple<Integer> friendId) {
        Friend f = findOne(friendId);
        if(f == null){
            friendId = new Tuple<>(friendId.getY(), friendId.getX());
            f = findOne(friendId);
        }
        try (Connection connection = DriverManager.getConnection(url,userName,parola);
             PreparedStatement statement = connection.prepareStatement("delete from friends WHERE \"id1\" = ? and \"id2\" = ?")){
            statement.setInt(1,friendId.getX());
            statement.setInt(2,friendId.getY());
            statement.executeUpdate();
            return f;
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Friend update(Friend entity) {
        return null;
    }
}
