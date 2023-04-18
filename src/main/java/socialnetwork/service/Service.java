package socialnetwork.service;

import socialnetwork.domain.Friend;
import socialnetwork.domain.User;
import socialnetwork.repo.db.FriendDbRepo;
import socialnetwork.repo.db.UserDbRepo;
import socialnetwork.tools.Graph;
import socialnetwork.tools.Tuple;
import socialnetwork.validation.FriendValidator;
import socialnetwork.validation.UserValidator;
import socialnetwork.validation.exceptions.ValidationException;

import java.util.Collection;
import java.util.HashSet;

public class Service {
    protected FriendDbRepo friendRepo;
    protected UserDbRepo userRepo;

    /**
     *
     * @param friendValidator
     * validates Friend entities that need to be added
     * @param userValidator
     * validates User entities that need to be added
     */
    public Service(FriendValidator friendValidator, UserValidator userValidator)
    {
        friendRepo = new FriendDbRepo("jdbc:postgresql://localhost:5432/ReteaSocializareDb", "postgres","postgres", friendValidator);
        userRepo = new UserDbRepo("jdbc:postgresql://localhost:5432/ReteaSocializareDb", "postgres","postgres", userValidator);
    }

    /**
     * Adauga un utilizator in repo
     * @param nume
     * numele utilizatorului care trebuie adaugat
     */
    public void addUser(String nume, String username, String passsword)
    {
        User user = new User(nume, username, passsword);
        try {
            userRepo.save(user);
        }
        catch (ValidationException e)
        {
            System.out.println(e.toString());
        }
    }

    /**
     * Adauga o relatie de prietenie in repo
     * @param u1
     * primul user
     * @param u2
     * al doilea user
     */
    public void addFriend(User u1, User u2)
    {
        Friend friend = new Friend(u1,u2);
        try {
            Friend f = friendRepo.save(friend);
        }
        catch (ValidationException e)
        {
            System.out.println(e.toString());
            throw new ValidationException(e.toString());
        }
    }

    /**
     * Sterge un user din repo
     * @param id
     * id-ul utilizatorului care urmeaza sa fie sters
     * @return
     * utilizatorul care a fost sters
     */
    public User deleteUser(int id)
    {
        return userRepo.delete(id);
    }

    /**
     * Sterge o relatie de prietenie din repo
     * @param id
     * id-ul relatiei care urmeaza sa fie stearsa(de tipul (id1,id2))
     * @return
     * relatia care a fost stearsa
     */
    public Friend deleteFriend(Tuple<Integer> id)
    {

        return friendRepo.delete(id);
    }

    /**
     * Returneaza numarul de comunitati
     * @return
     * numarul de comunitati
     */

    public Iterable<Friend> findAllFriends(){
        return friendRepo.findAll();
    }

    public Iterable<Friend> findAllFriendsOfUser(Integer id){
        return friendRepo.findAllOfOne(id);
    }

    public Iterable<Friend> findAllRequests(Integer id){
        return friendRepo.findAllRequests(id);
    }

    public void handleFriendRequest(Tuple<Integer> id, Boolean accepted){
        if(friendRepo.findOne(id) == null)
        {
            id = new Tuple<>(id.getY(),id.getX());
        }
        Friend f = friendRepo.findOne(id);
        f.setAccepted(accepted);
        f.setPending(false);
        friendRepo.delete(id);
        friendRepo.save(f);

    }

    public User findUserByUsername(String username){
        return userRepo.findOneByUsername(username);
    }

    public int noOfCommunities()
    {
        Graph friendGraph = new Graph(friendRepo.findAll());
        return friendGraph.connectedComponents().size();
    }

    /**
     * Returneaza comunitatea cu cei mai multi membrii
     * @return
     * comunitatea cu cei mai multi membrii
     */
    public Collection<Integer> biggestCommunity()
    {
        Graph friendGraph = new Graph(friendRepo.findAll());
        int maxSize=0;
        Collection<Integer> maxCommunity = new HashSet<>();
        for(Collection<Integer> collection : friendGraph.connectedComponents())
        {
            if(collection.size() > maxSize)
            {
                maxSize = collection.size();
            }
        }

        for(Collection<Integer> collection : friendGraph.connectedComponents())
        {
            if(collection.size() == maxSize)
            {
                maxCommunity = collection;
            }
        }
        return maxCommunity;
    }
}
