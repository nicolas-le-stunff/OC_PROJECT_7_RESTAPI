package com.nnk.springboot.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

import javassist.NotFoundException;

@Service
public class RuleNameService {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	private Logger log = LoggerFactory.getLogger(RuleNameService.class);

	/**
	 * Get a list RuleName
	 * 
	 * @return List ruleName
	 */
	public List<RuleName> getAllRuleName() {
		log.info("Get all ruleName");
		return ruleNameRepository.findAll();
	}

	/**
	 * Delete RuleName by ID
	 * 
	 * @param id
	 */
	@Transactional
	public void deleteRuleName(Integer id) {
		log.info("Delete rule id : " + id);
		ruleNameRepository.deleteById(id);
	}

	/**
	 * Create a RuleName by ID
	 * 
	 * @param ruleName
	 * @return ruleName
	 */
	public RuleName createRuleName(RuleName ruleName) {
		log.info("Create a new ruleName");
		RuleName newRuleName = new RuleName();
		newRuleName.setDescription(ruleName.getDescription());
		newRuleName.setName(ruleName.getName());
		newRuleName.setSqlPart(ruleName.getSqlPart());
		newRuleName.setSqlStr(ruleName.getSqlStr());
		newRuleName.setTemplate(ruleName.getTemplate());
		newRuleName.setJson(ruleName.getJson());
		return ruleNameRepository.save(newRuleName);
	}

	/**
	 * 
	 * Get ruleName by ID
	 * 
	 * @param id
	 * @return RuleName
	 * @throws NotFoundException
	 */
	public RuleName getRuleNameById(Integer id) throws NotFoundException {
		log.info("Get ruleName by ID : " + id);
		if (ruleNameRepository.existsById(id)) {
			return ruleNameRepository.getOne(id);
		}
		log.error("ruleName Id : " + id + " not exist");
		throw new NotFoundException("ruleName Id : " + id + " not exist");
	}

	/**
	 * Update a RuleName by ID
	 * 
	 * @param id
	 * @param ruleName
	 * @return
	 * @throws NotFoundException
	 */
	@Transactional
	public RuleName updateRuleName(Integer id, RuleName ruleName) throws NotFoundException {
		log.info("Update ruleName");
		if (ruleNameRepository.existsById(id)) {
			RuleName ruleNameUpdated = ruleNameRepository.getOne(id);
			ruleNameUpdated.setDescription(ruleName.getDescription());
			ruleNameUpdated.setJson(ruleName.getJson());
			ruleNameUpdated.setName(ruleName.getName());
			ruleNameUpdated.setSqlPart(ruleName.getSqlPart());
			ruleNameUpdated.setSqlStr(ruleName.getSqlStr());
			ruleNameUpdated.setTemplate(ruleName.getTemplate());
			return ruleNameRepository.save(ruleNameUpdated);

		}
		log.error("ruleName Id : " + id + " not exist");
		throw new NotFoundException("ruleName Id : " + id + " not exist");
	}

}
