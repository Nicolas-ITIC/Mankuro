# Mankuro

App Android de biblioteca de mangas i manhwas (Institut TIC de Barcelona).

## Documentació

| Document | Què explica |
|---|---|
| [MenuNavegacio.md](MenuNavegacio.md) | Menú de navegació inferior i com es carreguen els Fragments |
| [LlistesISplash.md](LlistesISplash.md) | Pantalla inicial (splash) i llistes dinàmiques (RecyclerView, Adapter i Holder) |
| [Accessibilitat.md](Accessibilitat.md) | Accions d'accessibilitat, proves i limitacions |
| [ArquitecturaMVVM.md](ArquitecturaMVVM.md) | Patró MVVM, classes `ViewModel`, `LiveData` i validacions de Login i Registre |

## Activitat 06 - Menú i llistes

| Què demana l'activitat | On està explicat |
|---|---|
| Pantalla inicial (splash) amb animació | [LlistesISplash.md](LlistesISplash.md), apartat 1 |
| Menú de navegació funcional | [MenuNavegacio.md](MenuNavegacio.md) |
| RecyclerView amb Adapter i Holder | [LlistesISplash.md](LlistesISplash.md), apartat 2 |
| Events afegir, modificar i eliminar (Log o Toast) | [LlistesISplash.md](LlistesISplash.md), taula d'events |
| Accessibilitat | [Accessibilitat.md](Accessibilitat.md) |

## Activitat 7.1 - Login i Registre

| Què demana l'activitat | On està explicat |
|---|---|
| Separació de responsabilitats (patró MVVM) | [ArquitecturaMVVM.md](ArquitecturaMVVM.md), resum i apartat 1 |
| Ús de `ViewModel` i `LiveData` (observabilitat) | [ArquitecturaMVVM.md](ArquitecturaMVVM.md), apartat 1 |
| Recodificació i validació del Login (`LogInViewModel`) | [ArquitecturaMVVM.md](ArquitecturaMVVM.md), apartat 2 |
| Recodificació i validació del Registre (`SignUpViewModel`) | [ArquitecturaMVVM.md](ArquitecturaMVVM.md), apartat 3 |

## Resum

- **Splash:** el logotip apareix amb una animació i obre la pantalla principal.
- **Menú:** una `MainPageActivity` amb un `BottomNavigationView` que carrega cinc Fragments (Biblioteca, Explorar, Comunitat, Perfil i Ajustos).
- **Llistes:** tres RecyclerView (Biblioteca, Explorar i Comunitat) amb dades de prova i events d'afegir, modificar i eliminar.
- **Accessibilitat:** pantalla d'ajustos (mida del text, alt contrast, reduir animacions i vibració), mode clar i fosc, i millores de contrast i mida tàctil.
- **Arquitectura MVVM (Login i Registre):** validació de formularis (usuari, correu electrònic i contrasenya) separada de la vista mitjançant `LogInViewModel`, `SignUpViewModel` i `LiveData`.
