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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BasicApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfileRepositoryIT {

	@Autowired
	private ProfileRepository repository;
	
	@Test
	public void it_should_find_by_id() {
		List<Profile> profileList = this.repository.findAll();
		
		assertEquals(profileList.get(0).getId(), this.repository.findById(profileList.get(0).getId()).getId());
	}
	
	@Test
	public void it_should_find_by_name() {
		List<Profile> profileList = this.repository.findAll();
		
		assertEquals(profileList.get(0).getId(), this.repository.findByName(profileList.get(0).getName()).getId());
	}
	
}
