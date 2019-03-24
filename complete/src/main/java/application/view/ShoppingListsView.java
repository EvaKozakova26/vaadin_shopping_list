package application.view;

import application.model.ShoppingList;
import application.model.User;
import application.presenter.ShoppingListsPresenter;
import application.presenter.UserAuthPresenter;
import application.security.MyUserPrincipal;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Route("")
public class ShoppingListsView extends VerticalLayout {

    private final ShoppingListsPresenter shoppingListsPresenter;
    private final UserAuthPresenter userAuthPresenter;

    final Grid<ShoppingList> grid;

    private final Button addNewBtn;
    private final Button btnLogin;
    private final Button btnRegister;
    private final Button btnLogout;


    public ShoppingListsView(ShoppingListsPresenter shoppingListsPresenter, UserAuthPresenter userAuthPresenter) {
        this.shoppingListsPresenter = shoppingListsPresenter;
        this.userAuthPresenter = userAuthPresenter;
      //  this.editor = editor;
        this.grid = new Grid<>(ShoppingList.class);
        this.addNewBtn = new Button("create new", VaadinIcon.PLUS.create());


        //login
        LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setTitle("Please log in");
        loginOverlay.addLoginListener(e -> {
            User currentUser = userAuthPresenter.loginUser(e.getUsername(), e.getPassword());
            if (currentUser.getName() != null) {
                loginOverlay.setOpened(false);
                getShoppingLists();
            }
        });

       //register
        LoginOverlay registerOverlay = new LoginOverlay();
        registerOverlay.setTitle("Use this form to register");
        registerOverlay.addLoginListener(e -> {
            userAuthPresenter.registerUser(e.getUsername(), e.getPassword());
            registerOverlay.setOpened(false);
            getShoppingLists();
        });


        this.btnLogin = new Button("login", VaadinIcon.CHILD.create(), e -> loginOverlay.setOpened(true));
        this.btnRegister = new Button("register", VaadinIcon.CHILD.create(), e -> registerOverlay.setOpened(true));
        this.btnLogout = new Button("logout", VaadinIcon.CHILD.create(), e -> userAuthPresenter.logout());
        HorizontalLayout actions = new HorizontalLayout(addNewBtn, btnLogin, btnRegister, btnLogout);
        add(actions, grid);
        grid.setHeight("300px");
        grid.setColumns("id", "createdAt");

        NativeButtonRenderer<ShoppingList> buttonRenderer =  new NativeButtonRenderer<>("delete", clickedItem -> {
            shoppingListsPresenter.deletelist(clickedItem);
            getShoppingLists();
        });

        grid.addColumn(buttonRenderer);

        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        grid.asSingleSelect().addValueChangeListener(e -> {
            grid.getUI().get().getSession().setAttribute("id", e.getValue().getId());
            addNewBtn.getUI().ifPresent(ui -> ui.navigate("getItems"));
        });

        addNewBtn.addClickListener(e -> addNewBtn.getUI().ifPresent(ui -> ui.navigate("createItems")));

        // Initialize listing
        getShoppingLists();
    }


    private void getShoppingLists() {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(myUserPrincipal.getUsername() + " logged" );
            grid.setItems(shoppingListsPresenter.getAllListsByUser(myUserPrincipal.getUsername()));
        }
    }

}
