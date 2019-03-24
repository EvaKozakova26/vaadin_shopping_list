package application.presenter;

import application.model.User;
import application.security.MyUserPrincipal;
import application.services.MyUserDetailService;
import application.services.UserService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@SpringComponent
public class UserAuthPresenter {

    private final UserService userService;

    private final MyUserDetailService myUserDetailService;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserAuthPresenter(UserService userService, MyUserDetailService myUserDetailService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.myUserDetailService = myUserDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String name, String password) {
        MyUserPrincipal currentUser = userService.createNewUser(name, password);
        User user = new User();
        user.setName(currentUser.getUsername());
        user.setPassword(currentUser.getPassword());
        return user;
    }

    public User loginUser(String name, String password) {
        MyUserPrincipal currentUser = (MyUserPrincipal) myUserDetailService.loadUserByUsername(name);

        if (passwordEncoder.matches(password, currentUser.getPassword())) {
            User user = new User();
            user.setName(currentUser.getUsername());
            user.setPassword(currentUser.getPassword());
            Authentication a = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(a);

            return user;
        }
        System.out.println("wrong password");
        return new User();

    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }

    public User getCurrentUser() {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(myUserPrincipal.getUsername() + " logged" );
            Optional<User> user = userService.findByUsername(myUserPrincipal.getUsername());
            return user.orElse(null);
        }
        return null;
    }
}
