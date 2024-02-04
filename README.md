# Projet APO
## Introduction
Ce projet a été réalisé par Nicolas GALLET, Jules GINHAC et Maxime ANTOINE dans le cadre de l'unité d'enseignement "Algoritmique et Programmation Objet", à Polytech Lyon.
## Généralités
Ce projet porte sur le thème des automates cellulaires. Nous avons implémenté plusieurs automates (Automate 1D, feu de forêt, majorité et jeu de la vie), utilisables et configurables via une interface graphique. Il est aussi posible de coder vos propres automates dans l'onglet "Personnaliser".
Cela est possible car nous avons créé un langage de programmation permettant de coder plus efficacement nos automates cellulaires : le DAC (Définition d'Automate Cellulaire). La documentation du DAC est disponible dans le dossier `./doc`. Vous pouvez aussi la retrouver en ligne sur le site officiel du DAC que nous avons récemment déployé : https://dac.poly-api.fr/index.php.
Vous pouvez retrouver tous nos fichiers sources sur notre github : https://github.com/JulesUSG15/APO_projet.
Et les fichiers sources du DAC dans cet autre repository : https://github.com/NicoutG/DAC

## Organisation des fichiers
- `./src` : Fichiers source, cela inclut les classes des automates prédéfinis ainsi que les fichiers du DAC.
- `./doc` : Documentation, cela inclut les différents diagrammes UML que nous avons utilisé, ainsi que la documentation du DAC.
- `./data` : Code DAC des automates cellulaires prédéfinis (ainsi que le code d'autres automates utilisables en les chargant depuis l'interface "Personaliser"). On y trouve aussi les tableaux enregistrés.
- `./tests` : Dans ce dossier vous trouverez touts les fichiers des tests unitaires concernant les classes du DAC. Pour réaliser les tests unitaires nous avons utilisé junit.jupiter, nous avons au total 50 tests unitaires fonctionnels.
- `./javadoc` : Dans ce dossier vous retrouverez toute la génération de notre javadoc pour le projet.


## Utilisation
Le programme se lance depuis le fichier `./src/Main.java`.
Ensuite on peut utiliser l'interface graphique pour naviguer entre les différents automates.

### Configuration des automates
Lorsque l'on sélectionne un automate, on peut le configurer avec plusieurs paramètres, chaque automate à ses spécificités.
##### Automate 1D
- Numéro de règle (ex: 123)
- Configuration initiale (ex : 101011011)
- Nombre d'étapes
- Affichage :
    - Etape pas étape : on visualise les changements frame par frame
    - 2D : les lignes s'affichent les unes en dessous des autres
##### Majorité
- Nombre d'étapes
- Nombre de voisins (4, 6 ou 8)
##### Jeu de la vie
- Nombre d'étapes
##### Feu de forêt 
- Nombre d'étapes
- Nombre de voisins (4, 6 ou 8)
- Probabilité de feu instantané (probabilité qu'une forêt prenne feu toute seule)
- Probabilité de propagation du feu
- Puissance du vent
- Orientation du vent (en degrés)
##### Personnaliser
Cette interface est spéciale car ce n'est pas un automate prédéfini, on doit fournir le code DAC de l'automate.
On peut :
- choisir le nombre d'étapes
- Charger une règle (du code DAC)
- Sauvegarder une règle
- Ecrire directement le code DAC dans l'interface. (la syntaxe sera vérifié avant la préparation de la simulation)

Pour écrire en DAC vous pouvez vous référer à la documentation du language : https://dac.poly-api.fr/documentation.php. Vous pouvez aussi utiliser l'IDE en ligne que nous avons fait qui fourni une assistance à la définition des voisins : https://dac.poly-api.fr/code.php 

### Préparation de la simulation
Une fois l'automate l'automate configuré on peut préparer la simulation. Cela permet de définir la grille de départ.
On peut :
- Choisir la taille du tableau.
- Choisir le nombre de couleurs à générer puis les placer. aléatoirement sur la grille.
- Colorer manuellement la grille en cliquant sur les cases.
- Charger un tableau enregistré.
- Sauvegarder un tableau.

Lorsque l'on prépare la simulation depuis l'interface "Personnaliser" on à accès à quelques fonctionnalités en plus :
- La visualisation de la valeur des cases
- La possibilité de choisir le pas entre les valeurs (couleurs) lors de la génération aléatoire.
- Changer le type de grille (carrée ou hexagonale).

### Lancement de la simulation
Une fois l'automate configuré et la simulation préparée on peut lancer la simulation.
Il est ensuite possible de naviguer dans la simualtion en utilisant les flèches du clavier. Vous pouvez aussi voir les statistiques de la grille à un instant t. On peut connaître :
- La valeur maximale
- La valeur minimale
- la valeur moyenne
- la répartition des couleurs sur la grille (en %)

## Liens utiles
- Github du projet : https://github.com/JulesUSG15/APO_projet
- Github du DAC : https://github.com/NicoutG/DAC
- Site Web du DAC : https://dac.poly-api.fr/index.php
- Documentation du DAC : https://dac.poly-api.fr/documentation.php
- IDE DAC en ligne : https://dac.poly-api.fr/code.php
- Présentation d'automates réalisés avec le DAC : https://dac.poly-api.fr/index.php#examples
