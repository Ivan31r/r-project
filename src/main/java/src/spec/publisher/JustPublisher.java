package src.spec.publisher;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import src.spec.Publisher;
import src.spec.Subscriber;
import src.spec.Subscription;

@AllArgsConstructor
public class JustPublisher<T> implements Publisher<T> {
    private final T[] value;

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        Subscription subscription = new JustSubscription(subscriber);
        subscriber.onSubscribe(subscription);

    }

    @RequiredArgsConstructor
    private class JustSubscription implements Subscription {
        private final Subscriber<T> subscriber;
        private int position = 0;

        @Override
        public void request(int n) {
            for (int i = 0; i < n; i++) {
                if (position == value.length) {
                    subscriber.onComplete();
                    return;
                }
                subscriber.onNext(value[position++]);
            }

        }
    }
}
