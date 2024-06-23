package io.github.tml.core.starter;

public interface Starter {
    void start();

    void afterStart();

    int order(); // 启动顺序
}
