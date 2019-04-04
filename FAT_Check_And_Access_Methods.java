import java.io.IOException;
import java.io.RandomAccessFile;

public class FAT_Check_And_Access_Methods{

    //SPRAWDZA CZY NIEZMIENNE WARTOSCI W TABLICY FAT SIE ZGADZAJA
    public static boolean checkForExceptedValuesInFat(RandomAccessFile stream, Integer addressOfVolumeId){
        try {
            stream.seek(addressOfVolumeId*512 + 11);
            int[] helpArrayToRead = new int[2];
            helpArrayToRead[0] = stream.read();
            helpArrayToRead[1] = stream.read();
            int helpVariableToRememberResults = Help_Methods.convertAddressInHexToDec(helpArrayToRead);
            if (helpVariableToRememberResults == 512){
                stream.seek(addressOfVolumeId*512 + 16);
                if (stream.read() == 2){
                    stream.seek(addressOfVolumeId*512 + 510);
                    if (Integer.toHexString(stream.read()).compareTo("55") == 0 && Integer.toHexString(stream.read()).compareTo("aa") == 0){
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (IOException e){

        }

        return false;
    }

    //SPRAWDZA CZY MBR JEST BEZ BLEDOW
    public static boolean sanityCheck(RandomAccessFile stream){
        try {
            stream.seek(510);
            if (stream.read() == 85){
                if (stream.read() == 170){
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (IOException e){

        }

        return false;
    }

    //SZUKA ODPOWIEDNICH ADRESOW POTRZEBNYCH DO PORUSZANIA SIE PO TABLICY FAT
    public static int findAddressOfFirstPartition(RandomAccessFile stream){
        try {
            stream.seek(446+4);
            int variableForComparison = stream.read();
            if (variableForComparison == 11 || variableForComparison == 12){
                stream.seek(454);
                byte[] arrayWithBytes = new byte[4];
                stream.read(arrayWithBytes);
                // reverseArray(arrayWithBytes);
                return Help_Methods.convertAddressInHexToDec(arrayWithBytes);
            } else {
                return -1;
            }
        } catch (IOException e){

        }
        return  -1;
    }

    public static int findNumberOfReservedSectors(RandomAccessFile stream, Integer address){
        try {
            stream.seek(address*512 + 14);
            int[] arrayWithResults = new int[2];
            arrayWithResults[0] = stream.read();
            arrayWithResults[1] = stream.read();
            return Help_Methods.convertAddressInHexToDec(arrayWithResults);
        } catch (IOException e){

        }

        return  -1;
    }

    public static int findSectorsPerFAT(RandomAccessFile stream, Integer address){
        try {
            stream.seek(address*512 + 36);
            int[] arrayWithResults = new int[4];
            arrayWithResults[0] = stream.read();
            arrayWithResults[1] = stream.read();
            arrayWithResults[2] = stream.read();
            arrayWithResults[3] = stream.read();
            return Help_Methods.convertAddressInHexToDec(arrayWithResults);
        } catch (IOException e){

        }

        return  -1;
    }

    public static int findSectorsPerCluster(RandomAccessFile stream, Integer address){
        try {
            stream.seek(address*512 + 13);
            return stream.read();
        } catch (IOException e){

        }
        return -1;
    }

    public static long findSomeAddress(RandomAccessFile stream, long addressOfLBA, int placeOfAddressToFind, int lengthOfAddressInBytes){
        try {
            stream.seek(addressOfLBA*512 + placeOfAddressToFind);
            int[] arrayWithResults = new int [lengthOfAddressInBytes];
            for (int i = 0; i < lengthOfAddressInBytes; i++){
                arrayWithResults[i] = stream.read();
            }

            return Help_Methods.convertAddressInHexToDec(arrayWithResults);
        } catch (IOException e){

        }

        return  -1;
    }

    //SPRAWDZA CZY DANY FOLDER JEST ZAPISANY W WIECEJ NIZ JEDYNYM INTEGERZE W TABLICY FAT
    public static long checkForNextFat(RandomAccessFile stream, long addressToCheck){
        try {
            stream.seek(addressToCheck);
            int[] helpArray = new int [4];
            helpArray[0] = stream.read();
            helpArray[1] = stream.read();
            helpArray[2] = stream.read();
            helpArray[3] = stream.read();
            long possibleAddress = Help_Methods.convertAddressInHexToDec(helpArray);
            if (possibleAddress >= 268435448){
                return -1;
            } else {
                return possibleAddress;
            }

        } catch (IOException e){

        }

        return -1;
    }

}
