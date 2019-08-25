import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args)  {
        Scanner scan = new Scanner(System.in);
        //try{scan = new Scanner(new File("input00.txt"));}catch(Exception e){}
        
        step[] steps = new step[scan.nextInt()];
        passenger[] passengers = new passenger[scan.nextInt()];
        int nitro = scan.nextInt();
        
        loadStuff(scan,steps,passengers);
        addPassengers(steps,passengers);
        calcDepartures(steps);
        //printStations(steps);
        //System.out.println(passengerTime(steps,passengers));
        
        Queue<run> runs = new PriorityQueue();
        findruns(runs,steps);
        //printruns(runs);
        //System.out.println(totalDistance(steps));
        saveNitro(steps,runs,nitro);
        calcDepartures(steps);
        System.out.println(passengerTime(steps,passengers));
        
    }
    
    static void saveNitro(step[] steps,Queue<run> runs,int nitroLimit){
        long targetSaving = totalDistance(steps) - nitroLimit;
        run r;
        int s;
        int x;
        
        while(0<targetSaving){
            r = runs.poll();
            s = r.station;
            x = steps[s].distance - steps[s].travelTime;
            if(x>r.deadline){x=r.deadline;}
            if(x>targetSaving){x=(int)targetSaving;}
            
            //System.out.println("Station "+String.valueOf(s)+", saved "+String.valueOf(x));
            steps[s].travelTime += x;
            r.deadline -= x;
            targetSaving -= x;
            if ((0<s) && (0 < r.deadline)){
                r.carrying += steps[s].dropped;
                r.station--;
                runs.add(r);
            }
        }
    }
    
    static long totalDistance(step[] steps){
        long distance=0;
        for(step s : steps){
            distance += s.distance;
        }
        return distance;
    }
    
    static void printruns(Queue<run> runs){
        for(run r : runs){
            System.out.println("~~~~~~~~");
            System.out.println("station : "+String.valueOf(r.station));
            System.out.println("deadline : "+String.valueOf(r.deadline));
            System.out.println("tocarry : "+String.valueOf(r.carrying));
        }
    }
    
    static void findruns(Queue<run> runs,step[] steps){
        // timeTaken should be 0 for all stations
        steps[steps.length-1].departure = 2000000000;
        for(int i=0;i<steps.length-1;i++){
            if(steps[i].departure < steps[i+1].departure){
                run r = new run();
                r.station = i;
                r.deadline = steps[i+1].departure - steps[i].departure;
                r.carrying = steps[i+1].dropped;
                runs.add(r);
            }
        }
    }
    
    static long passengerTime(step[] steps,passenger[] passengers){
        long total = 0;
        for(passenger p : passengers){
            total += steps[p.dest-1].departure + steps[p.dest-1].travelTime - p.arrival;
        }
        return total;
    }
    
    
    static void calcDepartures(step[] steps){
        int t = 0;
        for (step s : steps){
            if(s.departure < t){
                s.departure = t;
            }else{
                t = s.departure;
            }
            t+=s.travelTime;
        }
    }
    
    static void addPassengers(step[] steps, passenger[] passengers){
        for (passenger p : passengers) {
            if(steps[p.start].departure < p.arrival){
                steps[p.start].departure = p.arrival;
            }
            steps[p.start].pickedUp++;
            steps[p.dest].dropped++;
        }
        
        int load=0;
        for (step s : steps){
            load += s.pickedUp - s.dropped;
            s.carried = load;
        }
    }
    
    static void loadStuff(Scanner scan,step[] steps, passenger[] passengers){
        for(int i=0;i<steps.length-1;i++){
            steps[i] = new step();
            steps[i].distance = scan.nextInt();
            steps[i].departure = 0;
            steps[i].travelTime = 0;
            steps[i].carried = 0;
            steps[i].pickedUp = 0;
            steps[i].dropped = 0;
            
        }
        steps[steps.length-1] = new step();
        
        for(int i=0;i<passengers.length;i++){
            passengers[i] = new passenger();
            passengers[i].arrival = scan.nextInt();
            passengers[i].start = scan.nextInt()-1;
            passengers[i].dest = scan.nextInt()-1;
        }
    }
    
    static void printStations(step[] steps){
        for(step s : steps){
            //System.out.println(" : "+String.valueOf(s));
            System.out.println("-------");
            System.out.println("departure : "+String.valueOf(s.departure));
            System.out.println("distance : "+String.valueOf(s.distance));
            System.out.println("travel time : "+String.valueOf(s.travelTime));
            System.out.println("picked up : "+String.valueOf(s.pickedUp));
            System.out.println("dropped : "+String.valueOf(s.dropped));
            System.out.println("carried : "+String.valueOf(s.carried));
        }
    }
}

class passenger{
    public
    int arrival;
    int start;
    int dest;
}

class step{
public
    int departure;
    int distance;
    int carried;
    int pickedUp;
    int dropped;
    int travelTime;
}

class run implements Comparable<run>{
public
    int station;
    int deadline;
    int carrying;
    
    @Override public int compareTo(run r2){
        return (this.carrying - r2.carrying);
    }
}