package com.eldar.fullstackexcersicelorenzolopez;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class FullstackExerciseLorenzoLopezApplicationTests {

	@Autowired
	public EntityManager entityManager;

	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Autowired
	private MessageSource messageSource;

	protected String getMessage(String messageKey) {
		return messageSource.getMessage(messageKey, null, null);
	}

}
