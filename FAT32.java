import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.Thread.sleep;

//mmls

public class FAT32 {

    public static void main(String[] args) {

        try {
            System.out.println("PODAJ DYSK DO ODCZYTU:");
            Scanner input = new Scanner(System.in);
            String actualCom = "";
            actualCom = input.next();

            RandomAccessFile strumien = new RandomAccessFile(actualCom, "rw");
            if (FAT_Check_And_Access_Methods.sanityCheck(strumien) == false){
                System.out.println("FAIL");
                sleep(1000);
                return ;
            }

            int varForASecond = FAT_Check_And_Access_Methods.findAddressOfFirstPartition(strumien);

            if (FAT_Check_And_Access_Methods.checkForExceptedValuesInFat(strumien, varForASecond) == false){
                System.out.println("Problem with FAT. FAIL");
                sleep(1000);
                return;
            };

            long fatLBABegin = varForASecond + FAT_Check_And_Access_Methods.findSomeAddress(strumien, varForASecond, 14, 2);
            long clusterBeginLBA = varForASecond + FAT_Check_And_Access_Methods.findSomeAddress(strumien, varForASecond, 14, 2) +
                    (2 * FAT_Check_And_Access_Methods.findSomeAddress(strumien, varForASecond, 36, 4));
            long sectorsPerCluster = FAT_Check_And_Access_Methods.findSomeAddress(strumien, varForASecond, 13, 1);
            long rootDirFirstCluster = FAT_Check_And_Access_Methods.findSomeAddress(strumien, varForASecond, 44, 4);

            long lbaAddress = clusterBeginLBA + (rootDirFirstCluster - 2) * sectorsPerCluster;
            strumien.seek(3162112);
            strumien.seek(lbaAddress*512);

            String[] actualDir = new String [1];
            actualDir[0] = "/root/";
            LinkedList<Integer> actualPathClusterNumbers = new LinkedList<>(); //Wczesniej byly tu adresy
            actualPathClusterNumbers.add((int)rootDirFirstCluster);
            LinkedList<String> actualPathNames = new LinkedList<>();

            System.out.print(actualDir[0] + " ");
            actualCom = input.next();
            Integer[] actualIntegerInFat = new Integer[1];
            actualIntegerInFat[0] = (int)rootDirFirstCluster;

            while (actualCom.compareTo("end") != 0){

                if (actualCom.compareTo("ls") == 0){
                    Methods_Which_Simulate_BASH_Functions.ls(strumien, strumien.getFilePointer(), actualIntegerInFat[0], clusterBeginLBA, sectorsPerCluster, fatLBABegin*512);
                } else if (actualCom.compareTo("cd") == 0){
                    actualCom = input.nextLine();
                    Methods_Which_Simulate_BASH_Functions.cd(strumien, actualPathClusterNumbers, actualPathNames, actualDir, actualCom, clusterBeginLBA, sectorsPerCluster, actualIntegerInFat);
                } else if (actualCom.compareTo("showFilePtr") == 0){
                    System.out.println(strumien.getFilePointer());
                } else if (actualCom.compareTo("mkdir") == 0){
                    actualCom = input.nextLine();
                    Methods_Which_Simulate_BASH_Functions.mkdir(strumien, actualCom, fatLBABegin*512, clusterBeginLBA, sectorsPerCluster, actualIntegerInFat);
                } // Ponizsze else if sa przydatne w debugowaniu
                else if (actualCom.compareTo("fat") == 0){
                    int actualInt = input.nextInt();
                    long actualAddr = strumien.getFilePointer();
                    strumien.seek((fatLBABegin*512 + actualInt*4)*1);
                    System.out.println(strumien.getFilePointer());
                    int[] helpArray = new int [4];
                    helpArray[0] = strumien.read();
                    helpArray[1] = strumien.read();
                    helpArray[2] = strumien.read();
                    helpArray[3] = strumien.read();
                    System.out.println(actualInt);
                    System.out.println(Help_Methods.convertAddressInHexToDec(helpArray));
                    strumien.seek(actualAddr);
                } else if (actualCom.compareTo("fat1") == 0){
                    System.out.println(actualIntegerInFat[0]);
                }
                System.out.print(actualDir[0] + " ");
                actualCom = input.next();
            }

        } catch (IOException e){

        } catch (InterruptedException e){

        }
    }
}
