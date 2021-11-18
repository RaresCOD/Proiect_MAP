package ro.ubbcluj.map.pb3.Message;

import ro.ubbcluj.map.pb3.domain.Utilizator;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyMessage extends Message {
    private String rplyMsg;

    public ReplyMessage(Utilizator from, List<Utilizator> to, String msg) {
        super(from, to, msg);
    }

    @Override
    public String toString() {
        return "ReplyMessage{" +
                "rplyMsg='" + rplyMsg + '\'' +
                '}';
    }
}
