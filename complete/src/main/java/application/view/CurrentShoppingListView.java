package application.view;

import application.model.Item;
import application.presenter.CurrentShoppingListPresenter;
import application.presenter.ItemsViewPresenter;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("getItems")
public class CurrentShoppingListView extends VerticalLayout {

	private final CurrentShoppingListPresenter currentShoppingListPresenter;
	private final ItemsViewPresenter itemsViewPresenter;

	private final ItemEditor editor;

	final Grid<Item> grid;


	private final Button addNewBtn;
	private final Button btnSaveList;

	private int shoppingListID ;

    @Autowired
	public CurrentShoppingListView(CurrentShoppingListPresenter currentShoppingListPresenter, ItemsViewPresenter itemsViewPresenter, ItemEditor editor) {
        this.currentShoppingListPresenter = currentShoppingListPresenter;
		this.itemsViewPresenter = itemsViewPresenter;
		this.editor = editor;
		this.grid = new Grid<>(Item.class);
		this.addNewBtn = new Button("New item", VaadinIcon.PLUS.create());
		this.btnSaveList = new Button("Save list", VaadinIcon.CHECK.create());

		shoppingListID = 0;
		VaadinSession vaadinSession = UI.getCurrent().getSession();
		if (vaadinSession != null) {
			shoppingListID = (int) UI.getCurrent().getSession().getAttribute("id");
			UI.getCurrent().getSession().setAttribute("id", null);
		}


		// build layout
		HorizontalLayout actions = new HorizontalLayout(addNewBtn, btnSaveList);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("id", "name", "count", "state");

		NativeButtonRenderer<Item> buttonRenderer =  new NativeButtonRenderer<>("check", clickedItem -> {
            if (clickedItem.getState().equals("NEW")) {
                clickedItem.setState("FINISHED");
            } else {
                clickedItem.setState("NEW");
            }
            itemsViewPresenter.saveItem(clickedItem);
            listItems();
        });

        grid.addColumn(buttonRenderer);

        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		// Hook logic to components

		// Replace listing with filtered content when user changes filter

		// Connect selected Item to editor or hide if none is selected
		// Instantiate and edit new Item the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCustomer(new Item()));
		btnSaveList.addClickListener(e -> updateShoppingList());

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listItems();
		});

		// Initialize listing
		listItems();
    }

	private void updateShoppingList() {
		List<Item> currItems = currentShoppingListPresenter.getItemsByList(shoppingListID);
		currItems.addAll(editor.currentItems);
        currentShoppingListPresenter.onListUpdateListener(shoppingListID, currItems);
	}

	// tag::listItems[]
	private void listItems() {
    	List<Item> currItems = currentShoppingListPresenter.getItemsByList(shoppingListID);
    	currItems.addAll(editor.currentItems);
    	grid.setItems(currItems);
	}
	// end::listItems[]

}
