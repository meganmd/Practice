import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Collections;

public class Sorting {
    public String[] findRelativeRanks(int[] nums) {
        int places[][] = new int[nums.length][2];
        for(int i = 0; i < nums.length; i++) {
            places[i] = new int[]{nums[i], i};
        }


        Arrays.sort(places, new Comparator<int[]>() {

            public int compare(int[] a, int[] b) {
                return Integer.compare(a[0],b[0]);
            }
        });



        TreeMap<Integer, Integer> ranks = new TreeMap<>(Collections.reverseOrder());
        String[] result = new String[nums.length];
        for (int  i=0; i < nums.length; i++) {
            ranks.put(nums[i], i);
        }
        int n = 1;
        for (Map.Entry<Integer,Integer> e : ranks.entrySet()) {
            if(n == 1) {
                result[e.getValue()] = "Gold Medal";
                n++;
            } else if(n == 2) {
                result[e.getValue()] = "Silver Medal";
                n++;
            } else if(n == 3) {
                result[e.getValue()] = "Bronze Medal";
                n++;
            } else {
                result[e.getValue()] = Integer.toString(n++);
            }
        }
        return result;
    }

    public static void main(String[] args) {
      findRelativeRanks(new int[]{2,1,5});
    }
}
