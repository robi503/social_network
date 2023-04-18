package socialnetwork.domain;


import socialnetwork.tools.Tuple;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Extinde clasa entity, avand un ID de forma (id1, id2)
 */
public class Friend extends Entity<Tuple<Integer>>{

    private Integer id1;
    private Integer id2;
    private LocalDateTime friendsFrom;

    private Boolean pending;

    private Boolean accepted;

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }


    public Integer getId1() {
        return id1;
    }

    public Integer getId2() {
        return id2;
    }

    public void setId2(Integer id2) {
        this.id2 = id2;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return "Friend{" +
                "id1=" + id1 +
                ", id2=" + id2 +
                ", friends from " + friendsFrom.format(format)+
                ", pending" + pending +
                ", accepted" + accepted +
                '}';
    }

    /**
     * Creeaza o relatie de prietenie intre 2 utilizatori
     * Id-ul relatiei va fi format din id-urile celor 2 si va fi un tuplu de forma(id1, id2)
     * @param u1
     * primul utilizator
     * @param u2
     * al doilea utilizator
     */
    public Friend(User u1, User u2)
    {
        this.id1 = u1.getId();
        this.id2 = u2.getId();
        Tuple<Integer> idFriend= new Tuple<>(u1.getId(),u2.getId());
        this.friendsFrom = LocalDateTime.now();
        this.pending = true;
        this.accepted = false;
        super.setId(idFriend);
    }

    public Friend(Integer id1, Integer id2)
    {
        this.id1 = id1;
        this.id2 = id2;
        Tuple<Integer> idFriend= new Tuple<>(id1,id2);
        this.friendsFrom = LocalDateTime.now();
        this.pending = true;
        this.accepted = false;
        super.setId(idFriend);
    }
}
