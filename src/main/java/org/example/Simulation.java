package org.example;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Simulation extends PApplet {
    private ArrayList<PVector> input;
    private PolynomialGD polynomial;

    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void setup() {
        input = genRandomPoints(100);
        polynomial = new PolynomialGD(2);
    }

    @Override
    public void draw() {
        background(255);
        frameRate(10);
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

        polynomial.gradientDescent(input);
    }

    private final float a = 0.2f, b = -1, c = -5;

    public ArrayList<PVector> genRandomPoints(int n) {
        var points = new ArrayList<PVector>();
        for (int i = 0; i < n; i++) {
            float x = random(-10, 10);
            float noise = random(-1.5f, 1.5f);
            points.add(new PVector(x, a*x*x + b*x + c + noise));
        }
        return points;
    }
}
