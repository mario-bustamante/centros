package com.altoservicios.centros.presentation.screens.client

import com.altoservicios.centros.domain.model.PhotoResponse
import com.altoservicios.centros.domain.model.Province
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.domain.model.Room
import com.altoservicios.centros.domain.model.Town
import com.altoservicios.centros.domain.util.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ensureActive
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@ActivityRetainedScoped
class ClientResidenceCache @Inject constructor() {
    private class Slot<T : Any>(var value: T? = null)

    private val lock = Any()
    private var selectedResidenceId: String? = null
    private var revision = 0L
    private val residence = Slot<Residence>()
    private val rooms = Slot<List<Room>>()
    private val photos = Slot<PhotoResponse>()
    private val provinces = Slot<List<Province>>()
    private val towns = mutableMapOf<Int, Slot<List<Town>>>()
    private var residenceDraft: Residence? = null
    private var roomsDraft: Map<Int, String>? = null

    fun selectResidence(id: String) = synchronized(lock) {
        clearLocked()
        selectedResidenceId = id.takeIf { it.isNotBlank() }
    }

    fun clear() = synchronized(lock) {
        clearLocked()
        selectedResidenceId = null
    }

    fun invalidateResidence(id: String) = invalidate(id, residence)
    fun invalidateRooms(id: String) = invalidate(id, rooms)
    fun invalidatePhotos(id: String) = invalidate(id, photos)

    fun residenceDraft(id: String): Residence? = synchronized(lock) {
        residenceDraft.takeIf { selectedResidenceId == id }
    }

    fun setResidenceDraft(id: String, draft: Residence) = synchronized(lock) {
        if (selectedResidenceId == id) residenceDraft = draft
    }

    fun clearResidenceDraft(id: String, saved: Residence) = synchronized(lock) {
        if (selectedResidenceId == id && residenceDraft == saved) residenceDraft = null
    }

    fun roomsDraft(id: String): Map<Int, String>? = synchronized(lock) {
        roomsDraft.takeIf { selectedResidenceId == id }
    }

    fun setRoomsDraft(id: String, draft: Map<Int, String>) = synchronized(lock) {
        if (selectedResidenceId == id) roomsDraft = draft
    }

    fun clearRoomsDraft(id: String, saved: Map<Int, String>) = synchronized(lock) {
        if (selectedResidenceId == id && roomsDraft == saved) roomsDraft = null
    }

    suspend fun residence(id: String, load: suspend () -> Resource<Residence>) =
        get(id, residence, load)

    suspend fun rooms(id: String, load: suspend () -> Resource<List<Room>>) =
        get(id, rooms, load)

    suspend fun photos(id: String, load: suspend () -> Resource<PhotoResponse>) =
        get(id, photos, load)

    suspend fun provinces(id: String, load: suspend () -> Resource<List<Province>>) =
        get(id, provinces, load)

    suspend fun towns(id: String, provinceId: Int, load: suspend () -> Resource<List<Town>>): Resource<List<Town>> {
        val slot = synchronized(lock) { towns.getOrPut(provinceId) { Slot() } }
        return get(id, slot, load)
    }

    private suspend fun <T : Any> get(
        id: String,
        slot: Slot<T>,
        load: suspend () -> Resource<T>
    ): Resource<T> {
        val requestRevision = synchronized(lock) {
            if (selectedResidenceId == null && id.isNotBlank()) selectedResidenceId = id
            if (selectedResidenceId == id) {
                slot.value?.let { return Resource.Success(it) }
            }
            revision
        }
        val result = load()
        coroutineContext.ensureActive()
        if (result is Resource.Success) {
            synchronized(lock) {
                if (selectedResidenceId == id && revision == requestRevision) {
                    slot.value = result.data
                }
            }
        }
        return result
    }

    private fun <T : Any> invalidate(id: String, slot: Slot<T>) = synchronized(lock) {
        if (selectedResidenceId == id) {
            revision++
            slot.value = null
        }
    }

    private fun clearLocked() {
        revision++
        residence.value = null
        rooms.value = null
        photos.value = null
        provinces.value = null
        towns.clear()
        residenceDraft = null
        roomsDraft = null
    }
}
