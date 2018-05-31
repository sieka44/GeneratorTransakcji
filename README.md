# GeneratorTransakcji

## Jak zacząć:
1. Wejdź w konsoli do pliku z jar-em (GeneratorTransakcji\build\libs)
2. Wpisz jakie pliki JSON chcesz wygenerować według parametrów. Przykładowo: 
```
java -jar transaction-generator.jar -customerIds 1:20 -dateRange "2018-03-08T00:00:00.000-0100":"2018-03-08T23:59:59.999-0100" -itemsFile items.csv -itemsCount 5:15 -itemsQuantity 1:30 -eventsCount 10 -outDir ./output -format yaml
```

## Parametry:

customerIds: zakres, z jakiego będą generowane wartości do pola "customer_id". Domyślny 1:20

dateRange: zakres dat, z jakiego będzie generowane pole "timestamp". Domyślny dzień uruchomienia, cała doba

itemsFile: nazwa pliku csv zawierającego spis produktów. Przykładowy plik (items.csv) dołączony do zadania. - (Required)

itemsCount: zakres ilości elementów generowanych w tablicy "items". Domyślny 1:5

itemsQuantity: zakres z jakiego będzie generowana ilość kupionych produktów danego typu (pole "quantity"). Domyślnie 1:5

eventsCount: ilość transakcji (pojedynczych plików) do wygenerowania. Domyślnie 100.

outDir: katalog, do którego mają być zapisane pliki. Domyślnie aktualny katalog roboczy.

format: format pliku wyjściowego (json/xml/yaml)

## Docker:
Aby uruchomić dockera proszę skopiować własny plik properties do pliku <code> /storage/generator.properties </code> oraz własny plik csv (można posłużyć się przykładowym zawartym w folderze <code> src/main/java/Docker </code> ) następnie będąc w katalogu projektu uruchomić:
```
docker build --tag generator-transakcji .
```
oraz 
```
docker run generator-transakcji
```
