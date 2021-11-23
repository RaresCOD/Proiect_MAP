package ro.ubbcluj.map.pb3.service;

import jdk.jshell.execution.Util;
import ro.ubbcluj.map.pb3.Conexitate.DFS;
import ro.ubbcluj.map.pb3.Message.Message;
import ro.ubbcluj.map.pb3.domain.Prietenie;
import ro.ubbcluj.map.pb3.domain.Tuple;
import ro.ubbcluj.map.pb3.domain.Utilizator;
import ro.ubbcluj.map.pb3.domain.validators.ValidationException;
import ro.ubbcluj.map.pb3.repository.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * service
 */
public class UtilizatorService {
    Repository<Long, Utilizator> repo;
    Repository<Tuple<Long, Long>, Prietenie> repoFriend;
    Repository<Long, Message> repoMessages;

    private void makeFriends() {
        for(Prietenie curent : repoFriend.findAll()) {
            Long left = curent.getId().getLeft();
            Long right = curent.getId().getRight();
            Utilizator util1 = repo.findOne(left);
            Utilizator util2 = repo.findOne(right);
            util1.addFriend(util2);
            util2.addFriend(util1);
//            System.out.println(util1.getFriends());
        }
        for(Utilizator curent:repo.findAll()){
            System.out.println(curent);
            System.out.println(curent.getFriends());
        }

    }

    private Long lastID() {
        Long lID = 0L;
        for(Utilizator util : repo.findAll()) {
            lID = util.getId();
        }
        return lID;
    }

    /**
     *
     * @param id1 - id ul primului prieten
     * @param id2 - id ul celui de al doilea prieten
     * @return - prietenia daca exista, altfel null
     */
    public Prietenie FindOneFriend(Long id1, Long id2) {
        return repoFriend.findOne(new Tuple(id1, id2));
    }

    /**
     *
     * @param repo user repo
     * @param repoFriend friendship repo
     */
    public UtilizatorService(Repository<Long, Utilizator> repo, Repository<Tuple<Long, Long>, Prietenie> repoFriend, Repository<Long, Message> repoMessages) {
        this.repo = repo;
        this.repoFriend = repoFriend;
        this.repoMessages = repoMessages;
//        makeFriends();
    }

