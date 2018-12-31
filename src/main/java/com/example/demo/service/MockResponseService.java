package com.example.demo.service;

import com.example.demo.repository.domain.MockConfiguration;
import com.example.demo.repository.domain.MockResponse;
import com.example.demo.request.MockRequestStrategy;
import com.example.demo.request.RequestData;
import com.example.demo.rules.RuleWrapper;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class MockResponseService {

    @Autowired
    private List<MockRequestStrategy> strategies;

    public Optional<MockResponse> getResponse(List<MockConfiguration> mocks, RequestData requestData) {
        MockResponse response = null;

        for (MockConfiguration mockConfiguration : mocks) {
            Optional<MockRequestStrategy> strategy = strategies.stream()
                    .filter(s -> s.isApplicable(mockConfiguration, requestData))
                    .findAny();
            Map<String, MockResponse> responses = mockConfiguration.getResponses();
            Object request = strategy.map(s -> s.resolve(requestData)).orElse(null);

            if (request != null) {
                applyRule(new RuleWrapper(request, responses), mockConfiguration.getRule());
                response = responses.values().stream()
                        .filter(MockResponse::isActive)
                        .findAny().orElse(null);
            }
        }

        return Optional.ofNullable(response);
    }

    private void applyRule(RuleWrapper wrapper, String rule) {
        KieServices services = KieServices.Factory.get();
        KieFileSystem system = services.newKieFileSystem();
        system.write("src/main/resources/rules/rule.drl", rule);
        services.newKieBuilder(system).buildAll();

        KieContainer container = services.newKieContainer(services.getRepository().getDefaultReleaseId());
        StatelessKieSession session = container.getKieBase().newStatelessKieSession();
        KieCommands kieCommands = services.getCommands();
        Command command = kieCommands.newInsert(wrapper);
        session.execute(command);

    }
}
