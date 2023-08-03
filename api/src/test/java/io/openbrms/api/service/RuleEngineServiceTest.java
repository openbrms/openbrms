package io.openbrms.api.service;

import com.twineworks.tweakflow.lang.values.Value;
import io.openbrms.base.VariableDefinition;
import jakarta.inject.Inject;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class RuleEngineServiceTest {

    @Mock
    RuleRetrieveService ruleRetrieveService;
    RuleEngineService ruleEngineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ruleEngineService = new RuleEngineService(ruleRetrieveService);
    }

    /**
     * Method under test:
     * {@link RuleEngineService#prepareListValue(Collection, VariableDefinition)}
     */
    @Test
    void testPrepareListValue() {
        List<Object> collection = new ArrayList<>();
        Value value = ruleEngineService.prepareListValue(collection, null);
        Asserts.check(value.isList(), "err");
    }
}
