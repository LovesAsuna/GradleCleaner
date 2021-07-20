package com.hyosakura.cleaner.strategy;

import com.hyosakura.cleaner.struct.Dependency;
import com.hyosakura.cleaner.util.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author LovesAsuna
 **/
@FunctionalInterface
public interface CleanStrategy extends Strategy<Dependency> {

    Consumer<Dependency> getStrategy();

    enum CleanStrategyEnum implements CleanStrategy {
        DEFAULT {
            public Consumer<Dependency> getStrategy() {
                return dependency -> {
                    List<String> list = VersionStrategy.VersionStrategyEnum.DEFAULT.getStrategy().apply(dependency);
                    if (list.size() == 1) {
                        return;
                    }
                    for (int i = 0; i < list.size() - 1; i++) {
                        Path path = dependency.getPath().resolve(Paths.get(list.get(i)));
                        try {
                            FileUtil.deleteDir(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
            }
        }
    }

}


