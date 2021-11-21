package ro.ubbcluj.map.pb3.domain;

import java.sql.Date;

/**
 * friendship class
 */
public class Prietenie extends Entity<Tuple<Long,Long>> {

    /**
     * date
     */
    Date date;

    /**
     * constructor
     */
    public Prietenie() {}

    @Override
    public String toString() {
        return "Prietenie{" +
                "P1 = " + this.getId().getLeft() + ", P2 = " + this.getId().getRight() + ", date = " + date.toString() +
                '}';
    }

    /**
     *
     * @return the date of the friendship
     */
    public Date getDate() {
        return date;
    }


    /**
     *
     * @param date the date of the friendship to be set
     */
    public void setDate(Date date) { this.date = date; }
}
