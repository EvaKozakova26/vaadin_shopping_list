package backend;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import application.presenter.UserAuthPresenter;
import application.services.ShoppingListService;
import application.view.ItemEditor;
import application.view.ItemsView;
import application.model.Item;
import application.presenter.ItemsViewPresenter;
import application.services.ItemService;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItemsViewTests.Config.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ItemsViewTests {

	@Autowired
	ItemService itemService;

	@Autowired
	ShoppingListService shoppingListService;

	@Autowired
	ItemsViewPresenter itemsViewPresenter;
	@Autowired
	UserAuthPresenter userAuthPresenter;


	VaadinRequest vaadinRequest = Mockito.mock(VaadinRequest.class);

	ItemEditor editor;

	ItemsView itemsView;

	@Before
	public void setup() {
		this.editor = new ItemEditor(this.itemsViewPresenter);
		this.itemsView = new ItemsView(this.itemsViewPresenter, editor,  this.userAuthPresenter);
	}

	@Test
	public void shouldInitializeTheGridWithCustomerRepositoryData() {
	/*	//int customerCount = (int) this.itemService.count();

		then(itemsView.grid.getColumns()).hasSize(3);
		then(getCustomersInGrid()).hasSize(customerCount);*/
	}

	private List<Item> getCustomersInGrid() {
		ListDataProvider<Item> ldp = (ListDataProvider) itemsView.grid.getDataProvider();
		return new ArrayList<>(ldp.getItems());
	}

	@Test
	public void shouldFillOutTheGridWithNewData() {
		/*int initialCustomerCount = (int) this.itemService.count();

		customerDataWasFilled(editor, "Marcin", "Grzejszczak");

		this.editor.save();

		then(getCustomersInGrid()).hasSize(initialCustomerCount + 1);

		then(getCustomersInGrid().get(getCustomersInGrid().size() - 1))
			.extracting("firstName", "lastName")
			.containsExactly("Marcin", "Grzejszczak");*/

	}

	@Test
	public void shouldFilterOutTheGridWithTheProvidedLastName() {

	/*	this.itemService.save(new Item("Josh", "Long"));

		itemsView.listItems("Long");

		then(getCustomersInGrid()).hasSize(1);
		then(getCustomersInGrid().get(getCustomersInGrid().size() - 1))
			.extracting("firstName", "lastName")
			.containsExactly("Josh", "Long");*/
	}

	@Test
	public void shouldInitializeWithInvisibleEditor() {

		then(this.editor.isVisible()).isFalse();
	}

	@Test
	public void shouldMakeEditorVisible() {
		Item first = getCustomersInGrid().get(0);
		this.itemsView.grid.select(first);

		then(this.editor.isVisible()).isTrue();
	}

	/*private void customerDataWasFilled(ItemEditor editor, String firstName,
									   String lastName) {
		this.editor.firstName.setValue(firstName);
		this.editor.lastName.setValue(lastName);
		editor.editCustomer(new Item(firstName, lastName));
	}*/

	@Configuration
	@EnableAutoConfiguration(exclude = com.vaadin.flow.spring.SpringBootAutoConfiguration.class)
	static class Config {

		@Autowired
		ItemService service;

		@PostConstruct
		public void initializeData() {
			/*this.itemService.save(new Item("Jack", "Bauer"));
			this.itemService.save(new Item("Chloe", "O'Brian"));
			this.itemService.save(new Item("Kim", "Bauer"));
			this.itemService.save(new Item("David", "Palmer"));
			this.itemService.save(new Item("Michelle", "Dessler"));*/
		}
	}
}
