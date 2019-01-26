import java.io.*;
import java.util.*;

public class Solution {
    static class House {
        private long price;
        private long area;

        public long getPrice() {
            return price;
        }

        public void setPrice(long price) {
            this.price = price;
        }

        public long getArea() {
            return area;
        }

        public void setArea(long area) {
            this.area = area;
        }

        public House(long area, long price) {
            this.area = area;
            this.price = price;
        }
    }

    static class Client {
        private long price;
        private long area;

        public long getPrice() {
            return price;
        }

        public void setPrice(long price) {
            this.price = price;
        }

        public long getArea() {
            return area;
        }

        public void setArea(long area) {
            this.area = area;
        }

        public Client(long area, long price) {
            this.area = area;
            this.price = price;
        }
    }

    static boolean findHouse(boolean[][] matrix, int clientU, boolean[] isSeen, int[] matchR) {
        int length = isSeen.length;
        for (int v = 0; v < length; v++) {
            if (matrix[clientU][v] == true && !isSeen[v]) {
                isSeen[v] = true;

                /*
                 * If the house is occupied by some client, then traversal up and find another
                 * house for that client
                 */
                if (matchR[v] < 0 || findHouse(matrix, matchR[v], isSeen, matchR)) {
                    matchR[v] = clientU;
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int clients = in.nextInt();
        int houses = in.nextInt();

        ArrayList<House> housesArray = new ArrayList<>(houses);
        ArrayList<Client> clientsArray = new ArrayList<>(clients);

        for (int i = 0; i < clients; i++) {
            long area = in.nextLong();
            long price = in.nextLong();
            clientsArray.add(new Client(area, price));
        }

        for (int i = 0; i < houses; i++) {
            long area = in.nextLong();
            long price = in.nextLong();
            housesArray.add(new House(area, price));
        }

        boolean[][] matrix = new boolean[clients][houses];
        for (int i = 0; i < clients; i++) {
            for (int j = 0; j < houses; j++) {
                if (clientsArray.get(i).getArea() <= housesArray.get(j).getArea()
                        && clientsArray.get(i).getPrice() >= housesArray.get(j).getPrice())
                    matrix[i][j] = true;
            }
        }

        int[] matchR = new int[houses];
        Arrays.fill(matchR, -1);

        int result = 0;

        for (int u = 0; u < clients; u++) {
            boolean[] isSeen = new boolean[houses];
            if (findHouse(matrix, u, isSeen, matchR))
                result++; // if client u could buy a house
        }

        System.out.println(result);
        // for(int i = 0; i < clients; i ++) {
        // for(int j = 0; j < houses; j++) {
        // System.out.print(matrix[i][j] + " ");
        // }
        // System.out.println();
        // }
        in.close();
    }

}

