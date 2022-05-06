package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RatingService;
import com.nnk.springboot.service.RuleNameService;

import javassist.NotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleServiceTests {

	
	@Mock
	private RuleNameRepository ruleNameRepository;

	@InjectMocks
	private RuleNameService ruleNameService;
	
	@Test
	public void createRuleNameTest() {
		Mockito.verify(ruleNameRepository,Mockito.times(0)).save(any());
		
		RuleName ruleName = new RuleName();
		ruleName.setId(1);
		ruleName.setDescription("test");
		ruleName.setJson("1");
		ruleName.setSqlPart("2");
		ruleName.setSqlStr("3");
		ruleName.setTemplate("4");
		
		ruleNameService.createRuleName(ruleName);
		
		Mockito.verify(ruleNameRepository,Mockito.times(1)).save(any());
		
	}
	
	@Test
	public void updateRuleNameTest() {	
		Mockito.when(ruleNameRepository.existsById(1)).thenReturn(true);
		
		
		RuleName ruleName = new RuleName();
		ruleName.setId(1);
		ruleName.setDescription("test");
		ruleName.setJson("1");
		ruleName.setSqlPart("2");
		ruleName.setSqlStr("3");
		ruleName.setTemplate("4");
		
		Mockito.when(ruleNameRepository.getOne(1)).thenReturn(ruleName);
		
		
		RuleName ruleNameUpdate = new RuleName();
		ruleNameUpdate.setId(1);
		ruleNameUpdate.setDescription("update");
		ruleNameUpdate.setJson("1");
		ruleNameUpdate.setSqlPart("2");
		ruleNameUpdate.setSqlStr("3");
		ruleNameUpdate.setTemplate("4");
		
		
		try {
			ruleNameService.updateRuleName(1, ruleNameUpdate);
			RuleName ruleNameTest = ruleNameService.getRuleNameById(1);
			
			assertEquals(ruleNameUpdate.getDescription(),ruleNameTest.getDescription(),0);
			
			
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		
	}
	
	@Test
	public void getAllCurvePointTest() {
		
	
		RuleName ruleName = new RuleName();
		ruleName.setId(1);
		ruleName.setDescription("test");
		ruleName.setJson("1");
		ruleName.setSqlPart("2");
		ruleName.setSqlStr("3");
		ruleName.setTemplate("4");
		
		RuleName ruleName2 = new RuleName();
		ruleName2.setId(2);
		ruleName2.setDescription("test");
		ruleName2.setJson("1");
		ruleName2.setSqlPart("2");
		ruleName2.setSqlStr("3");
		ruleName2.setTemplate("4");
		
		List<RuleName> ruleNameList = new ArrayList<>();
		ruleNameList.add(ruleName);
		ruleNameList.add(ruleName2);
		
		Mockito.when(ruleNameRepository.findAll()).thenReturn(ruleNameList);
		
		ruleNameService.createRuleName(ruleName);
		ruleNameService.createRuleName(ruleName2);
		
		List<RuleName> allRuleName = ruleNameService.getAllRuleName();	
		assertEquals(allRuleName.size(),2);
	}
	
	
	
	
/*	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Test
	public void ruleTest() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		rule = ruleNameRepository.save(rule);
		Assert.assertNotNull(rule.getId());
		Assert.assertTrue(rule.getName().equals("Rule Name"));

		// Update
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);
		Assert.assertTrue(rule.getName().equals("Rule Name Update"));

		// Find
		List<RuleName> listResult = ruleNameRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		Assert.assertFalse(ruleList.isPresent());
	}*/
}
