package com.example.bidding.parser;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class parse the incoming request parameters to required format.
 */
public class RequestParamsParser implements Function<Map<String, String[]>, Map<String, String>> {

    @Override
    public Map<String, String> apply(Map<String, String[]> map) {
        return map.entrySet().stream()
                .filter(m -> m.getKey() != null && m.getValue() !=null && m.getValue().length >= 1 && !m.getValue()[0].isEmpty())
                .collect(
                        Collectors.toMap(Map.Entry::getKey, e -> (String)e.getValue()[0])
                );
    }
}
