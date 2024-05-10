package br.com.sum.test.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Numbers {

    public String numberOne, numberTwo;

    public Numbers(String numberOne, String numberTwo, Double result) {
        this.numberOne = numberOne;
        this.numberTwo = numberTwo;
    }


}
