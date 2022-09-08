# Provera klasterabilnosti neusmerenih označenih mreža

## Algoritam
Implementiran je algoritam za proveru klasterabilnosti mreže. Provera se vrši pozivom metoda `checkIsClusterable()`[5]. Algoritam prvo generiše listu klastera, ignorišući negativne linkove. Nakon toga se proverava da li postoje dva čvora u klasteru koji su u originalnoj mreži povezani linkom sa negativnom težinom. Kada program naiđe na prvu pojavu takavog linka, vraća se vrednost `false`.

Ukoliko se pozove metod `edgesToRemove()`, algoritam će izvršavati isti proces kao i za određivanje da li je mreža klasterabilna, sa razlikom da će svaki takav link biti upisana u `HashSet` i pretraga će obuhvatiti sve klastere. Pozivom metoda `numberOfEdgesToRemove()` moguće je dobiti i broj linkova koje treba ukloniti da bi mreža bila klasterabilna.

Metod `getListOfClusters` omogućava dobijanje liste grafova klastera (listu `UndirectedSparseGraph` objekata). Pored liste klastera, moguće je dobiti i listu koalicija, kao i listu klastera koji nisu koalicije. Ovi podaci su dobijeni prilikom pretrage da li postoje linkovi negativne težine u klasteru.
Dostupan je i metod `getClusterNetwork()` koji vraća graf takav da čvorovi predstavljaju klastere, a linkovi negativne linkove između klastera, ako takve postoje u originalnom grafu.


## Testiranje na malim mrežama
Prvi korak u testiranju algoritma je bio pisanjem junit testova. Generisan je graf od 4 čvora i 5 linkova, predstavljen na slici 1 (_Slika 1_). 
* Prvi test je sve linkove posmatrao kao pozitivne, samim tim očekivan rezultat je bio da je graf klasterabilan i da postoji jedan klaster.
* Drugi test je sve linkove posmatrao kao negativne i očekivan rezultat je bio da je graf i dalje klaterabilan, ali da su svi klasteri izolovani čvorovi.
* Treći test je link između čvorova "1" i "3" posmatrao kao link sa negativnom težinom i time je dobijen graf koji nije klasterabilan. Očekivan rezultat u ovom slučaju je bio da je link koji je potrebno ukloniti upravo taj, što je i postigunuto. Uklanjanjem tog linka je dobijen klasterabilan graf.

<img src="img/graph_custom.png" width="200">

_Slika 1. Ručno generisan graf_

## Testiranje na random mrežama
Za generisanje random grafa je korišćen Erdős–Rényi model implementiran u _networx_ biblioteci u programskom jeziku Python. Nakon što je generisana random mreža, svakom linku je dodeljena težina -1 ili 1 da bi se dobila označena mreža. Dodela pozitivne ili negativne vrednosti se radila tako što se proveravala deljivost broja čvora sa brojem 5. Ukoliko su oba čvora deljiva sa brojem 5, postojala je mogućnost da budu povezani. Generisani su grafovi sa 10000 i 100000 čvorova sa mogućnošću povezivanja dva čvora 0.15.

Nakon testiranja, nad generisanim mrežama su uklonjeni određeni pozitivni linkovi da bi se dobila mreža koja nije klasterabilna. Uklanjanje linkova je vršeno na sledeći način: ako su čvor A i čvor B susedni i povezani linkom sa pozitivnom težinom, proverava se da li postoji čvor C koji je sused sa oba čvora i povezan je sa oba linkom sa pozitivnom težinom. Ukoliko postoje takva tri čvora, menja se težina linka između čvorova A i B, tako da postaje negativna.

## Testiranje nad realnim mrežama
Nakon tesova na ručno napravljenim mrežama i na random mrežama, program je pokrenut i na realnim mrežama: Epinions, Wikipedia i Slashdot[1]

### Epinions

Mreža se sastoji od 131828 čvorova i 841372 linkova. Dobijeno je da mreža nije klasterabilna i da je potrebno ukloniti  84518 linkova i da se dobija 23614 klastera. Najveći klaster sadrži 100631 čvorova, dok najmanji sadrži 1.


### Wikipedia
Dobijeno je da se mreža nije klasterabilna. Potrebno je ukloniti 3965 linkova i dobija se 233 klastera. Najveći klaster ima 2255 čvorova.

### Slashdot
Posmatrana mreža se sastojala od 77350 čvorova i 516575 linkova. Pokretanjem programa je dobijeno da je mreža nije klasterabilna. Potrebno je ukloniti 103269 linkova i da se dobija 7286 klastera. Najveći klaster sadrži 69714 čvorova, dok najmanji sadrži 1.


## Reference
[1] https://snap.stanford.edu/data/#signnets

[2] http://jung.sourceforge.net/

[3] https://www.researchgate.net/publication/251879688_Jung_2_Tutorial

[4] https://networkx.org/documentation/stable/reference/index.html

[5] https://github.com/DianaSantavec/SOCNET-project