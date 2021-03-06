package com.kelsos.mbrc.ui.navigation.library.genres

import android.arch.paging.PagedList
import com.kelsos.mbrc.content.library.genres.GenreEntity
import com.kelsos.mbrc.mvp.BaseView
import com.kelsos.mbrc.mvp.Presenter

interface BrowseGenrePresenter : Presenter<BrowseGenreView> {
  fun load()
  fun reload()
}

interface BrowseGenreView : BaseView {
  fun update(pagedList: PagedList<GenreEntity>)
  fun failure(throwable: Throwable)
  fun hideLoading()
}