package com.altoservicios.centros.data.repository

import com.altoservicios.centros.data.dataSource.remote.service.ContactService
import com.altoservicios.centros.domain.model.ContactValuesResponse
import com.altoservicios.centros.domain.model.ValoresFormulario
import com.altoservicios.centros.domain.repository.ContactRepository
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.domain.util.ResponseToRequest
import com.altoservicios.centros.domain.util.map

class ContactRepositoryImpl(
    private val service: ContactService
) : ContactRepository {

    override suspend fun getValues(): Resource<ValoresFormulario> =
        ResponseToRequest.call { service.getValues() }.map { it.toValues() }

    private fun ContactValuesResponse.toValues() = ValoresFormulario(
        urgencia = urgency.orEmpty().toList(),
        parentesco = relationship.orEmpty().toList(),
        trato = title.orEmpty().toList(),
        textos = texts.orEmpty(),
        codigos = codes.orEmpty().map { it.code.orEmpty() to "${it.flag.orEmpty()} ${it.country.orEmpty()}".trim() },
        fecha = date
    )
}
