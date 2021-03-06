package DataAsset.Data.ZippedBoolean.Discarded;

import java.util.Random;
import java.util.Scanner;

/**
 * Embeds ZippedBoolean as a 2-d array
 * Not finished yet
 */
public class ZippedBooleanArray {
    private final int ROW_SIZE;
    private final int COLUMN_SIZE;
    private ZippedBoolean zippedData[];
    private final int LIMIT;
    private final int META_LEN = 64;

    ZippedBooleanArray(int rowsWidth, int columnsHeight) {
        //suppose to verify rows and columns here...
        this.ROW_SIZE = rowsWidth;
        this.COLUMN_SIZE = columnsHeight;
        this.LIMIT = rowsWidth * columnsHeight;
        zippedData = new ZippedBoolean[rowsWidth * columnsHeight % META_LEN == 0
                ? rowsWidth * columnsHeight / META_LEN
                : rowsWidth * columnsHeight / META_LEN + 1];
        //initialize data
        for (int i = 0; i < zippedData.length; i++) {
            zippedData[i] = new ZippedBoolean("00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000");
        }
    }

    ZippedBooleanArray(int rows, int columns, String[] biStr) {
        //suppose to verify rows and columns here...
        this.ROW_SIZE = rows;
        this.COLUMN_SIZE = columns;
        this.LIMIT = rows * columns;
        zippedData = new ZippedBoolean[rows * columns % META_LEN == 0 ? rows * columns / META_LEN : rows * columns / META_LEN + 1];
        //split string
        String[] split;
        //initialize data
        for (int i = 0; i < zippedData.length; i++) {
            zippedData[i] = new ZippedBoolean("00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000");
        }
    }

    public boolean getBooleanAt(int row, int column) {
        int rowAt = (row * COLUMN_SIZE + column) / META_LEN;
        int columnAt = (row * COLUMN_SIZE + column) % META_LEN;
        return zippedData[rowAt].getBooleanAt(columnAt);
    }

    public void setBooleanAt(boolean value, int row, int column) {
        int rowAt = (row * COLUMN_SIZE + column) / META_LEN;
        int columnAt = (row * COLUMN_SIZE + column) % META_LEN;
        zippedData[rowAt].setBooleanAt(value, columnAt);
    }


    protected void debug_PrintOriginalData() {
        for (int i = 0; i < zippedData.length; i++) {
            zippedData[i].outputBinaryString();
        }
    }

    protected void debug_PrintExpectedData() {
        for (int i = 0; i < ROW_SIZE; i++) {
            System.out.print("[");
            for (int j = 0; j < COLUMN_SIZE; j++) {
                System.out.printf("%d", zippedData[(i * COLUMN_SIZE + j) / META_LEN].getBitAt((i * COLUMN_SIZE + j) % META_LEN));
                if (j != COLUMN_SIZE - 1) System.out.print(", ");
            }
            System.out.print("]\n");
        }
    }
}

class Test {
    public static void test1() {
        ZippedBooleanArray array = new ZippedBooleanArray(5, 50);
        array.debug_PrintOriginalData();
        array.debug_PrintExpectedData();
        //test1
        System.out.println("\tTest1:");
        array.setBooleanAt(false, 0, 0);
        array.setBooleanAt(true, 0, 1);
        System.out.println(array.getBooleanAt(0, 0));
        System.out.println(array.getBooleanAt(0, 1));
        array.debug_PrintOriginalData();
        array.debug_PrintExpectedData();
    }

    public static void main(String[] args) {
        test1();
        System.exit(0);
        Scanner sc = new Scanner(System.in);
        //test
        System.out.println("\tTest3: Speed and memory test");

        ZippedBooleanArray booleans = new ZippedBooleanArray(6400, 6400);
        for (int i = 0; i < 6400; i++) {
            for (int j = 0; j < 6400; j++) {
                Random r = new Random();
                booleans.setBooleanAt(r.nextBoolean(), i, j);
            }
        }
        System.out.println("Done");
        sc.nextInt();//to pause the execution

        //compare

        boolean[] booleans2 = new boolean[6400 * 6400];
        for (int i = 0; i < 6400 * 6400; i++) {
            Random r = new Random(i);
            booleans2[i] = r.nextBoolean();
        }
        sc.nextInt();

        //Machine dependent test:
        //runtime: 90+22MB
        //array: 98+60MB
        //zipped_boolean: 99+652MB
    }
}
