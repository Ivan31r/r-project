package src.spec.publisher;

import lombok.RequiredArgsConstructor;
import src.spec.Publisher;
import src.spec.Subscriber;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class DeferredPublisher<T> implements Publisher<T> {
    private final Supplier<Publisher<T>> supplier;

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        supplier.get().subscribe(subscriber);
    }
}
