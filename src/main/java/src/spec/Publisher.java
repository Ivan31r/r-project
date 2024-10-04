package src.spec;

import src.spec.publisher.DeferredPublisher;
import src.spec.publisher.JustPublisher;
import src.spec.publisher.MapPublisher;
import src.spec.publisher.ParallelPublisher;
import src.spec.subscriber.CollectingSubscriber;
import src.spec.subscriber.ConsumingSubscriber;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Publisher<T> {
    void subscribe(Subscriber<T> subscriber);

    @SafeVarargs
    static <V> Publisher<V> just(V... values) {
        return new JustPublisher<>(values);
    }

    static <V> Publisher<V> deferred(Supplier<Publisher<V>> supplier) {
        return new DeferredPublisher<>(supplier);
    }

    static <V> Publisher<V> from(Supplier<V> supplier) {
        return new DeferredPublisher<>(() -> Publisher.just(supplier.get()));
    }


    default List<T> collect() {
        CollectingSubscriber<T> subscriber = new CollectingSubscriber<>();
        subscribe(subscriber);
        return subscriber.blockingGetResults();
    }

    default <R> Publisher<R> map(Function<T, R> mapper) {
        return new MapPublisher<>(this, mapper);
    }

    default Publisher<T> peek(Consumer<T> consumer) {
        return new MapPublisher<>(this, v -> {
            consumer.accept(v);
            return v;
        });
    }

    default void consume(Consumer<T> consumer) {
        subscribe(new ConsumingSubscriber<>(consumer));
    }

    default Publisher<T> parallel(int parallelism) {
        return new ParallelPublisher<>(this, parallelism);
    }

}
