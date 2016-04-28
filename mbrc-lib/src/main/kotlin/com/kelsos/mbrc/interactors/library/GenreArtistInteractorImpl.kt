package com.kelsos.mbrc.interactors.library

import com.google.inject.Inject
import com.kelsos.mbrc.domain.Artist
import com.kelsos.mbrc.mappers.ArtistMapper
import com.kelsos.mbrc.repository.library.ArtistRepository
import rx.Observable
import rx.functions.Func1

class GenreArtistInteractorImpl : GenreArtistInteractor {
    @Inject private lateinit var repository: ArtistRepository

    override fun getGenreArtists(genreId: Long): Observable<List<Artist>> {
        return repository.getArtistsByGenreId(genreId)
                .flatMap<List<Artist>>(Func1 {
                    Observable.just(ArtistMapper.mapGenreArtists(it))
                })
    }
}
