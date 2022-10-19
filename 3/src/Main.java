import java.util.*;
import java.util.stream.Collectors;

public class Main {   //glowna funkcja
    public static void main(String[] args) {
        Scanner podaj = new Scanner(System.in); //deklaracja wprowadzacza danych
        String znaki;  //ciag znakow
        System.out.print("Podaj ciag znakow: ");
        znaki = podaj.nextLine();    //podawanie ciagu
        LinkedHashMap<Character, Long> characterLongMap = Wystapienie(znaki); //pojemnik przechowujacy wystapienia znakow
        System.out.println(characterLongMap); //wyswietlenie
        Map<Character, String> characterStringMap = Binar(characterLongMap); //pojemnik przechowujacy kody binarne
        System.out.println(characterStringMap); //wyswietlanie
        //String encryptedMessage = encryptMessage(characterStringMap, znaki);
       // System.out.println("Zaszyfrowana wiadomosc: " + encryptedMessage);
       // System.out.println("Odszyfrowana wiadomosc: " + decryptMessage(characterStringMap, encryptedMessage));
        System.out.println(Podzial(characterLongMap)); //wyswietlenie podzialu na 0 i 1
    }

    public static LinkedHashMap<Character, Long> Wystapienie(String znaki){  //zliczanie wystapien znakow
        Map <Character, Long> mapStringToCount = znaki  //pojemnik zliczajacy takie sanem znaki
                .chars().mapToObj(c -> (char) c)  //ktory wyswietla znak i jego ilosc
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        return sort(mapStringToCount);
    }

    public static LinkedHashMap<Character, Long> sort(Map<Character, Long> tab){  //sortowanie danych znakow
        LinkedHashMap<Character, Long> wynik = new LinkedHashMap<>();  //od najczesciej wystepujacych
        tab.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) //wyswietlanie znaku i ilosci
                .forEachOrdered(value -> wynik.put(value.getKey(), value.getValue()));

        return wynik;
    }

    public static Map<Character, String> Binar(Map<Character, Long> tab){  //zmiana na kodu binarny
        Map<Character, String> wartoscB = new LinkedHashMap<>();  //deklaracja wartosci binarnej
        int r = tab.size()-1;    //rozmiar tabeli
        String bin = "1";   //DEklaracja pierwszego znaku w kodzie binarnym
        for (Character wystapienie: tab.keySet()){  //dopisywanie kolejnych znakow kodu binarnego
            if(r == 0){   //dla kolejnego wysepujacego znaku
                wartoscB.put(wystapienie, "0");
            }
            else {
                wartoscB.put(wystapienie, bin.repeat(r) + "0");
            }
            r--;
        }
        return wartoscB;
    }

    public static String encryptMessage(Map<Character, String> wartoscBinarna, String wiadomosc){
        for (Map.Entry<Character, String> szyfr : wartoscBinarna.entrySet()){
            wiadomosc = wiadomosc.replace(szyfr.getKey().toString(), szyfr.getValue());
        }
        return wiadomosc;
    }

    private static String decryptMessage(Map<Character, String> wartoscBinarna, String encryptedMessage){
        for (Map.Entry<Character, String> szyfr : wartoscBinarna.entrySet()){
            encryptedMessage = encryptedMessage.replace(szyfr.getValue(), szyfr.getKey().toString());
        }
        return encryptedMessage;
    }
    public  static  LinkedHashMap<Long, Long> Podzial(LinkedHashMap<Character,Long> suma){ //podzial kodu na sumy
        long s=0; //suma z przodu
        long s2=0; //suma z tyłu  //ss2 to ostatni element z tyłu mapy

       LinkedHashMap<Long,Long> wyniki = new LinkedHashMap();   //deklarowanie ywników
       List<Long> lista = suma.values().stream().collect(Collectors.toList()); //lista przechowujaca sumy
       int r = lista.size()-1;
       for (int i=0;i<lista.size();i++){  //petla przydzielajaca 1 lub 0 do danej sumy
           if(s <= s2){
               s+=lista.get(i);
           }
           else if (r==i){
               break;
           }
           else {
               s2+=lista.get(r);
           }
       }
        wyniki.put(s,0L);
       wyniki.put(s2,1L);
       return wyniki;  //lub Podzial2(wyniki);
    }
    public static Map<Character,Long> Podzial2(LinkedHashMap<Long,Long> binar){
        for(Map.Entry<Long,Long> iter: binar.entrySet()){
            //na bdb....
        }
        return null;
    }

}
