package io.github.tml.core;

/**
 * 将对象转为不可变对象
 * @param <T>
 */
public class FinalWrapper <T>{

    private final T value;

    public FinalWrapper(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

}
