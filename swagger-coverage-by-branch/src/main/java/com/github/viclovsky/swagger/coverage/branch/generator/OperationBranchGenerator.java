package com.github.viclovsky.swagger.coverage.branch.generator;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.model.BranchOperationCoverage;
import com.github.viclovsky.swagger.coverage.branch.model.OperationsHolder;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

class OperationBranchGenerator {

    private static final Logger log = LoggerFactory.getLogger(OperationBranchGenerator.class);

    static Map<String, BranchOperationCoverage> getOperationMap(Swagger swagger, List<BranchRule> rules) {
        OperationsHolder operations = SwaggerSpecificationProcessor.extractOperation(swagger);
        Map<String, BranchOperationCoverage> coverage = new TreeMap<>();

        operations.getOperations().forEach((key, value) -> {
            BranchOperationCoverage oc = buildBranchOperationCoverage(value, rules);
            log.info(String.format("put operation %s", key));
            coverage.put(key, oc);
        });

        return coverage;
    }

    static BranchOperationCoverage buildBranchOperationCoverage(Operation operation, List<BranchRule> rules){
        BranchOperationCoverage operationCoverage = new BranchOperationCoverage();
        operationCoverage.setOperation(operation);
        operationCoverage.setBranches(generateBranchList(operation,rules));

        return operationCoverage;
    }

    static List<Branch> generateBranchList(Operation operation, List<BranchRule> rules){
        List<Branch> branches = rules
                .stream()
                .map(rule -> rule.createBranch(operation))
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        log.info(String.format("created list is %s",branches));
        return branches;
    }
}