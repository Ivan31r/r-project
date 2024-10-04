package src.spec.subscriber;

import lombok.RequiredArgsConstructor;
import src.spec.Subscriber;
import src.spec.Subscription;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class ConsumingSubscriber<T> implements Subscriber<T> {

    private final Consumer<T> consumer;
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(Integer.MAX_VALUE);

    }

    @Override
    public void onNext(T value) {
        consumer.accept(value);
    }

    @Override
    public void onComplete() {
        System.out.println("THE END");
    }
}
