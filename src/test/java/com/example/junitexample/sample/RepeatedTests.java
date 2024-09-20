package com.example.junitexample.sample;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

@Slf4j
@DisplayName("반복테스트 샘플")
public class RepeatedTests {

    @RepeatedTest(value = 5, name = "{totalRepetitions} 회중  {currentRepetition}번째 테스트")
    @DisplayName("5회 반복테스트")
    void repeatedTest() {
        Random random = new Random();
        int randomValue = Math.abs(random.nextInt()) % 10; //양수 10미만
        log.info("RandomValue >>> {}", randomValue);
        Assertions.assertTrue(randomValue < 5);
    }
}
