package org.example;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Simulation extends PApplet {
    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void setup() {

    }

    @Override
    public void draw() {
        super.draw();
    }

    private final float a = 2, b = -1, c = 1;

    public ArrayList<PVector> genRandomPoints(int n) {
        var points = new ArrayList<PVector>();
        for (int i = 0; i < n; i++) {
            float x = random(-10, 10);
            float noise = random(-0.5f, 0.5f);
            points.add(new PVector(x, a*x*x + b*x + c + noise));
        }
    }
}
