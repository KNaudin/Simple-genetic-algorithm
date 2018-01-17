/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga_disque;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Kévin
 */
public class ga {
    private ArrayList<Chromosome> generation = new ArrayList<>();
    private double mutationRisk = 0;
    private double crossingRisk = 0;
    private int tries = 30;
    
    public ga(int nbIndividuals, double mutationRisk, double crossingRisk, int tries){
        this.mutationRisk = mutationRisk;
        this.crossingRisk = crossingRisk;
        this.tries = tries;
        generateFirstBatch(nbIndividuals);
        displayCurrentGeneration();
        for(int i = 0; i < tries; i++){
            System.out.println("---------------------------GENERATION "+(i+1)+" ----------------------------");
            batchNewGeneration();
            displayCurrentGeneration();
        }
    }
    
    private void generateFirstBatch(int nbIndividuals){
        generation.clear();
        for(int i = 0; i < nbIndividuals; i++){
            generation.add(new Chromosome());
        }
        Collections.sort(generation);
    }
    
    private void batchNewGeneration(){
        ArrayList<Chromosome> newGen = new ArrayList<>();
        int totalFitness = (int) Math.floor(getTotalFitness());
        for(int i = 0; i < generation.size()/2; i++){
            Chromosome[] chosen = new Chromosome[2];
            for(int j = 0; j < 2; j++){
                chosen[j] = selectOne(totalFitness);
            }
            newGen.addAll(cross1Point(chosen[0], chosen[1]));
        }
        Collections.sort(newGen);
        generation = newGen;
    }
    
    private Chromosome selectOne(int totalFitness){
        int stopValue = new Random().nextInt(totalFitness+1);
        double currentValue = 0;
        int index = 0;
        if(stopValue == 0)
            return generation.get(0);
        while(currentValue < stopValue){
            currentValue += generation.get(index).getFitness();
            index++;
        }
        return generation.get(index-1);
    }
    
    private ArrayList<Chromosome> cross1Point(Chromosome a, Chromosome b){
        ArrayList<Chromosome> offspring = new ArrayList<>();
        if(new Random().nextDouble() <= crossingRisk)
        {
            ArrayList<int[]> g1 = new ArrayList<>();
            ArrayList<int[]> g2 = new ArrayList<>();

            int crossPoint = new Random().nextInt(2);
            for(int i = 0; i < crossPoint; i++){
                g1.add(a.getGene(i));
                g2.add(b.getGene(i));
            }
            for(int j = crossPoint; j <= 1; j++){
                g1.add(b.getGene(j));
                g2.add(a.getGene(j));
            }
            Chromosome c = new Chromosome(g1);
            Chromosome d = new Chromosome(g1);
            mutate(c);
            mutate(d);
            offspring.add(c);
            offspring.add(d);
        }
        else{
            offspring.add(a);
            offspring.add(b);
        }
        return offspring;
    }
    
    private void mutate(Chromosome c){
        if(new Random().nextDouble() <= mutationRisk)
        {
            int mutationIn = new Random().nextInt(2);
            int mutationOn = new Random().nextInt(7);
            c.mutate(mutationIn, mutationOn);
        }
    }
    
    private double getTotalFitness(){
        double totalFitness = 0;
        for(Chromosome c : generation){
            totalFitness += c.getFitness();
        }
        return totalFitness;
    }
    
    public void displayCurrentGeneration(){
        generation.forEach((c) -> {
            System.out.println(c);
        });
    }
}
