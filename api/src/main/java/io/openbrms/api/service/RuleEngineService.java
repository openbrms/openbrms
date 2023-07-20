package io.openbrms.api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twineworks.tweakflow.lang.values.DictValue;
import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.lang.values.Values;
import java.util.*;
import java.util.stream.Collectors;

import io.quarkus.runtime.util.StringUtil;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import io.openbrms.api.exception.NotFoundException;
import io.openbrms.base.RuleDefinition;
import io.openbrms.base.RuleExecutor;
import io.openbrms.base.VariableDefinition;
import io.openbrms.domain.EvalResultDto;
import io.openbrms.domain.VariableType;

@Slf4j
@Singleton
public class RuleEngineService {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final RuleRetrieveService ruleRetrieveService;

    public RuleEngineService(RuleRetrieveService ruleRetrieveService) {
        this.ruleRetrieveService = ruleRetrieveService;
    }

    public EvalResultDto eval(String workflowId, String ruleId, Map<String, Object> params) {
        RuleDefinition rd = ruleRetrieveService
                .findRuleDefinitionById(workflowId, ruleId)
                .orElseThrow(NotFoundException::new);
        return eval(rd, params);
    }

    public List<EvalResultDto> eval(String workflowId, Map<String, Object> params) {
        List<RuleDefinition> rd = ruleRetrieveService.listRulesByWorkflowId(workflowId);
        return rd.stream().map(m -> eval(workflowId, m.getId(), params)).collect(Collectors.toList());
    }

    public EvalResultDto eval(RuleDefinition rd, Map<String, Object> params) {
        Map<String, Value> input = prepareInput(params, rd);
        prepareDependentInputs(input, rd, params);
        RuleExecutor ruleExecutor = new RuleExecutor();
        Value result = ruleExecutor.execute(rd, input).orElse(null);
        if (result == null) {
            return EvalResultDto.builder().ruleId(rd.getId()).fits(false).build();
        }

        Object value = result.value();
        if (value instanceof DictValue) {
            Iterator<Map.Entry<String, Value>> it = ((DictValue) result.value()).entryIterator();
            Map<String, Object> mapResult = new HashMap<>();
            while (it.hasNext()) {
                Map.Entry<String, Value> entry = it.next();
                Value v = entry.getValue();
                if (v.isBoolean()) {
                    mapResult.put(entry.getKey(), v.bool());
                } else if (v.isString()) {
                    mapResult.put(entry.getKey(), v.toString());
                } else if (v.isLongNum()) {
                    mapResult.put(entry.getKey(), v.longNum());
                } else if (v.isDoubleNum()) {
                    mapResult.put(entry.getKey(), v.doubleNum());
                }

                /*
                 *
                 * public boolean isString() { return type == Types.STRING; }
                 *
                 * public boolean isLongNum() { return this.type == Types.LONG; }
                 *
                 * public boolean isDoubleNum(){ return this.type == Types.DOUBLE; }
                 *
                 * public boolean isDecimal(){ return this.type == Types.DECIMAL; }
                 *
                 * public boolean isNumeric() {return this.type.isNumeric();}
                 *
                 * public boolean isDateTime(){ return this.type == Types.DATETIME; }
                 *
                 * public boolean isBoolean() { return this.type == Types.BOOLEAN; }
                 *
                 * public boolean isBinary() { return this.type == Types.BINARY; }
                 *
                 * public Value castTo(Type type){ if (type == this.type || type == Types.ANY ||
                 * this == Values.NIL) return this; // no cast necessary return
                 * type.castFrom(this); }
                 *
                 * public boolean isList() { return this.type == Types.LIST; }
                 *
                 * public boolean isFunction() { return this.type == Types.FUNCTION; }
                 *
                 * public boolean isDict() { return this.type == Types.DICT; }
                 */

            }
            value = mapResult;
        }

        return EvalResultDto.builder()
                .ruleId(rd.getId())
                .type(VariableType.from(result.type().toString()))
                .value(value)
                .fits(true)
                .build();
    }

    private void prepareDependentInputs(
            Map<String, Value> input, RuleDefinition rd, Map<String, Object> params) {
        for (Map.Entry<String, VariableDefinition> entry : rd.getVariables().entrySet()) {
            String key = entry.getKey();
            VariableDefinition vd = entry.getValue();
            if (!StringUtil.isNullOrEmpty(vd.getRuleId())) {
                if (vd.getVar() == null) {
                    EvalResultDto r = eval(rd.getWorkflowId(), vd.getRuleId(), params);
                    if (r.isFits()) {
                        Value value = Values.make(r.getValue());
                        input.put(key, value);
                    }
                } else {
                    EvalResultDto r = eval(rd.getWorkflowId(), vd.getRuleId(), params);
                    if (r.isFits()) {
                        Map<String, Object> dict = mapper.convertValue(r.getValue(), new TypeReference<>() {
                        });
                        Value value = Values.make(dict.get(vd.getVar()));
                        input.put(key, value);
                    }
                }
            }
        }
    }

    private Map<String, Value> prepareInput(Map<String, Object> params, RuleDefinition rd) {
        Map<String, Value> input = new HashMap<>();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String var = entry.getKey();
            Object obj = entry.getValue();

            VariableDefinition vard = rd.getVariables().get(var);
            if (vard == null)
                continue;
            if (vard.getType() == null)
                throw new RuntimeException(
                        String.format("Rule '%s' variable '%s' has not type", rd.getId(), var));

            Value v;
            if (obj instanceof Collection) {
                v = prepareListValue((Collection<Object>) obj, vard);
            } else {
                v = Values.make(obj);
            }

            input.put(var, v);
        }

        return input;
    }

    private Value prepareListValue(Collection<Object> collection, VariableDefinition vard) {
        List<Value> values = new ArrayList<>();

        for (Object o : collection) {
            Value v = Values.make(o);
            values.add(v);
        }

        return Values.make(values);
    }
}
