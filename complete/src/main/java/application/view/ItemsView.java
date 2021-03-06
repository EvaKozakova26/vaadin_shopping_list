package application.view;

import application.model.Item;
import application.model.User;
import application.presenter.ItemsViewPresenter;
import application.presenter.UserAuthPresenter;
import application.security.MyUserPrincipal;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import javafx.beans.binding.Bindings;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("createItems")
public class ItemsView extends VerticalLayout {

	private final ItemsViewPresenter presenter;
	private final UserAuthPresenter userAuthPresenter;

	private final ItemEditor editor;

	public final Grid<Item> grid;

	private final Button addNewBtn;
	private final Button btnSaveList;

	private int shoppingListID ;

	public ItemsView(ItemsViewPresenter presenter, ItemEditor editor, UserAuthPresenter userAuthPresenter) {
		this.presenter = presenter;
		this.userAuthPresenter = userAuthPresenter;
		this.editor = editor;
		this.grid = new Grid<>(Item.class);
		this.addNewBtn = new Button("New item", VaadinIcon.PLUS.create());
		this.btnSaveList = new Button("Save list", VaadinIcon.CHECK.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(addNewBtn, btnSaveList);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("id", "name", "count");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		// Hook logic to components

		// Connect selected Item to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCustomer(e.getValue());
		});

		// Instantiate and edit new Item the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCustomer(new Item()));
		btnSaveList.addClickListener(e ->{
			presenter.saveShoppingList(getCurrentUser(), editor.currentItems);
		});

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listItems();
		});

		// Initialize listing
		listItems();
	}

	private User getCurrentUser() {
		return userAuthPresenter.getCurrentUser();
	}

	// tag::listItems[]
	void listItems() {
		grid.setItems(editor.currentItems);
	}
	// end::listItems[]

}
