/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga_disque;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Kévin
 */
public class Chromosome implements Comparable<Chromosome>{
    private ArrayList<int[]> genes = new ArrayList<>();
    private Double fitness = 0.;
    int diskA = 0;
    int diskB = 0;
    int diskC = 0;
    boolean didMutate = false;
    
    public Chromosome(){
        generateGenes();
        calculateFitness();
    }
    
    public Chromosome(ArrayList<int[]> genes){
        this.genes = genes;
        calculateFitness();
    }
    
    private void generateGenes(){
        for(int i = 0; i < 2; i++){
            int[] allele = new int[7];
            for(int j=0; j<7; j++){
                allele[j] = new Random().nextInt(2);
            }
            genes.add(allele);
        }
    }
    
    private void calculateFitness(){
        diskA = convertInRange(binaryToInt(genes.get(0)), 10, 80);
        diskB = convertInRange(binaryToInt(genes.get(1)), 10, 90-diskA);
        diskC = 100 - diskA - diskB;
        
        double aDiskA = calculateArea(diskA);
        double aDiskB = calculateArea(diskB);
        double aDiskC = calculateArea(diskC);
        fitness = 7853.98163397 - aDiskA - aDiskB - aDiskC;
    }
    
    private int binaryToInt(int[] bitTab){
        int sum = 0;
        
        for(int i = 6; i >=0 ; i--){
            sum += bitTab[i]*Math.pow(2, i);
        }
        
        return sum;
    }
    
    private double calculateArea(double diametre){
        return Math.PI*Math.pow(diametre/2, 2);
    }
    
    private int convertInRange(int value, int min, int max){
        double percent = (double) value/127.;
        int realValue = (int) Math.floor((double)(max-min)*percent+min);
        return realValue;
    }
    
    public int[] getGene(int index){
        return genes.get(index);
    }
    
    public void mutate(int geneNbr, int alleleNbr){
        if(genes.get(geneNbr)[alleleNbr] == 1){
            genes.get(geneNbr)[alleleNbr] = 0;
        }
        else{
            genes.get(geneNbr)[alleleNbr] = 1;
        }
        calculateFitness();
        this.didMutate = true;
    }
    
    public Double getFitness(){ return fitness; }
    
    @Override
    public String toString(){
        return diskA+" "+diskB+" "+diskC+" Fitness : "+fitness+((didMutate) ? " M" : "");
    }

    @Override
    public int compareTo(Chromosome o) {
        return fitness.compareTo(o.fitness);
    }
}
