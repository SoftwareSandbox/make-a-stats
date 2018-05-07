package be.swsb.makeastats.kotlinbackend.controllers.util

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor.*
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.type.CollectionType
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.IOException

object ObjectMapperFactory {

    private val OBJECT_MAPPER = ObjectMapper()
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setVisibility(FIELD, JsonAutoDetect.Visibility.ANY)
            .setVisibility(CREATOR, JsonAutoDetect.Visibility.NONE)
            .setVisibility(GETTER, JsonAutoDetect.Visibility.NONE)
            .setVisibility(IS_GETTER, JsonAutoDetect.Visibility.NONE)
            .setVisibility(SETTER, JsonAutoDetect.Visibility.NONE)
            .registerModule(KotlinModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    fun writer(): ObjectWriter {
        return OBJECT_MAPPER.writer()
    }

    fun reader(): ObjectReader {
        return OBJECT_MAPPER.reader()
    }

    fun instance(): ObjectMapper {
        return OBJECT_MAPPER
    }

    @Throws(IOException::class)
    fun json(someObject: Any): String {
        return instance().writeValueAsString(someObject)
    }

    @Throws(IOException::class)
    fun <T> json(json: String, clazz: Class<T>): T {
        return reader().forType(clazz).readValue(json)
    }

    @Throws(IOException::class)
    fun <T> json(json: String, clazz: CollectionType): List<T> {
        return reader().forType(clazz).readValue(json)
    }

    fun <T> list(clazz: Class<T>): CollectionType {
        return reader().typeFactory.constructCollectionType(MutableList::class.java, clazz)
    }
}
