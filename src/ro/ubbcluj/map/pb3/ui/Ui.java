package ro.ubbcluj.map.pb3.ui;

import ro.ubbcluj.map.pb3.domain.Prietenie;
import ro.ubbcluj.map.pb3.domain.Utilizator;
import ro.ubbcluj.map.pb3.domain.validators.ValidationException;
import ro.ubbcluj.map.pb3.service.UtilizatorService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Provider;
import java.util.regex.Pattern;

public class Ui {

    private boolean run;
    private UtilizatorService service;

    public Ui(boolean run, UtilizatorService service) {
        this.run = run;
        this.service = service;
    }

    BufferedReader cin = new BufferedReader(new InputStreamReader((System.in)));

    public void menu() {
        System.out.println("1 - adauga Utilizator");
        System.out.println("2 - sterge Utilizator");
        System.out.println("3 - modifica Utilizator");
        System.out.println("4 - adauga prieteni");
        System.out.println("5 - sterge prieteni");
        System.out.println("6 - afiseaza prieteni");
        System.out.println("7 - gaseste o prietenie");
        System.out.println("8 - Numar comunitati");
        System.out.println("9 - cea mai sociabila comunitate");
        System.out.println("10 - login");
        System.out.println("0 - exit");
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
                    System.out.println("Login: ");
                    String username = cin.readLine();
                    Long id4 = service.Login(username);
                    System.out.println(id4);
                    if( id4 != null) {
                        UiUsers uiUsers = new UiUsers(id4, service);
                        uiUsers.menu();
                    } else {
                        System.out.println("User unregistered");
                    }
                    break;
                case 0:
                    run = false;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
