import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

public class LZ77 {
    private String znaki;    //deklaracja zmiennych globalnych - wiadomosc
    private Scanner podaj = new Scanner(System.in);   //scanner do wprowadzania
    private String podstawa ="____";   //podstawowa wiadomosc poczatkowo pusta
    private String bufor = "";   //bufor na dane
    private String mapa;    //mapa przechowujaca klucze
    private Integer w;      // wielkosc slownika
    private LinkedHashMap<String, String> podstawowaM = new LinkedHashMap<>();  //lista na podstawowy slownik
    private LinkedList<Integer> Deszyfr = new LinkedList<>();  //lista na odszyfrowana wiadomosc
    private Integer pierwszy;   //pierwszy znak na poczatku
    private Integer ostatni;   //ostatni znak

    public void Zapisz(){
        System.out.print("Podaj wiadmosc do zakodowania: ");  //podawanie wiadomosc i wielkosci slownika
        znaki = podaj.nextLine();

        System.out.println("Wielkosc slownika? ");
        w = podaj.nextInt();

        slowinkPodstawowy();
    }

    private void slowinkPodstawowy() {  //budowa slownika pdostawowego
        char pierwszaL = znaki.charAt(0);  //deklaracja pierwsze lietry

        mapa = String.format("(0, 0, %s)", pierwszaL);  // wstawienie jej w odpwoednim formacie do mapy
        podstawowaM.put(mapa, podstawa);

        podstawa = podstawa.substring(1) + pierwszaL;  //przeesuniecie slownikowe
        slownikRozbudowany();
    }

    private void slownikRozbudowany(){   //rozbudowywanie slownika
        for(int j = 1; j<= znaki.length()+ w; j++){
            if(j >= znaki.length()-1){  //warunki ktore oskeslaja co robic gdy wzorzec jest juz w slowniku
                bufor = String.valueOf(znaki.charAt(znaki.length()-1));
                if (Zliczanie(bufor)) {
                    mapa = String.format("(%d, %d, %s)", pierwszy, ostatni, "_");
                    podstawowaM.put(mapa, podstawa);
                }
            }else {   //lub gdy nie ma w slowniku
                if(bufor.length() == 0){    //to tworzy nowy
                    bufor = znaki.substring(j, j+4);   // podmienia bufor
                }else {   //i zmienia slowna czesc slownika
                    podstawa = podstawa.substring(ostatni +1) + bufor.substring(0, ostatni +1);
                    bufor = bufor.substring(ostatni +1) + znaki.substring(j, j+ ostatni +1);
                }
                podstawowaM.put("#", podstawa);
                if (Zliczanie(bufor)){  //po sliczeniu warunki do przesuniecia
                    podstawowaM.remove("#");    //w lewo o odpowiednia ilosc
                    char firstLetter = bufor.charAt(ostatni);
                    mapa = String.format("(%d, %d, %s)", pierwszy, ostatni, firstLetter);
                    podstawowaM.put(mapa, podstawa);
                    if(ostatni + 1 == w){
                        j += ostatni -1;
                    }else {
                        j += ostatni + 1;
                    }
                }
                else {
                    char firstLetter = bufor.charAt(0);
                    mapa = String.format("(0, 0, %s)", firstLetter);
                    podstawa = podstawa.substring(1) + firstLetter;
                    podstawowaM.put(mapa, podstawa);
                    bufor = "";
                }
            }
        }
        Szyfr();
    }

    private void Szyfr(){   //wypisywanie szyfru
        for (String index : podstawowaM.keySet()){
            System.out.print(index + " ");
        }
    }

   /* public void zapiszDoPlikuONazwie() {   //zapisywanie
        if (mapaPodstawowa.isEmpty()){
            System.out.println("\nSłownik jest pusty\nBlad");
            return;
        }
        try{
            System.out.print("\nPodaj nazwe : ");
            String nazwaPliku = scanner.nextLine();
            File file = new File(nazwaPliku);
            if(!file.exists()){
                System.out.println("Utworzono plik");
                file.createNewFile();
            }
            if(file.canWrite()) {
                FileWriter fileWriter = new FileWriter(file);
                Formatter formatter = new Formatter(fileWriter);
                for (Map.Entry<Integer, String> mapka : mapaPodstawowa.entrySet()){
                    formatter.format("%d | %s\r\n", mapka.getKey(), mapka.getValue());
                }
                formatter.close();
                fileWriter.close();
            }
            System.out.println("Zapisano");
        }catch (Exception e){
            System.out.println("Blad w zapisie");
            System.out.println(e.getMessage());
        }
    }*/
    /*public void decryptMessage(){
        String decryptedMessage = "";
        for(Integer index : encryptedMessage){
            decryptedMessage += mapaPodstawowa.get(index);
        }
        System.out.println("\nWiadomosc: " + decryptedMessage);
    }

    public void odczytajZPliku(){  //odczytywanie
        try{
            mapaPodstawowa.clear();
            System.out.print("Podaj nazwe pliku: ");
            String nazwaPliku = scanner.nextLine();
            File file = new File(nazwaPliku);
            String odczytZpliku;
            if (file.exists()){
                Scanner fileScanner = new Scanner(file);
                while(fileScanner.hasNextLine()){
                    odczytZpliku = fileScanner.nextLine();
                    String[] split = odczytZpliku.split("[|]\s");
                    int index = Integer.parseInt(split[0].replace(" ", ""));
                    String values = split[1].replace(" ", "");
                    mapaPodstawowa.put(index, values);
                }
                fileScanner.close();
            }
            else{
                System.out.println("Plik nie istnieje");
            }
        }catch (Exception e){
            System.out.println("Wystapil blad podczas odczytu pliku");
        }
    }*/
        private boolean Zliczanie(String buffor){  //zliczanie  wartosci i
        int rozmiarBufora = w /2;    //ustawianie odpowiedniego rozmiaru buforu
        LinkedList<String> wartosciSlownika = podstawowaM.values().stream().collect(Collectors.toCollection(LinkedList::new));
        for(int s=wartosciSlownika.size()-1; s>=0; s--){
            String slowo = wartosciSlownika.get(s).replaceAll("_", "");
            if(buffor.length()==1){
                if(slowo.contains(buffor)){
                    pierwszy = wartosciSlownika.get(s).indexOf(buffor);
                    ostatni = 1;
                    return true;
                }
                return false;
            }
            for(int i =0; i<2; i++) {
                String bufSub = buffor.substring(i, i + 3);
                if(slowo.contains(bufSub)){
                    pierwszy = wartosciSlownika.get(s).indexOf(bufSub);
                    ostatni = 3;
                    return true;
                }
            }
            for(int i =0; i<=buffor.length()-rozmiarBufora; i++){
                String bufSub = buffor.substring(i, i + 2);
                if(slowo.contains(bufSub)){
                    pierwszy = wartosciSlownika.get(s).indexOf(bufSub);
                    ostatni = 2;
                    return true;
                }}}
        return false;
    }
}
