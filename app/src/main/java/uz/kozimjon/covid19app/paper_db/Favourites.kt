package uz.kozimjon.covid19app.paper_db

import io.paperdb.Paper
import uz.kozimjon.covid19app.model.SavedArticle

object Favourites {
    private const val FAVOURITES_DB_NAME = "favourites"

    fun existFavourite(savedArticle: SavedArticle): Boolean {
        val favourites = Paper.book().read<ArrayList<SavedArticle>>(FAVOURITES_DB_NAME)
        var isExist = false
        if (!favourites.isNullOrEmpty()) {
            for (favourite in favourites) {
                if (favourite == savedArticle) {
                    isExist = true
                    break
                }
            }
        }
        return isExist
    }

    fun addFavourite(savedArticle: SavedArticle) {
        val favourites = Paper.book().read<ArrayList<SavedArticle>>(FAVOURITES_DB_NAME)
        if (favourites.isNullOrEmpty()) {
            val favourite = ArrayList<SavedArticle>()
            favourite.add(savedArticle)
            Paper.book().write(FAVOURITES_DB_NAME, favourite)
        } else {
            favourites.add(savedArticle)
            Paper.book().write(FAVOURITES_DB_NAME, favourites)
        }
    }

    fun removeFavourite(savedArticle: SavedArticle) {
        val favourites = Paper.book().read<ArrayList<SavedArticle>>(FAVOURITES_DB_NAME)
        favourites?.remove(savedArticle)
        Paper.book().write(FAVOURITES_DB_NAME, favourites!!)
    }

    fun getFavourites(): ArrayList<SavedArticle> {
        return Paper.book().read(FAVOURITES_DB_NAME, arrayListOf())!!
    }
}