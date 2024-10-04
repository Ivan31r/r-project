package src.spec.gpt.sort;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Files> files = List.of(
                Files.builder().fileLength(10L).filename("name1.xml").build(),
                Files.builder().fileLength(10L).filename("name2.xml").build(),
                Files.builder().fileLength(10L).filename("name3.xml").build(),
                Files.builder().fileLength(10L).filename("name1.sign").build(),
                Files.builder().fileLength(10L).filename("name2.sign").build()
        );

        Map<String, List<Files>> collect = files.stream().collect(Collectors.groupingBy(file -> {
            String filename = file.filename;
            int pointIndex = filename.lastIndexOf('.');
            return pointIndex != -1 ? filename.substring(0, pointIndex) : filename;
        }));
        Map<String, List<Files>> filesWithPair = new HashMap<>();
        Map<String, List<Files>> filesWithoutPair = new HashMap<>();
        collect.forEach((key, value) -> {
            if (value.size() > 1) {
                filesWithPair.put(key, value);
            } else {
                filesWithoutPair.put(key, value);
            }
        });

        System.out.println("Map of files with pair of file + sign");
        filesWithPair.forEach((key, value) -> {
            if(value.get(0).getFilename().contains(".xml")){
                System.out.println("value(0) has xml format");
            }else {
                System.out.println("value() has xml format");
            }
//            System.out.println(key + ": " + value);
        });

//        System.out.println("Map of files without pair. Miss this files");
//        filesWithoutPair.forEach((key, value) -> {
//            System.out.println(key + ": " + value);
//        });

    }

    @Data
    @Builder
    static class Files {
        private String filename;
        private Long fileLength;
    }
}
