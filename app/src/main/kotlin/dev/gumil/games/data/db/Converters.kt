package dev.gumil.games.data.db

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dev.gumil.games.data.FieldName
import dev.gumil.games.data.GameImage
import dev.gumil.games.data.GamePlatform
import dev.gumil.games.data.GameVideo
import dev.gumil.games.data.InvolvedCompany

@OptIn(ExperimentalStdlibApi::class)
class Converters {

    private val moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromFieldNameList(value: List<FieldName>?): String =
        moshi.adapter<List<FieldName>>().toJson(value)

    @TypeConverter
    fun toFieldNameList(value: String): List<FieldName>? =
        moshi.adapter<List<FieldName>>().fromJson(value)

    @TypeConverter
    fun fromInvolvedCompanyList(value: List<InvolvedCompany>?): String =
        moshi.adapter<List<InvolvedCompany>>().toJson(value)

    @TypeConverter
    fun toInvolvedCompanyList(value: String): List<InvolvedCompany>? =
        moshi.adapter<List<InvolvedCompany>>().fromJson(value)

    @TypeConverter
    fun fromGamePlatformList(value: List<GamePlatform>?): String =
        moshi.adapter<List<GamePlatform>>().toJson(value)

    @TypeConverter
    fun toGamePlatformList(value: String): List<GamePlatform>? =
        moshi.adapter<List<GamePlatform>>().fromJson(value)

    @TypeConverter
    fun fromGameImageList(value: List<GameImage>?): String =
        moshi.adapter<List<GameImage>>().toJson(value)

    @TypeConverter
    fun toGameImageList(value: String): List<GameImage>? =
        moshi.adapter<List<GameImage>>().fromJson(value)

    @TypeConverter
    fun fromGameVideoList(value: List<GameVideo>?): String =
        moshi.adapter<List<GameVideo>>().toJson(value)

    @TypeConverter
    fun toGameVideoList(value: String): List<GameVideo>? =
        moshi.adapter<List<GameVideo>>().fromJson(value)
}
