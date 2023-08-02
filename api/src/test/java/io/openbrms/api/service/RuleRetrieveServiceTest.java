package io.openbrms.api.service;

import io.openbrms.api.dao.RuleRepository;
import io.openbrms.api.entity.Rule;
import io.openbrms.base.RuleDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RuleRetrieveServiceTest {

    @Mock
    RuleRepository ruleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method under test: {@link RuleRetrieveService#listRulesByWorkflowId(String)}
     */
    @Test
    void testListRulesByWorkflowId() {
        when(ruleRepository.findAllByWorkflowId(Mockito.any())).thenReturn(new ArrayList<>());
        assertTrue((new RuleRetrieveService(ruleRepository)).listRulesByWorkflowId("test").isEmpty());
        verify(ruleRepository).findAllByWorkflowId(Mockito.any());
    }

    /**
     * Method under test: {@link RuleRetrieveService#listRulesByWorkflowId(String)}
     */
    @Test
    void testListRulesByWorkflowId2() {
        Rule rule = Rule.builder().id("test-rule1").workflowId("test").required(true).when("true").then("1").build();
        when(ruleRepository.findAllByWorkflowId(Mockito.any())).thenReturn(List.of(rule));
        List<RuleDefinition> list = (new RuleRetrieveService(ruleRepository)).listRulesByWorkflowId("test");
        assertFalse(list.isEmpty());
        assertTrue(list.get(0).isRequired());
        assertEquals("test", list.get(0).getWorkflowId());
        assertEquals("true", list.get(0).getWhen());
        assertTrue(list.get(0).getVariables().isEmpty());
        assertEquals("1", list.get(0).getThen());
        verify(ruleRepository).findAllByWorkflowId(Mockito.any());
    }

    /**
     * Method under test:
     * {@link RuleRetrieveService#findRuleDefinitionById(String, String)}
     */
    @Test
    void testFindRuleDefinitionById() {
        Rule rule = Rule.builder().id("test-rule1").workflowId("test").required(true).when("true").then("1").build();
        when(ruleRepository.findByWorkflowIdAndId(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.of(rule));
        Optional<RuleDefinition> actualFindRuleDefinitionByIdResult = (new RuleRetrieveService(ruleRepository))
                .findRuleDefinitionById("test", "test1");
        assertTrue(actualFindRuleDefinitionByIdResult.isPresent());
        RuleDefinition getResult = actualFindRuleDefinitionByIdResult.get();
        assertEquals("test-rule1", getResult.getId());
        assertTrue(getResult.isRequired());
        assertEquals("test", getResult.getWorkflowId());
        assertEquals("true", getResult.getWhen());
        assertTrue(getResult.getVariables().isEmpty());
        assertEquals("1", getResult.getThen());
        verify(ruleRepository).findByWorkflowIdAndId(Mockito.any(), Mockito.any());
    }
}
