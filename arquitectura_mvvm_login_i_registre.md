# Arquitectura MVVM: Login i Registre

Explicació senzilla de com hem aplicat el patró **MVVM (Model - View - ViewModel)** a les pantalles d'inici de sessió i registre de Mankuro per separar la interfície d'usuari de la lògica de validació.

---

## Resum

Abans de refactoritzar aquestes pantalles, les `Activity` ho feien tot al mateix lloc: pintar la pantalla, recollir els textos i navegar. Amb el patró **MVVM** separem les responsabilitats en tres peces clares:

- **View (`Activity` + XML):** Només mostra la interfície, recull el que escriu l'usuari i reacciona als canvis. No té cap `if` per validar correus ni contrasenyes.
- **ViewModel:** Guarda l'estat del formulari i conté tota la lògica de negoci (les comprovacions dels camps).
- **LiveData:** És el canal reactiu que connecta el `ViewModel` amb la `View`. Quan el `ViewModel` actualitza un valor, la pantalla s'entera automàticament i es redibuixa.

### Fitxers implicats

| Fitxer | Paper |
|---|---|
| `logInActivity.kt` i `activity_log_in.xml` | Vista del Login: recull email i contrasenya, observa els errors i navega |
| `LogInViewModel.kt` | Valida el formulari de Login i exposa l'estat amb `LiveData` |
| `SignUpActivity.kt` i `activity_sign_up.xml` | Vista del Registre: envia els canvis de text al `ViewModel` i mostra els avisos |
| `SignUpViewModel.kt` | Guarda els camps del registre, els valida i indica si el formulari és correcte |

---

## 1. Per què fem servir ViewModel i LiveData?

### Avantatges del `ViewModel`

1. **Separació de responsabilitats:** El codi de les `Activity` queda molt més net i fàcil de mantenir, ja que només s'ocupen de la UI.
2. **Supervivència als canvis de configuració:** Si l'usuari gira el mòbil i Android destrueix i torna a crear l'`Activity`, el `ViewModel` es manté viu a la memòria i no es perden les dades ni els missatges d'error.

### Com funciona `LiveData` (observabilitat)

Per protegir les dades i seguir bones pràctiques d'encapsulació, per a cada dada definim sempre **dues variables** dins del `ViewModel`:

- **`MutableLiveData` (privat):** Comença amb guió baix (ex. `_emailError`). Només el `ViewModel` pot modificar-ne el valor.
- **`LiveData` (públic):** És de només lectura (ex. `emailError`). L'`Activity` s'hi subscriu amb `.observe(this)` per reaccionar cada vegada que canvia.

```kotlin
// Dins del ViewModel
private val _emailError = MutableLiveData<String>()
val emailError: LiveData<String> = _emailError

// Dins de l'Activity
viewModel.emailError.observe(this) { errorMsg ->
    tilEmail.error = if (errorMsg.isEmpty()) null else errorMsg
}
```

> L'avantatge principal de `LiveData` és que respecta el **cicle de vida** de l'`Activity`: només envia actualitzacions quan la pantalla està activa i visible, evitant errors en segon pla o fugues de memòria.

---

## 2. Pantalla de Login (`LogInViewModel`)

A la pantalla de Login, l'usuari escriu el correu i la contrasenya i, en prémer el botó d'iniciar sessió, `logInActivity` envia els textos a `viewModel.validateLogin(email, password)`.

### Els tipus `LiveData` del Login

| Variable `LiveData` | Tipus | Què guarda |
|---|---|---|
| `emailError` | `LiveData<String>` | El missatge d'error del correu, o `""` si és vàlid |
| `passwordError` | `LiveData<String>` | El missatge d'error de la contrasenya, o `""` si és vàlida |
| `loginSuccess` | `LiveData<Boolean>` | Passa a `true` quan tots els camps passen la validació |

### Validacions implementades

1. **Correu electrònic:** Es netegen els espais sobrants (`.trim()`), es comprova que no estigui buit i es verifica que tingui format de correu vàlid amb `Patterns.EMAIL_ADDRESS`.
2. **Contrasenya:** Es comprova que el camp no estigui buit.

### Com s'inicialitza i es connecta (`logInActivity.kt`)

Inicialitzem el `ViewModel` amb `ViewModelProvider`:

```kotlin
viewModel = ViewModelProvider(this)
    .get(LogInViewModel::class.java)
```

Pas a pas del flux:

