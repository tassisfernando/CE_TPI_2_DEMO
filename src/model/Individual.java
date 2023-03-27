package model;

import java.util.Arrays;
import java.util.Random;

public class Individual implements Comparable<Individual> {

  private static final int DIMENSIONS = 2;
  private final Random random;

  private double[] genes = new double[2];
  private double functionValue;

  public Individual(int min, int max) {
    this.random = new Random();
    createRandomGenes(max, min);
  }

  public Individual() {
    this.random = new Random();
  }

  private void createRandomGenes(int min, int max) {
    for (int i = 0; i < DIMENSIONS; i++) {
      genes[i] = random.nextDouble() * max * 2 + min;
    }
  }

  public void evaluate() {
    this.functionValue = Math.pow(this.genes[0], 2) + Math.pow(this.genes[1], 2);
  }

  public double getFunctionValue() {
    return functionValue;
  }

  public double[] getGenes() {
    return genes;
  }

  public void setGenes(double[] genes) {
    this.genes = genes;
  }

  @Override
  public int compareTo(Individual another) {
    if (this.functionValue == another.functionValue) {
      return 0;
    }

    return functionValue > another.functionValue ? 1 : -1;
  }

  @Override
  public String toString() {
    return "Genes: \n" +
            Arrays.toString(this.genes) +
            "\nAvaliação: " +
            this.functionValue;
  }
}