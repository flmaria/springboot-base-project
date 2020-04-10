package com.flm.baseproject.repository;

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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BasicApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleRepositoryIT {

	@Autowired
	private RoleRepository repository;
	
	@Test
	public void it_should_find_by_id() {
		List<Role> roleList = this.repository.findAll();
		
		assertEquals(roleList.get(0).getId(), this.repository.findById(roleList.get(0).getId()).getId());
	}
	
	@Test
	public void it_should_find_by_name() {
		List<Role> roleList = this.repository.findAll();
		
		assertEquals(roleList.get(0).getId(), this.repository.findByName(roleList.get(0).getName()).getId());
	}
	
}