1. L'usuari prem `btnLogin`.
2. `logInActivity` llegeix el contingut de `etEmail` i `etPassword` i crida `viewModel.validateLogin(email, password)`.
3. `LogInViewModel` fa les comprovacions i actualitza `_emailError`, `_passwordError` o `_loginSuccess`.
4. Els observadors de l'`Activity` reben el canvi al moment: mostren el text vermell al `TextInputLayout` corresponent (o el treuen assignant `null` si arriba `""`).
5. Si `loginSuccess` passa a `true`, es mostra un `Toast` i s'obre `MainPageActivity` tancant el Login amb `finish()`.

---

## 3. Pantalla de Registre (`SignUpViewModel`)

A la pantalla de Registre hem aplicat el mateix patró MVVM, però actualitzant les dades al `ViewModel` en temps real a mesura que l'usuari escriu amb `doAfterTextChanged`. Quan prem el botó de crear compte (`btnSignUp`), només cal cridar `viewModel.validateForm()`.

### Els tipus `LiveData` del Registre

| Variable `LiveData` | Tipus | Què guarda |
|---|---|---|
| `username`, `email`, `password`, `confirmPassword` | `LiveData<String>` | Els textos que l'usuari va escrivint a cada camp |
| `usernameError`, `emailError`, `passwordError`, `confirmPasswordError` | `LiveData<String?>` | El missatge d'error de cada camp (`null` si no hi ha error) |
| `isFormValid` | `LiveData<Boolean>` | S'inicia a `false` i passa a `true` només si els quatre camps són correctes |

### Validacions implementades

1. **Nom d'usuari:** Obligatori (no pot quedar en blanc).
2. **Correu electrònic:** Ha de tenir format d'email vàlid (`Patterns.EMAIL_ADDRESS`).
3. **Contrasenya:** Ha de tenir un mínim de **8 caràcters**.
4. **Confirmació de contrasenya:** Ha de coincidir exactament amb la contrasenya escrita.

```kotlin
// Comprovació final dins de SignUpViewModel
_isFormValid.value =
    _usernameError.value == null &&
    _emailError.value == null &&
    _passwordError.value == null &&
    _confirmPasswordError.value == null
```

### Connexió a `SignUpActivity.kt`

En aquest cas obtenim el `ViewModel` mitjançant el delegat `by viewModels()` de la llibreria `activity-ktx`:

```kotlin
private val viewModel: SignUpViewModel by viewModels()
```

Quan l'usuari prem `btnSignUp`, s'executa `validateForm()`. Cada `TextInputLayout` mostra l'error corresponent gràcies al seu observador. Si tots els camps són vàlids (`isFormValid == true`), es mostra el missatge *"Cuenta creada con éxito"* i es redirigeix a `logInActivity`.

---

## 4. Millores de disseny i usabilitat als formularis

Perquè els missatges d'error de les validacions es vegin bé a qualsevol dispositiu:

- **Contenidor `ScrollView`:** Tant `activity_log_in.xml` com `activity_sign_up.xml` fan servir un `ScrollView` amb `android:fillViewport="true"`. Així, quan s'obre el teclat i apareixen els missatges d'error, l'usuari pot desplaçar la pantalla sense que quedin botons tallats.
- **Espai reservat per a errors (`app:errorEnabled="true"`):** Els `TextInputLayout` del Login reserven l'espai del missatge d'error per evitar salts visuals bruscos en mostrar-lo o amagar-lo.
- **Visibilitat de contrasenya:** Tots els camps de contrasenya mantenen `app:endIconMode="password_toggle"` perquè l'usuari pugui comprovar què ha escrit abans d'enviar el formulari.

---

## 5. Limitacions i possibles millores

- **Sense persistència ni base de dades:** Actualment es valida el format de les dades introduïdes, però encara no es comprova contra una base de dades si l'usuari ja existeix ni es desa el registre de forma permanent.
- **Unificar l'estil dels dos ViewModels:** `LogInViewModel` utilitza `""` per indicar que no hi ha error i s'inicialitza amb `ViewModelProvider`, mentre que `SignUpViewModel` utilitza `String?` (`null`) i `by viewModels()`. Més endavant es poden unificar amb el mateix criteri dins el paquet `viewmodel`.
- **Esdeveniments d'un sol ús:** Els `LiveData` de navegació (`loginSuccess` i `isFormValid`) es podrien reiniciar després de navegar per evitar que es tornin a disparar si es torna enrere.