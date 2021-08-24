# FnDm

***

## Wprowadzenie

**FnDm** to aplikacja przetwarzająca zarejestrowane odsłony użytkowników przykładowego portalu 
w raport, pokazujący dla każdgego użytkownika, początkowy oraz ostatni artykuł oraz dział.
Z raportu wykluczeni są użytkownicy, którzy dokonali tylko jednej odsłony.

Aplikacja dziala w Sparku (v3.1.2) i jest napisana w Scali (v2.12.14)
Uruchamiana skryptem run.sh.




## Repozytorium
Źródła aplikacji można znaleźć na github pod adresem: https://github.com/Biadko/FnDm.git
Osobą odpowiedzialną jest *Biadko* 

## Opis działania FnDm

FnDm przetwarza odsłony użytkowników w interwebie

1. Wczytanie danych wejściowych z odslonami 
2. 2.Parsowanie danych przy pomocy Spark SQL (wyciąganie id artykułu, id wiki)
3. Znalezienie pierwszych i ostatnich odsłon wg czasu rejestru odslony dla każdego uytkownika osobno.
4. Sprawdzenie czy ostatnia i pierwsza odsłona zawierają te same id-ki artykułu i wiki.
5. Wykluczenie użytkowników, którzy zrobili tylko jedną odsłonę.
6. Zapis wyniku końcowego do folderu tymczasowego
7. Oczyszczenie niepotrzebnych śmieci powstałych przy zapisie danych przez Sparka
8. Zapis pliku z wynikami do data/result.csv


## Przygotowanie aplikacji do działania
Gotową wersję aplikacji przygotowujemy przez zbudowanie jej skryptem:
bash scripts/build.bash

#### Uruchamianie aplikacji
Do uruchamiania aplikacji z linii poleceń służy skrypt `run.sh`:
`./run.sh
Komenda uruchamia Sparka oraz modyfikuje końcową nazwę pliku wynikowego.

#### Wynik końcowy
Plik z wynikami znajduje się w data/result.csv




