# FnDm

***

## Wprowadzenie

**FnDm** to aplikacja przetwarzająca zarejestrowane odsłony użytkowników przykładowego portalu 
w raport, pokazujący dla każdgego użytkownika, początkowy oraz ostatni artykuł oraz dział.
Z raportu wykluczeni są użytkownicy, którzy dokonali tylko jednej odsłony.

## Repozytorium
Źródła aplikacji można znaleźć na github pod adresem: 
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

## Opis działania FnDm
Aplikację uruchamiamy skryptem
bash run.sh



2. Plik z danymi zawiera 5 kolumn., bez nagłówków, rodzielone znakiem pipe.