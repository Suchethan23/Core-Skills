// Definition for a pair
// class Pair {
//     int key;       // sorting will be based on this key
//     String value;  // additional value attached to key
//
//     Pair(int key, String value) {
//         this.key = key;
//         this.value = value;
//     }
// }



// Insertion Sort logic

// For each index i, you take the element pairs[i] and place it in the correct position among the already-sorted left part (0 ... i-1).

// You do this by shifting larger elements to the right until you find the correct spot.
public class Solution {
    public List<List<Pair>> insertionSort(List<Pair> pairs) {

        // 'res' will store snapshots (intermediate states) of the array
        List<List<Pair>> res = new ArrayList<>();

        int n = pairs.size();

        // Outer loop goes from 0 → n-1 (each iteration places the i-th element in correct position)
        for (int i = 0; i < n; i++) {
            
            // Start comparing with elements before the i-th index
            int j = i - 1;

            // While current element is smaller than the one before it → swap backwards
            while (j >= 0 && pairs.get(j).key > pairs.get(j + 1).key) {
                
                // Swap pairs[j] and pairs[j+1]
                Pair temp = pairs.get(j);
                pairs.set(j, pairs.get(j + 1));
                pairs.set(j + 1, temp);

                j--; // keep moving left
            }

            // Save the current state of list after finishing this i-th iteration
            res.add(new ArrayList<>(pairs));
            // We use `new ArrayList<>(pairs)` to make a snapshot (deep copy of current state).
        }

        return res; // Return all intermediate states
    }
}
