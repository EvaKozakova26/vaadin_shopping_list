package backend;

import backend.model.Item;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

@Route("items")
public class MainView extends VerticalLayout {

	private final ItemRepository repo;

	private final ItemEditor editor;

	final Grid<Item> grid;

	final TextField filter;

	private final Button addNewBtn;

	public MainView(ItemRepository repo, ItemEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Item.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New item", VaadinIcon.PLUS.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
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

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listItems(filter.getValue());
		});

		// Initialize listing
		listItems(null);
	}

	// tag::listItems[]
	void listItems(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findByName(filterText));
		}
	}
	// end::listItems[]

}
