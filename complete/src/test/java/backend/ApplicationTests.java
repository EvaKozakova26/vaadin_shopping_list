package backend;

import backend.services.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ApplicationTests {

	@Autowired
	private ItemService itemService;

	@Test
	public void shouldFillOutComponentsWithDataWhenTheApplicationIsStarted() {
	/*	then(this.itemService.count()).isEqualTo(5);*/
	}

	@Test
	public void shouldFindTwoBauerCustomers() {
		/*then(this.itemService.findByName("Bauer")).hasSize(2);*/
	}
}
