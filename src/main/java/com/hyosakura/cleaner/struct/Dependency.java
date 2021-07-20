package com.hyosakura.cleaner.struct;

import java.nio.file.Path;
import java.util.Set;

/**
 * @author LovesAsuna
 **/
public class Dependency {
    private String group;
    private String name;
    private Path path;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Set<String> getVersions() {
        return versions;
    }

    public void setVersions(Set<String> versions) {
        this.versions = versions;
    }

    private Set<String> versions;

    public Dependency(String group, String name, Path path) {
        this(group, name, path, null);
    }

    public Dependency(String group, String name, Path path, Set<String> versions) {
        this.group = group;
        this.name = name;
        this.path = path;
        this.versions = versions;
    }

    @Override
    public String toString() {
        return "group: " + group + ", name: " + name + ", version: " + versions;
    }
}
