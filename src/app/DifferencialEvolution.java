package app;

import model.Individual;

import java.util.*;

public class DifferencialEvolution {
  private final int QTD_POP_INI = 20;
  private final double F = 0.5;
  private final double CROSSOVER_RATE = 0.5;

  private final int MAX_VALUE = 20;
  private final int MIN_VALUE = -20;
  private static final Random random = new Random();

  public static void main(String[] args) {
    DifferencialEvolution diffEvol = new DifferencialEvolution();
    Individual bestIndividual = diffEvol.init();

    System.out.println(bestIndividual);
  }

  private Individual init() {
    int numGen = 1;
    int MAX_GEN = 100;

    List<Individual> popInd = generateRandomIndividuals();
    evaluateIndividuals(popInd);

    while (numGen <= MAX_GEN) {
      List<Individual> newPop = new ArrayList<>(QTD_POP_INI);

      for (int i = 0; i < QTD_POP_INI; i++) {
        Individual u = generateUInd(popInd);

        Individual exp = recombine(popInd.get(i), u);
        exp.evaluate();

        if (exp.getFunctionValue() < popInd.get(i).getFunctionValue()) {
          newPop.add(exp);
        } else {
          newPop.add(popInd.get(i));
        }
      }
      popInd = newPop;
      printIndividual(popInd, numGen);
      numGen++;
    }

    Collections.sort(popInd);
    return popInd.get(0);
  }

  private void printIndividual(List<Individual> popInd, int numGen) {
    Individual betterInd = popInd.stream()
            .min(Comparator.comparing(Individual::getFunctionValue))
            .orElseThrow(NoSuchElementException::new);

    System.out.printf("Geração: %d - Melhor ind.: %f \n", numGen, betterInd.getFunctionValue());
  }

  private List<Individual> generateRandomIndividuals() {
    List<Individual> individuals = new ArrayList<>();
    for (int i = 0; i < QTD_POP_INI; i++) {
      individuals.add(new Individual(MIN_VALUE, MAX_VALUE));
    }
    return individuals;
  }

  private void evaluateIndividuals(List<Individual> individuals) {
    for (Individual individual : individuals) {
      individual.evaluate();
    }
  }

  private Individual generateUInd(List<Individual> popInd) {
    Individual u = new Individual();
    int randomIndex1 = random.nextInt(QTD_POP_INI);
    int randomIndex2 = random.nextInt(QTD_POP_INI);
    int randomIndex3 = random.nextInt(QTD_POP_INI);

    Individual ind1 = popInd.get(randomIndex1);
    Individual ind2 = popInd.get(randomIndex2);
    Individual ind3 = popInd.get(randomIndex3);

    double[] val = new double[2];

    for (int i = 0; i < val.length; i++) {
      val[i] = ind3.getGenes()[i] + (F * (ind1.getGenes()[i] - ind2.getGenes()[i]));
    }
    u.setGenes(val);

    return u;
  }

  private Individual recombine(Individual individual, Individual u) {
    Individual son = new Individual(MIN_VALUE, MAX_VALUE);

    for (int i = 0; i < 2; i++) {
      double r = random.nextDouble();

      if (r < CROSSOVER_RATE) {
        son.getGenes()[i] = individual.getGenes()[i];
      } else {
        son.getGenes()[i] = u.getGenes()[i];
      }
    }

    return son;
  }
}