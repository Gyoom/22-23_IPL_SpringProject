package be.vinci.ipl.catflix.users;

import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository repository;

    public UsersService(UsersRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a user
     * @param user User to create
     * @return true if the user could be created, false if another user exists with this pseudo
     */
    public boolean createOne(User user) {
        if (repository.existsById(user.getPseudo())) return false;
        repository.save(user);
        return true;
    }

    /**
     * Reads a user
     * @param pseudo Pseudo of the user
     * @return The user found, or null if the user couldn't be found
     */
    public User readOne(String pseudo) {
        return repository.findById(pseudo).orElse(null);
    }

    /**
     * Updates a user
     * @param user User to update
     * @return True if the user could be updated, false if the user couldn't be found
     */
    public boolean updateOne(User user) {
        if (!repository.existsById(user.getPseudo())) return false;
        repository.save(user);
        return true;
    }

    /**
     * Deletes a user
     * @param pseudo Pseudo of the user
     * @return True if the user could be deleted, false if the user couldn't be found
     */
    public boolean deleteOne(String pseudo) {
        if (!repository.existsById(pseudo)) return false;
        repository.deleteById(pseudo);
        return true;
    }

}
