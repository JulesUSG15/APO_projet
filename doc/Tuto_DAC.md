# DAC

## Introduction

Le **DAC** (Définition d'Automates Cellulaires) est un language qui permet comme son nom l'indique de définir des automates cellulaires. Il permet notamment de définir les voisins et les règles appliquées sur chaque cellules de l'automate (un tableau de dimension n).

Voici un exemple de code écrit en DAC :
```
0, -1;
0, 1;
-1, 0;
1, 0;
@

count1>0 && #0==1?
    $var_Exe4:#2, (0.9+count3+verif(#2==2)):2;
!(#4<=1 || #3==0) && count(#1*count(#2))==total?   /* Ceci est un commentaire */
    2:count1-1;
```

## Documentation

### Généralités

Il est important de noter que la mise en forme n'est pas importante en DAC.
On peut tout écrire sur une ligne mais la mise en forme facilite la compréhension du code.
Les caracteres `/*` et `*/` permettent d'écrire des commentaires qui sont ignorés lors la compilation du fichier DAC.

Par exemple, on peut écrire :
`/* Ceci est un commentaire */`


### Définition des voisins

Un fichier DAC commence toujours par la définition des voisins. 

- Pour définir un voisin, on écrit ses coordonnées relatives à la cellule sur laquelle la règle est appliquée.
- Les coordonnées sont des entiers et sont séparés par le caractère : `,`.
- Tous les voisins doivent avoir le même nombre de coordonnées.
- Ce nombre de coordonnées définit la dimension des tableaux sur lesquels peuvent s'appliquer ces règles.

**Il est donc indispensable de définir au moins 1 voisin même si on ne l'utilise pas.**

- La définition d'un voisin se finit par le caractère `;`.
- La fin de la définition des voisins est marquée par le caractère `@`.

Voici un exemple de définition de voisins :
 ```
0, -1;
0, 1;
-1, 0;
1, 0;
@
```
- 4 voisins définis
- s'applique sur des tableaux de dimensions 2 (car deux coordonnées pour chaque voisin)
- les voisins sont : la cellule du dessus, du dessous, de gauche et de droite 


### Les types de valeurs

La partie suivante du code DAC utilise plusieurs types de valeurs.

##### Le type immediat

Il s'agit d'un simple réel.

Exemple ci-dessous :
- 0
- 0.5
- 0.9
- 2

##### Le type variable

Le type variable est défini par le caractère `$`.
Ce qui suit `$` est une chaine de caracteres composée de lettres, de chiffres et de `_` (underscore).

Les variables valent 0 par défaut et peuvent contenir des réels.

Les valeurs des variables peuvent être modifiés après la définition des règles.

Exemple ci-dessous :
`$var_Exe4` : vaut 0 mais peut être modifié après la définition de la règle
    
##### Le type étude

Le type étude est défini par un mot.
Rien n'empêche de créer une variable du même nom.
Ce type permet de renvoyer un réel après l'étude des voisins ou du tableau.

Voici les différentes études possibles :
- `maximum` : renvoie la valeur maximale parmi les voisins
- `minimum` : renvoie la valeur minimale parmi les voisins
- `majorite` : renvoie la valeur la plus présente parmi les voisins
- `minorite` : renvoie la valeur la moins présente parmi les voisins
- `moyenne` : renvoie la moyenne des valeurs des voisins
- `total` : renvoie le total des valeurs des voisins
- `taille` : renvoie la taille du tableau
        
Par exemple :
`count(#1*count(#2))==total?`
Dans ce code, `total` renvoie le total des valeurs des voisins

##### Les types opérateur binaire

Les types opérateur binaire sont les résultats d'opérations arithmetiques.
- **Addition** défini par `+`. Prend une valeur à sa gauche et à sa droite.
- **Soustraction** défini par `-`. Prend une valeur à sa gauche et à sa droite.
- **Modulo** défini par `%`. Prend une valeur à sa gauche et à sa droite.
- **Multiplication** défini par `*`. Prend une valeur à sa gauche et à sa droite.
- **Division** défini par `/`. Prend une valeur à sa gauche et à sa droite.
- **Puissance** défini par `^`. Prend une valeur à sa gauche et à sa droite.
        
Par exemple :
- `#1*count(#2)` renvoie le produit de `#1` et `count(#2)`.
- `0.9+count3` renvoie l'addition de `0.9` et `count3`.
- `count1-1` renvoie la soustraction de `count1` par `1`.
    
##### Les types opérateur unaire

Les types opérateur unaire sont des fonctions.

- `verif` : Prend une condition à sa droite et renvoie 1 si elle est validée, 0 sinon.
- `count` : Prend une valeur à sa droite et renvoie le nombre de voisins ayant cette valeur.
- `#` : Prend un entier à sa droite et renvoie la valeur contenue dans le i eme voisin. (0 étant la cellule actuelle).
- `cos` : Prend une valeur à sa droite et renvoie le cosinus en degrés de cette valeur.
- `sin` : Prend une valeur à sa droite et renvoie le inus en degrés de cette valeur.
- `tan` : Prend une valeur à sa droite et renvoie la tangente en degrés de cette valeur.
- `exp` : Prend une valeur à sa droite et renvoie l'exponentielle de cette valeur.
- `ln` : Prend une valeur à sa droite et renvoie le logarithme de cette valeur.
- `rand`. Prend un entier supérieur ou égal à 1 à sa droite et renvoi un entier supérieur ou égal à 0 et inférieur strictement à cet entier.
- `coord`. Prend un entier compris entre 1 et la dimension de la règle et renvoi la i ème coordonnée de la cellule.
- `int`. Prend une valeur à sa droite et renvoie la partie entière de cette valeur.
        
Par exemple :
- `verif(#2==2)` renvoie `1` si `#2==2`, `0` sinon
- `count1` renvoie le nombre de voisins dont leur valeur vaut `1`
- `count0` renvoie le nombre de voisins dont leur valeur vaut `0`
- `count(#2)` renvoie le nombre de voisins dont leur valeur vaut celle du voisin 2
- `count(#1*count(#2))` renvoie le nombre de voisins dont leur valeur vaut le résultat de `#1*count(#2)`
- `#0` renvoie la valeur contenue dans la cellule sur laquelle la règle est appliquée
- `#1` renvoie la valeur contenue dans la cellule de coordonnées 0, -1 relativement à la cellule sur laquelle la règle est appliquée
- `#3` renvoie la valeur contenue dans la cellule de coordonnées -1, 0 relativement à la cellule sur laquelle la règle est appliquée
- `#4` renvoie la valeur contenue dans la cellule de coordonnées 1, 0 relativement à la cellule sur laquelle la règle est appliquée


### Définition des règles

Après avoir défini les voisins, on peut désormais définir les règles.
Ces règles sont une succession de conditions et d'actions.
Lorsqu'une condition est validée, les suivantes ne sont pas testées.

##### Les conditions

La définition d'une condition se finit toujours par le caractère `?` et est toujours suivie d'au moins une action.

La condition est testée de gauche à droite et prend en compte le parenthésage.

Une condition peut être composée de plusieurs opérateurs conditionnels :

- **Et** défini par `&&`. Prend une condition à sa gauche et à sa droite.
- **Ou** défini par `||`. Prend une condition à sa gauche et à sa droite.
- **Non** défini par `!`. Prend une condition à sa droite.
- **Egal** défini par `==`. Prend une valeur à sa gauche et à sa droite.
- **Different** défini par `!=`. Prend une valeur à sa gauche et à sa droite.
- **Inférieur** défini par `<`. Prend une valeur à sa gauche et à sa droite.
- **Supérieur** défini par `>`. Prend une valeur à sa gauche et à sa droite.
- **Inférieur ou égal** défini par `<=`. Prend une valeur à sa gauche et à sa droite.
- **Supérieur ou égal** défini par `>=`. Prend une valeur à sa gauche et à sa droite.
        
Par exemple :
- `count1>0 && #0==1` est valide si au moins un voisin vaut 1 et si la cellule sur laquelle la règle est appliquée vaut 0
- `!(#4<=1 || #3==0) && count(#1*count(#2))==total` est valide si `count(#1*count(#2))` vaut le total des valeurs des voisins et que l'on n'a pas `#4` inferieur ou égal à 1 ou `#3` égal à 0.
    
##### Les actions

Une action est toujours précédée d'une condition.
Si la condition du dessus est valide alors on exécute l'action.

Une action se finit toujours par le caractère `;`.

Une action peut être composée de plusieurs instructions séparées par le caractère `,`.

##### Les instructions

Une instruction commence par un coefficient qui représente le poids de l'instruction au sein de l'action.

- Ce coefficient est une valeur.
- Si la valeur est négative, la valeur 0 est appliquée.
- Le coefficient est suivi par `:` puis d'une valeur.

Lors de l'exécution d'une instruction, on assigne à la cellule sur laquelle la règle est appliquée la valeur définie.
        
Lors de l'exécution d'une action, on exécute une seule instruction prise au hasard en fonction de son coefficient.

Plus son coefficient est élevé, plus l'instruction a de chances d'être exécutée.

Prenons un exemple simple : `$x:1, $y:2`. Ce code signifie qu'il y a une probabilité de `$x/($x+$y)` que la cellule sur laquelle la règle s'applique prenne la valeur 1 et une probabilité de `$y/($x+$y)` qu'elle prenne la valeur 2.

Maintenant un exemple plus poussé, reprenons le code de l'introduction :
 ```
0, -1;
0, 1;
-1, 0;
1, 0;
@

count1>0 && #0==1?
    $var_Exe4:#2, (0.9+count3+verif(#2==2)):2;
!(#4<=1 || #3==0) && count(#1*count(#2))==total?   /* Ceci est un commentaire */
    2:count1-1;
```

- Si `count1>0 && #0==1` est valide, on execute `$var_Exe4:#2, (0.9+count3+verif(#2==2)):2`;
- La première instruction a une probabilité de `$var_Exe4/($var_Exe4+0.9+count3+verif(#2==2))` d'être exécutée.
On assignerait alors la valeur du deuxième voisin à la cellule sur laquelle la règle est appliquée.
- La deuxième instruction a une probabilité de `(0.9+count3+verif(#2==2))/($var_Exe4+0.9+count3+verif(#2==2))` d'être exécutée.
On assignerait alors la valeur 2 à la cellule sur laquelle la règle est appliquée.
- Si `!(#4<=1 || #3==0) && count(#1*count(#2))==total` est valide, on execute `2:count1-1;`
- L'instruction a une probabilité de `2/2` = 100% de chances d'être exécutée.
On assignerait alors (le nombre de voisins valant 1) moins 1 à la cellule sur laquelle la règle est appliquée.


Vous avez désormais toutes les clés pour définir vos propres automates cellulaires.

