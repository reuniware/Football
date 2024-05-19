Ce code définit une classe FootballRepository qui est responsable de récupérer les données liées aux ligues de football à partir d'un service externe appelé FootballService.
Voici une explication détaillée du code :<br/>
kotlin
class FootballRepository @Inject constructor(private val service: FootballService)

Cette ligne définit la classe FootballRepository et utilise l'annotation @Inject pour injecter une instance de FootballService dans le constructeur. Cela permet de découpler la classe FootballRepository de la création de l'instance de FootballService, facilitant ainsi les tests et la maintenance.<br/>
kotlin
suspend fun getAllLeagues(): ArrayList<League>

Cette fonction suspendue (coroutine) est responsable de récupérer toutes les ligues de football disponibles. Elle retourne une ArrayList d'objets League.<br/>
kotlin
val resp = service.getAllLeagues()
val listOfLeagues = ArrayList<League>()

Ces lignes appellent la méthode getAllLeagues() du service FootballService pour obtenir les données des ligues, et initialise une liste vide listOfLeagues pour stocker les objets League.<br/>
kotlin
resp.results.forEach {
    listOfLeagues.add(League(it.id, it.league, it.sport, it.leagueAlternate))
}

Cette boucle forEach itère sur les résultats obtenus du service FootballService. Pour chaque résultat, elle crée un nouvel objet League avec les propriétés id, league, sport et leagueAlternate, et l'ajoute à la liste listOfLeagues.<br/>
kotlin
return listOfLeagues

Enfin, la fonction retourne la liste listOfLeagues contenant tous les objets League créés à partir des données récupérées du service.
En résumé, ce code fournit une abstraction pour récupérer les données des ligues de football à partir d'un service externe, en les mappant à des objets League et en les retournant sous forme de liste. Cette approche facilite la séparation des préoccupations et permet une meilleure testabilité et maintenabilité du code.
