# Digital Storage Room – Feature-Katalog

> Stand: 27.09.2026 · Version 0.1 · Fokus: fachliche Features (Architektur, CI/CD und Tests folgen separat)

> This was generated with the help of Opus 5.5

## 1. Vision

Lebensmittel liegen in der Wohnung an vielen Orten: im Kühlschrank, im Vorratsschrank, im Keller, auf dem Balkon. Der **Digital Storage Room** bildet diese Orte digital ab. So weiß ich jederzeit ohne Nachsehen,

1. **was ich nachkaufen muss** (Hauptziel) und
2. **was ich habe und wo es liegt** (Überblick).

Andere Funktionen wie Haltbarkeit, Statistiken oder KI sind Zusatznutzen. Sie dürfen die beiden Hauptziele nicht verkomplizieren.

## 2. Leitprinzipien


| Prinzip                               | Bedeutung                                                                                                                           |
| ------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| **Die Einkaufsliste ist das Produkt** | Ein Feature muss am Ende eine verlässliche Einkaufsliste möglich machen.                                                            |
| **Erfassen ohne Mühe**                | Der Bestand stimmt nur, wenn Einbuchen *und* Entnehmen schnell gehen. Jeder zusätzliche Klick ist ein Risiko.                       |
| **Offline-first**                     | Im Keller gibt es kein Netz. Die App muss dort trotzdem voll funktionieren.                                                         |
| **Klein anfangen**                    | Version 1 ist für einen Nutzer auf einem Gerät gedacht. Mehrere Nutzer kommen später, das Datenmodell soll sie aber nicht verbauen. |


## 3. Begriffe (Glossar)


| Begriff                            | Bedeutung                                                                                                                                                                   |
| ---------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Lagerort** *(Storage Space)*     | Digitales Abbild eines physischen Orts, z. B. Kühlschrank, Keller, Balkon. Jeder Lagerort hat einen Typ.                                                                    |
| **Lagerort-Typ**                   | `GEKÜHLT` (Kühlschrank, kühler Keller), `TIEFGEKÜHLT` (Gefriertruhe), `RAUMTEMPERATUR` (Regal, Vorratsschrank), `DRAUSSEN` (Balkon)                                         |
| **Produkt** *(ItemRepresentation)* | Stammdaten eines Artikels: Name, Bild, Kategorie, Nährwerte usw. Jedes Produkt hat eine eigene ID. Ein Barcode verweist auf ein Produkt, ist aber nicht zwingend eindeutig. |
| **Bestandsartikel** *(StoredItem)* | Ein konkretes, physisch vorhandenes Stück eines Produkts an einem Lagerort. Optional mit Einlagerungsdatum und Haltbarkeitsdatum.                                           |
| **Mindestbestand**                 | Menge eines Produkts, die immer vorrätig sein soll. Wird sie unterschritten, muss nachgekauft werden.                                                                       |
| **Einkaufsliste**                  | Produkte, deren Bestand unter dem Mindestbestand liegt, plus manuell ergänzte Einträge.                                                                                     |
| **Bestandsbuchung**                | Jede Änderung am Bestand: Einbuchen, Entnehmen, Umlagern oder Korrektur.                                                                                                    |


## 4. Priorisierung

- **MVP:** Das braucht Version 1, damit das Hauptproblem gelöst ist.
- **Next:** Deutlicher Mehrwert, direkt nach dem MVP.
- **Later:** Ausbaustufen und Ideen.

## 5. Features

### 5.1 Lagerorte — MVP


| ID   | Feature                                                                                                                                                                   | Prio  |
| ---- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| LO-1 | Lagerorte anlegen, umbenennen und löschen                                                                                                                                 | MVP   |
| LO-2 | Jedem Lagerort einen Typ zuweisen (siehe Glossar)                                                                                                                         | MVP   |
| LO-3 | Lagerorte sortieren, z. B. nach Laufweg in der Wohnung                                                                                                                    | Next  |
| LO-4 | Ein Lagerort mit Bestand darf nur gelöscht werden, wenn der Bestand vorher umgelagert oder verworfen wird                                                                 | MVP   |
| LO-5 | **Lagerort DRAUSSEN:** Außentemperatur anzeigen. Liegt sie außerhalb eines sicheren Bereichs, werden gefährdete Artikel markiert und die Farbe des Lagerorts ändert sich. | Later |


