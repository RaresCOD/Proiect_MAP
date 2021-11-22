package ro.ubbcluj.map.pb3.ui;

import ro.ubbcluj.map.pb3.Message.Message;
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

    private void allChats() {

    }

    public UiUsers(Long userId, UtilizatorService service) {
        this.userId = userId;
        this.service = service;
    }

    public void menu() {
        try {
            Boolean run = true;
            while (run == true) {
                System.out.println("Recived Messages:");
                service.showAllMessagesForThisUser(userId);
                System.out.println("////////////////");
                System.out.println("1 - Send a private message");
                System.out.println("2 - Send Group message");
//                System.out.println("3 - Group chat");
                System.out.println("4 - Reply to a message");
                System.out.println("0 - Logout");
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

                        List<Long> Listid = new ArrayList<>();
                        while (true) {
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
                        System.out.println("Group Chats:");

                        break;
                    case 4:
                        System.out.println("Select the message and user you want to reply to\nId:");
                        Long msgId = Long.valueOf(cin.readLine());
                        System.out.println("Message");
                        String msg1 = cin.readLine();
                        service.sendReply(msgId, userId, msg1);
                        break;
                    case 0:
                        run = false;
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
