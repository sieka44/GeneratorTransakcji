# GeneratorTransakcji

## Jak zacząć:
1. Wejdź w konsoli do pliku z jar-em (GeneratorTransakcji\build\libs)
2. Wpisz jakie pliki JSON chcesz wygenerować według parametrów. Przykładowo: 
```
java -jar transaction-generator.jar -customerIds 1:20 -dateRange "2018-03-08T00:00:00.000-0100":"2018-03-08T23:59:59.999-0100" -itemsFile items.csv -itemsCount 5:15 -itemsQuantity 1:30 -eventsCount 1000 -outDir ./output
```

## Parametry:

customerIds: zakres, z jakiego będą generowane wartości do pola "customer_id". Domyślny 1:20

dateRange: zakres dat, z jakiego będzie generowane pole "timestamp". Domyślny dzień uruchomienia, cała doba

itemsFile: nazwa pliku csv zawierającego spis produktów. Przykładowy plik (items.csv) dołączony do zadania. - (Required)

itemsCount: zakres ilości elementów generowanych w tablicy "items". Domyślny 1:5

itemsQuantity: zakres z jakiego będzie generowana ilość kupionych produktów danego typu (pole "quantity"). Domyślnie 1:5

eventsCount: ilość transakcji (pojedynczych plików) do wygenerowania. Domyślnie 100.

outDir: katalog, do którego mają być zapisane pliki. Domyślnie aktualny katalog roboczy.