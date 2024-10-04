package src.spec;

public interface Subscriber<T> {
    void onSubscribe(Subscription subscription);

    void onNext(T value);

    void onComplete();
}
