package ro.ubbcluj.map.pb3.domain;

import java.time.LocalDateTime;

/**
 * friendship class
 */
public class Prietenie extends Entity<Tuple<Long,Long>> {

    /**
     * date
     */
    LocalDateTime date;

    /**
     * constructor
     */
    public Prietenie() {
        this.date = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "P1 = " + this.getId().getLeft() + ", P2 = " + this.getId().getRight()+
                '}';
    }

    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate() {
        return date;
    }
}
