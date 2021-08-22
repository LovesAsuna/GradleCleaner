package com.hyosakura.cleaner;

import picocli.CommandLine;

/**
 * @author LovesAsuna
 **/
public class Main {
    public static void main(String[] args) {
        Options Options = new Options();
        new CommandLine(Options).execute(args);
    }

    @CommandLine.Command(name = "")
    private static class Options implements Runnable {
        @CommandLine.Option(names = {"-h", "--home"}, required = true, description = "Gradle的位置")
        private String gradleHome;

        @CommandLine.ArgGroup(multiplicity = "1")
        Exclusive exclusive;

        private static class Exclusive {
            @CommandLine.Option(names = {"-l", "--list"}, description = "列出所有Gradle依赖")
            private boolean list;

            @CommandLine.Option(names = {"-d", "--delete"}, description = "保留最新版本的Gradle依赖")
            private boolean delete;
        }

        @Override
        public void run() {
            Cleaner cleaner = Cleaner.newInstance(gradleHome);
            if (exclusive.list) {
                cleaner.getAllDependency().forEach(System.out::println);
            } else if (exclusive.delete) {
                cleaner.cleanAll();
            }
        }
    }
}
