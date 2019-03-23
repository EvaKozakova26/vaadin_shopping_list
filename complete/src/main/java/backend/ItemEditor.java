package backend;

import backend.model.Item;
import backend.model.ShoppingList;
import backend.presenter.ItemsViewPresenter;
import backend.services.ItemService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in ItemsView.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX.
 */
@SpringComponent
@UIScope
public class ItemEditor extends VerticalLayout implements KeyNotifier {

	private final ItemsViewPresenter presenter;

	/**
	 * The currently edited item
	 */
	private Item item;

	/* Fields to edit properties in Item entity */
	TextField name = new TextField("name");
	NumberField count = new NumberField("count");

	List<Item> currentItems = new ArrayList<>();

	/* Action buttons */
	// TODO why more code?
	private Button save = new Button("Save", VaadinIcon.CHECK.create());
	private Button cancel = new Button("Cancel");
	private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
	private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	private Binder<Item> binder = new Binder<>(Item.class);
	private ChangeHandler changeHandler;

	@Autowired
	public ItemEditor(ItemsViewPresenter presenter) {
		this.presenter = presenter;

		add(name, count, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);

		save.getElement().getThemeList().add("primary");
		delete.getElement().getThemeList().add("error");

		addKeyPressListener(Key.ENTER, e -> createItem());

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> createItem());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> editCustomer(item));
		setVisible(false);
	}

	void delete() {
		presenter.deleteItem(item);
		changeHandler.onChange();
	}

	void createItem() {
		item.setState("NEW");
		item.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		currentItems.add(item);
		//itemService.saveItem(item);
		changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editCustomer(Item c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		//TODO najit v databazi

		item = c;
		// Bind item properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(item);
		setVisible(true);
		// Focus first name initially
		name.focus();

	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		changeHandler = h;
	}

}
