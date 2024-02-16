package org.hospital.services.notification.template;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.common.model.NotificationDetails;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TemplateResolverServiceImpl implements TemplateResolverService {
    private final SpringTemplateEngine springTemplateEngine;

    @Override
    public String resolveContent(String templateName, NotificationDetails notificationDetails) {
        final Map<String, Object> variablesMap = notificationDetails.getItems()
                .stream()
                .collect(Collectors.toUnmodifiableMap(NotificationDetails.KeyValueItem::getKey, NotificationDetails.KeyValueItem::getValue));

        final Context context = new Context();
        context.setVariables(variablesMap);

        return springTemplateEngine.process(templateName, context);
    }
}
