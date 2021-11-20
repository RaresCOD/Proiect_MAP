package ro.ubbcluj.map.pb3.ui;

import ro.ubbcluj.map.pb3.domain.Utilizator;
import ro.ubbcluj.map.pb3.service.UtilizatorService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UiUsers {
    BufferedReader cin = new BufferedReader(new InputStreamReader((System.in)));
    private Long userId;
    UtilizatorService service;

    public UiUsers(Long userId, UtilizatorService service) {
        this.userId = userId;
        this.service = service;
    }

    public void menu() {
        System.out.println("1 - Send a private message");
        System.out.println("2 - Create Group chat");
        System.out.println("3 - Reply to a message");
        System.out.println("0 - Logout");
        try {
            int cmd = Integer.parseInt(cin.readLine());
            switch (cmd){
                case 1:
                    System.out.println("Friends list: ");
                    for(Utilizator curent: service.getFriends(userId)) {
                        System.out.println(curent.getFirstName());
                    }
                    System.out.println("Choose a friend to chat with:");
                    String friendUserName = cin.readLine();
                    Long friendUserId = service.Login(friendUserName);
                    if (friendUserId != null) {
                        System.out.println("Message:");
                        String msg = cin.readLine();
                        service.addMessage(userId, friendUserId, msg);
                    }
                    break;
                case 2:
                    System.out.println("Friends list: ");
                    for(Utilizator curent: service.getFriends(userId)) {
                        System.out.println(curent.getFirstName());
                    }
                    Boolean run = true;
                    List<Long> Listid = new ArrayList<>();
                    while (run == true) {
                        int cmd1;
                        System.out.println("1-Add a friend:");
                        System.out.println("0-Exit");
                        cmd1 = Integer.parseInt(cin.readLine());
                        if(cmd1 == 0) {
                            break;
                        } else {
                            String friendUserName1 = cin.readLine();
                            Long friendUserId1 = service.Login(friendUserName1);

                            if (friendUserId1 == null) {
                                System.out.println("Invalid User");
                            } else {
                                Listid.add(friendUserId1);
                            }
                        }
                    }
//                    if (friendUserId != null) {
                    System.out.println("Message:");
                    String msg = cin.readLine();
                    service.addGroupMessage(userId,Listid, msg);

                    break;
                case 3:

                    break;
                case 0:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
