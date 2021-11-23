package ro.ubbcluj.map.pb3.ui;

import ro.ubbcluj.map.pb3.Message.Message;
import ro.ubbcluj.map.pb3.domain.Tuple;
import ro.ubbcluj.map.pb3.domain.Utilizator;
import ro.ubbcluj.map.pb3.service.UtilizatorService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
                System.out.println("3 - Reply to a message");
                System.out.println("4 - Show all friends");
                System.out.println("0 - Logout");
                String cmd = cin.readLine();
                if (Pattern.matches("^[a-zA-Z]*$", cmd)) {
                    System.out.println("Invalid input");
                } else {
                    int cmdNou = Integer.parseInt(cmd);
                    switch (cmdNou){
                        case 1:
                            System.out.println("Choose a friend to chat with:");
                            String friendUserName = cin.readLine();
                            Long friendUserId = service.Login(friendUserName);
                            if(service.areFriends(userId, friendUserId) == false) {
                                System.out.println(friendUserName + " is not a friend");
                                break;
                            }
                            if (friendUserId != null) {
                                System.out.println("Message:");
                                String msg = cin.readLine();
                                service.addMessage(userId, friendUserId, msg);
                            }
                            break;
                        case 2:
                            List<Long> Listid = new ArrayList<>();
                            while (true) {
                                System.out.println("1-Add a friend:");
                                System.out.println("0-Exit");
                                String cmd11 = cin.readLine();
                                if (Pattern.matches("^[a-zA-Z]*$", cmd11)) {
                                    System.out.println("Invalid input");
                                } else {
                                    int cmd1 = Integer.parseInt(cmd11);
                                    if(cmd1 == 0) {
                                        break;
                                    } else {
                                        String friendUserName1 = cin.readLine();
                                        Long friendUserId1 = service.Login(friendUserName1);

                                        if (friendUserId1 == null) {
                                            System.out.println("Invalid User");
                                        } else {
                                            if(service.areFriends(userId, friendUserId1) == false) {
                                                System.out.println(friendUserName1 + " is not a friend");
                                            } else {
                                                Listid.add(friendUserId1);
                                            }
                                        }
                                    }
                                }
                            }
                            System.out.println("Message:");
                            String msg = cin.readLine();
                            service.addGroupMessage(userId,Listid, msg);
                            break;
                        case 3:
                            System.out.println("Select the message and user you want to reply to\nId:");
                            String msgId1 = cin.readLine();
                            if (Pattern.matches("^[a-zA-Z]*$", msgId1)) {
                                System.out.println("Invalid input");
                            } else {
                                Long msgId = Long.parseLong(msgId1);
                                System.out.println("Message");
                                String msg1 = cin.readLine();
                                service.sendReply(msgId, userId, msg1);
                            }
                            break;
                        case 4:
                            System.out.println("Friends list: ");
                            for(Tuple<Utilizator, Date> curent: service.getFriends(userId)) {
                                System.out.println(curent.getLeft().getFirstName() + " | " + curent.getLeft().getLastName() + " | " + curent.getRight());
                            }
                            break;
                        case 0:
                            run = false;
                            break;
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
