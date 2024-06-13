package io.github.tml.core.monitor;

// 消息反馈
public interface InfoFeedback<T> {
    void feedback(T msg);
}
