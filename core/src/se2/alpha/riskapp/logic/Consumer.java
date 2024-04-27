package se2.alpha.riskapp.logic;

@FunctionalInterface
public interface Consumer<T> {
    void call(T t);
}
