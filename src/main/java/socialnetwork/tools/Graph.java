package socialnetwork.tools;

import socialnetwork.domain.Friend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Graph {
    HashMap<Integer, ArrayList<Integer>> adjListArray;

    HashSet<Integer> vertices;
    public Graph(Iterable<Friend> edgeArray)
    {
        adjListArray = new HashMap<>();

        HashSet<Integer> vertices = new HashSet<>();
        for(Friend e : edgeArray)
        {
            vertices.add(e.getId1());
            vertices.add(e.getId2());
        }

        this.vertices = vertices;

        for(Integer v : vertices)
        {
            adjListArray.put(v, new ArrayList<>());
        }

        for(Friend e : edgeArray)
        {
            adjListArray.get(e.getId1()).add(e.getId2());
            adjListArray.get(e.getId2()).add(e.getId1());
        }
    }


    HashSet<Integer> DFSUtil(int v, HashMap<Integer,Boolean> visited,HashSet<Integer> connection)
    {
        visited.put(v,true);
        connection.add(v);
        for (int x : adjListArray.get(v)) {
            if (!visited.get(x))
                DFSUtil(x, visited, connection);
        }
        return connection;
    }

    public ArrayList<HashSet<Integer>> connectedComponents()
    {
        HashMap<Integer,Boolean> visited = new HashMap<>();
        ArrayList<HashSet<Integer>> allConnections = new ArrayList<>();
        for (Integer v: this.vertices) {
                visited.put(v,false);
        }
        for (Integer v: this.vertices) {
            if (!visited.get(v)) {
                HashSet<Integer> connection = new HashSet<>();
                allConnections.add(DFSUtil(v, visited, connection));
            }
        }
        return allConnections;
    }
}
