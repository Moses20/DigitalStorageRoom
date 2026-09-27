# Digital Storage Room – User Stories MVP

> Stand: 27.09.2026 · Grundlage: Feature-Katalog v0.3 (inkl. Entscheidungen E-1 bis E-11) · Umfang: alle Features mit Prio **MVP**
>
> Akzeptanzkriterien im Format **Gegeben / Wenn / Dann**, damit sie sich später direkt in Tests übersetzen lassen. Jede Story trägt die ID aus dem Katalog. Geplante Ablage: eine Datei pro Story unter `docs/stories/`.
>
> This was generated with the help of Opus 5.5
> 
## Vorlage

```markdown
# <ID> – <Titel>

**Status:** Geplant | In Arbeit | Fertig
**Bezug:** <verwandte Feature-IDs, Entscheidungen, NF-Anforderungen>

## Story
Als <Rolle> möchte ich <Ziel>, damit <Nutzen>.

## Akzeptanzkriterien
- **AK-1:** Gegeben … / Wenn … / Dann …

## Randfälle
- …

## Nicht im Umfang
- …
```

---

## Übersicht

| Epic | Stories |
|---|---|
| Lagerorte | LO-1, LO-2, LO-4 |
| Scannen | SC-1, SC-2, SC-3, SC-4 |
| Produkte | PR-1, PR-3, PR-5 |
| Bestand | BE-1, BE-2, BE-3, BE-4, BE-5 |
| Entnehmen | EN-1, EN-2 |
| Einkaufsliste | EK-1, EK-2, EK-3, EK-4 |
| Offline | OF-1, OF-2 |

---

## Epic: Lagerorte

### LO-1 – Lagerorte anlegen, umbenennen und löschen

**Bezug:** LO-2, LO-4

**Story:** Als Nutzer möchte ich meine physischen Lagerorte in der App anlegen, damit ich meinen Bestand dort ablegen kann, wo er wirklich liegt.

**Akzeptanzkriterien**
- **AK-1:** Gegeben die Lagerortübersicht / Wenn ich „Lagerort hinzufügen“ wähle, einen Namen eingebe und speichere / Dann erscheint der neue Lagerort in der Übersicht.
- **AK-2:** Gegeben das Anlegen eines Lagerorts / Wenn der Name leer ist oder nur aus Leerzeichen besteht / Dann kann ich nicht speichern und sehe einen Hinweis.
- **AK-3:** Gegeben ein Lagerort „Keller“ existiert / Wenn ich einen weiteren Lagerort „keller“ anlegen will / Dann werde ich auf den doppelten Namen hingewiesen (Vergleich ohne Groß-/Kleinschreibung).
- **AK-4:** Gegeben ein bestehender Lagerort / Wenn ich ihn umbenenne / Dann bleibt sein Bestand vollständig erhalten.
- **AK-5:** Gegeben ein Lagerort ohne Bestand / Wenn ich ihn lösche und bestätige / Dann verschwindet er aus der Übersicht.
- **AK-6:** Gegeben die App wird zum ersten Mal gestartet / Dann gibt es keine Lagerorte, und die leere Übersicht fordert mich auf, meinen ersten Lagerort anzulegen.

**Nicht im Umfang:** Sortieren der Lagerorte (LO-3).

### LO-2 – Lagerort-Typ zuweisen

**Story:** Als Nutzer möchte ich jedem Lagerort einen Typ geben, damit die App später z. B. Temperaturwarnungen (LO-5) oder Haltbarkeitshinweise darauf abstimmen kann.

**Akzeptanzkriterien**
- **AK-1:** Gegeben das Anlegen eines Lagerorts / Dann muss ich genau einen Typ wählen: `GEKÜHLT`, `TIEFGEKÜHLT`, `RAUMTEMPERATUR` oder `DRAUSSEN`.
- **AK-2:** Gegeben die Typauswahl / Dann ist `RAUMTEMPERATUR` vorausgewählt.
- **AK-3:** Gegeben ein bestehender Lagerort / Wenn ich den Typ ändere / Dann bleibt sein Bestand erhalten.
- **AK-4:** Gegeben die Lagerortübersicht / Dann ist der Typ jedes Lagerorts an einem Symbol erkennbar.

