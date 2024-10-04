package src.spec.publisher;

import lombok.RequiredArgsConstructor;
import src.spec.Publisher;
import src.spec.Subscriber;
import src.spec.Subscription;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ParallelPublisher<T> implements Publisher<T> {
    private final Publisher<T> upstream;
    private final ThreadPoolExecutor executor;

    public ParallelPublisher(Publisher<T> upstream, int parallelism) {
        this.upstream = upstream;
        executor = new ThreadPoolExecutor(
                parallelism,
                parallelism,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        upstream.subscribe(new ParrallelSubscriber(subscriber));
    }

    @RequiredArgsConstructor
    private class ParrallelSubscriber implements Subscriber<T> {
        private final Subscriber<? super T> subscriber;

        @Override
        public void onSubscribe(Subscription subscription) {
            subscriber.onSubscribe(subscription);
        }

        @Override
        public void onNext(T value) {
            executor.submit(() -> subscriber.onNext(value));

        }

        @Override
        public void onComplete() {
            subscriber.onComplete();
            executor.shutdown();
        }
    }
}
