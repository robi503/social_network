package socialnetwork.domain;


import java.io.Serializable;

public class Entity<ID> implements Serializable {
    private ID id;

    public void setId(ID ID) {
        this.id = ID;
    }

    public ID getId() {
        return this.id;
    }
}
