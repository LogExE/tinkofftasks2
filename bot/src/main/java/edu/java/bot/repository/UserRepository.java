package edu.java.bot.repository;

import edu.java.bot.repository.dto.User;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    HashMap<Long, User> users;
    HashMap<String, HashSet<User>> whoTracksByLink;

    public UserRepository() {
        users = new HashMap<>();
        whoTracksByLink = new HashMap<>();
    }

    public void registerUser(long tgId) {
        if (!users.containsKey(tgId)) {
            users.put(tgId, new User(tgId));
        }
    }

    public boolean registered(long tgId) {
        return users.containsKey(tgId);
    }

    public Set<String> linksByUser(long tgId) {
        return users.containsKey(tgId) ? users.get(tgId).links() : null;
    }

    public boolean tracks(long tgId, String link) {
        if (!users.containsKey(tgId)) {
            return false;
        }
        return users.get(tgId).tracks(link);
    }

    public void addTrack(long tgId, String link) {
        if (users.containsKey(tgId)) {
            User user = users.get(tgId);
            user.addTrack(link);
        }
    }

    public void removeTrack(long tgId, String link) {
        if (users.containsKey(tgId)) {
            User user = users.get(tgId);
            user.removeTrack(link);
        }
    }
}
