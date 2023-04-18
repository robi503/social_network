package socialnetwork.domain;


public class User extends Entity<Integer> {
    private String nume;
    private String username;
    private String passsword;

    public User(String nume, String username, String passsword) {
        this.nume = nume;
        this.username = username;
        this.passsword = passsword;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getUserName() {
        return username;
    }

    public void setUserbame(String userName) {
        this.username = userName;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }

    @Override
    public String toString() {
        return "User{" +
                "nume='" + nume + '\'' +
                ", userName='" + username + '\'' +
                ", passsword='" + passsword + '\'' +
                '}';
    }
}