**Hinweis:** Im MVP hat der Typ außer der Anzeige noch keine Wirkung.

### LO-4 – Lagerort mit Bestand löschen

**Bezug:** LO-1, NF-5

**Story:** Als Nutzer möchte ich beim Löschen eines Lagerorts entscheiden, was mit seinem Bestand passiert, damit keine Artikel unbemerkt aus meinem Gesamtbestand verschwinden.

**Akzeptanzkriterien**
- **AK-1:** Gegeben ein Lagerort mit Bestand > 0 / Wenn ich ihn löschen will / Dann sehe ich, wie viele Artikel betroffen sind, und muss wählen: **In anderen Lagerort verschieben** oder **Bestand verwerfen**.
- **AK-2:** Gegeben ich wähle „Verschieben“ / Wenn ich einen Ziel-Lagerort auswähle / Dann wird der gesamte Bestand dorthin gebucht, und danach wird der Lagerort gelöscht.
- **AK-3:** Gegeben ich wähle „Verwerfen“ / Wenn ich bestätige / Dann wird für jeden Artikel eine Entnahme gebucht, und danach wird der Lagerort gelöscht.
- **AK-4:** Gegeben es ist der einzige Lagerort / Dann steht „Verschieben“ nicht zur Verfügung.
- **AK-5:** Gegeben ich breche den Dialog ab / Dann bleiben Lagerort und Bestand unverändert.
- **AK-6:** Gegeben der Bestand wird verworfen / Dann wirkt sich das sofort auf die Einkaufsliste aus (EK-2).

---

## Epic: Scannen

### SC-1 – Scanner öffnen, Lagerort und Modus wählen

**Bezug:** EN-1, NF-3

**Story:** Als Nutzer möchte ich den Scanner mit wenigen Tipps für einen bestimmten Lagerort und Modus öffnen, damit ich meinen Einkauf zügig einbuchen kann.

**Akzeptanzkriterien**
- **AK-1:** Gegeben irgendein Hauptbildschirm / Dann erreiche ich den Scanner mit einem Tipp.
- **AK-2:** Gegeben der Scanner ist geöffnet / Dann sehe ich jederzeit den gewählten **Lagerort** und den **Modus** (Einbuchen/Entnehmen) und kann beides im Scanner wechseln.
- **AK-3:** Gegeben ich öffne den Scanner / Dann sind der zuletzt verwendete Lagerort und Modus vorausgewählt.
- **AK-3a:** Gegeben ich starte die App / Dann öffnet sich **direkt der Scanner** (E-8). Die übrigen Bereiche (Lagerorte, Alle Produkte, Einkaufsliste) erreiche ich über die Navigation.
- **AK-4:** Gegeben ich öffne den Scanner aus der Ansicht eines Lagerorts / Dann ist dieser Lagerort vorausgewählt.
- **AK-5:** Gegeben die Modi / Dann sind Einbuchen und Entnehmen farblich klar unterscheidbar (z. B. grün/rot), damit ich nicht versehentlich im falschen Modus scanne.
- **AK-6:** Gegeben die Kameraberechtigung fehlt / Dann erklärt die App, wofür die Kamera gebraucht wird, und bietet an, die Berechtigung zu erteilen.
- **AK-7:** Gegeben es existiert noch kein Lagerort / Dann werde ich zuerst aufgefordert, einen anzulegen.

### SC-2 – Scan per Auslöser-Button

**Bezug:** NF-1, NF-2, E-7 · Nachfolger: SC-7 (Dauerscan, Next)

**Story:** Als Nutzer möchte ich einen Artikel mit einem Tipp auf den Scan-Button erfassen, damit ich genau kontrolliere, was gebucht wird, und nichts versehentlich doppelt zähle.

