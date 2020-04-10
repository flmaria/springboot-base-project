package com.flm.baseproject.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.flm.baseproject.BasicApplication;
import com.flm.baseproject.model.Profile;
import com.flm.baseproject.model.Role;
import com.flm.baseproject.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BasicApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoryIT {

	@Autowired
	private UserRepository repository;
	
	@Test
	public void it_should_find_by_id() {
		List<User> userList = this.repository.findAll();
		
		assertEquals(userList.get(0).getId(), this.repository.findById(userList.get(0).getId()).getId());
	}
	
	@Test
	public void it_should_find_by_login_or_email() {
		List<User> userList = this.repository.findAll();
		
		assertEquals(userList.get(0).getId(), this.repository.findByLoginOrEmail(userList.get(0).getLogin()).getId());
		assertEquals(userList.get(0).getId(), this.repository.findByLoginOrEmail(userList.get(0).getEmail()).getId());
	}
	
	@Test
	public void it_should_list_all_on_find_all_order_by_name_asc() {
		assertTrue(this.repository.findAllOrderByNameAsc().size() > 0);
	}
	
	@Test
	public void it_should_find_by_login() {
		List<User> userList = this.repository.findAll();
		
		assertEquals(userList.get(0).getId(), this.repository.findByLogin(userList.get(0).getLogin()).getId());
	}
	
	@Test
	public void it_should_find_by_email() {
		List<User> userList = this.repository.findAll();
		
		assertEquals(userList.get(0).getId(), this.repository.findByEmail(userList.get(0).getEmail()).getId());
	}
}
