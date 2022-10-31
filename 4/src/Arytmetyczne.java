import java.util.*;
import java.util.stream.Collectors;

public class Arytmetyczne {
    private String w;
    private Scanner podaj = new Scanner(System.in);
    public  void Aryt(){   //podawanie danych do kodowania
        System.out.println("Podaj wiadomosc do zakodowania: ");
        w=podaj.nextLine();
        System.out.println(wystapienia(w));  //wyswietlenie wynikow

    }
    private LinkedHashMap<Character, ArrayList< Float>> wystapienia(String znaki){  //zliczanie wystapien znakow
        LinkedHashMap<Character,Float> sort = new LinkedHashMap<>();  //tablica przechowujaca wystapienia
        Map<Character,Long> zamiana = znaki.chars().mapToObj(z ->(char) z).collect(Collectors.groupingBy(z -> z,Collectors.counting()));//pokazanie pojedynczego znaku
        zamiana.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(e -> sort.put(e.getKey(), Float.valueOf(e.getValue())));//sortowanie znakow alfabetycznie
        return przedzial(sort);
    }
    private LinkedHashMap<Character, ArrayList<Float>> przedzial (Map<Character,Float> wyst){ //tworzenie przedzialu
        boolean p = true;  //flaga pomagajaca przejsc petle
        LinkedHashMap<Character,ArrayList<Float>> wynik = new LinkedHashMap<>(); //tablica przechowujaca przedzialy
        for (Map.Entry<Character, Float> iterator: wyst.entrySet()){ //petla zwarcajaca wynik dla danego znaku
            ArrayList<Float> nowy = new ArrayList<>();
            if(p){  //jezeli flaga jest prawdziwa to do listy dodaje sie wartosc dla danego znaku
                nowy.add((float) 0);
                nowy.add(iterator.getValue()/w.length()); //dodawanie wyniku do tablicy ze znakami
                wynik.put(iterator.getKey(),nowy); //dodawanie wyniku do tablicy z wartosciami
                p=false; //po dodaniu znaku flaga staje sie falszem
            }
            else { //w przeciwnym wypadku dodawany jest do tablicy wynikow ostatni element konczacy przedzial
                float ost = wynik.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                                .findFirst().get().getValue().get(1);

                nowy.add(ost);
                nowy.add(ost+(iterator.getValue()/w.length()));
                wynik.put(iterator.getKey(),nowy);
            }
        }
        return wynik; //wyswietlanie wynikow
    }

    }