**Akzeptanzkriterien**
- **AK-1:** Gegeben der Scanner ist geöffnet / Dann zeigt er ein Live-Kamerabild und einen großen, mit dem Daumen gut erreichbaren **Scan-Button**.
- **AK-2:** Gegeben ein bekannter Barcode ist im Bild / Wenn ich den Scan-Button tippe / Dann wird innerhalb von **≤ 0,5 s** genau **eine** Buchung erzeugt.
- **AK-3:** Gegeben ein Barcode ist im Bild / Solange ich nicht tippe / Dann wird **nichts** gebucht.
- **AK-4:** Gegeben ich tippe auf den Scan-Button, aber kein Barcode ist lesbar / Dann wird nichts gebucht, und ich sehe den Hinweis „Kein Barcode erkannt“.
- **AK-5:** Gegeben mehrere Barcodes sind im Bild / Wenn ich tippe / Dann wird nur der Barcode gebucht, der der Bildmitte am nächsten ist. *(Ein Zielrahmen in der Bildmitte hilft beim Ausrichten.)*
- **AK-6:** Gegeben ich tippe zweimal schnell hintereinander auf denselben Artikel / Dann entstehen zwei Buchungen. *(Gewollt: So lassen sich zwei gleiche Artikel schnell erfassen.)*
- **AK-7:** Gegeben ein unbekannter Barcode / Dann startet PR-1.
- **AK-8:** Gegeben wenig Licht / Dann kann ich im Scanner die Taschenlampe einschalten.

### SC-3 – Rückmeldung pro Scan

**Story:** Als Nutzer möchte ich bei jedem Scan sofort merken, dass und was gebucht wurde, damit ich nicht auf den Bildschirm schauen muss.

**Akzeptanzkriterien**
- **AK-1:** Gegeben ein erfolgreicher Scan / Dann gibt es einen kurzen Ton **und** eine Vibration.
- **AK-2:** Gegeben ein erfolgreicher Scan / Dann werden für ca. 2 s Produktname, Aktion (+1/−1) und neue Menge am Lagerort eingeblendet.
- **AK-3:** Gegeben die Modi Einbuchen und Entnehmen / Dann unterscheiden sich Ton oder Einblendung.
- **AK-4:** Gegeben das Gerät ist lautlos gestellt / Dann gibt es nur Vibration und Einblendung.
- **AK-5:** Gegeben eine Entnahme scheitert, weil der Bestand schon 0 ist (siehe EN-1) / Dann gibt es eine deutlich andere Warn-Rückmeldung.

### SC-4 – Letzten Scan rückgängig machen

**Bezug:** NF-5

**Story:** Als Nutzer möchte ich einen Fehlscan sofort zurücknehmen, damit mein Bestand korrekt bleibt.

**Akzeptanzkriterien**
- **AK-1:** Gegeben ein Scan wurde gebucht / Dann gibt es im Scanner eine gut erreichbare Schaltfläche „Rückgängig“.
- **AK-2:** Gegeben ich tippe auf „Rückgängig“ / Dann wird eine Gegenbuchung erzeugt, und die Einblendung zeigt die korrigierte Menge.
- **AK-3:** Gegeben ich tippe mehrfach / Dann werden die Scans der aktuellen Sitzung nacheinander in umgekehrter Reihenfolge zurückgenommen.
- **AK-4:** Gegeben ich habe den Scanner geschlossen / Dann ist Rückgängig nicht mehr verfügbar. Korrekturen laufen dann über den Bestand (BE-4).
- **AK-5:** Gegeben eine Rücknahme / Dann wird die ursprüngliche Buchung **nicht gelöscht**, sondern durch eine neue Gegenbuchung ausgeglichen.

---

## Epic: Produkte

### PR-1 – Unbekanntes Produkt aus dem Scanner anlegen

**Bezug:** SC-2, E-4

**Story:** Als Nutzer möchte ich ein Produkt, dessen Barcode die App nicht kennt, direkt beim Scannen anlegen, damit ich meinen Einkauf ohne Umweg weiter einbuchen kann.

