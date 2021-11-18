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
        BufferedReader cin = new BufferedReader(new InputStreamReader((System.in)));

//        Repository<Long, Utilizator> repo = new UtilizatorFile("data/users.csv", new UtilizatorValidator());
        Repository<Tuple<Long, Long>, Prietenie> repoFriend = new FriendshipFile("data/friendship.csv", new FriendshipValidator());

        Repository<Long, Utilizator> repoDb = new UtilizatorDbRepository("jdbc:postgresql://localhost:5432/Tema1", "postgres", "kokonel1002", new UtilizatorValidator());
        Repository<Tuple<Long, Long>, Prietenie> repoFDb = new FriendshipDbRepository("jdbc:postgresql://localhost:5432/Tema1", "postgres", "kokonel1002", new FriendshipValidator());
        Repository<Long, Message> repoMsgDb = new MessageDbRepository("jdbc:postgresql://localhost:5432/Tema1", "postgres", "kokonel1002", new MessageValidator());
        UtilizatorService service = new UtilizatorService(repoDb, repoFDb);

        Utilizator utilizator = new Utilizator("COCO","FLO");
        Utilizator utilizator1 = new Utilizator("Rares","Co");
        List<Utilizator> l = new ArrayList<Utilizator>();
        l.add(utilizator);
        repoMsgDb.save(new Message(utilizator1, l, "aia e"));

        boolean run = true;
        while (run) {
            System.out.println("1 - adauga Utilizator");
            System.out.println("2 - sterge Utilizator");
            System.out.println("3 - modifica Utilizator");
            System.out.println("4 - adauga prieteni");
            System.out.println("5 - sterge prieteni");
            System.out.println("6 - afiseaza prieteni");
            System.out.println("7 - gaseste o prietenie");
            System.out.println("8 - Numar comunitati");
            System.out.println("9 - cea mai sociabila comunitate");
            System.out.println("10 - exit");
            int cmd;
            try {
                cmd = Integer.parseInt(cin.readLine());
                Long id;
                String firstName, lastName;
                switch (cmd){
                    case 1:
                        System.out.println("Prenume: ");
                        firstName = cin.readLine();
                        System.out.println("Nume de familie: ");
                        lastName = cin.readLine();
                        service.addUtilizator(firstName, lastName);
                        break;
                    case 2:
                        System.out.println("Id: ");
                        String id3 = cin.readLine();
                        if (Pattern.matches("^[a-zA-Z]*$", id3)) {
                            System.out.println("Id invalid");
                            break;
                        }
                        id = Long.valueOf(id3);
                        service.deleteUtilizator(id);
                        break;
                    case 3:
                        System.out.println("Id: ");
                        id3 = cin.readLine();
                        if (Pattern.matches("^[a-zA-Z]*$", id3)) {
                            System.out.println("Id invalid");
                            break;
                        }
                        id = Long.valueOf(id3);
                        System.out.println("Prenume: ");
                        firstName = cin.readLine();
                        System.out.println("Nume de familie: ");
                        lastName = cin.readLine();
                        service.updateUtilizator(id, firstName, lastName);
                        break;
                    case 4:
                        Iterable<Utilizator> all = service.getAll();
                        System.out.println("Lista de utilizatori: ");
                        for (Utilizator curent: all) {
                            System.out.println(curent);
                        }
                        System.out.println("Adauga pritenie dupa id: ");
                        System.out.println("Id 1: ");
                        id3 = cin.readLine();
                        if (Pattern.matches("^[a-zA-Z]*$", id3)) {
                            System.out.println("Id invalid");
                            break;
                        }
                        Long id1 = Long.valueOf(id3);
                        System.out.println("Id 2: ");
                        id3 = cin.readLine();
                        if (Pattern.matches("^[a-zA-Z]*$", id3)) {
                            System.out.println("Id invalid");
                            break;
                        }
                        Long id2 = Long.valueOf(id3);
                        try {
                            service.addFriend(id1, id2);
                        } catch (ValidationException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        all = service.getAll();
                        System.out.println("Lista de utilizatori: ");
                        for (Utilizator curent: all) {
                            System.out.println(curent);
                        }
                        System.out.println("Sterge pritenie dupa id: ");
                        System.out.println("Id 1: ");
                        id3 = cin.readLine();
                        if (Pattern.matches("^[a-zA-Z]*$", id3)) {
                            System.out.println("Id invalid");
                            break;
                        }
                        id1 = Long.valueOf(id3);
                        System.out.println("Id 2: ");
                        id3 = cin.readLine();
                        if (Pattern.matches("^[a-zA-Z]*$", id3)) {
                            System.out.println("Id invalid");
                            break;
                        }
                        id2 = Long.valueOf(id3);
                        try {
                            service.deleteFriend(id1, id2);
                        } catch (ValidationException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 6:
                        for(Prietenie p: service.getAllFriends()) {
                            System.out.println(p);
                        }
                        break;
                    case 7:
                        all = service.getAll();
                        System.out.println("Lista de utilizatori: ");
                        for (Utilizator curent: all) {
                            System.out.println(curent);
                        }
                        System.out.println("Cauta pritenie dupa id: ");
                        System.out.println("Id 1: ");
                        id3 = cin.readLine();
                        if (Pattern.matches("^[a-zA-Z]*$", id3)) {
                            System.out.println("Id invalid");
                            break;
                        }
                        id1 = Long.valueOf(id3);
                        System.out.println("Id 2: ");
                        id3 = cin.readLine();
                        if (Pattern.matches("^[a-zA-Z]*$", id3)) {
                            System.out.println("Id invalid");
                            break;
                        }
                        id2 = Long.valueOf(id3);
                        try {
                            Prietenie prietenie = service.FindOneFriend(id1, id2);
                            if (prietenie != null) {
                                System.out.println(prietenie);
                            } else {
                                System.out.println("Prietenie inexistenta");
                            }
                        } catch (ValidationException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 8:
                        System.out.println(service.numarComunitati());
                        break;
                    case 9:
                        System.out.println(service.JonnyVorbaretu());
                        break;
                    case 10:
                        run = false;
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
