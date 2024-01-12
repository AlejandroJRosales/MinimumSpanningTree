import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GaleShapley {    
    public static ArrayList<int[]> createPrefList(String fileName) {
        //creating a dictionary to store our set
        ArrayList<int[]> set = new ArrayList<int[]>();
        
        // read the file line by line
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String line;
        try {
            while ((line = br.readLine()) != null) {
                // split the line by spaces to grab each string
                String[] arrOfStr = line.split(" ");
                
                int[] values = new int[arrOfStr.length];
                // start off at the 1st index so we do not add the name in
                // subtract 1 from index so we start off adding values to 
                // the value list at index 0
                for (int index = 1; index < arrOfStr.length; index++) {
                    String temp = arrOfStr[index];
                    values[index - 1] = Integer.parseInt(temp);
                }    
                // add the values now
                set.add(values);
            }
        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        return set;
    }
    
    public static boolean withoutPartner(int[] partnerList) {
        // detect if there list is empty
        return partnerList.length == 0;
    }
    
    public static boolean anyoneWithoutPartner(int[][] partnerList2) {
        // detect if there is still a person without a partner
        for (int[] partnerList: partnerList2) {
            if (withoutPartner(partnerList)) {
                return true;
            }
        }
        return false;
    }
    
    public static int arrayIndexOf(int[] array, int x) {
        for (int pos = 0; pos < array.length; pos++) {
            if (array[pos] == x) {
                return pos;
            }
        }
        return -1;
    }
    
    public static void gS(ArrayList<int[]> m, ArrayList<int[]> g) {
        for (int[] l: m) {
            System.out.println(Arrays.toString(l));
        }
        System.out.println();
        for (int[] l: g) {
            System.out.println(Arrays.toString(l));
        }
        
        int[][] malePartnerList = new int[m.size()][];
        int[][] femalePartnerList = new int[m.size()][];
        while (anyoneWithoutPartner(malePartnerList)) {
            for (int maleIndex = 0; maleIndex < m.size(); maleIndex++) {
                int[] currentMale = m.get(maleIndex);
                for (int preference: currentMale) {
                    if (withoutPartner(malePartnerList[maleIndex])) {
                        malePartnerList[maleIndex][0] = preference;
                        femalePartnerList[preference][0] = maleIndex;
                    }
                    else {
                        int currentPartner = malePartnerList[maleIndex][0];
                        if (arrayIndexOf(currentMale, currentPartner) > arrayIndexOf(currentMale, preference)) {
                            malePartnerList[maleIndex][0] = preference;
                            femalePartnerList[preference][0] = maleIndex;
                        }
                    }
                }
            }
        }
    }
    
    public static void main(String args[]) throws IOException {
        //place where file is located. double backward slash should be used.
        ArrayList<int[]> males = createPrefList(args[0]);
        ArrayList<int[]> girls = createPrefList(args[1]);
        
        // call Gale-Shapley algorithm
        gS(males, girls);
    }
}
