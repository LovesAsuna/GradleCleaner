package com.hyosakura.cleaner.strategy;

import com.hyosakura.cleaner.struct.Dependency;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author LovesAsuna
 **/
@FunctionalInterface
public interface VersionStrategy extends Strategy<Dependency> {
    Function<Dependency, List<String>> getStrategy();

    enum VersionStrategyEnum implements VersionStrategy {
        DEFAULT {
            @Override
            public Function<Dependency, List<String>> getStrategy() {
                return d -> d.getVersions().stream().sorted((o1, o2) -> {
                    String[] split1 = o1.split("\\.");
                    String[] split2 = o2.split("\\.");
                    for (int i = 0; i < (Math.max(split1.length, split2.length)); i++) {
                        if (split1[i].compareTo(split2[i]) == 0) {
                            continue;
                        }
                        return split1[i].compareTo(split2[i]);
                    }
                    return 0;
                }).collect(Collectors.toCollection(ArrayList::new));
            }
        }
    }
}

