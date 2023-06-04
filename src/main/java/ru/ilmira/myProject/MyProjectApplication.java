package ru.ilmira.myProject;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class MyProjectApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MyProjectApplication.class, args);
	}

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... args) throws Exception {
		Integer rolesCount = jdbcTemplate.queryForObject("select count(role_id) from roles", Integer.class);
		if (rolesCount == 0) {
			jdbcTemplate.execute("insert into roles (name) values ('ROLE_USER')");
			jdbcTemplate.execute("insert into roles (name) values ('ROLE_MODERATOR')");
			jdbcTemplate.execute("insert into roles (name) values ('ROLE_ADMIN')");
		}
	}
}
