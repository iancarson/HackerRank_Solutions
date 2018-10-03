import java.io.*;
import java.util.*;

public class Solution {
    
    static Map<Integer,Map<Integer,Integer>> threeSecondBombs = new HashMap<>();
    static Map<Integer,Map<Integer,Integer>> twoSecondBombs = new HashMap<>();
    static Map<Integer,Map<Integer,Integer>> oneSecondBombs = new HashMap<>();
    static Map<Integer,Map<Integer,Integer>> damagedBombs = new HashMap<>();

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner input = new Scanner(System.in);
        int row = input.nextInt();
        int col = input.nextInt();
        int n = input.nextInt();
        input.nextLine(); 
        
        if(n % 2 == 0)//If n is even we always have a full grid of bombs
        {
            n = 2;
        }
        else if(n > 3) //We are in a repeated pattern(See example above) so we only do either 5 or 7 iterations
        {
            n = (n % 4)+4;
        }
        
        //Initialze variables according to input grid
        char[][] grid = new char[row][col];
        
        for(int i = 0; i < row; i++)
        {
            String readRow  = input.nextLine();
            for(int j = 0; j < col; j++)
            {
                if(readRow.charAt(j) == 'O')
                {
                    if(threeSecondBombs.get(i) == null)
                    {
                        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
                        threeSecondBombs.put(i, map);  
                        threeSecondBombs.get(i).put(j,0);
                    }
                    else
                    {
                        threeSecondBombs.get(i).put(j,0);
                    }
                }
                    
                    
                    
                grid[i][j] = readRow.charAt(j);
            }
        }
        
        
        int cycle = 2;
        
        //Plant all the 2 second bombs
        if(cycle <= n)//2 second cycle
        {
            
            plantBombs(twoSecondBombs, grid);
            cycle++;
            //System.out.println("Plant 2 sec bombs");
            //System.out.println("Cycle: 2");
            //printGrid(grid);
        }        
        
        if(cycle <= n)//3 second cycle
        {   
            detonateBombs(threeSecondBombs, grid);
            threeSecondBombs = new HashMap<>();
            cycle++;
            //System.out.println("Detonate 3 sec bombs");
            //System.out.println("Cycle: 3");
            //printGrid(grid);
        }
        
              
        //All future cycles
        
        //These will function as switches where false is place bomb and true is detonate bomb
        boolean one = false;
        boolean two = true;
        boolean three = false;
        
        while(cycle <= n)
        {
            //System.out.println("Cycle: "+cycle);
            
            if(cycle % 3 == 1)//One cycle
            {
                if(!one)
                {
                    plantBombs(oneSecondBombs, grid);
                    one = !one;
                    //System.out.println("Plant 1 sec bombs");
                    
                }
                else
                {
                    detonateBombs(oneSecondBombs, grid);
                    one = !one;
                    //System.out.println("Detonate 1 sec bombs");
                }
            }
            else if(cycle % 3 == 2)//Two cycle
            {
                if(!two)
                {
                    plantBombs(twoSecondBombs, grid);
                    two = !two;
                    //System.out.println("Plant 2 sec bombs");
                }
                else
                {
                    detonateBombs(twoSecondBombs, grid);
                    two = !two;
                    //System.out.println("Detonate 2 sec bombs");
                }
            }
            else if(cycle % 3 == 0)//Three cycle
            {
                if(!three)
                {
                    plantBombs(threeSecondBombs, grid);
                    three = !three;
                    //System.out.println("Plant 3 sec bombs");
                }
                else
                {
                    detonateBombs(threeSecondBombs, grid);
                    three = !three;
                    //System.out.println("Detonate 3 sec bombs");                    
                }
            }
            cycle++;
            //printGrid(grid); //Grid after each cycle
        }    
        
        //Print the output grid
        printGrid(grid);
    }
    
    //Plants a bomb on all open tiles
    static void plantBombs(Map<Integer,Map<Integer,Integer>> bombSet, char[][] grid)
    {
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[0].length; j++)
            {
                if(grid[i][j] == '.')
                {
                    //System.out.println("Planting 2s Bomb");
                    if(bombSet.get(i) == null)
                    {
                        //System.out.println("No bomb in row "+i);
                        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
                        bombSet.put(i, map);  
                        bombSet.get(i).put(j,0);
                    }
                    else
                    {
                        bombSet.get(i).put(j,0);
                    }
                    grid[i][j] = 'O';
                }
            }
        }
    }
    
    //Detonates bombs of a given Map updating the other maps and the grid
    static void detonateBombs(Map<Integer,Map<Integer,Integer>> bombSet, char[][] grid)
    {
        
        for(Map.Entry<Integer, Map<Integer,Integer>> x : bombSet.entrySet())
        {
            int px = x.getKey();
            for(Map.Entry<Integer,Integer> y : x.getValue().entrySet())
            {
                removeDamage(px,y.getKey(),grid);
            }
        }

        for(Map.Entry<Integer, Map<Integer,Integer>> x : damagedBombs.entrySet())
        {
            int px = x.getKey();
            for(Map.Entry<Integer,Integer> y : x.getValue().entrySet())
            {
                //System.out.println("Removing Bomb at("+px+","+y.getKey()+")");
                if(threeSecondBombs.get(px) != null)
                {
                    threeSecondBombs.get(px).remove(y.getKey());
                    //System.out.println("Removing 3s Bomb");
                }
                if(twoSecondBombs.get(px) != null)
                {
                    twoSecondBombs.get(px).remove(y.getKey());
                    //System.out.println("Removing 2s Bomb");
                }
                if(oneSecondBombs.get(px) != null)
                {
                    oneSecondBombs.get(px).remove(y.getKey());
                    //System.out.println("Removing 1s Bomb");
                }
            }
        }
        damagedBombs = new HashMap<>();//Remove the bombs now that we have removed all damage
    }
    
    //Replaces all surrounding O with . and adds surrounding to a list of damaged bombs
    static void removeDamage(int x, int y, char[][] grid)
    {
        grid[x][y] = '.';
        removeBomb(x, y);
        
        //Left
        if(y-1 >= 0)
        {
            grid[x][y-1] = '.';
            removeBomb(x, y-1);
        }
            
        //Right
        if(y+1 < grid[0].length)
        {
            grid[x][y+1] = '.';
            removeBomb(x, y+1);
        }
        
        //Up
        if(x-1 >= 0)
        {
            grid[x-1][y] = '.';
            removeBomb(x-1, y);
        }
        
        //Down
        if(x+1 < grid.length)
        {
            grid[x+1][y] = '.';
            removeBomb(x+1, y);
        }
    }
    
    //Adds a bomb to the Map of damaged bombs
    static void removeBomb(int x, int y)
    {
        if(damagedBombs.get(x) == null)
        {
            Map<Integer,Integer> map = new HashMap<Integer, Integer>();
            damagedBombs.put(x, map);  
            damagedBombs.get(x).put(y,0);
        }
        else
        {
            damagedBombs.get(x).put(y,0);
        }
    }
    
    static void printBombSet(Map<Integer,Map<Integer,Integer>> bombSet)
    {
        for(Map.Entry<Integer, Map<Integer,Integer>> x : bombSet.entrySet())
        {
            int px = x.getKey();
            for(Map.Entry<Integer,Integer> y : x.getValue().entrySet())
            {
                System.out.println("("+px+","+y.getKey()+")");
            }
        }
    }
    
    static void printGrid(char[][] grid)
    {
        for(char[] l : grid)
        {
            for(char m : l)
            {
                System.out.print(m);
            }
            System.out.println("");
        }
        //System.out.println(""); //Uncomment if you are printing iteratively
    }
}