**Akzeptanzkriterien**
- **AK-1:** Gegeben ein unbekannter Barcode im Modus Einbuchen / Dann öffnet sich ein Formular zum Anlegen mit dem Barcode bereits eingetragen.
- **AK-2:** Gegeben das Formular / Dann ist nur **Name** Pflicht. Alles andere (Foto, Notiz, Mindestbestand) ist optional.
- **AK-3:** Gegeben ich speichere / Dann wird das Produkt angelegt, **sofort 1× am aktuellen Lagerort eingebucht**, und ich lande wieder im Scanner.
- **AK-4:** Gegeben ich breche ab / Dann wird nichts angelegt oder gebucht, und ich lande wieder im Scanner.
- **AK-5:** Gegeben ein unbekannter Barcode im Modus **Entnehmen** / Dann erscheint der Hinweis „Produkt unbekannt – nicht im Bestand“, und es wird nichts angelegt.
- **AK-6:** Gegeben dasselbe Produkt wird danach erneut gescannt / Dann wird es erkannt und ohne Formular gebucht.
- **AK-7:** Gegeben kein Internet / Dann funktioniert das Anlegen ganz normal (OF-1).
- **AK-8:** Gegeben beim Anlegen / Dann kann ich statt eines neuen Produkts auch **ein bestehendes Produkt wählen**. Der Barcode wird diesem Produkt zugeordnet. *(Minimalvariante von PR-4.)*

### PR-3 – Produkte ohne Barcode

**Bezug:** EN-2

**Story:** Als Nutzer möchte ich auch Obst, Gemüse, lose Ware oder Selbstgemachtes erfassen, damit mein Bestand vollständig ist.

**Akzeptanzkriterien**
- **AK-1:** Gegeben die Ansicht eines Lagerorts oder die Produktliste / Wenn ich „Produkt hinzufügen“ wähle / Dann kann ich ein Produkt ohne Barcode anlegen (Name ist Pflicht).
- **AK-2:** Gegeben ich lege das Produkt aus einem Lagerort heraus an / Dann kann ich direkt eine Anfangsmenge angeben (Standard 1).
- **AK-3:** Gegeben ein Produkt ohne Barcode / Dann lässt es sich genauso buchen, suchen und auf die Einkaufsliste setzen wie jedes andere Produkt.
- **AK-4:** Gegeben ein bestehendes Produkt ohne Barcode / Dann kann ich ihm später einen Barcode zuordnen.
- **AK-5:** Gegeben ich tippe einen Namen, der einem bestehenden Produkt ähnelt / Dann schlägt die App das bestehende Produkt vor, um Dubletten zu vermeiden.

### PR-5 – Produkt-Detailseite

**Bezug:** BE-2, EK-1

**Story:** Als Nutzer möchte ich auf einen Blick sehen, wie viel ich von einem Produkt insgesamt habe und wo es liegt, damit ich nicht suchen muss.

**Akzeptanzkriterien**
- **AK-1:** Gegeben ein Produkt / Dann zeigt die Detailseite: Name, Foto (falls vorhanden), Barcode(s), **Gesamtbestand**, **Mindestbestand** und die **Verteilung auf die Lagerorte**.
- **AK-2:** Gegeben die Verteilung / Dann werden nur Lagerorte mit Bestand > 0 angezeigt.
- **AK-3:** Gegeben die Detailseite / Dann kann ich Name, Foto und Mindestbestand bearbeiten.
- **AK-4:** Gegeben die Detailseite / Dann sehe ich, ob das Produkt gerade auf der Einkaufsliste steht.
- **AK-5:** Gegeben ich will ein Produkt löschen / Wenn es noch Bestand hat / Dann werde ich gewarnt und muss bestätigen. Der Bestand wird dabei als Entnahme gebucht.

---

## Epic: Bestand

### BE-1 – Bestand pro Lagerort

**Story:** Als Nutzer möchte ich sehen, was in einem bestimmten Lagerort liegt, damit ich z. B. weiß, was im Keller ist, ohne hinunterzugehen.

**Akzeptanzkriterien**
- **AK-1:** Gegeben ich öffne einen Lagerort / Dann sehe ich alle Produkte dieses Lagerorts mit ihrer Menge.
- **AK-2:** Gegeben Produkte unter Mindestbestand / Dann sind sie in der Liste markiert (z. B. mit einem Einkaufswagen-Symbol).
- **AK-3:** Gegeben ein leerer Lagerort / Dann sehe ich einen Hinweis und kann direkt den Scanner für diesen Lagerort öffnen.
- **AK-4:** Gegeben die Lagerortübersicht / Dann sehe ich pro Lagerort die Anzahl der Artikel.

