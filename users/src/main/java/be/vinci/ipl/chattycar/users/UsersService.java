package be.vinci.ipl.chattycar.users;

import be.vinci.ipl.chattycar.users.data.UsersRepository;
import be.vinci.ipl.chattycar.users.models.NewUser;
import be.vinci.ipl.chattycar.users.models.User;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository repository;

    public UsersService(UsersRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a user
     * @param newUser User to create
     * @return true if the user could be created, false if another user exists with this email
     */
    public User createOne(NewUser newUser) {
        if (repository.existsByEmail(newUser.getEmail())) return null;
        User user = new User();
        user.setEmail(newUser.getEmail());
        user.setFirstname(newUser.getFirstname());
        user.setLastname(newUser.getLastname());
        return repository.save(user);
    }

    /**
     * return the user with the specified id
     * @param id id of the user
     * @return The user found, or null if the user couldn't be found
     */
    public User getOne(int id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * return the user with the specified email
     * @param email the email of the user
     * @return The user found, or null if the user couldn't be found
     */
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    /**
     * Updates a user
     * @param user User to update
     * @return True if the user could be updated, false if the user couldn't be found
     */
    public boolean updateOne(User user) {
        if (!repository.existsById(user.getId())) return false;
        repository.save(user);
        return true;
    }

    /**
     * Deletes a user
     * @param id The id of the user
     * @return True if the user could be deleted, false if the user couldn't be found
     */
    public boolean deleteOne(int id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

}
