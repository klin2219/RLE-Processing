import java.util.*;

public class RleProgram {
    public static void printMenu(){
        System.out.println("RLE Menu");
        System.out.println("--------");
        System.out.println("0. Exist");
        System.out.println("1. Load File");
        System.out.println("2. Load Test Image");
        System.out.println("3. Read RLE String");
        System.out.println("4.ead RLE Hex String");
        System.out.println("5. Read Data Hex String");
        System.out.println("6. Display Image");
        System.out.println("7. Display RLE String");
        System.out.println("8. Display HEX RLE Data");
        System.out.println("Display Hex Flat Data");
        System.out.println();
    }

    public static String toHexString (byte[] data){
        String hexString = "";
        int test;
        for ( int i = 0; i < data.length; i++){
            if(i % 2 == 0){
                continue;
            }
            else{
                test = data[i];
                if ( test == 10 ){
                    hexString = hexString + "a";
                }
                else if ( test == 11 ){
                    hexString = hexString + "b";
                }
                else if ( test == 12 ){
                    hexString = hexString + "c";
                }
                else if ( test == 13 ){
                    hexString = hexString + "d";
                }
                else if ( test == 14 ){
                    hexString = hexString + "e";
                }
                else if ( test == 15 ){
                    hexString = hexString + "f";
                }
                else{
                    hexString = hexString + test;
                }
            }
        }

        return hexString;
    }

    public static int countRuns (byte[] flatData){
        int runs = 0;
        int test = flatData[0];
        int i = 0;
        int tracker = 0;
        while(i < flatData.length){

            tracker++;
            if(tracker == 16 && test == flatData[i]){
                runs++;
            }
            else if ( test != flatData[i] ){
                runs++;
                test = flatData[i];
            }

            i++;

        }
        return runs;
    }

    public static byte[] encodeRLE (byte[] flatData){
        int amount = countRuns(flatData);
        byte[] encoded = new byte[amount*2];
        byte test = flatData[0];
        byte runs = 0;
        int i = 0;
        int indexE = 0;

        while (i < flatData.length){
            if(test == flatData[i]){
                runs++;
                test = flatData[i];
            }

            if(test != flatData[i] || runs == 16){
                encoded[indexE++] = runs;
                runs = 0;
                encoded[indexE++] = test;
                test = flatData[i];
            }

            if(indexE == encoded.length){
                break;
            }

        }

        return encoded;
    }

    public static int getDecodedLength (byte[] rleData){
        int retLength = 0;
        for ( int i = 0; i < rleData.length; i++){
            if(i % 2 == 0 ){
                retLength += rleData[i];
            }
        }
        return retLength;
    }

    public static byte[] decodeRLE (byte[] rleData){
        byte[] decoded = new byte[getDecodedLength(rleData)];
        int amount = 0, total = 0;
        byte value = 0;

        for ( int i = 0; i < rleData.length; i++){
            if ( i > 1 ){
                for (int j = total; j < amount; j++){
                    decoded[j] = value;
                }
                total += amount;
                amount = 0;
                value = 0;
            }

            if (i % 2 == 0){
                amount = rleData[i];
            }
            else if ( i % 2 == 1 ){
                value = rleData[i];
            }
        }
        return decoded;
    }

    public static byte[] stringToData (String dataString){
        byte[] data = new byte[dataString.length()];
        String test = "";
        for ( int i = 0; i < dataString.length(); i++){
            test = dataString.substring(i, i+1);
            if(test.equalsIgnoreCase("a")){
                test = "10";
            }
            else if(test.equalsIgnoreCase("b")){
                test = "11";
            }
            else if(test.equalsIgnoreCase("c")){
                test = "12";
            }
            else if(test.equalsIgnoreCase("d")){
                test = "13";
            }
            else if(test.equalsIgnoreCase("e")){
                test = "14";
            }
            else if(test.equalsIgnoreCase("f")){
                test = "15";
            }

            data[i] = (byte) Integer.parseInt(test);

        }
        return data;
    }


    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        //welcome message
        System.out.println("Welcome to RLE image encoder:");

        ConsoleGfx.displayImage(ConsoleGfx.testRainbow);

        byte[] imageData = null;
        int userChoice;
        String userInput;
        boolean exit = false;

        while (exit != true) {

            //print menu
            printMenu();

            //prompt for input
            System.out.println("Select a Menu Option:");
            userChoice = scnr.nextInt();
            if (userChoice == 0) {
                exit = true;
                break;
            }

            //3.1 - option 1
            if (userChoice == 1) {
                System.out.println("Enter name of file to load: ");
                userInput = scnr.next();
                imageData = ConsoleGfx.loadFile(userInput);
                System.out.println();
            }
            //3.2 - option 2
            else if (userChoice == 2) {
                imageData = ConsoleGfx.testImage;
                System.out.println("Test image data loaded.");
                System.out.println();
            }
            //3.6 - option 6
            else if (userChoice == 6) {
                System.out.println("Displaying image...");
                ConsoleGfx.displayImage(imageData);
                System.out.println();
            }
            //complete option 0,3,4,5,7,8,9


        }
    }
}
