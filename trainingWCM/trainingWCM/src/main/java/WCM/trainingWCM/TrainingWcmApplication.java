package WCM.trainingWCM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringBootApplication
public class TrainingWcmApplication implements CommandLineRunner {

	@Autowired
	private ItemRepository repository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(TrainingWcmApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String sql = "SELECT * FROM items";
		List<Item> items = jdbcTemplate.query(sql,
				BeanPropertyRowMapper.newInstance(Item.class));

		repository.saveAll(items);
		items.forEach(System.out :: println);
	}
}
