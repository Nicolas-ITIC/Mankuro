# Accessibilitat a Mankuro

Documentació de les accions que hem fet perquè l'app sigui més fàcil d'utilitzar per a tothom: persones amb poca visió, que necessiten text més gran, que prefereixen menys moviment o que utilitzen un lector de pantalla.

## Resum

Hem treballat l'accessibilitat en tres nivells:

1. **Una pantalla d'ajustos d'accessibilitat** perquè l'usuari adapti l'app (mida del text, alt contrast, animacions i vibració).
2. **Decisions de disseny** que ja fan l'app més accessible per defecte (descripcions, mides tàctils, contrast, components natius).
3. **Proves i correccions** d'errors que vam trobar durant el desenvolupament.

---

## 1. Pantalla d'Accessibilitat

La pantalla `AccessibilityActivity` (layout `activity_accessibility.xml`) agrupa quatre ajustos. Tots es llegeixen i es desen a través de la classe d'ajuda `AccessibilityHelper`.

| Ajust | Què fa | Com ho hem fet |
|---|---|---|
| **Mida del text** | L'usuari tria una mida de text entre diverses opcions | Un quadre de diàleg (`MaterialAlertDialogBuilder`) amb opció única. En triar, es desa l'índex i es crida `recreate()` per aplicar-ho |
| **Text d'alt contrast** | Fons negre i text blanc, més fàcil de llegir | Un interruptor que activa el tema `Theme.Mankuro.HighContrast` (fons `#000000`, text blanc) i recrea l'Activity |
| **Reduir animacions** | Evita moviments innecessaris | Un interruptor; quan s'activa, treu les animacions de finestra amb `window.setWindowAnimations(0)` |
| **Vibració en prémer** | Retorn tàctil en botons i controls | Un interruptor; si està actiu, `AccessibilityHelper.performHapticFeedback` fa vibrar el dispositiu en prémer botons i interruptors |

Com s'apliquen els ajustos:

- Les Activities principals (`AccessibilityActivity` i `MainPageActivity`) criden `AccessibilityHelper.applyAccessibility(this)` **abans de `super.onCreate`**, així el tema i la mida del text ja són els correctes quan es crea la pantalla.
- En obrir la pantalla, els interruptors es posen primer al seu estat guardat i **després** s'afegeixen els listeners. D'aquesta manera no es dispara cap canvi en carregar-la.

---

## 2. Altres accions de disseny accessible

| Acció | On | Per què |
|---|---|---|
| **Descripcions de contingut** (`contentDescription`) | Botons de tornar, logotip, imatges del chibi, botó de tancar sessió | Els lectors de pantalla (TalkBack) poden llegir què és cada element |
| **Textos a `strings.xml`** | Tota l'app | Cap text escrit directament als layouts; facilita traduir-los i mantenir-los |
| **Mides de text en `sp`** | Tots els layouts | El text s'adapta a la mida de lletra que l'usuari tingui al sistema |
| **Zones tàctils de 48dp** | Botons d'icona (tornar, menú, tancar sessió, etc.) | És la mida mínima recomanada per prémer sense errors |
| **Interruptors que canvien de color** | Ajustos i Accessibilitat | L'estat activat/desactivat es distingeix pel color i també pel moviment del cercle, no només per un dels dos |
| **Menú inferior amb etiquetes** | `BottomNavigationView` amb `labelVisibilityMode="labeled"` | Totes les opcions mostren text, no només la icona |
| **Component natiu de menú** | `BottomNavigationView` | Exposa cada opció amb el seu títol als lectors de pantalla |
| **Botó per mostrar la contrasenya** | Login i registre (`endIconMode="password_toggle"`) | Permet comprovar el que s'escriu |
| **Tipus d'entrada adequats** | Camps d'email (`textEmailAddress`) i contrasenya (`textPassword`) | El teclat que surt és el correcte |
| **Feedback visual en elements clicables** | Files d'ajustos | Mostren un efecte en prémer-les |
| **Mode clar i fosc** | `values/` (fosc, per defecte) i `values-notnight/` (clar) | L'usuari pot veure l'app amb la paleta que li resulti més còmoda |
| **Estils i tema comuns** | `themes.xml`, `styles.xml` | Aspecte coherent a totes les pantalles, que ajuda a trobar els elements |

---

## 3. Contrast de colors

Hem calculat la relació de contrast (fórmula WCAG) de les combinacions principals. El nivell **AA** demana almenys **4,5:1** per a text normal.

| Mode | Combinació | Contrast |
|---|---|---|
| Fosc | Text principal sobre fons | 19,0:1 |
| Fosc | Text principal sobre targeta | 17,6:1 |
| Fosc | Gris secundari sobre targeta | 8,7:1 |
| Fosc | Color d'accent sobre targeta | 5,2:1 |
| Clar | Text principal sobre fons | 16,9:1 |
| Clar | Text principal sobre targeta | 17,9:1 |
| Clar | Gris secundari sobre targeta | 6,9:1 |
| Clar | Text blanc sobre botó principal | 5,7:1 |
| Alt contrast | Blanc sobre negre | 21,0:1 |
| Alt contrast | Color principal (`#A855F7`) sobre negre | 5,3:1 |

---

## 4. Problemes detectats i corregits

Durant el desenvolupament vam trobar aquests errors d'accessibilitat i els vam solucionar:

1. **Recreació contínua de la pantalla principal.** `MainPageActivity` comparava l'estat dels ajustos amb uns valors inicials que mai s'assignaven; en tenir un ajust actiu, es tornava a crear sense parar. Ara llegeix l'estat en crear-se i només es recrea quan un ajust canvia de debò.
2. **Interruptors indistingibles.** Tenien el mateix color activats i desactivats, de manera que només es notava pel moviment del cercle. Ara el color canvia segons l'estat.
3. **Zones tàctils massa petites.** Alguns botons d'icona feien 40dp; els hem passat a 48dp.
4. **Tema d'alt contrast que feia fallar l'app.** El tema utilitzava colors escrits directament en lloc de recursos de color i el menú inferior (`BottomNavigationView`) no s'arribava a crear. Hem passat els colors a recursos de color.

---

## 5. Proves realitzades

> Completar amb el que s'hagi fet realment i afegir captures.

| Prova | Resultat |
|---|---|
| Mida de lletra del sistema al màxim |<img width="289" height="317" alt="image" src="https://github.com/user-attachments/assets/3cd776c5-0462-4c94-ba5a-80f2f1a0dead" />
|
| Tema d'alt contrast comparat amb el normal | <img width="286" height="435" alt="image" src="https://github.com/user-attachments/assets/568c4734-d272-47e9-8459-d28ed888a296" />
|
