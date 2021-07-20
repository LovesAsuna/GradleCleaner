package com.hyosakura.cleaner;

import com.hyosakura.cleaner.strategy.CleanStrategy;
import com.hyosakura.cleaner.struct.Dependency;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * @author LovesAsuna
 **/
@Slf4j
public class Cleaner {
    private final String gradleHome;

    private Cleaner(String gradleHome) {
        this.gradleHome = gradleHome;
    }

    public static Cleaner newInstance(String gradleHome) {
        return new Cleaner(gradleHome);
    }

    public void cleanAll() {
        clean(getAllDependency(), CleanStrategy.CleanStrategyEnum.DEFAULT);
    }

    public void clean(Collection<Dependency> dependencies, CleanStrategy strategy) {
        try {
            dependencies.forEach(strategy.getStrategy());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Dependency> getAllDependency() {
        Path start = Paths.get(gradleHome).resolve("caches\\modules-2\\files-2.1");
        Set<Dependency> dependencies = new HashSet<>();
        try {
            Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
                private String group;
                private String name;
                private Set<String> versions;
                private Path path;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    int count = start.relativize(dir).getNameCount();
                    if (count == 3) {
                        Path relativizePath = start.relativize(dir);
                        String currentName = relativizePath.getParent().getFileName().toString();
                        String currentGroup = relativizePath.getParent().getParent().getFileName().toString();
                        String version = relativizePath.getFileName().toString();
                        if (!currentGroup.equals(group) ||!currentName.equals(name)) {
                            if (versions == null) {
                                versions = new HashSet<>();
                                versions.add(version);
                            } else {
                                Dependency dependency = new Dependency(group, name, path, versions);
                                dependencies.add(dependency);
                                versions = null;
                            }
                            group = currentGroup;
                            name = currentName;
                            path = dir.getParent();
                            return FileVisitResult.CONTINUE;
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            return null;
        }
        return dependencies;
    }

}
