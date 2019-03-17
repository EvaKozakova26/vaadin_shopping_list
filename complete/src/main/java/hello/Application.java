package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner loadData(ItemRepository repository) {
		return (args) -> {
			// save a couple of customers
			Item item = new Item();
			item.setName("madafaka");
			item.setCount(1);
			item.setCreatedAt(null);
			item.setState(false);

			repository.save(item);


			/*// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Item customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual item by ID
			Item item = repository.findById(1L).get();
			log.info("Item found with findOne(1L):");
			log.info("--------------------------------");
			log.info(item.toString());
			log.info("");

			// fetch customers by last name
			log.info("Item found with findByName('Bauer'):");
			log.info("--------------------------------------------");
			for (Item bauer : repository
					.findByName("Bauer")) {
				log.info(bauer.toString());
			}
			log.info("");*/
		};
	}

}
