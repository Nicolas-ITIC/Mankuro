# Menú de navegació de Mankuro

Explicació del funcionament del menú principal de l'app: com està dissenyat, com navega entre pantalles i quines decisions vam prendre.

## Resum

L'app té **una sola Activity principal** (`MainPageActivity`) amb un **menú inferior** (`BottomNavigationView`). Cada opció del menú carrega un **Fragment** diferent dins la zona central de la pantalla. Així, la barra inferior es manté sempre al mateix lloc i només canvia el contingut.

| Opció del menú | Fragment que carrega |
|---|---|
| Biblioteca | `LibraryFragment` |
| Explorar | `ExploreFragment` |
| Comunitat | `CommunityFragment` |
| Perfil | `ProfileFragment` |
| Ajustos | `SettingsFragment` |

### Fitxers implicats

| Fitxer | Paper |
|---|---|
| `activity_main_page.xml` | Layout de l'Activity: capçalera, zona central i menú inferior |
| `menu/bottom_nav_menu.xml` | Defineix les cinc opcions del menú |
| `color/bottom_nav_colors.xml` | Selector de colors per ressaltar la pestanya seleccionada |
| `MainPageActivity.kt` | Escolta els clics i canvia de Fragment |

---

## 1. Disseny del menú

A `activity_main_page.xml` vam **reemplaçar la barra inferior manual** (feta amb `LinearLayout`) pel component oficial **`BottomNavigationView`** de Material Design.

```xml
<com.google.android.material.bottomnavigation.BottomNavigationView
    android:id="@+id/bottomNavigation"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/surface"
    android:elevation="8dp"
    app:labelVisibilityMode="labeled"
    app:itemIconTint="@color/bottom_nav_colors"
    app:itemTextColor="@color/bottom_nav_colors"
    app:menu="@menu/bottom_nav_menu" />
```

- **`app:menu`** connecta la barra amb `@menu/bottom_nav_menu`, que conté les nostres cinc opcions principals (Biblioteca, Explorar, Comunitat, Perfil i Ajustos).
- **`app:itemIconTint` i `app:itemTextColor`** apliquen el selector `@color/bottom_nav_colors`, que fa que la pestanya seleccionada es vegi diferent de les altres.
- **`app:labelVisibilityMode="labeled"`** mostra sempre el text sota la icona, també a les pestanyes no seleccionades.

La zona central és un `FrameLayout` amb l'ID `mainContent`. Ocupa tot l'espai que sobra entre la capçalera i el menú (`layout_height="0dp"` amb `layout_weight="1"`) i fa de **marc on carreguem cada secció**:

```xml
<FrameLayout
    android:id="@+id/mainContent"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:layout_weight="1"
    android:background="@color/background" />
```

---

## 2. Lògica de navegació

Tot el codi és a `MainPageActivity.kt`, dins el mètode `onCreate`.

```kotlin
class MainPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        // Ajusta el padding perquè el contingut no quedi sota les barres del sistema
        val mainLayout = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Pantalla inicial: només la primera vegada que es crea l'Activity
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContent, LibraryFragment())
                .commit()
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.nav_library -> LibraryFragment()
                R.id.nav_explore -> ExploreFragment()
                R.id.nav_profile -> ProfileFragment()
                R.id.nav_settings -> SettingsFragment()
                R.id.nav_community -> CommunityFragment()
                else -> null
            }

            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContent, selectedFragment)
                    .commit()
                true
            } else {
                false
            }
        }
    }
}
```

Pas a pas:

1. **Vincular la vista.** Amb `findViewById<BottomNavigationView>(R.id.bottomNavigation)` obtenim el menú inferior.
2. **Fragment inicial.** Si l'Activity es crea per primera vegada (`savedInstanceState == null`), carreguem `LibraryFragment` perquè l'usuari no vegi la zona central buida. Si l'Activity es recrea (per exemple en girar el mòbil), Android ja restaura el Fragment que hi havia i no cal tornar-lo a posar.
3. **Detecció de clics.** Amb `setOnItemSelectedListener` capturem l'opció que prem l'usuari a través de `item.itemId`.
4. **Selecció del Fragment.** Un `when` comprova quin botó s'ha premut i crea el Fragment corresponent. Si l'ID no és cap dels cinc, el resultat és `null`.
5. **Reemplaçament de la vista.** Si el Fragment és vàlid, una transacció de `supportFragmentManager` fa `replace(R.id.mainContent, selectedFragment)` i `commit()`, i canvia la pantalla central.
6. **Valor de retorn.** El listener retorna `true` quan s'ha gestionat el clic, i així la icona premuda queda marcada com a activa. Retorna `false` si l'opció no és coneguda.

---

## 3. Per què Activity + Fragments

Una alternativa seria tenir una Activity diferent per a cada secció. Vam escollir **Fragments** perquè:

- El menú inferior i la capçalera es **mantenen fixos**; només es canvia el contingut de `mainContent`.
- Canviar de Fragment és més lleuger que obrir una Activity nova.
- Cada secció té el seu propi codi i layout, però comparteix el mateix marc.

La pantalla d'**Accessibilitat** és una **Activity pròpia** (`AccessibilityActivity`) i no un Fragment del menú, perquè no és una de les cinc seccions principals i es tanca amb el botó de tornar.

---

## 4. Accessibilitat del menú

- Utilitzem el component **natiu `BottomNavigationView`**, que ja exposa cada opció amb el seu títol als lectors de pantalla.
- Amb `labelVisibilityMode="labeled"`, **totes les opcions mostren text**, no només la icona.
- El selector `bottom_nav_colors` fa que la pestanya activa es distingeixi de les altres.
- A més del menú, l'app té una pantalla d'**Accessibilitat** amb ajustos de mida del text, alt contrast, reducció d'animacions i vibració.

---

## 5. Possibles millores

Coses que hem detectat i que podríem fer més endavant:

- **Conservar l'estat de cada pestanya.** Ara, cada vegada que es prem una opció es crea un Fragment nou amb `replace`, de manera que es perd, per exemple, la posició del scroll d'una llista. Es podria solucionar guardant les instàncies o amb el **Navigation Component**.
- **Gestionar el clic sobre la pestanya ja activa** amb `setOnItemReselectedListener` (per exemple, per tornar a dalt de la llista).
- **Capçalera amb `MaterialToolbar`** que canviï el títol segons la pestanya, en lloc de la capçalera fixa actual.
