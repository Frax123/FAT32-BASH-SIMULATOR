import java.io.IOException;
import java.io.RandomAccessFile;

class Pair <K, V>{
    private K keyValue;
    private V Value;

    Pair(K _keyValue, V _value){
        keyValue = _keyValue;
        Value = _value;
    }

    Pair(){
        keyValue = null;
        Value = null;
    }

    public K getKeyValue() {
        return keyValue;
    }

    public V getValue() {
        return Value;
    }

    public void setValue(V value) {
        Value = value;
    }

    public void setKeyValue(K _keyValue){
        keyValue = _keyValue;
    }
}

public class Help_Methods {
    //FUNKCJA ODWRACAJACA STRING
    public static String reverseString(String s){
        String result = "";

        for (int i = s.length()-1; i >=0; i--){
            result += s.charAt(i);
        }

        return result;
    }

    //FUNKCJA PRZELICZA OTRZYMANY ADRES (W KOLEJNYCH LICZBACH SZESNASTKOWY ZAPISANYCH
    //W POSTACI DZIESIETNEJ) NA JEDEN OSTATECZNY ADRES DZIESIETNY
    public static int convertAddressInHexToDec(int[] arrayWithBytes){
        int actualLengthOfNumber = 0;
        String result = "";
        Pair<Integer, String> pairToRememberSegments;
        for (int i = arrayWithBytes.length-1; i >= 0 ; i--){
            pairToRememberSegments = convertDecToHex(arrayWithBytes[i]);
            actualLengthOfNumber += pairToRememberSegments.getKeyValue();
            if (pairToRememberSegments.getValue().length() == 1){
                pairToRememberSegments.setValue("0" + pairToRememberSegments.getValue());
                actualLengthOfNumber += 1;
            }
            result += pairToRememberSegments.getValue();

        }

        int finalResult = 0;
        int i = 0;
        int helpCharVariable = '\0';
        while (actualLengthOfNumber > 0){
            helpCharVariable = result.charAt(i);
            if (helpCharVariable >= 48 && helpCharVariable <= 57) {
                finalResult += (result.charAt(i) - 48) * Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'A'){
                finalResult += 10*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'B'){
                finalResult += 11*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'C'){
                finalResult += 12*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'D'){
                finalResult += 13*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'E'){
                finalResult += 14*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'F'){
                finalResult += 15*Math.pow(16, actualLengthOfNumber-1);
            }
            actualLengthOfNumber--;
            i++;
        }

        return finalResult;
    }

    public static int convertAddressInHexToDec(byte[] arrayWithBytes){
        int actualLengthOfNumber = 0;
        String result = "";
        Pair<Integer, String> pairToRememberSegments;
        for (int i = arrayWithBytes.length-1; i >= 0 ; i--){
            pairToRememberSegments = convertDecToHex(arrayWithBytes[i]);
            actualLengthOfNumber += pairToRememberSegments.getKeyValue();
            if (pairToRememberSegments.getValue().length() == 1){
                pairToRememberSegments.setValue("0" + pairToRememberSegments.getValue());
                actualLengthOfNumber += 1;
            }
            result += pairToRememberSegments.getValue();

        }

        int finalResult = 0;
        int i = 0;
        int helpCharVariable = '\0';
        while (actualLengthOfNumber > 0){
            helpCharVariable = result.charAt(i);
            if (helpCharVariable >= 48 && helpCharVariable <= 57) {
                finalResult += (result.charAt(i) - 48) * Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'A'){
                finalResult += 10*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'B'){
                finalResult += 11*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'C'){
                finalResult += 12*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'D'){
                finalResult += 13*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'E'){
                finalResult += 14*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'F'){
                finalResult += 15*Math.pow(16, actualLengthOfNumber-1);
            }
            actualLengthOfNumber--;
            i++;
        }
        return finalResult;
    }

    //OBLICZA ILOSC CYFR W LICZBIE W POSTACI INT
    public static int computeLengthOfInteger(int value){
        if (value == 0) return 1;

        int i = 0;
        while (value != 0){
            i++;
            value = value/10;
        }

        return i;
    }

