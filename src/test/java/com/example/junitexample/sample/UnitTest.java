package com.example.junitexample.sample;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


class UnitTest {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    class Calculator {
        public int add(int firstNumber, int secondNumber) {
            return firstNumber + secondNumber;
        }

        public int minus(int firstNumber, int secondNumber) {
            return firstNumber - secondNumber;
        }

        public int multiple(int firstNumber, int secondNumber) {
            return firstNumber * secondNumber;
        }

        public int divide(int firstNumber, int secondNumber) {
            return firstNumber / secondNumber;
        }

        public int mod(int firstNumber, int secondNumber) {
            return firstNumber % secondNumber;
        }
    }

    Calculator calculator = new Calculator();

    @Test
    void addTest() {
        int firstNumber = 2;
        int secondNumber = 3;
        int result = calculator.add(firstNumber, secondNumber);

        Assertions.assertEquals(5, result);
    }


    @Test
    public void 이거는_테스트_케이스_입니다() {
        System.out.println("5");
    }


}