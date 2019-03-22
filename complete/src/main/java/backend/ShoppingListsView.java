package backend;

import backend.model.ShoppingList;
import backend.services.ShoppingListService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

@Route("")
public class ShoppingListsView extends VerticalLayout {

    private final ShoppingListService shoppingLisService;

    final Grid<ShoppingList> grid;

    private final Button addNewBtn;


    public ShoppingListsView(ShoppingListService shoppingListService) {
        this.shoppingLisService = shoppingListService;
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
        getShoppingList(null);
    }


    private void getShoppingList(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(shoppingLisService.findAllLists());
        }
    }

}
