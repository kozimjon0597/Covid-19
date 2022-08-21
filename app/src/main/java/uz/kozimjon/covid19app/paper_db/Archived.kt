package uz.kozimjon.covid19app.paper_db

import io.paperdb.Paper
import uz.kozimjon.covid19app.model.SavedArticle

object Archived {
    private const val ARCHIVED_DB_NAME = "archived"

    fun existArchived(savedArticle: SavedArticle): Boolean {
        val favourites = Paper.book().read<ArrayList<SavedArticle>>(ARCHIVED_DB_NAME)
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

    fun addArchived(savedArticle: SavedArticle) {
        val favourites = Paper.book().read<ArrayList<SavedArticle>>(ARCHIVED_DB_NAME)
        if (favourites.isNullOrEmpty()) {
            val favourite = ArrayList<SavedArticle>()
            favourite.add(savedArticle)
            Paper.book().write(ARCHIVED_DB_NAME, favourite)
        } else {
            favourites.add(savedArticle)
            Paper.book().write(ARCHIVED_DB_NAME, favourites)
        }
    }

    fun removeArchived(savedArticle: SavedArticle) {
        val favourites = Paper.book().read<ArrayList<SavedArticle>>(ARCHIVED_DB_NAME)
        favourites?.remove(savedArticle)
        Paper.book().write(ARCHIVED_DB_NAME, favourites!!)
    }

    fun getArchived(): ArrayList<SavedArticle> {
        return Paper.book().read(ARCHIVED_DB_NAME, arrayListOf())!!
    }
}