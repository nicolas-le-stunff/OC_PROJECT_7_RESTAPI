package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import javassist.NotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleServiceTests {

	@Mock
	private RuleNameRepository ruleNameRepository;

	@InjectMocks
	private RuleNameService ruleNameService;

	private RuleName ruleName = new RuleName();
	private RuleName ruleName2 = new RuleName();

	@Before
	public void init() {
		ruleName.setId(1);
		ruleName.setDescription("test");
		ruleName.setJson("1");
		ruleName.setSqlPart("2");
		ruleName.setSqlStr("3");
		ruleName.setTemplate("4");

		ruleName2.setId(2);
		ruleName2.setDescription("test");
		ruleName2.setJson("1");
		ruleName2.setSqlPart("2");
		ruleName2.setSqlStr("3");
		ruleName2.setTemplate("4");
	}

	@Test
	public void createRuleNameTest() {
		Mockito.verify(ruleNameRepository, Mockito.times(0)).save(any());

		ruleNameService.createRuleName(ruleName);

		Mockito.verify(ruleNameRepository, Mockito.times(1)).save(any());

	}

	@Test
	public void updateRuleNameTest() throws NotFoundException {
		Mockito.when(ruleNameRepository.existsById(1)).thenReturn(true);

		Mockito.when(ruleNameRepository.getOne(1)).thenReturn(ruleName);

		RuleName ruleNameUpdate = new RuleName();
		ruleNameUpdate.setId(1);
		ruleNameUpdate.setDescription("update");
		ruleNameUpdate.setJson("1");
		ruleNameUpdate.setSqlPart("2");
		ruleNameUpdate.setSqlStr("3");
		ruleNameUpdate.setTemplate("4");

		ruleNameService.updateRuleName(1, ruleNameUpdate);
		RuleName ruleNameTest = ruleNameService.getRuleNameById(1);

		assertEquals(ruleNameUpdate.getDescription(), ruleNameTest.getDescription());

	}

	@Test(expected = NotFoundException.class)
	public void updateRuleNameTestThrowError() throws NotFoundException {
		Mockito.when(ruleNameRepository.existsById(1)).thenReturn(true);

		Mockito.when(ruleNameRepository.getOne(1)).thenReturn(ruleName);

		RuleName ruleNameUpdate = new RuleName();
		ruleNameUpdate.setId(1);
		ruleNameUpdate.setDescription("update");

		ruleNameService.updateRuleName(5, ruleNameUpdate);

	}

	@Test
	public void getAllCurvePointTest() {
		List<RuleName> ruleNameList = new ArrayList<>();
		ruleNameList.add(ruleName);
		ruleNameList.add(ruleName2);

		Mockito.when(ruleNameRepository.findAll()).thenReturn(ruleNameList);

		ruleNameService.createRuleName(ruleName);
		ruleNameService.createRuleName(ruleName2);

		List<RuleName> allRuleName = ruleNameService.getAllRuleName();
		assertEquals(allRuleName.size(), 2);
	}

	@Test
	public void deleteRuleNameTest() {
		Mockito.verify(ruleNameRepository, Mockito.times(0)).save(any());

		ruleNameService.createRuleName(ruleName);

		Mockito.verify(ruleNameRepository, Mockito.times(1)).save(any());

		ruleNameService.deleteRuleName(1);

		Mockito.verify(ruleNameRepository, Mockito.times(1)).deleteById(any());
	}

	@Test
	public void getTradeByIdTest() throws NotFoundException {
		Mockito.when(ruleNameRepository.existsById(1)).thenReturn(true);

		Mockito.when(ruleNameRepository.getOne(1)).thenReturn(ruleName);

		ruleNameService.createRuleName(ruleName);
		RuleName rule = ruleNameService.getRuleNameById(1);

		assertEquals(rule, ruleName);

	}

	@Test(expected = NotFoundException.class)
	public void getTradeByIdNotExistTest() throws NotFoundException {

		assertEquals(ruleNameService.getRuleNameById(1), 0);

	}
}
