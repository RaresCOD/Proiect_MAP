package ro.ubbcluj.map.pb3.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User class
 */
public class Utilizator extends Entity<Long>{
    /**
     * firstname
     */
    private String firstName;
    /**
     * lastname
     */
    private String lastName;
    /**
     * friends
     */
    private List<Utilizator> friends = new ArrayList<Utilizator>();

    /**
     * constructor
     * @param firstName fn
     * @param lastName ln
     */
    public Utilizator(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setter
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getter
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setter
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return all friends
     */
    public List<Utilizator> getFriends() {
        return friends;
    }

    /**
     *
     * @param e user
     */
    public void addFriend(Utilizator e) {
        this.friends.add(e);
    }

    /**
     *
     * @param e user
     */
    public void deleteFriend(Utilizator e) {
        friends.remove(e);
    }

    @Override
    public String toString() {
        return getId() + " " + firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getFriends().equals(that.getFriends());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getFriends());
    }
}