### BE-2 – Gesamtbestand über alle Lagerorte

**Bezug:** PR-5, EK-2, E-2

**Story:** Als Nutzer möchte ich alle Produkte über alle Lagerorte hinweg in einer Liste sehen, damit ich weiß, wie viel ich insgesamt von etwas habe.

**Akzeptanzkriterien**
- **AK-1:** Gegeben die Ansicht „Alle Produkte“ / Dann wird jedes Produkt **einmal** mit seinem Gesamtbestand angezeigt.
- **AK-2:** Gegeben ein Produkt an mehreren Orten / Dann sehe ich die Verteilung direkt in der Liste, z. B. „3 · 1× Kühlschrank, 2× Keller“.
- **AK-3:** Gegeben Milch: 1× Kühlschrank, 2× Keller / Wenn ich 1× im Keller entnehme / Dann zeigt die Gesamtansicht sofort 2.
- **AK-4:** Gegeben ich tippe auf ein Produkt / Dann öffnet sich die Detailseite (PR-5).

### BE-3 – Suchen und sortieren

**Story:** Als Nutzer möchte ich Produkte schnell finden, damit ich auch bei großem Bestand den Überblick behalte.

**Akzeptanzkriterien**
- **AK-1:** Gegeben eine Bestandsliste (pro Lagerort oder gesamt) / Wenn ich einen Suchbegriff eingebe / Dann wird die Liste während der Eingabe gefiltert (Teilwort, ohne Groß-/Kleinschreibung).
- **AK-2:** Gegeben die Suche / Dann findet sie auch Produkte mit Umlauten bei vereinfachter Eingabe (z. B. „kase“ → „Käse“).
- **AK-3:** Gegeben eine Liste / Dann kann ich sortieren nach **Name**, **Menge** und **Einlagerungsdatum** (zuletzt eingebucht), jeweils auf- und absteigend.
- **AK-4:** Gegeben ich habe eine Sortierung gewählt / Dann merkt sich die App diese pro Ansicht.
- **AK-5:** Gegeben die Suche findet nichts / Dann wird angeboten, das Produkt anzulegen (PR-3).

### BE-4 – Menge mit +/− anpassen und speichern

**Bezug:** EN-2, NF-5

**Story:** Als Nutzer möchte ich Mengen in der Liste korrigieren, damit ich Abweichungen zur Realität schnell beheben kann, ohne durch Fehltipps etwas kaputtzumachen.

**Akzeptanzkriterien**
- **AK-1:** Gegeben die Bestandsliste eines Lagerorts / Wenn ich den **Bearbeitungsmodus** aktiviere / Dann erscheinen pro Produkt + und −.
- **AK-2:** Gegeben ich tippe +/− / Dann ändert sich die angezeigte Menge sofort, ist aber als **ungespeichert** markiert.
- **AK-3:** Gegeben ungespeicherte Änderungen / Wenn ich „Speichern“ tippe / Dann wird pro geändertem Produkt eine Buchung mit der Differenz erzeugt.
- **AK-4:** Gegeben ungespeicherte Änderungen / Wenn ich „Verwerfen“ tippe oder die Ansicht verlasse / Dann werde ich gefragt, ob ich verwerfen will.
- **AK-5:** Gegeben die Menge ist 0 / Dann ist − deaktiviert. Negative Mengen sind nicht möglich.
- **AK-6:** Gegeben ein Produkt geht +2 und dann −2 / Dann entsteht beim Speichern **keine** Buchung.

### BE-5 – Bestand 0 bleibt sichtbar

**Bezug:** EK-2

**Story:** Als Nutzer möchte ich, dass aufgebrauchte Produkte nicht einfach verschwinden, damit ich sehe, was fehlt, und sie mit einem Tipp wieder auffüllen kann.

