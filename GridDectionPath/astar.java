package YBHalgo;
import java.util.*;

public class AStar {
    public static final int DIAGONAL_COST = 14;
    public static final int V_H_COST = 10;
    
    static class Shell{  
        int heuristicCost = 0; //Heuristic cost
        int finalCost = 0; //G+H
        int i, j;
        Shell parent; 
        
        Shell(int i, int j){
            this.i = i;
            this.j = j; 
        }
        
        @Override
        public String toString(){
            return "["+this.i+", "+this.j+"]";
        }
    }
    
    //Blocked Shells are just null Shell values in grid
    static Shell [][] grid = new Shell[5][5];
    
    static PriorityQueue<Shell> open;
     
    static boolean closed[][];
    static int startI, startJ;
    static int endI, endJ;
            
    public static void setBlocked(int i, int j){
        grid[i][j] = null;
    }
    
    public static void setStartShell(int i, int j){
        startI = i;
        startJ = j;
    }
    
    public static void setEndShell(int i, int j){
        endI = i;
        endJ = j; 
    }
    
    static void checkAndUpdateCost(Shell current, Shell t, int cost){
        if(t == null || closed[t.i][t.j])return;
        int t_final_cost = t.heuristicCost+cost;
        
        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }
    
    public static void AStar(){ 
        
        //add the start location to open list.
        open.add(grid[startI][startJ]);
        
        Shell current;
        
        while(true){ 
            current = open.poll();
            if(current==null)break;
            closed[current.i][current.j]=true; 

            if(current.equals(grid[endI][endJ])){
                return; 
            } 

            Shell t;  
            if(current.i-1>=0){
                t = grid[current.i-1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

                if(current.j-1>=0){                      
                    t = grid[current.i-1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }

                if(current.j+1<grid[0].length){
                    t = grid[current.i-1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
            } 

            if(current.j-1>=0){
                t = grid[current.i][current.j-1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.j+1<grid[0].length){
                t = grid[current.i][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.i+1<grid.length){
                t = grid[current.i+1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

                if(current.j-1>=0){
                    t = grid[current.i+1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
                
                if(current.j+1<grid[0].length){
                   t = grid[current.i+1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }  
            }
        } 
    }
    
    /*
    Params :
    tCase = test case No.
    x, y = Board's dimensions
    si, sj = start location's x and y coordinates
    ei, ej = end location's x and y coordinates
    int[][] blocked = array containing inaccessible Shell coordinates
    */
    public static void test(int tCase, int x, int y, int si, int sj, int ei, int ej, int[][] blocked){
           System.out.println("\n\nTest Case #"+tCase);
            //Reset
           grid = new Shell[x][y];
           closed = new boolean[x][y];
           open = new PriorityQueue<>((Object o1, Object o2) -> {
                Shell c1 = (Shell)o1;
                Shell c2 = (Shell)o2;

                return c1.finalCost<c2.finalCost?-1:
                        c1.finalCost>c2.finalCost?1:0;
            });
           //Set start position
           setStartShell(si, sj);  //Setting to 0,0 by default. Will be useful for the UI part
           
           //Set End Location
           setEndShell(ei, ej); 
           
           for(int i=0;i<x;++i){
              for(int j=0;j<y;++j){
                  grid[i][j] = new Shell(i, j);
                  grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
//                  System.out.print(grid[i][j].heuristicCost+" ");
              }
//              System.out.println();
           }
           grid[si][sj].finalCost = 0;
           
           /*
             Set blocked Shells. Simply set the Shell values to null
             for blocked Shells.
           */
           for(int i=0;i<blocked.length;++i){
               setBlocked(blocked[i][0], blocked[i][1]);
           }
           
           
           AStar(); 
           System.out.println("\nScores for Shells: ");
           for(int i=0;i<x;++i){
               for(int j=0;j<x;++j){
                   if(grid[i][j]!=null)System.out.printf("%-3d ", grid[i][j].finalCost);
                   else System.out.print("BL  ");
               }
               System.out.println();
           }
           System.out.println();
            
           if(closed[endI][endJ]){
               //Trace back the path 
                System.out.println("Path: ");
                Shell current = grid[endI][endJ];
                System.out.print(current);
                while(current.parent!=null){
                    System.out.print(" -> "+current.parent);
                    current = current.parent;
                } 
                System.out.println();
           }else System.out.println("No possible path");
    }
     
    public static void main(String[] args) throws Exception{   
        test(1, 5, 5, 0, 0, 3, 2, new int[][]{{0,4},{2,2},{3,1},{3,3}}); 
        test(2, 5, 5, 0, 0, 4, 4, new int[][]{{0,4},{2,2},{3,1},{3,3}});   
        test(3, 7, 7, 2, 1, 5, 4, new int[][]{{4,1},{4,3},{5,3},{2,3}});
        
        test(1, 5, 5, 0, 0, 4, 4, new int[][]{{3,4},{3,3},{4,3}});
    }
}