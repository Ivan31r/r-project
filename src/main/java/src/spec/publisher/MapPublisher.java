package src.spec.publisher;

import lombok.RequiredArgsConstructor;
import src.spec.Publisher;
import src.spec.Subscriber;
import src.spec.Subscription;

import java.util.function.Function;

@RequiredArgsConstructor
public class MapPublisher<T, R> implements Publisher<R> {

    private final Publisher<T> source;
    private final Function<T, R> mapper;

    @Override
    public void subscribe(Subscriber<R> subscriber) {
        source.subscribe(new MapSubscriber(subscriber));
    }

    @RequiredArgsConstructor
    private class MapSubscriber implements Subscriber<T> {

        private final Subscriber<? super R> subscriber;

        @Override
        public void onSubscribe(Subscription subscription) {
            subscriber.onSubscribe(subscription);
        }

        @Override
        public void onNext(T value) {
            subscriber.onNext(mapper.apply(value));
        }

        @Override
        public void onComplete() {
            subscriber.onComplete();
        }
    }
}
