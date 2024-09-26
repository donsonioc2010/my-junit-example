package com.example.junitexample.sample;

import java.time.Duration;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("TimeOut Test Samples")
class TimeOutTest {
    @Test
    @DisplayName("1초 이내로 실행되면 테스트 케이스는 성공한다.")
    void whenIn1Sec_thenTestCaseSuccess() {
        Assertions.assertTimeout(Duration.ofSeconds(1L), () -> {
            long startTime = System.currentTimeMillis();

            log.info("타임아웃 성공케이스 로직 실행");
            int zeroOneHundred = IntStream.range(0, 100).sum();
            log.info("0 ~ 100 합은? >>> {}", zeroOneHundred);
            long totalTime = System.currentTimeMillis() - startTime;
            log.info("타임아웃 성공케이스 종료~, Total 소요시간 >>> {} millSeconds", Duration.ofSeconds(totalTime).getSeconds());
        });
    }

    @Test
    @DisplayName("1초 이외로 실행되면 테스트 케이스는 성공한다.")
    void whenOut1Sec_thenTestCaseSuccess() {
        Assertions.assertThrows(AssertionError.class, () -> {
            Assertions.assertTimeout(Duration.ofSeconds(1L), () -> {
                long startTime = System.currentTimeMillis();
                log.info("타임아웃 실패케이스 로직 실행");
                sleep(1500L);
                long totalTime = System.currentTimeMillis() - startTime;

                log.info("타임아웃 실패케이스 종료~, Total 소요시간 >>> {} millSeconds", Duration.ofSeconds(totalTime).getSeconds());
            });
        },  "1초를 초과했기 때문에 AssertionError가 발생해야 함");
    }

    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
