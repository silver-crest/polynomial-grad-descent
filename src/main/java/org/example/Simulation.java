package org.example;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Simulation extends PApplet {
    private ArrayList<PVector> input;
    private PolynomialGD polynomial;
    private PolynomialGD target;

    private final int DEGREE = 3;

    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void setup() {
        polynomial = new PolynomialGD(DEGREE);

        target = new PolynomialGD(DEGREE);
        target.setCoefficients(List.of(-1f, 1f, 0.25f, -0.125f));

        input = genRandomPoints(100);
    }

    @Override
    public void draw() {
        background(255);
        fill(0);
        displayCoeffs(polynomial, 20, 20);

        pushMatrix();
        translate(width / 2, height / 2);
        scale(40, -40);

        for (PVector p : input) {
            noStroke();
            fill(255, 0 , 0);
            circle(p.x, p.y, 10f/40);
        }

        stroke(0);
        strokeWeight(2f/40);
        PVector lastPt = null;
        for (float x = -10; x <= 10; x += 0.1f) {
            PVector currentPt = new PVector(x, polynomial.eval(x));
            if (lastPt != null) {
                line(lastPt.x, lastPt.y, currentPt.x, currentPt.y);
            }
            lastPt = currentPt;
        }

        popMatrix();

        for (int i = 0; i < 100; i++)
            polynomial.gradientDescent(input);
    }

    public void displayCoeffs(PolynomialGD polynomial, int x, int y) {
        StringBuilder dis = new StringBuilder();
        ArrayList<Float> coefficients = polynomial.getCoefficients();

        for (int i = 0; i < coefficients.size(); i++) {
            double coeff = coefficients.get(i);
            dis.append(String.format("%c = %.2f\n", 'a' + i, coeff));
        }
        text(dis.toString(), x, y);
    }

    public ArrayList<PVector> genRandomPoints(int n) {
        var points = new ArrayList<PVector>();
        for (int i = 0; i < n; i++) {
            float x = random(-10, 10);
            float noise = random(-0.7f, 0.7f);
            points.add(new PVector(x, target.eval(x) + noise));
        }
        return points;
    }
}
