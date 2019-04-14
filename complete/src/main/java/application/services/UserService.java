package application.services;

import application.model.User;
import application.repository.RoleRepository;
import application.repository.UserRepository;
import application.security.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public MyUserPrincipal createNewUser(String name, String password) throws DataAccessException {
        Optional<User> u = userRepository.findByName(name);
        if (u.isPresent()) return new MyUserPrincipal(u.get());

        User user = new User();
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(roleRepository.findByName("user"));
        userRepository.save(user);
        return new MyUserPrincipal(user);
    }

    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepository.findByName(username);
    }

}
