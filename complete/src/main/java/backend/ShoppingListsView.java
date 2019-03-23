package backend;

import backend.model.ShoppingList;
import backend.presenter.ShoppingListsPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class ShoppingListsView extends VerticalLayout {

    private final ShoppingListsPresenter shoppingListsPresenter;

    final Grid<ShoppingList> grid;

    private final Button addNewBtn;


    public ShoppingListsView(ShoppingListsPresenter shoppingListsPresenter) {
        this.shoppingListsPresenter = shoppingListsPresenter;
      //  this.editor = editor;
        this.grid = new Grid<>(ShoppingList.class);
        this.addNewBtn = new Button("create new", VaadinIcon.PLUS.create());

        // build layout
        HorizontalLayout actions = new HorizontalLayout(addNewBtn);
        add(actions, grid);

        grid.setHeight("300px");
        grid.setColumns("id", "createdAt");
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
            grid.setItems(shoppingListsPresenter.getAllLists());
    }

}