**Akzeptanzkriterien**
- **AK-1:** Gegeben ein Produkt erreicht an einem Lagerort Menge 0 / Dann bleibt es in der Liste dieses Lagerorts, **ausgegraut** und ans Ende sortiert.
- **AK-2:** Gegeben die Gesamtansicht / Dann bleibt ein Produkt mit Gesamtbestand 0 ebenfalls ausgegraut sichtbar.
- **AK-3:** Gegeben ein Schalter „Leere ausblenden“ / Wenn ich ihn aktiviere / Dann werden Produkte mit Menge 0 ausgeblendet.
- **AK-4:** Gegeben ein Produkt mit Menge 0 in einem Lagerort / Wenn ich „Aus diesem Lagerort entfernen“ wähle / Dann verschwindet es dort. Das Produkt selbst bleibt bestehen.
- **AK-5:** Gegeben ein ausgegrautes Produkt / Wenn ich + tippe oder es scanne / Dann ist es wieder normal sichtbar.

---

## Epic: Entnehmen

### EN-1 – Entnehmen per Scan

**Bezug:** SC-1 bis SC-4, NF-3

**Story:** Als Nutzer möchte ich einen Artikel beim Verbrauch kurz scannen, damit der Bestand ohne Nachdenken stimmt.

**Akzeptanzkriterien**
- **AK-1:** Gegeben der Scanner im Modus Entnehmen / Wenn ich einen bekannten Barcode scanne / Dann wird 1× am gewählten Lagerort ausgebucht.
- **AK-2:** Gegeben der Bestand am gewählten Lagerort ist 0, das Produkt liegt aber an einem anderen Ort / Dann fragt die App: „Nicht in *Kühlschrank* – aus *Keller* entnehmen?“
- **AK-3:** Gegeben das Produkt hat nirgends Bestand / Dann wird nichts gebucht, und es gibt eine Warn-Rückmeldung (SC-3 AK-5).
- **AK-4:** Gegeben die App startet direkt im Scanner (E-8) / Dann ist Entnehmen in **≤ 2 Tipps** erledigt: ggf. Modus auf „Entnehmen“ wechseln → Scan-Button.

**Nicht im Umfang:** Lagerort automatisch erkennen (EN-3).

### EN-2 – Entnehmen per Tipp („Aufgebraucht“)

**Bezug:** BE-4, NF-3

**Story:** Als Nutzer möchte ich einen verbrauchten Artikel ohne Kamera mit einer Geste ausbuchen, damit Entnehmen auch dann gelingt, wenn die Verpackung schon im Müll ist.

**Akzeptanzkriterien**
- **AK-1:** Gegeben eine Bestandsliste (pro Lagerort oder gesamt) / Wenn ich ein Produkt nach links wische / Dann wird **sofort** 1× ausgebucht, ohne Speichern.
- **AK-2:** Gegeben eine Entnahme per Wischen / Dann erscheint für ca. 5 s „1× Milch entnommen – Rückgängig“.
- **AK-3:** Gegeben ich wische in der **Gesamtansicht** und das Produkt liegt an mehreren Orten / Dann fragt die App, aus welchem Lagerort entnommen wird. Der Ort mit dem geringsten Bestand ist vorausgewählt.
- **AK-4:** Gegeben die Menge ist 0 / Dann ist die Wischgeste deaktiviert.
- **AK-5:** Gegeben die App startet im Scanner / Dann erreiche ich die Bestandsliste mit einem Tipp über die Navigation. *(Der Wisch-Weg braucht damit 2 Interaktionen plus ggf. Suche. NF-3 ist über den Scan-Weg EN-1 erfüllt.)*

> **Abgrenzung zu BE-4:** Das Wischen „Aufgebraucht“ ist der schnelle Alltagsweg und wird **sofort** gebucht (mit Rückgängig). Der Bearbeitungsmodus mit +/− und Speichern (BE-4) dient Korrekturen. So ist sowohl NF-3 (schnell) als auch der Schutz vor Fehltipps erfüllt.

---

## Epic: Einkaufsliste

### EK-1 – Mindestbestand festlegen

**Bezug:** E-2, PR-5

**Story:** Als Nutzer möchte ich pro Produkt festlegen, wie viel ich mindestens im Haus haben will, damit die App weiß, wann ich nachkaufen muss.

