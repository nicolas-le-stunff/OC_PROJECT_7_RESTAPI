package com.nnk.springboot.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RuleNameService ruleNameService;

	@MockBean
	private RuleNameRepository ruleNameRepository;

	private RuleName ruleName = new RuleName();

	@Before
	public void addRuleName() {
		ruleName.setDescription("description");
		ruleName.setName("name");
		ruleName.setSqlPart("SqlPart");
		ruleName.setSqlStr("SqlStr");
		ruleName.setTemplate("Template");
		ruleName.setJson("Json");
		ruleNameService.createRuleName(ruleName);
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getRuleNameTest() throws Exception {

		mockMvc.perform(get("/ruleName/list")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("ruleName/list"));
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getRuleNameAddTest() throws Exception {
		mockMvc.perform(get("/ruleName/add")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("ruleName/add")).andExpect(model().attributeExists("ruleName"));

	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void postRuleNameTest() throws Exception {
		ruleName.setDescription("description");
		ruleName.setName("Name");
		ruleName.setSqlPart("SqlPart");
		ruleName.setSqlStr("SqlStr");
		ruleName.setTemplate("Template");
		ruleName.setJson("Json");

		mockMvc.perform(post("/ruleName/validate").param("name", "name").param("description", "description")
				.param("json", "Json").param("template", "Template").param("sqlStr", "SqlStr")
				.param("sqlPart", "SqlPart").with(csrf())).andDo(print())
				.andExpect(view().name("redirect:/ruleName/list")).andExpect(status().is3xxRedirection());

	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void postRuleNameWithErrorTest() throws Exception {
		mockMvc.perform(post("/ruleName/validate").param("sqlPart", "SqlPart").with(csrf())).andDo(print())
				.andExpect(view().name("ruleName/add")).andExpect(status().is2xxSuccessful());

	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getRuleNameUpdateByExistingIdTest() throws Exception {
		ruleName.setDescription("description");
		ruleName.setName("Name");
		ruleName.setSqlPart("SqlPart");
		ruleName.setSqlStr("SqlStr");
		ruleName.setTemplate("Template");
		ruleName.setJson("Json");

		when(ruleNameService.getRuleNameById(1)).thenReturn(ruleName);

		mockMvc.perform(get("/ruleName/update/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("ruleName/update")).andExpect(model().attributeExists("ruleName"));
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getRuleNameUpdateByUnknownIdTest() throws Exception {
		ruleName.setDescription("description");
		ruleName.setName("Name");
		ruleName.setSqlPart("SqlPart");
		ruleName.setSqlStr("SqlStr");
		ruleName.setTemplate("Template");
		ruleName.setJson("Json");

		when(ruleNameService.getRuleNameById(5)).thenThrow(new NotFoundException("ruleName Id : " + 5 + " not exist"));

		mockMvc.perform(get("/ruleName/update/5").param("name", "name").param("description", "description")
				.param("json", "Json").param("template", "Template").param("sqlStr", "SqlStr")
				.param("sqlPart", "SqlPart").with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("errorNotFound"));
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void postRuleNameUpdateTest() throws Exception {
		ruleName.setDescription("description");
		ruleName.setName("Name");
		ruleName.setSqlPart("SqlPart");
		ruleName.setSqlStr("SqlStr");
		ruleName.setTemplate("Template");
		ruleName.setJson("Json");

		when(ruleNameService.updateRuleName(1, ruleName)).thenReturn(ruleName);

		mockMvc.perform(post("/ruleName/update/1").param("name", "name").param("description", "description")
				.param("json", "Json").param("template", "Template").param("sqlStr", "SqlStr")
				.param("sqlPart", "SqlPart").with(csrf())).andDo(print())
				.andExpect(view().name("redirect:/ruleName/list")).andExpect(status().is3xxRedirection());
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void postRuleNameUpdateWithErrorTest() throws Exception {

		mockMvc.perform(
				post("/ruleName/update/1").param("name", "").param("description", "description").param("json", "Json")
						// .param("template", "Template")
						.param("sqlStr", "SqlStr").param("sqlPart", "SqlPart").with(csrf()))
				.andDo(print()).andExpect(view().name("ruleName/update")).andExpect(status().isOk())
				.andExpect(model().hasErrors()).andExpect(model().attributeExists("ruleName")).andReturn().getResponse()
				.containsHeader("Account is mandatory");
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getRuleNameDeleteByIdTest() throws Exception {

		mockMvc.perform(get("/ruleName/delete/1")).andDo(print()).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/ruleName/list"));
	}
}
