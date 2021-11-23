package ro.ubbcluj.map;


import ro.ubbcluj.map.pb3.Message.Message;
import ro.ubbcluj.map.pb3.domain.Prietenie;
import ro.ubbcluj.map.pb3.domain.Tuple;
import ro.ubbcluj.map.pb3.domain.Utilizator;
import ro.ubbcluj.map.pb3.domain.validators.FriendshipValidator;
import ro.ubbcluj.map.pb3.domain.validators.MessageValidator;
import ro.ubbcluj.map.pb3.domain.validators.UtilizatorValidator;
import ro.ubbcluj.map.pb3.domain.validators.ValidationException;
import ro.ubbcluj.map.pb3.repository.Repository;
import ro.ubbcluj.map.pb3.repository.db.FriendshipDbRepository;
import ro.ubbcluj.map.pb3.repository.db.MessageDbRepository;
import ro.ubbcluj.map.pb3.repository.db.UtilizatorDbRepository;
import ro.ubbcluj.map.pb3.repository.file.FriendshipFile;
import ro.ubbcluj.map.pb3.repository.file.UtilizatorFile;
import ro.ubbcluj.map.pb3.service.UtilizatorService;
import ro.ubbcluj.map.pb3.ui.Ui;

import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

/**
 * main
 */
public class Main {
    /**
     *
     * @param args arg cmd line
     */
    public static void main(String[] args) {
        Repository<Long, Utilizator> repoDb = new UtilizatorDbRepository("jdbc:postgresql://localhost:5432/Tema1", "postgres", "kokonel1002", new UtilizatorValidator());
        Repository<Tuple<Long, Long>, Prietenie> repoFDb = new FriendshipDbRepository("jdbc:postgresql://localhost:5432/Tema1", "postgres", "kokonel1002", new FriendshipValidator());
        Repository<Long, Message> repoMsgDb = new MessageDbRepository("jdbc:postgresql://localhost:5432/Tema1", "postgres", "kokonel1002", new MessageValidator());
        UtilizatorService service = new UtilizatorService(repoDb, repoFDb, repoMsgDb);

        boolean run = true;
        while (run) {
            Ui ui = new Ui(run, service);
            ui.menu();
        }
    }
}
