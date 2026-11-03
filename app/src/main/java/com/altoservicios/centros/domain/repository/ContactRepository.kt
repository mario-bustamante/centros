package com.altoservicios.centros.domain.repository

import com.altoservicios.centros.domain.model.ValoresFormulario
import com.altoservicios.centros.domain.util.Resource

interface ContactRepository {

    suspend fun getValues(): Resource<ValoresFormulario>
}
