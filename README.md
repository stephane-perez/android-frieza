# Frieza 🧊

Application Android (Kotlin + Jetpack Compose) de gestion de congélateur — 100% locale, aucun compte, aucune clé API.

## Fonctionnalités

- Plusieurs congélateurs, chacun avec des tiroirs nommés (ajout, renommage, réordonnancement, suppression)
- Vue verticale : tiroirs empilés et repliables, aliments en puces colorées par catégorie
- Catégories personnalisables (nom + couleur), plusieurs catégories par aliment
- Quantités : nombre, poids (g) et portions, cumulables
- Date de congélation, date « à consommer avant » (raccourcis +3/+6/+12 mois), notes
- Badges de péremption proche, recherche, filtre par catégorie, tri (nom / péremption / ajout récent)
- Dupliquer un aliment, le déplacer d'un tiroir ou congélateur à l'autre (formulaire)
- Liste de courses (avec « ranger au congélateur »)
- Sélection multiple (appui long) puis copie d'un prompt « recettes » pour l'IA de ton choix
- Sauvegarde / restauration JSON
- 100 % local, aucun compte, aucune clé API. Les données de la v1 sont migrées automatiquement.

## Générer l'APK (sans Android Studio)

Tout est compilé par GitHub Actions (`.github/workflows/android-build.yml`) :
le dépôt contient le wrapper Gradle complet, aucune installation locale n'est
nécessaire.

Le workflow « Build APK » se lance :

- à chaque push sur `main` et à chaque pull request vers `main` ;
- à chaque tag `v*` (par ex. `v1.2.0`) ;
- à la demande via **Actions > Build APK > Run workflow**, avec un champ
  optionnel `tag` pour reconstruire un tag existant et l'attacher à sa release.

Numéro de version :

- tag `v1.2.3` → `versionName` 1.2.3 ;
- toute autre build → `0.0.0-dev.<numéro du run>` ;
- `versionCode` = numéro du run, donc toujours croissant.

Chaque run produit un artifact `frieza-apk` (zip contenant `frieza.apk`),
téléchargeable en bas de la page du run.

### Publier une version

1. Sur GitHub, **Releases > Draft a new release**, crée un tag `vX.Y.Z` sur
   `main` et publie la release.
2. Le push du tag déclenche le workflow, qui compile l'APK puis attache
   `frieza.apk` à la release (avec des notes de version générées).

Le tag doit pointer sur un commit qui contient déjà le workflow : un tag ne
compile que le code sur lequel il pointe.

## Installer l'APK sur ton téléphone

1. Télécharge `frieza.apk` depuis la release (ou l'artifact d'un run) sur le
   téléphone.
2. Ouvre le fichier ; Android demande d'autoriser l'installation depuis cette
   source (à activer une fois).
3. Installe. Les mises à jour s'installent par-dessus la version précédente,
   sans perdre les données.

L'APK est signé avec la clé `app/debug.keystore`, commitée dans le dépôt pour
que toutes les builds aient la même signature. Cette clé est publique : elle
convient pour un usage perso, **jamais** pour une publication sur un store.

Minimum : Android 8.0 (API 26).

## Structure du code

```
app/src/main/java/com/frieza/freezer/
├── data/            Entités Room (Freezer, Drawer, Category, Food, ShoppingItem),
│                    DAOs, base + migration v1→v2, Repository, sauvegarde JSON
├── ui/freezer/      Écran principal : tiroirs empilés et repliables, recherche,
│                    filtre, tri, sélection multiple, export presse-papier
├── ui/food/         Formulaire d'ajout / édition / duplication d'un aliment
├── ui/setup/        Création d'un congélateur, édition (renommage, tiroirs)
├── ui/category/     Gestion des catégories (nom, couleur)
├── ui/shopping/     Liste de courses
├── ui/navigation/   Graphe de navigation
├── ui/theme/        Thème Material 3 (couleurs bleu glacé)
└── util/            Dates (stockées à minuit UTC) et formatage des aliments
```

## Pistes pour la suite

- Internationalisation (anglais par défaut, français conservé)
- Alertes de péremption (WorkManager + notifications) à partir de `expirationDate`
- Historique des aliments consommés / statistiques de gaspillage
