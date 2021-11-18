package ro.ubbcluj.map.pb3.Message;

import ro.ubbcluj.map.pb3.domain.Entity;
import ro.ubbcluj.map.pb3.domain.Utilizator;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Message extends Entity<Long> {
    private Utilizator from;
    private List<Utilizator> to;
    private LocalDateTime data;

    public Message(Utilizator from, List<Utilizator> to, LocalDateTime data) {
        this.from = from;
        this.to = to;
        this.data = data;
    }

    public Utilizator getFrom() {
        return from;
    }

    public List<Utilizator> getTo() {
        return to;
    }

    public void setFrom(Utilizator from) {
        this.from = from;
    }

    public void setTo(List<Utilizator> to) {
        this.to = to;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public LocalDateTime getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from +
                ", to=" + to +
                ", data=" + data +
                '}';
    }
}
