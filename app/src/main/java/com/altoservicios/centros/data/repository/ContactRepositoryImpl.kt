package com.altoservicios.centros.data.repository

import com.altoservicios.centros.data.dataSource.remote.service.ContactService
import com.altoservicios.centros.domain.model.ContactValuesData
import com.altoservicios.centros.domain.model.ValoresFormulario
import com.altoservicios.centros.domain.repository.ContactRepository
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.domain.util.ResponseToRequest

class ContactRepositoryImpl(
    private val service: ContactService
) : ContactRepository {

    override suspend fun getValues(): Resource<ValoresFormulario> =
        when (val response = ResponseToRequest.call { service.getValues() }) {
            is Resource.Success -> response.data.data?.let {
                Resource.Success(it.toValues())
            } ?: Resource.Failure("La respuesta de contacto no contiene datos")
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }

    private fun ContactValuesData.toValues() = ValoresFormulario(
        urgencia = urgency.orEmpty().toList(),
        parentesco = relationship.orEmpty().toList(),
        trato = title.orEmpty().toList(),
        textos = texts.orEmpty(),
        codigos = codes.orEmpty().map { it.code.orEmpty() to "${it.flag.orEmpty()} ${it.country.orEmpty()}".trim() },
        fecha = date
    )
}