**Akzeptanzkriterien**
- **AK-1:** Gegeben ein Produkt / Dann kann ich einen Mindestbestand als ganze Zahl ≥ 0 festlegen (Detailseite und beim Anlegen).
- **AK-2:** Gegeben ein neues Produkt / Dann ist der Mindestbestand **0** (keine automatische Einkaufsliste).
- **AK-3:** Gegeben der Mindestbestand / Dann bezieht er sich auf den **Gesamtbestand über alle Lagerorte** (E-2).
- **AK-4:** Gegeben ich ändere den Mindestbestand / Dann wird die Einkaufsliste sofort angepasst.

### EK-2 – Automatische Einkaufsliste

**Bezug:** EK-1, BE-2

**Story:** Als Nutzer möchte ich eine Einkaufsliste, die sich aus meinem Bestand von selbst füllt, damit ich vor dem Einkauf nicht alle Lagerorte abklappern muss.

**Akzeptanzkriterien**
- **AK-1:** Gegeben Mindestbestand 3, Gesamtbestand 1 / Dann steht das Produkt auf der Liste mit Menge **2**.
- **AK-2:** Gegeben Mindestbestand 3, Gesamtbestand 3 / Dann steht das Produkt **nicht** auf der Liste. *(Nachkaufen erst bei Unterschreitung.)*
- **AK-3:** Gegeben Mindestbestand 0 / Dann steht das Produkt nie automatisch auf der Liste.
- **AK-4:** Gegeben eine Buchung ändert den Gesamtbestand (Scan, Wischen, Speichern, Rückgängig, Lagerort löschen) / Dann wird die Liste **sofort** aktualisiert, auch offline.
- **AK-5:** Gegeben der Bestand steigt wieder auf ≥ Mindestbestand / Dann verschwindet der automatische Eintrag.
- **AK-6:** Gegeben die Liste / Dann erkenne ich automatische und manuelle Einträge (EK-3) optisch auseinander.

### EK-3 – Manuelle Einträge

**Story:** Als Nutzer möchte ich auch Dinge auf die Liste setzen, die unter dem Mindestbestand noch nicht fehlen oder gar nicht erfasst sind, damit ich nur eine Liste brauche.

**Akzeptanzkriterien**
- **AK-1:** Gegeben die Einkaufsliste / Wenn ich einen Text eingebe, z. B. „Geschenkpapier“ / Dann wird ein freier Eintrag angelegt.
- **AK-2:** Gegeben die Eingabe / Wenn der Text zu einem bestehenden Produkt passt / Dann wird es vorgeschlagen, und der Eintrag wird mit dem Produkt verknüpft.
- **AK-3:** Gegeben ein manueller Eintrag / Dann kann ich eine Menge angeben (Standard 1).
- **AK-4:** Gegeben ein Produkt steht schon automatisch auf der Liste / Wenn ich es manuell hinzufüge / Dann entsteht **kein** doppelter Eintrag. Stattdessen wird die Menge des bestehenden Eintrags erhöht.
- **AK-5:** Gegeben ein manueller Eintrag / Dann kann ich ihn bearbeiten und löschen.

### EK-4 – Im Laden abhaken

**Story:** Als Nutzer möchte ich im Laden abhaken, was im Wagen liegt, damit ich nichts vergesse.

**Akzeptanzkriterien**
- **AK-1:** Gegeben ein Eintrag / Wenn ich ihn antippe / Dann wird er abgehakt und ans Ende der Liste verschoben. Nochmal tippen hebt das auf.
- **AK-2:** Gegeben die Liste im Laden / Dann ist sie offline nutzbar, und die Tippfläche ist mit einer Hand gut bedienbar.
- **AK-3:** Gegeben „Abgehakte entfernen“ / Dann werden **manuelle** abgehakte Einträge gelöscht.
- **AK-4:** Gegeben ein **automatischer** Eintrag ist abgehakt / Dann bleibt er abgehakt, bis der Bestand wieder ≥ Mindestbestand ist (durch Einbuchen zu Hause). Dann verschwindet er (EK-2 AK-5).
- **AK-5:** Gegeben ein abgehakter automatischer Eintrag / Wenn der Bestand danach weiter sinkt / Dann wird die Menge angepasst, der Eintrag bleibt aber abgehakt.

**Nicht im Umfang:** Abgehakte Artikel direkt einbuchen (EK-5).

