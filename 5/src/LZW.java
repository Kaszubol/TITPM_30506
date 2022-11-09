import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class LZW {
    private Scanner podaj = new Scanner(System.in); //deklaracja zmiennych
    private String znaki;  //znaki do szyfrowania
    private Integer i=0;   //pomocnicza zmienna do powtorzen
    private String z="";   // zaszyfrowana wiadomosc, poczatkowa puszta
    private LinkedList<Integer> lista = new LinkedList<>(); // lista na zapamietywane indeksy
     private LinkedHashMap<Integer, String> mapa = new LinkedHashMap<>(); //mapa podstawowa

    public void Wiadomosc() {  //wyswietlanie prosby o podanie wiadomosci
        System.out.println("Podaj znaki do zaszyfrowania: ");
        znaki = podaj.nextLine();
        slownik(); //wywolanie funkcji stworzenia slownika
    }

    private void slownik(){  //podstawowy slownik
        List<Character> lista = znaki.chars().mapToObj(c -> (char) c).distinct().collect(Collectors.toList());
        Collections.sort(lista);   //posortowanie alfabetycznie listy przechowujacej znaki

        for(Character v: lista){  //petla wykonujaca zmiane znakow
            i++;                  //i wprowadzajaca je do listy
            mapa.put(i, v.toString());
        }
        slownik2();
    }

    private void slownik2(){   //rozbudowywanie slownika o kolejne elementy

        for(int j=0;j<=znaki.length()-1;j++) { // dodawanie pozostalych znakow
            if (j == znaki.length() - 1) {     // warunek dla ostatniego elementu
                String ostatni = String.valueOf(znaki.charAt(j)); //pobranie wartosci ostatniego
                List<Integer> ostatni_z = mapa.entrySet().stream() //lista z ostatnnimi znakami
                        .filter(c -> c.getValue().equals(ostatni)) //ktora wybierze prawidlowy
                        .map(c -> c.getKey()).collect(Collectors.toList());
                lista.add(ostatni_z.get(0));
            } else {  //jezeli element nie jest ostatni
                z += znaki.substring(j, j + 2); // jezeli dana fraza jest juz w slowniku
                if (mapa.containsValue(z)) {  //to dopisujemy do niego ta wartosc
                    int ind=z.length()-1;

                    z = z.substring(0,ind);
                } else {                   //w przeciwnym przypadku zmieniamy obecny ciag w slowniku
                    int indeks = z.length() - 1;   // i zapisujemy indeks
                    String w = z.substring(0, indeks);
                    List<Integer> indeks_list = mapa.entrySet().stream().filter(c -> c.getValue().equals(w)).map(c -> c.getKey())
                            .collect(Collectors.toList());
                    i++;   //dodajac go do stworzonej listy indeksow
                    lista.add(indeks_list.get(0));
                    mapa.put(i, z);
                    z = "";

                }
            }
        }
        Pokaz();
        }
        private void Pokaz(){   //wyswietlamydany kod
            for(Integer i: lista){  //czyli wszystkie elementy listy
                System.out.print(i+" ");
            }
        }

         public void Zapisz() {   //zapisywanie slownika do  pliku
        if (mapa.isEmpty()){   //pod warunkiem, ze cos zostalo do niego zapisane
            System.out.println("Nie mozna zapisac");
            return;
        }
        try{
            System.out.println("Podaj nazwe pliku do zapisania: ");
            String nazwa = podaj.nextLine();//tworzymy plik o nazwie
            File plik = new File(nazwa);
            if(!plik.exists()){          //i tworzymy go jesli nie istnieje
                System.out.println("Utworzono");
                plik.createNewFile();
            }
            if(plik.canWrite()){    //zapisujemy do pliku
                FileWriter zapis = new FileWriter(plik);
                Formatter form = new Formatter(zapis);
                for(Map.Entry<Integer,String> wynik: mapa.entrySet()){  //elementy mapy
                    form.format("%s | %s\r\n", wynik.getKey(),wynik.getValue()); // w formie..
                }   // ...numeru i danej wartosci
                form.close();
                zapis.close();
            }
            System.out.println("Zapisano");  //jesli istnieje to zmieniamy zawartosc
        }catch (Exception e){
            System.out.println("Blad");
        }
    }
    public void Deszyfrowanie(){  //odszyfrowanie
        String szyf="";   //zmienna przechowujaca szyfr
        for (Integer i: lista){  //odczytanie danej wartosci dla znaku
            szyf +=mapa.get(i);
        }
        System.out.println("Odszyfrowana wiadomosc jest nastepujaca: "+szyf);  //wyswietlenie wyniku
    }
    public void Plik(){  //otwieranie z pliku
        try{
            mapa.clear(); //wprowadzenie danych do wyczyszczonej mapy
            System.out.print("Podaj nazwe pliku : ");  //wskazanie pliku
            String nazwa = podaj.nextLine();  //ze slownikiem
            File plik = new File(nazwa);
            String odczyt;
            if (plik.exists()){
                Scanner sc = new Scanner(plik);  //odczytywanie pliku
                while(sc.hasNextLine()){
                    odczyt=sc.nextLine();
                    String[] zawartosc = odczyt.split("[|]\s");  //deklaracja separatora wartosci

                        int k = Integer.parseInt(zawartosc[0].replace(" ",""));
                        String wartosci = zawartosc[1].replace(" ",""); //deklaracja odszyfrowanych wartosci
                        mapa.put(k,wartosci);  //umieszczenie ich w mapie

                }
                sc.close();
            }
        }catch (Exception e){
            System.out.println("Blad");
        }
    }
    }


