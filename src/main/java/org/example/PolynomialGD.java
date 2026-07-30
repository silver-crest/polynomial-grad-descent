package org.example;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.Collections;

public class PolynomialGD {
    public static final double LEARNING_RATE = 0.001;

    private final ArrayList<Integer> coefficients;

    public PolynomialGD(int degree) {
        coefficients = new ArrayList<>(degree);
        Collections.fill(coefficients, 0);
    }

    public void gradientDescent(ArrayList<PVector> points) {

    }

}