    /**
     *
     * @param firstName fn
     * @param lastName ln
     * @return add user
     */
    public Utilizator addUtilizator(String firstName, String lastName) {
        Utilizator nou = new Utilizator(firstName, lastName);
        Long id = lastID() + 1L;
        nou.setId(id);
        try {
            Utilizator util = repo.save(nou);
            return util;
        } catch (IllegalArgumentException i) {
            System.out.println(i.getMessage());
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     *
     * @param id id
     * @return delete user
     */
    public Utilizator deleteUtilizator(Long id) {
        try {
            Utilizator utilizator = repo.findOne(id);
            if (utilizator == null) {
                System.out.println("Id inexistent");
                return null;
            }
            for(Utilizator util : repo.findAll()) {

                    util.deleteFriend(utilizator);
                    repoFriend.delete(new Tuple(util.getId(), utilizator.getId()));
                    repoFriend.delete(new Tuple(utilizator.getId(), util.getId()));

            }
            repo.delete(id);
            return utilizator;
        } catch (IllegalArgumentException i) {
            System.out.println(i.getMessage());
        }
        return null;
    }

    /**
     *
     * @param id id
     * @param firstName fn
     * @param lastName ln
     * @return update user
     */
    public Utilizator updateUtilizator(Long id, String firstName, String lastName) {
        Utilizator nou = new Utilizator(firstName, lastName);
        nou.setId(id);
        try {
            Utilizator util = repo.update(nou);
            if (util != null) {
                System.out.println("Utilizator inexistent");
            }
            return util;
        } catch (IllegalArgumentException i) {
            System.out.println(i.getMessage());
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     *
     * @param id1 user id
     * @param id2 user id
     */
    public void addFriend(Long id1, Long id2) {
        Prietenie prietenie = new Prietenie();
        Tuple<Long, Long> tuple = new Tuple(id1, id2);

        if (repo.findOne(id1) == null) {
            throw new ValidationException("User inexistent!");
        }
        if (repo.findOne(id2) == null) {
            throw new ValidationException("User inexistent!");
        }

        prietenie.setId(tuple);

        repoFriend.save(prietenie);
        Utilizator util1 = repo.findOne(id1);
        Utilizator util2 = repo.findOne(id2);
        util1.addFriend(util2);
        util2.addFriend(util1);
    }

    /**
     *
     * @param id1 user id
     * @param id2 user id
     */
    public void deleteFriend(Long id1, Long id2) {
        if (repo.findOne(id1) == null) {
            throw new ValidationException("User inexistent!");
        }
        if (repo.findOne(id2) == null) {
            throw new ValidationException("User inexistent!");
        }
        if (repoFriend.findOne(new Tuple(id1, id2)) == null) {
            throw new ValidationException("Prietenie inexistenta!");
        }
        Utilizator util1 = repo.findOne(id1);
        Utilizator util2 = repo.findOne(id2);
        util1.deleteFriend(util2);
        util2.deleteFriend(util1);
        repoFriend.delete(new Tuple(id1, id2));
    }

    /**
     *
     * @param id id
     * @return specific frineds
     */
    public List<Utilizator> getFriends(Long id)  {
        Utilizator util = repo.findOne(id);
        return util.getFriends();
    }

    /**
     *
     * @return all users
     */
    public Iterable<Utilizator> getAll() {
        return repo.findAll();
    }

    /**
     *
     * @return all friends
     */
    public Iterable<Prietenie> getAllFriends() {
        return repoFriend.findAll();
    }

    /**
     *
     * @return nb of communites
     */
    public int numarComunitati() {
        DFS dfs = new DFS(Math.toIntExact(lastID()), repoFriend.findAll(), repo.findAll());
        return dfs.execute1();
    }

    /**
     * largest community
     * @return int
     */
    public int JonnyVorbaretu() {
        DFS dfs = new DFS(Math.toIntExact(lastID()), repoFriend.findAll(), repo.findAll());
        return dfs.execute2();
    }

    private Long getUserId(String userName) {
        Iterable<Utilizator> list = repo.findAll();
        for (Utilizator curent: list) {
            if(curent.getFirstName().equals(userName)) {
                return curent.getId();
            }
        }
        return null;
    }

    public Long Login(String userName) {
        return getUserId(userName);
    }

    public void addMessage(Long id1, Long id2, String msg) {
        Utilizator from = repo.findOne(id1);
        Utilizator user2 = repo.findOne(id2);
        List<Utilizator> to = new ArrayList<Utilizator>();
        to.add(user2);
        Message message = new Message(from, to, msg);
        message.setReplyMsg(null);
        repoMessages.save(message);
    }

    public void addGroupMessage(Long id1, List<Long> Listid, String msg) {
        Utilizator from = repo.findOne(id1);
        List<Utilizator> to = new ArrayList<Utilizator>();
        Listid
                .stream()
                .forEach(x -> to.add(repo.findOne(x)));
//        Utilizator user2 = repo.findOne(id2);

//        to.add(user2);
        Message message = new Message(from, to, msg);
        message.setReplyMsg(null);
        repoMessages.save(message);
    }

    public void showAllMessagesForThisUser(Long userId) {
        Iterable<Message> all = repoMessages.findAll();
        for(Message curent:all) {
            List<Utilizator> list = curent.getTo();
            Boolean found = false;
            for(Utilizator to:list) {
                if(to.getId() == userId) {
                    found = true;
                }
            }
            if (found == true) {
                System.out.println(curent.getFrom().getFirstName() + " sent " + curent.getMsg() + " id: " + curent.getId());
            }
        }
    }

    public void showAllGroupChats(Long userId) {
        Iterable<Message> all = repoMessages.findAll();

        for (Message curent: all) {

        }
    }

    public boolean areFriends(Long id1, Long id2) {
        if (repoFriend.findOne(new Tuple<>(id1,id2)) == null && repoFriend.findOne(new Tuple<>(id2,id1)) == null) {
            return false;
        }
        return true;
    }


    public void sendReply(Long msgId, Long userId, String msg) {
        Utilizator from = repo.findOne(userId);
        Message message = repoMessages.findOne(msgId);
        List<Utilizator> to = new ArrayList<>();
        to.add(message.getFrom());
        for(Utilizator curent: message.getTo()) {
            if(curent.getId() != userId) {
                to.add(curent);
            }
        }
        Message newReply = new Message(from, to, msg);
        newReply.setReplyMsg(message);
        repoMessages.save(newReply);
    }

}
