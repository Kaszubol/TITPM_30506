#include <iostream>
#include <string>
using namespace std;

int main()
{
    string slowo;  //deklarujemy zmienne 
    unsigned long long klucz, a, m, c;

    m = 39562;   // definiujemy generator LCG
    a = 19781;
    c = 130;

    cout << "Wprowadz klucz szyfrujacy (liczba od 0 do 3214565):"<<endl,cin >> klucz; // wczytujemy klucz;
    cout<<"Podaj slowo do odszyfrowania: ",cin >> slowo;  //wpisujemy slowo

    for (int i = 0; i < slowo.length(); i++) //deszyfrowanie
    {
        klucz = (a * klucz + c) % m;  //obliczanie klucza dla kazdego znaku
        slowo[i] = toupper(slowo[i]);  //zamiana malych liter na wielkie
        if ((slowo[i] >= 'A') && (slowo[i] <= 'Z')) slowo[i] = 65 + (slowo[i] - 39 - klucz % 26) % 26;// deszyfrowanie liter
    }
    cout<<"Jawny tekst:" << slowo << endl << endl;  //pokazanie wyniku

    return 0;
}
