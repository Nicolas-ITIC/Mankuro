# Llistes dinàmiques i pantalla inicial

Explicació senzilla de com funcionen les llistes de l'app (RecyclerView, Adapter i Holder) i de la pantalla inicial (splash).

---

## 1. Pantalla inicial (splash)

És la primera pantalla que es veu en obrir l'app. Mostra el **logotip de Mankuro amb una animació** i, passats uns segons, obre la pantalla principal.

**Com ho hem fet (`SplashActivity.kt` i `activity_splash.xml`):**

1. El layout només té una imatge, el logotip (`splashLogo`), sobre el color de fons de l'app.
2. Quan s'obre la pantalla, el logotip comença **invisible i una mica més petit** (transparència 0 i mida 67 %).
3. Amb `animate()`, durant **1 segon** es fa visible i creix fins a la mida normal. És un efecte d'aparèixer amb un petit zoom.
4. Amb un `Handler`, després de **2,2 segons** s'obre `MainPageActivity` amb un fos (`fade in / fade out`).
5. Cridem `finish()` perquè la pantalla inicial es tanqui i, si l'usuari prem enrere, no hi torni.
6. A `onDestroy` cancel·lem el temporitzador (`removeCallbacks`). Així, si l'usuari surt abans d'hora, no s'obre la pantalla principal pel darrere.

---

## 2. Llistes dinàmiques

### Què és un RecyclerView?

És el component d'Android per fer **llistes llargues**. El truc és que no crea una fila per a cada element: només crea les files que es veuen a la pantalla i les **reutilitza** quan fas scroll. Per això és ràpid, encara que la llista tingui molts elements.

### Les peces que necessita

Ho podem explicar amb un exemple de restaurant:

| Peça | Què és | Al nostre codi |
|---|---|---|
| **Les dades** | El que volem mostrar | `Work`, `ExploreWork` i `Post` (són `data class`) |
| **Dades de prova** | Les dades d'exemple | `SampleData` (és un `object`) |
| **El disseny d'una fila** | Com es veu un element | `library_items.xml`, `explore_items.xml`, `community_post_items.xml` |
| **El Holder** | Guarda les vistes d'**una** fila perquè no les haguem de buscar cada vegada | Classe `ViewHolder` dins de cada adapter |
| **L'Adapter** | Agafa les dades i les posa a les files | `LibraryAdapter`, `ExploreAdapter`, `PostAdapter` |
| **El Fragment** | Prepara la llista i decideix què passa en prémer | `LibraryFragment`, `ExploreResultsFragment`, `CommunityFragment` |

L'Adapter és com un cambrer: sap què hi ha a la carta (les dades) i ho porta a cada taula (cada fila de la pantalla).

### Les dades: `data class` i `object`

Una **`data class`** és una classe pensada per guardar informació. Kotlin crea automàticament coses útils (com comparar-les o mostrar-les):

```kotlin
data class Work(
    val id: Int,
    var title: String,
    var type: String,
    var currentChapter: Int,
    val totalChapters: Int,
    var rating: Double
)
```

Un **`object`** és una classe que només té **una còpia**. L'utilitzem per guardar les dades de prova sense haver de crear-la:

```kotlin
object SampleData {
    fun libraryWorks(): MutableList<Work> = mutableListOf(
        Work(1, "Solo Leveling", "Manhwa", 120, 200, 9.5),
        // ... més obres de prova
    )
}
```

### L'Adapter i el Holder

Aquí tens l'esquema de `LibraryAdapter` (els altres dos són iguals):

```kotlin
class LibraryAdapter(
    private val items: MutableList<Work>,
    private val onItemClick: (Work, Int) -> Unit,
    private val onItemLongClick: (Work, Int) -> Unit
) : RecyclerView.Adapter<LibraryAdapter.WorkViewHolder>() {

    // HOLDER: guarda les vistes d'una fila
    class WorkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        // ...
    }

    // 1) Crea una fila nova a partir del disseny XML
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkViewHolder { ... }

    // 2) Diu quantes files hi ha
    override fun getItemCount(): Int = items.size

    // 3) Omple una fila amb les dades de la posició que toca
    override fun onBindViewHolder(holder: WorkViewHolder, position: Int) { ... }
}
```

