# Technical Requirements

## Architecture

### Eventsourcing von Nutzeraktionen

Anstatt die Gesamtzahl gescannter Items zu speichern werden die Aktionen, also das Ein- bzw. Ausscanner 
der Produkte, emmitiert und in einem Log gespeichert. Aus dieser gesamt Menge an Aktionen lässt sich 
zu jederzeit der aktuelle Stand wiederherstellen, indem alle Ereignisse konsumiert und visualisiert werdne. 
Beispiel: Die Ereignisse AddItemEvent("barcode-1", "kuehlschrank"), AddItemEvent("barcode-1", "kuehlschrank"), 
RemoveItemEvet("barcode-1", "kuehlschrank"), gibt akkumuliert den Bestand für "barcode-1" = 1 aus.

- Events sollten als Batch an das Backend gesendet werden, nicht jedes Event einzeln.

#### Nachteile
- Aufwändiger
  - Fehlerbehebung ist nicht einfach eine Zahl anpassen, ist die Loghistorie immutable - und das sollte sie sein - dann kann ein Fehler nur behoben werden, indem ein neues Ereignis erzeugt wird

#### Vorteile
- Es ist cool und wäre mal etwas neues
- Es ist im Detail nachvollziehbar wann welches Item gekauft wurde bzw. gescannt wurde. Somit liese sich der Warenbestandsverlauf sehr schön visualisieren.
  - Zudem ließen sich Prognosen erstellen, wie oft ein Produkt benötigt wird, ob dieses zuneige geht
  - Eventuell ließe sich dies mit den Preisen der Produkte verbinden, sodass auch ein Preisverlauf pro Produkt vorstellbar wäre


## TODO's
- [ ] Introduce a linter and linting rules
  - What are common linting rules for Android applications?
- 