    //ZAMIENIA LICZBE DZIESIETNA NA STRING SZESNASTKOWY
    private static Pair<Integer, String> convertDecToHex(int number){ // Zmienia liczbe dec na jej reprezentacje hex zapisana w stringu result
        //zwraca dlugosc liczby i reprezentacje
        String result = "";
        Integer modValue = 0;
        Integer lengthOfNumber = 0;

        if (number == 0){
            result = "0";
            lengthOfNumber = 1;
        }

        while (number > 0){
            modValue = number%16;
            if (modValue == 15){
                result+='F';
            } else if (modValue == 14) {
                result+='E';
            } else if (modValue == 13) {
                result+='D';
            } else if (modValue == 12) {
                result+='C';
            } else if (modValue == 11) {
                result+='B';
            } else if (modValue == 10) {
                result+='A';
            } else {
                result += modValue;
            }
            number/=16;
            lengthOfNumber++;
        }

        result = reverseString(result);

        return new Pair<>(lengthOfNumber, result);
    }

    //ODWRACA KOLEJNOSC ELEMENTOW W TABLICY
    public static void reverseArray(int[] array){
        int temp = 0;
        for (int i = 0; i < array.length/2; i++){
            temp = array[i];
            array[i] = array[array.length-1-i];
            array[array.length-i-1] = temp;
        }
    }

    public static void reverseArray(byte[] array){
        byte temp = 0;
        for (int i = 0; i < array.length/2; i++){
            temp = array[i];
            array[i] = array[array.length-1-i];
            array[array.length-i-1] = temp;
        }
    }

    //ODCZYTUJE NAZWE PLIKU WEDLE SPECYFIKACJI FAT32
    public static String decodeFileName(RandomAccessFile stream, long startOfFileName) {
        String result = "";
        try {
            stream.seek(startOfFileName);
            for (int i = 0; i < 11; i++) {
                result += (char)(stream.read());
            }
            return result;
        }  catch (IOException e){

        }

        return "Fail";
    }

    //POPRAWIA TABLICE FAT PO DODANIU NOWEGO ELEMENTU
    public static void updateFAT(RandomAccessFile stream, long startOFFAT, int lastFATNumber, int newPlaceToOccupy){
        try {
            if (lastFATNumber != -1){
                stream.seek(startOFFAT + 4 * lastFATNumber);
                stream.write(0);
                stream.write(0);
                stream.write(0);
                stream.write(newPlaceToOccupy);
            }
            stream.seek(startOFFAT + 4*newPlaceToOccupy);
            stream.write(255);
            stream.write(255);
            stream.write(255);
            stream.write(255);
        } catch (IOException e){

        }
    }

    //DODAJE NOWY FOLDER DO AKTUALNEGO FOLDERU
    public static int addNewDirToActualDir(RandomAccessFile stream, String nameOfNewDir, long integerInFATToStart,
                                           long startOfClusters, long startOfFAT, long sectorsPerCluster, int freePlaceInFAT){
        try {
            long helpToRememberStartOfDirectory = stream.getFilePointer();
            //int condition = 1;
            int i = 0;
            int counterOfDirRecords = 0;
            long numberOfIntegerInFat = integerInFATToStart;
            long previousNumberOfIntegerInFAT = 0;
            while (numberOfIntegerInFat != -1) {
                //stream.seek(startOfDirectory);
                stream.seek((startOfClusters + (integerInFATToStart-2)*sectorsPerCluster)*512);
                i = stream.read();
                while (i != 0 && i != 229 && counterOfDirRecords != 16) {
                    stream.seek(stream.getFilePointer() + 31);
                    i = stream.read();
                    counterOfDirRecords ++;
                }

                if (counterOfDirRecords == 16) {
                    previousNumberOfIntegerInFAT = numberOfIntegerInFat;
                    numberOfIntegerInFat = FAT_Check_And_Access_Methods.checkForNextFat(stream, startOfFAT + 4 * numberOfIntegerInFat);
                    counterOfDirRecords = 0;

                    if (numberOfIntegerInFat == -1){
                        updateFAT(stream, startOfFAT, (int)previousNumberOfIntegerInFAT, freePlaceInFAT);
                        stream.seek((startOfClusters + (freePlaceInFAT-2)*sectorsPerCluster)*512);
                        makeDirRecord(stream, nameOfNewDir, freePlaceInFAT);
                        int newFreePlaceInFAT = searchForFreeFATPlace(stream, freePlaceInFAT,startOfFAT);
                        updateFAT(stream, startOfFAT, -1, newFreePlaceInFAT);
                        return -1;
                    }
                } else {
                    makeDirRecord(stream, nameOfNewDir, freePlaceInFAT);
                    return 1;
                }
            }

            stream.seek(helpToRememberStartOfDirectory);
        } catch (IOException e) {

        }

        return 1;
    }

