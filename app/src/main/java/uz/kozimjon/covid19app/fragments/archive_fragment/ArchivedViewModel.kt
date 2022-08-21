package uz.kozimjon.covid19app.fragments.archive_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.kozimjon.covid19app.model.SavedArticle
import uz.kozimjon.covid19app.paper_db.Archived

class ArchivedViewModel: ViewModel() {
    private var _archivedList = MutableLiveData<List<SavedArticle>>()
    val archivedList: LiveData<List<SavedArticle>> = _archivedList

    init {
        _archivedList.value = Archived.getArchived()
    }
}