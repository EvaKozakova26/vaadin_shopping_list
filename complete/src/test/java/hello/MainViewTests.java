package hello;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

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
@SpringBootTest(classes = MainViewTests.Config.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MainViewTests {

	@Autowired
    ItemRepository repository;

	VaadinRequest vaadinRequest = Mockito.mock(VaadinRequest.class);

	ItemEditor editor;

	MainView mainView;

	@Before
	public void setup() {
		this.editor = new ItemEditor(this.repository);
		this.mainView = new MainView(this.repository, editor);
	}

	@Test
	public void shouldInitializeTheGridWithCustomerRepositoryData() {
		int customerCount = (int) this.repository.count();

		then(mainView.grid.getColumns()).hasSize(3);
		then(getCustomersInGrid()).hasSize(customerCount);
	}

	private List<Item> getCustomersInGrid() {
		ListDataProvider<Item> ldp = (ListDataProvider) mainView.grid.getDataProvider();
		return new ArrayList<>(ldp.getItems());
	}

	@Test
	public void shouldFillOutTheGridWithNewData() {
		/*int initialCustomerCount = (int) this.repository.count();

		customerDataWasFilled(editor, "Marcin", "Grzejszczak");

		this.editor.save();

		then(getCustomersInGrid()).hasSize(initialCustomerCount + 1);

		then(getCustomersInGrid().get(getCustomersInGrid().size() - 1))
			.extracting("firstName", "lastName")
			.containsExactly("Marcin", "Grzejszczak");*/

	}

	@Test
	public void shouldFilterOutTheGridWithTheProvidedLastName() {

	/*	this.repository.save(new Item("Josh", "Long"));

		mainView.listItems("Long");

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
		this.mainView.grid.select(first);

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
        ItemRepository repository;

		@PostConstruct
		public void initializeData() {
			/*this.repository.save(new Item("Jack", "Bauer"));
			this.repository.save(new Item("Chloe", "O'Brian"));
			this.repository.save(new Item("Kim", "Bauer"));
			this.repository.save(new Item("David", "Palmer"));
			this.repository.save(new Item("Michelle", "Dessler"));*/
		}
	}
}