---

## Epic: Offline

### OF-1 – Kernfunktionen ohne Internet

**Bezug:** NF-4

**Story:** Als Nutzer möchte ich die App im Keller ohne Netz vollständig nutzen, damit ich nicht auf eine Verbindung achten muss.

**Akzeptanzkriterien**
- **AK-1:** Gegeben kein Netz (Flugmodus) / Dann funktionieren ohne Einschränkung: Lagerorte verwalten, Scannen (Ein- und Ausbuchen), Produkte anlegen, Bestand ansehen und bearbeiten, Einkaufsliste nutzen.
- **AK-2:** Gegeben kein Netz / Dann erscheint **keine** Fehlermeldung, und kein Ladebalken blockiert die Bedienung.
- **AK-3:** Gegeben die App wird offline beendet oder stürzt ab / Wenn ich sie neu starte / Dann sind alle Buchungen erhalten.

### OF-2 – Lokale Speicherung mit vorbereiteter Sync-Schnittstelle

**Bezug:** NF-4, NF-5, E-6 · Nachfolger: OF-5 (Sync), OF-6 (Backup), MU-4/MU-6/MU-9

**Story:** Als Nutzer möchte ich, dass alle Buchungen zuverlässig auf dem Gerät gespeichert sind, damit nichts verloren geht. Als Entwickler möchte ich, dass sie schon so aufgebaut sind, dass später ein Backend, weitere Geräte und Hardware-Scanner sie übernehmen können, ohne dass ich die App umbauen muss.

**Akzeptanzkriterien**
- **AK-1:** Gegeben eine Bestandsänderung / Dann wird sie als **eigenständige Buchung** gespeichert mit: eindeutiger ID, Zeitpunkt, Art (Einbuchen/Entnehmen/Korrektur/Gegenbuchung), Produkt, Lagerort, Menge und **Quelle** (im MVP: dieses Smartphone).
- **AK-2:** Gegeben alle Buchungen / Dann lässt sich der Bestand jederzeit vollständig daraus berechnen.
- **AK-3:** Gegeben die App / Dann ist der Zugriff auf Buchungen, Produkte und Lagerorte hinter einer **Schnittstelle** gekapselt. Die lokale Speicherung ist eine Implementierung davon. Eine Backend-Anbindung kann später als weitere Implementierung ergänzt werden.
- **AK-4:** Gegeben IDs von Produkten, Lagerorten und Buchungen / Dann sind sie **weltweit eindeutig** (z. B. UUID) und werden auf dem Gerät erzeugt. So gibt es keine Konflikte, wenn später Daten mehrerer Geräte zusammengeführt werden.
- **AK-5:** Gegeben die Schnittstellendefinition / Dann ist sie dokumentiert und erlaubt Buchungen von Quellen **ohne Bildschirm** (Hardware-Scanner MU-9 über ESP32-Host, E-11), die nur „Barcode X, Lagerort Y, Ein-/Ausbuchen“ liefern. Unbekannte Barcodes solcher Quellen landen in einer Warteschlange, die in der App aufgelöst wird.

> **Hinweis:** Die konkrete Schnittstellendefinition (Datenformate, API) erarbeiten wir in der Architekturphase. Das Funkprotokoll zwischen Hardware-Scanner und ESP32-Host ist davon getrennt (E-11).

## Geklärte Punkte

| # | Frage | Entscheidung |
|---|---|---|
| Q-1 | Wann darf ein Barcode erneut gezählt werden? | Im MVP gibt es einen **Scan-Button**: ein Tipp = ein Scan (E-7). Der Dauerscan kommt später als SC-7. |
| Q-2 | Wischen sofort, +/− mit Speichern? | **Ja** (E-9) |
| Q-3 | Abgehakte automatische Einträge bleiben bis zum Einbuchen? | **Ja** (E-10) |
| Q-4 | Entnehmen in ≤ 2 Tipps? | **Die App startet direkt im Scanner** (E-8) |
| Q-5 | Backend im MVP? | **Nein.** Rein lokal, aber mit definierter Schnittstelle für Backend, mehrere Geräte und Hardware-Scanner (E-6, OF-2) |
