package com.altoservicios.centros.presentation.screens.client

import com.altoservicios.centros.domain.model.Photo
import com.altoservicios.centros.domain.model.PhotoMeta
import com.altoservicios.centros.domain.model.PhotoResponse
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.domain.model.Room
import com.altoservicios.centros.domain.util.Resource
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ClientResidenceCacheTest {
    @Test
    fun reusesDataAndInvalidatesOnlyChangedData() = runBlocking {
        val cache = ClientResidenceCache()
        cache.selectResidence("1")
        var residenceCalls = 0
        var roomCalls = 0
        var photoCalls = 0
        suspend fun residence() = cache.residence("1") {
            residenceCalls++
            Resource.Success(Residence(id = "1"))
        }
        suspend fun rooms() = cache.rooms("1") {
            roomCalls++
            Resource.Success(listOf(Room(id = 1)))
        }
        suspend fun photos() = cache.photos("1") {
            photoCalls++
            Resource.Success(PhotoResponse(listOf(Photo(id = photoCalls)), PhotoMeta(6)))
        }

        residence()
        rooms()
        photos()
        residence()
        rooms()
        photos()
        assertEquals(1, residenceCalls)
        assertEquals(1, roomCalls)
        assertEquals(1, photoCalls)

        cache.invalidatePhotos("1")
        val updated = photos() as Resource.Success
        residence()
        rooms()
        assertEquals(2, updated.data.data?.single()?.id)
        assertEquals(1, residenceCalls)
        assertEquals(1, roomCalls)
        assertEquals(2, photoCalls)

        cache.invalidateResidence("1")
        cache.invalidateRooms("1")
        residence()
        rooms()
        assertEquals(2, residenceCalls)
        assertEquals(2, roomCalls)
    }

    @Test
    fun selectionAndClearDiscardCachedDataAndFailuresAreNotCached() = runBlocking {
        val cache = ClientResidenceCache()
        cache.selectResidence("1")
        var calls = 0
        suspend fun load(id: String) = cache.residence(id) {
            calls++
            Resource.Success(Residence(id = id))
        }
        load("1")
        cache.selectResidence("1")
        load("1")
        cache.selectResidence("2")
        assertEquals("2", (load("2") as Resource.Success).data.id)
        cache.clear()
        load("2")
        assertEquals(4, calls)

        cache.invalidateResidence("2")
        val failure = cache.residence("2") { Resource.Failure("error") }
        assertTrue(failure is Resource.Failure)
        load("2")
        assertEquals(5, calls)
    }

    @Test
    fun doesNotCacheAnOldResponseAfterReselectionOrInvalidation() = runBlocking {
        val cache = ClientResidenceCache()
        cache.selectResidence("1")
        val started = CompletableDeferred<Unit>()
        val finish = CompletableDeferred<Unit>()
        val oldRequest = async {
            cache.residence("1") {
                started.complete(Unit)
                finish.await()
                Resource.Success(Residence(id = "1", name = "old"))
            }
        }
        started.await()
        cache.selectResidence("1")
        val current = cache.residence("1") { Resource.Success(Residence(id = "1", name = "new")) }
        finish.complete(Unit)
        oldRequest.await()

        val cached = cache.residence("1") { Resource.Success(Residence(id = "1", name = "wrong")) }
        assertEquals("new", (current as Resource.Success).data.name)
        assertEquals("new", (cached as Resource.Success).data.name)
    }

    @Test
    fun keepsDraftsUntilSavedOrResidenceIsReselected() {
        val cache = ClientResidenceCache()
        val first = Residence(id = "1", name = "first")
        val later = first.copy(name = "later")
        cache.selectResidence("1")
        cache.setResidenceDraft("1", first)
        cache.setRoomsDraft("1", mapOf(1 to "2"))
        cache.setResidenceDraft("1", later)
        cache.clearResidenceDraft("1", first)
        cache.clearRoomsDraft("1", mapOf(1 to "1"))
        assertEquals(later, cache.residenceDraft("1"))
        assertEquals(mapOf(1 to "2"), cache.roomsDraft("1"))

        cache.clearResidenceDraft("1", later)
        assertNull(cache.residenceDraft("1"))
        cache.selectResidence("1")
        assertNull(cache.roomsDraft("1"))
    }
}
