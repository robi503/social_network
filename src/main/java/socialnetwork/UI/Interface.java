package socialnetwork.UI;

import socialnetwork.domain.Friend;
import socialnetwork.domain.User;
import socialnetwork.service.Service;
import socialnetwork.tools.Tuple;
import socialnetwork.validation.FriendValidator;
import socialnetwork.validation.UserValidator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Interface extends Service {
    private Scanner scanner = new Scanner(System.in);

    public Interface(FriendValidator friendValidator, UserValidator userValidator) {
        super(friendValidator, userValidator);
    }

    public void menu()
    {
        System.out.println("1.Adauga user");
        System.out.println("2.Adauga prieten");
        System.out.println("3.Sterge user");
        System.out.println("4.Sterge prieten");
        System.out.println("5.Afisare toti utlizatorii");
        System.out.println("6.Afisare toate relatiile de prietenie");
        System.out.println("7.Afisare nr de comunitati");
        System.out.println("8.Afisare cea mai mare comunitate");
        System.out.println("9.Gaseste user dupa username");
        System.out.println("0.Inchide");
    }

    public void printAllUsers()
    {
        for(User u : super.userRepo.findAll())
        {
            System.out.println(u);
        }
    }

    public void printAllFriends()
    {
        for(Friend f : super.friendRepo.findAll())
        {
            System.out.println(f);
        }
    }

    public void addUser()
    {
        System.out.print("Introduceti numele: ");
        String name = scanner.nextLine();
        System.out.print("Introduceti username: ");
        String username = scanner.nextLine();
        System.out.print("Introduceti parola: ");
        String password = scanner.nextLine();
        super.addUser(name,username,password);
    }

    public void addFriend()
    {
        printAllUsers();
        try {
            System.out.print("Id1: ");
            Integer id1 = scanner.nextInt();
            User user1 = super.userRepo.findOne(id1);
            System.out.print("Id2: ");
            Integer id2 = scanner.nextInt();
            User user2 = super.userRepo.findOne(id2);
            super.addFriend(user1, user2);
        }
        catch (InputMismatchException e)
        {
            System.out.println("Id-ul introdus nu este de tipul corect");
        }
        catch (NullPointerException e)
        {
            System.out.println("Nu exista acest ID");
        }
    }

    public void deleteUser()
    {
        printAllUsers();
        try {
            System.out.print("Id utilizatorului: ");
            int id = scanner.nextInt();
            super.deleteUser(id);
        }
        catch (InputMismatchException e)
        {
            System.out.println("Id-ul introdus nu este de tipul corect");
        }
    }

    public void deleteFriend()
    {
        printAllFriends();
        try {
            System.out.print("Id1: ");
            int id1 = scanner.nextInt();
            System.out.print("Id2: ");
            int id2 = scanner.nextInt();
            Tuple<Integer> id = new Tuple<>(id1, id2);
            super.deleteFriend(id);
        }
        catch (InputMismatchException e)
        {
            System.out.println("Id-ul introdus nu este de tipul corect");
        }
    }

    public void findUserByUsername()
    {
        System.out.print("Username: ");
        String un = scanner.next();
        System.out.println(super.findUserByUsername(un));
    }

    public void numberOfCommunities()
    {
        System.out.println(super.noOfCommunities());
    }

    public void bigCommunity()
    {
        System.out.println(super.biggestCommunity());
    }

    public void run() {
        String option;
        do {
            menu();
            option = scanner.nextLine();
            switch (option) {
                case "1" -> addUser();
                case "2" -> addFriend();
                case "3" -> deleteUser();
                case "4" -> deleteFriend();
                case "5" -> printAllUsers();
                case "6" -> printAllFriends();
                case "7" -> numberOfCommunities();
                case "8" -> bigCommunity();
                case "9" -> findUserByUsername();
            }
        } while (!option.equals("0")); // quitting the program
    }
}
