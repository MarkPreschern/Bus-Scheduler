package BusScheduler;

import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
public class Test
//tests busScheduler
{
    public static void main() throws IOException
    //creates a new Input with given variables
    {
        Random rand = new Random();
        int length = rand.nextInt(201) + 400;
        int busses = length/40;
        int stops = length/4;
        int maxPeoplePerBus = stops/busses + 1;
        int[] busGarage = {rand.nextInt(length/2), rand.nextInt(length/2)};
        int[] destination = {rand.nextInt(length/2) + length/2, rand.nextInt(length/2) + length/2};
        ArrayList<Person> stopLocations = gen(stops, busGarage, destination, length);
        Information i = new Information(length, busses, maxPeoplePerBus, busGarage, destination, stopLocations);
    }
    public static ArrayList<Person> gen(int number, int[] start, int[] des, int length) throws IOException
    //generates random people
    {
        Random rand = new Random();
        ArrayList<Person> tempPerson = new ArrayList<Person>();
        ArrayList<String> names = getNames(number);
        int[] ages = new int[number];
        for(int i=0;i<number;i++)
            ages[i] = rand.nextInt(11) + 5;
        ArrayList<Integer[]> loc = new ArrayList<Integer[]>();
        for(int i=0;i<number;i++){
            int[] tempStop = {rand.nextInt(length+1), rand.nextInt(length+1)};
            if(tempStop != start && tempStop != des){
                Integer[] t = {(Integer)tempStop[0], (Integer)tempStop[1]};
                loc.add(t);
            }else i--;            
        }
        
        for(int i=0;i<number;i++){
            int[] temp = {(int)loc.get(i)[0], (int)loc.get(i)[1]};
            Person p = new Person(names.get(i), ages[i], temp);
            tempPerson.add(p);
        }
        return tempPerson;
    }
    public static ArrayList<String> getNames(int number) throws IOException
    //generates a random name
    {
        Random rand = new Random();
        ArrayList<String> tempNames = new ArrayList<String>();
        ArrayList<String> first = new ArrayList<String>();
        ArrayList<String> last = new ArrayList<String>();
        Scanner scan = new Scanner(new FileReader("LastNames.txt"));
        Scanner scan2 = new Scanner(new FileReader("FemaleFirstNames.txt"));
        Scanner scan3 = new Scanner(new FileReader("MaleFirstNames.txt")); 
        
        while(scan.hasNextLine()){ 
            String temp = scan.nextLine();
            String n = "";
            for(int i=0;i<temp.length();i++)
                if(!temp.substring(i,i+1).equals(" "))
                    n = n + temp.substring(i,i+1);
                else break;
            last.add(n);
        }
        scan.close();
        while(scan2.hasNextLine()){ 
            String temp = scan2.nextLine();
            String n = "";
            for(int i=0;i<temp.length();i++)
                if(!temp.substring(i,i+1).equals(" "))
                    n += temp.substring(i,i+1);
                else break;
            first.add(n);
        }
        scan2.close();
        while(scan3.hasNextLine()){ 
            String temp = scan3.nextLine();
            String n = "";
            for(int i=0;i<temp.length();i++){
                if(!temp.substring(i,i+1).equals(" "))
                    n += temp.substring(i,i+1);
                else break;
            }
            first.add(n);
        }
        scan3.close();
        
        for(int i=0;i<number;i++){
            String firstIndex = first.get(rand.nextInt(first.size()));
            String lastIndex = last.get(rand.nextInt(last.size()));
            tempNames.add(firstIndex + " " + lastIndex);
        }
        return tempNames;
    }
}
