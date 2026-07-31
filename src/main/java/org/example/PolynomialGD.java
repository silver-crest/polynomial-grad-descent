package org.example;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PolynomialGD {
    public static final float LEARNING_RATE = 0.000001f;
    public static final float DELTA = 0.001f;

    private final int degree;
    private final ArrayList<Float> coefficients;

    public PolynomialGD(int degree) {
        this.degree = degree;
        coefficients = new ArrayList<>(Collections.nCopies(degree + 1, 0f));
    }

    public PolynomialGD(List<Float> coefficients) {
        this(coefficients.size() - 1);
        for (int i = 0; i < coefficients.size(); i++)
            updateCoeff(i, coefficients.get(i));
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

    public ArrayList<Float> getGradient(ArrayList<PVector> points) {
        var gradient = new ArrayList<Float>();
        for (int d = 0; d <= degree; d++) {
            var copy = new PolynomialGD(this);
            copy.offsetCoeff(d, DELTA);
            gradient.add(
                    (copy.getCost(points) - getCost(points)) / DELTA
            );
        }
        return gradient;
    }

    public float getCost(ArrayList<PVector> points) {
        float cost = 0;
        for (PVector p : points)
            cost += (float) Math.pow(eval(p) - p.y, 2);
        return cost / points.size();
    }

    public float eval(float x) {
        float result = 0;
        for (int d = 0; d <= degree; d++)
            result += (float) (getCoeff(d) * Math.pow(x, d));
        return result;
    }

    // Only uses point.x
    public float eval(PVector p) {
        return eval(p.x);
    }

    public int getDegree() {
        return degree;
    }

    public float getCoeff(int degree) {
        return coefficients.get(degree);
    }

    public ArrayList<Float> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(List<Float> coefficients) {
        if (coefficients.size() != this.coefficients.size())
            throw new RuntimeException("coefficients size mismatch");

        for (int i = 0; i < this.coefficients.size(); i++) {
            updateCoeff(i, coefficients.get(i));
        }
    }

    public void updateCoeff(int degree, float newCoeff) {
        coefficients.set(degree, newCoeff);
    }

    public void offsetCoeff(int degree, float offset) {
        updateCoeff(degree, getCoeff(degree) + offset);
    }

}