Els tres mètodes obligatoris són:

1. **`onCreateViewHolder`**: crea una fila buida a partir del disseny.
2. **`getItemCount`**: diu quants elements té la llista.
3. **`onBindViewHolder`**: posa les dades de l'element de cada posició dins de la fila.

### Com avisem el Fragment dels clics

L'Adapter no sap res de Toasts ni de diàlegs. Només rep **dues funcions** (`onItemClick` i `onItemLongClick`) i les crida quan l'usuari prem una fila. El Fragment és qui decideix què fer. Així cada peça fa una sola cosa.

### Els events: afegir, modificar i eliminar

Tots fan un **Log** (es veu al Logcat) i un **Toast** (el missatge petit a la pantalla).

| Llista | Afegir | Modificar | Eliminar |
|---|---|---|---|
| **Biblioteca** | Botó flotant: apareix una obra nova a dalt | Tocar una fila: avança un capítol | Mantenir premuda una fila: surt un diàleg per confirmar |
| **Explorar** | Tocar un resultat: s'afegeix a la biblioteca | — | Mantenir premut: s'oculta el resultat |
| **Comunitat** | Botó "Crear publicació" | Tocar el cor: m'agrada o treure'l | Mantenir premuda una publicació: diàleg per confirmar |

Per actualitzar la llista, l'Adapter té funcions que **modifiquen les dades i avisen el RecyclerView**:

- `notifyItemInserted(posició)` quan s'afegeix,
- `notifyItemChanged(posició)` quan es modifica,
- `notifyItemRemoved(posició)` quan s'elimina.

Així només es redibuixa la fila que ha canviat, no tota la llista.

> Dins dels clics fem servir `bindingAdapterPosition`. Serveix per saber la posició **actual** de la fila, perquè pot haver canviat si s'han afegit o eliminat elements.

### El diàleg de confirmació

Quan s'elimina una obra o una publicació surt un diàleg (`MaterialAlertDialogBuilder`) que pregunta si n'estem segurs. És per **evitar esborrar sense voler** amb una pulsació llarga.

### La funció d'extensió (data)

A cada publicació de la Comunitat es mostra la data. Per formatar-la hem creat una **funció d'extensió**: afegeix un mètode nou a la classe `Date` sense haver de canviar la classe original.

```kotlin
fun Date.toDisplayString(): String =
    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(this)
```

Després s'utilitza com si fos un mètode de `Date`: `post.date.toDisplayString()`.

### Navegació dins d'Explorar

Explorar té **tres passos**: triar el tipus (Manga / Manhwa), triar la categoria i veure els resultats. Cada pas és un Fragment:

1. `ExploreFragment`: botons Manga, Manhwa i Manga + Manhwa.
2. `ExploreCategoryFragment`: Top puntuacions, Recomanats, etc.
3. `ExploreResultsFragment`: la llista de resultats.

Per anar d'un pas a l'altre fem `replace` dins del contenidor principal i `addToBackStack`, així la fletxa de tornar els pot desfer. El tipus i la categoria triats es passen al pas següent com a **arguments** (`Bundle`) i es veuen al títol, per exemple "Manga > Top puntuacions".

### Accessibilitat a les llistes

- Les icones de m'agrada i comentaris fan **48 dp**, la mida mínima recomanada per prémer-les bé.
- L'estat del cor **no es nota només pel color**: també es descriu amb text ("Te gusta" / "Sin me gusta") perquè el llegeixi un lector de pantalla.
- Eliminar demana confirmació abans de fer-ho.
- Les files de les llistes mostren un efecte visual en prémer-les.

---

## 3. Limitacions

- Les dades són **de prova i es guarden només a la memòria**. Si canviem de pestanya i tornem, la llista es reinicia, perquè cada pestanya crea un Fragment nou.
- "Afegir" crea una obra o una publicació d'exemple, no hi ha encara un formulari.
- "Modificar" només avança un capítol, no obre una pantalla d'edició.
- Guardar les dades de forma permanent s'haurà de fer més endavant (base de dades o preferències).
