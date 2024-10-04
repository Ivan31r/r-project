package src.spec.subscriber;

import src.spec.Subscriber;
import src.spec.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CollectingSubscriber<T> implements Subscriber<T> {

    private Subscription subscription;
    private final List<T> results = new ArrayList<>();
    private volatile boolean isComplete = false;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;

    }

    @Override
    public void onNext(T value) {
        results.add(value);

    }

    @Override
    public void onComplete() {
        this.isComplete = true;
    }

    public List<T> blockingGetResults() {
        Objects.requireNonNull(subscription, "subscription is null");
        subscription.request(Integer.MAX_VALUE);
        while (!isComplete) {
            Thread.onSpinWait();
        }
        return results;
    }
}
