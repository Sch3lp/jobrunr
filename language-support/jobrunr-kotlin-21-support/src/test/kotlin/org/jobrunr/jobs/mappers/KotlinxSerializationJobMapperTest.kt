package org.jobrunr.jobs.mappers

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import org.jobrunr.jobs.mappers.JobMapperTest
import org.jobrunr.jobs.mappers.testModule
import org.jobrunr.kotlin.utils.mapper.KotlinxSerializationJsonMapper
import org.jobrunr.utils.mapper.JsonMapper

internal class KotlinxSerializationJobMapperTest : JobMapperTest() {
    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    override fun getJsonMapper(): JsonMapper {
        return KotlinxSerializationJsonMapper(testModule)
    }
}