### 5.2 Einbuchen per Scan — MVP


| ID   | Feature                                                                                                                                                             | Prio |
| ---- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---- |
| SC-1 | Scanner öffnen, Lagerort und Modus wählen (**Einbuchen**/**Entnehmen**)                                                                                             | MVP  |
| SC-2 | **Dauerscan:** Ein Artikel wird erfasst, sobald er im Bild ist, und zwar genau einmal. Erst wenn er das Bild verlässt und wieder erscheint, wird er erneut gezählt. | MVP  |
| SC-3 | Rückmeldung pro Scan über Ton, Vibration und kurzes Einblenden von Produktname und neuer Menge                                                                      | MVP  |
| SC-4 | Den letzten Scan mit einem Tipp rückgängig machen                                                                                                                   | MVP  |
| SC-5 | Übersicht am Ende der Scan-Sitzung: „12 Artikel in *Keller* eingebucht“                                                                                             | Next |
| SC-6 | Menge beim Scan erhöhen, z. B. „6er-Pack Wasser = 6 Stück“                                                                                                          | Next |


### 5.3 Produkte (Stammdaten) — MVP


| ID   | Feature                                                                                                               | Prio |
| ---- | --------------------------------------------------------------------------------------------------------------------- | ---- |
| PR-1 | Ist ein Barcode unbekannt, legt der Nutzer das Produkt direkt aus dem Scanner an (Name reicht, alles andere optional) | MVP  |
| PR-2 | Produktdaten automatisch aus einer offenen Datenbank vorbefüllen (z. B. Open Food Facts)                              | Next |
| PR-3 | Produkte **ohne Barcode** anlegen und buchen (Obst, Gemüse, lose Ware, Selbstgemachtes)                               | MVP  |
| PR-4 | Mehrere Barcodes einem Produkt zuordnen (z. B. „Milch 1,5 %“ von verschiedenen Marken als *ein* Produkt)              | Next |
| PR-5 | Produkt-Detailseite mit Stammdaten, Gesamtbestand und Verteilung auf die Lagerorte                                    | MVP  |
| PR-6 | Kategorien (Milchprodukte, Konserven, Getränke …)                                                                     | Next |


### 5.4 Bestand und Überblick — MVP


| ID   | Feature                                                                                                                                                        | Prio |
| ---- | -------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---- |
| BE-1 | **Bestand pro Lagerort:** Liste der Produkte mit Menge                                                                                                         | MVP  |
| BE-2 | **Gesamtbestand über alle Lagerorte:** z. B. „Milch: 3 (1× Kühlschrank, 2× Keller)“                                                                            | MVP  |
| BE-3 | Suchen sowie sortieren nach Name, Menge und Einlagerungsdatum                                                                                                  | MVP  |
| BE-4 | Menge in der Liste mit **+/−** anpassen. Änderungen werden gesammelt und erst mit „Speichern“ übernommen (Schutz vor Fehltipps).                               | MVP  |
| BE-5 | **Bestand 0 bleibt sichtbar:** Ein Produkt mit Menge 0 bleibt in der Liste und wird ausgegraut. Ausblenden lässt es sich über einen Filter oder durch Löschen. | MVP  |
| BE-6 | **Umlagern** als eigene Aktion, z. B. 1× Milch vom Keller in den Kühlschrank                                                                                   | Next |
| BE-7 | Filtern, z. B. „nur unter Mindestbestand“, „nur Kategorie X“                                                                                                   | Next |


### 5.5 Entnehmen — MVP

> Hier liegt das größte Risiko: Wird das Entnehmen vergessen, stimmen Bestand und Einkaufsliste nicht mehr. Deshalb gibt es mehrere Wege, die gleich schnell sind.


| ID   | Feature                                                                                                        | Prio  |
| ---- | -------------------------------------------------------------------------------------------------------------- | ----- |
| EN-1 | **Per Scan:** Scanner im Modus „Entnehmen“ (siehe SC-1)                                                        | MVP   |
| EN-2 | **Per Tipp:** In der Bestandsliste über **−** oder eine Wischgeste „Aufgebraucht“                              | MVP   |
| EN-3 | Beim Entnehmen per Scan wird der Lagerort erkannt: Liegt das Produkt nur an einem Ort, muss man keinen wählen. | Next  |
| EN-4 | Homescreen-Widget „Schnell entnehmen“ (Scanner direkt im Entnahmemodus öffnen)                                 | Later |
| EN-5 | Regelmäßige Bestandsprüfung („Stimmt das noch?“) für einzelne Lagerorte, um Abweichungen zu korrigieren        | Later |


### 5.6 Mindestbestand und Einkaufsliste — MVP (Kern)


| ID   | Feature                                                                                                                                      | Prio  |
| ---- | -------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| EK-1 | **Mindestbestand pro Produkt** festlegen (Standard: 0 = kein automatisches Nachkaufen)                                                       | MVP   |
| EK-2 | **Automatische Einkaufsliste:** Liegt der Gesamtbestand unter dem Mindestbestand, kommt das Produkt auf die Liste, mit der Menge, die fehlt. | MVP   |
| EK-3 | Manuelle Einträge ergänzen (auch Dinge ohne Produkt, z. B. „Geschenkpapier“)                                                                 | MVP   |
| EK-4 | Im Laden abhaken                                                                                                                             | MVP   |
| EK-5 | **Nach dem Einkauf einbuchen:** Abgehakte Artikel mit einem Klick einem Lagerort zuordnen und einbuchen, ganz ohne Scannen                   | Next  |
| EK-6 | Einkaufsliste nach Kategorie oder Supermarkt-Laufweg sortieren                                                                               | Later |
| EK-7 | Einkaufsliste teilen oder exportieren (Text, Messenger)                                                                                      | Next  |


### 5.7 Offline-first — MVP


| ID   | Feature                                                                                                         | Prio |
| ---- | --------------------------------------------------------------------------------------------------------------- | ---- |
| OF-1 | Alle Kernfunktionen (Scannen, Buchen, Bestand, Einkaufsliste) funktionieren ohne Internet                       | MVP  |
| OF-2 | Buchungen werden lokal gespeichert und automatisch synchronisiert, sobald wieder eine Verbindung besteht        | MVP  |
| OF-3 | Sichtbare Statusanzeige: „3 Änderungen noch nicht synchronisiert“                                               | Next |
| OF-4 | Unbekannte Barcodes ohne Netz werden vorläufig angelegt und bei Verbindung mit der Produktdatenbank abgeglichen | Next |


### 5.8 Haltbarkeit — Next


| ID   | Feature                                                                              | Prio  |
| ---- | ------------------------------------------------------------------------------------ | ----- |
| HA-1 | Optional ein Haltbarkeitsdatum pro Bestandsartikel erfassen                          | Next  |
| HA-2 | Hinweis bzw. Benachrichtigung: „Läuft in 3 Tagen ab“                                 | Next  |
| HA-3 | Beim Entnehmen den Artikel vorschlagen, der zuerst abläuft (First-Expired-First-Out) | Later |
| HA-4 | Aktion „Weggeworfen“ als eigener Entnahmegrund (Grundlage für Statistik)             | Later |


### 5.9 Verlauf, Statistik und Prognose — Later


| ID   | Feature                                                                                          | Prio  |
| ---- | ------------------------------------------------------------------------------------------------ | ----- |
| ST-1 | Buchungshistorie pro Produkt und Lagerort: wann was ein- und ausgebucht wurde                    | Next  |
| ST-2 | Verlauf des Bestands als Grafik                                                                  | Later |
| ST-3 | **Verbrauchsprognose:** „Milch geht in ca. 3 Tagen aus“, frühzeitig auf die Einkaufsliste setzen | Later |
| ST-4 | Mindestbestand anhand des Verbrauchs vorschlagen                                                 | Later |
| ST-5 | Preise erfassen und Preisverlauf pro Produkt anzeigen                                            | Later |


### 5.10 Mehrere Nutzer und Haushalte — Later

> In Version 1 nicht enthalten. Das Datenmodell soll so gebaut werden, dass diese Features später ohne Umbau möglich sind.


| ID   | Feature                                                                                     | Prio  |
| ---- | ------------------------------------------------------------------------------------------- | ----- |
| MU-1 | Login und eigenes Konto                                                                     | Later |
| MU-2 | **Haushalt:** mehrere Personen und Geräte teilen sich einen Digital Storage Room            | Later |
| MU-3 | Mitglieder in einen Haushalt einladen oder daraus entfernen                                 | Later |
| MU-4 | Änderungen erscheinen live auf allen Geräten des Haushalts                                  | Later |
| MU-5 | Anzeige, wer oder welches Gerät eine Buchung gemacht hat                                    | Later |
| MU-6 | Gleichzeitige Änderungen gehen nicht verloren (z. B. zwei Personen buchen parallel offline) | Later |
| MU-7 | Mehrere unabhängige Haushalte auf der Plattform (Multi-Tenancy), strikt getrennte Daten     | Later |
| MU-8 | Weitere Plattformen: iOS, Web                                                               | Later |


### 5.11 KI-Funktionen — Later


| ID   | Feature                                                                               | Prio  |
| ---- | ------------------------------------------------------------------------------------- | ----- |
| KI-1 | Produkte automatisch kategorisieren                                                   | Later |
| KI-2 | Bild für Produkte ohne Foto generieren                                                | Later |
| KI-3 | Rezeptvorschläge aus dem aktuellen Bestand, vorrangig mit Artikeln, die bald ablaufen | Later |


## 6. Nicht-funktionale Anforderungen (fachlich)


| ID   | Anforderung                                                                                                                                        |
| ---- | -------------------------------------------------------------------------------------------------------------------------------------------------- |
| NF-1 | **Scan-Geschwindigkeit:** ≤ 0,5 s pro Artikel, vergleichbar mit einem Kassenscanner                                                                |
| NF-2 | **Keine Doppelscans:** Ein Artikel, der im Bild bleibt, wird nicht mehrfach gezählt                                                                |
| NF-3 | **Entnehmen in ≤ 2 Interaktionen** ab App-Start (Tipp oder Scan)                                                                                   |
| NF-4 | **Keine Datenverluste** bei fehlender Verbindung oder Absturz                                                                                      |
| NF-5 | **Nachvollziehbarkeit:** Jede Bestandsänderung ist als Buchung dokumentiert. Korrekturen erfolgen durch neue Buchungen, nicht durch Überschreiben. |
| NF-6 | **Mehrsprachigkeit:** Texte lassen sich übersetzen (zunächst Deutsch, später Englisch)                                                             |


## 7. MVP auf einen Blick

```mermaid
flowchart LR
    A[Lagerorte anlegen] --> B[Einkauf einscannen]
    B --> C[Bestand pro Ort & gesamt]
    C --> D[Entnehmen per Scan oder Tipp]
    D --> E{Unter Mindestbestand?}
    E -- ja --> F[Automatisch auf Einkaufsliste]
    F --> G[Im Laden abhaken]
    G --> B
```

**MVP-Umfang:** LO-1, LO-2, LO-4 · SC-1 bis SC-4 · PR-1, PR-3, PR-5 · BE-1 bis BE-5 · EN-1, EN-2 · EK-1 bis EK-4 · OF-1, OF-2 · NF-1 bis NF-5

## 8. Offene Fragen

1. **Mengen und Einheiten:** Zählen wir nur Stück, oder gibt es auch Gewicht und Volumen (z. B. „500 g Mehl, halb verbraucht“)?
2. **Mindestbestand:** Gilt er pro Produkt gesamt (Vorschlag) oder pro Lagerort (z. B. „immer 1 Milch im Kühlschrank“)?
3. **Angebrochene Packungen:** Wie werden sie abgebildet, oder gilt „eine Packung = 1, bis sie leer ist“?
4. **Produktdatenbank:** Ist der Einsatz einer externen Quelle wie Open Food Facts gewünscht, auch hinsichtlich Lizenz und Offline-Cache?
5. **Einbuchen ohne Scan:** Reicht EK-5 (Einkaufsliste → Lagerort) als schnelle Alternative für den Wocheneinkauf?

