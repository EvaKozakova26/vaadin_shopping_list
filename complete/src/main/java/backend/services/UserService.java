package backend.services;

import backend.repository.RoleRepository;
import backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

   // private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
   //     this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

   /* @Transactional
    public MyUserPrincipal createNewUser(@Valid UserDto userDto) throws DataAccessException {
        Optional<User> u = userRepository.findByUsername(userDto.getName());
        if (u.isPresent()) return new MyUserPrincipal(u.get());

        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(roleRepository.findByName("user").get());
        userRepository.save(user);
        return new MyUserPrincipal(user);
    }

    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }*/

}
