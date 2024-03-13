package notebook.controller;

import notebook.model.User;
import notebook.model.repository.GBRepository;
import notebook.util.UserValidator;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserController {
    private final GBRepository repository;

    public UserController(GBRepository repository) {
        this.repository = repository;
    }

    public void saveUser(User user) {
        repository.create(user);
    }

    public User readUser(Long userId) throws Exception {
        List<User> users = repository.findAll();
        for (User user : users) {
            if (Objects.equals(user.getId(), userId)) {
                return user;
            }
        }

        throw new RuntimeException("User not found");
    }

    public List<User> readAll(){
        return repository.findAll();
    }

    public void updateUser(String userId, User update) {
        update.setId(Long.parseLong(userId));
        repository.update(Long.parseLong(userId), update);
    }

    public void deleteUser(String id){
        if(repository.delete(Long.parseLong(id))){
            System.out.println("Пользователь под номером " + id + " удалён");
        }
    }

    public User createUser() {
        String firstName = promptController("Имя: ");
        String lastName = promptController("Фамилия: ");
        String phone = promptController("Номер телефона: ");

        UserValidator validator = new UserValidator();
        return validator.validate(new User(firstName,lastName,phone));
    }

    private String promptController(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }
}
