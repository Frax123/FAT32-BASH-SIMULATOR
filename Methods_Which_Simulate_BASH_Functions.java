import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

public class Methods_Which_Simulate_BASH_Functions {
    //FUNKCJE SYMULUJA DZIALANIE ICH ODPOWIEDNIKOW W BASH'U

    public static void ls(RandomAccessFile stream, long startOfDirectory, long integerInFATToStart, long startOfClusters,
                          long sectorsPerCluster, long startOfFat){ //Wypisuje wszystko z danego directory
        try {

            stream.seek(startOfDirectory);
            long helpToRememberStartOfDirectory = stream.getFilePointer();
            int i = 0;
            long numberOfIntegerInFat = integerInFATToStart;
            while (numberOfIntegerInFat != -1) {
                stream.seek((startOfClusters + (integerInFATToStart-2)*sectorsPerCluster)*512);
                i = stream.read();
                while (i != 0 && i != 229) {
                    stream.seek(stream.getFilePointer() - 1);
                    System.out.println(Help_Methods.decodeFileName(stream, stream.getFilePointer()));
                    stream.seek(stream.getFilePointer() + 21);
                    i = stream.read();
                }

                numberOfIntegerInFat = FAT_Check_And_Access_Methods.checkForNextFat(stream, startOfFat+4*numberOfIntegerInFat);
            }

            stream.seek(helpToRememberStartOfDirectory);
        } catch (IOException e) {

        }
    }

    public static void cd(RandomAccessFile stream, LinkedList<Integer> actualPathAddressClusterNumbers, LinkedList<String> actualPathNames,
                          String[] actualDir, String directoryToGo, long clusterBeginLBA, long sectorsPerCluster, Integer[] actualIntegerInFAT){
        try {
            if (directoryToGo.compareTo(" ..") == 0){
                if (actualPathNames.size() == 0){
                    return;
                }

                actualPathAddressClusterNumbers.remove(actualPathAddressClusterNumbers.size()-1);
                stream.seek((clusterBeginLBA + (actualPathAddressClusterNumbers.get(actualPathAddressClusterNumbers.size()-1) - 2) * sectorsPerCluster)*512);
                actualPathNames.remove(actualPathNames.size()-1);
                actualDir[0] = "/root/";
                for (int i = 0; i<actualPathNames.size(); i++){
                    actualDir[0] += actualPathNames.get(i);
                }


                actualIntegerInFAT[0] = actualPathAddressClusterNumbers.get(actualPathAddressClusterNumbers.size()-1);
                return ;
            }

            String helpStringForComparison = " ";
            int i = 0;
            while (i < 16 && stream.read() != 0){
                stream.seek(stream.getFilePointer()-1);
                helpStringForComparison = Help_Methods.decodeFileName(stream, stream.getFilePointer());
                if ((" " + helpStringForComparison).compareTo(directoryToGo) == 0 && stream.read() == 16){
                    stream.seek(stream.getFilePointer() + 14);
                    int[] helpArray = new int [2];
                    helpArray[0] = stream.read();
                    helpArray[1] = stream.read();
                    stream.seek((clusterBeginLBA + (Help_Methods.convertAddressInHexToDec(helpArray)-2)*sectorsPerCluster)*512);
                    actualIntegerInFAT[0] = Help_Methods.convertAddressInHexToDec(helpArray);
                    actualDir[0] +=  helpStringForComparison + "/";
                    actualPathAddressClusterNumbers.add(actualIntegerInFAT[0]);
                    actualPathNames.add(helpStringForComparison + "/");
                    return;
                }
                stream.seek(stream.getFilePointer()+21);
            }
            stream.seek((clusterBeginLBA + (actualPathAddressClusterNumbers.get(actualPathAddressClusterNumbers.size()-1) - 2) * sectorsPerCluster)*512);

        } catch (IOException e){
            System.out.println("Cd error " + e);
        }
        System.out.println("No such directory");
        return;
    }

    public static void mkdir(RandomAccessFile stream, String nameOfNewDir, long startOfFat, long clusterBeginLBA,
                             long sectorsPerCluster, Integer[] actualIntegerInFAT){
        try {
            long addressBefore = stream.getFilePointer();
            int freePlaceInFAT = Help_Methods.searchForFreeFATPlace(stream, actualIntegerInFAT[0], startOfFat);
            if (Help_Methods.addNewDirToActualDir(stream, nameOfNewDir, actualIntegerInFAT[0], clusterBeginLBA, startOfFat, sectorsPerCluster, freePlaceInFAT) != -1) {
                Help_Methods.updateFAT(stream, startOfFat, -1, freePlaceInFAT);
            }
            stream.seek(addressBefore);
        } catch (IOException e){

        }
    }


}
