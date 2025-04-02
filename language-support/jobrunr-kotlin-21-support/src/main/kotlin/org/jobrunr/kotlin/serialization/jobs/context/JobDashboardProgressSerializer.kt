package org.jobrunr.kotlin.serialization.jobs.context

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.*
import org.jobrunr.jobs.context.JobDashboardProgressBar
import org.jobrunr.kotlin.serialization.utils.ClassDiscriminatedContextualSerializer

object JobDashboardProgressSerializer : KSerializer<JobDashboardProgressBar.JobDashboardProgress>, ClassDiscriminatedContextualSerializer.PolymorphicContinuationDeserializer {
	override val descriptor = buildClassSerialDescriptor(JobDashboardProgressBar.JobDashboardProgress::class.qualifiedName!!) {
		element("@class", String.serializer().descriptor)
		element("totalAmount", Long.serializer().descriptor)
		element("succeededAmount", Long.serializer().descriptor)
		element("failedAmount", Long.serializer().descriptor)
		element("progress", Int.serializer().descriptor)
	}

	override fun serialize(encoder: Encoder, value: JobDashboardProgressBar.JobDashboardProgress) = encoder.encodeStructure(descriptor) {
		encodeStringElement(descriptor, 0, value::class.java.typeName)
		encodeLongElement(descriptor, 1, value.totalAmount)
		encodeLongElement(descriptor, 2, value.succeededAmount)
		encodeLongElement(descriptor, 3, value.failedAmount)
		encodeIntElement(descriptor, 4, value.progress)
	}

	override fun CompositeDecoder.continueDecode(): JobDashboardProgressBar.JobDashboardProgress {
		var totalAmount = -1L
		var succeededAmount = 0L
		var failedAmount = 0L

		while (true) {
			when (val index = decodeElementIndex(descriptor)) {
				CompositeDecoder.DECODE_DONE -> break
				0 -> decodeStringElement(descriptor, index)
				1 -> totalAmount = decodeLongElement(descriptor, index)
				2 -> succeededAmount = decodeLongElement(descriptor, index)
				3 -> failedAmount = decodeLongElement(descriptor, index)
				4 -> decodeIntElement(descriptor, index)
				else -> error("Unexpected index $index")
			}
		}

		return JobDashboardProgressBar.JobDashboardProgress(totalAmount).apply {
			setProgress(totalAmount, succeededAmount, failedAmount)
		}
	}

	override fun deserialize(decoder: Decoder) = decoder.decodeStructure(descriptor) {
		continueDecode()
	}
}
