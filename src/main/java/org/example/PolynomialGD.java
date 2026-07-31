package org.example;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.Collections;

public class PolynomialGD {
    public static final double LEARNING_RATE = 0.001;
    public static final double DELTA = 0.001;

    private final int degree;
    private final ArrayList<Double> coefficients;

    public PolynomialGD(int degree) {
        this.degree = degree;
        coefficients = new ArrayList<>(degree);
        Collections.fill(coefficients, 0.0);
    }

    // Copy constructor
    public PolynomialGD(PolynomialGD p) {
        degree = p.degree;
        coefficients = new ArrayList<>(p.coefficients);
    }

    public void gradientDescent(ArrayList<PVector> points) {
        var gradient = getGradient(points);
        for (int d = 0; d <= degree; d++) {
            offsetCoeff(d, -LEARNING_RATE * gradient.get(d));
        }
    }

    public ArrayList<Double> getGradient(ArrayList<PVector> points) {
        var gradient = new ArrayList<Double>();
        for (int d = 0; d <= degree; d++) {
            var copy = new PolynomialGD(this);
            copy.offsetCoeff(d, DELTA);
            gradient.add(copy.getCost(points));
        }
        return gradient;
    }

    public double getCost(ArrayList<PVector> points) {
        double cost = 0;
        for (PVector p : points)
            cost += Math.pow(eval(p) - p.y, 2);
        return cost;
    }

    public double eval(double x) {
        double result = 0;
        for (int d = 0; d <= degree; d++)
            result += getCoeff(d) * Math.pow(x, degree);
        return result;
    }

    // Only uses point.x
    public double eval(PVector p) {
        return eval(p.x);
    }

    public int getDegree() {
        return degree;
    }

    public double getCoeff(int degree) {
        return coefficients.get(degree);
    }

    public void updateCoeff(int degree, double newCoeff) {
        coefficients.set(degree, newCoeff);
    }

    public void offsetCoeff(int degree, double offset) {
        updateCoeff(degree, getCoeff(degree) + offset);
    }

}
