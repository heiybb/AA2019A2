## Approach

### Task A
**Graph.java**  
This class is used for **Task A** to convert the PathMap into the Graph that my Dijkstra algorithm can identify and handle.
  
- **nodeList**  
Format -> ArrayList<Coordinate>  
Store all the Coordinates that are passable.  

- **adjMap**  
Format -> HashMap<Coordinate, ArrayList<Coordinate>>  
Store the all Coordinate's neighbours into an ArrayList and using itself as the HashMap's Key.  

**dijkstra Method**  
Iterate all the Coordinate's distance to the start Node/Coordinate.  
Update with new distance whene a Node/Coordinate is visited.  
Accept Node List, Node AdjMap, Start Node, End Node as income argument.  
Will return the shortest path between the Start Node and End Node.  
If the path is not existed, the return path List's size is 0.

### Task B
Implement a comparator and using the steam to summary to total cost of a path by adding the terrain cost value together.  

```java
    private static class CoordinateListComparator<T extends Coordinate> implements Comparator<List<T>>, Serializable {
        @Override
        public int compare(List<T> path1, List<T> path2) {

            int sumCost1 = path1
                    .stream()
                    .map(T::getTerrainCost)
                    .mapToInt(Integer::intValue)
                    .sum();

            int sumCost2 = path2
                    .stream()
                    .map(T::getTerrainCost)
                    .mapToInt(Integer::intValue)
                    .sum();

            return Integer.compare(sumCost1, sumCost2);
        }
    }
```
### Task C
Use multiple for loop to generate the list which contains all the Node/Coordinate that must pass.  
Pass the list's Start Node and End Node to the dijkstra method.  
Store all the path list into another list and use the customized Comparator to get the shortest(lowest cost) path.  

### Task D
**Permutation.java**  
This class provide the interface to generate the permutation of any Object List.  
Cooperate with the Task B Comparator function.  
The shortest path wich also contains all the way points can be figured out.  


