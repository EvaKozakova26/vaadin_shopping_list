package backend;

import backend.model.Item;
import backend.model.ShoppingList;
import backend.services.ItemService;
import backend.services.ShoppingListService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route("createItems")
public class ItemsView extends VerticalLayout {

	private final ItemService itemService;
	private final ShoppingListService shoppingListService;

	private final ItemEditor editor;

	final Grid<Item> grid;

	final TextField filter;

	private final Button addNewBtn;
	private final Button btnSaveList;

	private int shoppingListID ;

	public ItemsView(ItemService itemService, ShoppingListService shoppingListService, ItemEditor editor) {
		this.itemService = itemService;
		this.shoppingListService = shoppingListService;
		this.editor = editor;
		this.grid = new Grid<>(Item.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New item", VaadinIcon.PLUS.create());
		this.btnSaveList = new Button("Save list", VaadinIcon.CHECK.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn, btnSaveList);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("id", "name", "count", "state");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filter by name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listItems(e.getValue()));

		// Connect selected Item to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCustomer(e.getValue());
		});

		// Instantiate and edit new Item the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCustomer(new Item()));
		btnSaveList.addClickListener(e -> saveShoppingList());

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listItems(filter.getValue());
		});

		// Initialize listing
		listItems(null);
	}

	private void saveShoppingList() {
		shoppingListService.createList(null, editor.currentItems);
	}

	// tag::listItems[]
	void listItems(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
				grid.setItems(editor.currentItems);
		}
		else {
			grid.setItems(itemService.findByName(filterText));
		}
	}
	// end::listItems[]

}