    //TWORZY NOWY FOLDER W TRAKCIE DODAWANIA
    public static void makeDirRecord(RandomAccessFile stream, String nameOfNewDir, Integer FreePlaceInFAT){
        try {
            stream.seek(stream.getFilePointer()-1);
            int i = 0;
            for (i = 1; i < nameOfNewDir.length(); i++){
                stream.write((int)nameOfNewDir.charAt(i));
            }
            for (; i < 12; i++){
                stream.write(32);
            }
            stream.write(16);
            stream.seek(stream.getFilePointer()+14);
            writeAddressOfNewDir(stream, FreePlaceInFAT);
            for (int j = 0; j < 4; j++){
                stream.write(0);
            }
        } catch (IOException e){
            System.out.println(e);
        }
    }

    //ZAPISUJE ADRES NOWEGO FOLDERU
    public static void writeAddressOfNewDir(RandomAccessFile stream, Integer FreePlaceInFAT){
        try {
            String help = "";
            //TUTAJ DODAC INNE PRZYPADKI

            stream.write(0);
            stream.write(convertHexNumberToDecNumber(Integer.toHexString(FreePlaceInFAT)));
        } catch (IOException e){

        }
    }

    //KONWERTUJE LICZBE HEKSADECYMALNA ZAPISANA JAKO STRING NA DZIESIETNA
    public static int convertHexNumberToDecNumber(String HexNumber){
        int finalResult = 0;
        char helpCharVariable = '\0';
        int i = 0;
        int actualLengthOfNumber = HexNumber.length();
        while (actualLengthOfNumber > 0){
            helpCharVariable = HexNumber.charAt(i);
            if (helpCharVariable >= 48 && helpCharVariable <= 57) {
                finalResult += (HexNumber.charAt(i) - 48) * Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'a'){
                finalResult += 10*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'b'){
                finalResult += 11*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'c'){
                finalResult += 12*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'd'){
                finalResult += 13*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'e'){
                finalResult += 14*Math.pow(16, actualLengthOfNumber-1);
            } else if (helpCharVariable == 'f'){
                finalResult += 15*Math.pow(16, actualLengthOfNumber-1);
            }
            actualLengthOfNumber--;
            i++;
        }

        return finalResult;
    }

    //SZUKA PUSTEGO MIEJSCA W TABLICY FAT
    public static int searchForFreeFATPlace(RandomAccessFile stream, Integer actualIntegerInFAT, long startOfFat){
        try {
            int hashFAT = actualIntegerInFAT;
            while (true) {
                stream.seek(startOfFat*512 + 4*hashFAT);
                int[] helpArray = new int[4];
                helpArray[0] = stream.read();
                helpArray[1] = stream.read();
                helpArray[2] = stream.read();
                helpArray[3] = stream.read();
                if (0 == Help_Methods.convertAddressInHexToDec(helpArray)){
                    return hashFAT;
                }
                hashFAT += 1;
            }
        } catch (IOException e) {

        }

        return -1;
    }

    //OBLICZA WARTOSCI POTRZEBNE DO PORUSZANIA SIE PO TABLICY FAT. MOGLA PRZYDAC SIE PRZY DEBUGGINGU.
    public static void computeValuesNeededToMoveInFAT(Long fatLBABegin, Long clusterBeginLBA, Long sectorsPerCluster, Long rootDirFirstCluster,
                                                      RandomAccessFile strumien, int addressOfFirstPartition){

        //Funkcja liczy wartosci potrzebne do poruszania sie po FAT-cie
        fatLBABegin = addressOfFirstPartition + FAT_Check_And_Access_Methods.findSomeAddress(strumien, addressOfFirstPartition, 14, 2);
        clusterBeginLBA = addressOfFirstPartition + FAT_Check_And_Access_Methods.findSomeAddress(strumien, addressOfFirstPartition, 14, 2) +
                (2 * FAT_Check_And_Access_Methods.findSomeAddress(strumien, addressOfFirstPartition, 36, 4));
        sectorsPerCluster = FAT_Check_And_Access_Methods.findSomeAddress(strumien, addressOfFirstPartition, 13, 1);
        rootDirFirstCluster = FAT_Check_And_Access_Methods.findSomeAddress(strumien, addressOfFirstPartition, 44, 4);
    }

}
