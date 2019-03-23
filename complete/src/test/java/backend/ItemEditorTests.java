package backend;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.then;

import backend.model.Item;
import backend.services.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ItemEditorTests {

	private static final String FIRST_NAME = "Marcin";
	private static final String LAST_NAME = "Grzejszczak";

	@Mock
	ItemService itemService;
	@InjectMocks
	ItemEditor editor;
	@Mock ItemEditor.ChangeHandler changeHandler;

	@Before
	public void init() {
		editor.setChangeHandler(changeHandler);
	}

	@Test
	public void shouldStoreCustomerInRepoWhenEditorSaveClicked() {
		emptyCustomerWasSetToForm();

		this.editor.name.setValue(FIRST_NAME);

		this.editor.createItem();

		//then(this.itemService).should().save(argThat(customerMatchesEditorFields()));
	}

	@Test
	public void shouldDeleteCustomerFromRepoWhenEditorDeleteClicked() {
	/*	customerDataWasFilled();

		editor.delete();

		then(this.itemService).should().delete(argThat(customerMatchesEditorFields()));*/
	}

	private void emptyCustomerWasSetToForm() {
		this.editor.editCustomer(new Item());
	}
	private void customerDataWasFilled() {
		//this.editor.editCustomer(new Item(FIRST_NAME, LAST_NAME));
	}

	private ArgumentMatcher<Item> customerMatchesEditorFields() {
		return customer -> FIRST_NAME.equals(customer.getName());
	}

}
