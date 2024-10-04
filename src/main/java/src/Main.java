package src;

import lombok.SneakyThrows;
import src.spec.Publisher;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
//        List<Integer> list = Publisher.just(1, 2, 3)
//                .peek(System.out::println)
//                .map(el -> el + 1)
//                .peek(System.out::println)
//                .collect();
//        System.out.println(list);
//        System.out.println("-----------------");
//        Publisher.just(10, 20, 30)
//                .peek(System.out::println)
//                .map(i -> i * 2)
//                .consume(System.out::println);
        System.out.println("-----------------");
        Publisher.just(100, 200, 300)
                .map(i -> i * 2)
                .parallel(10)
                .map(Main::map)
                .consume(System.out::println);
        System.out.println("-----------------");
        Thread.sleep(15000);


    }

    private static int map(Integer el) {
        try {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return el + 11;
    }
}
