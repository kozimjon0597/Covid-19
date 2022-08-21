package uz.kozimjon.covid19app.paper_db

import io.paperdb.Paper
import uz.kozimjon.covid19app.model.SavedArticle

object Viewed {
    private const val VIEWED_DB_NAME = "viewed"

    fun existViewed(savedArticle: SavedArticle): Boolean {
        val favourites = Paper.book().read<ArrayList<SavedArticle>>(VIEWED_DB_NAME)
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

    fun addViewed(savedArticle: SavedArticle) {
        val favourites = Paper.book().read<ArrayList<SavedArticle>>(VIEWED_DB_NAME)
        if (favourites.isNullOrEmpty()) {
            val favourite = ArrayList<SavedArticle>()
            favourite.add(savedArticle)
            Paper.book().write(VIEWED_DB_NAME, favourite)
        } else {
            favourites.add(savedArticle)
            Paper.book().write(VIEWED_DB_NAME, favourites)
        }
    }

    fun removeViewed(savedArticle: SavedArticle) {
        val favourites = Paper.book().read<ArrayList<SavedArticle>>(VIEWED_DB_NAME)
        favourites?.remove(savedArticle)
        Paper.book().write(VIEWED_DB_NAME, favourites!!)
    }

    fun getViewed(): ArrayList<SavedArticle> {
        return Paper.book().read(VIEWED_DB_NAME, arrayListOf())!!
    }
}